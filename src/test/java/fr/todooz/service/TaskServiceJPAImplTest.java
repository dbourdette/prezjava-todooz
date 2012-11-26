package fr.todooz.service;

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.hibernate.Session;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.todooz.domain.Task;
import fr.todooz.util.IntervalUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TaskServiceJPAImplTest {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Inject
    private TaskService taskService;

    @After
    public void cleanDb() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        entityManager.createQuery("delete from Task").executeUpdate();

        transaction.commit();

        entityManager.close();
    }

    @Test
    public void save() {
        Task task = task();

        taskService.save(task);

        Assert.assertNotNull(task.getId());
    }

    @Test
     public void udpate() {
        Task task = task();

        taskService.save(task);
        taskService.save(task);

        Assert.assertEquals(1, taskService.count());
    }

    @Test
    public void delete() {
        Task task = task();

        taskService.save(task);

        taskService.delete(task.getId());

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Assert.assertEquals(0, entityManager.createQuery("from Task").getResultList().size());

        entityManager.close();
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

        Assert.assertEquals(2, taskService.findByQuery("jpa").size());
        Assert.assertEquals(2, taskService.findByQuery("JPA").size());
        Assert.assertEquals(2, taskService.findByQuery("manager").size());
        Assert.assertEquals(0, taskService.findByQuery("hibernate").size());
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
        Assert.assertEquals(1, taskService.findByInterval(IntervalUtils.tomorrowInterval()).size());;
    }

    @Test
    public void count() {
        taskService.save(task());
        taskService.save(task());

        Assert.assertEquals(2, taskService.count());
    }

    @Test
    public void findById() {
        Task task = task();
        taskService.save(task);

        Task dbTask = taskService.findById(task.getId());

        Assert.assertEquals("JPA EntityManager FTW!", dbTask.getTitle());
    }

    private Task task() {
        return task("java,jpa");
    }

    private Task task(String tags) {
        return task(new Date(), tags);
    }

    private Task task(Date date, String tags) {
        Task task = new Task();
        task.setDate(date);
        task.setTitle("JPA EntityManager FTW!");
        task.setText("Use some stuff about JPA");
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
