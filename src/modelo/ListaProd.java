/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.util.ArrayList;

public class ListaProd {
    public ArrayList<Producto> lp;
    
    public ListaProd(){
         lp = new ArrayList();
    }
    public int longitud(){
        return lp.size();
    }
    
    public void insertar(Producto p){
        lp.add(p);
    }
    
    public Producto obtener(int p){
        return lp.get(p);
    }
    
    public void eliminar(Categoria cat){
        for (int i = 0; i < lp.size(); i++){
            if (lp.get(i).nombre.equals(cat.nom)){
                lp.remove(i);
            }
        }
    }
}
