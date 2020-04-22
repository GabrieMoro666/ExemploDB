package com.example.exemplodb.banco.model;

public class Estado {
    private Long   id;
    private String pais;
    private String estado;

    public Estado(){
        id = 0L;
        pais = "";
        estado = "";
    }

    public Estado(Long id, String pais, String estado){
        this.id = id;
        this.pais = pais;
        this.estado = estado;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getPais() {
        return pais;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
}
