/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.util.Hashtable;

/**
 *
 * @author 81664
 */
public class Var_Cons_Etiq {
    
    Hashtable<String, Integer> Etiquetas;
    
    public Var_Cons_Etiq(){
        this.Etiquetas = new Hashtable();
    }
    
    /**
     * Guarda una etiqueta y su posición
     * @param etiqueta Es la etiqueta a guardar
     * @param pos Es la posisión (número de línea) en la que se enuentra la instrucción que le sigue a la etiqueta
     */
    public void agregarEtiqueta(String etiqueta, int pos){
       Etiquetas.put(etiqueta, pos);
    }
    
    public int buscarEtiqueta(String etiqueta){
        int pos = 0;
        if(Etiquetas.containsKey(etiqueta)){
            pos =  Etiquetas.get(etiqueta);
            return pos;
        }else{
            return pos;
        }
    }
}
