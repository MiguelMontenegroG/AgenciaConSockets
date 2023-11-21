package Proyecto.model;


import Proyecto.enums.EstadoReserva;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reserva implements Serializable {
    //------atributos----//
private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaPlanificadaViaje;
    private String clienteInvolucrado;
    private int cantidadPersonaViejan;
    private String paqueteTuristicoSeleccionado;
    private String guiaTutistico;
    //---------clase--------//
    private EstadoReserva estandoReserva;
    private GuiaTuristico guiaTuristicoReserva;
    private Cliente clienteReserva;
    private PaqueteTuristico paqueteTuristico;

    public String getGuiaTutistico() {
        return guiaTutistico;
    }

    public void setGuiaTutistico(String guiaTutistico) {
        this.guiaTutistico = guiaTutistico;
    }

    public void setEstadoReserva(EstadoReserva estadoReserva) {
    }

    public void setGuiaTuristico(String guiaTuristico) {
    }

    public EstadoReserva getEstandoReserva() {
        return estandoReserva;
    }

    public void setEstandoReserva(EstadoReserva estandoReserva) {
        this.estandoReserva = estandoReserva;
    }

    public GuiaTuristico getGuiaTuristicoReserva() {
        return guiaTuristicoReserva;
    }

    public void setGuiaTuristicoReserva(GuiaTuristico guiaTuristicoReserva) {
        this.guiaTuristicoReserva = guiaTuristicoReserva;
    }
}