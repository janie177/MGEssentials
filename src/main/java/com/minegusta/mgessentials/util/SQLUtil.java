package com.minegusta.mgessentials.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SQLUtil {

	private static final String driver = "com.mysql.jdbc.Driver";

	/**
	 * Create a database if it does not exist yet.
	 *
	 * @param user             The user to connect with.
	 * @param pass             The password to connect with.
	 * @param url              The url to connect to.
	 * @param dataBaseToCreate The database that is going to be created.
	 * @return true if no errors occured.
	 */
	public static boolean createDB(String user, String pass, String url, String dataBaseToCreate) {
		try {
			Class.forName(driver).newInstance();

			Connection conn = DriverManager.getConnection(url, user, pass);

			String sqlCreateDB = "CREATE DATABASE IF NOT EXISTS " + dataBaseToCreate;

			Statement stmt = conn.createStatement();
			stmt.execute(sqlCreateDB);
			conn.close();

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Create a table in a database if it does not exist already
	 *
	 * @param user     The username to connect with.
	 * @param pass     The password to connect with.
	 * @param url      The url to connect to. Example: "database-url", "jdbc:mysql://localhost:3306/"
	 * @param database The database name.
	 * @param table    The table name to create.
	 * @param columns  The columns to include in the created table. Example: (uuid VARCHAR(40),credits INTEGER, PRIMARY KEY(uuid))
	 * @return true if the table was created.
	 */
	public static boolean createTable(String user, String pass, String url, String database, String table, String columns) {
		String sqlCreate = "CREATE TABLE IF NOT EXISTS " + table + " " + columns;

		try {
			Class.forName(driver).newInstance();
			Connection conn = DriverManager.getConnection(url + database, user, pass);
			Statement stmt = conn.createStatement();
			stmt.execute(sqlCreate);
			conn.close();

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Open a database connection with the given database.
	 *
	 * @param url      The url of the mysql server.
	 * @param database The database name.
	 * @param user     The username.
	 * @param pass     The password.
	 * @return The connection to the database.
	 */
	public static Connection openDB(String url, String database, String user, String pass) {
		try {
			Class.forName(driver).newInstance();
			return DriverManager.getConnection(url + database, user, pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Close the database.
	 *
	 * @param conn The connection to close.
	 * @return True if the database was sucessfully closed.
	 */
	public static boolean closeDB(Connection conn) {
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
