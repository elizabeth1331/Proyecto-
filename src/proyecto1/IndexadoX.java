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
public class IndexadoX {
    
    Hashtable<String, String> IndexadoX;
    Hashtable<String, Integer> BytesIndexadoX;
    
    
    //se crea objeto para conectar con la lista de Mnemonicos encontrada en Mnemonico.java
    Mnemonicos m=new Mnemonicos();
    //Se recuperan las listas de instrucciones del modo indexado 
    metodosDeLectura lectura = new metodosDeLectura();
    
    public String revisarLineaX(String line, Mnemonicos m){
        
    
        IndexadoX=m.LeerOpcode("ListaIndexadoX.txt");
        BytesIndexadoX=m.LeerBytes("ListaIndexadoX.txt");
         
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
                    System.out.println(instruccion +" Es instruccion de Indexado en X");
                }/*else if (lectura.EsInstruccion(palabra, m)){
                    System.out.println("Error 000: ERROR DE SINTAXIS 1");
                    //Instrucción de otro modo de direccionamiento con #
                    System.out.println("Error 000: ERROR DE SINTAXIS 1");
                    return "Error 000: ERROR DE SINTAXIS";
                }*/else{
                    System.out.println("Error 004: MNEMÓNICO INEXISTENTE");
                    return "Error 004: MNEMÓNICO INEXISTENTE";
                }
            }
            
            //Se verifica el operando, el cual corresponde a la segunda palabra de la línea y debe comenzar con $
            if((numPalabra==2)){
                int n=palabra.length();
                
                // Se transforma la palabra a cadena
                char[] p = palabra.toCharArray();
                char[] auxPalabra=new  char[n-2];
                char[] auxP=new  char[n-3];
                for (int i = 0; i < n-2; i++){
                    auxPalabra[i]=p[i]; 
                }
                for (int j = 0; j < n-3; j++){   
                    auxP[j]=p[j+1];
                }
                
                 aux = String.valueOf(auxP);
                 palabra = String.valueOf(auxPalabra);
                 
                 //En caso de que sea un operando en número hexadecimal
                if(palabra.startsWith("$")){
                    System.out.println(palabra+" Es operando hexadecimal");
                    
                    //Se comprueba que la longitud del operando coincida con el necesario por la instrucción
                    System.out.println("La instruccion es "+instruccion +" y su numero de bytes debe ser: "+BytesIndexadoX.get(instruccion));
                    
                    newLine=newLine.concat(aux);

                   if( (BytesIndexadoX.get(instruccion))==(Integer.parseInt(String.valueOf(newLine.length()/2)))){
                        //Si coinciden, se agrega a la cadena que será regresada
                        System.out.println("El tamaño de bytes coincide con el Mnemonico");
                        
                    }else{
                       newLine=palabra;
                        System.out.println("Error 007: MAGNITUD DE  OPERANDO ERRONEA");
                        return "Error 007: MAGNITUD DE  OPERANDO ERRONEA";
                    }
                    
                //En caso de que sea un caracter
                }else if(palabra.startsWith("#'")){
                    //System.out.println(palabra+" Es operando ASCII");
                    //Se utiliza la cadena aux para separar #' del operando
                    aux=aux.concat(palabra.substring(2));
                    //System.out.println(aux+" Es el caracter");
                    //Se comprueba si el operando es un solo caracter
                    if(aux.length()==1){
                        char character = aux.charAt(0); 
                        //System.out.println("El caracter es: "+character);
                        //Se convierte el caracter a decimal
                        int ascii = (int) character;
                        //System.out.println("El valor en ASCII es: "+ ascii);
                        //Se convierte el decimal a hexadecimal y se guarda como cadena de mayúsculas
                        String ASCII= Integer.toHexString(ascii).toUpperCase();
                        //System.out.println("El valor en hexadecimal es: "+ASCII);
                        //Se anexa a la cadena que regresará el método
                        newLine=newLine.concat(ASCII);
                    }else{
                        System.out.println("Error 007: MAGNITUD DE  OPERANDO ERRONEA");
                        return "Error 007: MAGNITUD DE  OPERANDO ERRONEA";
                    }
                //En caso de que sea un operando en número decimal
                }/*else{
                    //Se utiliza la cadena aux para separar # del operando
                    aux=aux.concat(palabra.substring(1));
                    //Se convierte a número decimal
                    int op=Integer.parseInt(aux);
                    //System.out.println(op+" Es el operando decimal sin #");
                    //Se convierte el número decimal a hexadecimal y como cadena
                    String operando= Integer.toHexString(op).toUpperCase();
                    //System.out.println(operando+" Es el operando en hexadecimal");
                    //Se comprueba que la longitud del operando coincida con el necesario por la instrucción
                    if(BytesIndexadoX.get(instruccion)==(operando.length()/2)&&(operando.length()%2==0)){
                        //Si coinciden, se agrega a la cadena que será regresada
                        newLine=newLine.concat(operando);
                    }else{
                        System.out.println("Error 007: MAGNITUD DE  OPERANDO ERRONEA");
                        return "Error 007: MAGNITUD DE  OPERANDO ERRONEA";
                    }
                }*/
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

    }
    
    
    

