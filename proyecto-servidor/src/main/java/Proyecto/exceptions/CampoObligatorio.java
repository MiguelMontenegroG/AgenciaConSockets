package Proyecto.exceptions;

public class CampoObligatorio extends Throwable {
    public CampoObligatorio(String mensaje) {
        super(mensaje);
    }
}
