/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.util.ArrayList;

public class ProductosComanda {
    public ArrayList<ProductoC> listaProd;
    
    public ProductosComanda(){
        listaProd = new ArrayList();
    }
    
    public int longitud(){
        return listaProd.size();
    }
    
    public void insertar(ProductoC p){
        listaProd.add(p);
    }
    
    public ProductoC obtener(int p){
        return listaProd.get(p);
    }
    
    public void eliminar(ProductoC p){
        for (int i = 0; i < listaProd.size(); i++){
            if (listaProd.get(i).getNom().equals(p.getNom())){
                listaProd.remove(i);
            }
        }
    }
}
