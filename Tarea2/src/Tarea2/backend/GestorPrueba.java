package Tarea2.backend;

import java.io.*;
import java.util.*;

public class GestorPrueba {
    private List<Suscriptor> suscriptores = new ArrayList<>();
    private Prueba prueba;
    private int indiceActual = 0;

    public void suscribir(Suscriptor s) {
        suscriptores.add(s);
    }

    private void notificar(String evento, Object data) {
        for (Suscriptor s : suscriptores) {
            s.actualizar(evento, data);
        }
    }

    public void cargarPruebaDesdeArchivo(String ruta) {
        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {
            List<Item> items = new ArrayList<>();
            String linea;
            lector.readLine(); // saltar cabecera

            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (partes.length != 6) continue;

                String tipo = partes[0].trim();
                String enunciado = partes[1].trim();
                String[] opciones = partes[2].replace("\"", "").split(",");
                String respuesta = partes[3].trim();
                String nivel = partes[4].trim();
                int tiempo = Integer.parseInt(partes[5].trim());

                items.add(new Item(enunciado, opciones, respuesta, tipo, nivel, tiempo));
            }

            this.prueba = new Prueba(items);
            this.indiceActual = 0;
            notificar("pruebaCargada", prueba);

        } catch (Exception e) {
            notificar("errorCarga", e.getMessage());
        }
    }

    public Prueba getPrueba() {
        return prueba;
    }

    public int getIndiceActual() {
        return indiceActual;
    }

    public Item getItemActual() {
        if (this.prueba == null || this.prueba.getItems().isEmpty()) {
            throw new IllegalStateException("No hay ítems cargados en la prueba.");
        }

        return prueba.getItems().get(indiceActual);
    }

    public void avanzar() {
        if (indiceActual < prueba.getCantidadItems() - 1) {
            indiceActual++;
            notificar("cambioItem", getItemActual());
        }
    }

    public void retroceder() {
        if (indiceActual > 0) {
            indiceActual--;
            notificar("cambioItem", getItemActual());
        }
    }

    public void registrarRespuesta(String respuesta) {
        prueba.getItems().get(indiceActual).setRespuestaUsuario(respuesta);
    }

    public void enviarPrueba() {
        notificar("revisionFinal", prueba);
    }
}