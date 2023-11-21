package Proyecto.model;

import lombok.*;

@Setter
@Getter
@ToString
public class Cliente extends Persona {

    private String telefono;
    private String direccionResidencia;
    public Cliente(String nombreCompleto, String identificacion, String correo, String pasword, String telefono, String direccionResidencia) {
        super(nombreCompleto, identificacion, correo, pasword);
        this.telefono = telefono;
        this.direccionResidencia = direccionResidencia;
    }

}
