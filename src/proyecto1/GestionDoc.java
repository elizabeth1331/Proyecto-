package proyecto1;
import java.io.*;
import javax.swing.JOptionPane;

/**
 *
 * @author eliss
 */
public class GestionDoc {
    FileInputStream entrada;
    FileOutputStream salida;
    File archivo;
    
    
    public GestionDoc(){
        
        
    }
    
    public void BuscarA(String fd){
        metodosDeLectura op= new metodosDeLectura();
        String f;
        File archivo=new File(fd);
       
            System.out.print( archivo.getAbsolutePath());
            if(archivo.exists()){
               f = archivo.getAbsolutePath();
              System.out.print( "\nDireccion absoluta del archivo ---"+f+ " \n");
               op.Lectura(f);

            }else{
                System.out.print(" \n "+ archivo + "++++"+fd + "*****Archivo inexistente, por favor vuelve a ingresar el nombre de un archivo*****");
                JOptionPane.showMessageDialog(null, fd + "*****Archivo inexistente, por favor vuelve a ingresar el nombre de un archivo*****", "ATENCION", JOptionPane.ERROR_MESSAGE);
            }
            
        
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
    
    
        

    
}
