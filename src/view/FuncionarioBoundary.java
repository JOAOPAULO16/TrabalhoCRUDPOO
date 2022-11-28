package view;

import control.FuncionarioControl;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import model.Funcionario;

public class FuncionarioBoundary extends Application {
	private TextField txtId = new TextField("");
	private TextField txtNome = new TextField("");
	private TextField txtCPF = new TextField("");
	private TextField txtSalario = new TextField("");
	private Button btnBuscar = new Button("Buscar");
	private Button btnSalvar = new Button("Salvar");

	private FuncionarioControl fc = new FuncionarioControl();
	private TableView<Funcionario> tabelaFuncionario = new TableView<>();


	@Override
	public void start(Stage stage) throws Exception {
		BorderPane bp = new BorderPane();
		GridPane gp = new GridPane();
		Scene sc = new Scene(bp, 400, 300);
		
		bp.setTop(gp);
		bp.setCenter(tabelaFuncionario);
		
		criarTabela();
		
		gp.add(new Label("Id : "), 0, 0);
		gp.add(txtId, 1, 0);
		gp.add(new Label("Nome : "), 0, 1);
		gp.add(txtNome, 1, 1);
		gp.add(new Label("CPF : "), 0, 2);
		gp.add(txtCPF, 1, 2);
		gp.add(new Label("Salario : "), 0, 3);
		gp.add(txtSalario, 1, 3);
		gp.add(btnSalvar, 0, 4);
		gp.add(btnBuscar, 1, 4);
		
		btnBuscar.setOnAction(e -> {
			fc.pesquisar();
			fc.limpar();
			fc.pesquisar();
		});
		
		btnSalvar.setOnAction(e -> {
			fc.salvar();
			fc.limpar();
		});
		
		vincular();
		
		stage.setScene(sc);
		stage.setTitle("Cadastrar Funcionário");
		stage.show();
	}


	private void vincular() {
		StringConverter<? extends Number> converterInt = 
				new IntegerStringConverter();
		Bindings.bindBidirectional(txtId.textProperty(),
				fc.idProperty(),
				(StringConverter)converterInt);
		
		Bindings.bindBidirectional(txtNome.textProperty(), fc.nomeProperty());
		Bindings.bindBidirectional(txtCPF.textProperty(), fc.cpfProperty());
		
		StringConverter<? extends Number> converterDouble = 
				new DoubleStringConverter();
		Bindings.bindBidirectional(txtSalario.textProperty(),
				fc.salarioProperty(),
				(StringConverter)converterDouble);	
	}


	private void criarTabela() {
		TableColumn<Funcionario, Integer> col1 = new TableColumn<>("Id");
		TableColumn<Funcionario, String> col2 = new TableColumn<>("Nome");
		TableColumn<Funcionario, String> col3 = new TableColumn<>("CPF");
		TableColumn<Funcionario, Double> col4 = new TableColumn<>("Salario");
		TableColumn<Funcionario, String> col5 = new TableColumn("Ações");
		
		col1.setCellValueFactory(new PropertyValueFactory<Funcionario, Integer>("id") );
		col2.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("nome") );
		col3.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("cpf") );
		col4.setCellValueFactory(new PropertyValueFactory<Funcionario, Double>("valor") );
		
		tabelaFuncionario.getColumns().clear();
		tabelaFuncionario.getColumns().addAll(col1, col2, col3, col4, col5);
		
		Callback<TableColumn<Funcionario, String>, TableCell<Funcionario, String>> cellFactory
        = //
        new Callback<TableColumn<Funcionario, String>, TableCell<Funcionario, String>>() {
			@Override
			public TableCell call(final TableColumn<Funcionario, String> param) {
				final TableCell<Funcionario, String> cell = new TableCell<Funcionario, String>() {
        	
					final Button btnDeletar = new Button("Deletar");
					final Button btnEditar = new Button("Editar");

		            @Override
		            public void updateItem(String item, boolean empty) {
		                super.updateItem(item, empty);
		                if (empty) {
		                    setGraphic(null);
		                    setText(null);
		                } else {
		                    btnDeletar.setOnAction(event -> {
		                        Funcionario f = getTableView().getItems().get(getIndex());
		                        fc.deletar(f);
		                        fc.limpar();
		                        fc.pesquisar();
		                    });
		                    
		                    btnEditar.setOnAction(event -> {
		                    	Funcionario f = getTableView().getItems().get(getIndex());
		                    	fc.setEntity(f);
		                    	fc.editar();
		                    });
		                 	FlowPane fp = new FlowPane();
		                    fp.getChildren().addAll(btnDeletar, btnEditar);
		                    
		                    setGraphic(fp);
		                    setText(null);
		                }
		            }
		        };
		        return cell;
		    }
		};
		
		col5.setCellFactory(cellFactory);
		
		tabelaFuncionario.setItems(fc.getListaFuncionario());
	
	}
	
	public static void main(String[] args) {
		Application.launch(FuncionarioBoundary.class, args);
	}

}
