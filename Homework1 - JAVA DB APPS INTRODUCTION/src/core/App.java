package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class App {
    public static void main(String[] args) throws IOException, SQLException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        Properties properties = new Properties();

        //System.out.println("Enter username (root):");
        //String user = bf.readLine().trim();
        // System.out.println("Enter password (root123):");
        //String password = bf.readLine().trim();

        properties.setProperty("user", "root"); // change credentials here
        properties.setProperty("password", "root123");// and here

        Connection connection =
                DriverManager.getConnection(MessagesAndConfiguration.CONNECTION_URL, properties);
        //!!!Please check the MessagesAndConfiguration class!!!


        //INTRO MESSAGE
        MessagesAndConfiguration.showIntroMessage();

        Engine engine = new Engine(connection, bf);
        engine.run();
        System.out.println("------------------");

        UI.goON(bf, engine);


    }
}
