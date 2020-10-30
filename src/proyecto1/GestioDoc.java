package proyecto1;
import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author eliss
 */
public class GestioDoc {
    FileInputStream entrada;
    FileOutputStream salida;
    File archivo;
    
    
    public GestioDoc(){
        
    }
    
    public String abrirArchivo(File archivo){
        String contenido="";
        try{
            int ascci;
            entrada= new FileInputStream(archivo);
            while((ascci = entrada.read()) != -1){
                char caracter =(char)ascci;
                contenido +=caracter;
                
            }
        }catch(Exception e){
            
        }
        return contenido;
    }
    
    
    public String GuardarArchivo(File archivo, String contenido){
        
        String respuesta=null;
        
        try {
            salida=new FileOutputStream(archivo);
            byte[] byteTxt= contenido.getBytes();
            salida.write(byteTxt);
            respuesta="Se guardo con exito el archivo";
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al guardar el contenido", "SAVE", JOptionPane.ERROR_MESSAGE);
        }
    return respuesta;
}
    
    
    public void Lectura(String name){
        Scanner stdIn = new Scanner(System.in);
        Scanner file;
        String line;
        try {
            System.out.print("Introduzca el nombre completo del archivo (con ruta): ");
            file = new Scanner(new FileReader(stdIn.nextLine()));
            while (file.hasNextLine()) {
                line = file.nextLine();
                System.out.println(line);
            }
            file.close();
        }
        catch (FileNotFoundException e){
            System.out.println("Error al leer el archivo, " + e.getMessage());
        }
    }    

    
}
