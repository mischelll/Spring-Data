package tasks;

import tasks.queries.Queries;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GetMinionsNames extends CommandImpl {


    public GetMinionsNames(Connection connection, BufferedReader bufferedReader) {
        super(connection, bufferedReader);
    }

    @Override
    public void execute() throws SQLException, IOException {
        System.out.println("Enter villain id:");
        int villainId = Integer.parseInt(getBufferedReader().readLine());

        getVillainNameById(villainId);

        PreparedStatement preparedStatement = this.getConnection()
                .prepareStatement(Queries.GET_MINION_NAME_AGE_BY_VILLAIN_ID);
        preparedStatement.setInt(1, villainId);

        ResultSet resultSet = preparedStatement.executeQuery();
        int minionIndex = 0;


        while (resultSet.next()) {
            System.out.printf("%d. %s %d%n"
                    , ++minionIndex
                    , resultSet.getString("name")
                    , resultSet.getInt("age"));
        }

    }

    private void getVillainNameById(int villainId) throws SQLException {
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(Queries.GET_VILLAIN_NAME_BY_ID);
        preparedStatement.setInt(1, villainId);
        ResultSet resultSet = preparedStatement.executeQuery();


        if (!resultSet.next()) {
            System.out.printf("No villain with ID %s exists in the database.%n", villainId);
            return;
        }
        System.out.printf("Villain: %s%n", resultSet.getString("name"));


    }
}
