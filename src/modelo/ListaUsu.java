/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.util.ArrayList;

public class ListaUsu {
    public ArrayList<Usuario> usuarios;
    
    public ListaUsu(){
        usuarios = new ArrayList();
    }
    
     public int longitud(){
        return usuarios.size();
    }
    
    public void insertar(Usuario u){
        usuarios.add(u);
    }
    
    public Usuario obtener(int p){
        return usuarios.get(p);
    }
    
    public void eliminar(Usuario u){
        for (int i = 0; i < usuarios.size(); i++){
            if (usuarios.get(i).nombre.equals(u.nombre)){
                usuarios.remove(i);
            }
        }
    }
}
