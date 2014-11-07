package red.dragon.controllers.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.ui.Image;

public abstract class AbstractApi {

	private static String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static String DB_HOST = "jdbc:mysql://db.luivillage.com:3306/reddragon";
	//private static String DB_HOST = "jdbc:mysql://localhost:3306/reddragon";
	private static String DB_USER = "root";
	private static String DB_PASSWORD = "webercs3750";
	private Connection conn;

	public AbstractApi() {
		try {
			Class.forName(DB_DRIVER).newInstance();
			openConn();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void openConn() {
		try {
			conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet query(String selectString) {
		Statement statement;
		ResultSet result = null;
		try {
			if (conn.isClosed())
				openConn();
			statement = conn.createStatement();
			if (statement.execute(selectString))
				result = statement.getResultSet();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void update(String insertString) {

		Statement statement;
		try {
			if (conn.isClosed())
				openConn();
			statement = conn.createStatement();
			statement.executeUpdate(insertString);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
