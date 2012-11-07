package fr.todooz.hibernate;

import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.MySQL5Dialect;
import org.junit.Test;

import fr.todooz.domain.Task;

public class CreateDBTest {
    @Test
    public void createDB() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(Task.class);

        for (String string : configuration.generateSchemaCreationScript(new MySQL5Dialect())) {
            System.out.println(string + ";");
        }
    }
}
