package com.example.exemplodb.adapterGrade.model;

public class Row extends IRecyclerViewItem {
    private String pais;
    private String estado;

    public Row(String pais, String estado){
        this.pais = pais;
        this.estado = estado;
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
