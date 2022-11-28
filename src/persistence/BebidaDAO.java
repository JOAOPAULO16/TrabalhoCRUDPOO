package persistence;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mariadb.jdbc.Connection;

import model.Bebida;

public class BebidaDAO implements IBebidaDAO {
	
	public final static String URL = 
			"jdbc:mariadb://localhost:3306/pizzaria";
	public final static String USER = "root";
	public final static String PASS  = "123456";
	private java.sql.Connection con;

	public BebidaDAO() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			con = DriverManager.getConnection(URL, USER, PASS);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}	
	}
	
	@Override
	public void Criar(Bebida b) {
		String sql = "INSERT INTO bebida "
				+ "(id, nome, valor) "
				+ "VALUES (" + b.getId() + ", '" +b.getNome() + "', " 
				+ b.getValor() + ")";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Bebida> pesquisarPorNome(String nome) {
		List<Bebida> lista = new ArrayList<>();
		String sql = "SELECT * FROM bebida WHERE nome LIKE '%" + nome + "%'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Bebida b = new Bebida();
				b.setId(rs.getInt("id"));
				b.setNome(rs.getString("nome"));
				b.setValor(rs.getDouble("valor"));
				lista.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public void Deletar(Bebida b) {
		String sql = "DELETE FROM bebida "
				+ " WHERE id = " + b.getId();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void Atualizar (String nomeAntigo, Bebida b) {
		String sql = "UPDATE bebida "
				+ "SET id = " + b.getId() + ", nome = '" +b.getNome() + "', valor = " 
				+ b.getValor() + "WHERE nome = '" + nomeAntigo + "'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
