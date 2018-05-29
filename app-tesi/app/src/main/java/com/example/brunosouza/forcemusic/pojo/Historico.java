package com.example.brunosouza.forcemusic.pojo;

public class Historico {
    private String data, hora;
    private int id;

    public Historico(String data, String hora, int id) {
        this.data = data;
        this.hora = hora;
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}