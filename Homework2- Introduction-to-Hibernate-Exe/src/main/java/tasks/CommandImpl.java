package tasks;

import tasks.interfaces.Command;

import javax.persistence.EntityManager;
import java.io.BufferedReader;

public abstract class CommandImpl implements Command {
    private final EntityManager entityManager;
    private final BufferedReader bufferedReader;

    public CommandImpl(EntityManager entityManager, BufferedReader bufferedReader) {
        this.entityManager = entityManager;
        this.bufferedReader = bufferedReader;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }
}
