package fr.todooz.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class HelloEJB {
    @PostConstruct
    public void hello() {
        System.out.println("\n\n\nhello app serv\n\n\n");
    }

    public String hello(String name) {
        return "Hi " + name;
    }
}
