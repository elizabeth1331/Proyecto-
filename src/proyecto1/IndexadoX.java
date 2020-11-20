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
        
    
    public String revisarLineaX(String line, Mnemonicos m, Hashtable<String,Integer> variables){
        
    
        IndexadoX=m.LeerOpcode("ListaIndexadoX.txt");
        BytesIndexadoX=m.LeerBytes("ListaIndexadoX.txt");
         
        //Palabra nos sirve para separar la linea en palabras y contabilizarlas
            String palabra, cop, dop;
            int numPalabra=0;
        // 
            boolean ec=true;
    
        //Esta cadena será la que se devolverá si la sintaxis es correcta
            String newLine="", instruccion="", lc="", coment="", lineF="", salida="\n", nl="";
        
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

                    if (IndexadoX.containsKey(palabra)){
                        
                        instruccion=instruccion.concat(palabra);
                        nl=newLine=newLine.concat(IndexadoX.get(palabra));
                        System.out.println(instruccion +" Es instruccion de Indexado en X ");
                    }else{
                        System.out.println("\u001B[31m Error 004: MNEMÓNICO INEXISTENTE\u001B[0m");
                        return "\n\t\t\t^Error 004: MNEMÓNICO INEXISTENTE\n";
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
                                if((newLine.length()%2 ==0 )&& (BytesIndexadoX.get(instruccion))==(Integer.parseInt(String.valueOf(newLine.length()/2)))){
                                //Si coinciden, avisa que la longitud es correcta 
                                   // System.out.println("El tamaño de bytes coincide con el Mnemonico");
                                    System.out.print("\n\u001B[44;37m"+nl+"\u001B[0m");
                                    System.out.print("\u001B[34m"+aux+"\u001B[0m"+"\t\t\t"+instruccion + " " + lc+"\n");
                                
                                }else{
                                //avisa que el tamaño es incorrecto y muestra el valor incorrecto 
                                   newLine=palabra;
                                   //Se comprueba que la longitud del operando coincida con el necesario por la instrucción
                                    //System.out.println("-----La instruccion es "+instruccion +" y su numero de bytes a ocupar debe ser: "+BytesIndexadoX.get(instruccion));
                                    System.out.println("\u001B[31m Error 007: MAGNITUD DE  OPERANDO ERRONEA\u001B[0m");
                                    return "\n\t\t\t^Error 007: MAGNITUD DE  OPERANDO ERRONEA";
                                }  
                             
                        }
                //Define si es constante
                    if(false==(esD(recortarSS(palabra, 3))) && true==(esCoV(recortarCS(palabra, 2))) && true==(variables.containsKey(recortarSS(palabra, 3)))){
                        //Busca a palabra en la HashTable de constantes y variables
                            if(variables.containsKey(recortarSS(palabra, 3))){
                                    //Si existe guarda el valor de la variable en cop 
                                    cop=variables.get(recortarSS(palabra, 3)).toString();

                                    //Se comprueba que la longitud del operando coincida con el necesario por la instrucción
                                      //System.out.println("La instruccion es "+instruccion +" y su numero de bytes debe ser: "+BytesIndexadoY.get(instruccion));

                                       newLine=newLine.concat(cop);

                                      if((newLine.length()%2 ==0 )&& (BytesIndexadoX.get(instruccion))==(Integer.parseInt(String.valueOf(newLine.length()/2)))){
                                            System.out.print("\n\u001B[44;37m"+nl+"\u001B[0m");
                                            System.out.print("\u001B[34m"+cop+"\u001B[0m"+"\t\t\t"+instruccion + " " + lc+"\n");  
                                      }else{
                                          newLine=palabra;
                                           System.out.println("\u001B[31m Error 007: MAGNITUD DE  OPERANDO ERRONEA\u001B[0m");
                                           return "\n\t\t\t^Error 007: MAGNITUD DE  OPERANDO ERRONEA";
                                       }
                            }
                    }else if(false==(esD(recortarCS(palabra, 2))) && true==(esCoV(recortarCS(palabra, 2)))&& false==(variables.containsKey(recortarCS(palabra, 2)))){
                             
                                System.out.println("\u001B[31m Error 001: CONSTANTE INEXISTENTE\u001B[0m");
                                return "\n\t\t\t^Error 001: CONSTANTE INEXISTENTE";
                    }
                //Define si es variable
                     if(false==(esD(recortarCS(palabra, 2))) && false==(esCoV(recortarSS(palabra, 2))) && true==(variables.containsKey(recortarCS(palabra, 2))) && esCoH(recortarCS(palabra, 2))== false && Hexa( palabra)==true && recortarCS(palabra, 2).startsWith("$")== false){ 

                                cop=variables.get(recortarCS(palabra, 2)).toString();
                               
                                //Se comprueba que la longitud del operando coincida con el necesario por la instrucción
                                    //System.out.println("La instruccion es "+instruccion +" y su numero de bytes debe ser: "+BytesIndexadoY.get(instruccion));

                                   newLine=newLine.concat(cop);

                                  if((newLine.length()%2 ==0 )&& (BytesIndexadoX.get(instruccion))==(Integer.parseInt(String.valueOf(newLine.length()/2)))){
                                    System.out.print("\n\u001B[44;37m"+nl+"\u001B[0m");
                                    System.out.print("\u001B[34m"+cop+"\u001B[0m"+"\t\t\t"+instruccion + " " + lc+"\n");  

                                  }else{
                                        newLine=palabra;
                                        System.out.println("\u001B[31m Error 007: MAGNITUD DE  OPERANDO ERRONEA\u001B[0m");
                                        return "\n\t\t\t^Error 007: MAGNITUD DE  OPERANDO ERRONEA";
                                   }
                        }
                     if(false==(esD(recortarCS(palabra, 2))) && false==(esCoV(recortarCS(palabra, 2)))&& false==(variables.containsKey(recortarCS(palabra, 2))) && esCoH(recortarCS(palabra, 2))==false&& Hexa(palabra)==true &&recortarCS(palabra, 2).startsWith("$")== false){
                                System.out.println("\u001B[31m Error 001: VARIABLE INEXISTENTE\u001B[0m");
                                 int num=recortarCS(palabra,3).length();
                                
                                return "\n\t\t\t^Error 001: VARIABLE INEXISTENTE";
                        }
                //Tratando a un caracter como operando 
                    if(esCoH(recortarCS(palabra, 2))== true ){
                        aux =recortarSS(palabra, 3);
          
                        //confirmar tamaño del operando
                            if(aux.length()==1){
                                char character = aux.charAt(0);
                                
                                int va = (int)character;
                                cop=Integer.toHexString(va);
                                System.out.println(aux +palabra +"******************"+ character +"*****************"+va+"*****************"+cop);
                                newLine=newLine.concat(cop);
                                System.out.print("\n\u001B[44;37m"+nl+"\u001B[0m");
                                System.out.print("\u001B[34m"+cop+"\u001B[0m"+"\t\t\t"+instruccion + " " + lc+"\n");  
                            }else{  
                            newLine=palabra;
                            System.out.println("\u001B[31m Error 007: MAGNITUD DE  OPERANDO ERRONEA\u001B[0m");
                            return "\n\t\t\t^Error 007: MAGNITUD DE  OPERANDO ERRONEA";
                                       }
                    }

                //Tratando a un operando decimal
                    if((esD(recortarCS(palabra, 2)))&&!(palabra.startsWith("$"))){
                           
                            //Se convierte a número decimal
                                int opN=Integer.parseInt(recortarCS(palabra, 2));
                            //Se convierte el número decimal a hexadecimal y como cadena
                                aux=Integer.toHexString(opN).toUpperCase();
                                //System.out.println(aux+ " ---"+ newLine+" operanco en Hexadecimal");
                            //Concatena el valor con su opcode correspondiente
                                newLine=newLine.concat(aux);
                            //Compara para ver si el numero de bytes coincide 
                                if((newLine.length()%2 ==0 )&& (BytesIndexadoX.get(instruccion))==(Integer.parseInt(String.valueOf(newLine.length()/2)))){
                                   System.out.print("\n\u001B[44;37m"+nl+"\u001B[0m");
                                   System.out.print("\u001B[34m"+aux+"\u001B[0m"+"\t\t\t"+instruccion + " " + lc+"\n");  
                                }else{
                                //avisa que el tamaño es incorrecto y muestra el valor incorrecto 
                                   newLine=palabra;
                                //Se comprueba que la longitud del operando coincida con el necesario por la instrucción
                                //System.out.println("-----La instruccion es "+instruccion +" y su numero de bytes a ocupar debe ser: "+BytesIndexadoX.get(instruccion));
                                    System.out.println("\u001B[31m Error 007: MAGNITUD DE  OPERANDO ERRONEA\u001B[0m");
                                    return "\n\t\t\t^Error 007: MAGNITUD DE  OPERANDO ERRONEA";
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
                System.out.println("\u001B[31m Error 000: ERROR DE SINTAXIS 2\u001B[0m");
                return "\n\t\t\t^Error 005: Error 000: ERROR DE SINTAXIS";
            }

            }
            
        
        if (numPalabra<2){
            System.out.println("\u001B[31m Error 005: INSTRUCCIÓN CARECE DE  OPERANDO(S)\u001B[0m");
            return "\n\t\t\t^Error 005: INSTRUCCIÓN CARECE DE  OPERANDO(S)";
        }
       
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
    
public boolean Hexa(String palabra){
        int num=recortarCS(palabra,3).length();
        if (esD(recortarCS(recortarSS(palabra,3), num-1))==false && esD(recortarSS(recortarSS(palabra,3), num-1))==false ){
            
            return false;
        }else{
            return true;
            
        }
    }
    
    public boolean esCoH(String palabra){
        if (palabra.startsWith("'")){ 
            return true;
        }else {
            return false;
        }
    }
    
    
    //Funcion que quita ',x' / ',X', sin #
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
    //Funcion que  quita # y ',x' / ',X'
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