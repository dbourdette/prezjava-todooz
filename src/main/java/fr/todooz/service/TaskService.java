package fr.todooz.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import fr.todooz.domain.Task;

public class TaskService {
	private SessionFactory sessionFactory;

	public void save(Task task) {
		Session session = sessionFactory.openSession();

		session.save(task);
		
		session.close();
	}

	public void delete(Long id) {
		Session session = sessionFactory.openSession();

		session.delete(session.get(Task.class, id));
		
		session.flush();
		session.close();
	}

	public List<Task> findAll() {
		Session session = sessionFactory.openSession();

		List<Task> tasks = session.createCriteria(Task.class).list();
		
		session.close();
		
		return tasks;
	}

	public List<Task> findByQuery(String query) {
		Session session = sessionFactory.openSession();

		List<Task> tasks = session.createCriteria(Task.class)
				.add(Restrictions.ilike("title", query, MatchMode.ANYWHERE)).list();
		
		session.close();
		
		return tasks;
	}

	public int count() {
		Session session = sessionFactory.openSession();

		int count = ((Long) session.createCriteria(Task.class)
				.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		
		session.close();
		
		return count;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
