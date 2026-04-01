package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.MarcaDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.Marca;
import br.edu.ifsc.fln.utils.AlertDialog;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLAnchorPaneCadastroMarcaController implements Initializable {

    @FXML
    private TableView<Marca> tableViewMarcas;
    @FXML
    private TableColumn<Marca, String> tableColumnMarcaNome;
    @FXML
    private Label lbMarcaId;
    @FXML
    private Label lbMarcaNome;
    @FXML
    private Button btInserir;
    @FXML
    private Button btAlterar;
    @FXML
    private Button btExcluir;

    private List<Marca> listMarcas;
    private ObservableList<Marca> observableListMarcas;

    private final Database database = DatabaseFactory.getDatabase("mysql"); // ou "postgresql"
    private final Connection connection = database.conectar();
    private final MarcaDAO marcaDAO = new MarcaDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        marcaDAO.setConnection(connection);
        carregarTableViewMarcas();

        tableViewMarcas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewMarcas(newValue));
    }

    public void carregarTableViewMarcas() {
        tableColumnMarcaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        listMarcas = marcaDAO.listar();
        observableListMarcas = FXCollections.observableArrayList(listMarcas);
        tableViewMarcas.setItems(observableListMarcas);
    }

    public void selecionarItemTableViewMarcas(Marca marca) {
        if (marca != null) {
            lbMarcaId.setText(String.valueOf(marca.getId()));
            lbMarcaNome.setText(marca.getNome());
        } else {
            lbMarcaId.setText("");
            lbMarcaNome.setText("");
        }
    }

    @FXML
    public void handleBtInserir(ActionEvent event) throws IOException {
        Marca marca = new Marca();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastroMarcaDialog(marca);
        if (buttonConfirmarClicked) {
            marcaDAO.inserir(marca);
            carregarTableViewMarcas();
        }
    }

    @FXML
    public void handleBtAlterar(ActionEvent event) throws IOException {
        Marca marca = tableViewMarcas.getSelectionModel().getSelectedItem();
        if (marca != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastroMarcaDialog(marca);
            if (buttonConfirmarClicked) {
                marcaDAO.alterar(marca);
                carregarTableViewMarcas();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma marca na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleBtExcluir(ActionEvent event) throws IOException {
        Marca marca = tableViewMarcas.getSelectionModel().getSelectedItem();
        if (marca != null) {
            if (AlertDialog.confirmarExclusao("Tem certeza que deseja excluir a marca " + marca.getNome() + "?")) {
                marcaDAO.remover(marca);
                carregarTableViewMarcas();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma marca na Tabela!");
            alert.show();
        }
    }

    private boolean showFXMLAnchorPaneCadastroMarcaDialog(Marca marca) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastroMarcaController.class.getResource("/view/FXMLAnchorPaneCadastroMarcaDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Marca");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        FXMLAnchorPaneCadastroMarcaDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setMarca(marca);

        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();
    }
}