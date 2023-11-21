package Proyecto.agenciaViajes;

import java.io.*;
import java.net.Socket;

public class ServidorHilo extends Thread {
    private Socket socket;

    public ServidorHilo(Socket socket) {
        this.socket = socket;
    }

    public static void EnviarArchivo(String rutaArchivo, OutputStream outputStream) throws IOException {
        File archivo = new File(rutaArchivo);
        byte[] buffer = new byte[(int) archivo.length()];

        FileInputStream fis = new FileInputStream(archivo);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(buffer, 0, buffer.length);

        outputStream.write(buffer, 0, buffer.length);
        outputStream.flush();

        bis.close();
        fis.close();
    }

    public static void RecibirArchivo(String rutaDestino, InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;

        FileOutputStream fos = new FileOutputStream(rutaDestino);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }

        bos.close();
        fos.close();
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Mensaje recibido del cliente: " + inputLine);

                out.println("Mensaje recibido por el servidor: " + inputLine);
            }

            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
