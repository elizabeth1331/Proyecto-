/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Esta clase hace el análisis principal del código del archivo .asc
 * @author todas
 */
public class metodosDeLectura {
    /**
     * En este método se lee el archivo .asc línea por línea para su análisis
     * @param name Nombre del archivo .asc
     */
    public void Lectura(String name){
        /*Se crea el archivo "inmmediato.txt" dentro de la carpeta del proyecto, la cual guarda una lista de las
        instrucciones correspondientes a este método*/
        Mnemonicos m=new Mnemonicos();
        m.insertar();
        
        Scanner file;
        String line;
        try {
            file = new Scanner(new FileReader(name));
            while (file.hasNextLine()) {
                line = file.nextLine();
                System.out.println(line);
                if(line.startsWith("*")){
                    //Se salta a la sig línea
                }else if(line.startsWith("\t")|line.startsWith(" ")){
                    DifModoDeDireccionamiento(line);
                }else if(line.startsWith("EQU")|line.startsWith("equ")){
                    //Ir a la parte de constantes y variables
                }else if(line.startsWith("ORG")||line.startsWith("org")){
                    //Asignar el valor de inicio de memoria  
                }else if(line.startsWith("END")||line.startsWith("end")){
                    //Terminar la lectura del archivo?
                }else{
                    //Error 009 
                }
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
     * @return newLine Es la línea que se analizó más su OPCODE o el error detectado.
     */
    public String DifModoDeDireccionamiento(String line){
        String newLine;
        
        if(line.contains("#")){
            //Si la línea contiene un #, se utiliza la clase correspondiente al método de direccionamiento inmediato.
            Inmediato INM= new Inmediato();
            newLine=INM.AnalizarLinea(line);
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
            //ir a modo de direccionamiento idexado
        }else{
            //Ir a cualquiera de los cuatro restantes
        }
        return null;
    }
}
