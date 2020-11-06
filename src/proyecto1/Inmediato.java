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
    
    public Inmediato(){
        this.Inmediato = new Hashtable();
    }
    /**
     * En este método se analiza la línea que supuestamente contiene una instrucción del modo de 
     * direccionamiento inmediato.
     * @param line linea que supuestamente contiene una instrucción del modo de direccionamiento INM.
     * @return OPCODE de la instrucción procesada o mensaje de error.
     */
    public String AnalizarLinea(String line, Mnemonicos m){
        //Se llama a mnemónicos para recuperar la lista de instrucciones del modo Inmediato
        metodosDeLectura lectura = new metodosDeLectura();
        Inmediato=m.LeerOpcode("ListaInmediato.txt");
        BytesInmediato=m.LeerBytes("ListaInmediato.txt");
        
        System.out.println(Inmediato);
        
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
            
            System.out.println(numPalabra);
            System.out.println("Palabra: "+palabra);
            
            String instruccion="";
            
            /*Se verifica que la primera palabra sea una instrucción del modo de direccionamiento inmediato, y de 
            ser así, se concatena al inicio de la cadena que se desea devolver*/
            if(numPalabra==1){
                palabra=palabra.toUpperCase();
                
                if (Inmediato.containsKey(palabra)){
                    instruccion=palabra;
                    newLine=newLine.concat(Inmediato.get(palabra));
                    System.out.println("Es instruccion de inmediato");
                }else if (lectura.EsInstruccion(palabra, m)){
                    System.out.println("Error 000: ERROR DE SINTAXIS 1");
                    return "Error 000: ERROR DE SINTAXIS";
                    //Instrucción de otro modo de direccionamiento con #
                }else{
                    System.out.println("Error 004: MNEMÓNICO INEXISTENTE");
                    return "Error 004: MNEMÓNICO INEXISTENTE";
                }
            }
            
            //Se verifica el operando, el cual corresponde a la segunda palabra de la línea y debe comenzar con #
            if((numPalabra==2)&&(palabra.startsWith("#"))){
                //En caso de que sea un operando en número hexadecimal
                if(palabra.startsWith("#$")){
                    System.out.println("Es operando hexadeimal");
                    //Se utiliza la cadena aux para separar #$ del operando
                    String aux=palabra.concat(palabra.substring(2));
                    //Se comprueba que la longitud del operando coincida con el necesario por la instrucción
                    if(BytesInmediato.get(instruccion)==(aux.length()/2)){
                        //Si coinciden, se agrega a la cadena que será regresada
                        newLine=newLine.concat(aux);
                    }else{
                        System.out.println("Error 007: MAGNITUD DE  OPERANDO ERRONEA");
                        return "Error 007: MAGNITUD DE  OPERANDO ERRONEA";
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
                        System.out.println("Error 007: MAGNITUD DE  OPERANDO ERRONEA");
                        return "Error 007: MAGNITUD DE  OPERANDO ERRONEA";
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
                        System.out.println("Error 007: MAGNITUD DE  OPERANDO ERRONEA");
                        return "Error 007: MAGNITUD DE  OPERANDO ERRONEA";
                    }
                }
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
        System.out.println(numPalabra);
        System.out.println(newLine);
        return newLine;
    }
}
