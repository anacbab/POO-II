package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.ServicoDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.Servico;
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

public class FXMLAnchorPaneCadastroServicoController implements Initializable {

    @FXML
    private TableView<Servico> tableViewServicos;
    @FXML
    private TableColumn<Servico, String> tableColumnServicoDescricao;
    @FXML
    private TableColumn<Servico, Double> tableColumnServicoValor;
    @FXML
    private Label lbServicoId;
    @FXML
    private Label lbServicoDescricao;
    @FXML
    private Label lbServicoValor;
    @FXML
    private Label lbServicoPontos;
    @FXML
    private Button btInserir;
    @FXML
    private Button btAlterar;
    @FXML
    private Button btExcluir;

    private List<Servico> listServicos;
    private ObservableList<Servico> observableListServicos;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ServicoDAO servicoDAO = new ServicoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        servicoDAO.setConnection(connection);
        carregarTableViewServicos();

        tableViewServicos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewServicos(newValue));
    }

    public void carregarTableViewServicos() {
        tableColumnServicoDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tableColumnServicoValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

        listServicos = servicoDAO.listar();
        observableListServicos = FXCollections.observableArrayList(listServicos);
        tableViewServicos.setItems(observableListServicos);
    }

    public void selecionarItemTableViewServicos(Servico servico) {
        if (servico != null) {
            lbServicoId.setText(String.valueOf(servico.getId()));
            lbServicoDescricao.setText(servico.getDescricao());
            lbServicoValor.setText(String.format("R$ %.2f", servico.getValor()));
            lbServicoPontos.setText(String.valueOf(Servico.getPontos())); // Mostra o valor estático
        } else {
            lbServicoId.setText("");
            lbServicoDescricao.setText("");
            lbServicoValor.setText("");
            lbServicoPontos.setText("");
        }
    }

    @FXML
    public void handleBtInserir(ActionEvent event) throws IOException {
        Servico servico = new Servico();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastroServicoDialog(servico);
        if (buttonConfirmarClicked) {
            servicoDAO.inserir(servico);
            carregarTableViewServicos();
        }
    }

    @FXML
    public void handleBtAlterar(ActionEvent event) throws IOException {
        Servico servico = tableViewServicos.getSelectionModel().getSelectedItem();
        if (servico != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastroServicoDialog(servico);
            if (buttonConfirmarClicked) {
                servicoDAO.alterar(servico);
                carregarTableViewServicos();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um serviço na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleBtExcluir(ActionEvent event) throws IOException {
        Servico servico = tableViewServicos.getSelectionModel().getSelectedItem();
        if (servico != null) {
            if (AlertDialog.confirmarExclusao("Tem certeza que deseja excluir o serviço " + servico.getDescricao() + "?")) {
                servicoDAO.remover(servico);
                carregarTableViewServicos();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um serviço na Tabela!");
            alert.show();
        }
    }

    private boolean showFXMLAnchorPaneCadastroServicoDialog(Servico servico) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastroServicoController.class.getResource("/view/FXMLAnchorPaneCadastroServicoDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Serviço");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        FXMLAnchorPaneCadastroServicoDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setServico(servico);

        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();
    }
}