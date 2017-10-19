package product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dbUtils.DatabaseConnection;

public class ProductModel {
	
Connection connection;
	
	public ProductModel(){
		
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
	
	public static boolean isExcisting(String productName, String lenght){
		PreparedStatement statement = null;
		ResultSet result = null;
		String sql = "SELECT * FROM login where username = ? and password = ? and divison = ?";
		return false;
	}

}
