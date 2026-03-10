package br.edu.ifsc.fln.prjjavafx1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HelloController {

    @FXML
    private Button btCalcular;

    @FXML
    private Button btNovo;

    @FXML
    private ComboBox<String> comboSexo;

    @FXML
    private Spinner<Integer> spinnerIdade;

    @FXML
    private TextField tfAltura;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfPeso;

    @FXML
    public void initialize() {

        spinnerIdade.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 120, 0)
        );

        comboSexo.getItems().addAll(
                "Masculino",
                "Feminino",
                "Outro"
        );
    }

    @FXML
    void btOnActionCalcularIMC(ActionEvent event) {
        double altura = Double.parseDouble(tfAltura.getText());
        double peso = Double.parseDouble(tfPeso.getText());


        double imc = peso / (altura * altura);


        String classificacao;
        if (imc < 18.5) {
            classificacao = "Baixo peso";
        } else if (imc < 25) {
            classificacao = "Eutrofia (peso adequado)";
        } else if (imc < 30) {
            classificacao = "Sobrepeso";
        } else if (imc < 35) {
            classificacao = "Obesidade grau 1";
        } else if (imc < 40) {
            classificacao = "Obesidade grau 2";
        }
        else {
            classificacao = "Obesidade extrema";
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Calculo de IMC");
        alert.setHeaderText(null);
        alert.setContentText("Nome: " + tfNome.getText() + "\nIdade: " + spinnerIdade.getValue() + "\nSexo: " + comboSexo.getValue() + "\nAltura: " + tfAltura.getText() +  "\nPeso: " + tfPeso.getText() + "\n\nIMC: " + String.format("%.2f", imc) +
            "\nClassificação: " + classificacao);

        alert.showAndWait();

    }

    @FXML
    void btOnActionNovo(ActionEvent event) {
            tfNome.clear();
            spinnerIdade.getValueFactory().setValue(0);
            comboSexo.setValue(null);
            tfAltura.clear();
            tfPeso.clear();
            tfNome.requestFocus();
    }


}

