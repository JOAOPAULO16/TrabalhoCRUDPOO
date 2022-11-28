package control;

import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Funcionario;
import persistence.FuncionarioDAO;
import persistence.IFuncionarioDAO;

public class FuncionarioControl {
	
	private IntegerProperty id = new SimpleIntegerProperty(0);
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty cpf = new SimpleStringProperty("");
	private DoubleProperty salario = new SimpleDoubleProperty(0.00);
	
	private IFuncionarioDAO fDAO = new FuncionarioDAO();
	
	private boolean editando = false;
	private String nomeAntigo = null;
	
	private ObservableList<Funcionario> listaFuncionario = FXCollections.observableArrayList();
	
	public Funcionario getEntity() {
		Funcionario f = new Funcionario();
		f.setId(id.get());
		f.setNome(nome.get());
		f.setCPF(cpf.get());	
		f.setSalario(salario.get());
		return f;
	}
	
	public void setEntity(Funcionario f) {
		id.set( f.getId() );
		nome.set( f.getNome() );
		cpf.set( f.getCPF() );
		salario.set( f.getSalario() );
	}
	
	public void editar() {
		this.editando = true;
		this.nomeAntigo = nome.get();
	}
	
	public void salvar() {
		Funcionario f = getEntity();
		if (this.editando) {
			fDAO.atualizar(nomeAntigo, f);
		} else {
			fDAO.criar(f);
		}
		
	}
	
	public void limpar() {
		id.set(0);
		nome.set("");
		cpf.set("");
		salario.set(0);
		this.editando = false;
		this.nomeAntigo = null;
	}
	
	public void pesquisar() {
		List<Funcionario> tempList = fDAO.pesquisarPorNome( nome.get() );
		listaFuncionario.clear();
		listaFuncionario.addAll(tempList);
	}
	
	public void deletar(Funcionario f) {
		fDAO.deletar(f);
	}
	
	public IntegerProperty idProperty() {
		return id;
	}

	public StringProperty nomeProperty() {
		return nome;
	}
	
	public StringProperty cpfProperty() {
		return cpf;
	}
	
	public DoubleProperty salarioProperty() {
		return salario;
	}

	public boolean isEditando() {
		return editando;
	}
	
	public String getNomeAntigo() {
		return nomeAntigo;
	}

	public ObservableList<Funcionario> getListaFuncionario() {
		return listaFuncionario;
	}
	

}
