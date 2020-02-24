package core;

import tasks.*;
import tasks.interfaces.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Engine implements Runnable {
    private Connection connection;
    private BufferedReader bufferedReader;
    private DbManipulation dbManipulation;


    public Engine(Connection connection, BufferedReader bufferedReader) {
        this.connection = connection;
        this.bufferedReader = bufferedReader;
        dbManipulation = new DbManipulation();

    }

    @Override
    public void run() {
        boolean check = false;
        try {
            String inputCommand = bufferedReader.readLine();
            switch (inputCommand) {
                case "Get Villain's Name":
                    new GetVillainsNames(this.connection, this.bufferedReader).execute();
                    check = true;
                    break;

                case "Get Minions Name":
                    new GetMinionsNames(this.connection, this.bufferedReader).execute();
                    check = true;
                    break;

                case "Add Minion":
                    new AddMinion(this.connection, this.bufferedReader).execute();
                    check = true;
                    break;

                case "Change Town Names Casing":
                    new ChangeTownNamesCasing(this.connection, this.bufferedReader).execute();
                    check = true;
                    break;

                case "Remove Villain":
                    new RemoveVillain(this.connection, this.bufferedReader).execute();
                    check = true;
                    break;

                case "Print All Minion Names":
                    new PrintAllMinionNames(this.connection, this.bufferedReader).execute();
                    check = true;
                    break;

                case "Increase Minions Age":
                    new IncreaseMinionsAge(this.connection, this.bufferedReader).execute();
                    check = true;
                    break;

                case "Increase Age Stored Procedure":
                    new IncreaseAgeStoredProcedure(this.connection, this.bufferedReader).execute();
                    check = true;
                    break;
            }
            if (check) {
                dbManipulation.truncateData(connection);
                dbManipulation.insertData(connection);
            }

        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Invalid task name!");
        }
    }


}
