/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * Clase que analiza una instrucción del modo de direccionamiento inmediato.
 * @author Ari
 */
public class Inmediato {
    Hashtable<String, String> Inmediato;
    Hashtable<String, Integer> BytesInmediato;
    
    /**
     * En este método se analiza la línea que supuestamente contiene una instrucción del modo de 
     * direccionamiento inmediato.
     * @param line linea que supuestamente contiene una instrucción del modo de direccionamiento INM.
     * @return OPCODE de la instrucción procesada o mensaje de error.
     */
    public String AnalizarLinea(String line){
        //Se llama a mnemónicos para recuperar la lista de instrucciones del modo Inmediato
        Mnemonicos m=new Mnemonicos();
        Inmediato=m.LeerOpcodeINM();
        BytesInmediato=m.LeerBytesINM();
        
        //Palabra nos sirve para separar la linea en palabras y contabilizarlas
        String palabra;
        int numPalabra=0;
        
        //Esta cadena será la que se devolverá si la sintaxis es correcta
        String newLine="";
        
        //Se leen las palabras de la línea
        StringTokenizer st = new StringTokenizer (line);
        while (st.hasMoreTokens())
        {
            palabra = st.nextToken();
            numPalabra++;
            String instruccion="";
            
            /*Se verifica que la primera palabra sea una instrucción del modo de direccionamiento inmediato, y de 
            ser así, se concatena al inicio de la cadena que se desea devolver*/
            if(numPalabra==1){
                palabra=palabra.toLowerCase();
                if (Inmediato.containsKey(palabra)){
                    instruccion=palabra;
                    newLine=newLine.concat(Inmediato.get(palabra));
                }
            }else {
                return "Error 004: Mnemónico inexistente";
            }
            
            //Se verifica el operando, el cual corresponde a la segunda palabra de la línea y debe comenzar con #
            if((numPalabra==2)&&(palabra.startsWith("#"))){
                //En caso de que sea un operando en número hexadecimal
                if(palabra.startsWith("#$")){
                    //Se utiliza la cadena aux para separar #$ del operando
                    String aux=palabra.concat(palabra.substring(2));
                    //Se comprueba que la longitud del operando coincida con el necesario por la instrucción
                    if(BytesInmediato.get(instruccion)==(aux.length()/2)){
                        //Si coinciden, se agrega a la cadena que será regresada
                        newLine=newLine.concat(aux);
                    }else{
                        return "Error 007: Magnitud de operando errónea";
                    }
                    
                //En caso de que sea un caracter
                }else if(palabra.startsWith("#'")){
                    //Se utiliza la cadena aux para separar #' del operando
                    String aux=palabra.concat(palabra.substring(2));
                    //Se comprueba si el operando es un solo caracter
                    if(aux.length()==1){
                        char character = aux.charAt(0);
                        //Se convierte el caracter a decimal
                        int ascii = (int) character;
                        //Se convierte el decimal a hexadecimal y se guarda como cadena de mayúsculas
                        String ASCII= Integer.toHexString(ascii).toUpperCase();
                        //Se anexa a la cadena que regresará el método
                        newLine=newLine.concat(ASCII);
                    }else{
                        return "Error 007: Magnitud de operando errónea";
                    }
                //En caso de que sea un operando en número decimal
                }else{
                    //Se utiliza la cadena aux para separar # del operando
                    String aux=palabra.concat(palabra.substring(1));
                    //Se convierte a número decimal
                    int op=Integer.parseInt(aux);
                    //Se convierte el número decimal a hexadecimal y como cadena
                    String operando= Integer.toHexString(op).toUpperCase();
                    //Se comprueba que la longitud del operando coincida con el necesario por la instrucción
                    if(BytesInmediato.get(instruccion)==(operando.length()/2)){
                        //Si coinciden, se agrega a la cadena que será regresada
                        newLine=newLine.concat(operando);
                    }else{
                        return "Error 007: Magnitud de operando errónea";
                    }
                }
            }else{
                return "Error 005: Instrucción carece de operando";
            }
            if((numPalabra==3)&&(palabra.startsWith("*"))){
                //Es un comentario, no es necesario realizar nada más
            }else{
                return "Error: Sintaxis incorrecta";
            }
        }
        if (numPalabra<2){
            return "Error 005: Instrucción carece de operando";
        }
        return newLine;
    }
}
