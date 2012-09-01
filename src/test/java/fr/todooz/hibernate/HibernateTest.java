package fr.todooz.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.todooz.domain.Task;

public class HibernateTest {
	private SessionFactory sessionFactory;
	
	@Before
	public void createSessionFactory() {
	   Configuration configuration = new Configuration();

	   configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.DerbyDialect");
	   configuration.setProperty("hibernate.connection.url", "jdbc:derby:target/testdb;create=true");
	   configuration.setProperty("hibernate.connection.driver_class", "org.apache.derby.jdbc.EmbeddedDriver");
	   configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");

	   configuration.addAnnotatedClass(Task.class);
	   
	   sessionFactory = configuration.buildSessionFactory();
	}
	
	@After
	public void cleanDb() {
	    Session session = sessionFactory.openSession();

	    session.createQuery("delete from Task").executeUpdate();

	    session.close();

	    sessionFactory.close();
	}
	
	@Test
	public void saveTask() {
		Task task = new Task();

	    task.setDate(new Date());
		task.setTitle("Read Effective Java");
		task.setText("Read Effective Java before it's too late");
		task.setTags("java,java");

	    Session session = sessionFactory.openSession();

	    session.save(task);
	    
	    System.out.println(session.createCriteria(Task.class).list().size());
	    
	    session.close();
	}
	
	@Test
	public void findTask() {
		saveTask();

	    Session session = sessionFactory.openSession();

	    Query query = session.createQuery("from Task where title = :title");

	    query.setString("title", "Read Effective Java");

	    List<Task> tasks = query.list();

	    session.close();

	    Assert.assertEquals(1, tasks.size());
	    Assert.assertEquals("Read Effective Java", tasks.get(0).getTitle());
	}
	
	@Test
	public void findTaskCriteria() {
		saveTask();

	    Session session = sessionFactory.openSession();

	    Criteria criteria = session.createCriteria(Task.class);

	    criteria.add(Restrictions.eq("title", "Read Effective Java"));

	    List<Task> tasks = criteria.list();

	    session.close();

	    Assert.assertEquals(1, tasks.size());
	    Assert.assertEquals("Read Effective Java", tasks.get(0).getTitle());
	}
}
