package fr.todooz.web.controller;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.joda.time.DateMidnight;
import org.joda.time.Interval;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.todooz.domain.Task;
import fr.todooz.service.TagCloudService;
import fr.todooz.service.TaskService;
import fr.todooz.util.IntervalUtils;

@Controller
public class IndexController {
	@Inject
	private TaskService taskService;
	
	@Inject
	private TagCloudService tagCloudService;
	
	@RequestMapping({ "/", "/index" })
	public String index(Model model) {
		model.addAttribute("tasks", taskService.findAll());
		model.addAttribute("tagCloud", tagCloudService.buildTagCloud());

		return "index";
	}
	
	@RequestMapping("/search")
	public String search(String query, Model model) {
		model.addAttribute("tasks", taskService.findByQuery(query));
		model.addAttribute("tagCloud", tagCloudService.buildTagCloud());

		return "index";
	}
	
	@RequestMapping("/tag/{tag}")
	public String tag(@PathVariable String tag, Model model) {
		model.addAttribute("tasks", taskService.findByTag(tag));
		model.addAttribute("tagCloud", tagCloudService.buildTagCloud());

		return "index";
	}
	
	@RequestMapping("/today")
	public String today(Model model) {
		model.addAttribute("tasks", taskService.findByInterval(IntervalUtils.todayInterval()));
		model.addAttribute("tagCloud", tagCloudService.buildTagCloud());

		return "index";
	}
	
	@RequestMapping("/tomorrow")
	public String tomorrow(Model model) {
		model.addAttribute("tasks", taskService.findByInterval(IntervalUtils.tomorrowInterval()));
		model.addAttribute("tagCloud", tagCloudService.buildTagCloud());

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
