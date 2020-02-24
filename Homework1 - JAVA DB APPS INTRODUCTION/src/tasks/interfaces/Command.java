package tasks.interfaces;

import java.io.IOException;
import java.sql.SQLException;

public interface Command {
    void execute() throws SQLException, IOException;
}
