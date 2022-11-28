package persistence;

import java.util.List;

import model.Funcionario;

public interface IFuncionarioDAO {
	
	void criar(Funcionario f);
	List<Funcionario> pesquisarPorNome(String nome);
	void deletar(Funcionario f);
	void atualizar(String nomeAntigo, Funcionario f);

}
