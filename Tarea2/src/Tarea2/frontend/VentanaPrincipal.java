package Tarea2.frontend;

import Tarea2.backend.GestorPrueba;
import Tarea2.backend.Prueba;
import Tarea2.backend.Suscriptor;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class VentanaPrincipal extends JFrame implements Suscriptor {
    private GestorPrueba gestor;
    private JLabel etiquetaInfo;
    private JButton botonCargarArchivo;
    private JButton botonIniciar;

    public VentanaPrincipal(GestorPrueba gestor) {
        this.gestor = gestor;
        gestor.suscribir(this);

        setTitle("Sistema de Evaluación Bloom");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        etiquetaInfo = new JLabel("Cargue una prueba para comenzar.", SwingConstants.CENTER);
        etiquetaInfo.setFont(new Font("Arial", Font.PLAIN, 14));

        botonCargarArchivo = new JButton("Cargar archivo");
        botonCargarArchivo.addActionListener(e -> cargarArchivo());

        botonIniciar = new JButton("Iniciar prueba");
        botonIniciar.setEnabled(false);
        botonIniciar.addActionListener((e) -> {
            if (gestor.getPrueba() != null && !gestor.getPrueba().getItems().isEmpty()) {
                new VistaPrueba(gestor);
            } else {
                this.etiquetaInfo.setText("La prueba no se ha cargado correctamente.");
            }
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonCargarArchivo);
        panelBotones.add(botonIniciar);

        add(etiquetaInfo, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void cargarArchivo() {
        JFileChooser selector = new JFileChooser();
        int resultado = selector.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();
            gestor.cargarPruebaDesdeArchivo(archivo.getAbsolutePath());
        }
    }

    @Override
    public void actualizar(String evento, Object data) {
        if (evento.equals("pruebaCargada")) {
            Prueba prueba = (Prueba) data;
            etiquetaInfo.setText("Ítems: " + prueba.getCantidadItems()
                    + " | Tiempo total: " + prueba.getTiempoTotal() + " seg.");
            botonIniciar.setEnabled(true);
        } else if (evento.equals("errorCarga")) {
            etiquetaInfo.setText("Error al cargar archivo: " + data.toString());
        }
    }
}
