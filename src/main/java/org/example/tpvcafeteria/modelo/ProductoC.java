package org.example.tpvcafeteria.modelo;

public class ProductoC {
    String id;
    String nom;
    String info;

    public ProductoC(){
        this.id = "";
        this.nom = "";
        this.info = "";
    }

    public ProductoC(String id, String nom, String info){
        this.id = id;
        this.nom = nom;
        this.info = info;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
