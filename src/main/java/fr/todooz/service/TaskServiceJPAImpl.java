package fr.todooz.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ArrayUtils;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.todooz.domain.Task;

@Service
@Qualifier("jpa")
public class TaskServiceJPAImpl implements TaskService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional("jpa")
    public void save(Task task) {
        if (task.getId() == null) {
            entityManager.persist(task);
        } else {
            entityManager.merge(task);
        }
    }

    @Override
    @Transactional("jpa")
    public void delete(Long id) {
        entityManager.remove(findById(id));
    }

    @Override
    @Transactional(value = "jpa", readOnly = true)
    public List<Task> findAll() {
        return entityManager.createQuery("from Task order by date desc").getResultList();
    }

    @Override
    @Transactional(value = "jpa", readOnly = true)
    public List<Task> findByQuery(String query) {
        return entityManager.createQuery("from Task where lower(title) LIKE :title order by date desc").setParameter("title", "%" + query.toLowerCase() + "%").getResultList();
    }

    @Override
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
    public List<Task> findByInterval(Interval interval) {
        return entityManager.createQuery("from Task where date > :startDate and date < :endDate order by date desc")
                .setParameter("startDate", interval.getStart().toDate())
                .setParameter("endDate", interval.getEnd().toDate())
                .getResultList();
    }

    @Override
    public int count() {
        return findAll().size();
    }

    @Override
    public Task findById(Long id) {
        return entityManager.find(Task.class, id);
    }
}
