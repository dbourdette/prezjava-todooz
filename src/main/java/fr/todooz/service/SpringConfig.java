package fr.todooz.service;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import fr.todooz.domain.MongoTask;

@Configuration
public class SpringConfig {
    @Bean
    public Datastore datastore() throws MongoException, UnknownHostException {
        Morphia morphia = new Morphia();

        morphia.map(MongoTask.class);

        return morphia.createDatastore(new Mongo(), "todooz");
    }
}
