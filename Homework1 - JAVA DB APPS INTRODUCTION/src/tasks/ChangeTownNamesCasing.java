package tasks;

import tasks.queries.Queries;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChangeTownNamesCasing extends CommandImpl {
    private final int EXECUTE_UPDATE_ZERO = 0; 

    public ChangeTownNamesCasing(Connection connection, BufferedReader bufferedReader) {
        super(connection, bufferedReader);
    }

    @Override
    public void execute() throws SQLException, IOException {
        System.out.println("Enter country: ");
        String countryName = getBufferedReader().readLine();

        setNamesToUpper(countryName);
    }


    private void setNamesToUpper(String countryName) throws SQLException {
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(Queries.SET_TOWN_NAME_TO_UPPERCASE);
        preparedStatement.setString(1, countryName);

        int result = preparedStatement.executeUpdate();

        if (result == EXECUTE_UPDATE_ZERO) {
            System.out.println("No town names were affected.");
            return;
        }

        printUpdatedTowns(countryName);

    }
/*
    private boolean checkName(String countryName) throws SQLException {
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(Queries.CHECK_FOR_COUNTRY);
        preparedStatement.setString(1, countryName);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {

            return false;
        }
        return true;
    }
*/

    private void printUpdatedTowns(String countryName) throws SQLException {
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(Queries.CHECK_FOR_COUNTRY);
        preparedStatement.setString(1, countryName);

        List<String> towns = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            towns.add(resultSet.getString("name"));
        }

        System.out.println(String.format("%d towns were affected.%n[%s]", towns.size(), String.join(", ", towns)));
    }

}
