package fr.todooz.selenium;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import fr.javafreelance.fluentlenium.core.test.FluentTest;

public class TodoozTest extends FluentTest {
    @Test
    public void home() {
        goTo("http://localhost:8080");

        Assert.assertEquals("Todooz", title());
    }

    @Test
    public void add() {
        goTo("http://localhost:8080/add");

        fill("#title").with("test task");
        fill("#tags").with("test,java");
        fill("#text").with("some test text");
        submit("#title");

        goTo("http://localhost:8080/");

        await().atMost(5, TimeUnit.SECONDS).until("a").withText("test task").isPresent();
    }
}
