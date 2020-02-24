package tasks;

import tasks.queries.Queries;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddMinion extends CommandImpl {
    public AddMinion(Connection connection, BufferedReader bufferedReader) {
        super(connection, bufferedReader);
    }

    @Override
    public void execute() throws SQLException, IOException {
        System.out.println("Enter minion credentials:");
        String[] inputDataMinion = getBufferedReader().readLine().split("\\s+");
        System.out.println("Enter villain name:");
        String[] inputDataVillain = getBufferedReader().readLine().split("\\s+");

        String minionName = inputDataMinion[1];
        int minionAge = Integer.parseInt(inputDataMinion[2]);
        String minionTown = inputDataMinion[3];

        String villainName = inputDataVillain[1];


        if (!checkMinionTown(minionTown)) {
            System.out.println(addMinionTown(minionTown));
        }

        if (!checkVillain(villainName)) {
            System.out.println(addVillain(villainName));
        }

        addMinion(minionName, minionAge, minionTown);

        System.out.println(addMinionToVillain(minionName, villainName));
    }

    private void addMinion(String minionName, int minionAge, String minionTown) throws SQLException {
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(Queries.INSERT_MINION);
        preparedStatement.setString(1, minionName);
        preparedStatement.setInt(2, minionAge);
        preparedStatement.setString(3, minionTown);

        preparedStatement.executeUpdate();
    }

    private String addMinionToVillain(String minionName, String villainName) throws SQLException {
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(Queries.INSERT_MINION_TO_VILLAIN);
        preparedStatement.setString(1, minionName);
        preparedStatement.setString(2, villainName);

        preparedStatement.executeUpdate();

        return String.format("Successfully added %s to be minion of %s.", minionName, villainName);

    }

    private String addMinionTown(String minionTown) throws SQLException {
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(Queries.INSERT_MINION_TOWN);
        preparedStatement.setString(1, minionTown);

        int resultSet = preparedStatement.executeUpdate();

        return String.format("Town %s was added to the database.", minionTown);

    }

    private boolean checkVillain(String villainName) throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement(Queries.CHECK_VILLAIN);
        preparedStatement.setString(1, villainName);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    private String addVillain(String villainName) throws SQLException {
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(Queries.INSERT_VILLAIN);
        preparedStatement.setString(1, villainName);

        int resultSet = preparedStatement.executeUpdate();
        return String.format("Villain %s was added to the database.", villainName);
    }

    private boolean checkMinionTown(String minionTown) throws SQLException {
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(Queries.CHECK_MINION_TOWN);
        preparedStatement.setString(1, minionTown);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
}
