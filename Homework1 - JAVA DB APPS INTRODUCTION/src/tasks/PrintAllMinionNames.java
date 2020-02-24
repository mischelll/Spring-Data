package tasks;

import tasks.queries.Queries;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrintAllMinionNames extends CommandImpl {

    public PrintAllMinionNames(Connection connection, BufferedReader bufferedReader) {
        super(connection, bufferedReader);
    }

    @Override
    public void execute() throws SQLException, IOException {
        PreparedStatement preparedStatement1 = this
                .getConnection()
                .prepareStatement(Queries.SELECT_ALL_NAMES_FROM_MINIONS);

        PreparedStatement preparedStatement2 = this
                .getConnection()
                .prepareStatement(Queries.GET_COUNT_OF_MINIONS);

        ResultSet allNames = preparedStatement1.executeQuery();
        ResultSet count = preparedStatement2.executeQuery();

        count.next();
        int allMinionsCount = count.getInt("count");
        int iteration = 0;

        while (iteration != allMinionsCount / 2){
            allNames.first();
            for (int i = 0; i < iteration; i++) {
                allNames.next();
            }
            System.out.printf("%s%n",allNames.getString("name"));
            
            allNames.last();
            for (int i = 0; i < iteration; i++) {
                allNames.previous();
            }
            System.out.printf("%s%n",allNames.getString("name"));

            iteration++;
        }
    }
}
