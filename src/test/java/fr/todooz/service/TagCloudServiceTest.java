package fr.todooz.service;

import java.util.Date;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.todooz.domain.Task;
import fr.todooz.util.TagCloud;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TagCloudServiceTest {
	@Inject
	private SessionFactory sessionFactory;

	@Inject
	private TagCloudService tagCloudService;

	@After
	public void cleanDb() {
		Session session = sessionFactory.openSession();

		Transaction transaction = session.beginTransaction();

		session.createQuery("delete from Task").executeUpdate();

		transaction.commit();

		session.close();
	}

	@Test
	public void buildEmptyTagCloud() {
		TagCloud tagCloud = tagCloudService.buildTagCloud();

		Assert.assertEquals(0, tagCloud.size());
	}

	@Test
	public void buildTagCloud() {
		saveSomeTasks();

		TagCloud tagCloud = tagCloudService.buildTagCloud();

		Assert.assertEquals(5, tagCloud.size());
		Assert.assertTrue(tagCloud.contains("java"));
		Assert.assertTrue(tagCloud.contains("python"));
		Assert.assertTrue(tagCloud.contains("nodejs"));
	}

	private void saveSomeTasks() {
		Session session = sessionFactory.openSession();

		Transaction transaction = session.beginTransaction();

		session.save(buildTask("java,cobol"));
		session.save(buildTask("java,python"));
		session.save(buildTask("ruby,python"));
		session.save(buildTask("nodejs"));

		transaction.commit();

		session.close();
	}

	private Task buildTask(String tags) {
		Task task = new Task();

		task.setDate(new Date());
		task.setTitle("Read Effective Java");
		task.setText("Read Effective Java before it's too late");
		task.setTags(tags);

		return task;
	}

}
