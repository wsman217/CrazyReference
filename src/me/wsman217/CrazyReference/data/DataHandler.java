package me.wsman217.CrazyReference.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.wsman217.CrazyReference.CrazyReference;

public class DataHandler {

	private CrazyReference plugin;
	private DataBase db;

	public DataHandler(DataBase db) {
		this.plugin = CrazyReference.getInstance();
		this.db = db;
	}

	public void generateTables() {
		Connection connection = this.db.getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(
					"CREATE TABLE IF NOT EXISTS reference_leaderboard(player_name VARCHAR(40) PRIMARY KEY, total_referrals INTEGER)");
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertIntoTable(String p, int total) {
		Connection connection = this.db.getConnection();
		String statement = "INSERT INTO reference_leaderboard VALUES(?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(statement);
			ps.setString(1, p);
			ps.setInt(2, total);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteFromTable(String p, String lookFor) {
		Connection connection = this.db.getConnection();
		String statement = "DELETE FROM reference_leaderboard WHERE UPPER(" + p + ")=" + lookFor.toUpperCase();
		try {
			PreparedStatement ps = connection.prepareStatement(statement);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void changeTotal(String p, int newTotal) {
		Connection connection = this.db.getConnection();
		String originalPlayerName = "";

		String statement1 = "SELECT * FROM reference_leaderboard WHERE UPPER(player_name)=" + p.toUpperCase();
		String statement2 = "REPLACE INTO reference_leaderboard VALUES(?,?)";

		try {
			PreparedStatement ps1 = connection.prepareStatement(statement1);
			ResultSet rs1 = ps1.executeQuery();
			if (rs1.next()) {
				originalPlayerName = rs1.getString("player_name");
			}
			ps1.close();
			rs1.close();
			if (!originalPlayerName.equals("")) {
				PreparedStatement ps2 = connection.prepareStatement(statement2);
				ps2.setString(1, originalPlayerName);
				ps2.setInt(2, newTotal);
				ps2.execute();
				ps2.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*public void updateOnReferredJoin(String p) {
		Connection connection = this.db.getConnection();
		String originalPlayerName = "";
		int total = 0;

		String statement1 = "SELECT * FROM reference_leaderboard WHERE UPPER(player_name)=" + p.toUpperCase();
		String statement2 = "REPLACE INTO reference_leaderboard VALUES(?,?)";

		try {
			PreparedStatement ps1 = connection.prepareStatement(statement1);
			ResultSet rs1 = ps1.executeQuery();
			if (rs1.next()) {
				originalPlayerName = rs1.getString("player_name");
				total = rs1.getInt("total_referrals");
			}
			ps1.close();
			rs1.close();
			if (!originalPlayerName.equals("")) {
				PreparedStatement ps2 = connection.prepareStatement(statement2);
				ps2.setString(1, originalPlayerName);
				ps2.setInt(2, (total + 1));
				ps2.execute();
				ps2.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/
}
