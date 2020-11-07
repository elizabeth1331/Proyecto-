/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 *
 * @author Eliss
 */
public class Indexado {
    
    Hashtable<String, String> IndexadoX;
    Hashtable<String, Integer> BytesIndexadoX;
    
    Hashtable<String, String> IndexadoY;
    Hashtable<String, Integer> BytesIndexadoY;
    
    //se crea objeto para conectar con la lista de Mnemonicos encontrada en Mnemonico.java
    Mnemonicos mnemo=new Mnemonicos();
    String respuesta;
    
    
    public String revisarLineaX(String line, Mnemonicos m){
        
    //Se recuperan las listas de instrucciones del modo indexado 
        metodosDeLectura lectura = new metodosDeLectura();
        IndexadoX=mnemo.LeerOpcode("ListaIndexadoX.txt");
        BytesIndexadoX=mnemo.LeerBytes("ListaIndexadoX.txt");
        
    //Palabra nos sirve para separar la linea en palabras y contabilizarlas
        String palabra;
        int numPalabra=0;
    //Esta cadena será la que se devolverá si la sintaxis es correcta
        String newLine="", instruccion="";
        
        //Se leen las palabras de la línea
        StringTokenizer st = new StringTokenizer (line);
        while (st.hasMoreTokens())
        {
            palabra = st.nextToken();
            numPalabra++;
          
            String aux="";
            
            /*Se verifica que la primera palabra sea una instrucción del modo de direccionamiento inmediato, y de 
            ser así, se concatena al inicio de la cadena que se desea devolver*/
            if(numPalabra==1){
                palabra=palabra.toUpperCase();
                
                if (IndexadoX.containsKey(palabra)){
                    instruccion=instruccion.concat(palabra);
                    newLine=newLine.concat(IndexadoX.get(palabra));
                    System.out.println(instruccion +" Es instruccion de inmediato");
                }else if (lectura.EsInstruccion(palabra, m)){
                    //Instrucción de otro modo de direccionamiento con #
                    System.out.println("Error 000: ERROR DE SINTAXIS 1");
                    return "Error 000: ERROR DE SINTAXIS";
                }else{
                    System.out.println("Error 004: MNEMÓNICO INEXISTENTE");
                    return "Error 004: MNEMÓNICO INEXISTENTE";
                }
            }
            
            //Se verifica el operando, el cual corresponde a la segunda palabra de la línea y debe comenzar con #
            if((numPalabra==2)){
                
            }
            if((numPalabra==3)&&(palabra.startsWith("*"))){
                //Es un comentario, no es necesario realizar nada más
            }else if((numPalabra==3)&&(!palabra.startsWith("*"))){
                System.out.println("Error 000: ERROR DE SINTAXIS 2");
                return "Error 000: ERROR DE SINTAXIS";
            }
        }
        if (numPalabra<2){
            System.out.println("Error 005: INSTRUCCIÓN CARECE DE  OPERANDO(S)");
            return "Error 005: INSTRUCCIÓN CARECE DE  OPERANDO(S)";
        }
        //System.out.println("El número total de palabras leídas es "+numPalabra);
        System.out.println("La cadena generada es: "+newLine);
        return newLine;
    }


    
    
    public String revisarLineaY(String line, Mnemonicos m){
        
        IndexadoY=mnemo.LeerOpcode("ListaIndexadoY.txt");
        BytesIndexadoY=mnemo.LeerBytes("ListaIndexadoY.txt");

        
        
        return respuesta;
      
        
    }
    
    
    
}
