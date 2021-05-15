/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.util.ArrayList;

public class ListCat {
    public ArrayList<Categoria> categorias;
    public ListCat(){
        categorias = new ArrayList<>();
    }
    
    public int longitud(){
        return categorias.size();
    }
    
    public void insertar(Categoria cat){
        categorias.add(cat);
    }
    
    public Categoria obtener(int p){
        return categorias.get(p);
    }
    
    public void eliminar(Categoria cat){
        for (int i = 0; i < categorias.size(); i++){
            if (categorias.get(i).nom.equals(cat.nom)){
                categorias.remove(i);
            }
        }
    }
}
