package Proyecto.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import Proyecto.agenciaViajes.Clientes;
import java.io.IOException;

@Setter
@Getter
public class InicioController {

    private Stage ventanaAnterior;
    @FXML
    private Button btnLogin;

    @FXML
    private Button btnReservas;

    @FXML
    private Button btnPaquetes;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnEstadistica;

    @FXML
    private TextField txtIdPerfil;

    @FXML
    private TextField txtNombrePerfil;

    @FXML
    private TextField txtCorreoPerfil;

    @FXML
    private TextField txtTelefonoPerfil;

    @FXML
    private TextField txtDireccionPerfil;

    private void mostrarAlerta(String titulo, String encabezado, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAlertaPositiva(String titulo, String encabezado, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Utiliza INFORMATION en lugar de ERROR
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void mostrarLogin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Clientes login = new Clientes("127.0.0.2", 5007);
            } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void mostrarVentanaOriginal() {
        stage.show(); // Mostrar la ventana original al cerrar la nueva ventana
    }

    @FXML
    private void mostrarVenPer() {
        mostrarAlertaPositiva("Error de inicio", "No a iniciado seción", "Inicie seción para acceder a las funciones");
    }

    @FXML
    private void mostrarVenPqCliente() {
        mostrarAlertaPositiva("Error de inicio", "No a iniciado seción", "Inicie seción para acceder a las funciones");
    }

    @FXML
    private void salirAction() {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();

        Platform.exit();
    }

    @FXML
    private void mostraraVenMisViajesCLiente() {
        mostrarAlertaPositiva("Error de inicio", "No a iniciado seción", "Inicie seción para acceder a las funciones");
    }

    @FXML
    private void ActualizarPerfilCliente() {
        mostrarAlertaPositiva("Error de inicio", "No a iniciado seción", "Inicie seción para acceder a las funciones");
    }
}
