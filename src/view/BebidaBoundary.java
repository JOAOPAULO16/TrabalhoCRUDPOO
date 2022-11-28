package view;

import control.BebidaControl;
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
import model.Bebida;

public class BebidaBoundary extends Application {
	
	private TextField txtId = new TextField("");
	private TextField txtNome = new TextField("");
	private TextField txtValor = new TextField("");
	private Button btnBuscar = new Button("Buscar");
	private Button btnSalvar = new Button("Salvar");

	private BebidaControl bc = new BebidaControl();
	private TableView<Bebida> tabelaBebida = new TableView<>();
	
	@Override
	public void start(Stage stage) throws Exception {
		BorderPane bp = new BorderPane();
		GridPane gp = new GridPane();
		Scene sc = new Scene(bp, 400, 300);
		
		bp.setTop(gp);
		bp.setCenter(tabelaBebida);
		
		criarTabela();
		
		gp.add(new Label("Id : "), 0, 0);
		gp.add(txtId, 1, 0);
		gp.add(new Label("Nome : "), 0, 1);
		gp.add(txtNome, 1, 1);
		gp.add(new Label("Valor : "), 0, 2);
		gp.add(txtValor, 1, 2);
		
		gp.add(btnSalvar, 0, 3);
		gp.add(btnBuscar, 1, 3);
		
		btnBuscar.setOnAction(e -> {
			bc.pesquisar();
			bc.limpar();
			bc.pesquisar();
		});
		
		btnSalvar.setOnAction(e -> {
			bc.salvar();
			bc.limpar();
		});
		
		vincular();
		
		stage.setScene(sc);
		stage.setTitle("Cadastrar Bebida");
		stage.show();
	}

	private void criarTabela() {
		TableColumn<Bebida, Integer> col1 = new TableColumn<>("Id");
		TableColumn<Bebida, String> col2 = new TableColumn<>("Nome");
		TableColumn<Bebida, Double> col3 = new TableColumn<>("Valor");
		TableColumn<Bebida, String> col4 = new TableColumn("Ações");
		
		col1.setCellValueFactory(new PropertyValueFactory<Bebida, Integer>("id") );
		col2.setCellValueFactory(new PropertyValueFactory<Bebida, String>("nome") );
		col3.setCellValueFactory(new PropertyValueFactory<Bebida, Double>("valor") );
		
		tabelaBebida.getColumns().clear();
		tabelaBebida.getColumns().addAll(col1, col2, col3, col4);
		
        Callback<TableColumn<Bebida, String>, TableCell<Bebida, String>> cellFactory
                = //
                new Callback<TableColumn<Bebida, String>, TableCell<Bebida, String>>() {
            @Override
            public TableCell call(final TableColumn<Bebida, String> param) {
                final TableCell<Bebida, String> cell = new TableCell<Bebida, String>() {
                	
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
                                Bebida b = getTableView().getItems().get(getIndex());
                                bc.deletar(b);
                                bc.limpar();
                                bc.pesquisar();
                            });
                            
                            btnEditar.setOnAction(event -> {
                            	Bebida b = getTableView().getItems().get(getIndex());
                            	bc.setEntity(b);
                            	bc.editar();
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
		
        col4.setCellFactory(cellFactory);
        
		tabelaBebida.setItems(bc.getListaBebida());
		
	}

	public void vincular() {
		StringConverter<? extends Number> converterInt = 
				new IntegerStringConverter();
		Bindings.bindBidirectional(txtId.textProperty(),
				bc.idProperty(),
				(StringConverter)converterInt);
		
		Bindings.bindBidirectional(txtNome.textProperty(), bc.nomeProperty());
		
		StringConverter<? extends Number> converterDouble = 
				new DoubleStringConverter();
		Bindings.bindBidirectional(txtValor.textProperty(),
				bc.valorProperty(),
				(StringConverter)converterDouble);
	}
	
	public static void main(String[] args) {
		Application.launch(BebidaBoundary.class, args);
	}
}
