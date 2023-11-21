package Proyecto.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class PaqueteTuristico implements Serializable {

    private String nombre;
    private int duracion;
    private ArrayList<String> serviciosAdicionales;
    private Double precio;
    private int cupoMaxPersona;
    private LocalDateTime fechaDisponibles;
    //-------clase---//
    @Getter
    private ArrayList<Destino> destinos;

    public PaqueteTuristico(String nombre, int duracion, ArrayList<String> serviciosAdicionales,
                            Double precio, int cupoMaxPersona, LocalDateTime fechaDisponibles) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.serviciosAdicionales = serviciosAdicionales;
        this.precio = precio;
        this.cupoMaxPersona = cupoMaxPersona;
        this.fechaDisponibles = fechaDisponibles;
        this.destinos = new ArrayList<>();
    }


    public void agregarDestino(Destino destino) {
        if (destinos == null) {
            destinos = new ArrayList<>();
        }
        destinos.add(destino);
    }

    public void setDestinos(ArrayList<Destino> destinos) {

        this.destinos = destinos;
    }

    public String getDestinosComoCadena() {
        return destinos.stream().map(Destino::getNombre).collect(Collectors.joining(", "));
    }

}


