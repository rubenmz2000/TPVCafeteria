/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

public class ProductoC {
    String id;
    String nom;
    double prec;
    
    public ProductoC(){
        this.id = "";
        this.nom = "";
        this.prec = 0;
    }
    
    public ProductoC(String id, String nom, double prec){
        this.id = id;
        this.nom = nom;
        this.prec = prec;
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

    public double getPrec() {
        return prec;
    }

    public void setPrec(double prec) {
        this.prec = prec;
    }
    
    
}
