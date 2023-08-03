package com.example.programa;

import javafx.animation.KeyValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ProgressBar;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;

import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


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
    private ProgressBar progressBar;
    @FXML
    private Label porcentajeLabel;
    private double progreso = 0.0;

    private Timeline timeline;




    @FXML
    private void initialize() {
        datosAnchorPane.setVisible(false);
        blancoAnchorPane.setVisible(false);
        // Crear el ToggleGroup y asignarlo a los JFXRadioButton
        generoToggleGroup = new ToggleGroup();
        femeninoRadioButton.setToggleGroup(generoToggleGroup);
        masculinoRadioButton.setToggleGroup(generoToggleGroup);
        // Agregar las opciones al ComboBox
        ObservableList<String> opciones = FXCollections.observableArrayList("DPI", "NIT", "Pasaporte");
        identidad_GeneroComboBox.setItems(opciones);
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
            // Actualizar el progreso a 0

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
