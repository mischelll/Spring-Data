package tasks;

import tasks.queries.Queries;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

public class IncreaseAgeStoredProcedure extends CommandImpl {

    public IncreaseAgeStoredProcedure(Connection connection, BufferedReader bufferedReader) {
        super(connection, bufferedReader);
    }

    @Override
    public void execute() throws SQLException, IOException {
        System.out.println("Enter minion id: ");
        int minion_id = Integer.parseInt(getBufferedReader().readLine());

        CallableStatement callableStatement = getConnection().prepareCall(Queries.CALL_PROCEDURE);
        callableStatement.setInt(1, minion_id);

        callableStatement.execute();

        PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.SELECT_NAME_AGE);
        preparedStatement.setInt(1, minion_id);

        ResultSet set = preparedStatement.executeQuery();

        while (set.next()){
            System.out.printf("Minion name: %s%nMinion age: %d%n", set.getString("name"),set.getInt("age"));
        }

    }
}
