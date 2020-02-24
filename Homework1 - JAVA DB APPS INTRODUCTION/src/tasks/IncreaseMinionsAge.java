package tasks;

import com.mysql.cj.protocol.Resultset;
import tasks.queries.Queries;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class IncreaseMinionsAge extends CommandImpl {

    public IncreaseMinionsAge(Connection connection, BufferedReader bufferedReader) {
        super(connection, bufferedReader);
    }

    @Override
    public void execute() throws SQLException, IOException {
        System.out.println("Enter minion IDs: ");
        int[] id = Arrays
                .stream(getBufferedReader().readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        boolean check = false;
        for (int i = 0; i < id.length; i++) {
            if (checkForMinionID(id[i])) {//look at what the method is doing, if you do not understand :)
                check = true;
            }
        }

        if (check) {
            getMinionsNameAge();
        }
    }

    private boolean checkForMinionID(int id) throws SQLException {
        String minionName = "";
        PreparedStatement getNameById = this.getConnection().prepareStatement(Queries.GET_MINION_NAME_BY_ID);
        getNameById.setInt(1, id);

        ResultSet resultSet = getNameById.executeQuery();

        if (!resultSet.next()) {
            System.out.printf("No minion found with ID %d!", id);
            return false;
        }
        System.out.printf("Minion found!%nConverting minion's name to lowercase...%nIncreasing minion's age by 1...%n");
        minionName = resultSet.getString("name");

        changeNameToLowerCaseAndIncreaseAge(minionName);

        return true;
    }

    private void changeNameToLowerCaseAndIncreaseAge(String minionName) throws SQLException {
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(Queries.SET_NAME_TO_LOWERCASE_INCREASE_AGE);
        preparedStatement.setString(1, minionName);

        int resultSet = preparedStatement.executeUpdate();
        System.out.printf("Converted minion's name successfully!%nIncreased minion's age successfully!%n");
    }

    private void getMinionsNameAge() throws SQLException {
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(Queries.SELECT_MINIONS_NAME_AGE);
        ResultSet resultSet = preparedStatement.executeQuery();

        System.out.printf("name | age%n");
        while (resultSet.next()) {
            System.out.printf("%s   %d%n"
                    , resultSet.getString("name")
                    , resultSet.getInt("age"));
        }
    }


}
