package Tarea2.frontend;

import Tarea2.backend.GestorPrueba;
import Tarea2.backend.Prueba;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class VistaRevision extends JFrame {

    public VistaRevision(GestorPrueba gestor) {
        setTitle("Revisión de Respuestas");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea areaResumen = new JTextArea();
        areaResumen.setEditable(false);

        Prueba prueba = gestor.getPrueba();

        areaResumen.append("✓ Resultados por Nivel Bloom:\n");
        for (Map.Entry<String, Integer> entry : prueba.obtenerEstadisticasPorNivelBloom().entrySet()) {
            areaResumen.append("- " + entry.getKey() + ": " + entry.getValue() + "%\n");
        }

        areaResumen.append("\n✓ Resultados por Tipo de Ítem:\n");
        for (Map.Entry<String, Integer> entry : prueba.obtenerEstadisticasPorTipoItem().entrySet()) {
            areaResumen.append("- " + entry.getKey() + ": " + entry.getValue() + "%\n");
        }

        JScrollPane scroll = new JScrollPane(areaResumen);
        add(scroll, BorderLayout.CENTER);

        JButton botonCerrar = new JButton("Cerrar");
        botonCerrar.addActionListener(e -> dispose());

        JPanel panelBoton = new JPanel();
        panelBoton.add(botonCerrar);
        add(panelBoton, BorderLayout.SOUTH);

        setVisible(true);
    }
}
