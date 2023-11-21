package Proyecto.enums;

import lombok.Getter;
import lombok.Setter;

public enum Clima {
    CALIDO("Calido"),
    TEMPLADOS("Templados"),
    FRIO("Frio"),
    PARAMOS("Paramos"),
    ZONAS_GLACIARES("Zonas glaciares"),
    SELVAS_TROPICALES("Selvas tropicales"),
    TROPICAL_ESTEPA("Tropical estepa");

    @Setter
    @Getter
    private String nombreClima;
    Clima(String nombreClima){this.nombreClima= nombreClima;}

    public static Clima obtenerNombreClima(String nombreClima) {
        for (Clima c : Clima.values()) {
            if (c.getNombreClima().equalsIgnoreCase(nombreClima)) {
                return c;
            }
        }
        return null;
    }
}
