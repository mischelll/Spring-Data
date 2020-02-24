package tasks;

import tasks.queries.Queries;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RemoveVillain extends CommandImpl {
    private static final int DEFAULT_PARAMETER_ZERO = 0;

    public RemoveVillain(Connection connection, BufferedReader bufferedReader) {
        super(connection, bufferedReader);
    }

    @Override
    public void execute() throws SQLException, IOException {
        System.out.println("Enter villain ID: ");
        int villainId = Integer.parseInt(getBufferedReader().readLine());
        String villainName = getVillainName(villainId);

        if (villainName == null || villainName.equals("")) {
            System.out.println("No such villain was found");
        } else {
            System.out.printf("%s was deleted%n", villainName);
            releaseMinions(villainId);
        }

    }

    private void releaseMinions(int villainId) throws SQLException {
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(Queries.RELEASE_MINIONS);
        preparedStatement.setInt(1, villainId);

        int result = preparedStatement.executeUpdate();

        deleteVillain(villainId, result);

    }

    private void deleteVillain(int villainId, int queryUpdateResult) throws SQLException {
        PreparedStatement deleteFromMinionsVillains = this.getConnection().prepareStatement(Queries.DELETE_VILLAIN_FROM_MINIONS_VILLAINS);
        deleteFromMinionsVillains.setInt(1, villainId);
        deleteFromMinionsVillains.executeUpdate();


        PreparedStatement deleteFromVillains = this.getConnection().prepareStatement(Queries.DELETE_VILLAIN_FROM_VILLAINS);
        deleteFromVillains.setInt(1, villainId);

        if (deleteFromVillains.executeUpdate() != DEFAULT_PARAMETER_ZERO) {
            System.out.printf("%d minions released%n", queryUpdateResult);
        }

    }

    private String getVillainName(int villainId) throws SQLException {
        PreparedStatement preparedStatement = this.getConnection().prepareStatement(Queries.GET_VILLAIN_NAME_BY_ID);
        preparedStatement.setInt(1, villainId);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            return null;
        }
        return resultSet.getString("name");
    }


}
