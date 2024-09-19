package ru.nwtls.megapaybot;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class MainDatabase {
    private final @NotNull Logger logger = LoggerFactory.getLogger(MainDatabase.class);

    private final @NotNull String url;
    private final @NotNull String login;
    private final @NotNull String password;

    public MainDatabase(@Nullable String url, @Nullable String login, @Nullable String password) throws MainDatabaseException {
        if (url == null || login == null || password == null) {
            throw new MainDatabaseException("Provided connection configuration to database is incorrect, startup aborted");
        }
        this.url = url;
        this.login = login;
        this.password = password;
    }

    public static final class MainDatabaseException extends Exception {
        public MainDatabaseException(@NotNull String message) {
            super(message);
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(this.url, this.login, this.password);
        } catch (SQLException e) {
            this.logger.atError().log("Failed to connect to database, connection aborted, exception: " + e.getMessage());
        }
        return connection;
    }

    public void init() {
        HashMap<String, String> tables = this.getStringHashMap();

        List<String> tablesName = List.of("players", "goodreps", "badreps");

        for (String name : tablesName) {
            try (Connection connection = this.getConnection()) {
                String query = tables.get(name);
                PreparedStatement statement = connection.prepareStatement(query);
                statement.executeUpdate();
                if (statement.getWarnings() == null) {
                    this.logger.atInfo().log("Table '" + name + "' created successfully");
                }
            } catch (SQLException e) {
                this.logger.atError().log("Failed to create the table '" + name + "' , connection aborted, exception: " + e.getMessage());
            }
        }
        this.logger.info("Connection to database established");
    }

    private @NotNull HashMap<String, String> getStringHashMap() {
        HashMap<String, String> tables = new HashMap<>();

        tables.put("players", "CREATE TABLE IF NOT EXISTS players ("
                + "uuid VARCHAR(36) NOT NULL PRIMARY KEY,"
                + "goodrep TINYINT UNSIGNED NOT NULL,"
                + "badrep TINYINT UNSIGNED NOT NULL"
                + ");");
        tables.put("goodreps", "CREATE TABLE IF NOT EXISTS goodreps ("
                + "target VARCHAR(36) NOT NULL,"
                + "sender VARCHAR(36) NOT NULL"
                + ");");
        tables.put("badreps", "CREATE TABLE IF NOT EXISTS badreps ("
                + "target VARCHAR(36) NOT NULL,"
                + "sender VARCHAR(36) NOT NULL"
                + ");");

        return tables;
    }


}
