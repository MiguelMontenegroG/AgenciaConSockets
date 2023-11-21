package Proyecto.model;

import Proyecto.enums.Calificacion;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CalificacionDestino implements Serializable  {

    private String destino;
    private Calificacion nota;
    private String comentario;
}
