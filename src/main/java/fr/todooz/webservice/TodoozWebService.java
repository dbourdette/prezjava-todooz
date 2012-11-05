package fr.todooz.webservice;

import javax.inject.Inject;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.todooz.domain.Tasks;
import fr.todooz.service.TaskService;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class TodoozWebService extends SpringBeanAutowiringSupport {
    @Inject
    private TaskService taskService;

    public String hi(String name) {
        return "Hello " + StringUtils.defaultIfEmpty(name, "unknown");
    }

    public Tasks getTasks() {
        return new Tasks(taskService.findAll());
    }
}
