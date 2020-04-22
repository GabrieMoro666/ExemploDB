package com.example.exemplodb.banco.model;

public class Pais {
    private Long id;
    private String pais;
    private Integer estados;

    public Pais(){
        this.id = 0L;
        this.pais = "";
        this.estados = 0;
    }

    public Pais(Long id, String pais, Integer estados){
        this.id = id;
        this.pais = pais;
        this.estados = estados;
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

    public void setEstados(Integer estados) {
        this.estados = estados;
    }

    public Integer getEstados() {
        return estados;
    }
}
