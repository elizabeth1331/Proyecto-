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
 * @author eliss
 */
public class Variables {
    
    
    
        public Hashtable<String,Integer> GuardarVariablesH(String clave, Integer valor,Hashtable<String,Integer> variables){
        
        System.out.print("   jkjhdgjkh"+ clave +"---"+ valor+"\n ");
        variables.put(clave,valor);        
        return variables;
        
        /*
        
        st = new StringTokenizer (line);
                        //Leemos la ínea hasta encontrar el primer espacio
                        palabra = st.nextToken();
        
        
        
        if (variables.containsKey(palabra)){
               //instruccion=instruccion.concat(palabra);
               //newLine=newLine.concat(variables.get(palabra));
               System.out.println(palabra +" No es palabra repetida");
                                    }*/
    }
    
    
    public Hashtable<String,Integer> GuardarVariables(String line,Hashtable<String,Integer> variables){
       //Palabra nos sirve para separar la linea en palabras y contabilizarlas
                    String palabra, clave="";
                    Integer valor=0;
                    int numPalabra=0;

                    //Se leen las palabras de la línea
                    StringTokenizer st1 = new StringTokenizer (line);
                    while (st1.hasMoreTokens())
                    {
                        palabra = st1.nextToken();
                        numPalabra++;
                        String aux="";

                        /*Guarda el nombre de la variable o etiqueta*/
                        if(numPalabra==1){
                            clave=palabra=palabra.toUpperCase(); 
                        }

                        //Guarda el contenido de la variable, quitando el $ en el proceso 
                        if((numPalabra==3)){
                            int n=palabra.length();

                            // Se transforma la palabra a cadena
                            char[] p = palabra.toCharArray();
                            //Arreglo auxiliar donde guardara sin $
                            char[] auxP=new  char[n-1];
                            //proceso de guardado
                            for (int j = 0; j < n-1; j++){   
                                auxP[j]=p[j+1];
                            }
                             aux = String.valueOf(auxP);
                             valor=Integer.parseInt(aux);

/*
                             //En caso de que sea un operando en número hexadecimal
                            if(palabra.startsWith("$")){
                                System.out.println(palabra+" Es variable hexadecimal");
                                valor=Integer.parseInt(palabra);
                                //newLine=newLine.concat(aux);

                            //En caso de que sea un caracter
                            }else if(palabra.startsWith("#'")){
                                //System.out.println(palabra+" Es operando ASCII");
                                //Se utiliza la cadena aux para separar #' del operando
                                aux=aux.concat(palabra.substring(2));
                                //System.out.println(aux+" Es el caracter");
                                //Se comprueba si el operando es un solo caracter

                            //En caso de que sea un operando en número decimal
                            }*/
                        }
                    }
                            //Envia a la funcion para guardar en la HashTable, y revisar que no contenga
                             //otra constante o variable con el mismo nombre
                             GuardarVariablesH(clave,valor,variables);
                 return variables;
                    
}
    
}
