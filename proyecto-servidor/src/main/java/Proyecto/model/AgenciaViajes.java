package Proyecto.model;

import Proyecto.enums.Ciudades;
import Proyecto.enums.Clima;
import Proyecto.enums.Lenguajes;
import Proyecto.exceptions.*;
import Proyecto.utils.Persistencia;
import lombok.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Setter
@Getter
@ToString
public class AgenciaViajes {

    //------atributos----//
    private String nombreEmpresa;

    //------Clases----//
    private ArrayList<Persona> listaPersona;
    private ArrayList<Reserva> listaReservas;
    private ArrayList<PaqueteTuristico> listaPaqueteTuristicos;
    @Getter
    private ArrayList<Destino> listaDestinos;
    private ArrayList<GuiaTuristico> listaGuias;

    //-----LOGGER----//
    private static final Logger LOGGER = Logger.getLogger(AgenciaViajes.class.getName());

    //Variable que tendrá la instancia de esta clase
    private static AgenciaViajes agenciaViajes;

    /**
     * Constructor debe ser privado para que ninguna otra clase pueda crear instancias de esta clase
     */
    private AgenciaViajes() {

        registrarArchivoLogger(); // Llamada al método para registrar el archivo del logger.

        this.listaPersona = new ArrayList<>();
        this.listaPersona = Persistencia.leerPersona();

        this.listaDestinos = new ArrayList<>();
        this.listaDestinos = Persistencia.leerDestinos();

        this.listaPaqueteTuristicos = new ArrayList<>();
        //this.listaPaqueteTuristicos = Persistencia.leerPaqueteTuristicos();

        this.listaReservas = new ArrayList<>();

    }

    /**
     * Método para registrar el archivo del logger.
     */
    private void registrarArchivoLogger() {
        try {
            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        LOGGER.log(Level.INFO, "Se crea una nueva instancia de AgenciaViajes");
    }

    //------------------------------  Singleton -----------------------------------------//

    /**
     * Método que se usará en las otras clases que requieran una instancia de esta clase
     *
     * @return Instancia del objeto Empresa
     */
    public static AgenciaViajes getInstance() {
        if (agenciaViajes == null) {
            agenciaViajes = new AgenciaViajes();
        }
        return agenciaViajes;
    }

    //----------------------DESTINO----------------------//
    //----------------------CREAR------------------------//

    /**
     * Método para crear un nuevo destino.
     *
     * @param nombre      El nombre del destino.
     * @param ciudad      La ciudad del destino.
     * @param descripcion La descripción del destino.
     * @param imagenes    Una lista de imágenes del destino.
     * @param clima       El clima del destino.
     * @return Retorna un objeto de tipo Destino.
     * @throws InformacionNoExiste Se lanza cuando la información no existe.
     * @throws CampoObligatorio    Se lanza cuando falta un campo obligatorio.
     * @throws SeleccionarNoOpcion Se lanza cuando no se selecciona una opción.
     * @throws SeleccioneCargar    Se lanza cuando no se selecciona cargar.
     */
    public Destino crearDestino(String nombre, Ciudades ciudad, String descripcion, ArrayList<String> imagenes, Clima clima) throws InformacionNoExiste, CampoObligatorio, SeleccionarNoOpcion, SeleccioneCargar {
        Destino nuevoDestino = null;
        // Muestra los campos obligatorios.
        if (validarDestinoCampoObligatorio(nombre, ciudad, descripcion, imagenes, clima)) {
            nuevoDestino = new Destino();
            nuevoDestino.setNombre(nombre);
            nuevoDestino.setCiudad(ciudad);
            nuevoDestino.setDescripcion(descripcion);
            nuevoDestino.setImagenes(imagenes);
            nuevoDestino.setClima(clima);
            getListaDestinos().add(nuevoDestino);
            // Registra en el logger que el destino se ha creado exitosamente.
            LOGGER.log(Level.INFO, "El destino se ha creado exitosamente : " + nombre);
            // Escribe la lista de los Destinos en la persistencia de datos.
            Persistencia.escribirDestino(nuevoDestino);
        }
        return nuevoDestino;
    }


    /**
     * Este método valida los campos de un destino turístico. Si alguno de los campos no cumple con las condiciones establecidas, se lanza una excepción.
     *
     * @param nombre      El nombre del destino turístico. No puede ser null o vacío.
     * @param ciudad      La ciudad del destino turístico. No puede ser null.
     * @param descripcion La descripción del destino turístico. No puede ser null o vacía.
     * @param imagenes    Una lista de imágenes del destino turístico. No puede ser null o vacía.
     * @param clima       El clima del destino turístico. No puede ser null.
     * @return true si todos los campos son válidos, de lo contrario se lanzará una excepción.
     * @throws CampoObligatorio    Se lanza esta excepción cuando el nombre o la descripción son null o vacíos.
     * @throws SeleccionarNoOpcion Se lanza esta excepción cuando no se selecciona una opción para la ciudad o el clima.
     * @throws SeleccioneCargar    Se lanza esta excepción cuando no se selecciona un archivo para cargar en las imágenes.
     */
    private boolean validarDestinoCampoObligatorio(String nombre, Ciudades ciudad, String descripcion, ArrayList<String> imagenes, Clima clima) throws CampoObligatorio, SeleccionarNoOpcion, SeleccioneCargar {
        // Si el nombre está vacío, registra un error y lanza una excepción.
        if (nombre == null || nombre.isEmpty()) {
            LOGGER.log(Level.SEVERE, "El nombre es obligatorio de campo");
            throw new CampoObligatorio("El nombre es obligatorio de campo");
        }
        // Si la ciudad está vacía, registra un error y lanza una excepción.
        if (ciudad == null) {
            LOGGER.log(Level.SEVERE, "Por favor, selecciona una opción para  la ciudad.");
            throw new SeleccionarNoOpcion("Por favor, selecciona una opción para  la ciudad.");
        }
        // Si la clima está vacía, registra un error y lanza una excepción.
        if (clima == null) {
            LOGGER.log(Level.SEVERE, "Por favor, selecciona una opción para el clima.");
            throw new SeleccionarNoOpcion("Por favor, selecciona una opción para el clima.");
        }
        // Si el descripcio está vacío, registra un error y lanza una excepción.
        if (descripcion == null || descripcion.isEmpty()) {
            LOGGER.log(Level.SEVERE, "El descripcio es obligatorio de campo");
            throw new CampoObligatorio("El descripcio es obligatorio de campo");
        }
        // Si la imágen está vacía, registra un error y lanza una excepción.
        if (imagenes == null || imagenes.isEmpty()) {
            LOGGER.log(Level.SEVERE, "Por favor, Seleccione un archivo para cargar.");
            throw new SeleccioneCargar("Por favor, Seleccione un archivo para cargar.");
        }

        return true;
    }
    //----------------------ACTUALIZAR-------------------//

    /**
     * Este método se utiliza para actualizar la información de un destino específico.
     *
     * @param nombreNuevo El nombre del destino que se va a actualizar.
     * @param ciudad      La ciudad del destino que se va a actualizar.
     * @param descripcion La descripción del destino que se va a actualizar.
     * @param imagenes    Una lista de imágenes del destino que se va a actualizar.
     * @param clima       El clima del destino que se va a actualizar.
     * @return Retorna verdadero si el destino se actualiza exitosamente, de lo contrario retorna falso.
     * @throws CampoObligatorio    Se lanza esta excepción si algún campo obligatorio está vacío.
     * @throws SeleccioneCargar    Se lanza esta excepción si no se selecciona ninguna opción para cargar.
     * @throws SeleccionarNoOpcion Se lanza esta excepción si no se selecciona ninguna opción.
     */
    public boolean actualizarDestino(String nombreActual, String nombreNuevo, Ciudades ciudad, String descripcion, ArrayList<String> imagenes, Clima clima) throws CampoObligatorio, SeleccioneCargar, SeleccionarNoOpcion {
        Destino destino = obtenerDestino(nombreActual);
        boolean esDestinoActualizado = false;

        if (validarDestinoCampoObligatorio(nombreNuevo, ciudad, descripcion, imagenes, clima)) {
            if (destino != null) {
                destino.setNombre(nombreNuevo);
                destino.setCiudad(ciudad);
                destino.setDescripcion(descripcion);
                destino.setImagenes(imagenes);
                destino.setClima(clima);
                LOGGER.info("Destino actualizado exitosamente.");
                esDestinoActualizado = true;
            } else {
                LOGGER.severe("No se encontró el destino para actualizar.");
            }
        }
        return esDestinoActualizado;
    }


    /**
     * Este método recibe un nombre y busca un objeto Destino con ese nombre en la lista de destinos.
     * Utiliza un método auxiliar recursivo para realizar la búsqueda.
     *
     * @param nombreActual El nombre del Destino que se está buscando.
     * @return El objeto Destino si se encuentra, null si no se encuentra.
     */
    private Destino obtenerDestino(String nombreActual) {
        return obtenerDestinoAux(nombreActual, 0);
    }

    private Destino obtenerDestinoAux(String nombreActual, int index) {
        if (index >= listaDestinos.size()) {
            return null;
        }
        Destino destino = listaDestinos.get(index);
        if (destino.getNombre().equals(nombreActual)) {
            return destino;
        }
        return obtenerDestinoAux(nombreActual, index + 1);
    }


    //----------------------ELIMINAR---------------------//

    /**
     * Este método elimina un objeto Destino de la lista de destinos por su nombre.
     *
     * @param nombre El nombre del destino que se desea eliminar.
     * @return Retorna true si el destino se eliminó exitosamente; de lo contrario, retorna false.
     */
    public boolean eliminarDestino(String nombre) {
        Destino destino = obtenerDestino(nombre);
        if (destino != null) {
            getListaDestinos().remove(destino);
            LOGGER.info("Destino eliminado exitosamente. " + nombre);
            return true;
        }
        LOGGER.severe("El destino no se puede eliminar");
        return false;
    }

    //----------------------GUIAS----------------------//
    //----------------------CREAR------------------------//

    /**
     * Este método crea un nuevo objeto GuiaTuristico y lo añade a la lista de personas.
     *
     * @param nombreCompleto El nombre completo del guía turístico.
     * @param identificacion La identificación del guía turístico.
     * @param usuario        El correo electrónico del guía turístico.
     * @param password       La contraseña del guía turístico.
     * @param experiencia    La experiencia del guía turístico.
     * @param lenguaje       El lenguaje que habla el guía turístico.
     * @return Retorna el objeto GuiaTuristico creado.
     * @throws CampoNegativo       Se lanza si se introduce un valor negativo donde no se permite.
     * @throws CampoObligatorio    Se lanza si se deja un campo obligatorio vacío.
     * @throws SeleccionarNoOpcion Se lanza si no se selecciona ninguna opción donde se requiere.
     */
    public GuiaTuristico crearGuiaTuristico(String nombreCompleto, String identificacion, String usuario, String password, int experiencia, Lenguajes lenguaje) throws CampoNegativo, CampoObligatorio, SeleccionarNoOpcion {
        GuiaTuristico nuevoGuiaTuristico = null;
        if (validarGuiaTuristicoCamposObligatorios(nombreCompleto, identificacion, usuario, password, experiencia, lenguaje)) {
            nuevoGuiaTuristico = new GuiaTuristico();
            nuevoGuiaTuristico.setNombreCompleto(nombreCompleto);
            nuevoGuiaTuristico.setIdentificacion(identificacion);
            nuevoGuiaTuristico.setCorreo(usuario);
            nuevoGuiaTuristico.setPassword(password);
            nuevoGuiaTuristico.setExperiencia(experiencia);
            nuevoGuiaTuristico.setLenguaje(lenguaje);
            getListaPersona().add(nuevoGuiaTuristico);
            LOGGER.log(Level.INFO, "La guia turistico se ha creado exitosamente : " + identificacion);
            Persistencia.escribirGuiaTuristico(nuevoGuiaTuristico);
        }
        return nuevoGuiaTuristico;
    }


    /**
     * Este método valida los campos de un guía turístico. Si alguno de los campos no cumple con las condiciones establecidas, se lanza una excepción.
     *
     * @param nombreCompleto El nombre completo del guía turístico. No puede ser null o vacío.
     * @param identificacion La identificación del guía turístico. No puede ser null o vacío.
     * @param usuario        El nombre de usuario del guía turístico. No puede ser null o vacío.
     * @param password       La contraseña del guía turístico. No puede ser null o vacío.
     * @param experiencia    La experiencia del guía turístico en años. Debe ser un número positivo.
     * @param lenguaje       El lenguaje que habla el guía turístico. No puede ser null.
     * @return true si todos los campos son válidos, de lo contrario se lanzará una excepción.
     * @throws CampoObligatorio    Se lanza esta excepción cuando el nombre completo, la identificación, el usuario, la contraseña o el lenguaje son null o vacíos.
     * @throws CampoNegativo       Se lanza esta excepción cuando la experiencia es negativa o cero.
     * @throws SeleccionarNoOpcion Se lanza esta excepción cuando no se selecciona una opción para el lenguaje.
     */
    private boolean validarGuiaTuristicoCamposObligatorios(String nombreCompleto, String identificacion, String usuario, String password, int experiencia, Lenguajes lenguaje) throws CampoObligatorio, CampoNegativo, SeleccionarNoOpcion {
        if (nombreCompleto == null || nombreCompleto.isEmpty()) {
            LOGGER.log(Level.SEVERE, "El nombre completo es un campo obligatorio");
            throw new CampoObligatorio("El nombre completo es un campo obligatorio");
        }
        if (identificacion == null || identificacion.isEmpty()) {
            LOGGER.log(Level.SEVERE, "La identificación es un campo obligatorio");
            throw new CampoObligatorio("La identificación es un campo obligatorio");
        }
        if (usuario == null || usuario.isEmpty()) {
            LOGGER.log(Level.SEVERE, "El usuario es un campo obligatorio");
            throw new CampoObligatorio("El usuario es un campo obligatorio");
        }
        if (password == null || password.isEmpty()) {
            LOGGER.log(Level.SEVERE, "La contraseña es un campo obligatorio");
            throw new CampoObligatorio("La contraseña es un campo obligatorio");
        }
        if (experiencia <= 0) {
            LOGGER.log(Level.SEVERE, "La experiencia no puede ser negativa o cero");
            throw new CampoNegativo("La experiencia no puede ser negativa o cero");
        }
        if (lenguaje == null) {
            LOGGER.log(Level.SEVERE, "Por favor, selecciona una opción para el lenguaje.");
            throw new SeleccionarNoOpcion("Por favor, selecciona una opción para el lenguaje.");
        }
        return true;
    }
    //----------------------ACTUALIZAR-------------------//

    /**
     * Método para actualizar la información de un guía turístico.
     *
     * @param identificacionActual La identificación actual del guía turístico.
     * @param nombreCompleto       El nombre completo del guía turístico.
     * @param identificacionNuevo  La nueva identificación del guía turístico.
     * @param usuario              El nombre de usuario del guía turístico.
     * @param password             La contraseña del guía turístico.
     * @param experiencia          La experiencia (en años) del guía turístico.
     * @param lenguaje             El lenguaje que habla el guía turístico.
     * @return Verdadero si el guía turístico se actualizó correctamente, falso en caso contrario.
     * @throws CampoNegativo       Se lanza cuando se introduce un valor negativo donde no es permitido.
     * @throws CampoObligatorio    Se lanza cuando se deja un campo obligatorio vacío.
     * @throws SeleccionarNoOpcion Se lanza cuando no se selecciona ninguna opción en un campo de selección.
     */
    public boolean actualizarGuiaTuristico(String identificacionActual, String nombreCompleto, String identificacionNuevo, String usuario, String password, int experiencia, Lenguajes lenguaje) throws CampoNegativo, CampoObligatorio, SeleccionarNoOpcion {
        GuiaTuristico guiaTuristico = obtenerGuiaTuristico(identificacionActual);
        boolean esGuiaTuristicoActualizado = false;
        if (validarGuiaTuristicoCamposObligatorios(nombreCompleto, identificacionNuevo, usuario, password, experiencia, lenguaje)) {
            if (guiaTuristico != null) {
                guiaTuristico.setNombreCompleto(nombreCompleto);
                guiaTuristico.setIdentificacion(identificacionNuevo);
                guiaTuristico.setExperiencia(experiencia);
                guiaTuristico.setCorreo(usuario);
                guiaTuristico.setPassword(password);
                LOGGER.info("Guia turistico actualizado exitosamente.");
                esGuiaTuristicoActualizado = true;
            } else {
                LOGGER.severe("No se encontró la guia turistico para actualizar.");
            }
        }
        return esGuiaTuristicoActualizado;
    }

    /**
     * Este método recibe una identificación y busca un objeto GuiaTuristico con esa identificación en la lista de personas.
     * Utiliza un método auxiliar recursivo para realizar la búsqueda.
     *
     * @param identificacionActual La identificación del GuiaTuristico que se está buscando.
     * @return El objeto GuiaTuristico si se encuentra, null si no se encuentra.
     */
    private GuiaTuristico obtenerGuiaTuristico(String identificacionActual) {
        return obtenerGuiaTuristicoAux(identificacionActual, 0);
    }

    private GuiaTuristico obtenerGuiaTuristicoAux(String identificacionActual, int index) {
        if (index >= listaPersona.size()) {
            return null;
        }
        Persona persona = listaPersona.get(index);
        if (persona instanceof GuiaTuristico && persona.getIdentificacion().equals(identificacionActual)) {
            return (GuiaTuristico) persona;
        }
        return obtenerGuiaTuristicoAux(identificacionActual, index + 1);
    }

    //----------------------ELIMINAR---------------------//

    /**
     * Método para eliminar un guía turístico a partir de su identificación.
     *
     * @param identificacion La identificación del guía turístico que se desea eliminar.
     * @return Verdadero si el guía turístico se eliminó correctamente, falso en caso contrario.
     */
    public boolean eliminarGuiaTuristico(String identificacion) {
        GuiaTuristico guiaTuristico = obtenerGuiaTuristico(identificacion);
        if (guiaTuristico != null) {
            getListaPersona().remove(guiaTuristico);
            LOGGER.info("Guia turistico eliminado exitosamente. " + identificacion);
            return true;
        }
        LOGGER.severe("La guia turistico  no se puede eliminar");
        return false;
    }

    //----------------------PAQUETES----------------------//
    //----------------------CREAR------------------------//

    /**
     * Método para crear un nuevo paquete turístico.
     *
     * @param nombre               El nombre del paquete turístico.
     * @param duracion             La duración (en días) del paquete turístico.
     * @param serviciosAdicionales Una lista de servicios adicionales incluidos en el paquete turístico.
     * @param precio               El precio del paquete turístico.
     * @param cupoMaxPersona       El número máximo de personas que pueden adquirir el paquete turístico.
     * @param fechaDisponibles     La fecha en la que el paquete turístico estará disponible.
     * @return El nuevo paquete turístico creado.
     * @throws CampoNegativo    Se lanza cuando se introduce un valor negativo donde no es permitido.
     * @throws CampoObligatorio Se lanza cuando se deja un campo obligatorio vacío.
     * @throws FechaException   Se lanza cuando la fecha introducida no es válida.
     */
    public PaqueteTuristico crearPaquete(String nombre, int duracion, ArrayList<String> serviciosAdicionales, Double precio, int cupoMaxPersona, LocalDateTime fechaDisponibles, List<Destino> destinosSeleccionados) throws CampoNegativo, CampoObligatorio, FechaException {
        PaqueteTuristico nuevoPaqueteTuristico = null;
        if (validarPaqueteTuristicoCampoObligatorio(nombre, duracion, serviciosAdicionales, precio, cupoMaxPersona, fechaDisponibles)) {
            nuevoPaqueteTuristico = new PaqueteTuristico();
            nuevoPaqueteTuristico.setNombre(nombre);
            nuevoPaqueteTuristico.setDuracion(duracion);
            nuevoPaqueteTuristico.setServiciosAdicionales(serviciosAdicionales);
            nuevoPaqueteTuristico.setPrecio(precio);
            nuevoPaqueteTuristico.setCupoMaxPersona(cupoMaxPersona);
            nuevoPaqueteTuristico.setFechaDisponibles(fechaDisponibles);
            nuevoPaqueteTuristico.setDestinos((ArrayList<Destino>) destinosSeleccionados); // Establece la lista de destinos en el paquete
            getListaPaqueteTuristicos().add(nuevoPaqueteTuristico);
            LOGGER.log(Level.INFO, "El paquete turistico se ha creado exitosamente : " + nombre);
            Persistencia.escribirPaqueteTuristico(nuevoPaqueteTuristico);
        }
        return nuevoPaqueteTuristico;
    }



    /**
     * Este método valida los campos de un paquete turístico. Si alguno de los campos no cumple con las condiciones establecidas,
     * se lanza una excepción.
     *
     * @param nombre               El nombre del paquete turístico. No puede ser null o vacío.
     * @param duracion             La duración del paquete turístico en días. Debe ser un número positivo.
     * @param serviciosAdicionales Una lista de servicios adicionales incluidos en el paquete. No puede ser null o vacía.
     * @param precio               El precio del paquete turístico. Debe ser un número positivo.
     * @param cupoMaxPersona       El cupo máximo de personas para el paquete turístico. Debe ser un número positivo.
     * @param fechaDisponibles     La fecha en la que el paquete turístico estará disponible. No puede ser una fecha pasada.
     * @throws CampoObligatorio Se lanza esta excepción cuando el nombre o los servicios adicionales son null o vacíos.
     * @throws CampoNegativo    Se lanza esta excepción cuando la duración, el precio o el cupo máximo de personas son negativos o cero.
     * @throws FechaException   Se lanza esta excepción cuando la fecha disponible es anterior a la fecha actual.
     */
    private boolean validarPaqueteTuristicoCampoObligatorio(String nombre, int duracion, ArrayList<String> serviciosAdicionales, Double precio, int cupoMaxPersona, LocalDateTime fechaDisponibles) throws CampoObligatorio, CampoNegativo, FechaException {
        if (nombre == null || nombre.isEmpty()) {
            LOGGER.log(Level.SEVERE, "El nombre es obligatorio de campo");
            throw new CampoObligatorio("El nombre es obligatorio de campo");
        }
        if (serviciosAdicionales == null || serviciosAdicionales.isEmpty()) {
            LOGGER.log(Level.SEVERE, "El servicios adicionales es obligatorio de campo");
            throw new CampoObligatorio("El servicios adicionales es obligatorio de campo");
        }
        if (duracion < 0 || duracion == 0) {
            LOGGER.log(Level.SEVERE, "El duracion estos no sean negativos o esta vacio");
            throw new CampoNegativo("El duracion estos no sean negativos o esta vacio");
        }
        if (precio < 0.0 || precio == 0.0) {
            LOGGER.log(Level.SEVERE, "El precio estos no sean negativos o esta vacio");
            throw new CampoNegativo("El precio  estos no sean negativos o esta vacio");
        }
        if (cupoMaxPersona < 0 || cupoMaxPersona == 0) {
            LOGGER.log(Level.SEVERE, "El cupo maximo de las personas estos no sean negativos o esta vacio");
            throw new CampoNegativo("El cupo maximo de las personas estos no sean negativos o esta vacio");
        }
        LocalDateTime hoy = LocalDateTime.now();
        if (fechaDisponibles.isBefore(hoy)) {
            LOGGER.log(Level.SEVERE, "La fecha no puede ser anterior al día actual");
            throw new FechaException("La fecha no puede ser anterior al día actual");
        }
        return true;
    }

    //----------------------ACTUALIZAR-------------------//
    public boolean actualizarPaqueteTuristico(String nombreActual, String nombreNuevo, int duracion, ArrayList<String> serviciosAdicionales, Double precio, int cupoMaxPersona, LocalDateTime fechaDisponibles) throws CampoNegativo, CampoObligatorio, FechaException {
        PaqueteTuristico paqueteTuristico = obtenerPaqueteTuristico(nombreActual);
        boolean esPaqueteTuristicoActualizado = false;
        if (validarPaqueteTuristicoCampoObligatorio(nombreNuevo, duracion, serviciosAdicionales, precio, cupoMaxPersona, fechaDisponibles)) {
            if (paqueteTuristico != null) {
                paqueteTuristico.setNombre(nombreNuevo);
                paqueteTuristico.setPrecio(precio);
                paqueteTuristico.setDuracion(duracion);
                paqueteTuristico.setFechaDisponibles(fechaDisponibles);
                paqueteTuristico.setCupoMaxPersona(cupoMaxPersona);
                paqueteTuristico.setServiciosAdicionales(serviciosAdicionales);
                paqueteTuristico.setServiciosAdicionales(serviciosAdicionales);
                LOGGER.info("El paquete turistico actualizado exitosamente.");
                esPaqueteTuristicoActualizado = true;
            } else {
                LOGGER.severe("No se encontró el paquete turistico para actualizar.");
            }
        }
        return esPaqueteTuristicoActualizado;
    }

    private PaqueteTuristico obtenerPaqueteTuristico(String nombreActual) {
        return obtenerPaqueteTuristicoAux(nombreActual, 0);
    }

    private PaqueteTuristico obtenerPaqueteTuristicoAux(String nombreActual, int index) {
        if (index >= listaPaqueteTuristicos.size()) {
            return null;
        }
        PaqueteTuristico paqueteTuristico = listaPaqueteTuristicos.get(index);
        if (paqueteTuristico.getNombre().equals(nombreActual)) {
            return paqueteTuristico;
        }
        return obtenerPaqueteTuristicoAux(nombreActual, index + 1);
    }

    //----------------------ELIMINAR---------------------//
    public boolean eliminarPaqueteTuristico(String nombre) {
        PaqueteTuristico paqueteTuristico = obtenerPaqueteTuristico(nombre);
        if (paqueteTuristico != null) {
            getListaPaqueteTuristicos().remove(paqueteTuristico);
            LOGGER.info("El paquete turistico eliminado exitosamente. " + nombre);
            return true;
        }
        LOGGER.severe("El paquete turistico no se puede eliminar");
        return false;
    }
    public void agregarDestino(String nombre, double precio) {
        Destino nuevoDestino = new Destino("kjk" ,Ciudades.APARTADO, "jjl",null ,Clima.CALIDO);
        listaDestinos.add(nuevoDestino);
    }

}
