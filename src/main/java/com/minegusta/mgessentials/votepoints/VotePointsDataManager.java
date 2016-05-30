package com.minegusta.mgessentials.votepoints;

import com.google.common.collect.Lists;
import com.minegusta.mgessentials.Main;
import com.minegusta.mgessentials.util.SQLUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

public class VotePointsDataManager {
    static File file;
    static FileConfiguration conf;

    private static final String database = Main.PLUGIN.getConfig().getString("database-name", "minegusta");
    private static final String user = Main.PLUGIN.getConfig().getString("user", "root");
    private static final String pass = Main.PLUGIN.getConfig().getString("password", "");
    private static final String url = Main.PLUGIN.getConfig().getString("database-url", "jdbc:mysql://localhost:3306/");
    private static final String table = "votepoints";

    private static boolean useSQL = false;

    public static boolean initSQL() {
        String tableColumns = "(uuid VARCHAR(40),votepoints INTEGER,total INTEGER, PRIMARY KEY(uuid))";
        boolean returned = SQLUtil.createDB(user, pass, url, database) && SQLUtil.createTable(user, pass, url, database, table, tableColumns);
        useSQL = returned;
        return returned;
    }

    public static void createOrLoadPointsFile(Plugin p) {
        try {

            file = new File(p.getDataFolder(), "votepoints.yml");

            if (!file.exists()) {
                p.saveResource("votepoints.yml", false);
                Bukkit.getLogger().info("Successfully created " + file.getName() + ".");
            }
            conf = YamlConfiguration.loadConfiguration(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            conf.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getPlayerVotes(UUID p) {
        if (useSQL) {
            int votepoints = 0;
            Connection conn = SQLUtil.openDB(url, database, user, pass);
            if (conn != null) {
                try {
                    String sqlGetCredits = "SELECT * FROM " + table + " WHERE uuid='" + p.toString() + "'";
                    Statement statement = conn.createStatement();
                    ResultSet set = statement.executeQuery(sqlGetCredits);
                    while (set.next()) {
                        votepoints = set.getInt("votepoints");
                    }

                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return votepoints;
        }
        return conf.getInt(p.toString() + ".unclaimed", 0);
    }

    public static int getTotalVotes(String uuid) {
        if (useSQL) {
            int votepoints = 0;
            Connection conn = SQLUtil.openDB(url, database, user, pass);
            if (conn != null) {
                try {
                    String sqlGetCredits = "SELECT * FROM " + table + " WHERE uuid='" + uuid + "'";
                    Statement statement = conn.createStatement();
                    ResultSet set = statement.executeQuery(sqlGetCredits);
                    while (set.next()) {
                        votepoints = set.getInt("total");
                    }

                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return votepoints;
        }
        return conf.getInt(uuid + ".total", 0);
    }

    public static List<String> getMostVotes() {
        if (useSQL) {
            List<String> users = Lists.newArrayList();
            Connection conn = SQLUtil.openDB(url, database, user, pass);
            if (conn != null) {
                try {
                    String sqlGetCredits = "SELECT * FROM " + table + " ORDER BY total DESC LIMIT 5";
                    Statement statement = conn.createStatement();
                    ResultSet set = statement.executeQuery(sqlGetCredits);
                    while (set.next()) {
                        users.add(set.getString("uuid"));
                    }
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return users;
        }
        List<String> users = Lists.newArrayList();
        int highest = 0;

        for (String s : conf.getKeys(false)) {
            int gotten = getTotalVotes(s);
            if (gotten > highest) highest = gotten;
        }

        for (String s : conf.getKeys(false)) {
            if (getTotalVotes(s) >= highest) {
                users.add(s);
            }
        }

        return users;
    }

    public static List<String> getMoreThan(int amount) {
        if (useSQL) {
            List<String> users = Lists.newArrayList();
            Connection conn = SQLUtil.openDB(url, database, user, pass);
            if (conn != null) {
                try {
                    String sqlGetCredits = "SELECT * FROM " + table + " WHERE total>" + amount;
                    Statement statement = conn.createStatement();
                    ResultSet set = statement.executeQuery(sqlGetCredits);
                    while (set.next()) {
                        users.add(set.getString("uuid"));
                    }
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return users;
        }
        List<String> users = Lists.newArrayList();

        for (String s : conf.getKeys(false)) {
            if (getTotalVotes(s) >= amount) {
                users.add(s);
            }
        }
        return users;
    }


    public static void addVote(UUID p) {
        if (useSQL) {
            int newTotal = getPlayerVotes(p) + 1;
            Connection conn = SQLUtil.openDB(url, database, user, pass);
            if (conn != null) {
                try {
                    String sqlSetCredits = "REPLACE INTO " + table + " (uuid, credits)" +
                            "VALUES ('" + p.toString() + "', '" + newTotal + "')";

                    Statement statement = conn.createStatement();
                    statement.execute(sqlSetCredits);

                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            conf.set(p.toString() + ".unclaimed", getPlayerVotes(p) + 1);
            conf.set(p.toString() + ".total", getTotalVotes(p.toString()) + 1);
        }
    }

    public static void resetvotes(UUID p) {
        if (useSQL) {
            Connection conn = SQLUtil.openDB(url, database, user, pass);
            if (conn != null) {
                try {
                    String sqlSetCredits = "REPLACE INTO " + table + " (uuid, credits, total)" +
                            "VALUES ('" + p.toString() + "', '0', '0')";

                    Statement statement = conn.createStatement();
                    statement.execute(sqlSetCredits);

                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else if (conf.isSet(p.toString())) {
            conf.set(p.toString() + ".unclaimed", 0);
            conf.set(p.toString() + ".total", 0);
        }

    }

    public static void removeUnclaimedVote(UUID p) {
        if (useSQL) {
            int newTotal = getPlayerVotes(p) - 1;
            Connection conn = SQLUtil.openDB(url, database, user, pass);
            if (conn != null) {
                try {
                    String sqlSetCredits = "REPLACE INTO " + table + " (uuid, credits)" +
                            "VALUES ('" + p.toString() + "', '" + newTotal + "')";

                    Statement statement = conn.createStatement();
                    statement.execute(sqlSetCredits);

                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            conf.set(p.toString() + ".unclaimed", getPlayerVotes(p) - 1);
        }
    }
}
