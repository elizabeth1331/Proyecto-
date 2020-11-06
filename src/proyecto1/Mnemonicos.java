/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

/**
 * Esta clase crea las listas para los diferentes métodos de direccionamiento.
 * @author 81664, Ari
 */
public class Mnemonicos {
    Hashtable<String,String> Inmediato;
    Hashtable<String,Integer> BytesInmediato;
    //Hashtable<String,String> Inherente;
    Hashtable<String,String> Relativo;
    //Hashtable<String,String> Indexado;
    //Hashtable<String,String> directoYExtendido;
    Hashtable<String,Integer> modo;
    Hashtable<String,String> mod;
    
    public Mnemonicos(){
        this.Inmediato = new Hashtable();
        this.BytesInmediato = new Hashtable();
        this.Relativo = new Hashtable();
    }
    
    /**
     * En este método guarda cada una de las instrucciones con su respectivo OPCODE y/o con el tamaño de su operando.
     * 
     */
    public void insertar(){
        //OPCODE correspondiente de cada instrucción
        Inmediato=new Hashtable<>();
        Inmediato.put("ADCA","89");
        Inmediato.put("ADCB","C9");
        Inmediato.put("ADDA","8B");
        Inmediato.put("ADDB","CB");
        Inmediato.put("ADDD","C3");
        Inmediato.put("ANDA","84");
        Inmediato.put("ANDB","C4");
        Inmediato.put("BITA","85");
        Inmediato.put("BITB","C5");
        Inmediato.put("CMPA","81");
        Inmediato.put("CMPB","C1");
        Inmediato.put("CPD","1A83");
        Inmediato.put("CPX","8C");
        Inmediato.put("CPY","188C");
        Inmediato.put("EORA","88");
        Inmediato.put("EORB","C8");
        Inmediato.put("IDAA","86");
        Inmediato.put("IDAB","C6");
        Inmediato.put("IDD","CC");
        Inmediato.put("IDS","8E");
        Inmediato.put("IDX","CE");
        Inmediato.put("IDY","18CE");
        Inmediato.put("ORAA","8A");
        Inmediato.put("ORAB","CA");
        Inmediato.put("SBCA","82");
        Inmediato.put("SBCB","C2");
        Inmediato.put("SUBA","80");
        Inmediato.put("SUBB","C0");
        Inmediato.put("SUBD","83");
        
        
        Relativo.put("BCC", "24");
        Relativo.put("BCS", "25");
        Relativo.put("BEQ", "27");
        Relativo.put("BGE", "2C");
        Relativo.put("BGT", "2E");
        Relativo.put("BHI", "22");
        Relativo.put("BHS", "24");
        Relativo.put("BLE", "2F");
        Relativo.put("BLO", "25");
        Relativo.put("BLS", "23");
        Relativo.put("BLT", "2D");
        Relativo.put("BMI", "2B");
        Relativo.put("BNE", "26");
        Relativo.put("BPL", "2A");
        Relativo.put("BRA", "20");
        Relativo.put("BSR", "8D");
        Relativo.put("BVC", "28");
        Relativo.put("BVS", "29");
        
        
        
        //Número de bytes de los operandos que debería tener cada instrucción
        BytesInmediato=new Hashtable<>();
        BytesInmediato.put("ADCA",1);
        BytesInmediato.put("ADCB",1);
        BytesInmediato.put("ADDA",1);
        BytesInmediato.put("ADDB",1);
        BytesInmediato.put("ADDD",2);
        BytesInmediato.put("ANDA",1);
        BytesInmediato.put("ANDB",1);
        BytesInmediato.put("BITA",1);
        BytesInmediato.put("BITB",1);
        BytesInmediato.put("CMPA",1);
        BytesInmediato.put("CMPB",1);
        BytesInmediato.put("CPD",2);
        BytesInmediato.put("CPX",2);
        BytesInmediato.put("CPY",2);
        BytesInmediato.put("EORA",1);
        BytesInmediato.put("EORB",1);
        BytesInmediato.put("IDAA",1);
        BytesInmediato.put("IDAB",1);
        BytesInmediato.put("IDD",2);
        BytesInmediato.put("IDS",2);
        BytesInmediato.put("IDX",2);
        BytesInmediato.put("IDY",2);
        BytesInmediato.put("ORAA",1);
        BytesInmediato.put("ORAB",1);
        BytesInmediato.put("SBCA",1);
        BytesInmediato.put("SBCB",1);
        BytesInmediato.put("SUBA",1);
        BytesInmediato.put("SUBB",1);
        BytesInmediato.put("SUBD",2);
        
        
        guardarListas();
    }
    /**
     * En este método se generan los archivos que guardan las listas de instrucciones para cada método de direccionamiento
     * 
     */
    public void guardarListas(){
        ObjectOutputStream fileOut;
        try{
            fileOut = new ObjectOutputStream(new FileOutputStream("ListaInmediato.txt"));
            fileOut.writeObject(Inmediato);
            fileOut.writeObject(BytesInmediato);
            fileOut.close();
        }catch(IOException e){
            System.out.println("No se pudo escribir el archivo ListaInmediato.txt");
        }
        
        //Lista de modo de direccionamiento relativo
        try{
            fileOut = new ObjectOutputStream(new FileOutputStream("ListaRelativo.txt"));
            fileOut.writeObject(Relativo);
            fileOut.close();
        }catch(IOException e){
            System.out.println("No se pudo escribir el archivo ListaRelativo.txt");
        }
    }
    
    /**
     * En este método se lee el archivo que contiene la tabla hash del OPCODE de cada instruccion del modo de direccionamiento inmediato.
     * @return Tabla hash con valores Instruccion, OPCODE.
     */
    /*public Hashtable<String,String> LeerOpcodeINM(){
        ObjectInputStream fileIn;
        try{
            fileIn = new ObjectInputStream(new FileInputStream("ListaInmediato.txt"));
            Inmediato=(Hashtable)fileIn.readObject();
            fileIn.close();
            return Inmediato;
        }catch(Exception e){
            System.out.println("No se pudo leer el archivo ListaInmediato.txt o no se pudo recuperar la lista");
            return null;
        }
    } */
    
    /**
     * En este método se lee el archivo que contiene la tabla hash del OPCODE de cada instruccion del modo de direccionamiento relativo.
     * @return Tabla hash con valores Instruccion, OPCODE. 
     */
   /* public Hashtable<String,String> LeerOpcodeREL(){
        ObjectInputStream fileIn;
        try{
            fileIn = new ObjectInputStream(new FileInputStream("ListaRelativo.txt"));
            Relativo=(Hashtable)fileIn.readObject();
            fileIn.close();
            return Relativo;
        }catch(Exception e){
            System.out.println("No se pudo leer el archivo ListaRelativo.txt o no se pudo recuperar la lista");
            return null;
        }
    } */
    
    /**
     * En este método se lee el archivo que contiene la tabla hash con la longitud en bytes del operando de cada instruccion del modo de direccionamiento inmediato.
     * @return Tabla hash con valores Instruccion, longitud del operando en bytes.
     */
    /*public Hashtable<String,Integer> LeerBytesINM(){
        ObjectInputStream fileIn;
        try{
            fileIn = new ObjectInputStream(new FileInputStream("ListaInmediato.txt"));
            BytesInmediato=(Hashtable)fileIn.readObject();
            fileIn.close();
            return BytesInmediato;
        }catch(Exception e){
            System.out.println("No se pudo leer el archivo ListaInmediato.txt o no se pudo recuperar la lista");
            return null;
        }
    } */
    
    public Hashtable<String,String> LeerOpcode(String file){
        ObjectInputStream fileIn;
        try{
            fileIn = new ObjectInputStream(new FileInputStream(file));
            if(file == "ListaIndexadoX.txt"){
                mod=(Hashtable)fileIn.readObject();
                fileIn.close();
            }else if(file== "ListaIndexadoY.txt"){
                mod=(Hashtable)fileIn.readObject();
                fileIn.close(); 
            }else if(file== "ListaInmediato.txt"){
                mod=(Hashtable)fileIn.readObject();
                fileIn.close(); 
            }else if(file== "ListaRelativo.txt"){
                mod=(Hashtable)fileIn.readObject();
                fileIn.close(); 
            }  
            return mod;
        }catch(Exception e){
            System.out.println("No se pudo leer el archivo ListaInmediato.txt o no se pudo recuperar la lista");
            return null;
        }
    }
    
    /**
     * En este método se lee el archivo que contiene la tabla hash con la longitud en bytes del operando de cada instruccion del modo de direccionamiento inmediato.
     * @return Tabla hash con valores Instruccion, longitud del operando en bytes.
     */
    public Hashtable<String,Integer> LeerBytes(String file){
        ObjectInputStream fileIn;
        try{
            fileIn = new ObjectInputStream(new FileInputStream(file));
            
            if(file == "ListaIndexadoX.txt"){
                modo=(Hashtable)fileIn.readObject();
                fileIn.close();
            }else if(file== "ListaIndexadoY.txt"){
                modo=(Hashtable)fileIn.readObject();
                fileIn.close(); 
            }else if(file== "ListaInmediato.txt"){
                mod=(Hashtable)fileIn.readObject();
                fileIn.close(); 
            }
            return modo;
        }catch(Exception e){
            System.out.println("No se pudo leer el archivo ListaInmediato.txt o no se pudo recuperar la lista");
            return null;
        }
    
    }
}
