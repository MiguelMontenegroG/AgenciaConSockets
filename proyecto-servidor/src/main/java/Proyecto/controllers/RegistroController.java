package Proyecto.controllers;

import Proyecto.utils.ArchivoUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import Proyecto.model.Correos;
import javafx.stage.Stage;


public class RegistroController {

    @FXML
    private Label adminusuariolb;

    @FXML
    private Label passlb;

    @FXML
    private PasswordField adminpass;

    @FXML
    private Button crearCuentabt;

    @FXML
    private TextField adminuser;

    @FXML
    private Label nomComlb;

    @FXML
    private Label correolb;

    @FXML
    private TextField nomComtf;

    @FXML
    private TextField correotf;

    @FXML
    private TextField direcciontf;

    @FXML
    private TextField telefonotf;
    String rutaArchivo = "src/main/resources/persistencia/clientes.txt";

    private void mostrarAlerta(String titulo, String encabezado, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAlertaPositiva(String titulo, String encabezado, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    void handleCrearCuentaButtonAction(ActionEvent event) {
        String usuario = adminuser.getText();
        String contrasena = adminpass.getText();
        String nombreCompleto = nomComtf.getText();
        String correo = correotf.getText();
        String telefono = telefonotf.getText();
        String direccion = direcciontf.getText();

        if (!usuario.isEmpty() && !contrasena.isEmpty() && !nombreCompleto.isEmpty() && !correo.isEmpty() && !telefono.isEmpty() && !correo.isEmpty()) {
            mostrarAlertaPositiva("Confirmación de correo", "Confirmación de correo", "Revisa tu correo, te hemos enviado un codigo de verificacón");
            String codigo = Correos.generarCombinacion();
            Correos.EnviarCorreoComprobacion(correo, codigo);
            TextInputDialog cuadroComprobante = new TextInputDialog();
            cuadroComprobante.setTitle("");
            cuadroComprobante.setHeaderText("Ingrese el codigo enviado al correo");
            cuadroComprobante.setContentText("Codigo: ");
            Optional<String> resultado = cuadroComprobante.showAndWait();
            String codigoRecibido = resultado.orElse("");
            if (codigoRecibido.equals(codigo)) {
                try {
                    List<String> datosActuales = ArchivoUtils.leerArchivoBufferedReader(rutaArchivo);

                    String nuevoDato = usuario + "," + contrasena + "," + nombreCompleto + "," + correo + "," + telefono + "," + direccion;

                    datosActuales.add(nuevoDato);

                    ArchivoUtils.escribirArchivoBufferedWriter(rutaArchivo, datosActuales, false);

                    adminuser.clear();
                    adminpass.clear();
                    nomComtf.clear();
                    correotf.clear();
                    telefonotf.clear();
                    direcciontf.clear();

                    mostrarAlertaPositiva("Registro Exitoso", "Datos registrados con exito", "Te enviamos un correo ;)");
                    Correos.EnviarCorreoCuentaCreada(correo, nombreCompleto);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                mostrarAlerta("", "error", "");
            }
        } else {
            mostrarAlerta("Error de registro", "Falta de informacion", "Por favor, llena todos los espacion");
        }
    }
}