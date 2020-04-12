package tasks;

import entities.Town;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.util.List;

public class RemoveObjects extends CommandImpl {
    //private static final int REQUIRED_MAX_LENGTH = 5;

    public RemoveObjects(EntityManager entityManager, BufferedReader bufferedReader) {
        super(entityManager, bufferedReader);
    }

    @Override
    public void execute() {
        List<Town> towns = this.getEntityManager()
                .createQuery("SELECT t FROM Town as t " +
                        "WHERE length(t.name) > 5", Town.class)
                .getResultList();

        this.getEntityManager().getTransaction().begin();

        towns.forEach(this.getEntityManager()::detach);

        List<Town> attachedTowns = this.getEntityManager()
                .createQuery("FROM Town where length(name) <= 5", Town.class)
                .getResultList();

        for (Town attachedTown : attachedTowns) {
            attachedTown.setName(attachedTown.getName().toLowerCase());
        }

        towns.forEach(this.getEntityManager()::merge);

        this.getEntityManager().flush();
        this.getEntityManager().getTransaction().commit();
    }
}
