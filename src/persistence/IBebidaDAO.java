package persistence;

import java.util.List;

import model.Bebida;

public interface IBebidaDAO {

	void Criar(Bebida b);
	List<Bebida> pesquisarPorNome(String nome);
	void Deletar(Bebida b);
	void Atualizar(String nomeAntigo, Bebida b);
}
