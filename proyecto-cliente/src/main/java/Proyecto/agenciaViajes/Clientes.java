package Proyecto.agenciaViajes;

import java.io.*;
import java.net.*;

public class Clientes {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Clientes(String direccion, int puerto) {
        try {
            socket = new Socket(direccion, puerto);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarMensaje(String mensaje) {
        out.println(mensaje);
    }

    public String recibirMensaje() {
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void cerrarConexion() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean recibirRespuestaBooleana() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String respuesta = in.readLine();

            if (respuesta != null) {
                if (respuesta.equals("true")) {
                    return true;
                } else if (respuesta.equals("false")) {
                    return false;
                } else {
                    throw new IllegalArgumentException("La respuesta recibida no es válida: " + respuesta);
                }
            } else {
                throw new IOException("La respuesta recibida es nula");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
