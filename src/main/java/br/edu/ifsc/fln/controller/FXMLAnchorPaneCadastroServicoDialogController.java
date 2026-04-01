package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.domain.Servico;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLAnchorPaneCadastroServicoDialogController implements Initializable {

    @FXML
    private TextField tfDescricao;
    @FXML
    private TextField tfValor;
    @FXML
    private Button btConfirmar;
    @FXML
    private Button btCancelar;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Servico servico;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
        this.tfDescricao.setText(servico.getDescricao());
        if (servico.getValor() != 0.0) {
            this.tfValor.setText(String.valueOf(servico.getValor()));
        }
    }

    @FXML
    public void handleBtConfirmar(ActionEvent event) {
        if (validarEntradaDeDados()) {
            servico.setDescricao(tfDescricao.getText());
            servico.setValor(Double.parseDouble(tfValor.getText().replace(",", ".")));

            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    public void handleBtCancelar(ActionEvent event) {
        dialogStage.close();
    }


    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (tfDescricao.getText() == null || tfDescricao.getText().length() == 0) {
            errorMessage += "Descrição inválida!\n";
        }

        if (tfValor.getText() == null || tfValor.getText().length() == 0) {
            errorMessage += "Valor inválido!\n";
        } else {
            try {
                Double.parseDouble(tfValor.getText().replace(",", "."));
            } catch (NumberFormatException e) {
                errorMessage += "Valor deve ser um número!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no Cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
}