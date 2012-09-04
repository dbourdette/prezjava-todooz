package fr.todooz.web.controller;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.todooz.domain.Task;
import fr.todooz.service.TaskService;

@Controller
public class IndexController {
	@Inject
	private TaskService taskService;
	
	@RequestMapping({ "/", "/index" })
	public String index(Model model) {
		model.addAttribute("tasks", taskService.findAll());

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
