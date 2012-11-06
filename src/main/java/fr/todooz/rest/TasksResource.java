package fr.todooz.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import fr.todooz.domain.Task;
import fr.todooz.service.TaskService;

@Component
@Path("/tasks")
public class TasksResource {
    @Inject
    private TaskService taskService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> tasks() {
        return taskService.findAll();
    }
}
