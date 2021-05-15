package org.example.tpvcafeteria.modelo;

import java.io.Serializable;

public class Comanda implements Serializable {
    String id;
    String idMesa;
    String empleado;
    String hora;

    public Comanda(){
        this.id = "";
        this.idMesa = "";
        this.empleado = "";
        this.hora = "";
    }

    public Comanda(String id, String idMesa, String empleado, String hora){
        this.id = id;
        this.idMesa = idMesa;
        this.empleado = empleado;
        this.hora = hora;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(String idMesa) {
        this.idMesa = idMesa;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

}
