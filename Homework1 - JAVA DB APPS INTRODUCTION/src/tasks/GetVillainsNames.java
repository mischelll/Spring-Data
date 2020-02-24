package tasks;

import tasks.queries.Queries;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetVillainsNames extends CommandImpl {
    private static final int PARAMETER = 15;

    private ResultSet resultSet;

    public GetVillainsNames(Connection connection, BufferedReader bufferedReader) {
        super(connection, bufferedReader);
    }

    @Override
    public void execute() throws SQLException, IOException {
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(Queries.GET_VILLAIN_NAME);

        preparedStatement.setInt(1, PARAMETER);

        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.printf("%s %d"
                    , resultSet.getString("name")
                    , resultSet.getInt("count"))
                    .println();
        }
    }
}
