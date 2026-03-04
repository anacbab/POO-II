package br.edu.ifsc.fln.prjjavafx1;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Label lbNomeCompleto;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfSobrenome;

    @FXML
    private TextField tfIdade;


    @FXML
    private CheckBox cbHabilitarEntrada;


    @FXML
    void btOnAction(javafx.event.ActionEvent event) {
        String nome = tfNome.getText();
        String sobrenome = tfSobrenome.getText();
        String nomeCompleto = nome + " " + sobrenome;
        lbNomeCompleto.setText(nomeCompleto);
        int idade = Integer.parseInt(tfIdade.getText());
        String situacao = "";
        if (idade >= 18) {
            situacao = "Maior de idade";
        } else {
            situacao = "Menor de idade";
        }
        lbNomeCompleto.setText(nomeCompleto + " você é " + situacao);

    }

    @FXML
    void btLimparOnAction(javafx.event.ActionEvent event) {
        tfNome.setText("");
        tfSobrenome.setText("");
        tfNome.requestFocus();
    }

    @FXML
    void cbHabilitarEntradaOnAction(javafx.event.ActionEvent event) {
        tfNome.setEditable(cbHabilitarEntrada.isSelected());
        tfSobrenome.setEditable(cbHabilitarEntrada.isSelected());
        tfNome.setDisable(!cbHabilitarEntrada.isSelected());
        tfSobrenome.setDisable(!cbHabilitarEntrada.isSelected());
    }


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}