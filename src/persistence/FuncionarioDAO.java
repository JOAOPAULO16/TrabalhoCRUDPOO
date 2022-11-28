package persistence;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Funcionario;

public class FuncionarioDAO implements IFuncionarioDAO {
	
	public final static String URL = 
			"jdbc:mariadb://localhost:3306/pizzaria";
	public final static String USER = "root";
	public final static String PASS  = "123456";
	private java.sql.Connection con;
	
	public FuncionarioDAO() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			con = DriverManager.getConnection(URL, USER, PASS);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void criar(Funcionario f) {
		String sql = "INSERT INTO funcionario "
				+ "(id, nome, cpf, salario) "
				+ "VALUES (" + f.getId() + ", '" + f.getNome() + "', '" 
				+ f.getCPF() + "'," + f.getSalario()+ ")";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Funcionario> pesquisarPorNome(String nome) {
		List<Funcionario> lista = new ArrayList<>();
		String sql = "SELECT * FROM funcionario WHERE nome LIKE '%" + nome + "%'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Funcionario f = new Funcionario();
				f.setId(rs.getInt("id"));
				f.setNome(rs.getString("nome"));
				f.setCPF(rs.getString("cpf"));
				f.setSalario(rs.getDouble("salario"));
				lista.add(f);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public void deletar(Funcionario f) {
		String sql = "DELETE FROM funcionario "
				+ " WHERE id = " + f.getId();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void atualizar(String nomeAntigo, Funcionario f) {
		String sql = "UPDATE funcionario "
				+ "SET id = " + f.getId() + ", nome = '" + f.getNome() + 
				"', cpf = '" + f.getCPF() + "', salario = " + f.getSalario() 
				+ "WHERE nome = '" + nomeAntigo + "'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}