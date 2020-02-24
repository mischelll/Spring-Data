package tasks;

import tasks.interfaces.Command;

import java.io.BufferedReader;
import java.sql.Connection;

public abstract class CommandImpl implements Command {
    private Connection connection;
    private BufferedReader bufferedReader;

    public CommandImpl(Connection connection, BufferedReader bufferedReader) {
        this.connection = connection;
        this.bufferedReader = bufferedReader;
    }

    protected Connection getConnection(){
        return this.connection;
    }

    protected BufferedReader getBufferedReader(){
        return this.bufferedReader;
    }
}
