package Tarea2.backend;

public class Item {
        private String enunciado;
        private String[] opciones;
        private String respuestaCorrecta;
        private String tipoItem;      // "multiple" o "vf"
        private String nivelBloom;    // Recordar, Entender, etc.
        private int tiempoEstimado;
        private String respuestaUsuario;

        public Item(String enunciado, String[] opciones, String respuestaCorrecta,
                    String tipoItem, String nivelBloom, int tiempoEstimado) {
            this.enunciado = enunciado;
            this.opciones = opciones;
            this.respuestaCorrecta = respuestaCorrecta;
            this.tipoItem = tipoItem;
            this.nivelBloom = nivelBloom;
            this.tiempoEstimado = tiempoEstimado;
        }

        public String getEnunciado() {
            return enunciado;
        }

        public String[] getOpciones() {
            return opciones;
        }

        public String getRespuestaCorrecta() {
            return respuestaCorrecta;
        }

        public String getTipoItem() {
            return tipoItem;
        }

        public String getNivelBloom() {
            return nivelBloom;
        }

        public int getTiempoEstimado() {
            return tiempoEstimado;
        }

        public String getRespuestaUsuario() {
            return respuestaUsuario;
        }

        public void setRespuestaUsuario(String respuestaUsuario) {
            this.respuestaUsuario = respuestaUsuario;
        }
    }

