package fr.todooz.web.controller;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.todooz.domain.Task;
import fr.todooz.service.TagCloudService;
import fr.todooz.service.TaskService;
import fr.todooz.util.IntervalUtils;
import fr.todooz.util.TagCloud;

@Controller
public class IndexController {
	@Inject
	private TaskService taskService;
	
	@Inject
	private TagCloudService tagCloudService;

    @Inject
    private DataSource dataSource;

    @ModelAttribute
	public TagCloud tagCloud() {
		return tagCloudService.buildTagCloud();
	}

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
	
	@RequestMapping({ "/", "/index" })
	public String index(Model model) {
		return page(model, taskService.findAll());
	}
	
	@RequestMapping("/search")
	public String search(String query, Model model) {
		return page(model, taskService.findByQuery(query));
	}
	
	@RequestMapping("/tag/{tag}")
	public String tag(@PathVariable String tag, Model model) {
		return page(model, taskService.findByTag(tag));
	}
	
	@RequestMapping("/today")
	public String today(Model model) {
		return page(model, taskService.findByInterval(IntervalUtils.todayInterval()));
	}
	
	@RequestMapping("/tomorrow")
	public String tomorrow(Model model) {
		return page(model, taskService.findByInterval(IntervalUtils.tomorrowInterval()));
	}
	
	public String page(Model model, List<Task> tasks) {
		model.addAttribute("tasks", tasks);

		return "index";
	}

    @PostConstruct
    public void initUsers() throws SQLException {
        Connection connection = dataSource.getConnection();

        try {
            if (!tableExists("users")) {
                connection.prepareStatement("create table users (username varchar(50), password varchar(50), enabled boolean)").execute();
            }

            if (!tableExists("authorities")) {
                connection.prepareStatement("create table authorities (username varchar(50), authority varchar(50))").execute();
            }

            connection.prepareStatement("delete from authorities").execute();
            connection.prepareStatement("delete from users").execute();

            connection.prepareStatement("insert into users (username, password, enabled) values ('test', 'user', true)").execute();
            connection.prepareStatement("insert into authorities (username, authority) values ('test', 'ROLE_USER')").execute();
        } finally {
            connection.close();
        }
    }

    private boolean tableExists(String name) throws SQLException {
        Connection connection = dataSource.getConnection();

        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet rs = dbmd.getTables(null, "APP", name.toUpperCase(), null);
            return rs.next();
        } finally {
            connection.close();
        }
    }
	
	@PostConstruct
	public void bootstrap() {
		for (Task task : taskService.findAll()) {
			taskService.delete(task.getId());
		}
		
		if (taskService.count() == 0) {
			Task task1 = new Task();
			task1.setDate(new Date());
			task1.setTitle("Read Pro Git");
			task1.setTags("scm,git");
			
			taskService.save(task1);
			
			Task task2 = new Task();
			task2.setDate(new Date());
			task2.setTitle("Read Effective Java");
			task2.setTags("java");
			
			taskService.save(task2);
			
			Task task3 = new Task();
			task3.setDate(new Date());
			task3.setTitle("Read Test Driven Development");
			task3.setTags("java,test driven,junit");
			
			taskService.save(task3);
		}
	}
}
