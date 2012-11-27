package fr.todooz.service;

import java.util.Date;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.code.morphia.Datastore;

import fr.todooz.domain.MongoTask;
import fr.todooz.domain.Task;
import fr.todooz.util.IntervalUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TaskServiceMorphiaTest {
    @Inject
    private Datastore datastore;

    @Inject
    private TaskServiceMorphia taskService;

    @Before
    public void cleanDb() {
        datastore.delete(datastore.find(Task.class));
    }

    @Test
    public void save() {
        MongoTask task = task();

        taskService.save(task);

        Assert.assertNotNull(task.getId());
    }

    @Test
    public void udpate() {
        MongoTask task = task();

        taskService.save(task);
        taskService.save(task);

        Assert.assertEquals(1, taskService.count());
    }

    @Test
    public void delete() {
        MongoTask task = task();

        taskService.save(task);

        taskService.delete(task.getId());

        Assert.assertEquals(0, datastore.find(MongoTask.class).countAll());
    }

    @Test
    public void findAll() {
        taskService.save(task());
        taskService.save(task());

        Assert.assertEquals(2, taskService.findAll().size());
    }

    @Test
    public void findByQuery() {
        taskService.save(task());
        taskService.save(task());

        Assert.assertEquals(2, taskService.findByQuery("mongo").size());
        Assert.assertEquals(2, taskService.findByQuery("morphia").size());
        Assert.assertEquals(0, taskService.findByQuery("driven").size());
    }

    @Test
    public void findByTag() {
        taskService.save(task("java,python"));
        taskService.save(task("java,ruby"));

        Assert.assertEquals(2, taskService.findByTag("java").size());
        Assert.assertEquals(1, taskService.findByTag("ruby").size());
        Assert.assertEquals(0, taskService.findByTag("scala").size());
    }

    @Test
    public void findByInterval() {
        taskService.save(task(today(), "java"));
        taskService.save(task(today(), "java"));
        taskService.save(task(tomorrow(), "java"));

        Assert.assertEquals(2, taskService.findByInterval(IntervalUtils.todayInterval()).size());
        Assert.assertEquals(1, taskService.findByInterval(IntervalUtils.tomorrowInterval()).size());
        ;
    }

    @Test
    public void count() {
        taskService.save(task());
        taskService.save(task());

        Assert.assertEquals(2, taskService.count());
    }

    @Test
    public void findById() {
        MongoTask task = task();
        taskService.save(task);

        MongoTask dbTask = taskService.findById(task.getId());

        Assert.assertEquals("Mongo and Morphia Rock!", dbTask.getTitle());
    }

    private MongoTask task() {
        return task("java,mongo");
    }

    private MongoTask task(String tags) {
        return task(new Date(), tags);
    }

    private MongoTask task(Date date, String tags) {
        MongoTask task = new MongoTask();
        task.setDate(date);
        task.setTitle("Mongo and Morphia Rock!");
        task.setText("Use some morphia stuff");
        task.setTags(tags);
        return task;
    }

    private Date today() {
        return new Date();
    }

    private Date tomorrow() {
        return new DateTime().plusDays(1).toDate();
    }
}
