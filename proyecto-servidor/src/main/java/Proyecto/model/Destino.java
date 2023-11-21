package Proyecto.model;


import Proyecto.enums.Ciudades;
import Proyecto.enums.Clima;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Destino implements Serializable {

    private String nombre;
    private Ciudades ciudad;
    private String descripcion;
    private ArrayList<String> imagenes;
    private Clima clima;


    public Destino(String nombre, Ciudades ciudad,String descripcion, Clima clima) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this .descripcion = descripcion;
        this.clima = clima;

    }

    public Destino(String nombreDestino) {
    }
}
