/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * Clase que analiza una instrucción del modo de direccionamiento inherente.
 * @author Fer
 */
public class Inherente {
    Hashtable<String, String> Inherente;
    Hashtable<String, Integer> BytesInherente;
    
    
    public Inherente(){
        this.Inherente = new Hashtable();
    }
    /**
     * En este método se analiza la línea que supuestamente contiene una instrucción del modo de 
     * direccionamiento inherente.
     * @param line linea que supuestamente contiene una instrucción del modo de direccionamiento INH.
     * @return OPCODE de la instrucción procesada o mensaje de error.
     */
    public String AnalizarLinea(String line, Mnemonicos m){
        
        //Se llama a mnemónicos para recuperar la lista de instrucciones del modo Inherente
        metodosDeLectura lectura = new metodosDeLectura();
        Inherente=m.LeerOpcode("ListaInherente.txt");
        BytesInherente=m.LeerBytes("ListaInherente.txt");
        
        /*Palabra nos sirve para separar la linea en palabras y contabilizarlas, al ser inherente y NO tener operando, 
        solo debera tener una palabra que es el mnemonico. */
        String palabra;
        int numPalabra=0;
        
        //Esta cadena será la que se devolverá si la sintaxis es correcta.
        String newLine="";
        
        //Se leen las palabras de la línea.
        StringTokenizer st = new StringTokenizer (line);
        while (st.hasMoreTokens())
        {
            palabra = st.nextToken();
            numPalabra++;
            String instruccion="";
            /*Se verifica que la primera palabra sea una instrucción del modo de direccionamiento inherente, y de 
            ser así, se concatena al inicio de la cadena que se desea devolver*/
            if((numPalabra==1)&&(palabra.startsWith("*"))){
                //Es un comentario, no es necesario realizar nada más
                
            }else if((numPalabra==1)&&(!palabra.startsWith("*"))){
                palabra=palabra.toUpperCase();
                
                if (Inherente.containsKey(palabra)){
                    instruccion=instruccion.concat(palabra);
                    newLine=newLine.concat(Inherente.get(palabra));
                    System.out.println(instruccion +" Es instruccion de inherente");
                }else{
                    System.out.println("Error 004: MNEMÓNICO INEXISTENTE");
                    return "Error 004: MNEMÓNICO INEXISTENTE";
                }
            /*Se verifica que solo haya una palabra (el mnemonico), ya que si tiene mas palabras, corresponden al operando
            el cual NO admite este modo.*/
            }else if(numPalabra>=2){
                return "Error 006: INSTRUCCIÓN NO LLEVA OPERANDO(S)";
            }
        }
        return newLine;
    }
}