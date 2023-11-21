package Proyecto.agenciaViajes;

import java.io.*;
import java.net.*;

public class Servidor {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;



    public Servidor(Socket socket) {
        this.clientSocket = socket;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void iniciarServidor() {
        try {
            String credenciales;
            while ((credenciales = in.readLine()) != null) {
                System.out.println("Credenciales recibidas: " + credenciales);

                String[] partes = credenciales.split(",");
                if (partes.length >= 2) {
                    String usuario = partes[0].trim();
                    String contrasena = partes[1].trim();

                    boolean accesoConcedido = verificarCredencialesEnBaseDeDatos(usuario, contrasena);

                    if (accesoConcedido) {
                        out.println("AccesoConcedido");
                    } else {
                        out.println("AccesoDenegado");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cerrarConexion() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean verificarCredencialesEnBaseDeDatos(String usuarioIngresado, String contrasenaIngresada) {
        String archivoUsuarios = "src/main/resources/persistencia/clientes.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(archivoUsuarios))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    String usuario = partes[0].trim();
                    String contrasena = partes[1].trim();

                    if (usuario.equals(usuarioIngresado) && contrasena.equals(contrasenaIngresada)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}

