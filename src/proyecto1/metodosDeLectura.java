/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//C:\Users\81664\Desktop\Proyecto 2.0\Proyecto
//C:\Users\81664\Desktop\Proyecto 2.0\Proyecto\prueba_salto.txt
package proyecto1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
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
    Hashtable<String,String> variables=new Hashtable();
    static int numMemoria = 0;
    static List<Output> salidas = new ArrayList<Output>();
    
    public void Lectura(String name){
        //Variable que ayuda a llevar el conteo de líneas que ocupan memoria 
        int numPalabra=0, inicio = 0;
        numMemoria = 0;
        String error = "", memHexa ="", memHexaAux = "", inicioHexa= "";
        boolean end=false, wrong=false;
        
        /*Se crean varios archivos dentro de la carpeta del proyecto, cada archivo guarda una lista de las
        instrucciones correspondientes a cada modo de direccionamiento */
        Mnemonicos m=new Mnemonicos();
        m.insertar();
        
        //Creamos una instancia de la clase que nos sirve para guardar variables, constantes y etiquetas
        Var_Cons_Etiq VCE= new Var_Cons_Etiq();
        
        //Se elimina "archivo.txt" si existe
        
        File f=new File("Archivo.txt");
        if (f.exists()){
            f.delete();
        }
        
        String LST=name.substring(0,name.length()-3);
        f=new File(LST + "LST");
        if (f.exists()){
            f.delete();
        }
        
        f=new File(LST + "S19");
        if (f.exists()){
            f.delete();
        }
        
        Scanner file;
        String line = "";
        try {
            file = new Scanner(new FileReader(name));
            int num=0;
            while (file.hasNextLine()&&end==false) {
                num++;
                line = file.nextLine();
                //System.out.println("*****LIRIO******La memoria es:"+numMemoria);
                
                //System.out.println(line); // Palabra que lee
                if(line.startsWith("*")){
                     //No imprimimos la memoria
                       memHexaAux = " ";
                    //Se salta a la sig línea
                    error = line;
                }else if(line.contains("EQU")||line.contains("equ")){
                        //No imprimimos la memoria
                        memHexaAux = " ";
                        error = GuardarVariables(line, variables);
                }else if(line.startsWith("\t")||line.startsWith(" ")){
                    String lineAux=line;
                    while(lineAux.startsWith(" ")){
                            lineAux=lineAux.substring(1);
                    }
                    while(lineAux.startsWith("\t")){
                            lineAux=lineAux.replace("\t","");
                    }
                    if(lineAux.startsWith("*")){
                     //No imprimimos la memoria
                       memHexaAux = " ";
                    //Se salta a la sig línea
                    error = line;
                    }else if(lineAux.startsWith("ORG")||lineAux.startsWith("org")){
                        //No imprimimos la memoria
                        memHexaAux = " ";
                        int numP=0;
                        if(lineAux.startsWith("ORG"))
                            lineAux=lineAux.replace("ORG","");
                        if(lineAux.startsWith("org"))
                                lineAux=lineAux.replace("org","");
                        while(lineAux.startsWith(" ")){
                            lineAux=lineAux.substring(1);
                        }    
                        //Asignar el valor de inicio de memoria
                        StringTokenizer st = new StringTokenizer (lineAux);
                        String palabra = "";
                        while(st.hasMoreTokens()){
                            palabra = st.nextToken();
                            numP++;
                            if(palabra.startsWith("*")){
                                numP--;
                                break;
                            }else if(palabra.startsWith("$")){
                                inicio = Integer.parseInt(palabra.substring(1),16);
                                memHexa = Integer.toHexString(inicio).toUpperCase();
                            }else{
                                break;
                            }
                            if(numP==1){
                                error= memHexa + "\t" + lineAux;
                            }else{
                                error=line+"\n\t\t\t^Error: Sintaxis incorrecta";
                            }
                        }
                    }else if(lineAux.startsWith("FCB")||lineAux.startsWith("fcb")){
                        boolean bien=false;
                        int numB = 0;
                        if(lineAux.startsWith("FCB"))
                            lineAux=lineAux.replace("FCB","");
                        if(lineAux.startsWith("fcb"))
                            lineAux=lineAux.replace("fcb","");
                        while(lineAux.startsWith(" ")){
                            lineAux=lineAux.substring(1);
                        }
                        //Separados por coma o por espacio
                        if(lineAux.contains(",")){
                            lineAux=lineAux.replace(","," ");
                        }
                        StringTokenizer st = new StringTokenizer (lineAux);
                        //Leemos la ínea hasta encontrar el primer espacio
                        String palabra = "";
                        error="";
                        while(st.hasMoreTokens()){
                            palabra = st.nextToken();
                            //System.out.println("Palabra a analizar:"+palabra);
                            if(palabra.startsWith("*")){
                                bien=true;
                                break;
                            }else if(palabra.startsWith("$")){
                                palabra = palabra.substring(1);
                                //System.out.println("Palabra substring(1):"+palabra);
                                error = error + palabra;
                                numB=numB+1;
                                //System.out.println("numB al momento de "+lineAux+" es: "+numB);
                                bien=true;
                            }else{
                                bien=false;
                                error="";
                                error=line+"\n\t\t\t^Error: Sintaxis incorrecta";
                                memHexaAux=" ";
                                break;
                            }
                        }
                        if(lineAux.equals("")){
                            //No se calcula la dirección porque no tiene operandos
                            memHexaAux=" ";
                            //Se imprime la original porque la auxiliar está vacia
                            error = line;
                        }else if(bien){
                            //Se concatena la original porque la auxiliar no tiene la instruccion fcb
                            
                            error=error+line;
                            //System.out.println("Esta es la que se imprime ¿no?"+error);
                            //Se debe calcular la dirección porque tiene operandos
                            numMemoria = numMemoria + numB;
                            memHexaAux=Integer.toHexString(inicio+numMemoria).toUpperCase();
                        }
                    }else if(lineAux.startsWith("END")||lineAux.startsWith("end")){
                        if(lineAux.contains("END"))
                            lineAux=lineAux.replace("END","");
                        if(lineAux.contains("end"))
                            lineAux=lineAux.replace("end","");
                        while(lineAux.startsWith(" ")){
                            lineAux=lineAux.substring(1);
                        }
                        if(lineAux.startsWith("*")||lineAux.equals("")){
                            end=true;
                            error=line;
                        }else{
                            System.out.println(line+"\n\t\t\t^Error: Sintaxis incorrecta");
                        }
                        //No imprimimos la memoria
                        memHexaAux = "  ";
                    }
                else{
                        memHexa = Integer.toHexString(inicio+numMemoria).toUpperCase();
                        memHexaAux = memHexa;
                        error = DifModoDeDireccionamiento(line, m, numMemoria, VCE, num, inicio);
                        if(error.contains("error")||error.contains("Error")||error.contains("ERROR")){
                            wrong=true;
                        }
                    }
                    
                }else if(!line.equals("")){
                    //No imprimimos la memoria
                    memHexaAux = " ";
                    //Con este objeto podemos dividir la cadena en sub cadenas. 
                    StringTokenizer st = new StringTokenizer (line);
                    //Leemos la ínea hasta encontrar el primer espacio
                    String palabra = st.nextToken();
                    //Convertimos la palabra en mayúsculas 
                    String auxPalabra = palabra.toUpperCase();
                    
                        if(EsInstruccion(auxPalabra, m)!=0){
                        //Error 009
                        //Pero debe revisar todos los archivos para corroborar que la primera "palabra" sea una instrucción,
                        //Si no la encuentra entonces es una ETIQUETA     
                        System.out.println("\u001B[31m Error 09: INSTRUCCIÓN CARECE DE AL MENOS UN ESPACIO RELATIVO AL MARGEN");
                        error = line+"\n\t\t\t^Error 09: INSTRUCCIÓN CARECE DE AL MENOS UN ESPACIO RELATIVO AL MARGEN"; 
                        }else{
                            error = VCE.agregarEtiqueta(palabra, numMemoria + inicio);
                            if(!variables.contains(palabra)){
                                Integer intAux = numMemoria + inicio;
                                variables.put(palabra, intAux.toHexString(inicio).toUpperCase());
                            }
                            
                            //System.out.println(VCE.Etiquetas);
                        }
                    
                }else if(line.equals("")){
                    error=line;
                }
                try {
                    FileWriter fstream = new FileWriter("Archivo.txt", true);
                    BufferedWriter out = new BufferedWriter(fstream);
                    out.write(num+" A"+" "+memHexaAux+"\t"+error+"\n");
                    out.close();
                } catch (IOException ex) {
                    System.out.println("Error: "+ex.getMessage());
                }
                
            }
            
            //System.out.println("-------LISTA DE MENSAJES--------");
            //System.out.println("tamaño: " + salidas.size());
            //Output op = new Output();
            
            /*while(!salidas.isEmpty()){
                op =salidas.remove(0);
                System.out.println(op.mensaje);
                System.out.println("Es salto: "+op.salto);
            }*/
            
            if(!end){
                
                System.out.println("\u001B[31m Error 010: NO SE ENCUENTRA END\u001B[0m");
                error="\n\t\t\t^Error 010: NO SE ENCUENTRA END";
                
                try {
                    FileWriter fstream = new FileWriter("Archivo.txt", true);
                    BufferedWriter out = new BufferedWriter(fstream);
                    out.write(error);
                    out.close();
                } catch (IOException ex) {
                    System.out.println("Error: "+ex.getMessage());
                }
                    file.close();
                }            
        }
        catch (FileNotFoundException e){
            System.out.println("Error al leer el archivo, " + e.getMessage());
        }
        
        //Segunda pasada
        String newLine = "";
        int numLinea = -1;
        int lineaSalto = 0;
        boolean excepcion = false; 
        String palabra;
        String primerPalabra = "";
        Output op = new Output();
        int ultimoSalto = 0, saltoAnterior = 0;
        List <String> segunda = new ArrayList();
        int numsalto =0;
        
        while (!salidas.isEmpty()){ //Se va vaciando la lista de mensajes
            //numsalto ++;
            //System.out.println("El salto es: " + numsalto);
            saltoAnterior = segunda.size();           
            while(lineaSalto==0&&!salidas.isEmpty()){
                //Sacamos un elemento de la lista hasta que alguno corresponda a una línea con salto
                op =salidas.remove(0);
                if (op.salto){
                    lineaSalto = op.linea;
                    excepcion = op.excepcion;
                }else{
                    System.out.print(op.mensaje);
                }
                 
            }
            
            try{
                file = new Scanner(new FileReader("Archivo.txt"));
                
                /*Mientras no se llegue al final del archivo y no llegemos a una línea con salto, 
                 se irán transcribiendo las líneas que no tienen salto e iremos eliminando las líneas leidas. 
                */
                while (file.hasNextLine()&& numLinea != lineaSalto){ 
                    //System.out.println("Escribiendo archivo LST");
                    
                    /*for(int i = 0; i<=numLinea; i++){
                        line = file.nextLine();
                    }*/
                    line = file.nextLine();
                    System.out.println(line);
                    StringTokenizer st = new StringTokenizer(line);
                    if(st.hasMoreTokens())
                        primerPalabra = st.nextToken();
                    
                    /*Se obtiene el número de la línea que se está leyendo para saber si corresponde o no a un salto*/
                    if (!primerPalabra.startsWith("^")&&st.countTokens()>2)
                        numLinea = Integer.parseInt(primerPalabra);
                    //System.out.println("numLinea = " + numLinea);
                    if (numLinea == lineaSalto){
                        if(excepcion){
                            Excepciones EXC=new Excepciones();
                            line=EXC.analizarLinea(line, m, VCE, variables, numMemoria, 2, numLinea, inicio);
                        }else{
                            Relativo REL=new Relativo();
                            line=REL.RevisarLinea(line, m, VCE, numMemoria,2, numLinea, inicio);
                            if (Relativo.error){
                                ultimoSalto = saltoAnterior;
                            }
                        }
                    }
                    if(ultimoSalto!=0){
                        if(ultimoSalto < numLinea){
                            segunda.add(line);
                            System.out.println("------Se agrega: "+ line);
                        }
                    }else{
                        segunda.add(line);
                    }
                    
                    
                        
                    
                }
                ultimoSalto = segunda.size();
                file.close();
                
            }catch(FileNotFoundException e){
                System.out.println("Error al leer el archivo, " + e.getMessage());
            }
            lineaSalto = 0;
            
            System.out.println("-------LISTA DE 2da pasada--------");
            System.out.println("tamaño: " + salidas.size());
            
            for(int k = 0; k<segunda.size();k++){
                System.out.println(segunda.get(k));
                
            }
        }
        
                       
        while(!segunda.isEmpty()){
            String l = segunda.remove(0);
            try {
                FileWriter fstream = new FileWriter(LST+"LST", true);
                BufferedWriter out = new BufferedWriter(fstream);
                out.write(l + "\n");
                out.close();
            } catch (IOException ex) {
                System.out.println("Error: "+ex.getMessage());
            }  
        }
        
        /* Generar el S19
        int noLinea = 0;
        String lineaNueva = "";
        
        if(!wrong){
           try{
                file = new Scanner(new FileReader(LST + "LST"));
                 while (file.hasNextLine()){
                     
                     //Leer linea y analizar 
                     
                     
                     
                     
                     
                     
                 }
                
           }catch(Exception e){
               
           }
           //Sacar 10 de la lista, incrementar en 1 el cntador noLinea
           inicioHexa = inicioHexa + 10*noLinea;
           
           try {
                        FileWriter fstream = new FileWriter(LST+"S19", true);
                        BufferedWriter out = new BufferedWriter(fstream);
                        out.write("<"+inicioHexa+"> " + lineaNueva + "\n");
                        out.close();
                    } catch (IOException ex) {
                        System.out.println("Error: "+ex.getMessage());
                    }  
        }*/
        
        
        
       
        
    }
    /**
     * En este método envía la línea que se está analizando a su respectiva clase para analisar cada método de direccionamiento por separado.
     * @param line es la línea del código que se está analizando.
     * @param m es una instancia de la clase Mnemónicos, nos ayuda a recuperar las istas de Mnemónicos guardades en archivos. 
     * @return newLine Es la línea que se analizó más su OPCODE o el error detectado.
     */
    public String DifModoDeDireccionamiento(String line, Mnemonicos m, int numMemoria, Var_Cons_Etiq VCE, int numLinea, int inicio){
        Inmediato IMM= new Inmediato();
        Relativo REL=new Relativo();
        Inherente INH=new Inherente();
        Excepciones EXC=new Excepciones();
        String palabra="";
        String linea;
        //Directo y Extendido
        
        String newLine="";
        int op;
        
        if(line.contains("BCLR")||line.contains("BRCLR")||line.contains("BRSET")||line.contains("BSET")||line.contains("bclr")||line.contains("brclr")||line.contains("brset")||line.contains("bset")){
            /*Si la linea contiene alguna de las 4 excepciones, va la clase de excepciones para verificar si 
            es una excepcion o tratarla como un mnemonico comun.*/
            newLine=EXC.analizarLinea(line, m, VCE, variables, numMemoria, 1, numLinea, inicio);
            //Si se regresa un error al analizar la línea, se devuelve la cadena con el error
            //f(newLine.contains("No es mnemonico excepcional") || newLine.contains("ERROR")){
            //return newLine;
            //}
        }else if(line.contains(",")){
            
            if(line.contains(",X")||line.contains(",x")){
                //Si la línea contiene es de tipo ",x" o ",X", se utiliza la clase del método de direccionamiento indexado.
                IndexadoX IND= new IndexadoX();
                newLine=IND.revisarLineaX(line, m, variables, numMemoria);
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
                linea=newLine=IND.revisarLineaY(line, m, variables, numMemoria);
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
                    //System.out.println(palabra+" Es instrucción del modo inmediato, directo o extendido");
                    newLine=IMM.AnalizarLinea(line, m, variables, numMemoria);
                    break;
                case 2:
                    //System.out.println(palabra+" Es unstrucción del modo inherente");
                    newLine=INH.AnalizarLinea(line, m, numMemoria);
                    break;
                case 3:
                    //System.out.println(palabra+" Es instrucción del modo relativo");
                    newLine=REL.RevisarLinea(line, m, VCE, numMemoria, 1, numLinea, inicio);
                    break;
                }
        }
        return newLine;
    }

    public int EsInstruccion(String palabra, Mnemonicos m) {
        int op;
        
        //Se recuperan las tablas de todos los modos de direccionamiento 
        //System.out.println("La palabra a diferenciar es:"+palabra);
        
        Hashtable<String, String> Inmediato;
        Hashtable<String,String> Inherente;
        Hashtable<String, String> Relativo;
        Hashtable<String, String> IndexadoX;
        Hashtable<String, String> IndexadoY;
        Hashtable<String, String> Directo;
        Hashtable<String, String> Extendido;
        
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
    
    public String GuardarVariables(String line, Hashtable<String,String> variables){
       //Palabra nos sirve para separar la linea en palabras y contabilizarlas
                    String palabra, clave="";
                    String valor="";
                    int numPalabra=0;
                    String aux="";

                    //Se leen las palabras de la línea
                    StringTokenizer st1 = new StringTokenizer (line);
                    while (st1.hasMoreTokens())
                    {
                        palabra = st1.nextToken();
                        numPalabra++;
                        aux="";

                        /*Guarda el nombre de la variable o etiqueta*/
                        if(numPalabra==1){
                            clave=palabra=palabra.toUpperCase(); 
                        }

                        //Guarda el contenido de la variable, quitando el $ en el proceso 
                        if((numPalabra==3)){
                            String Saux=palabra.substring(1);
                            if(Saux.length()==2||Saux.length()==4){
                               valor=Saux; 
                            }else{
                                System.out.println("\u001B[31m Error: LONGITUD DE VARIABLE INCORRECTA\u001B[0m");
                                return line+"\n\t\t\t^Error: LONGITUD DE VARIABLE INCORRECTA";
                            }
                            
                            /*int n=palabra.length();

                            // Se transforma la palabra a cadena
                            char[] p = palabra.toCharArray();
                            //Arreglo auxiliar donde guardara sin $
                            char[] auxP=new  char[n-1];
                            //proceso de guardado
                            for (int j = 0; j < n-1; j++){   
                                auxP[j]=p[j+1];
                            }
                            aux = String.valueOf(auxP);
                            int aux2=Integer.parseInt(aux);
                            valor=Integer.toHexString(aux2).toString().toUpperCase();*/
                        }
                    }
                    //Envia a la funcion para guardar en la HashTable, y revisar que no contenga
                    //otra constante o variable con el mismo nombre
                    GuardarVariablesH(clave,valor);
                    System.out.println("\033[0;1m"+valor+"          "+"\u001B[0m"+line);
                    
                return valor+"          "+line;
                    
}
    public Hashtable<String,String> GuardarVariablesH(String clave, String valor){
        
        //System.out.print("\n   "+ clave +"---"+ valor+"\n");
        if(variables.containsKey(clave)){
            variables.replace(clave, valor);
        }else{
            variables.put(clave,valor);
        }
        //System.out.print("\nVariables al momento:\n   "+variables);
        
        return variables;
        
    }
    

}
