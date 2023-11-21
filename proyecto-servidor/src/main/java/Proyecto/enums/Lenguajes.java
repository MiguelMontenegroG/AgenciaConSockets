package Proyecto.enums;

import lombok.Getter;
import lombok.Setter;

public enum Lenguajes {
    INGLES("Ingles"),
    CHINO_MANDARIN("Chino Mandarin"),
    HINDI("Hindi"),
    ESPAÑOL("Español"),
    FRANCES("Frances"),
    ARABE_ESTANDAR_MODERNO("Arabe Estandar Moderno"),
    BENGALI("Bengali"),
    PORTUGUES("Portugues"),
    RUSO("Ruso"),
    URDU("Urdu"),
    INDONESIO("Indonesio"),
    ALEMAN("Aleman"),
    JAPONES("Japones"),
    MARATI("Marati"),
    TELUGU("Telugu"),
    TURCO("Turco"),
    TAMIL("Tamil"),
    CHINO_YUE("Chino Yue"),
    VIETNAMITA("Vietnamita"),
    TAGALO("Tagalo"),
    CHINO_WU("Chino Wu"),
    COREANO("Coreano"),
    PERSA_IRANI("Persa Irani"),
    HAUSA("Hausa"),
    ARABE_EGIPCIO("Arabe Egipcio"),
    SUAJILI("Suajili"),
    JAVANES("Javanes"),
    ITALIANO("Italiano"),
    PANYABI_OCCIDENTAL("Panyabi Occidental"),
    CANARES("Canares"),
    GUYARATI("Guyarati"),
    TAILANDES("Tailandes"),
    AMARICO("Amarico"),
    BHOSHPURI("Bhoshpuri"),
    PANYABI("Panyabi"),
    CHINO_MIN_NAN("Chino Min Nan"),
    CHINO_JIN("Chino Jin"),
    YORUBA("Yoruba"),
    CHINO_HAKKA("Chino Hakka"),
    BIRMANO("Birmano"),
    ARABE_SUDANES("Arabe Sudanes"),
    POLACO("Polaco"),
    ARABE_ARGELINO("Arabe Argelino"),
    LINGALA("Lingala");

    @Setter
    @Getter
    String nombreLenguajes;

    Lenguajes (String nombreLenguajes){this.nombreLenguajes=nombreLenguajes;}
    public static Lenguajes obtenerNombreLenguajes(String nombreLenguajes) {
        for (Lenguajes c : Lenguajes.values()) {
            if (c.getNombreLenguajes().equalsIgnoreCase(nombreLenguajes)) {
                return c;
            }
        }
        return null;
    }
}