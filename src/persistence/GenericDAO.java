package persistence;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.mariadb.jdbc.Connection;
import org.mariadb.jdbc.Driver;

public class GenericDAO {
	
	public final static String URL = 
			"jdbc:mariadb://localhost:3306/pizzaria";
	public final static String USER = "root";
	public final static String PASS  = "123456";
	
	public static void main(String[] args) 
			throws ClassNotFoundException {
		Class.forName("org.mariadb.jdbc.Driver");
		System.out.println("Classe Carregada");
		
		try (java.sql.Connection con = 
				DriverManager.getConnection(URL, USER, PASS)){
			System.out.println("Conexão OK");
			String sql = "INSERT INTO bebida "
					+ "(id, nome, valor) "
					+ "VALUES (1, 'Coca-Cola', 3.50)";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
