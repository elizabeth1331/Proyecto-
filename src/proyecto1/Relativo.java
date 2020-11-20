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
    public String RevisarLinea(String linea, Mnemonicos m, Var_Cons_Etiq VCE, int numMemoria, int pasada){
        
        //Recupera la tabla Hash con el OPCODE y las intrucciones del modo de direccionamiento REL
        Relativo = m.LeerOpcode("ListaRelativo.txt");
       
        //Palabra nos sirve para separar la linea en palabras y contabilizarlas
        String palabra = "";
        int numPalabra=0;
        
        //Se leen las palabras de la línea
        StringTokenizer st = new StringTokenizer (linea);
        
        //Esta cadena será la que se devolverá si no hay errores
        String newLine="";
        
        //En la primer pasada revisa toda la línea
        if(pasada ==1){
            
            palabra = st.nextToken();
            String instruccion="";
            
            palabra=palabra.toUpperCase();
            System.out.println("La palabra es: "+palabra);
            
            System.out.println("Instrucción del modo REL");
            
            //Cálculo del numero de memoria utilizado hasta el momento
            numMemoria = numMemoria + 2;
            
            newLine=linea;          
                
        //En la segunda pasada sólo se fija en el operando (la etiqueta)
        }else if (pasada==2){
            String instruccion = "";
            while (st.hasMoreTokens()){
                numPalabra++;
                palabra = st.nextToken();
                if(numPalabra == 1){
                    //Guardamos la instrucción
                    instruccion = palabra;
                }else if(numPalabra==2){
                    Boolean error = false;
                    newLine = verificarEtiqueta(palabra, VCE, numMemoria, error);
                    if(error){
                        return newLine;
                    }else{
                        newLine = Relativo.get(instruccion)+newLine+"   "+linea;
                    }
                }else if((numPalabra==3)&&(palabra.startsWith("*"))){
                    //Es un comentario, no es necesario realizar nada más
                    return newLine;
                }else{
                    return "\u001B[31m Error: Sintaxis incorrecta \u001B[0m";
                }                        
            }
            if (numPalabra<2){
                return "Error 005: INSTRUCCIÓN CARECE DE  OPERANDO(S)";
            }
        }
        return newLine;
    }
    
    public String verificarEtiqueta(String palabra, Var_Cons_Etiq VCE, int numLinea, boolean error){
        
        //Pos no ayuda a guardar la posición de la etiqueta
        int pos = 0;
        int salto = 0;
        
        //Buscar si existe esa etiqueta                    
        pos = VCE.buscarEtiqueta(palabra);
        if (pos == 0){
            error = true;
            System.out.println("\u001B[31m Error 003: ETIQUETA INEXISTENTE \u001B[0m");
            return "Error 003: ETIQUETA INEXISTENTE";
        }else{
            
            //Arreflo de caracteres que nos ayudará a calcular el operando
            char[] aBinario;
            
            if (pos<numLinea){
                //Caso de salto negativo
                System.out.println("El salto es negativo");
                salto = (numLinea+2)-pos;
                if (salto <= 127){
                    error = false;
                    String binario = Integer.toBinaryString(salto);
                    aBinario = binario.toCharArray();
                    
                    //Se cambian los 1s por 0s
                    for(int i=0; i<aBinario.length; i++){
                        if(aBinario[i]==1){
                            aBinario[i]=0;
                        }else{
                            aBinario[i]=1;
                        }
                    }
                    
                    //Convertimos el arreglo nuevamente a cadena
                    binario = String.valueOf(aBinario);
                    //Convertimos la cadena binaria a decimal
                    int decimal=Integer.parseInt(binario,2);
                    //Convertimos el decimal a una cadena haxadecimal
                    String hexadecimal = Integer.toHexString(decimal);
                    return hexadecimal;
                              
                }else{
                    error = true;
                    System.out.println("\u001B[31m Error 008: SALTO RELATIVO MUY LEJANO \u001B[0m");
                    return "Error 008: SALTO RELATIVO MUY LEJANO";
                }
            }else{
                //Caso de salto positivo
                System.out.println("El salto es positivo");
                salto = pos - (numLinea+2);
                if (salto <= 128){
                    error = false;
                    //Calcular el valor del operando
                    String hexadecimal = Integer.toHexString(salto);
                    return hexadecimal;
                }else{
                    error = true;
                    System.out.println("\u001B[31m Error 008: SALTO RELATIVO MUY LEJANO \u001B[0m");
                    return "Error 008: SALTO RELATIVO MUY LEJANO";
                }
            }
        }
    } 
}