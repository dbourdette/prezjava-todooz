package fr.todooz.rest;

import com.sun.jersey.api.core.PackagesResourceConfig;

public class TodoozApplication extends PackagesResourceConfig {
    public TodoozApplication() {
        super("fr.todooz.rest");
    }
}
