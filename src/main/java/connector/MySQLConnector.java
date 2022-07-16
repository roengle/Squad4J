package connector;

import a2s.Query;
import a2s.response.A2SInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import plugins.DBLog;
import server.LayerClassnameFormatter;
import server.SquadServer;
import util.ConfigLoader;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Robert Engle
 */
public class MySQLConnector extends Connector{
    private static final Logger LOGGER = LoggerFactory.getLogger(MySQLConnector.class);

    private static Connection conn;
    private static Statement statement;

    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static final Integer serverID = ConfigLoader.get("server.id", Integer.class);

    private MySQLConnector() {
        super("mysql");
    }

    public static void init(){
        String ip = ConfigLoader.get("connectors.mysql.host", String.class);
        Integer port = ConfigLoader.get("connectors.mysql.port", Integer.class);
        String user = ConfigLoader.get("connectors.mysql.username", String.class);
        String password = ConfigLoader.get("connectors.mysql.password", String.class);
        String schema = ConfigLoader.get("connectors.mysql.database", String.class);

        String baseConnectionString = String.format("jdbc:mysql://%s:%d/%s?timezone=UTC&autoReconnect=true", ip, port, schema);

        try {
            LOGGER.debug("Attempting to connect to MySQL server.");
            conn = DriverManager.getConnection(baseConnectionString, user, password);
            LOGGER.debug("Connected to MySQL server.");
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if(!tablesExist()){
                LOGGER.trace("At least one table does not exist, creating tables");
                createTables();
            }
            if(!serverExists()){
                LOGGER.debug("Server {} does not exist in DBLog_Servers, adding", serverID);
                createServer();
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception.", e);
        }
    }

    private static boolean serverExists() {
        try{
            String serverName = Query.queryInfo().getName();
            ResultSet rs = statement.executeQuery("SELECT `id`, `server` FROM DBLog_Servers WHERE id = " + serverID + ";");
            if(rs.first()){
                LOGGER.trace("Server with id {} already exists.", serverID);
                if(!rs.getString("server").equals(serverName)){
                    //Server exists but with a different name, update name
                    updateServerName(serverName);
                }
                return true;
            }
            rs.close();
        }catch (SQLException e){
            LOGGER.error("SQL Error.", e);
        }
        return false;
    }

    private static void updateServerName(String newServerName) {
        try {
            LOGGER.info("Server in database with id {} has a different name than the current name of the server. Updating name to {}", serverID, newServerName);
            statement.executeUpdate("UPDATE DBLog_Servers " +
                    "SET `server` = \'" + newServerName + "\'" +
                    "WHERE `id` = " + MySQLConnector.serverID + ";");
        } catch (SQLException e) {
            LOGGER.error("Error updating server name.", e);
        }
    }

    private static boolean tablesExist(){
        //Dont query information schema, just try to select from tables and if error, then they dont exist
        try {
            statement.executeQuery("SELECT COUNT(*) FROM DBLog_Servers");
            statement.executeQuery("SELECT COUNT(*) FROM DBLog_Matches");
            statement.executeQuery("SELECT COUNT(*) FROM DBLog_Tickrates");
            statement.executeQuery("SELECT COUNT(*) FROM DBLog_PlayerCounts");
            statement.executeQuery("SELECT COUNT(*) FROM DBLog_SteamUsers");
            statement.executeQuery("SELECT COUNT(*) FROM DBLog_Wounds");
            statement.executeQuery("SELECT COUNT(*) FROM DBLog_Deaths");
            statement.executeQuery("SELECT COUNT(*) FROM DBLog_Revives");
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    private static void createTables() throws SQLException {
        LOGGER.debug("Creating DBLog_Servers");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS DBLog_Servers (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "server VARCHAR(255)" +
                ") CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;");

        LOGGER.debug("Creating DBLog_Matches");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS DBLog_Matches (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "dlc VARCHAR(255)," +
                "mapClassname VARCHAR(255)," +
                "layerClassname VARCHAR(255)," +
                "map VARCHAR(255) NOT NULL," +
                "layer VARCHAR(255) NOT NULL," +
                "startTime DATETIME," +
                "endTime DATETIME," +
                "winner VARCHAR(255)," +
                "server INT NOT NULL," +
                "KEY `server` (`server`)," +
                "CONSTRAINT `squad4j_dblog_matches_fk_server` FOREIGN KEY (`server`) REFERENCES `DBLog_Servers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE" +
                ") CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");

        LOGGER.debug("Creating DBLog_Tickrates");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS DBLog_Tickrates (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "time DATETIME," +
                "tickRate DOUBLE NOT NULL," +
                "server INT NOT NULL," +
                "`match` INT DEFAULT NULL,\n" +
                "KEY `server` (`server`),\n" +
                "KEY `match` (`match`),\n" +
                "CONSTRAINT `squad4j_dblog_tickrates_fk_server` FOREIGN KEY (`server`) REFERENCES `DBLog_Servers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "CONSTRAINT `squad4j_dblog_tickrates_fk_match` FOREIGN KEY (`match`) REFERENCES `DBLog_Matches` (`id`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ") CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");

        LOGGER.debug("Creating DBLog_PlayerCounts");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS DBLog_PlayerCounts (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "time DATETIME NOT NULL," +
                "players INT NOT NULL DEFAULT 0," +
                "publicQueue INT NOT NULL DEFAULT 0," +
                "reserveQueue INT NOT NULL DEFAULT 0," +
                "server INT NOT NULL," +
                "`match` INT DEFAULT NULL," +
                "KEY `server` (`server`)," +
                "KEY `match` (`match`)," +
                "CONSTRAINT `squad4j_dblog_playercounts_fk_server` FOREIGN KEY (`server`) REFERENCES `DBLog_Servers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE," +
                "CONSTRAINT `squad4j_dblog_playercoutns_fk_match` FOREIGN KEY (`match`) REFERENCES `DBLog_Matches` (`id`) ON DELETE CASCADE ON UPDATE CASCADE" +
                ") CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");

        LOGGER.debug("Creating DBLog_SteamUsers");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS DBLog_SteamUsers(" +
                "steamID VARCHAR(255) PRIMARY KEY," +
                "lastName VARCHAR(255)" +
                ") CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");

        LOGGER.debug("Creating DBLog_Wounds");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS DBLog_Wounds(" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "`time` DATETIME NOT NULL," +
                "victimName VARCHAR(255)," +
                "victimTeamID INT," +
                "victimSquadID INT," +
                "attackerName VARCHAR(255)," +
                "attackerTeamID INT," +
                "attackerSquadID INT," +
                "damage DOUBLE," +
                "teamkill BOOLEAN," +
                "server INT NOT NULL," +
                "attacker VARCHAR(255) DEFAULT NULL," +
                "victim VARCHAR(255) DEFAULT NULL," +
                "`match` INT DEFAULT NULL," +
                "KEY `server` (`server`)," +
                "KEY `attacker` (`attacker`)," +
                "KEY `victim` (`victim`)," +
                "KEY `match` (`match`)," +
                "CONSTRAINT `squad4j_dblog_wounds_fk_server` FOREIGN KEY (`server`) REFERENCES `DBLog_Servers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE," +
                "CONSTRAINT `squad4j_dblog_wounds_fk_attacker` FOREIGN KEY (`attacker`) REFERENCES `DBLog_SteamUsers` (`steamID`) ON DELETE CASCADE ON UPDATE CASCADE," +
                "CONSTRAINT `squad4j_dblog_wounds_fk_victim` FOREIGN KEY (`victim`) REFERENCES `DBLog_SteamUsers` (`steamID`) ON DELETE CASCADE ON UPDATE CASCADE," +
                "CONSTRAINT `squad4j_dblog_wounds_fk_match` FOREIGN KEY (`match`) REFERENCES `DBLog_Matches` (`id`) ON DELETE CASCADE ON UPDATE CASCADE" +
                ") CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");

        LOGGER.debug("Creating DBLog_Deaths");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS DBLog_Deaths(" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "`time` DATETIME NOT NULL," +
                "woundTime DATE," +
                "victimName VARCHAR(255)," +
                "victimTeamID INT," +
                "victimSquadID INT," +
                "attackerName VARCHAR(255)," +
                "attackerTeamID INT," +
                "attackerSquadID INT," +
                "damage DOUBLE," +
                "weapon VARCHAR(255)," +
                "teamkill BOOLEAN," +
                "server INT NOT NULL," +
                "attacker VARCHAR(255) DEFAULT NULL," +
                "victim VARCHAR(255) DEFAULT NULL," +
                "`match` INT DEFAULT NULL," +
                "KEY `server` (`server`)," +
                "KEY `attacker` (`attacker`)," +
                "KEY `victim` (`victim`)," +
                "KEY `match` (`match`)," +
                "CONSTRAINT `squad4j_dblog_deaths_fk_server` FOREIGN KEY (`server`) REFERENCES `DBLog_Servers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE," +
                "CONSTRAINT `squad4j_dblog_deaths_fk_attacker` FOREIGN KEY (`attacker`) REFERENCES `DBLog_SteamUsers` (`steamID`) ON DELETE CASCADE ON UPDATE CASCADE," +
                "CONSTRAINT `squad4j_dblog_deaths_fk_victim` FOREIGN KEY (`victim`) REFERENCES `DBLog_SteamUsers` (`steamID`) ON DELETE CASCADE ON UPDATE CASCADE," +
                "CONSTRAINT `squad4j_dblog_deaths_fk_match` FOREIGN KEY (`match`) REFERENCES `DBLog_Matches` (`id`) ON DELETE CASCADE ON UPDATE CASCADE" +
                ") CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");

        LOGGER.debug("Creating DBLog_Revives");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS DBLog_Revives(" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "`time` DATETIME NOT NULL," +
                "woundTime DATE," +
                "victimName VARCHAR(255)," +
                "victimTeamID INT," +
                "victimSquadID INT," +
                "attackerName VARCHAR(255)," +
                "attackerTeamID INT," +
                "attackerSquadID INT," +
                "damage DOUBLE," +
                "weapon VARCHAR(255)," +
                "teamkill BOOLEAN," +
                "reviverName VARCHAR(255)," +
                "reviverTeamID INT," +
                "reviverSQuadID INT," +
                "server INT NOT NULL," +
                "attacker varchar(255) DEFAULT NULL," +
                "victim varchar(255) DEFAULT NULL," +
                "reviver varchar(255) DEFAULT NULL," +
                "`match` int DEFAULT NULL," +
                "KEY `server` (`server`)," +
                "KEY `attacker` (`attacker`)," +
                "KEY `victim` (`victim`)," +
                "KEY `reviver` (`reviver`)," +
                "KEY `match` (`match`)," +
                "CONSTRAINT `squad4j_dblog_revives_fk_server` FOREIGN KEY (`server`) REFERENCES `DBLog_Servers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE," +
                "CONSTRAINT `squad4j_dblog_revives_fk_attacker` FOREIGN KEY (`attacker`) REFERENCES `DBLog_SteamUsers` (`steamID`) ON DELETE CASCADE ON UPDATE CASCADE," +
                "CONSTRAINT `squad4j_dblog_revives_fk_victim` FOREIGN KEY (`victim`) REFERENCES `DBLog_SteamUsers` (`steamID`) ON DELETE CASCADE ON UPDATE CASCADE," +
                "CONSTRAINT `squad4j_dblog_revives_fk_reviver` FOREIGN KEY (`reviver`) REFERENCES `DBLog_SteamUsers` (`steamID`) ON DELETE CASCADE ON UPDATE CASCADE," +
                "CONSTRAINT `squad4j_dblog_revives_fk_match` FOREIGN KEY (`match`) REFERENCES `DBLog_Matches` (`id`) ON DELETE CASCADE ON UPDATE CASCADE" +
                ") CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");

        LOGGER.debug("Done creating tables");
    }

    private static void createServer() {
        //TODO: If query times out, info is null. Fix
        A2SInfoResponse info = Query.queryInfo();
        String serverName = info.getName();
        try {
            String query = "INSERT INTO DBLog_Servers (id, server) VALUES(" +
                    serverID + ", " +
                    "'" + serverName + "'" +
                    ");";
            LOGGER.trace(query);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            LOGGER.error("SQL exception while creating server.", e);
        }
    }

    public static Integer getCurrentMatchId(){
        try {
            String query = String.format("SELECT `id`, `layer` FROM DBLog_Matches WHERE `server` = %s AND `endTime` IS NULL ORDER BY `id` DESC LIMIT 1;", serverID);
            if(statement == null){
                LOGGER.warn("Cannot get current match ID yet because MySQLConnector hasn't been initialized yet.");
                return -1;
            }
            ResultSet rs = statement.executeQuery(query);

            String currentMap = SquadServer.getCurrentMap();
            String currentLayer = SquadServer.getCurrentLayer();

            if(rs.first() && currentLayer != null && currentLayer.equals(rs.getString("layer"))){
                int matchId = rs.getInt("id");
                rs.close();
                //Current layer matches most recent match in DB, go ahead and return it.
                return matchId;
            }
            rs.close();
            LOGGER.debug("Most recent match in DB does not match current or does not exist, creating new match.");
            //Insert new match since current match is not being tracked
            //TODO: Fill dlc and classname values properly
            insertMatch("",
                    LayerClassnameFormatter.formatMap(currentMap),
                    LayerClassnameFormatter.formatLayer(currentLayer),
                    currentMap,
                    currentLayer,
                    null);
            //Recalling this method will get the newly-made match id
            return getCurrentMatchId();
        } catch (SQLException e) {
            LOGGER.error("SQL exception while getting current match.", e);
            return null;
        }
    }

    public static void insertPlayerCount(Date time, Integer players, Integer publicQueue, Integer reserveQueue, Integer match){
        try {
            String query = "INSERT INTO DBLog_PlayerCounts (`time`, `players`, `publicQueue`, `reserveQueue`, `server`, `match`) VALUES(" +
                    "\'" + dateTimeFormat.format(time) + "\'," +
                    players + "," +
                    publicQueue + "," +
                    reserveQueue + "," +
                    serverID + "," +
                    (match == null ? "NULL" : match) +
                    ");";
            LOGGER.trace(query);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            LOGGER.error("SQL exception inserting player count.", e);
        }
    }

    public static void insertMatch(String dlc, String mapClassname, String layerClassname, String map, String layer, Date startTime){
        try{
            String query = "INSERT INTO DBLog_Matches (dlc, mapClassname, layerClassname, map, layer, startTime, endTime, winner, `server`) VALUES (" +
                    "\'" + dlc + "\', " +
                    "\'" + mapClassname + "\', " +
                    "\'" + layerClassname + "\', " +
                    "\'" + map + "\', +" +
                    "\'" + layer + "\', " +
                    (startTime == null ? "NULL" : dateTimeFormat.format(startTime)) + ", " +
                    "NULL, " +
                    "NULL, " +
                    serverID +
                    ");";
            statement.executeUpdate(query);
        }catch (SQLException e){
            LOGGER.error("SQL exception inserting match.", e);
        }
    }

    public static void updateMatch(Date time, String winningFaction){

    }

    public static void insertTickRate(Date time, Double tickRate, Integer match){
        try{
            String query = "INSERT INTO DBLog_Tickrates (`time`, `tickRate`, `server`, `match`) VALUES" +
                    "(" +
                    "\'" + dateTimeFormat.format(time) + "\'" + ", " +
                    tickRate + ", " +
                    serverID + ", " +
                    match +
                    ");";
            statement.executeUpdate(query);
        }catch (SQLException e){
            LOGGER.error("Error inserting tick rate.", e);
        }
    }
}
