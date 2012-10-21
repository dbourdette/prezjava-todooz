package fr.todooz.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.joda.time.Interval;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.todooz.domain.Task;

@Service
public class TaskServiceImpl implements TaskService {
	@Inject
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public void save(Task task) {
		Session session = sessionFactory.getCurrentSession();
		
		session.save(task);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Session session = sessionFactory.getCurrentSession();

		session.delete(session.get(Task.class, id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Task> findAll() {
		Session session = sessionFactory.getCurrentSession();

		return session.createCriteria(Task.class).list();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Task> findByQuery(String query) {
		Session session = sessionFactory.getCurrentSession();

		return session.createCriteria(Task.class)
				.add(Restrictions.ilike("title", query, MatchMode.ANYWHERE)).list();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Task> findByTag(final String tag) {
		List<Task> tasks = findAll();
		
		CollectionUtils.filter(tasks, new Predicate() {			
			@Override
			public boolean evaluate(Object object) {
				return ArrayUtils.contains(((Task) object).getTagArray(), tag);
			}
		});
		
		return tasks;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Task> findByInterval(Interval interval) {
		Session session = sessionFactory.getCurrentSession();

		return session.createCriteria(Task.class)
			.add(Restrictions.between("date", interval.getStart().toDate(), interval.getEnd().toDate()))
			.list();
	}

	@Override
	@Transactional(readOnly = true)
	public int count() {
		Session session = sessionFactory.getCurrentSession();

		return ((Long) session.createCriteria(Task.class)
				.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}
}
