/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

/**
 *
 * @author 81664
 */
public class Output {
    String mensaje;
    boolean salto;
    int linea;
    boolean excepcion;
    
    public Output(){
        mensaje = "";
        salto = false;
        linea = 0;
        excepcion = false;
    }
    public Output (String mensaje){
        this.mensaje = mensaje;
        salto = false;
        linea = 0;
    }
    public Output (String mensaje, boolean b, int numLinea, boolean excepcion){
        this.mensaje = mensaje;
        salto = b;
        this.linea = numLinea;
        this.excepcion = excepcion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public boolean isSalto() {
        return salto;
    }
    
    public int getLin(){
        return linea;
    }
    
}
