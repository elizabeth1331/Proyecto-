/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        int numLinea = 0, numPalabra=0;
        String error = "";
        boolean end=false, wrong=false;
        
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
            int num=0;
            while (file.hasNextLine()) {
                num++;
                line = file.nextLine();
                numPalabra++;
                System.out.println(line);
                if(line.startsWith("*")){
                    //Se salta a la sig línea
                }else if(line.startsWith("\t")|line.startsWith(" ")){
                    numLinea++;
                    error = DifModoDeDireccionamiento(line, m, numLinea, VCE); 
                    if(error.contains("error")||error.contains("Error")||error.contains("ERROR")){
                        wrong=true;
                    }
                }else if(line.contains("EQU")||line.contains("equ")){
                    GuardarVariables(line);
                    error=line;
                }else if(line.startsWith("ORG")||line.startsWith("org")){
                    //Asignar el valor de inicio de memoria
                    error=line;
                }else if(line.equals("END")||line.equals("end")){
                    //Terminar la lectura del archivo?
                    end=true;
                    error=line;
                }else if(!line.equals("")){
                    //Con este objeto podemos dividir la cadena en sub cadenas. 
                    StringTokenizer st = new StringTokenizer (line);
                    //Leemos la ínea hasta encontrar el primer espacio
                    String palabra = st.nextToken();
                    //Convertimos la palabra en mayúsculas 
                    palabra = palabra.toUpperCase();
                    if(numPalabra==1){
                        if(EsInstruccion(palabra, m)!=0){
                        //Error 009
                        //Pero debe revisar todos los archivos para corroborar que la primera "palabra" sea una instrucción,
                        //Si no la encuentra entonces es una ETIQUETA     
                        System.out.println("\u001B[31m Error 09: INSTRUCCIÓN CARECE DE AL MENOS UN ESPACIO RELATIVO AL MARGEN");
                        error = line+"\n\t\t\t^Error 09: INSTRUCCIÓN CARECE DE AL MENOS UN ESPACIO RELATIVO AL MARGEN"; 
                        }else{
                            palabra = "";
                            //Con este objeto podemos dividir la cadena en sub cadenas. 
                            st = new StringTokenizer (line);
                            //Leemos la ínea hasta encontrar el primer espacio
                            palabra = st.nextToken();
                            VCE.agregarEtiqueta(palabra, numLinea + 1);                    
                        }
                    }
                }else if(line.equals("")){
                    error=line;
                }
                try {
                    FileWriter fstream = new FileWriter("Archivo.txt", true);
                    BufferedWriter out = new BufferedWriter(fstream);
                    out.write("\n"+num+" A"+"\t"+error);
                    out.close();
                } catch (IOException ex) {
                    System.out.println("Error: "+ex.getMessage());
                }
            }
            if(!end){
                System.out.println("\u001B[31m Error 010: NO SE ENCUENTRA END\u001B[0m");
                error= line+"\n\t\t\t^Error 010: NO SE ENCUENTRA END";
            }
            try {
                FileWriter fstream = new FileWriter("Archivo.txt", true);
                BufferedWriter out = new BufferedWriter(fstream);
                out.write("\n"+num+" A"+"\t"+error);
                out.close();
            } catch (IOException ex) {
                System.out.println("Error: "+ex.getMessage());
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
        Inmediato IMM= new Inmediato();
        Relativo REL=new Relativo();
        Inherente INH=new Inherente();
        String palabra="";
        String linea;
        //Directo y Extendido
        
        String newLine="";
        int op;
        
        if(line.contains(",")){
            
            if(line.contains(",X")||line.contains(",x")){
                //Si la línea contiene es de tipo ",x" o ",X", se utiliza la clase del método de direccionamiento indexado.
                IndexadoX IND= new IndexadoX();
                newLine=IND.revisarLineaX(line, m, variables);
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
                linea=newLine=IND.revisarLineaY(line, m, variables);
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
        }else if(!line.equals("")){
            StringTokenizer st = new StringTokenizer (line);
            //Leemos la ínea hasta encontrar el primer espacio
            if(st.hasMoreTokens()){
                palabra = st.nextToken();
            }
            //Convertimos la palabra en mayúsculas 
            palabra = palabra.toUpperCase();
            op=EsInstruccion(palabra,m);
            switch (op){
                case 0:
                    System.out.println("\u001B[31m Error 004: MNEMÓNICO INEXISTENTE\u001B[0m");
                    newLine=line+"\n\t\t\t^Error 004: MNEMÓNICO INEXISTENTE";
                    break;
                case 1:
                    System.out.println(palabra+" Es instrucción del modo inmediato, directo o extendido");
                    newLine=IMM.AnalizarLinea(line, m, variables);
                    break;
                case 2:
                    System.out.println(palabra+" Es unstrucción del modo inherente");
                    newLine=INH.AnalizarLinea(line, m);
                    break;
                case 3:
                    System.out.println(palabra+" Es instrucción del modo relativo");
                    newLine=REL.RevisarLinea(line, m, VCE, numLinea);
                    break;
                }
        }
        return newLine;
    }

    public int EsInstruccion(String palabra, Mnemonicos m) {
        int op;
        
        //Se recuperan las tablas de todos los modos de direccionamiento 
        //System.out.println("La palabra a diferenciar es:"+palabra);
        
        Hashtable<String, String> Inmediato = new Hashtable();
        Hashtable<String,String> Inherente = new Hashtable();
        Hashtable<String, String> Relativo = new Hashtable();
        Hashtable<String, String> IndexadoX = new Hashtable();
        Hashtable<String, String> IndexadoY = new Hashtable();
        Hashtable<String, String> Directo = new Hashtable();
        Hashtable<String, String> Extendido = new Hashtable();
        
        Inmediato = m.LeerOpcode("ListaInmediato.txt");
        Relativo = m.LeerOpcode("ListaRelativo.txt");
        Inherente = m.LeerOpcode("ListaInherente.txt");
        IndexadoX= m.LeerOpcode("ListaIndexadoX.txt");
        IndexadoY=m.LeerOpcode("ListaIndexadoY.txt");
        Directo=m.LeerOpcode("ListaDirecto.txt");
        Extendido=m.LeerOpcode("ListaExtendido.txt");
        
        if(Directo.containsKey(palabra)||Inmediato.containsKey(palabra)||Extendido.containsKey(palabra)){
            op=1;
        }else if(Inherente.containsKey(palabra)){
            op=2;
        }else if(Relativo.containsKey(palabra)){
            op=3;
        }else{
            op=0;
        }
        return op;
        //Comprobamos si la palabra corresponde a algúna instrucción 
         
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
                        }
                    }
                    //Envia a la funcion para guardar en la HashTable, y revisar que no contenga
                    //otra constante o variable con el mismo nombre
                    GuardarVariablesH(clave,valor);
                return variables;
                    
}
    public Hashtable<String,Integer> GuardarVariablesH(String clave, Integer valor){
        
        System.out.print("\n   "+ clave +"---"+ valor+"\n");
        if(variables.containsKey(clave)){
            variables.replace(clave, valor);
        }else{
            variables.put(clave,valor);
        }
        System.out.print("\nVariables al momento:\n   "+variables);
        
        return variables;
        
    }
    

}
