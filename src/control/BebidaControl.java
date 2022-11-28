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
import model.Bebida;
import persistence.BebidaDAO;
import persistence.IBebidaDAO;

public class BebidaControl {
	
	private IntegerProperty id = new SimpleIntegerProperty(0);
	private StringProperty nome = new SimpleStringProperty("");
	private DoubleProperty valor = new SimpleDoubleProperty(0.00);
	
	private IBebidaDAO bDAO = new BebidaDAO();
	
	private boolean editando = false;
	private String nomeAntigo = null;
	
	private ObservableList<Bebida> listaBebida = FXCollections.observableArrayList();
	
	public Bebida getEntity() {
		Bebida b = new Bebida();
		b.setId(id.get());
		b.setNome(nome.get());
		b.setValor(valor.get());
		return b;
	}
	
	public void setEntity(Bebida b) {
		id.set( b.getId() );
		nome.set( b.getNome() );
		valor.set( b.getValor() );
	}
	
	public void editar() {
		this.editando = true;
		this.nomeAntigo = nome.get();
	}
	
	public void salvar() {
		Bebida b = getEntity();
		if (this.editando) {
			bDAO.Atualizar(nomeAntigo, b);
		} else {
			bDAO.Criar(b);
		}
		
	}
	
	public void limpar() {
		id.set(0);
		nome.set("");
		valor.set(0);
		this.editando = false;
		this.nomeAntigo = null;
	}
	
	public void deletar(Bebida b) {
		bDAO.Deletar(b);
	}
	
	public void pesquisar() {
		List<Bebida> tempList = bDAO.pesquisarPorNome( nome.get() );
		listaBebida.clear();
		listaBebida.addAll(tempList);
	}
	
	public IntegerProperty idProperty() {
		return id;
	}

	public StringProperty nomeProperty() {
		return nome;
	}
	
	public DoubleProperty valorProperty() {
		return valor;
	}

	public ObservableList<Bebida> getListaBebida() {
		return listaBebida;
	}

	public boolean isEditando() {
		return editando;
	}

	public void setEditando(boolean editando) {
		this.editando = editando;
	}

	public String getNomeAntigo() {
		return nomeAntigo;
	}

	public void setNomeAntigo(String nomeAntigo) {
		this.nomeAntigo = nomeAntigo;
	}
	
	
}
