/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Esta clase hace el análisis principal del código del archivo .asc
 * @author todas
 */



public class metodosDeLectura {
    /**
     * En este método se lee el archivo .asc línea por línea para su análisis
     * @param name Nombre del archivo .asc
     */
    
    //Se crea HashTable donde guardar las variables o/y constantes
    Hashtable<String,Integer> variables=new Hashtable();
    
    public void Lectura(String name){
        //Variable que ayuda a llevar el conteo de líneas que ocupan memoria 
        int numLinea = 0;
        String error = "";
        
        /*Se crean varios archivos dentro de la carpeta del proyecto, cada archivo guarda una lista de las
        instrucciones correspondientes a cada modo de direccionamiento */
        Mnemonicos m=new Mnemonicos();
        m.insertar();
        
        //Creamos una instancia de la clase que nos sirve para guardar variables, constantes y etiquetas
        Var_Cons_Etiq VCE= new Var_Cons_Etiq();
        
        Scanner file;
        String line = "";
        try {
            file = new Scanner(new FileReader(name));
            while (file.hasNextLine()) {
                line = file.nextLine();
                System.out.println(line);
                if(line.startsWith("*")){
                    //Se salta a la sig línea
                }else if(line.startsWith("\t")|line.startsWith(" ")){
                    numLinea++;
                    error = DifModoDeDireccionamiento(line, m, numLinea, VCE); 
                }else if(line.contains("EQU")|line.contains("equ")){
                    GuardarVariables(line);
                }else if(line.startsWith("ORG")||line.startsWith("org")){
                    //Asignar el valor de inicio de memoria  
                }else if(line.startsWith("END")||line.startsWith("end")){
                    //Terminar la lectura del archivo?
                }else {
                    //Con este objeto podemos dividir la cadena en sub cadenas. 
                    StringTokenizer st = new StringTokenizer (line);
                    //Leemos la ínea hasta encontrar el primer espacio
                    String palabra = st.nextToken();
                    //Convertimos la palabra en mayúsculas 
                    palabra = palabra.toUpperCase();
                    
                    if(EsInstruccion(palabra, m)){
                        //Error 009
                        //Pero debe revisar todos los archivos para corroborar que la primera "palabra" sea una instrucción,
                        //Si no la encuentra entonces es una ETIQUETA     
                        error = "Error 09: INSTRUCCIÓN CARECE DE AL MENOS UN ESPACIO RELATIVO AL MARGEN"; 
                    }else{
                        palabra = "";
                        //Con este objeto podemos dividir la cadena en sub cadenas. 
                        st = new StringTokenizer (line);
                        //Leemos la ínea hasta encontrar el primer espacio
                        palabra = st.nextToken();
                        VCE.agregarEtiqueta(palabra, numLinea + 1);                    
                    }
                }
                    
            }
            if(line.equals("END")||line.equals("end")){
                error = "Error 010: NO SE ENCUENTRA END";
            }
            file.close();
        }
        catch (FileNotFoundException e){
            System.out.println("Error al leer el archivo, " + e.getMessage());
        }
    }
    /**
     * En este método envía la línea que se está analizando a su respectiva clase para analisar cada método de direccionamiento por separado.
     * @param line es la línea del código que se está analizando.
     * @param m es una instancia de la clase Mnemónicos, nos ayuda a recuperar las istas de Mnemónicos guardades en archivos. 
     * @return newLine Es la línea que se analizó más su OPCODE o el error detectado.
     */
    public String DifModoDeDireccionamiento(String line, Mnemonicos m, int numLinea, Var_Cons_Etiq VCE){
        String newLine;
        
        if(line.contains("#")){
            //Si la línea contiene un #, se utiliza la clase correspondiente al método de direccionamiento inmediato.
            Inmediato INM= new Inmediato();
            newLine=INM.AnalizarLinea(line, m);
            //Si se regresa un error al analizar la línea, se devuelve la cadena con el error
            if(newLine.contains("Error")){
                return newLine;
            /*Si no se encuentra un error, se agrega el código frente al OPCODE dejando un espacio de 3 tabuladores
            y se regresan ambos*/
            }else{
                newLine=newLine.concat("\t\t\t"+line);
                return newLine;
            }
        }else if(line.contains(",")){
            
            
            if(line.contains(",X")||line.contains(",x")){
                //Si la línea contiene es de tipo ",x" o ",X", se utiliza la clase del método de direccionamiento indexado.
                IndexadoX IND= new IndexadoX();
                newLine=IND.revisarLineaX(line, m);
                //Si se regresa un error al analizar la línea, se devuelve la cadena con el error
                if(newLine.contains("Error")){
                    return newLine;
                /*Si no se encuentra un error, se agrega el código frente al OPCODE dejando un espacio de 3 tabuladores
                y se regresan ambos*/
                }else{
                    newLine=newLine.concat("\t\t\t"+line);
                    return newLine;
                }
                
            }else if(line.contains(",Y")||line.contains(",y")){
                
                //Si la línea contiene es de tipo ",y" o ",Y", se utiliza la clase del método de direccionamiento indexado.
                IndexadoY IND= new IndexadoY();
                newLine=IND.revisarLineaY(line, m);
                //Si se regresa un error al analizar la línea, se devuelve la cadena con el error
                if(newLine.contains("Error")){
                    return newLine;
                /*Si no se encuentra un error, se agrega el código frente al OPCODE dejando un espacio de 3 tabuladores
                y se regresan ambos*/
                }else{
                    newLine=newLine.concat("\t\t\t"+line);
                    return newLine;
                }
            }
        
        }else{
            Relativo REL = new Relativo();
            newLine = REL.RevisarLinea(line, m, VCE, numLinea);
            Inherente INH = new Inherente();
            newLine = INH.AnalizarLinea(line, m);
        }
        return null;
    }

    public boolean EsInstruccion(String palabra, Mnemonicos m) {
        
        //Se recuperan las tablas de todos los modos de direccionamiento 
        System.out.println("La palabra a diferenciar es:"+palabra);
        
        Hashtable<String, String> Inmediato = new Hashtable();
        Hashtable<String,String> Inherente;
        Hashtable<String, String> Relativo = new Hashtable();
        Hashtable<String, String> IndexadoX = new Hashtable();
        Hashtable<String, String> IndexadoY = new Hashtable();
        //Hashtable<String,String> directoYExtendido;
        
        Inmediato = m.LeerOpcode("ListaInmediato.txt");
        Relativo = m.LeerOpcode("ListaRelativo.txt");
        Inherente = m.LeerOpcode("ListaInherente.txt");
        IndexadoX= m.LeerOpcode("ListaIndexadoX.txt");
        IndexadoY=m.LeerOpcode("ListaIndexadoY.txt");
        //Añadir los métodos para recuperar el resto de Mnemónicos
        
        
        //Comprobamos si la palabra corresponde a algúna instrucción 
        if (Inmediato.containsKey(palabra)||Relativo.containsKey(palabra)){
            System.out.println("Es instrucción");
            return true;
        }else{
            System.out.println("No es instrucción");
        }
      return false; 
    }
    
    
    public Hashtable<String,Integer> GuardarVariablesH(String clave, Integer valor){
        
        System.out.print("   "+ clave +"---"+ valor+"\n");
        variables.put(clave,valor);
        System.out.print("   "+variables);
        
        return variables;
        
        /*if (variables.containsKey(palabra)){
               //instruccion=instruccion.concat(palabra);
               //newLine=newLine.concat(variables.get(palabra));
               System.out.println(palabra +" No es palabra repetida");
                                    }*/
    }
    
    
    public Hashtable<String,Integer> GuardarVariables(String line){
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
                             GuardarVariablesH(clave,valor);
                 return variables;
                    
}
    
    

}
