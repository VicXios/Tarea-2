package Tarea2.backend;

import java.util.*;

public class Prueba {
    private List<Item> items;

    public Prueba(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getCantidadItems() {
        return items.size();
    }

    public int getTiempoTotal() {
        return items.stream().mapToInt(Item::getTiempoEstimado).sum();
    }

    public Map<String, Integer> obtenerEstadisticasPorNivelBloom() {
        Map<String, int[]> conteo = new HashMap<>();
        for (Item item : items) {
            conteo.putIfAbsent(item.getNivelBloom(), new int[2]);
            conteo.get(item.getNivelBloom())[0]++;
             {if (item.getRespuestaUsuario() != null && item.getRespuestaUsuario().equals(item.getRespuestaCorrecta()))
                conteo.get(item.getNivelBloom())[1]++;
            }
        }

        Map<String, Integer> porcentaje = new HashMap<>();
        for (String nivel : conteo.keySet()) {
            int total = conteo.get(nivel)[0];
            int correctas = conteo.get(nivel)[1];
            porcentaje.put(nivel, (int)((correctas * 100.0) / total));
        }
        return porcentaje;
    }

    public Map<String, Integer> obtenerEstadisticasPorTipoItem() {
        Map<String, int[]> conteo = new HashMap<>();
        for (Item item : items) {
            conteo.putIfAbsent(item.getTipoItem(), new int[2]);
            conteo.get(item.getTipoItem())[0]++;
            if (item.getRespuestaUsuario() != null && item.getRespuestaUsuario().equals(item.getRespuestaCorrecta())) {
                conteo.get(item.getTipoItem())[1]++;
            }
        }

        Map<String, Integer> porcentaje = new HashMap<>();
        for (String tipo : conteo.keySet()) {
            int total = conteo.get(tipo)[0];
            int correctas = conteo.get(tipo)[1];
            porcentaje.put(tipo, (int)((correctas * 100.0) / total));
        }
        return porcentaje;
    }
}
