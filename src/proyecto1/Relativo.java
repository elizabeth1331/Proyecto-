/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * Clase que comprueba si la instrucción pertenece al modo de direccionamiento relativo, y de ser así comprueba su validéz. 
 * @author Lirio
 */
public class Relativo {
    Hashtable<String, String> Relativo;
    
    /**
     * Constructor
     */
    public Relativo(){
        this.Relativo = new Hashtable();
    }
    
    /**
     * Comprueba si se trara de una instrucción del modo de direccionamiento relativo, de ser vaida la existencia de su operando (etiqueta)
     * @param linea Es la línea que se ha leído del archivo
     * @param m Objeto que nos ayuda a recuperar el archivo que contiene las instrucciones del modo de direccionamiento relativo y su OPCODE
     * @return Regresa la línea de error, si es que lo hay
     */
    public String RevisarLinea(String linea, Mnemonicos m, Var_Cons_Etiq VCE, int numLinea){
        
        //Recupera la tabla Hash con el OPCODE y las intrucciones del modo de direccionamiento REL
        Relativo = m.LeerOpcode("ListaRelativo.txt");
       
        //Palabra nos sirve para separar la linea en palabras y contabilizarlas
        String palabra;
        int numPalabra=0;
        
        //Pos no ayuda a guardar la posición de la etiqueta
        int pos = 0;
        int salto = 0;
        
        //Esta cadena será la que se devolverá si no hay errores
        String newLine="";
        
        //Se leen las palabras de la línea
        StringTokenizer st = new StringTokenizer (linea);
        while (st.hasMoreTokens())
        {
            palabra = st.nextToken();
            numPalabra++;
            String instruccion="";
            
            if (numPalabra==1){
                
                palabra=palabra.toUpperCase();
                System.out.println("La palabra es: "+palabra);
                if (Relativo.containsKey(palabra)){
                    System.out.println("Instrucción del modo REL");
                    instruccion = palabra;
                }else{
                    System.out.println("Buscar en otro modo de direccionamiento");
                    return newLine;
                }
            }else if(numPalabra==2){
                //Buscar si existe esa etiqueta                    
                pos = VCE.buscarEtiqueta(palabra);
                if (pos == 0){
                    System.out.println("Error 003: ETIQUETA INEXISTENTE");
                    return "Error 003: ETIQUETA INEXISTENTE";
                }else{
                    if (pos<numLinea){
                        //Caso de salto negativo
                        salto = (numLinea+2)-pos;
                        if (salto <= 127){
                            //Calcular el valor del operando
                        }else{
                            return "Error 008: SALTO RELATIVO MUY LEJANO";
                        }
                    }else{
                        //Caso de salto positivo
                        salto = pos - (numLinea+2);
                        if (salto <= 128){
                            //Calcular el valor del operando
                        }else{
                            return "Error 008: SALTO RELATIVO MUY LEJANO";
                        }
                    }
                }
            }else if((numPalabra==3)&&(palabra.startsWith("*"))){
                //Es un comentario, no es necesario realizar nada más
                return newLine;
            }else{
                return "Error: Sintaxis incorrecta";
            }   
        }
        if (numPalabra<2){
            return "Error 005: INSTRUCCIÓN CARECE DE  OPERANDO(S)";
        }
       return newLine;
    }
}
