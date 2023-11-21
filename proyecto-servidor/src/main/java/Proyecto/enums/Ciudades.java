package Proyecto.enums;

import lombok.Getter;
import lombok.Setter;

public enum Ciudades {
    BOGOTA("Bogota"),
    MEDELLIN("Medellin"),
    CALI("Cali"),
    BARRANQUILLA("Barranquilla"),
    CARTAGENA("Cartagena"),
    SOLEDAD("Soledad"),
    CUCUTA("Cucuta"),
    IBAGUE("Ibague"),
    SOACHA("Soacha"),
    VILLAVICENCIO("Villavicencio"),
    BUCARAMANGA("Bucaramanga"),
    SANTA_MARTA("Santa Marta"),
    VALLEDUPAR("Valledupar"),
    BELLO("Bello"),
    PEREIRA("Pereira"),
    MONTERIA("Monteria"),
    PASTO("Pasto"),
    BUENAVENTURA("Buenaventura"),
    MANIZALES("Manizales"),
    NEIVA("Neiva"),
    PALMIRA("Palmira"),
    RIOHACHA("Riohacha"),
    SINCELEJO("Sincelejo"),
    POPAYAN("Popayan"),
    ITAGUI("Itagui"),
    FLORIDABLANCA("Floridablanca"),
    ENVIGADO("Envigado"),
    TULUA("Tulua"),
    SAN_ANDRES_DE_TUMACO("San Andres de Tumaco"),
    DOSQUEBRADAS("Dosquebradas"),
    APARTADO("Apartado"),
    TUNJA("Tunja"),
    GIRON("Giron"),
    URIBIA("Uribia"),
    BARRANCABERMEJA("Barrancabermeja"),
    FLORENCIA("Florencia"),
    TURBO("Turbo"),
    MAICAO("Maicao"),
    PIEDECUESTA("Piedecuesta"),
    YOPAL("Yopal");

    @Setter
    @Getter
    private String nombreCiudad;
    Ciudades(String nombreCiudad){
        this.nombreCiudad = nombreCiudad;
    }

    public static Ciudades obtenerNombreCiudades(String nombreCiudad) {
        for (Ciudades c : Ciudades.values()) {
            if (c.getNombreCiudad().equalsIgnoreCase(nombreCiudad)) {
                return c;
            }
        }
        return null;
    }
}
