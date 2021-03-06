/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class ListComanda implements Serializable {
    public ArrayList<Comanda> comandas;
    public ListComanda(){
        comandas = new ArrayList<>();
    }
    
    public int longitud(){
        return comandas.size();
    }
    
    public void insertar(Comanda cat){
        comandas.add(cat);
    }
    
    public Comanda obtener(int p){
        return comandas.get(p);
    }
    
    public void eliminar(Comanda cat){
        for (int i = 0; i < comandas.size(); i++){
            if (comandas.get(i).id.equals(cat.id)){
                comandas.remove(i);
            }
        }
    }
}
