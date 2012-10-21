package fr.todooz.service;

import java.util.Date;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
public class TaskServiceTest {

	@Inject
	private SessionFactory sessionFactory;
	
	@Inject
	private TaskService taskService;

	@After
	public void cleanDb() {
		Session session = sessionFactory.openSession();

		Transaction transaction = session.beginTransaction();

		session.createQuery("delete from Task").executeUpdate();

		transaction.commit();

		session.close();
	}

	@Test
	public void save() {
		taskService.save(task());
	}
	
	@Test
	public void delete() {
		Task task = task();

		taskService.save(task);

		taskService.delete(task.getId());

	    Session session = sessionFactory.openSession();

	    Assert.assertEquals(0, session.createQuery("from Task").list().size());

	    session.close();
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

	    Assert.assertEquals(2, taskService.findByQuery("read").size());
	    Assert.assertEquals(2, taskService.findByQuery("java").size());
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
	    Assert.assertEquals(1, taskService.findByInterval(IntervalUtils.tomorrowInterval()).size());;
	}
	
	@Test
	public void count() {
		taskService.save(task());
		taskService.save(task());

	    Assert.assertEquals(2, taskService.count());
	}
	
	private Task task() {
		return task("java,java");
	}
	
	private Task task(String tags) {
		return task(new Date(), "java,java");
	}
	
	private Task task(Date date, String tags) {
		Task task = new Task();
	    task.setDate(date);
		task.setTitle("Read Effective Java");
		task.setText("Read Effective Java before it's too late");
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
