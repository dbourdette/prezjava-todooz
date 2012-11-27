package fr.todooz.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ArrayUtils;
import org.bson.types.ObjectId;
import org.joda.time.Interval;

import com.google.code.morphia.Datastore;

import fr.todooz.domain.MongoTask;

public class TaskServiceMorphia {

    @Inject
    private Datastore datastore;

    public void save(MongoTask task) {
        datastore.save(task);
    }

    public void delete(ObjectId id) {
        datastore.delete(findById(id));
    }

    public List<MongoTask> findAll() {
        return datastore.find(MongoTask.class)
                .order("-date").asList();
    }

    public List<MongoTask> findByQuery(String query) {
        return datastore.find(MongoTask.class)
                .field("title").containsIgnoreCase(query)
                .order("-date").asList();
    }

    public List<MongoTask> findByTag(final String tag) {
        List<MongoTask> tasks = findAll();

        CollectionUtils.filter(tasks, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ArrayUtils.contains(((MongoTask) object).getTagArray(), tag);
            }
        });

        return tasks;
    }

    public List<MongoTask> findByInterval(Interval interval) {
        return datastore.find(MongoTask.class)
                .filter("date >=", interval.getStart().toDate())
                .filter("date <", interval.getEnd().toDate())
                .order("-date").asList();
    }

    public int count() {
        return (int) datastore.find(MongoTask.class).countAll();
    }

    public MongoTask findById(ObjectId id) {
        return datastore.get(MongoTask.class, id);
    }
}
