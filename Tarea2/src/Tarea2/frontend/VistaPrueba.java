package Tarea2.frontend;

import Tarea2.backend.GestorPrueba;
import Tarea2.backend.Item;
import Tarea2.backend.Suscriptor;

import javax.swing.*;
import java.awt.*;

public class VistaPrueba extends JFrame implements Suscriptor {
    private GestorPrueba gestor;
    private JLabel etiquetaEnunciado;
    private JPanel panelOpciones;
    private JButton botonAnterior;
    private JButton botonSiguiente;
    private ButtonGroup grupoOpciones;

    public VistaPrueba(GestorPrueba gestor) {
        this.gestor = gestor;
        gestor.suscribir(this);

        setTitle("Aplicación de Prueba");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        etiquetaEnunciado = new JLabel("Enunciado", SwingConstants.CENTER);
        etiquetaEnunciado.setFont(new Font("Arial", Font.BOLD, 14));
        add(etiquetaEnunciado, BorderLayout.NORTH);

        panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
        add(panelOpciones, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        botonAnterior = new JButton("Anterior");
        botonSiguiente = new JButton("Siguiente");

        botonAnterior.addActionListener(e -> {
            registrarRespuestaSeleccionada();
            gestor.retroceder();
        });

        botonSiguiente.addActionListener(e -> {
            registrarRespuestaSeleccionada();
            if (esUltimaPregunta()) {
                gestor.enviarPrueba();
                dispose();
            } else {
                gestor.avanzar();
            }
        });

        panelBotones.add(botonAnterior);
        panelBotones.add(botonSiguiente);
        add(panelBotones, BorderLayout.SOUTH);

        // Mostrar la primera pregunta al crear la vista
        mostrarPregunta(gestor.getItemActual());
        actualizarBotones();

        setVisible(true);
    }

    private void mostrarPregunta(Item item) {
        etiquetaEnunciado.setText(item.getEnunciado());
        panelOpciones.removeAll();
        grupoOpciones = new ButtonGroup();

        for (String opcion : item.getOpciones()) {
            JRadioButton boton = new JRadioButton(opcion);
            boton.setActionCommand(opcion);
            if (opcion.equals(item.getRespuestaUsuario())) {
                boton.setSelected(true);
            }
            grupoOpciones.add(boton);
            panelOpciones.add(boton);
        }

        panelOpciones.revalidate();
        panelOpciones.repaint();
    }

    private void registrarRespuestaSeleccionada() {
        if (grupoOpciones != null && grupoOpciones.getSelection() != null) {
            String seleccion = grupoOpciones.getSelection().getActionCommand();
            gestor.registrarRespuesta(seleccion);
        }
    }

    private boolean esUltimaPregunta() {
        return gestor.getIndiceActual() == gestor.getPrueba().getCantidadItems() - 1;
    }

    private void actualizarBotones() {
        botonAnterior.setEnabled(gestor.getIndiceActual() > 0);
        botonSiguiente.setText(esUltimaPregunta() ? "Enviar respuestas" : "Siguiente");
    }

    @Override
    public void actualizar(String evento, Object data) {
        if (evento.equals("cambioItem")) {
            this.mostrarPregunta((Item) data);
            this.actualizarBotones();
        } else if (evento.equals("pruebaCargada")) {
            this.mostrarPregunta(gestor.getItemActual());
            this.actualizarBotones();
        } else if (evento.equals("revisionFinal")) {
            new VistaRevision(this.gestor);
        }
    }
}
