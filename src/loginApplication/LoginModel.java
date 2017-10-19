package loginApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbUtils.DatabaseConnection;

public class LoginModel {
	Connection connection;
	
	public LoginModel(){
		
		try{
			this.connection = DatabaseConnection.getConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
		if(this.connection == null)
			System.exit(1);
	}
	
	public boolean isDatabaseConnected(){
		return this.connection != null;
	}
	public boolean isLogin(String username, String password, String option) throws Exception {
		
		PreparedStatement statement = null;
		ResultSet result = null;
		String sql = "SELECT * FROM login where username = ? and password = ? and divison = ?";
		
		try {
			statement = this.connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			statement.setString(3, option);
			
			result = statement.executeQuery();
			
			
			if(result.next()){
				return true;
			}
			return false;
		} catch (SQLException e) {
			return false;
			
		}

		finally {
			statement.close();
			result.close();
		}
	}

}
