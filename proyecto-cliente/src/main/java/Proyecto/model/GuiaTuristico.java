package Proyecto.model;

import Proyecto.enums.EstadoReserva;
import Proyecto.enums.Lenguajes;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class GuiaTuristico extends Persona{

    private int experiencia;
    private Lenguajes lenguaje;

    public GuiaTuristico(String nombreCompleto, String identificacion, String correo, String password, int experiencia, Lenguajes lenguaje) {
        super(nombreCompleto, identificacion, correo, password);
        this.experiencia = experiencia;
        this.lenguaje = lenguaje;
    }



    public GuiaTuristico(String nombre, String identificacion, String correo, String password, int experiencia, String lenguaje) {
    }

    public GuiaTuristico(LocalDateTime fechaSolicitud, LocalDateTime fechaPlanificadaViaje, String clienteInvolucrado, Integer cantidadPersonaViejan, String paqueteTuristicoSeleccionado, EstadoReserva estandoReserva, String guiaTutistico) {
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public Lenguajes getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(Lenguajes lenguaje) {
        this.lenguaje = lenguaje;
    }
}

