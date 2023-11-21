package Proyecto.agenciaViajes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import Proyecto.utils.ArchivoUtils;

public class ServidorInterfaz extends JFrame {
    private DefaultTableModel model;
    private JTable table;
    private JTextField textField;

    public void cargarDatosDesdeArchivo(DefaultTableModel model, String ruta) {
        ArrayList<String> lista = null;
        try {
            lista = ArchivoUtils.leerArchivoBufferedReader(ruta);
            for (String linea : lista) {
                String[] datos = linea.split(",");
                model.addRow(datos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarInterfaz() {
        setTitle("Ventana Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        JPanel panel = new JPanel(new GridLayout(5, 1));

        JButton clientesButton = new JButton("Clientes");
        clientesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirVentanaClientes();
            }
        });

        JButton administradoresButton = new JButton("Administradores");
        JButton paquetesButton = new JButton("Paquetes");
        JButton guiasCalButton = new JButton("GuiasCal");
        JButton paquetesCalButton = new JButton("PaquetesCal");

        panel.add(clientesButton);
        panel.add(administradoresButton);
        panel.add(paquetesButton);
        panel.add(guiasCalButton);
        panel.add(paquetesCalButton);

        add(panel); // Agrega el panel al JFrame

        setVisible(true);
    }


    public ServidorInterfaz() {



        // Otros métodos de la clase
    }

    private List<String> getDatosDesdeTabla(DefaultTableModel model) {
        List<String> datos = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            String fila = "";
            for (int j = 0; j < model.getColumnCount(); j++) {
                fila += model.getValueAt(i, j);
                if (j < model.getColumnCount() - 1) {
                    fila += ",";
                }
            }
            datos.add(fila);
        }
        return datos;
    }

    private void abrirVentanaClientes() {
        JFrame ventanaClientes = new JFrame("Ventana de Clientes");
        ventanaClientes.setSize(800, 600);
        ventanaClientes.setLocationRelativeTo(null);

        JPanel panelClientes = new JPanel(new BorderLayout());

        String[] columnas = {"Nombre", "Contraseña", "Nombre Completo", "Correo", "Teléfono", "Dirección"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        JTable tablaClientes = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tablaClientes);

        cargarDatosDesdeArchivo (model, "src/main/resources/persistencia/clientes.txt");

        JPanel panelDatos = new JPanel(new GridLayout(7, 2));
        JTextField[] camposTexto = new JTextField[6];

        for (int i = 0; i < 6; i++) {
            JLabel label = new JLabel(columnas[i] + ":");
            camposTexto[i] = new JTextField();
            panelDatos.add(label);
            panelDatos.add(camposTexto[i]);
        }

        JButton addButton = new JButton("Añadir");
        JButton deleteButton = new JButton("Borrar");
        JButton updateButton = new JButton("Actualizar");


        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object[] fila = new Object[6];
                for (int i = 0; i < 6; i++) {
                    fila[i] = camposTexto[i].getText();
                }
                model.addRow(fila);

                try {
                    ArchivoUtils.escribirArchivoBufferedWriter("src/main/resources/persistencia/clientes.txt", getDatosDesdeTabla(model), true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaClientes.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    model.removeRow(filaSeleccionada);

                    List<String> datosActualizados = getDatosDesdeTabla(model);

                    try {
                        ArchivoUtils.escribirArchivoBufferedWriter("src/main/resources/persistencia/clientes.txt", datosActualizados, false);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaClientes.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    for (int i = 0; i < 6; i++) {
                        model.setValueAt(camposTexto[i].getText(), filaSeleccionada, i);
                    }
                    try {
                        ArchivoUtils.escribirArchivoBufferedWriter("src/main/resources/persistencia/clientes.txt", getDatosDesdeTabla(model), false);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                while (model.getRowCount() > 0) {
                    model.removeRow(0);
                }
                cargarDatosDesdeArchivo(model, "src/main/resources/persistencia/clientes.txt");
            }
        });


        panelDatos.add(addButton);
        panelDatos.add(deleteButton);
        panelDatos.add(updateButton);

        panelClientes.add(scrollPane, BorderLayout.CENTER);
        panelClientes.add(panelDatos, BorderLayout.NORTH);

        ventanaClientes.add(panelClientes);
        ventanaClientes.setVisible(true);


    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ServidorInterfaz interfaz = new ServidorInterfaz();
            interfaz.mostrarInterfaz();
        });
    }
}
