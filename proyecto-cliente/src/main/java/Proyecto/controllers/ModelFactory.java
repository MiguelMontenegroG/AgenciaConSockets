package Proyecto.controllers;

import Proyecto.model.Destino;

import java.util.ArrayList;

public class ModelFactory {
    private static ModelFactory instancia;
    private ArrayList<Destino> listaDestinos;

    // Constructor privado para evitar instanciación directa
    private ModelFactory() {
        // Inicialización de la fábrica de modelos
        listaDestinos = new ArrayList<>();
    }

    // Método estático para obtener la instancia única de la fábrica de modelos
    public static ModelFactory getInstance() {
        if (instancia == null) {
            instancia = new ModelFactory();
        }
        return instancia;
    }

    // Resto de métodos y atributos de la fábrica de modelos
    public ArrayList<Destino> getListaDestinos() {
        return listaDestinos;
    }

    public void agregarDestino(Destino destino) {
        listaDestinos.add(destino);
    }


}