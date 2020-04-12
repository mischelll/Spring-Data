package core;

import common.Messages;
import common.UI;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) throws IOException {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Engine engine = new Engine(entityManager);

        Messages.showIntroMessage();
        engine.run();
        System.out.println("--------END OF TASK--------");

        //UI.goON(new BufferedReader(new InputStreamReader(System.in)), engine);
    }
}
