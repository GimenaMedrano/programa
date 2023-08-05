package com.example.programa;


import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.time.Instant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.scene.control.*;



import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class HelloController  {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField apellidoTextField;
    @FXML
    private JFXComboBox<String> identidad_GeneroComboBox;
    @FXML
    private JFXRadioButton masculinoRadioButton;
    @FXML
    private JFXRadioButton femeninoRadioButton;
    @FXML
    private ToggleGroup generoToggleGroup;
    @FXML
    private AnchorPane datosAnchorPane;

    @FXML
    private AnchorPane blancoAnchorPane;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label SALUDO;
    @FXML
    private CheckBox espanolCheckBox;

    @FXML
    private CheckBox inglesCheckBox;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label porcentajeLabel;
    @FXML
    private double progreso = 0.0;
    @FXML
    private void initialize() {

        generoToggleGroup = new ToggleGroup();
        femeninoRadioButton.setToggleGroup(generoToggleGroup);
        masculinoRadioButton.setToggleGroup(generoToggleGroup);
        // Agregar las opciones al ComboBox
        ObservableList<String> opciones = FXCollections.observableArrayList("DPI", "NIT", "Pasaporte");
        identidad_GeneroComboBox.setItems(opciones);

        Instant LocalDate = null;
        datePicker.setValue(java.time.LocalDate.from(java.time.LocalDate.now()));

        // Mostrar la fecha y hora actual en el Label
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(formatter);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), SALUDO);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(Timeline.INDEFINITE); // Repetir la animación indefinidamente
        fadeTransition.setAutoReverse(true); // Invertir la animación al final para que el texto vuelva a aparecer

        // Iniciar la animación
        fadeTransition.play();
        progressBar.setProgress(0.0);
        porcentajeLabel.setText("PORCENTAJE: 0%");



    }
    @FXML
    private void iniciarAplicacion() {
        // Reiniciar el progreso
        progreso = 0.0;
        progressBar.setProgress(0.0);
        porcentajeLabel.setText("PORCENTAJE: 0%");

        // Detener cualquier animación en curso
        Timeline timeline = null;
        if (timeline != null) {
            timeline.stop();
        }

        // Crear una nueva animación que se ejecute cada 1 segundo (1000 milisegundos)
        timeline = new Timeline(
                new KeyFrame(javafx.util.Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0.0)),
                new KeyFrame(javafx.util.Duration.seconds(1), e -> cargarProgreso())
        );

        timeline.setOnFinished(e -> {
            if (progreso >= 1.0) {
                redirigirAContenido();
            }
        });


        // Repetir la animación 10 veces para alcanzar el 100%
        timeline.setCycleCount(10);

        // Iniciar la animación
        timeline.play();
    }


    private void redirigirAContenido() {
        blancoAnchorPane.setVisible(true);
        datosAnchorPane.setVisible(false);

        irAInicio(null);


        if (progreso >= 1.0) {
            redirigirAContenido();
        }
    }


    private void cargarProgreso() {
        progreso += 0.1;
        if (progreso > 1.0) {
            progreso = 1.0; // Asegurarse de que el progreso no exceda 1.0
        }
        progressBar.setProgress(progreso);
        double porcentajeLlenado = progreso * 100;
        porcentajeLabel.setText(String.format("PORCENTAJE: %.0f%%", porcentajeLlenado));
    }



    @FXML

    private void guardarInformacion() {
        String nombre = nombreTextField.getText();
        String apellido = apellidoTextField.getText();
        String genero = generoToggleGroup.getSelectedToggle() != null
                ? ((JFXRadioButton) generoToggleGroup.getSelectedToggle()).getText()
                : "No especificado";
        String tipoIdentidad = identidad_GeneroComboBox.getValue() != null
                ? identidad_GeneroComboBox.getValue()
                : "No especificado";
        String idiomas = "";
        if (espanolCheckBox.isSelected()) {
            idiomas += "Español ";
        }
        if (inglesCheckBox.isSelected()) {
            idiomas += "Inglés ";
        }

        // Crear el contenido del cuadro de diálogo con la información ingresada
        String s = "\n"
                + "Idiomas: " + idiomas;
        String mensaje = "Nombre: " + nombre + "\n"
                + "Apellido: " + apellido + "\n"
                + "Género: " + genero + "\n"
                + "Tipo de Identificacion: " + tipoIdentidad +"\n"
                + "Idiomas: " + idiomas;

        // Mostrar el cuadro de diálogo con la información
        Alert informacionDialog = new Alert(Alert.AlertType.INFORMATION);
        informacionDialog.setTitle("Información Guardada");
        informacionDialog.setHeaderText(null);
        informacionDialog.setContentText(mensaje);
        informacionDialog.showAndWait();
    }

    @FXML
    private void limpiarCampos() {
        // Mostrar un cuadro de diálogo de confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmación");
        confirmacion.setHeaderText("¿Estás seguro de que deseas limpiar los campos?");
        confirmacion.setContentText("Los datos ingresados se perderán.");

        // Obtener la respuesta del usuario desde el cuadro de diálogo
        ButtonType respuesta = confirmacion.showAndWait().orElse(ButtonType.CANCEL);

        // Si el usuario hace clic en "Aceptar", limpiar los campos
        if (respuesta == ButtonType.OK) {
            nombreTextField.clear();
            apellidoTextField.clear();
            identidad_GeneroComboBox.setValue(null); // Opción en blanco

            // Desseleccionar ambos radio buttons
            generoToggleGroup.selectToggle(null);

            // Desmarcar los checkboxes
            espanolCheckBox.setSelected(false);
            inglesCheckBox.setSelected(false);
        }
    }


    @FXML
    public void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void irAInicio(ActionEvent actionEvent) {
        datosAnchorPane.setVisible(false);

        blancoAnchorPane.setVisible(true);
    }

    public void irAFormulario(ActionEvent actionEvent) {

        datosAnchorPane.setVisible(true);

        blancoAnchorPane.setVisible(false);
    }


}
