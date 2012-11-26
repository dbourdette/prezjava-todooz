package fr.todooz.ejb;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

@Singleton
public class MoreSeriousEJB {
    @EJB
    private HelloEJB helloEJB;

    private int count = 0;

    @Schedule(hour = "*", minute = "*", second = "*/5")
    public void endlessly() {
        System.out.println(helloEJB.hello("" + count));

        count++;
    }

    public String hello(String name) {
        return helloEJB.hello(name);
    }

}
