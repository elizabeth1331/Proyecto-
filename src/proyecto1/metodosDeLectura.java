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
 *
 * @author 81664
 */
public class metodosDeLectura {
    
    public void Lectura(String name){
        Scanner stdIn = new Scanner(System.in);
        Scanner file;
        String line;
        try {
            System.out.print("Introduzca el nombre completo del archivo (con ruta): ");
            file = new Scanner(new FileReader(stdIn.nextLine()));
            while (file.hasNextLine()) {
                line = file.nextLine();
                //System.out.println(line);
                if(line.startsWith("*")){
                    //Se salta a la sig línea
                }else if(line.startsWith("\t")|line.startsWith(" ")){
                    DifModoDeDireccionamiento(line);
                }else if(line.startsWith("EQU")|line.startsWith("equ")){
                    //Ir a la parte de constantes y variables
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
    
    public String DifModoDeDireccionamiento(String line){
        if(line.contains("#")){
            //ir a modo direccionamiento inmediato
        }else if(line.contains(",")){
            //ir a modo de direccionamiento idexado
        }else{
            //Ir a cualquiera de los cuatro restantes
        }
        return null;
    }
}
