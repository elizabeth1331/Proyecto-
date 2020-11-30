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
    int numTotalPalabra = 0;
    String Palabra2 = " ";
    
    public Inherente(){
        this.Inherente = new Hashtable();
    }
    /**
     * En este método se analiza la línea que supuestamente contiene una instrucción del modo de 
     * direccionamiento inherente.
     * @param line linea que supuestamente contiene una instrucción del modo de direccionamiento INH.
     * @return OPCODE de la instrucción procesada o mensaje de error.
     */
    public String AnalizarLinea(String line, Mnemonicos m, int numMemoria){
        //System.out.println("Entro a inherente");
        
        //Se llama a mnemónicos para recuperar la lista de instrucciones del modo Inherente
        metodosDeLectura lectura = new metodosDeLectura();
        Inherente=m.LeerOpcode("ListaInherente.txt");
        BytesInherente=m.LeerBytes("ListaInherente.txt");
        
        //Se cuenta el numero total de palabras para validar que la instruccion no tenga operando
        StringTokenizer stt = new StringTokenizer (line);
        numTotalPalabra = stt.countTokens();
        //System.out.println("Número total de palabras en la linea es: " + numTotalPalabra);
        
        String palabra ;
        int numPalabra=0;
        
        //Esta cadena será la que se devolverá si la sintaxis es correcta.
        String newLine="";
        
        //Se leen las palabras de la línea.
        StringTokenizer st = new StringTokenizer (line);
        while (st.hasMoreTokens()){
            palabra = st.nextToken();
            numPalabra++;
            String instruccion="";
            /*Se verifica que la primera palabra sea una instrucción del modo de direccionamiento inherente, y de 
            ser así, se concatena al inicio de la cadena que se desea devolver*/
            if((numPalabra==1)&& numTotalPalabra<2){
                if(palabra.startsWith("*")){
                    //Es un comentario, no es necesario realizar nada más
                }else{
                    palabra=palabra.toUpperCase();
                    if (Inherente.containsKey(palabra)){
                        instruccion=instruccion.concat(palabra+" ");
                                                
                        newLine=newLine.concat(Inherente.get(palabra));
                        
                        //System.out.println("La linea resultante es: " +newLine);
                        
                        //Cálculo del número de espacio en memoria utilizado hasta el momento
                        metodosDeLectura.numMemoria = metodosDeLectura.numMemoria + BytesInherente.get(palabra);
                    }else{
                        String mensaje = "\u001B[31m Error 004: MNEMÓNICO INEXISTENTE \u001B[0m\n";
                        //Guardamos la salida de la primer pasada
                        Output outPut = new Output();
                        outPut.mensaje = mensaje;
                        metodosDeLectura.salidas.add(outPut);
                        return line + "\n\t\t\t^Error 004: MNEMÓNICO INEXISTENTE";
                    }
                }
            }else if((numPalabra==1)&& numTotalPalabra>=2){
                Palabra2 = palabra;
                Palabra2 = st.nextToken();
                if((Palabra2.startsWith("*"))){
                    palabra=palabra.toUpperCase();
                    if (Inherente.containsKey(palabra)){
                        instruccion=instruccion.concat(palabra+" ");
                        
                        newLine=newLine.concat(Inherente.get(palabra));
                        
                        //System.out.println("La linea resultante es: " +newLine);
                        
                        //Cálculo del número de espacio en memoria utilizado hasta el momento
                        metodosDeLectura.numMemoria = metodosDeLectura.numMemoria + BytesInherente.get(palabra);
                    }
                }else{
                    String mensaje = "\u001B[31m Error 006: INSTRUCCIÓN NO LLEVA OPERANDO(S) \u001B[0m\n";
                    //Guardamos la salida de la primer pasada
                    Output outPut = new Output();
                    outPut.mensaje = mensaje;
                    metodosDeLectura.salidas.add(outPut);
                    return line + "\n\t\t\t^Error 006: INSTRUCCIÓN NO LLEVA OPERANDO(S)";
                }
            }
            
            if(numPalabra==2){
                if(palabra.startsWith("*")){
                    //Es un comentario, no es necesario realizar nada más
                }else if(!palabra.startsWith("*")){
                    String mensaje = "\u001B[31m Error 006: INSTRUCCIÓN NO LLEVA OPERANDO(S) \u001B[0m\n";
                    //Guardamos la salida de la primer pasada
                    Output outPut = new Output();
                    outPut.mensaje = mensaje;
                    metodosDeLectura.salidas.add(outPut);
                    return line + "\n\t\t\t^Error 006: INSTRUCCIÓN NO LLEVA OPERANDO(S)";
                }
            }
        }
        //System.out.println("******La resiltante es:" + newLine);
        String mensaje ="\u001B[42;30m"+ newLine+ "\u001B[0m";
        mensaje = mensaje + "\t\t\t"+line+"\n";
        //Guardamos la salida de la primer pasada
        Output outPut = new Output();
        outPut.mensaje = mensaje;
        metodosDeLectura.salidas.add(outPut);
        
        newLine = newLine.concat("          "+ line); 
        return newLine;
    }
}