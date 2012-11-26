package fr.todooz.web.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.todooz.domain.Task;
import fr.todooz.ejb.MoreSeriousEJB;
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

    @EJB(mappedName = "java:module/MoreSeriousEJB")
    private MoreSeriousEJB moreSeriousEJB;
	
	@ModelAttribute
	public TagCloud tagCloud() {
		return tagCloudService.buildTagCloud();
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

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(Model model) {
        return moreSeriousEJB.hello("EJBs from Controller");
    }
	
	public String page(Model model, List<Task> tasks) {
		model.addAttribute("tasks", tasks);

		return "index";
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
