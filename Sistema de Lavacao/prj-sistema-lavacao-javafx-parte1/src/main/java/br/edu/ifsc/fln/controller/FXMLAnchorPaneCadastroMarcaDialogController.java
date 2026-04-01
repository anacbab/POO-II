package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.domain.Marca;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLAnchorPaneCadastroMarcaDialogController implements Initializable {

    @FXML
    private TextField tfNome;
    @FXML
    private Button btConfirmar;
    @FXML
    private Button btCancelar;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Marca marca;

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

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
        this.tfNome.setText(marca.getNome());
    }

    @FXML
    public void handleBtConfirmar(ActionEvent event) {
        marca.setNome(tfNome.getText());
        buttonConfirmarClicked = true;
        dialogStage.close();
    }

    @FXML
    public void handleBtCancelar(ActionEvent event) {
        dialogStage.close();
    }
}