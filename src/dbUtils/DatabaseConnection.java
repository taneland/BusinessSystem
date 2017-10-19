package dbUtils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
	
private static final String SQCONN = "jdbc:sqlite:ProduktManager.sqlite";
	 
	
	public static Connection getConnection() throws Exception{
		
		try{
			Class.forName("org.sqlite.JDBC");
			return DriverManager.getConnection(SQCONN);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return null;
	}

}
