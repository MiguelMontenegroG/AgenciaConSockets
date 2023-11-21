package Proyecto.utils;

import Proyecto.enums.Ciudades;
import Proyecto.enums.Clima;
import Proyecto.enums.EstadoReserva;
import Proyecto.enums.Lenguajes;
import Proyecto.model.*;
import lombok.extern.java.Log;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log
public class Persistencia {

    // Ruta del archivo donde se guardan los datos de los destinos.
    public static final String RUTA_ARCHIVO_DESTINO = "src/main/resources/persistencia/destinos.txt";
    public static final String RUTA_ARCHIVO_GUIAS = "src/main/resources/persistencia/guias.txt";
    public static final String RUTA_ARCHIVO_PAQUETES = "src/main/resources/persistencia/paquetes.txt";
    private static final String RUTA_ARCHIVO_RESERVAS ="src/main/resources/persistencia/reservas.txt" ;

    //    public static final String RUTA_ARCHIVO_ALQUILER = "src/main/resources/persistencia/alquiler.ser";


    //----------------------escribir---------------------------//
    public static void escribirDestino(Destino destino) {
        try {
            String imagePath = destino.getImagenes().get(0);
            String linea =
                    destino.getNombre() + ";" +
                            destino.getCiudad().getNombreCiudad() + ";" +
                            destino.getClima().getNombreClima() + ";" +
                            "[" + imagePath + "]" + ";" +
                            destino.getDescripcion();
            ArchivoUtils.escribirArchivoBufferedWriter(RUTA_ARCHIVO_DESTINO, List.of(linea), true);
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }

    public static void escribirGuiaTuristico(GuiaTuristico guiaTuristico) {
        try {
            String linea = guiaTuristico.getNombreCompleto() + ";" +
                    guiaTuristico.getIdentificacion() + ";" +
                    guiaTuristico.getCorreo() + ";" +
                    guiaTuristico.getPassword() + ";" +
                    guiaTuristico.getExperiencia() + ";" +
                    guiaTuristico.getLenguaje().getNombreLenguajes();
            ArchivoUtils.escribirArchivoBufferedWriter(RUTA_ARCHIVO_GUIAS, List.of(linea), true);
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }

    public static void escribirPaqueteTuristico(PaqueteTuristico paqueteTuristico) {
        try {
            List<Destino> destinosSeleccionados = paqueteTuristico.getDestinos(); // Obtener todos los destinos asociados al paquete

            String destinosString = destinosSeleccionados.stream()
                    .map(Destino::getNombre)
                    .collect(Collectors.joining(","));

            String linea =
                    paqueteTuristico.getNombre() + ";" +
                            paqueteTuristico.getDuracion() + ";" +
                            paqueteTuristico.getServiciosAdicionales() + ";" +
                            paqueteTuristico.getPrecio() + ";" +
                            paqueteTuristico.getCupoMaxPersona() + ";" +
                            paqueteTuristico.getFechaDisponibles() + ";" +
                            destinosString; // Agrega la lista de destinos

            ArchivoUtils.escribirArchivoBufferedWriter(RUTA_ARCHIVO_PAQUETES, List.of(linea), true);
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }
    public static void escribirReserva(Reserva reserva) {
        try {
            String linea = reserva.getFechaSolicitud() + ";" +
                    reserva.getFechaPlanificadaViaje() + ";" +
                    reserva.getClienteInvolucrado() + ";" +
                    reserva.getCantidadPersonaViejan() + ";" +
                    reserva.getPaqueteTuristicoSeleccionado() + ";" +
                    reserva.getEstandoReserva() + ";" +
                    reserva.getGuiaTutistico();
            ArchivoUtils.escribirArchivoBufferedWriter(RUTA_ARCHIVO_RESERVAS, List.of(linea), true);
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }




    //----------------------leer---------------------------//

    public static ArrayList<Persona> leerPersona() {
        ArrayList<Persona> personas = new ArrayList<>();
        try {
            ArrayList<String> lineas = ArchivoUtils.leerArchivoScanner(RUTA_ARCHIVO_GUIAS);
            for (String linea : lineas) {
                String[] partes = linea.split(";");

                // Verifica si hay suficientes partes
                if (partes.length >= 6) {
                    Lenguajes lenguaje = Lenguajes.obtenerNombreLenguajes(partes[5]);
                    personas.add(new GuiaTuristico(
                            partes[0],
                            partes[1],
                            partes[2],
                            partes[3],
                            Integer.parseInt(partes[4]),
                            lenguaje));
                } else {
                    // Imprime un mensaje de error si no hay suficientes partes
                    System.err.println("Error: La línea no tiene suficientes partes para crear una instancia de GuiaTuristico");
                }
            }
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
        return personas;
    }

    public static ArrayList<Destino> leerDestinos() {
        ArrayList<Destino> destinos = new ArrayList<>();
        try {
            ArrayList<String> lineas = ArchivoUtils.leerArchivoScanner(RUTA_ARCHIVO_DESTINO);
            for (String linea : lineas) {
                String[] partes = linea.split(";");

                // Imprimir información de depuración
                System.out.println("Debug - Linea: " + linea + ", Partes: " + Arrays.toString(partes));

                // Verifica si hay suficientes partes
                if (partes.length >= 4) {
                    String imagePath = partes[3].substring(1, partes[3].length() - 1);
                    ArrayList<String> imagenes = new ArrayList<>(Collections.singletonList(imagePath));
                    destinos.add(new Destino(
                            partes[0],
                            Ciudades.obtenerNombreCiudades(partes[1]),
                            partes[4],
                            imagenes,
                            Clima.obtenerNombreClima(partes[2])));
                } else {
                    // Imprime un mensaje de error si no hay suficientes partes
                    System.err.println("Error: La línea no tiene suficientes partes para crear una instancia de Destino");
                }
            }
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
        return destinos;
    }

    public static ArrayList<PaqueteTuristico> leerPaqueteTuristicos() {
        ArrayList<PaqueteTuristico> paqueteTuristico = new ArrayList<>();
        try {
            ArrayList<String> lineas = ArchivoUtils.leerArchivoScanner(RUTA_ARCHIVO_PAQUETES);
            for (String linea : lineas) {
                String[] partes = linea.split(";");
                ArrayList<String> serviciosAdicionales = new ArrayList<>(Collections.singletonList(partes[2]));
                paqueteTuristico.add(new PaqueteTuristico(
                        partes[0],
                        Integer.parseInt(partes[1]),
                        serviciosAdicionales,
                        Double.parseDouble(partes[3]),
                        Integer.parseInt(partes[4]),
                        LocalDateTime.parse(partes[5])));
            }
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
        return paqueteTuristico;
    }
    public static void actualizarReserva(Reserva reservaActualizada) {
        try {
            // Leer todas las reservas actuales desde el archivo
            List<String> lineasReservas = ArchivoUtils.leerArchivoBufferedReader(RUTA_ARCHIVO_RESERVAS);

            // Crear una lista para almacenar las reservas actualizadas
            List<String> nuevasLineasReservas = new ArrayList<>();

            // Iterar sobre las líneas del archivo y actualizar la reserva específica
            for (String linea : lineasReservas) {
                String[] partes = linea.split(";");
                if (partes.length == 7) {
                    LocalDateTime fechaSolicitud = LocalDateTime.parse(partes[0]);
                    LocalDateTime fechaPlanificadaViaje = LocalDateTime.parse(partes[1]);
                    String clienteInvolucrado = partes[2];
                    int cantidadPersonaViejan = Integer.parseInt(partes[3]);
                    String paqueteTuristicoSeleccionado = partes[4];
                    EstadoReserva estadoReserva = EstadoReserva.valueOf(partes[5]);
                    String guiaTuristico = partes[6];

                    // Verificar si esta es la reserva que estamos actualizando
                    if (esLaReservaQueEstamosBuscando(reservaActualizada, fechaSolicitud, fechaPlanificadaViaje,
                            clienteInvolucrado, cantidadPersonaViejan, paqueteTuristicoSeleccionado, estadoReserva, guiaTuristico)) {
                        // Actualizar la reserva con los nuevos datos
                        String nuevaLinea = reservaActualizada.getFechaSolicitud() + ";" +
                                reservaActualizada.getFechaPlanificadaViaje() + ";" +
                                reservaActualizada.getClienteInvolucrado() + ";" +
                                reservaActualizada.getCantidadPersonaViejan() + ";" +
                                reservaActualizada.getPaqueteTuristicoSeleccionado() + ";" +
                                reservaActualizada.getEstandoReserva() + ";" +
                                reservaActualizada.getGuiaTutistico();

                        nuevasLineasReservas.add(nuevaLinea);
                    } else {
                        // Mantener la línea original si no es la reserva que estamos actualizando
                        nuevasLineasReservas.add(linea);
                    }
                }
            }

            // Escribir las reservas actualizadas en el archivo
            ArchivoUtils.escribirArchivoBufferedWriter(RUTA_ARCHIVO_RESERVAS, nuevasLineasReservas, false);

        } catch (IOException e) {
            e.printStackTrace();
            // Manejar el error según tus necesidades (mostrar mensaje, lanzar excepción, etc.)
        }
    }

    // Método auxiliar para verificar si una línea del archivo corresponde a la reserva que estamos actualizando
    private static boolean esLaReservaQueEstamosBuscando(Reserva reservaActualizada, LocalDateTime fechaSolicitud,
                                                         LocalDateTime fechaPlanificadaViaje, String clienteInvolucrado,
                                                         int cantidadPersonaViejan, String paqueteTuristicoSeleccionado,
                                                         EstadoReserva estadoReserva, String guiaTuristico) {
        return reservaActualizada.getFechaSolicitud().equals(fechaSolicitud) &&
                reservaActualizada.getFechaPlanificadaViaje().equals(fechaPlanificadaViaje) &&
                reservaActualizada.getClienteInvolucrado().equals(clienteInvolucrado) &&
                reservaActualizada.getCantidadPersonaViejan() == cantidadPersonaViejan &&
                reservaActualizada.getPaqueteTuristicoSeleccionado().equals(paqueteTuristicoSeleccionado) &&
                reservaActualizada.getEstandoReserva() == estadoReserva &&
                reservaActualizada.getGuiaTutistico().equals(guiaTuristico);
    }

}