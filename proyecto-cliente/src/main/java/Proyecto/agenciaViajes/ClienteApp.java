package Proyecto.agenciaViajes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClienteApp extends Application {

    private Clientes clientes;

    @Override
    public void start(Stage stage) throws Exception {
        try {
            // Resto del código para cargar la interfaz gráfica
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Inicio.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Cliente - Agencia Viajes");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(ClienteApp.class, args);
    }
}

