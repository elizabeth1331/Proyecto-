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
    public String agregarEtiqueta(String etiqueta, int pos){
       if(Etiquetas.containsKey(etiqueta)){
           String salida= "\033[0;1m"+ etiqueta +"\u001B[0m"+ "\u001B[31m Error: Esta etiqueta está duplicada \u001B[0m";
           
           //Guardamos la salida de la primer pasada
           Output outPut = new Output();
           outPut.mensaje = salida;
           metodosDeLectura.salidas.add(outPut);
           
           return etiqueta + "\n\t\t\t^Error: Esta etiqueta está duplicada";
       }
       Etiquetas.put(etiqueta, pos);
       return "";
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
