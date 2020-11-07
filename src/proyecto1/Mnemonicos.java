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
    Hashtable<String,String> Inherente;
    Hashtable<String,Integer> BytesInherente;
    Hashtable<String,String> Relativo;
    //Hashtable<String,String> Indexado;
    //Hashtable<String,String> directoYExtendido;
    Hashtable<String,Integer> modo;
    Hashtable<String,String> mod;
    
    public Mnemonicos(){
        this.Inmediato = new Hashtable();
        this.BytesInmediato = new Hashtable();
        this.Relativo = new Hashtable();
        this.Inherente = new Hashtable();
        this.BytesInherente = new Hashtable();
    }
    
    /**
     * En este método guarda cada una de las instrucciones con su respectivo OPCODE y/o con el tamaño de su operando.
     * 
     */
    public void insertar(){
        //OPCODE correspondiente de cada instrucción
        
        //OPCODE inmediato
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
        
        //OPCODE relativo
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
        
        //OPCODE inherente
        Inherente=new Hashtable<>();
        Inherente.put("ABA","1B");
        Inherente.put("ABX","3A");
        Inherente.put("ABY","183A");
        Inherente.put("ASLA","48");
        Inherente.put("ASLB","58");
        Inherente.put("ASLD","5");
        Inherente.put("ASRA","47");
        Inherente.put("ASRB","57");
        Inherente.put("CBA","11");
        Inherente.put("","");
        Inherente.put("CLC","0C");
        Inherente.put("CLI","0E");
        Inherente.put("CLRA","4F");
        Inherente.put("CLRB","5F");
        Inherente.put("CLV","0A");
        Inherente.put("COMA","43");
        Inherente.put("COMB","53");
        Inherente.put("DAA","19");
        Inherente.put("DECA","4A");
        Inherente.put("DECB","5A");
        Inherente.put("DES","34");
        Inherente.put("DEX","09");
        Inherente.put("DEY","1809");
        Inherente.put("FDIV","03");
        Inherente.put("IDIV","02");
        Inherente.put("INCA","4C");
        Inherente.put("INCB","5C");
        Inherente.put("INS","31");
        Inherente.put("INX","08");
        Inherente.put("INY","1808");
        Inherente.put("LSLA","48");
        Inherente.put("LSLB","58");
        Inherente.put("LSLD","05");
        Inherente.put("LSRA","44");
        Inherente.put("LSRB","54");
        Inherente.put("LSRD","04");
        Inherente.put("MUL","3D");
        Inherente.put("NEGA","40");
        Inherente.put("NEGB","50");
        Inherente.put("NOP","01");
        Inherente.put("PSHA","36");
        Inherente.put("PSHB","37");
        Inherente.put("PSHX","3C");
        Inherente.put("PSHY","183C");
        Inherente.put("PULA","32");
        Inherente.put("PULB","33");
        Inherente.put("PULX","38");
        Inherente.put("PULY","1838");
        Inherente.put("ROLA","49");
        Inherente.put("ROLB","59");
        Inherente.put("RORA","46");
        Inherente.put("RORB","56");
        Inherente.put("RTI","3B");
        Inherente.put("RTS","39");
        Inherente.put("SBA","10");
        Inherente.put("SEC","OD");
        Inherente.put("SEI","OF");
        Inherente.put("SEV","OB");
        Inherente.put("STOP","CF");
        Inherente.put("SWI","3F");
        Inherente.put("TAB","16");
        Inherente.put("TAP","06");
        Inherente.put("TBA","17");
        Inherente.put("TETS","00");
        Inherente.put("TPA","07");
        Inherente.put("TSTA","4D");
        Inherente.put("TSTB","5D");
        Inherente.put("TSX","30");
        Inherente.put("TSY","1830");
        Inherente.put("TXS","35");
        Inherente.put("TYS","1835");
        Inherente.put("WAI","3E");
        Inherente.put("XGDX","8F");
        Inherente.put("XGDY","188F");
        
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
        
        //Número de bytes modo inherente
        BytesInherente=new Hashtable<>();
        BytesInherente.put("ABA",1);
        BytesInherente.put("ABX",1);
        BytesInherente.put("ABY",2);
        BytesInherente.put("ASLA",1);
        BytesInherente.put("ASLB",1);
        BytesInherente.put("ASLD",1);
        BytesInherente.put("ASRA",1);
        BytesInherente.put("ASRB",1);
        BytesInherente.put("CBA",1);
        BytesInherente.put("CLC",1);
        BytesInherente.put("CLI",1);
        BytesInherente.put("CLRA",1);
        BytesInherente.put("CLRB",1);
        BytesInherente.put("CLV",1);
        BytesInherente.put("COMA",1);
        BytesInherente.put("COMB",1);
        BytesInherente.put("DAA",1);
        BytesInherente.put("DECA",1);
        BytesInherente.put("DECB",1);
        BytesInherente.put("DES",1);
        BytesInherente.put("DEX",1);
        BytesInherente.put("DEY",2);
        BytesInherente.put("FDIV",1);
        BytesInherente.put("IDIV",1);
        BytesInherente.put("INCA",1);
        BytesInherente.put("INCB",1);
        BytesInherente.put("INS",1);
        BytesInherente.put("INX",1);
        BytesInherente.put("INY",2);
        BytesInherente.put("LSLA",1);
        BytesInherente.put("LSLB",1);
        BytesInherente.put("LSLD",1);
        BytesInherente.put("LSRA",1);
        BytesInherente.put("LSRB",1);
        BytesInherente.put("LSRD",1);
        BytesInherente.put("MUL",1);
        BytesInherente.put("NEGA",1);
        BytesInherente.put("NEGB",1);
        BytesInherente.put("NOP",1);
        BytesInherente.put("PSHA",1);
        BytesInherente.put("PSHB",1);
        BytesInherente.put("PSHX",1);
        BytesInherente.put("PSHY",2);
        BytesInherente.put("PULA",1);
        BytesInherente.put("PULB",1);
        BytesInherente.put("PULX",1);
        BytesInherente.put("PULY",2);
        BytesInherente.put("ROLA",1);
        BytesInherente.put("ROLB",1);
        BytesInherente.put("RORA",1);
        BytesInherente.put("RORB",1);
        BytesInherente.put("RTI",1);
        BytesInherente.put("RTS",1);
        BytesInherente.put("SBA",1);
        BytesInherente.put("SEC",1);
        BytesInherente.put("SEI",1);
        BytesInherente.put("SEV",1);
        BytesInherente.put("STOP",1);
        BytesInherente.put("SWI",1);
        BytesInherente.put("TAB",1);
        BytesInherente.put("TAP",1);
        BytesInherente.put("TBA",1);
        BytesInherente.put("TETS",1);
        BytesInherente.put("TPA",1);
        BytesInherente.put("TSTA",1);
        BytesInherente.put("TSTB",1);
        BytesInherente.put("TSX",1);
        BytesInherente.put("TSY",2);
        BytesInherente.put("TXS",1);
        BytesInherente.put("TYS",2);
        BytesInherente.put("WAI",1);
        BytesInherente.put("XGDX",1);
        BytesInherente.put("XGDY",2);
        
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
        
        //Lista de modo de direccionaiento inherente
        try{
            fileOut = new ObjectOutputStream(new FileOutputStream("ListaInherente.txt"));
            fileOut.writeObject(Inherente);
            fileOut.writeObject(BytesInherente);
            fileOut.close();
        }catch(IOException e){
            System.out.println("No se pudo escribir el archivo ListaInherente.txt");
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
            }else if(file== "ListaInherente.txt"){
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
                modo=(Hashtable)fileIn.readObject();
                modo=(Hashtable)fileIn.readObject();
                fileIn.close(); 
            }else if(file== "ListaInherente.txt"){
                modo=(Hashtable)fileIn.readObject();
                modo=(Hashtable)fileIn.readObject();
                fileIn.close(); 
            }
            return modo;
        }catch(Exception e){
            System.out.println("No se pudo leer el archivo ListaInmediato.txt o no se pudo recuperar la lista");
            return null;
        }
    
    }
}
