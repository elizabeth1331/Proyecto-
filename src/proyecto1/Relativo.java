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
    static public Boolean error;
    
    /**
     * Constructor
     */
    public Relativo(){
        this.Relativo = new Hashtable();
        error = false;
    }
    
    /**
     * Comprueba si se trara de una instrucción del modo de direccionamiento relativo, de ser vaida la existencia de su operando (etiqueta)
     * @param linea Es la línea que se ha leído del archivo
     * @param m Objeto que nos ayuda a recuperar el archivo que contiene las instrucciones del modo de direccionamiento relativo y su OPCODE
     * @return Regresa la línea de error, si es que lo hay
     */
    public String RevisarLinea(String linea, Mnemonicos m, Var_Cons_Etiq VCE, int numMemoria, int pasada, int numLinea, int ini){
        
        //Recupera la tabla Hash con el OPCODE y las intrucciones del modo de direccionamiento REL
        Relativo = m.LeerOpcode("ListaRelativo.txt");
       
        //Palabra nos sirve para separar la linea en palabras y contabilizarlas
        String palabra = "";
        int numPalabra=0;
        int posMem = 0;
        
        //Se leen las palabras de la línea
        StringTokenizer st = new StringTokenizer (linea);
        
        //Esta cadena será la que se devolverá si no hay errores
        String newLine="";
        
        //En la primer pasada revisa toda la línea
        if(pasada ==1){
            
            palabra = st.nextToken();
            String instruccion="";
            
            palabra=palabra.toUpperCase();
            //System.out.println("La palabra es: "+palabra);
            
            //System.out.println("Instrucción del modo REL");
            
            //Cálculo del numero de memoria utilizado hasta el momento
            metodosDeLectura.numMemoria = metodosDeLectura.numMemoria + 2;
            
            newLine=linea;
            
            //Guardamos la línea leída en la primer pasada e indicamos que hay un salto que no se ha calculado
            Output outPut = new Output(newLine, true, numLinea, false);
            metodosDeLectura.salidas.add(outPut);
                
        //En la segunda pasada sólo se fija en el operando (la etiqueta)
        }else if (pasada==2){
            String instruccion = "";
            String inicio = ""; //Guardamos las pirmeras 3 palabras de la línea
            
            /*En el ciclo se obtiene la parte de la línea que corresponde al número de línea y el lugar de memoria*/
            for(int i=0; i<3;i++){
                palabra = st.nextToken();
                if (i<2){
                    inicio = inicio + palabra + " "; 
                }else if (i==2){
                    inicio = inicio + palabra + "\t";
                    posMem = Integer.parseInt(palabra,16);
                    //System.out.println("PosMem= "+posMem);
                    //System.out.println("Inicio : " + ini);
                }
            }
            //Guardamos el resto de la línea que corresponde a la línea original
            
            linea = "";
            while(st.hasMoreTokens()){
                linea = linea + st.nextToken() + " ";
            }
            st = new StringTokenizer (linea);
            while (st.hasMoreTokens()){
                numPalabra++;
                palabra = st.nextToken();
                if(numPalabra == 1){
                    //Guardamos la instrucción
                    instruccion = palabra;
                }else if(numPalabra==2){
                    //System.out.println(palabra);
                    if(palabra.startsWith("*")){
                        //Lo demás es un comentario así que no tiene operandos
                        System.out.println("\u001B[31m Error 005: INSTRUCCIÓN CARECE DE  OPERANDO(S) \u001B[0m");
                        return newLine + "\n\t\t\t^Error 005: INSTRUCCIÓN CARECE DE  OPERANDO(S)";
                    }
                    //Nos ayuda a identificar si hay un error en la etiqueta
                    
                    newLine = verificarEtiqueta(palabra, VCE, posMem);
                    if(error){
                        newLine = inicio + linea + newLine;
                        metodosDeLectura.salidas.get(numLinea-1).salto = false;
                       
                    }else{
                        System.out.print("\n\033[46;34m"+Relativo.get(instruccion)+"\u001B[44;36m"+newLine+"\u001B[0m"+"\t\t\t"+linea+"\n");
                        newLine = inicio+Relativo.get(instruccion)+newLine+"\t\t\t"+linea;
                    }
                }else if((numPalabra==3)&&(palabra.startsWith("*"))){
                    //Es un comentario, no es necesario realizar nada más
                    return newLine;
                    
                }else{
                    System.out.println("\u001B[31m Error: Sintaxis incorrecta \u001B[0m");
                    return linea + "\n\t\t\t^Error: Sintaxis incorrecta";
                }                        
            }
            if (numPalabra<2){
                System.out.println("\u001B[31m Error 005: INSTRUCCIÓN CARECE DE  OPERANDO(S) \u001B[0m");
                return newLine + "\n\t\t\t^Error 005: INSTRUCCIÓN CARECE DE  OPERANDO(S)";
            }
        }
        return newLine;
    }
    
    public String verificarEtiqueta(String palabra, Var_Cons_Etiq VCE, int numMem){
        
        //Pos no ayuda a guardar la posición de la etiqueta
        int pos = 0;
        int salto = 0;
        
        
        //Buscar si existe esa etiqueta                    
        pos = VCE.buscarEtiqueta(palabra);
        //System.out.println("pos: " + pos);
        if (pos == 0){
            error = true;
            System.out.println("\u001B[31m Error 003: ETIQUETA INEXISTENTE \u001B[0m");
            return "\n\t\t\t^Error 003: ETIQUETA INEXISTENTE";
        }else{
            
            //Arreflo de caracteres que nos ayudará a calcular el operando
            char[] aBinario = new char[8];
            
            if (pos<numMem){
                //Caso de salto negativo
                //System.out.println("El salto es negativo");
                salto = (numMem+2)-pos;
                //System.out.println("El salto es: "+ salto);
                if (salto <= 127){
                    error = false;
                    String binario = Integer.toBinaryString(salto);
                    //System.out.println("Binario antes: "+binario);
                   
                //Pasamos el String a un arreglo de caracteres, llenando los espacios vacíos con ceros
                    int tamaño = binario.length();
                    //Llenamos los espacios vacios con 0
                    for(int j = 0; j < (8 - tamaño); j++){
                        aBinario[j]=0;
                    }
                    //Guardamos el resto del número binario
                    int j=0;
                    for (int k = (8-tamaño); k<8; k++){
                        aBinario[k]=binario.charAt(j);
                        j++;
                    }
                    //Se cambian los 1s por 0s
                    for(int i=0; i<aBinario.length; i++){
                        if(aBinario[i]=='1'){
                            aBinario[i]='0';
                        }else{
                            aBinario[i]='1';
                        }
                    }
                    
                    //Convertimos el arreglo nuevamente a cadena
                    StringBuffer bin = new StringBuffer();
                    for (int i=0;i<aBinario.length;i++){
                        bin =bin.append(aBinario[i]);
                        //System.out.println(aBinario[i]);
                    }
                    binario = bin.toString();
                    //System.out.println(bin);
                    //System.out.println("Num bin: "+ binario);
                    //Convertimos la cadena binaria a decimal
                    int decimal=Integer.parseInt(binario,2);
                    //Le sumamos 1
                    decimal = decimal +1;
                    //Convertimos el decimal a una cadena haxadecimal
                    String hexadecimal = Integer.toHexString(decimal);
                    hexadecimal = hexadecimal.toUpperCase();
                    if(hexadecimal.length()==1){
                        hexadecimal = "0" + hexadecimal;
                    }
                    return hexadecimal;
                              
                }else{
                    error = true;
                    System.out.println("\u001B[31m Error 008: SALTO RELATIVO MUY LEJANO \u001B[0m");
                    return "\n\t\t\t^Error 008: SALTO RELATIVO MUY LEJANO";
                }
            }else{
                //Caso de salto positivo
                System.out.println("El salto es positivo");
                salto = pos - (numMem+2);
                if (salto <= 128){
                    error = false;
                    //Calcular el valor del operando
                    String hexadecimal = Integer.toHexString(salto);
                    hexadecimal = hexadecimal.toUpperCase();
                    if(hexadecimal.length()==1){
                        hexadecimal = "0" + hexadecimal;
                    }
                    return hexadecimal;
                }else{
                    error = true;
                    System.out.println("\u001B[31m Error 008: SALTO RELATIVO MUY LEJANO \u001B[0m");
                    return "\n\t\t\t^Error 008: SALTO RELATIVO MUY LEJANO";
                }
            }
        }
    } 
}