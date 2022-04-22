package connector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConfigLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector extends Connector{
    private static final Logger LOGGER = LoggerFactory.getLogger(MySQLConnector.class);

    private static Connection conn;

    private MySQLConnector() {
        super("mysql");
    }

    public static void init() throws SQLException {
        String ip = ConfigLoader.get("connectors.mysql.host", String.class);
        Integer port = ConfigLoader.get("connectors.mysql.port", Integer.class);
        String user = ConfigLoader.get("connectors.mysql.username", String.class);
        String password = ConfigLoader.get("connectors.mysql.password", String.class);
        String schema = ConfigLoader.get("connectors.mysql.database", String.class);

        String baseConnectionString = String.format("jdbc:mysql://%s:%d/%s?timezone=UTC&autoReconnect=true", ip, port, schema);

        conn = DriverManager.getConnection(baseConnectionString, user, password);
    }

    public void createTables(){

    }
}
