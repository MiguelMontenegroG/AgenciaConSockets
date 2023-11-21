package Proyecto.controllers;

import Proyecto.model.Cliente;
import Proyecto.model.Persona;
import Proyecto.model.Correos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import Proyecto.utils.ArchivoUtils;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import Proyecto.model.Exel;

import java.io.IOException;
import java.util.ArrayList;


public class
LoginController {

    @FXML
    private Label adminlb;

    @FXML
    private Label adminusuariolb;

    @FXML
    private PasswordField adminpass;

    @FXML
    private Button adminbt;

    @FXML
    private Label clientelb;

    @FXML
    private Label usuarioClientelb;

    @FXML
    private PasswordField clientepass;

    @FXML
    private Button clientebt;

    @FXML
    private Label crearCuentalb;

    @FXML
    private Button crearCuentabt;

    @FXML
    private TextField clienteuser;

    @FXML
    private TextField adminuser;

    String archivoClientes = "src/main/resources/persistencia/clientes.txt";
    String archivoAdmin = "src/main/resources/persistencia/admins.txt";

    private void mostrarAlerta(String titulo, String encabezado, String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    void handleAdminButtonAction(ActionEvent event) {
        String adminIngresado = adminuser.getText();
        String adminIngresada = adminpass.getText();

        try {
            ArrayList<String> lineas = ArchivoUtils.leerArchivoBufferedReader(archivoAdmin);

            for (String linea : lineas) {
                String[] partes = linea.split(",");
                if (partes.length == 4) {
                    String usuario = partes[0].trim();
                    String contrasena = partes[1].trim();
                    String nombreCompleto = "";
                    String correo = "";

                    Persona adminEnArchivo = new  Persona (nombreCompleto, usuario, correo, contrasena);

                    if (adminEnArchivo.getIdentificacion().equals(adminIngresado) && adminEnArchivo.getPassword().equals(adminIngresada)) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/admPrincipal.fxml"));
                            Parent root = fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            mostrarAlerta("Error", "Error al leer el archivo", "Ocurrió un error al intentar leer el archivo.");
                        }
                        return;
                    }
                }
            }
            mostrarAlerta("Error de autenticación", "Usuario o contraseña inválidos", "Por favor, verifica tus credenciales.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleCrearClienteButtonAction(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Registro.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al leer el archivo", "Ocurrió un error al intentar leer el archivo.");
        }
    }

    @FXML
    void handleClienteButtonAction(ActionEvent event) {
        String usuarioIngresado = clienteuser.getText();
        String contrasenaIngresada = clientepass.getText();

        try {
            ArrayList<String> lineas = ArchivoUtils.leerArchivoBufferedReader(archivoClientes);

            for (String linea : lineas) {
                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    String usuario = partes[0].trim();
                    String contrasena = partes[1].trim();

                    Cliente clienteEnArchivo = new Cliente("", usuario, "", contrasena, "", "");

                    if (clienteEnArchivo.getIdentificacion().equals(usuarioIngresado) && clienteEnArchivo.getPassword().equals(contrasenaIngresada)) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/clientePrincipal.fxml"));
                            Parent root = fxmlLoader.load();
                            ClientePrincipalController clientePrincipalController = fxmlLoader.getController();
                            // Pasa el cliente autenticado al controlador
                            clientePrincipalController.initData(clienteEnArchivo);

                            Stage stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            mostrarAlerta("Error", "Error al leer el archivo", "Ocurrió un error al intentar leer el archivo.");
                        }
                        return;
                    }
                }
            }
            mostrarAlerta("Error de autenticación", "Usuario o contraseña inválidos", "Por favor, verifica tus credenciales.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
