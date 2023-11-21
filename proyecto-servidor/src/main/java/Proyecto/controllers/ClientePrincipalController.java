package Proyecto.controllers;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import Proyecto.enums.Ciudades;
import Proyecto.enums.Clima;
import Proyecto.enums.EstadoReserva;
import Proyecto.enums.Lenguajes;
import Proyecto.model.*;
import Proyecto.utils.ArchivoUtils;
import Proyecto.utils.Persistencia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;


public class ClientePrincipalController implements Initializable {


    String rutaArchivo = "src/main/resources/persistencia/clientes.txt";
    String rutaArchivoPq = "src/main/resources/persistencia/paquetes.txt";
    public AnchorPane misViajesForm;
    private Cliente clienteAutenticado;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizarCliente;

    @FXML
    private Button btnAgregarGuiaRes;

    @FXML
    private Button btnBuscarpq;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnConfirmarReserva;

    @FXML
    private Button btnEstadistica;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnPaquetes;

    @FXML
    private Button btnPerfii;

    @FXML
    private Button btnReservar;

    @FXML
    private Button btnReservas;
    @FXML
    private Button btnCancelarReservaCliente;

    @FXML
    private TextField txtClienteRes;


    @FXML
    private Button btnVerImagenes;
    @FXML
    private ComboBox<Ciudades> cbxCiudad;

    @FXML
    private ComboBox<Clima> cbxClima;

    @FXML
    private ComboBox<String> cbxDestino;

    @FXML
    private ComboBox<?> cbxPaquete;

    @FXML
    private ComboBox<String> cbxPrecio;

    @FXML
    private TableColumn<PaqueteTuristico, Integer> colCupo;

    @FXML
    private TableColumn<PaqueteTuristico, Integer> colDuracion;

    @FXML
    private TableColumn<PaqueteTuristico, LocalDateTime> colFechaDisponible;

    @FXML
    private TableColumn<PaqueteTuristico, String> colNombre;

    @FXML
    private TableColumn<PaqueteTuristico, Double> colPrecio;

    @FXML
    private TableColumn<PaqueteTuristico, String> colServicioAdicional;
    @FXML
    private TableColumn<PaqueteTuristico,String> colDestinosCliente;
    @FXML
    private TableView<Reserva> tblReservas;
    @FXML
    private TableColumn<Reserva, EstadoReserva> colEstadRes;
    @FXML
    private TableColumn<Reserva, String> colClienteReser;
    @FXML
    private TableColumn<Reserva, String> colFechaSoliRes;

    @FXML
    private TableColumn<Reserva, String> colFechaPlaniRes;

    @FXML
    private TableColumn<Reserva, String> colCantiPersonRes;

    @FXML
    private TableColumn<Reserva, String> colGuiaRes;
    @FXML
    private TableColumn<Reserva, String> colPqRes;







    @FXML
    private TableColumn<GuiaTuristico, String> colCorreoGuiaRes;

    @FXML
    private TableColumn<GuiaTuristico, Integer> colExperienciaRes;
    @FXML
    private TableColumn<GuiaTuristico, String> colLenguajeRes;
    @FXML
    private TableColumn<GuiaTuristico, String> colNombreGuiaRes;
    private ObservableList<PaqueteTuristico> paqueteTuristicos = FXCollections.observableArrayList();
    private final ObservableList<GuiaTuristico> listaGuiasTuristicos = FXCollections.observableArrayList();
    private ObservableList<Reserva> listaReservas = FXCollections.observableArrayList();



    @FXML
    private AnchorPane estadisticaForm;

    @FXML
    private ImageView imagenView;

    @FXML
    private ImageView imgViewDestino1;

    @FXML
    private ImageView imgViewDestino2;

    @FXML
    private ImageView imgViewDestino3;
    @FXML
    private Label lblResCanPersonas;

    @FXML
    private Label lblResCliente;

    @FXML
    private Label lblResFechPLanificada;

    @FXML
    private Label lblResFechaSolicitud;

    @FXML
    private Label lblResPqTuristico;
    @FXML
    private Label lblGuiaSeleccionado;
    @FXML
    private TextField txtFechaPlanificada;

    @FXML
    private TextField txtFechaSolicitud;

    @FXML
    private TextField txtGuia;
    @FXML
    private TextField txtPqTuristico;
    @FXML
    private TextField txtCantPersonas;
    @FXML
    private DatePicker ldFechaIda;

    @FXML
    private DatePicker ldxFechaRegreso;

    @FXML
    private AnchorPane paquetesForm;

    @FXML
    private AnchorPane perfilForm;

    @FXML
    private AnchorPane reservaForm;

    @FXML
    private TableView<PaqueteTuristico> tblPqCliente;

    @FXML
    private TextField txtCorreoPerfil;

    @FXML
    private TextField txtDireccionPerfil;

    @FXML
    private TextField txtIdPerfil;

    @FXML
    private TextField txtNombrePerfil;

    @FXML
    private TextField txtTelefonoPerfil;

    @FXML
    private TableView<GuiaTuristico> tblListadoGuias;

    private ObservableList<PaqueteTuristico> listaPaqueteTuristicos = FXCollections.observableArrayList();

    @FXML
    void filtDestino(ActionEvent event) {
        actualizarTabla();

    }

    @FXML
    void filtFecha(ActionEvent event) {
        actualizarTabla();

    }

    @FXML
    void filtPrecio(ActionEvent event) {
        actualizarTabla();

    }


    @FXML
    void ActualizarPerfilCliente(ActionEvent event) {


        String nuevoNombre = txtNombrePerfil.getText();
        String nuevoCorreo = txtCorreoPerfil.getText();
        String nuevaDireccion = txtDireccionPerfil.getText();
        String nuevoTelefono = txtTelefonoPerfil.getText();

        //obtienes los nuevos datos y actualizas el objeto Cliente.
        clienteAutenticado.setNombreCompleto(nuevoNombre);
        clienteAutenticado.setCorreo(nuevoCorreo);
        clienteAutenticado.setDireccionResidencia(nuevaDireccion);
        clienteAutenticado.setTelefono(nuevoTelefono);


        //  actualiza los campos en la interfaz.
        actualizarCampos();

        mostrarAlerta("Cliente Actualizado", "Los datos del cliente se han actualizado con éxito.", Alert.AlertType.INFORMATION);
        String nuevaLinea = String.format("%s,%s,%s,%s,%s,%s",
                clienteAutenticado.getIdentificacion(),
                clienteAutenticado.getPassword(),
                clienteAutenticado.getNombreCompleto(),
                clienteAutenticado.getCorreo(),
                clienteAutenticado.getTelefono(),
                clienteAutenticado.getDireccionResidencia());

        actualizarDatosEnArchivo(nuevaLinea);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void actualizarCampos() {
        txtIdPerfil.setText(clienteAutenticado.getIdentificacion());
        txtNombrePerfil.setText(clienteAutenticado.getNombreCompleto());
        txtCorreoPerfil.setText(clienteAutenticado.getCorreo());
        txtDireccionPerfil.setText(clienteAutenticado.getDireccionResidencia());
        txtTelefonoPerfil.setText(clienteAutenticado.getTelefono());
    }
    @FXML
    void CancelarReserva(ActionEvent event) {
        Reserva reservaSeleccionada = tblReservas.getSelectionModel().getSelectedItem();


        if (reservaSeleccionada != null) {
            // Cambia el estado de la reserva a cancelado
            reservaSeleccionada.setEstandoReserva(EstadoReserva.CANCELADA);

            // Aquí puedes actualizar visualmente la TableView (opcional)
            tblReservas.refresh();

            // Luego, guarda los cambios en el archivo Reservas.txt
            actualizarArchivoReservas(listaReservas);
        }

    }


    @FXML
    void NuevopqCLiente(ActionEvent event) {
        cbxCiudad.setValue(null);
        cbxClima.setValue(null);
        cbxDestino.setValue(null);
        cbxPrecio.setValue(null);
        ldFechaIda.setValue(null);

        // Luego, actualiza la tabla
        actualizarTabla();


    }

    @FXML
    void buscarPaqueteCliente(ActionEvent event) {

    }

    @FXML
    void cerrarSesionClienteAction(ActionEvent event) {

    }

    @FXML
    void mostrarVenCliente(ActionEvent event) {


        if (event.getSource() == btnPerfii) {
            perfilForm.setVisible(true);
            reservaForm.setVisible(false);
            paquetesForm.setVisible(false);
            misViajesForm.setVisible(false);
        } else if (event.getSource() == btnPaquetes) {
            perfilForm.setVisible(false);
            reservaForm.setVisible(false);
            paquetesForm.setVisible(true);
            misViajesForm.setVisible(false);
        } else if (event.getSource() == btnReservas) {
            perfilForm.setVisible(true);
            reservaForm.setVisible(false);
            paquetesForm.setVisible(false);
            misViajesForm.setVisible(false);
        }


    }



    @FXML
    void reservarPq(ActionEvent event) {
        perfilForm.setVisible(false);
        reservaForm.setVisible(true);
        paquetesForm.setVisible(false);
        misViajesForm.setVisible(false);

        // Llama al método seleccionarFila para obtener la fila seleccionada
        seleccionarFila();

        // Ahora, la información de la fila seleccionada debería estar disponible en tu controlador
        PaqueteTuristico paqueteSeleccionado = paqueteSeleccionadoEnReservasForm;

        if (paqueteSeleccionado != null) {
            LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaActualStr = fechaActual.format(formatter);

            // Mostrar los datos en las etiquetas de reservasForm
            txtClienteRes.setText(paqueteSeleccionado.getNombre());
            txtFechaPlanificada.setText(String.valueOf(paqueteSeleccionado.getFechaDisponibles()));
            txtCantPersonas.setText(String.valueOf(paqueteSeleccionado.getCupoMaxPersona()));
            txtFechaSolicitud.setText(fechaActualStr);


            // Construye la cadena con destinos, precio y servicios adicionales
            txtPqTuristico.setStyle("-fx-font-size: 14; -fx-line-spacing: 0.5;");

            StringBuilder infoPaquete = new StringBuilder();
            infoPaquete.append("Destinos:\n");

            ArrayList<Destino> destinos = paqueteSeleccionado.getDestinos();
            for (Destino destino : destinos) {
                infoPaquete.append("- ").append(destino).append("\n");
            }

            infoPaquete.append("\nPrecio: $").append(paqueteSeleccionado.getPrecio()).append("\n");

            List<String> serviciosAdicionales = paqueteSeleccionado.getServiciosAdicionales();
            if (!serviciosAdicionales.isEmpty()) {
                infoPaquete.append("\nServicios Adicionales:\n");
                for (String servicio : serviciosAdicionales) {
                    infoPaquete.append("- ").append(servicio).append("\n");
                }
            }

            txtPqTuristico.setText(infoPaquete.toString().trim());


            // Actualiza las demás etiquetas con los datos necesarios
        }
    }

    // Variable para almacenar el paquete seleccionado
    private PaqueteTuristico paqueteSeleccionadoEnReservasForm;

    @FXML
    void seleccionarFila() {
        // Obtén la fila seleccionada
        PaqueteTuristico paqueteSeleccionado = tblPqCliente.getSelectionModel().getSelectedItem();

        if (paqueteSeleccionado != null) {
            // Guarda la información de la fila seleccionada en la variable
            paqueteSeleccionadoEnReservasForm = paqueteSeleccionado;
        }
    }





    @FXML
    void verImagenesPq(ActionEvent event) {

    }

    private void actualizarDatosEnArchivo(String nuevaLinea) {
        try {
            List<String> datosActuales = ArchivoUtils.leerArchivoBufferedReader(rutaArchivo);

            // Encuentra y reemplaza la línea existente con el nuevo dato.
            // Supongamos que cada línea es única y se basa en la identificación del cliente.
            for (int i = 0; i < datosActuales.size(); i++) {
                if (datosActuales.get(i).contains(clienteAutenticado.getIdentificacion())) {
                    datosActuales.set(i, nuevaLinea);
                    break;  // Se encontró y actualizó la línea, sale del bucle.
                }
            }

            // Guarda los datos actualizados en el archivo.
            ArchivoUtils.escribirArchivoBufferedWriter(rutaArchivo, datosActuales, false);

            // Muestra una alerta o realiza otras acciones según sea necesario.
            mostrarAlerta("Actualización Exitosa", "Datos actualizados en el archivo.", Alert.AlertType.INFORMATION);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error de Actualización", "Hubo un error al actualizar el archivo.", Alert.AlertType.ERROR);
        }
    }


    public void initData(Cliente clienteAutenticado) {
        this.clienteAutenticado = clienteAutenticado;

        try {
            // Lee los datos del archivo y busca la línea del cliente actual
            List<String> lineasClientes = ArchivoUtils.leerArchivoBufferedReader(rutaArchivo);
            for (String linea : lineasClientes) {
                String[] datosCliente = linea.split(",");

                // Supongamos que el primer campo es la identificación del cliente
                if (datosCliente.length > 0 && datosCliente[0].equals(clienteAutenticado.getIdentificacion())) {
                    // Establece los datos en los campos correspondientes
                    txtIdPerfil.setText(datosCliente[0]);
                    txtNombrePerfil.setText(datosCliente[2]);
                    txtCorreoPerfil.setText(datosCliente[3]);
                    txtDireccionPerfil.setText(datosCliente[5]);
                    txtTelefonoPerfil.setText(datosCliente[4]);
                    break; // Rompe el bucle una vez que se ha encontrado y configurado el cliente
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error de Lectura", "Hubo un error al leer el archivo.", Alert.AlertType.ERROR);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cbxCiudad.getItems().setAll(Ciudades.values());
        cbxClima.getItems().setAll(Clima.values());
        List<Destino> listaDestinos = ArchivoUtils.leerDestinosDesdeArchivo("src/main/resources/persistencia/destinos.txt");
        tblReservas.setItems(FXCollections.observableArrayList());

        System.out.println(listaDestinos);

        // Llenar el ComboBox con los nombres de los destinos
        for (Destino destino : listaDestinos) {
            cbxDestino.getItems().add(destino.getNombre());
        }


        cbxDestino.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Acciones cuando se selecciona un destino en el ComboBox
                System.out.println("Destino seleccionado: " + newValue);
            }
        });
        ObservableList<String> precios = FXCollections.observableArrayList(
                "menos de 500.000",
                "500.000 a 1.000.000",
                "1.000.000 a 2.000.000",
                "mas de 2.000.000"
        );

        // Asignar la lista al ComboBox
        cbxPrecio.setItems(precios);



        // Puedes agregar un listener para manejar eventos de selección si es necesario
        cbxPrecio.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Seleccionaste: " + newValue);
        });
        // Configura las columnas de la TableView
        colCupo.setCellValueFactory(new PropertyValueFactory<>("cupoMaxPersona"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        colFechaDisponible.setCellValueFactory(new PropertyValueFactory<>("fechaDisponibles"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colServicioAdicional.setCellValueFactory(new PropertyValueFactory<>("serviciosAdicionales"));
        colDestinosCliente.setCellValueFactory(new PropertyValueFactory<>("destinos"));
        System.out.println("Destinos:"+colDestinosCliente);



        // Lee los datos de los paquetes turísticos desde el archivo
        cargarPaquetesDesdeArchivo("src/main/resources/persistencia/paquetes.txt");

        // Asigna la lista a la TableView
        tblPqCliente.setItems(listaPaqueteTuristicos);
        System.out.println("Lista de paquetes turísticos: " + listaPaqueteTuristicos);
    //---------------Guias---------------------
        colNombreGuiaRes.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colCorreoGuiaRes.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colExperienciaRes.setCellValueFactory(new PropertyValueFactory<>("experiencia"));
        colLenguajeRes.setCellValueFactory(new PropertyValueFactory<>("lenguaje"));

        // Llama al método para cargar los guías desde el archivo
        cargarGuiasDesdeArchivo("src/main/resources/persistencia/guias.txt");
        //-----------------reservas-----------------------------------------
        if (colClienteReser != null) {
            colClienteReser.setCellValueFactory(new PropertyValueFactory<>("clienteInvolucrado"));
        } else {
            System.err.println("La columna colClienteReser es nula. Verifica la asignación @FXML y la inicialización.");
        }

        if (colFechaSoliRes != null) {
            colFechaSoliRes.setCellValueFactory(new PropertyValueFactory<>("fechaSolicitud"));
        } else {
            System.err.println("La columna colFechaSoliRes es nula. Verifica la asignación @FXML y la inicialización.");
        }

        if (colFechaPlaniRes != null) {
            colFechaPlaniRes.setCellValueFactory(new PropertyValueFactory<>("fechaPlanificadaViaje"));
        } else {
            System.err.println("La columna colFechaPlaniRes es nula. Verifica la asignación @FXML y la inicialización.");
        }

        if (colCantiPersonRes != null) {
            colCantiPersonRes.setCellValueFactory(new PropertyValueFactory<>("cantidadPersonaViejan"));
        } else {
            System.err.println("La columna colCantiPersonRes es nula. Verifica la asignación @FXML y la inicialización.");
        }

        if (colGuiaRes != null) {
            colGuiaRes.setCellValueFactory(new PropertyValueFactory<>("guiaTutistico"));
        } else {
            System.err.println("La columna colGuiaRes es nula. Verifica la asignación @FXML y la inicialización.");
        }

        if (colPqRes != null) {
            colPqRes.setCellValueFactory(new PropertyValueFactory<>("paqueteTuristicoSeleccionado"));
        } else {
            System.err.println("La columna colPqRes es nula. Verifica la asignación @FXML y la inicialización.");
        }

        if (colEstadRes != null) {
            colEstadRes.setCellValueFactory(new PropertyValueFactory<>("estandoReserva"));
        } else {
            System.err.println("La columna colEstadRes es nula. Verifica la asignación @FXML y la inicialización.");
        }

        // Ahora carga las reservas
        cargarReservasDesdeArchivo("src/main/resources/persistencia/reservas.txt");
    }

    private void cargarReservasDesdeArchivo(String rutaArchivo) {
        try {
            List<String> lineasReservas = ArchivoUtils.leerArchivoBufferedReader(rutaArchivo);

            for (String linea : lineasReservas) {
                String[] partes = linea.split(";");
                if (partes.length == 7) {
                    LocalDateTime fechaSolicitud = LocalDateTime.parse(partes[0]);
                    LocalDateTime fechaPlanificadaViaje = LocalDateTime.parse(partes[1]);
                    String clienteInvolucrado = partes[2];
                    int cantidadPersonaViejan = Integer.parseInt(partes[3]);
                    String paqueteTuristicoSeleccionado = partes[4];
                    EstadoReserva estandoReserva = EstadoReserva.valueOf(partes[5]);
                    String guiaTutistico = partes[6];

                    Reserva reserva = new Reserva();
                    reserva.setFechaSolicitud(fechaSolicitud);
                    reserva.setFechaPlanificadaViaje(fechaPlanificadaViaje);
                    reserva.setClienteInvolucrado(clienteInvolucrado);
                    reserva.setCantidadPersonaViejan(cantidadPersonaViejan);
                    reserva.setPaqueteTuristicoSeleccionado(paqueteTuristicoSeleccionado);
                    reserva.setEstandoReserva(estandoReserva);
                    reserva.setGuiaTutistico(guiaTutistico);

                    // Agrega la reserva a la lista
                    listaReservas.add(reserva);
                }
            }

            // Agrega las reservas a la TableView
            tblReservas.setItems(listaReservas);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error de Lectura", "Hubo un error al leer el archivo de reservas.", Alert.AlertType.ERROR);
        }

    }


    private void cargarGuiasDesdeArchivo(String rutaArchivo) {
        try {
            List<String> lineasGuias = ArchivoUtils.leerArchivoBufferedReader(rutaArchivo); // Asegúrate de tener este método definido

            for (String linea : lineasGuias) {
                // Asumiendo que tus guías están separados por comas o algún otro delimitador
                String[] partes = linea.split(";");

                // Asegúrate de que haya suficientes partes para construir una guía
                if (partes.length >= 6) {
                    String nombre = partes[0].trim();
                    String identificacion = partes[1].trim();
                    String correo = partes[2].trim();
                    String password = partes[3].trim();
                    int experiencia = Integer.parseInt(partes[4].trim());
                    String lenguaje = partes[5].trim();

                    // Crea un nuevo GuiaTuristico y agrégalo a la lista
                    GuiaTuristico guia = new GuiaTuristico(nombre, identificacion, correo, password, experiencia, Lenguajes.valueOf(lenguaje));
                    listaGuiasTuristicos.add(guia);
                }
            }

            // Agrega los guías a la TableView
            tblListadoGuias.setItems(listaGuiasTuristicos);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error de Lectura", "Hubo un error al leer el archivo de guías.", Alert.AlertType.ERROR);
        }
    }




    private void cargarPaquetesDesdeArchivo(String rutaArchivoPq) {
        try {
            List<String> lineasPaquetes = ArchivoUtils.leerArchivoBufferedReader(rutaArchivoPq);

            for (String linea : lineasPaquetes) {
                // Asumiendo que tus paquetes están separados por comas o algún otro delimitador
                String[] partes = linea.split(";");

                for (int i = 0; i < partes.length; i++) {
                    System.out.println("Parte " + i + ": " + partes[i]);
                }


                // Asegúrate de que haya suficientes partes para construir un PaqueteTuristico
                if (partes.length >= 7) {
                    int cupoMaxPersona = Integer.parseInt(partes[4]);
                    int duracion = Integer.parseInt(partes[1]);
                    LocalDateTime fechaDisponibles = LocalDateTime.parse(partes[5]); // Ajusta el formato según tu necesidad
                    String nombre = partes[0];
                    double precio = Double.parseDouble(partes[3]);
                    ArrayList<String> serviciosAdicionales = new ArrayList<>(Arrays.asList(partes[2].split(";")));
                    ArrayList<String> destinos = new ArrayList<>(Arrays.asList(partes[6].split(";")));



                    // Crea un nuevo PaqueteTuristico y agrégalo a la lista
                    PaqueteTuristico paquete = new PaqueteTuristico(nombre, duracion, serviciosAdicionales, precio, cupoMaxPersona, fechaDisponibles);




                    listaPaqueteTuristicos.add(paquete);
                    tblPqCliente.refresh();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error de Lectura", "Hubo un error al leer el archivo de paquetes.", Alert.AlertType.ERROR);
        }

    }

    @FXML
    private void actualizarTabla() {
        // Crea una lista filtrada que envuelva tu lista original
        FilteredList<PaqueteTuristico> listaFiltrada = new FilteredList<>(listaPaqueteTuristicos, p -> true);

        // Aplica filtros basados en las selecciones de ComboBox
        listaFiltrada.setPredicate(paquete -> {
//            boolean ciudadCoincide = cbxCiudad.getValue() == null || paquete.getCiudad().equals(cbxCiudad.getValue());
//            boolean climaCoincide = cbxClima.getValue() == null || paquete.getClima().equals(cbxClima.getValue());
            boolean destinoCoincide = cbxDestino.getValue() == null || paquete.getNombre().equals(cbxDestino.getValue());
            boolean precioCoincide = cbxPrecio.getValue() == null || cumpleCriterioPrecio(paquete, cbxPrecio.getValue());
            boolean fechaCoincide = ldFechaIda.getValue() == null || cumpleCriterioFechaIda(paquete, ldFechaIda.getValue().atStartOfDay());

            return fechaCoincide && destinoCoincide && precioCoincide;
        });

        // Envuelve la lista filtrada en una SortedList
        SortedList<PaqueteTuristico> listaOrdenada = new SortedList<>(listaFiltrada);

        // Vincula el comparador de SortedList al comparador de TableView
        listaOrdenada.comparatorProperty().bind(tblPqCliente.comparatorProperty());

        // Establece los elementos de TableView como la lista ordenada y filtrada
        tblPqCliente.setItems(listaOrdenada);
    }

    private boolean cumpleCriterioPrecio(PaqueteTuristico paquete, String criterioPrecio) {
        if (criterioPrecio == null) {
            return true; // No se aplica el filtro de precio
        }

        switch (criterioPrecio) {
            case "menos de 500.000":
                return paquete.getPrecio() < 500000;
            case "500.000 a 1.000.000":
                return paquete.getPrecio() >= 500000 && paquete.getPrecio() <= 1000000;
            case "1.000.000 a 2.000.000":
                return paquete.getPrecio() > 1000000 && paquete.getPrecio() <= 2000000;
            case "mas de 2.000.000":
                return paquete.getPrecio() > 2000000;
            default:
                return false; // Caso predeterminado si el criterio no coincide con ninguno
        }


    }
    private boolean cumpleCriterioFechaIda(PaqueteTuristico paquete, LocalDateTime fechaIda) {
        if (fechaIda == null) {
            return true; // Si no se selecciona ninguna fecha de ida, entonces cumple el criterio
        }

        LocalDateTime fechaIdaPaquete = paquete.getFechaDisponibles();

        // Comparar con la fecha de ida del paquete
        return fechaIdaPaquete != null && fechaIdaPaquete.toLocalDate().isEqual(ChronoLocalDate.from(fechaIda));
    }

    public void confirmarReserva(ActionEvent actionEvent) {
        perfilForm.setVisible(false);
        reservaForm.setVisible(false);
        paquetesForm.setVisible(false);
        misViajesForm.setVisible(true);
        // Obtener la información de los campos de texto y etiquetas
        String clienteInvolucrado = txtClienteRes.getText();
        String paqueteTuristicoSeleccionado = paqueteSeleccionadoEnReservasForm.getNombre();
        LocalDateTime fechaSolicitud = LocalDateTime.now();
        LocalDateTime fechaPlanificadaViaje = paqueteSeleccionadoEnReservasForm.getFechaDisponibles(); // O ajusta según sea necesario
        int cantidadPersonaViejan = Integer.parseInt(txtCantPersonas.getText());

        // Obtener el guía turístico seleccionado en el formulario
        String guiaTuristico = txtGuia.getText(); // Puedes ajustar esto según la lógica de tu aplicación

        // Crear una nueva instancia de Reserva
        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setFechaSolicitud(fechaSolicitud);
        nuevaReserva.setFechaPlanificadaViaje(fechaPlanificadaViaje);
        nuevaReserva.setClienteInvolucrado(clienteInvolucrado);
        nuevaReserva.setCantidadPersonaViejan(cantidadPersonaViejan);
        nuevaReserva.setPaqueteTuristicoSeleccionado(paqueteTuristicoSeleccionado);
        nuevaReserva.setGuiaTutistico(guiaTuristico); // Establecer el guía turístico obtenido del formulario
        nuevaReserva.setEstandoReserva(EstadoReserva.COMFIRMADA);
        // Establecer otros atributos según sea necesario

        // Llamar al método para escribir la reserva en el archivo
        Persistencia.escribirReserva(nuevaReserva);
        mostrarMensaje("Notificación", "La guia turistico", "se ha creado con éxitosamente", Alert.AlertType.INFORMATION);

    }

    public void agregarGuiaRes(ActionEvent actionEvent) {
        GuiaTuristico guiaSeleccionado = tblListadoGuias.getSelectionModel().getSelectedItem();
        if (guiaSeleccionado != null) {
            // Lógica para agregar el guía al Label
            txtGuia.setText(guiaSeleccionado.getNombreCompleto());
            txtGuia.setFont(new Font("Arial", 18));
        } else {
            // Maneja el caso en el que no se haya seleccionado ningún guía
            txtGuia.setText("Ningún guía seleccionado");
        }
    }

    public void seleccionarFila(MouseEvent mouseEvent) {
        // Obtén la fila seleccionada
        PaqueteTuristico paqueteSeleccionado = tblPqCliente.getSelectionModel().getSelectedItem();

        if (paqueteSeleccionado != null) {
            // Realiza acciones con la fila seleccionada
            System.out.println("Paquete seleccionado: " + paqueteSeleccionado.getNombre());
        }

    }
    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    public void filtrarReservasPasadas(ActionEvent actionEvent) {
    }

    public void filtrarReservasFuturas(ActionEvent actionEvent) {
    }

    //------------------------ reservaForm-------------------------------------------------------------------------

        private void actualizarArchivoReservas(List<Reserva> reservas) {
            try {
                // Abre el archivo para escritura
                BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/persistencia/reservas.txt"));

                // Recorre la lista de reservas y escribe cada reserva en una línea del archivo
                for (Reserva reserva : reservas) {
                    String linea = String.format(
                            "%s;%s;%s;%d;%s;%s;%s",
                            reserva.getFechaSolicitud(),
                            reserva.getFechaPlanificadaViaje(),
                            reserva.getClienteInvolucrado(),
                            reserva.getCantidadPersonaViejan(),
                            reserva.getPaqueteTuristicoSeleccionado(),
                            reserva.getEstandoReserva(),
                            reserva.getGuiaTutistico()
                    );

                    // Escribe la línea en el archivo
                    writer.write(linea);
                    writer.newLine();  // Agrega un salto de línea después de cada reserva
                }

                // Cierra el archivo
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta("Error de Escritura", "Hubo un error al escribir el archivo de reservas.", Alert.AlertType.ERROR);
            }
        }





    private String convertirReservaAString(Reserva reserva) {
        // Convierte una reserva a una línea de texto
        return String.format("%s;%s;%s;%d;%s;%s;%s",
                reserva.getFechaSolicitud(),
                reserva.getFechaPlanificadaViaje(),
                reserva.getClienteInvolucrado(),
                reserva.getCantidadPersonaViejan(),
                reserva.getPaqueteTuristicoSeleccionado(),
                reserva.getEstandoReserva(),
                reserva.getGuiaTutistico());
    }

}







