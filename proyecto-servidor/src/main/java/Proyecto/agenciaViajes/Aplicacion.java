package Proyecto.agenciaViajes;

import Proyecto.agenciaViajes.ServidorHilo;
import Proyecto.agenciaViajes.ServidorInterfaz;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Aplicacion {

    private ServerSocket serverSocket;

    public void iniciarServidorSocket() {
        try {
            serverSocket = new ServerSocket(5007);
            System.out.println("Servidor iniciado en el puerto 5007");

            while (true) {
                System.out.println("Esperando conexión...");
                Socket socket = serverSocket.accept();
                System.out.println("Cliente Conectado");

                // Iniciar un nuevo hilo para manejar la conexión del cliente
                ServidorHilo servidorHilo = new ServidorHilo(socket);
                servidorHilo.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para detener el servidor socket
    public void detenerServidorSocket() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("Servidor detenido");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ServidorInterfaz interfaz = new ServidorInterfaz();
            interfaz.mostrarInterfaz(); // Método para mostrar la interfaz
        });

        // Crear una instancia de Aplicacion para iniciar el servidor
        Aplicacion servidor = new Aplicacion();
        servidor.iniciarServidorSocket();
    }
}

