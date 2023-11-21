package Proyecto.controllers;

import Proyecto.enums.Ciudades;
import Proyecto.enums.Clima;
import Proyecto.enums.Lenguajes;
import Proyecto.exceptions.*;
import Proyecto.model.*;

import Proyecto.utils.ArchivoUtils;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.scene.chart.BarChart;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class AdmPrincipalViewController {
    private final AgenciaViajes agenciaViajes = AgenciaViajes.getInstance();
    private ArrayList<String> imagenes = new ArrayList<>();
    //---------------------------------------------------------------------------//
    ObservableList<Destino> listaDestinoData = FXCollections.observableArrayList();
    Destino destinoSeleccionado;
    ObservableList<GuiaTuristico> listaGuiaTuristicoData = FXCollections.observableArrayList();
    GuiaTuristico guiaTuristicoSeleccionado;
    ObservableList<PaqueteTuristico> listaPaqueteTuristicoData = FXCollections.observableArrayList();
    PaqueteTuristico paqueteTuristicoSeleccionado;

    //--------------------------------------------------------------------------//
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAdmDestinoActualizar;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnAdmDestinoAgragar;

    @FXML
    private Button btnAdmDestinoEliminar;

    @FXML
    private Button btnAdmDestinoNuevo;

    @FXML
    private Button btnAdmDestinoSeleccionarImag;

    @FXML
    private Button btnAdmGuiaActualizar;

    @FXML
    private Button btnAdmGuiaAgregar;

    @FXML
    private Button btnAdmGuiaEliminar;

    @FXML
    private Button btnAdmGuiaNuevo;

    @FXML
    private Button btnAdmPaqueteActualizar;

    @FXML
    private Button btnAdmPaqueteAgregar;

    @FXML
    private Button btnAdmPaqueteEliminar;

    @FXML
    private Button btnAdmPaqueteNuevo;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnDestinos;

    @FXML
    private Button btnEstadistica;

    @FXML
    private Button btnGuia;

    @FXML
    private Button btnPaquete;

    @FXML
    private AnchorPane destinoForm;

    @FXML
    private AnchorPane estadisticaForm;

    @FXML
    private AnchorPane guiasForm;

    @FXML
    private AnchorPane paquetesForm;

    @FXML
    private ComboBox<String> comboxOpcionesCiudad;

    @FXML
    private ComboBox<String> comboxOpcionesClima;

    @FXML
    private ComboBox<String> comboxOpcionesLenjuajes;

    @FXML
    private TableColumn<Destino, String> clAdmDestinoCiudad;

    @FXML
    private TableColumn<Destino, String> clAdmDestinoClima;

    @FXML
    private TableColumn<Destino, String> clAdmDestinoDescripcion;

    @FXML
    private TableColumn<Destino, String> clAdmDestinoNombre;

    @FXML
    private TableColumn<GuiaTuristico, String> clAdmGuiasContrasenia;

    @FXML
    private TableColumn<GuiaTuristico, String> clAdmGuiasCorreoElectronico;

    @FXML
    private TableColumn<GuiaTuristico, String> clAdmGuiasExperiencia;

    @FXML
    private TableColumn<GuiaTuristico, String> clAdmGuiasIdentificacion;

    @FXML
    private TableColumn<GuiaTuristico, String> clAdmGuiasLenguajes;

    @FXML
    private TableColumn<GuiaTuristico, String> clAdmGuiasNombreCompleto;

    @FXML
    private TableColumn<PaqueteTuristico, Integer> clAdmPaqueteCupo;

    @FXML
    private TableColumn<PaqueteTuristico, Integer> clAdmPaqueteDuracion;

    @FXML
    private TableColumn<PaqueteTuristico, LocalDateTime> clAdmPaqueteFechaDisponibles;

    @FXML
    private TableColumn<PaqueteTuristico, String> clAdmPaqueteNombre;

    @FXML
    private TableColumn<PaqueteTuristico, Double> clAdmPaquetePrecio;

    @FXML
    private TableColumn<PaqueteTuristico, String> clAdmPaqueteServicioAdicional;

    @FXML
    private TableColumn<Destino, String> clAdmPaqueteDestinoNombre;


    @FXML
    private TableView<Destino> tableAdmPaqueteDestinos;

    @FXML
    private TableView<Destino> tableAdmDestino;

    @FXML
    private TableView<GuiaTuristico> tableAdmGuias;

    @FXML
    private TableView<PaqueteTuristico> tableAdmPaquete;

    @FXML
    private TextArea txtAdmDestinoDescripcion;

    @FXML
    private TextField txtAdmDestinoNombre;

    @FXML
    private ImageView imagenView;

    @FXML
    private PasswordField txtAdmGuiaContrasenia;

    @FXML
    private TextField txtAdmGuiaCorreoElectronico;

    @FXML
    private TextField txtAdmGuiaExperencia;

    @FXML
    private TextField txtAdmGuiaIdentificacion;

    @FXML
    private TextField txtAdmGuiaNombreCompleto;

    @FXML
    private TextField txtAdmPaqueteCupoMaxPersonas;

    @FXML
    private TextField txtAdmPaqueteDuracion;

    @FXML
    private TextField txtAdmPaqueteNombre;

    @FXML
    private TextField txtAdmPaquetePrecio;

    @FXML
    private TextField txtAdmPaqueteServicioAdicionales;

    @FXML
    private DatePicker dataAdmPaqueteFechaDisponible;

    @FXML
    private BarChart<String, Integer> destinosReservadoBuscarBarChart;

    @FXML
    private BarChart<?, ?> guiarMejorPuntuadosBarChart;

    @FXML
    private BarChart<?, ?> paqueteSuelenReservarBarChart;

    @FXML
    void SeleccionarImagAdmDestinoAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String rutaImagen = file.getAbsolutePath();
            imagenes.add(rutaImagen);
            Image imagen = new Image(file.toURI().toString());
            imagenView.setImage(imagen);
        }
    }

    @FXML
    void actualizarAdmDestinoAction(ActionEvent event) {
        actualizarDestinoAdm();
    }

    private void actualizarDestinoAdm() {
        String nombre = txtAdmDestinoNombre.getText();
        String descripcion = txtAdmDestinoDescripcion.getText();
        String valorClima = comboxOpcionesClima.getValue();
        String valorCiudad = comboxOpcionesCiudad.getValue();
        Clima clima = null;
        Ciudades ciudad = null;
        if (valorClima != null) {
            clima = Clima.obtenerNombreClima(valorClima);
        }
        if (valorCiudad != null) {
            ciudad = Ciudades.obtenerNombreCiudades(valorCiudad);
        }
        try {
            boolean esDestinoActualizado;
            if (destinoSeleccionado != null) {
                esDestinoActualizado = agenciaViajes.actualizarDestino(destinoSeleccionado.getNombre(), nombre, ciudad, descripcion, imagenes, clima);
                if (esDestinoActualizado) {
                    tableAdmDestino.refresh();
                    mostrarMensaje("Notificación", "El destino", "se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                    limpiarCamposDestino();
                    tableAdmDestino.getSelectionModel().select(null);
                }
            }
        } catch (CampoObligatorio | SeleccioneCargar | SeleccionarNoOpcion e) {
            mostrarMensaje("Notificación", "El destino no se ha actualizado", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    void actualizarAdmGuiaAction(ActionEvent event) {
        actualizarAdmGuia();
    }

    private void actualizarAdmGuia() {
        String nombreCompleto = txtAdmGuiaNombreCompleto.getText();
        String identificacion = txtAdmGuiaIdentificacion.getText();
        String correo = txtAdmGuiaCorreoElectronico.getText();
        String password = txtAdmGuiaContrasenia.getText();
        String valorLenguajes = comboxOpcionesLenjuajes.getValue();

        int experiencia = 0;
        if (!txtAdmGuiaExperencia.getText().isEmpty()) {
            experiencia = Integer.parseInt(txtAdmGuiaExperencia.getText());
        }
        Lenguajes lenguajes = null;
        if (valorLenguajes != null) {
            lenguajes = Lenguajes.obtenerNombreLenguajes(valorLenguajes);
        }
        boolean esGuiaTuristicoActualizado;
        if (guiaTuristicoSeleccionado != null) {
            try {
                esGuiaTuristicoActualizado = agenciaViajes.actualizarGuiaTuristico(guiaTuristicoSeleccionado.getIdentificacion(), nombreCompleto, identificacion, correo, password, experiencia, lenguajes);
                if (esGuiaTuristicoActualizado) {
                    tableAdmPaqueteDestinos.refresh();
                    mostrarMensaje("Notificación", "La guia turistico", "se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                    limpiarCamposGuias();
                    tableAdmPaqueteDestinos.getSelectionModel().select(null);
                }
            } catch (CampoNegativo | CampoObligatorio | SeleccionarNoOpcion e) {
                mostrarMensaje("Notificación", "La guia turistico no se ha actualizado", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void actualizarAdmPaqueteAction(ActionEvent event) {
        actualizarAdmPaquete();
    }

    private void actualizarAdmPaquete() {
        String nombre = txtAdmPaqueteNombre.getText();
        String serviciosAdicionalesTexto = txtAdmPaqueteServicioAdicionales.getText();
        ArrayList<String> serviciosAdicionales = new ArrayList<>(Arrays.asList(serviciosAdicionalesTexto));
        int duracion = 0;
        if (!txtAdmPaqueteDuracion.getText().isEmpty()) {
            duracion = Integer.parseInt(txtAdmPaqueteDuracion.getText());
        }
        int cupoMaxPersona = 0;
        if (!txtAdmPaqueteCupoMaxPersonas.getText().isEmpty()) {
            cupoMaxPersona = Integer.parseInt(txtAdmPaqueteCupoMaxPersonas.getText());
        }
        Double precio = 0.0;
        if (!txtAdmPaquetePrecio.getText().isEmpty()) {
            precio = Double.parseDouble(txtAdmPaquetePrecio.getText());
        }
        LocalDateTime fechaDisponibles = null;
        if (dataAdmPaqueteFechaDisponible.getValue() != null) {
            fechaDisponibles = dataAdmPaqueteFechaDisponible.getValue().atStartOfDay();
        }
        boolean esPaqueteTuristicoActualizado;
        if (paqueteTuristicoSeleccionado != null) {
            try {
                esPaqueteTuristicoActualizado = agenciaViajes.actualizarPaqueteTuristico(paqueteTuristicoSeleccionado.getNombre(), nombre, duracion, serviciosAdicionales, precio, cupoMaxPersona, fechaDisponibles);
                if (esPaqueteTuristicoActualizado) {
                    tableAdmPaquete.refresh();
                    mostrarMensaje("Notificación", "El paquete turistico", "se ha actualizado con éxito", Alert.AlertType.INFORMATION);
                    limpiarCamposPaqueteTuristico();
                    tableAdmPaquete.getSelectionModel().select(null);
                }
            } catch (CampoNegativo | CampoObligatorio | FechaException e) {
                mostrarMensaje("Notificación", "El paquete turistico no se ha actualizado", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void agregarAdmDestinoAction(ActionEvent event) {
        crearDestinoAdm();
    }

    private void crearDestinoAdm() {
        String nombre = txtAdmDestinoNombre.getText();
        String descripcion = txtAdmDestinoDescripcion.getText();
        String valorClima = comboxOpcionesClima.getValue();
        String valorCiudad = comboxOpcionesCiudad.getValue();
        Clima climas = null;
        Ciudades ciudades = null;
        if (valorClima != null) {
            climas = Clima.obtenerNombreClima(valorClima);
        }
        if (valorCiudad != null) {
            ciudades = Ciudades.obtenerNombreCiudades(valorCiudad);
        }
        try {
            Destino destino;
            destino = agenciaViajes.crearDestino(nombre, ciudades, descripcion, imagenes, climas);
            if (destino != null) {
                listaDestinoData.add(destino);
                mostrarMensaje("Notificación", "El destino", "se ha creado con éxitosamente", Alert.AlertType.INFORMATION);
                limpiarCamposDestino();
            }
        } catch (CampoObligatorio | InformacionNoExiste | SeleccionarNoOpcion | SeleccioneCargar e) {
            mostrarMensaje("Notificación", "El destino se no ha creado", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void limpiarCamposDestino() {
        txtAdmDestinoDescripcion.setText("");
        txtAdmDestinoNombre.setText("");
        imagenView.setImage(null);
        comboxOpcionesCiudad.setValue("");
        comboxOpcionesClima.setValue("");
        destinoSeleccionado = null;
    }

    @FXML
    void agragarAdmGuiaAction(ActionEvent event) {
        agragarAdmGuia();
    }

    private void agragarAdmGuia() {
        String nombreCompleto = txtAdmGuiaNombreCompleto.getText();
        String identificacion = txtAdmGuiaIdentificacion.getText();
        String correo = txtAdmGuiaCorreoElectronico.getText();
        String password = txtAdmGuiaContrasenia.getText();
        String valorLenguajes = comboxOpcionesLenjuajes.getValue();
        int experiencia = 0;
        if (!txtAdmGuiaExperencia.getText().isEmpty()) {
            experiencia = Integer.parseInt(txtAdmGuiaExperencia.getText());
        }
        Lenguajes lenguajes = null;
        if (valorLenguajes != null) {
            lenguajes = Lenguajes.obtenerNombreLenguajes(valorLenguajes);
        }
        try {
            GuiaTuristico guiaTuristico;
            guiaTuristico = agenciaViajes.crearGuiaTuristico(nombreCompleto, identificacion, correo, password, experiencia, lenguajes);
            if (guiaTuristico != null) {
                listaGuiaTuristicoData.add(guiaTuristico);
                mostrarMensaje("Notificación", "La guia turistico", "se ha creado con éxitosamente", Alert.AlertType.INFORMATION);
                limpiarCamposGuias();
            }
        } catch (CampoNegativo | CampoObligatorio | SeleccionarNoOpcion e) {
            mostrarMensaje("Notificación", "La guia turistico se no ha creado", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void limpiarCamposGuias() {
        txtAdmGuiaNombreCompleto.setText("");
        txtAdmGuiaIdentificacion.setText("");
        txtAdmGuiaExperencia.setText("");
        txtAdmGuiaCorreoElectronico.setText("");
        txtAdmGuiaContrasenia.setText("");
        comboxOpcionesLenjuajes.setValue("");
        guiaTuristicoSeleccionado = null;
    }

    @FXML
    void agregarAdmPaqueteAction(ActionEvent event) {
        agregarAdmPaquete();
    }

    private void agregarAdmPaquete() {
        String nombre = txtAdmPaqueteNombre.getText();
        String serviciosAdicionalesTexto = txtAdmPaqueteServicioAdicionales.getText();
        ArrayList<String> serviciosAdicionales = new ArrayList<>(Arrays.asList(serviciosAdicionalesTexto));
        int duracion = 0;
        if (!txtAdmPaqueteDuracion.getText().isEmpty()) {
            duracion = Integer.parseInt(txtAdmPaqueteDuracion.getText());
        }
        int cupoMaxPersona = 0;
        if (!txtAdmPaqueteCupoMaxPersonas.getText().isEmpty()) {
            cupoMaxPersona = Integer.parseInt(txtAdmPaqueteCupoMaxPersonas.getText());
        }
        Double precio = 0.0;
        if (!txtAdmPaquetePrecio.getText().isEmpty()) {
            precio = Double.parseDouble(txtAdmPaquetePrecio.getText());
        }
        LocalDateTime fechaDisponibles = null;
        if (dataAdmPaqueteFechaDisponible.getValue() != null) {
            fechaDisponibles = dataAdmPaqueteFechaDisponible.getValue().atStartOfDay();
        }


// Obtener los destinos seleccionados directamente desde la TableView
        ObservableList<Destino> destinosSeleccionados = tableAdmPaqueteDestinos.getSelectionModel().getSelectedItems();

        try {
            PaqueteTuristico paqueteTuristico;
            paqueteTuristico = agenciaViajes.crearPaquete(nombre, duracion, serviciosAdicionales, precio, cupoMaxPersona, fechaDisponibles, new ArrayList<>(destinosSeleccionados));
            if (paqueteTuristico != null) {
                listaPaqueteTuristicoData.add(paqueteTuristico);
                mostrarMensaje("Notificación", "El paquete turístico", "se ha creado exitosamente", Alert.AlertType.INFORMATION);
                limpiarCamposPaqueteTuristico();
            }
        } catch (CampoNegativo | FechaException | CampoObligatorio e) {
            mostrarMensaje("Notificación", "El paquete turístico no se ha creado", e.getMessage(), Alert.AlertType.ERROR);
        }

    }


    private void limpiarCamposPaqueteTuristico() {
        txtAdmPaquetePrecio.setText("");
        txtAdmPaqueteDuracion.setText("");
        txtAdmPaqueteNombre.setText("");
        txtAdmPaqueteServicioAdicionales.setText("");
        txtAdmPaqueteCupoMaxPersonas.setText("");
        dataAdmPaqueteFechaDisponible.setValue(null);
        paqueteTuristicoSeleccionado = null;
    }

    @FXML
    void eliminarAdmDestinoAction(ActionEvent event) {
        eliminarAdmDestino();
    }

    private void eliminarAdmDestino() {
        boolean destinoEliminado;
        if (destinoSeleccionado != null) {
            if (mostrarMensajeConfirmacion("¿Esta seguro de eliminar al destino?")) {
                destinoEliminado = agenciaViajes.eliminarDestino(destinoSeleccionado.getNombre());
                if (destinoEliminado == true) {
                    listaDestinoData.remove(destinoSeleccionado);
                    tableAdmDestino.refresh();
                    tableAdmDestino.getSelectionModel().clearSelection();
                    limpiarCamposDestino();
                    mostrarMensaje("Notificacion destino", "Destino eliminado",
                            "El destino se ha eliminado con exito", Alert.AlertType.INFORMATION);
                } else {
                    mostrarMensaje("Notificacion destino", "destino no eliminado", "El destino no se puede eliminar",
                            Alert.AlertType.ERROR);
                }
            }
        }
    }

    @FXML
    void eliminarAdmGuiaAction(ActionEvent event) {
        if (guiaTuristicoSeleccionado == null) {
            mostrarMensaje("Error", "No Selection", "Primero seleccionar y vuelva intento", Alert.AlertType.ERROR);
        } else {
            eliminarAdmGuia();
        }
    }

    private void eliminarAdmGuia() {
        boolean guiaTuristicoEliminado;
        if (guiaTuristicoSeleccionado != null) {
            if (mostrarMensajeConfirmacion("¿Esta seguro de eliminar al guia turistico?")) {
                guiaTuristicoEliminado = agenciaViajes.eliminarGuiaTuristico(guiaTuristicoSeleccionado.getIdentificacion());
                if (guiaTuristicoEliminado == true) {
                    listaGuiaTuristicoData.remove(guiaTuristicoSeleccionado);
                    tableAdmPaqueteDestinos.refresh();
                    tableAdmPaqueteDestinos.getSelectionModel().clearSelection();
                    limpiarCamposGuias();
                    mostrarMensaje("Notificacion guia turistico", "Guia turistico eliminado",
                            "La guia turistico se ha eliminado con exito", Alert.AlertType.INFORMATION);
                } else {
                    mostrarMensaje("Notificacion guia turistico", "Guia turistico no eliminado", "La guia turistico no se puede eliminar",
                            Alert.AlertType.ERROR);
                }
            }
        }
    }

    @FXML
    void eliminarAdmPaqueteAction(ActionEvent event) {
        if (paqueteTuristicoSeleccionado == null) {
            mostrarMensaje("Error", "No Selection", "Primero seleccionar y vuelva intento", Alert.AlertType.ERROR);
        } else {
            eliminarAdmPaquete();
        }
    }

    private void eliminarAdmPaquete() {
        boolean paqueteTuristicoEliminado;
        if (paqueteTuristicoSeleccionado != null) {
            if (mostrarMensajeConfirmacion("¿Esta seguro de eliminar al paquete turistico?")) {
                paqueteTuristicoEliminado = agenciaViajes.eliminarPaqueteTuristico(paqueteTuristicoSeleccionado.getNombre());
                if (paqueteTuristicoEliminado == true) {
                    listaPaqueteTuristicoData.remove(paqueteTuristicoSeleccionado);
                    tableAdmPaquete.refresh();
                    tableAdmPaquete.getSelectionModel().clearSelection();
                    limpiarCamposPaqueteTuristico();
                    mostrarMensaje("Notificacion el paquete turistico", "El paquete turistico eliminado",
                            "EL paquete turistico se ha eliminado con exito", Alert.AlertType.INFORMATION);
                } else {
                    mostrarMensaje("Notificacion el paquete turistico", "El paquete turistico no eliminado", "La guia turistico no se puede eliminar",
                            Alert.AlertType.ERROR);
                }
            }
        }
    }

    @FXML
    void nuevoAdmDestinoAction(ActionEvent event) {
        if (tableAdmDestino.getSelectionModel().getSelectedItem() == null) {
            mostrarMensaje("Error", "No Selection", "Primero seleccionar y vuelva intento", Alert.AlertType.ERROR);
        } else {
            limpiarCamposDestino();
            tableAdmDestino.getSelectionModel().select(null);
        }
    }

    @FXML
    void nuevoAdmGuiaAction(ActionEvent event) {
        if (tableAdmPaqueteDestinos.getSelectionModel().getSelectedItem() == null) {
            mostrarMensaje("Error", "No Selection", "Primero seleccionar y vuelva intento", Alert.AlertType.ERROR);
        } else {
            limpiarCamposGuias();
            tableAdmPaqueteDestinos.getSelectionModel().select(null);
        }
    }

    @FXML
    void nuevoAdmPaqueteAction(ActionEvent event) {
        if (tableAdmPaquete.getSelectionModel().getSelectedItem() == null) {
            mostrarMensaje("Error", "No Selection", "Primero seleccionar y vuelva intento", Alert.AlertType.ERROR);
        } else {
            limpiarCamposPaqueteTuristico();
            tableAdmPaquete.getSelectionModel().select(null);
        }
    }

    @FXML
    void cerrarSesionAdmAction(ActionEvent event) {
        Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
        stage.close();

        Platform.exit();
    }

    @FXML
    void mostrarVentana(ActionEvent event) {
        if (event.getSource() == btnDestinos) {
            destinoForm.setVisible(true);
            guiasForm.setVisible(false);
            paquetesForm.setVisible(false);
            estadisticaForm.setVisible(false);
        } else if (event.getSource() == btnEstadistica) {
            destinoForm.setVisible(false);
            guiasForm.setVisible(false);
            paquetesForm.setVisible(false);
            estadisticaForm.setVisible(true);
        } else if (event.getSource() == btnGuia) {
            destinoForm.setVisible(false);
            guiasForm.setVisible(true);
            paquetesForm.setVisible(false);
            estadisticaForm.setVisible(false);
        } else if (event.getSource() == btnPaquete) {
            destinoForm.setVisible(false);
            guiasForm.setVisible(false);
            paquetesForm.setVisible(true);
            estadisticaForm.setVisible(false);
        }
    }

    @FXML
    void initialize() {
        mostrarEstadisticasReservadoBuscar();
        mostrarCombox();
        inicializarDestinoView();
        inicializarGuiasView();
        inicializarPaqueteTuristicoView();
        inicializarPaqueteDestinosView();
        inicializartblPaquetesAdmin();
        tableAdmPaqueteDestinos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


    }
    private void inicializarPaqueteDestinosView() {
        this.clAdmPaqueteDestinoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tableAdmPaqueteDestinos.getItems().clear();
        tableAdmPaqueteDestinos.setItems(getListaDestinoData());
        tableAdmPaqueteDestinos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                destinoSeleccionado = newSelection;
                    mostraInformacionPaqueteDestinos(destinoSeleccionado);
            }
        });
    }
    private void mostraInformacionPaqueteDestinos(Destino destinoSeleccionado) {
        if (destinoSeleccionado != null) {
            txtAdmDestinoNombre.setText(destinoSeleccionado.getNombre());
        }
    }
    private void mostrarEstadisticasReservadoBuscar() {
        // Crear un mapa para almacenar la frecuencia de cada destino
        Map<String, Integer> frecuenciaDestinos = new HashMap<>();
        // Recorrer la lista de reservas y contar la frecuencia de cada destino
        for (Reserva reserva : agenciaViajes.getListaReservas()) {
            PaqueteTuristico paquete = reserva.getPaqueteTuristico();
            for (Destino destino : paquete.getDestinos()) {
                String nombreDestino = destino.getNombre();
                frecuenciaDestinos.put(nombreDestino, frecuenciaDestinos.getOrDefault(nombreDestino, 0) + 1);
            }
        }
        // Recorrer la lista de paquetes turísticos y contar la frecuencia de cada destino
        for (PaqueteTuristico paquete : agenciaViajes.getListaPaqueteTuristicos()) {
            for (Destino destino : paquete.getDestinos()) {
                String nombreDestino = destino.getNombre();
                frecuenciaDestinos.put(nombreDestino, frecuenciaDestinos.getOrDefault(nombreDestino, 0) + 1);
            }
        }
        // Actualizar el gráfico de barras con los datos de frecuencia
        for (Map.Entry<String, Integer> entry : frecuenciaDestinos.entrySet()) {
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            destinosReservadoBuscarBarChart.getData().add(series);
        }
    }

    private void inicializarPaqueteTuristicoView() {
        this.clAdmPaqueteNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.clAdmPaqueteServicioAdicional.setCellValueFactory(new PropertyValueFactory<>("serviciosAdicionales"));
        this.clAdmPaqueteCupo.setCellValueFactory(new PropertyValueFactory<>("cupoMaxPersona"));
        this.clAdmPaqueteDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        this.clAdmPaqueteFechaDisponibles.setCellValueFactory(new PropertyValueFactory<>("fechaDisponibles"));
        this.clAdmPaquetePrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tableAdmPaquete.getItems().clear();
        tableAdmPaquete.setItems(getlistaPaqueteTuristicoData());
        tableAdmPaquete.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                paqueteTuristicoSeleccionado = newSelection;
                mostraInformacionPaqueteTuristico(paqueteTuristicoSeleccionado);
            }
        });
    }

    private void mostraInformacionPaqueteTuristico(PaqueteTuristico paqueteTuristicoSeleccionado) {
        if (paqueteTuristicoSeleccionado != null) {
            txtAdmPaqueteNombre.setText(paqueteTuristicoSeleccionado.getNombre());
            txtAdmPaqueteServicioAdicionales.setText(String.valueOf(paqueteTuristicoSeleccionado.getServiciosAdicionales()));
            txtAdmPaqueteDuracion.setText(String.valueOf(paqueteTuristicoSeleccionado.getDuracion()));
            txtAdmPaquetePrecio.setText(String.valueOf(paqueteTuristicoSeleccionado.getPrecio()));
            txtAdmPaqueteCupoMaxPersonas.setText(String.valueOf(paqueteTuristicoSeleccionado.getCupoMaxPersona()));
            dataAdmPaqueteFechaDisponible.setValue(LocalDate.from(paqueteTuristicoSeleccionado.getFechaDisponibles()));
        }
    }

    private void inicializarGuiasView() {
        this.clAdmGuiasNombreCompleto.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        this.clAdmGuiasIdentificacion.setCellValueFactory(new PropertyValueFactory<>("identificacion"));
        this.clAdmGuiasLenguajes.setCellValueFactory(celda -> {
            if (celda.getValue().getLenguaje() != null) {
                return new SimpleStringProperty(celda.getValue().getLenguaje().getNombreLenguajes());
            } else {
                return new SimpleStringProperty("");
            }
        });
        this.clAdmGuiasExperiencia.setCellValueFactory(new PropertyValueFactory<>("experiencia"));
        this.clAdmGuiasCorreoElectronico.setCellValueFactory(new PropertyValueFactory<>("correo"));
        this.clAdmGuiasContrasenia.setCellValueFactory(new PropertyValueFactory<>("password"));
        tableAdmGuias.getItems().clear();
        tableAdmGuias.setItems(getListaGuiaTuristicoData());
        tableAdmGuias.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                guiaTuristicoSeleccionado = newSelection;
                mostraInformacionGuias(guiaTuristicoSeleccionado);
            }
        });
    }

    private void mostraInformacionGuias(GuiaTuristico guiaTuristicoSeleccionado) {
        if (guiaTuristicoSeleccionado != null) {
            txtAdmGuiaNombreCompleto.setText(guiaTuristicoSeleccionado.getNombreCompleto());
            txtAdmGuiaIdentificacion.setText(guiaTuristicoSeleccionado.getIdentificacion());
            if (guiaTuristicoSeleccionado.getLenguaje() != null) {
                String lenguaje = guiaTuristicoSeleccionado.getLenguaje().getNombreLenguajes();
                comboxOpcionesLenjuajes.setValue(lenguaje);
            }
            txtAdmGuiaExperencia.setText(String.valueOf(guiaTuristicoSeleccionado.getExperiencia()));
            txtAdmGuiaCorreoElectronico.setText(guiaTuristicoSeleccionado.getCorreo());
            txtAdmGuiaContrasenia.setText(guiaTuristicoSeleccionado.getPassword());
        }
    }

    private void inicializarDestinoView() {
        this.clAdmDestinoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.clAdmDestinoCiudad.setCellValueFactory(celda -> {
            if (celda.getValue().getCiudad() != null) {
                return new SimpleStringProperty(celda.getValue().getCiudad().getNombreCiudad());
            } else {
                return new SimpleStringProperty("");
            }
        });

        this.clAdmDestinoClima.setCellValueFactory(celda -> {
            if (celda.getValue().getClima() != null) {
                return new SimpleStringProperty(celda.getValue().getClima().getNombreClima());
            } else {
                return new SimpleStringProperty("");
            }
        });
        this.clAdmDestinoDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        tableAdmDestino.getItems().clear();
        tableAdmDestino.setItems(getListaDestinoData());
        tableAdmDestino.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                destinoSeleccionado = newSelection;
                try {
                    mostraInformacionDestino(destinoSeleccionado);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void mostraInformacionDestino(Destino destinoSeleccionado) throws MalformedURLException {
        if (destinoSeleccionado != null) {
            txtAdmDestinoNombre.setText(destinoSeleccionado.getNombre());

            if (destinoSeleccionado.getClima() != null) {
                String clima = String.valueOf(destinoSeleccionado.getClima());
                comboxOpcionesClima.setValue(clima);
            }

            if (destinoSeleccionado.getCiudad() != null) {
                String ciudad = String.valueOf(destinoSeleccionado.getCiudad());
                comboxOpcionesCiudad.setValue(ciudad);
            }

            File file = new File(destinoSeleccionado.getImagenes().get(0));
            String imageUrl = file.toURI().toURL().toString();
            imagenView.setImage(new Image(imageUrl));

            String descripcion = destinoSeleccionado.getDescripcion();
            txtAdmDestinoDescripcion.setText(descripcion);
        }
    }

    private void mostrarCombox() {
        mostrarComboxOpcionesCiudades();
        mostrarComboxOpcionesClimas();
        mostrarComboxOpcionesLenguajes();
    }

    private void mostrarComboxOpcionesLenguajes() {
        ArrayList<Lenguajes> listaLenguaje = new ArrayList<>(Arrays.asList(Lenguajes.values()));
        listaLenguaje.sort(Comparator.comparing(Enum::name));
        ObservableList<String> listaLenguajesFormateadas = FXCollections.observableArrayList();
        for (Lenguajes lenguajes : listaLenguaje) {
            String nombreLenguajes = lenguajes.getNombreLenguajes();
            listaLenguajesFormateadas.add(nombreLenguajes);
        }
        comboxOpcionesLenjuajes.setItems(listaLenguajesFormateadas);
    }

    private void mostrarComboxOpcionesClimas() {
        ArrayList<Clima> listaClima = new ArrayList<>(Arrays.asList(Clima.values()));
        listaClima.sort(Comparator.comparing(Enum::name));
        ObservableList<String> listaClimaFormateadas = FXCollections.observableArrayList();
        for (Clima clima : listaClima) {
            String nombreClima = clima.getNombreClima();
            listaClimaFormateadas.add(nombreClima);
        }
        comboxOpcionesClima.setItems(listaClimaFormateadas);
    }

    private void mostrarComboxOpcionesCiudades() {
        ArrayList<Ciudades> listaCiudades = new ArrayList<>(Arrays.asList(Ciudades.values()));
        listaCiudades.sort(Comparator.comparing(Enum::name));
        ObservableList<String> listaCiudadesFormateadas = FXCollections.observableArrayList();
        for (Ciudades ciudad : listaCiudades) {
            String nombreCiudad = ciudad.getNombreCiudad();
            listaCiudadesFormateadas.add(nombreCiudad);
        }
        comboxOpcionesCiudad.setItems(listaCiudadesFormateadas);
    }

    //-------------------------ObservableList---------------------------------//

    public ObservableList<Destino> getListaDestinoData() {
        listaDestinoData.addAll(agenciaViajes.getListaDestinos());
        return listaDestinoData;
    }

    public ObservableList<GuiaTuristico> getListaGuiaTuristicoData() {
        for (Persona persona : agenciaViajes.getListaPersona()) {
            if (persona instanceof GuiaTuristico) {
                listaGuiaTuristicoData.addAll((GuiaTuristico) persona);
            }
        }
        return listaGuiaTuristicoData;
    }

    private ObservableList<PaqueteTuristico> getlistaPaqueteTuristicoData() {
        listaPaqueteTuristicoData.addAll(agenciaViajes.getListaPaqueteTuristicos());
        return listaPaqueteTuristicoData;
    }

    //-------------------------Mostrar mensaje---------------------------------//
    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    private boolean mostrarMensajeConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();
        return action.get() == ButtonType.OK;
    }

    @FXML
    private void generarExel(ActionEvent event) {
        Exel.agregarClientesExel();
        mostrarMensaje("Exel", "Exel de clientes generado", "", Alert.AlertType.CONFIRMATION);
    }
    private void inicializartblPaquetesAdmin() {

        clAdmPaqueteNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clAdmPaqueteDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        clAdmPaqueteServicioAdicional.setCellValueFactory(new PropertyValueFactory<>("serviciosAdicionales"));
        clAdmPaquetePrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        clAdmPaqueteCupo.setCellValueFactory(new PropertyValueFactory<>("cupoMaxPersona"));
        clAdmPaqueteFechaDisponibles.setCellValueFactory(new PropertyValueFactory<>("fechaDisponibles"));


    // Lee los datos de los paquetes turísticos desde el archivo
    cargarPaquetesDesdeArchivo("src/main/resources/persistencia/paquetes.txt");

    // Asigna la lista a la TableView
        tableAdmPaquete.setItems(listaPaqueteTuristicoData);
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
                if (partes.length >= 6) {
                    int cupoMaxPersona = Integer.parseInt(partes[4]);
                    int duracion = Integer.parseInt(partes[1]);
                    LocalDateTime fechaDisponibles = LocalDateTime.parse(partes[5]); // Ajusta el formato según tu necesidad
                    String nombre = partes[0];
                    double precio = Double.parseDouble(partes[3]);
                    ArrayList<String> serviciosAdicionales = new ArrayList<>(Arrays.asList(partes[2].split(";")));

                    // Crea un nuevo PaqueteTuristico y agrégalo a la lista
                    PaqueteTuristico paquete = new PaqueteTuristico(nombre, duracion, serviciosAdicionales, precio, cupoMaxPersona, fechaDisponibles);
                    listaPaqueteTuristicoData.add(paquete);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error de Lectura", "Hubo un error al leer el archivo de paquetes.", Alert.AlertType.ERROR);
        }
    }


    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


}
