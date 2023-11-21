package Proyecto.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Proyecto.exceptions.*;
import lombok.*;
import Proyecto.utils.ArchivoUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
            } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void mostrarVentanaOriginal() {
        stage.show();
    }

    @FXML
    private void mostrarVenPer() {
        mostrarAlertaPositiva("Error de inicio", "No a iniciado seción", "Inicie seción para acceder a las funciones");
    }

    @FXML
    private void mostrarVenPqCliente() {
        Platform.runLater(() -> {
            List<String> opciones = new ArrayList<>();
            opciones.add("5 Estrellas");
            opciones.add("4 Estrellas");
            opciones.add("3 Estrellas");
            opciones.add("2 Estrellas");
            opciones.add("1 Estrellas");

            // Diálogo para seleccionar una de las cinco opciones
            ChoiceDialog<String> dialogOpciones = new ChoiceDialog<>("", opciones);
            dialogOpciones.setTitle("Calificacion");
            dialogOpciones.setHeaderText("Seleccione una opción");
            dialogOpciones.setContentText("Opciones:");

            Optional<String> resultadoOpcion = dialogOpciones.showAndWait();
            String opcionElegida = resultadoOpcion.orElse("");

            // Leer el archivo de texto y extraer las opciones
            ArrayList<String> lineasArchivo = null;
            try {
                lineasArchivo = ArchivoUtils.leerArchivoScanner(rutaArchivoGuia);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            List<String> opcionesLugar = new ArrayList<>();
            for (String linea : lineasArchivo) {
                String[] partes = linea.split(";");
                if (partes.length > 0) {
                    opcionesLugar.add(partes[0]);
                }
            }

            // Diálogo para seleccionar un lugar
            ChoiceDialog<String> dialogLugar = new ChoiceDialog<>("", opcionesLugar);
            dialogLugar.setTitle("Selecciónel guia");
            dialogLugar.setHeaderText("Seleccione el guia de su viaje");
            dialogLugar.setContentText("Guia:");

            Optional<String> resultadoLugar = dialogLugar.showAndWait();
            String lugarElegido = resultadoLugar.orElse("");

            // Diálogo para escribir un comentario
            TextInputDialog dialogComentario = new TextInputDialog();
            dialogComentario.setTitle("Agregar comentario");
            dialogComentario.setHeaderText("Escriba un comentario");
            dialogComentario.setContentText("Comentario:");

            Optional<String> resultadoComentario = dialogComentario.showAndWait();
            String comentario = resultadoComentario.orElse("");

            String datosAGuardar = opcionElegida + ";" + lugarElegido + ";" + comentario;            List<String> datosParaGuardar = Collections.singletonList(datosAGuardar);
            try {
                ArchivoUtils.escribirArchivoBufferedWriter(rutaArchivoCalificacionGuia, datosParaGuardar, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Datos guardados en calificacionGuia.txt");
        });
        mostrarAlertaPositiva("Error de inicio", "No a iniciado seción", "Inicie seción para acceder a las funciones");
    }

    @FXML
    private void salirAction() {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();

        Platform.exit();
    }
    String rutaArchivo = "src\\main\\resources\\persistencia\\destinos.txt";
    String rutaArchivoCalificacionDestino = "src/main/resources/persistencia/calificacionDestino.txt";
    String rutaArchivoGuia = "src\\main\\resources\\persistencia\\guias.txt";
    String rutaArchivoCalificacionGuia = "src/main/resources/persistencia/calificacionGuia.txt";

    @FXML
    private void mostraraVenMisViajesCLiente() {
        Platform.runLater(() -> {
            List<String> opciones = new ArrayList<>();
            opciones.add("5 Estrellas");
            opciones.add("4 Estrellas");
            opciones.add("3 Estrellas");
            opciones.add("2 Estrellas");
            opciones.add("1 Estrellas");

            // Diálogo para seleccionar una de las cinco opciones
            ChoiceDialog<String> dialogOpciones = new ChoiceDialog<>("", opciones);
            dialogOpciones.setTitle("Calificacion");
            dialogOpciones.setHeaderText("Seleccione una opción");
            dialogOpciones.setContentText("Opciones:");

            Optional<String> resultadoOpcion = dialogOpciones.showAndWait();
            String opcionElegida = resultadoOpcion.orElse("");

            // Leer el archivo de texto y extraer las opciones
            ArrayList<String> lineasArchivo = null;
            try {
                lineasArchivo = ArchivoUtils.leerArchivoScanner(rutaArchivo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            List<String> opcionesLugar = new ArrayList<>();
            for (String linea : lineasArchivo) {
                String[] partes = linea.split(";");
                if (partes.length > 0) {
                    opcionesLugar.add(partes[0]);
                }
            }

            // Diálogo para seleccionar un lugar
            ChoiceDialog<String> dialogLugar = new ChoiceDialog<>("", opcionesLugar);
            dialogLugar.setTitle("Selección de lugar");
            dialogLugar.setHeaderText("Seleccione un lugar");
            dialogLugar.setContentText("Lugar:");

            Optional<String> resultadoLugar = dialogLugar.showAndWait();
            String lugarElegido = resultadoLugar.orElse("");

            // Diálogo para escribir un comentario
            TextInputDialog dialogComentario = new TextInputDialog();
            dialogComentario.setTitle("Agregar comentario");
            dialogComentario.setHeaderText("Escriba un comentario");
            dialogComentario.setContentText("Comentario:");

            Optional<String> resultadoComentario = dialogComentario.showAndWait();
            String comentario = resultadoComentario.orElse("");

            String datosAGuardar = opcionElegida + ";" + lugarElegido + ";" + comentario;List<String> datosParaGuardar = Collections.singletonList(datosAGuardar);
            try {
                ArchivoUtils.escribirArchivoBufferedWriter(rutaArchivoCalificacionDestino, datosParaGuardar, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Datos guardados en calificacionDestino.txt");
        });

        mostrarAlertaPositiva("Error de inicio", "No a iniciado seción", "Inicie seción para acceder a las funciones");
    }


    @FXML
    private void ActualizarPerfilCliente() {
        mostrarAlertaPositiva("Error de inicio", "No a iniciado seción", "Inicie seción para acceder a las funciones");
    }
}
