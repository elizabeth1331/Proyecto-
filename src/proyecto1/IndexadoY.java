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
public class IndexadoY {
    
        Hashtable<String, String> IndexadoY;
        Hashtable<String, Integer> BytesIndexadoY;
    
    
    //se crea objeto para conectar con la lista de Mnemonicos encontrada en Mnemonico.java
        Mnemonicos m=new Mnemonicos();
    //Se recuperan las listas de instrucciones del modo indexado 
        metodosDeLectura lectura = new metodosDeLectura();
        
    
    public String revisarLineaY(String line, Mnemonicos m, Hashtable<String,Integer> variables){
        
    
        IndexadoY=m.LeerOpcode("ListaIndexadoX.txt");
        BytesIndexadoY=m.LeerBytes("ListaIndexadoX.txt");
         
        //Palabra nos sirve para separar la linea en palabras y contabilizarlas
            String palabra, cop, dop;
            int numPalabra=0;
        // 
            boolean ec=true;
    
        //Esta cadena será la que se devolverá si la sintaxis es correcta
            String newLine="", instruccion="", lc="", coment="", lineF="", salida="\n";
        
        //Se leen las palabras de la línea
        StringTokenizer st = new StringTokenizer (line);
            while (st.hasMoreTokens())
            {
            
              palabra = st.nextToken();
              numPalabra++;
          
              String aux="", auxD="";
            
            /*Se verifica que la primera palabra sea una instrucción del modo de direccionamiento indexado, y de 
            ser así, se concatena al inicio de la cadena que se desea devolver*/
                if(numPalabra==1){
                    
                    palabra=palabra.toUpperCase();

                    if (IndexadoY.containsKey(palabra)){
                        
                        instruccion=instruccion.concat(palabra);
                        newLine=newLine.concat(IndexadoY.get(palabra));
                        System.out.println(instruccion +" Es instruccion de Indexado en Y " + instruccion);
                    }/*else if (lectura.EsInstruccion(palabra, m)){
                        System.out.println("Error 000: ERROR DE SINTAXIS 1");
                        //Instrucción de otro modo de direccionamiento con #
                        System.out.println("Error 000: ERROR DE SINTAXIS 1");
                        return "Error 000: ERROR DE SINTAXIS";
                    }*/else{
                        System.out.println("\n                         Error 004: MNEMÓNICO INEXISTENTE\n");
                        return "\n                         Error 004: MNEMÓNICO INEXISTENTE\n";
                    }
                }
            
            //Se verifica el operando, el cual corresponde a la segunda palabra de la línea y debe comenzar con $ o #
                if((numPalabra==2)){
                    lc=lc.concat(palabra);
                    
                //Caso donde no se trata de una constante o variable
                    if(recortarCS(palabra, 2).startsWith("$")== true) {
                        
                            int n=recortarSS(palabra, 2).length();
                        // Se transforma la palabra a cadena
                            char[] pl = palabra.toCharArray();
                            char[] auxPl=new  char[n-1];
                        
                        //Nos quita $ 
                            for (int j = 0; j < n-1; j++){   
                              auxPl[j]=pl[j+1];
                              }    
                        //Transforma a dato correcto 
                            aux = String.valueOf(auxPl);
                        
                        //Concatena el valor con su opcode correspondiente
                                newLine=newLine.concat(aux);
                        //Compara para ver si el numero de bytes coincide 
                                if((newLine.length()%2 ==0 )&& (BytesIndexadoY.get(instruccion))==(Integer.parseInt(String.valueOf(newLine.length()/2)))){
                                //Si coinciden, avisa que la longitud es correcta 
                                   // System.out.println("El tamaño de bytes coincide con el Mnemonico");
                                    if(!(esD(aux))){
                                     System.out.println("Es hexagecimal ++++"+ esD(aux) +aux);    
                                    }else{
                                      System.out.println("Es octal    ++++" +esD(aux) +aux);   
                                    }
                                }else{
                                //avisa que el tamaño es incorrecto y muestra el valor incorrecto 
                                   newLine=palabra;
                                   //Se comprueba que la longitud del operando coincida con el necesario por la instrucción
                                    //System.out.println("-----La instruccion es "+instruccion +" y su numero de bytes a ocupar debe ser: "+BytesIndexadoX.get(instruccion));
                                    System.out.println("\n                         Error 007: MAGNITUD DE  OPERANDO ERRONEA\n");
                                    return "\n                         Error 007: MAGNITUD DE  OPERANDO ERRONEA\n";
                                }  
                             
                        }
                //Define si es constante
                   else if(false==(esD(recortarSS(palabra, 3))) && true==(esCoV(recortarCS(palabra, 2))) && true==(variables.containsKey(recortarSS(palabra, 3)))){
                        //Busca a palabra en la HashTable de constantes y variables
                            if(variables.containsKey(recortarSS(palabra, 3))){
                                    //Si existe guarda el valor de la variable en cop 
                                    cop=variables.get(recortarSS(palabra, 3)).toString();

                                    //Se comprueba que la longitud del operando coincida con el necesario por la instrucción
                                      System.out.println("La instruccion es "+instruccion +" y su numero de bytes debe ser: "+BytesIndexadoY.get(instruccion));

                                       newLine=newLine.concat(cop);

                                      if((newLine.length()%2 ==0 )&& (BytesIndexadoY.get(instruccion))==(Integer.parseInt(String.valueOf(newLine.length()/2)))){
                                    //Si coinciden, se agrega a la cadena que será regresada
                                           System.out.println("El tamaño de bytes coincide con el Mnemonico");

                                      }else{
                                          newLine=palabra;
                                           System.out.println("\n                         Error 007: MAGNITUD DE  OPERANDO ERRONEA\n");
                                           return "\n                         Error 007: MAGNITUD DE  OPERANDO ERRONEA\n";
                                       }
                            }
                    }else if(false==(esD(recortarCS(palabra, 2))) && true==(esCoV(recortarCS(palabra, 2)))&& false==(variables.containsKey(recortarCS(palabra, 2)))){
                                //System.out.print(esCoV(recortarSS(palabra, 2))+ " ------ "+ palabra +" ------ "+recortarSS(palabra, 2)+" ------ "+lc);
                                System.out.println("\n                         Error 001: CONSTANTE INEXISTENTE\n");
                                return "\n                         Error 001: CONSTANTE INEXISTENTE\n";
                    }
                //Define si es variable
                    else if(false==(esD(recortarCS(palabra, 2))) && false==(esCoV(recortarSS(palabra, 2))) && true==(variables.containsKey(recortarCS(palabra, 2))) && recortarCS(palabra, 2).startsWith("'")== false){ 
                                //System.out.print((esD(palabra))+"   "+(esCoV(recortarCS(palabra, 2)))+"   "+recortarSS(palabra, 2));
                                //lc=lc.concat(palabra);

                            System.out.println("--------Es variable " + palabra);
                                //Si existe guarda el valor de la variable en cop 
                            
                                cop=variables.get(recortarCS(palabra, 2)).toString();
                               
                                   //Se comprueba que la longitud del operando coincida con el necesario por la instrucción
                                   System.out.println("La instruccion es "+instruccion +" y su numero de bytes debe ser: "+BytesIndexadoY.get(instruccion));

                                   newLine=newLine.concat(cop);

                                  if((newLine.length()%2 ==0 )&& (BytesIndexadoY.get(instruccion))==(Integer.parseInt(String.valueOf(newLine.length()/2)))){
                                       //Si coinciden, se agrega a la cadena que será regresada
                                       System.out.println("El tamaño de bytes coincide con el Mnemonico");

                                  }else{
                                      newLine=palabra;
                                       System.out.println("\n                         Error 007: MAGNITUD DE  OPERANDO ERRONEA\n");
                                       return "\n                         Error 007: MAGNITUD DE  OPERANDO ERRONEA\n";
                                   }
                        }else if(false==(esD(recortarCS(palabra, 2))) && false==(esCoV(recortarSS(palabra, 2)))&& false==(variables.containsKey(recortarSS(palabra, 2))) && recortarCS(palabra, 2).startsWith("'")== false){
                                    System.out.println("\n                         Error 002: VARIABLE INEXISTENTE\n "+recortarSS(palabra, 2));
                                    return "\n                         Error 002: VARIABLE INEXISTENTE\n";
                        }
                //Tratando a un caracter como operando 
                    if(recortarCS(palabra, 2).startsWith("'")== true ){
                        aux =recortarSS(palabra, 3);
                        System.out.print(aux + "operando tipo caracter ");
                        
                        //confirmar tamaño del operando
                            if(aux.length()==1){
                                char character = palabra.charAt(0);
                                int va = (int)character;
                                cop=Integer.toHexString(va).toUpperCase();
                                System.out.print(cop + "------------ ");
                                newLine=newLine.concat(cop);
                            }else{  
                            newLine=palabra;
                            System.out.println("\n                         Error 007: MAGNITUD DE  OPERANDO ERRONEA\n");
                            return "\n                         Error 007: MAGNITUD DE  OPERANDO ERRONEA\n";
                                       }
                    }

                //Tratando a un operando decimal
                    if((esD(recortarCS(palabra, 2)))&&!(palabra.startsWith("$"))){
                                
                                //System.out.println(palabra+" Es un operando decimal  0" +recortarCS(palabra, 2) );
                            //Se convierte a número decimal
                                int opN=Integer.parseInt(recortarCS(palabra, 2));
                            //Se convierte el número decimal a hexadecimal y como cadena
                                aux=Integer.toHexString(opN).toUpperCase();
                                //System.out.println(aux+ " ---"+ newLine+" operanco en Hexadecimal");
                            //Concatena el valor con su opcode correspondiente
                                newLine=newLine.concat(aux);
                            //Compara para ver si el numero de bytes coincide 
                                if((newLine.length()%2 ==0 )&& (BytesIndexadoY.get(instruccion))==(Integer.parseInt(String.valueOf(newLine.length()/2)))){
                            //Si coinciden, avisa que la longitud es correcta 
                            // System.out.println("El tamaño de bytes coincide con el Mnemonico");
                                }else{
                                //avisa que el tamaño es incorrecto y muestra el valor incorrecto 
                                   newLine=palabra;
                                //Se comprueba que la longitud del operando coincida con el necesario por la instrucción
                                //System.out.println("-----La instruccion es "+instruccion +" y su numero de bytes a ocupar debe ser: "+BytesIndexadoX.get(instruccion));
                                    System.out.println("\n                         Error 007: MAGNITUD DE  OPERANDO ERRONEA\n");
                                    return "\n                         Error 007: MAGNITUD DE  OPERANDO ERRONEA\n";
                                }
                        }
            }
                            
            if((numPalabra==3)&&(palabra.startsWith("*"))){
                ec=false;
                //Es un comentario, no es necesario realizar nada más
                coment=coment.concat(palabra + " ");
            }else if(numPalabra>3 && ec==false){
                //Es un comentario, no es necesario realizar nada más
                coment=coment.concat(palabra + " ");
            }else if((numPalabra==3)&&(!palabra.startsWith("*"))){
                System.out.println("\n                         Error 000: ERROR DE SINTAXIS 2\n");
                return "\n                         Error 000: ERROR DE SINTAXIS\n";
            }

            }
            
        
        if (numPalabra<2){
            System.out.println("\n                         Error 005: INSTRUCCIÓN CARECE DE  OPERANDO(S)\n");
            return "\n                         Error 005: INSTRUCCIÓN CARECE DE  OPERANDO(S)\n";
        }
        lineF=salida.concat(newLine+"			    " + instruccion + " " + lc + " " + coment+ "\n");
        System.out.println(lineF);
        return newLine;
    }

    public boolean esD(String operando){
        try{
            Integer.parseInt(operando);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
    
    
    public boolean esCoV(String palabra){
        if (palabra.startsWith("#")){ 
            return true;
        }else {
            return false;
        }
    }
    
    
    //Funcion que quita ',Y' / ',y', sin #
    public String recortarCS(String palabra, int no){
        
                            int n=palabra.length();
                        // Se transforma la palabra a cadena
                            char[] p = palabra.toCharArray();
                        //se crea un arreglo para guardar la constante a buscar
                            int v=n-no;
                            char[] auxP=new  char[v];
                        
                            for (int j = 0; j < v; j++){   
                              auxP[j]=p[j];
                              }
                        //Transformar a un dato String
                             String nPalabra = String.valueOf(auxP);
        return nPalabra;
        
    }
    //Funcion que  quita # y ',Y' / ',y'
    public String recortarSS(String palabra, int no){
        
                            int n=palabra.length();
                        // Se transforma la palabra a cadena
                            char[] p = palabra.toCharArray();
                        //se crea un arreglo para guardar la constante a buscar
                            int v=n-no;
                            char[] auxP=new  char[v];
                        
                            for (int j = 0; j < v; j++){   
                              auxP[j]=p[j+1];
                              }
                        //Transformar a un dato String
                             String nPalabra = String.valueOf(auxP);
        return nPalabra;
        
    }
}