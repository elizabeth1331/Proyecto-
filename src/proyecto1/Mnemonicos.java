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
    Hashtable<String,String> IndexadoX;
    Hashtable<String,Integer> BytesIndexadoX;
    Hashtable<String,String> IndexadoY;
    Hashtable<String,Integer> BytesIndexadoY;
    Hashtable<String,String> Directo;
    Hashtable<String,Integer> BytesDirecto;
    Hashtable<String,String> Extendido;
    Hashtable<String,Integer> BytesExtendido;
    Hashtable<String,String> ExcepDirecto;
    Hashtable<String,String> ExcepIndexadoX;
    Hashtable<String,String> ExcepIndexadoY;
    Hashtable<String,Integer> modo;
    Hashtable<String,String> mod;
    
    public Mnemonicos(){
        this.Inmediato = new Hashtable();
        this.BytesInmediato = new Hashtable();
        this.Relativo = new Hashtable();
        this.Inherente=new Hashtable<>();
        this.IndexadoX=new Hashtable<>();
        this.IndexadoY=new Hashtable<>();
        this.Directo=new Hashtable<>();
        this.Extendido=new Hashtable<>();
        this.BytesInherente=new Hashtable<>();
        this.BytesInmediato=new Hashtable<>();
        this.BytesDirecto=new Hashtable<>();
        this.BytesExtendido=new Hashtable<>();
        this.BytesIndexadoX=new Hashtable<>();
        this.BytesIndexadoY=new Hashtable<>();
        this.ExcepDirecto = new Hashtable();
        this.ExcepIndexadoX = new Hashtable();
        this.ExcepIndexadoY = new Hashtable();
    }
    
    /**
     * En este método guarda cada una de las instrucciones con su respectivo OPCODE y/o con el tamaño de su operando.
     * 
     */
    public void insertar(){
        
        //EXCEPCIONES*****************************************
        
        //OPCODE correspondiente a modo Directo 
        
        ExcepDirecto.put("BCLR","15");
        ExcepDirecto.put("BRCLR","13");
        ExcepDirecto.put("BRSET","12");
        ExcepDirecto.put("BSET","14");
        
        //OPCODE correspondiente a modo Indexado respecto a X
        
        ExcepIndexadoX.put("BCLR","1D");
        ExcepIndexadoX.put("BRCLR","1F");
        ExcepIndexadoX.put("BRSET","1E");
        ExcepIndexadoX.put("BSET","1C");
        
        //OPCODE correspondiente a modo Indexado respecto a Y
        
        ExcepIndexadoY.put("BCLR","181D");
        ExcepIndexadoY.put("BRCLR","181F");
        ExcepIndexadoY.put("BRSET","181E");
        ExcepIndexadoY.put("BSET","181C");
        
        //EXCEPCIONES*****************************************
        
        //OPCODE correspondiente de cada instrucción Inmediato 
        
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
        Inmediato.put("LDAA","86");
        Inmediato.put("LDAB","C6");
        Inmediato.put("LDD","CC");
        Inmediato.put("LDS","8E");
        Inmediato.put("LDX","CE");
        Inmediato.put("LDY","18CE");
        Inmediato.put("ORAA","8A");
        Inmediato.put("ORAB","CA");
        Inmediato.put("SBCA","82");
        Inmediato.put("SBCB","C2");
        Inmediato.put("SUBA","80");
        Inmediato.put("SUBB","C0");
        Inmediato.put("SUBD","83");
        
        //OPCODE correspondiente de cada instrucción Relativo 
        
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
        
        //OPCODE correspondiente de cada instrucción Indexado para X
        
        
        IndexadoX.put("ADCA","A9");
        IndexadoX.put("ADCB","E9");
        IndexadoX.put("ADDA","AB");
        IndexadoX.put("ADDB","EB");
        IndexadoX.put("ADDD","E3");
        IndexadoX.put("ANDA","B4");
        IndexadoX.put("ANDB","E4");
        IndexadoX.put("ASL","68");
        IndexadoX.put("ASR","67");
        IndexadoX.put("BCLR","1D");
        IndexadoX.put("BITA","A5");
        IndexadoX.put("BITB","E5");
        IndexadoX.put("BRCLR","1F");
        IndexadoX.put("BRSET","1E");
        IndexadoX.put("BSET","1C");
        IndexadoX.put("CLR","6F");
        IndexadoX.put("CMPA","A1");
        IndexadoX.put("CMPB","E1");
        IndexadoX.put("COM","63");
        IndexadoX.put("CPD","1AA3");
        IndexadoX.put("CPX","AC");
        IndexadoX.put("CPY","1AAC");
        IndexadoX.put("DEC","6A");
        IndexadoX.put("EORA","A8");
        IndexadoX.put("EORB","E8");
        IndexadoX.put("INC","6C");
        IndexadoX.put("JMP","6E");
        IndexadoX.put("JSR","AD");
        IndexadoX.put("LDAA","A6");
        IndexadoX.put("LDAB","E6");
        IndexadoX.put("LDD","EC");
        IndexadoX.put("LDS","AE");
        IndexadoX.put("LDX","EE");
        IndexadoX.put("LDY","1AEE");
        IndexadoX.put("LSL","68");
        IndexadoX.put("LSR","64");
        IndexadoX.put("NEG","60");
        IndexadoX.put("ORAA","AA");
        IndexadoX.put("ORAB","EA");
        IndexadoX.put("ROL","69");
        IndexadoX.put("ROR","66");
        IndexadoX.put("SBCA","A2");
        IndexadoX.put("SBCB","E2");
        IndexadoX.put("STAA","A7");
        IndexadoX.put("STAB","E7");
        IndexadoX.put("STD","ED");
        IndexadoX.put("STS","AF");
        IndexadoX.put("STX","EF");
        IndexadoX.put("STY","1AEF");
        IndexadoX.put("SUBA","A0");
        IndexadoX.put("SUBB","E0");
        IndexadoX.put("SUBD","A3");
        IndexadoX.put("TST","6D");
        
        //OPCODE correspondiente de cada instrucción Indexado para Y
        
        IndexadoY.put("ADCA","18A9");
        IndexadoY.put("ADCB","18E9");
        IndexadoY.put("ADDA","18AB");
        IndexadoY.put("ADDB","18EB");
        IndexadoY.put("ADDD","18E3");
        IndexadoY.put("ANDA","18B4");
        IndexadoY.put("ANDB","18E4");
        IndexadoY.put("ASL","1868");
        IndexadoY.put("ASR","1867");
        IndexadoY.put("BCLR","181D");
        IndexadoY.put("BITA","18A5");
        IndexadoY.put("BITB","18E5");
        IndexadoY.put("BRCLR","181F");
        IndexadoY.put("BRSET","181E");
        IndexadoY.put("BSET","181C");
        IndexadoY.put("CLR","186F");
        IndexadoY.put("CMPA","18A1");
        IndexadoY.put("CMPB","18E1");
        IndexadoY.put("COM","1863");
        IndexadoY.put("CPD","CDA3");
        IndexadoY.put("CPX","CDAC");
        IndexadoY.put("CPY","18AC");
        IndexadoY.put("DEC","186A");
        IndexadoY.put("EORA","18A8");
        IndexadoY.put("EORB","18E8");
        IndexadoY.put("INC","186C");
        IndexadoY.put("JMP","186E");
        IndexadoY.put("JSR","18AD");
        IndexadoY.put("LDAA","18A6");
        IndexadoY.put("LDAB","18E6");
        IndexadoY.put("LDD","18EC");
        IndexadoY.put("LDS","18AE");
        IndexadoY.put("LDX","CDEE");
        IndexadoY.put("LDY","18EE");
        IndexadoY.put("LSL","1868");
        IndexadoY.put("LSR","1864");
        IndexadoY.put("NEG","1860");
        IndexadoY.put("ORAA","18AA");
        IndexadoY.put("ORAB","18EA");
        IndexadoY.put("ROL","1869");
        IndexadoY.put("ROR","1866");
        IndexadoY.put("SBCA","18A2");
        IndexadoY.put("SBCB","18E2");
        IndexadoY.put("STAA","18A7");
        IndexadoY.put("STAB","18E7");
        IndexadoY.put("STD","18ED");
        IndexadoY.put("STS","18AF");
        IndexadoY.put("STX","CDEF");
        IndexadoY.put("STY","18EF");
        IndexadoY.put("SUBA","18A0");
        IndexadoY.put("SUBB","18E0");
        IndexadoY.put("SUBD","18A3");
        IndexadoY.put("TST","186D");
        
        //OPCODE Directo
        
        Directo.put("ADCA","99");
        Directo.put("ADCB","D9");
        Directo.put("ADDA","9B");
        Directo.put("ADDB","DB");
        Directo.put("ADDD","D3");
        Directo.put("ANDA","94");
        Directo.put("ANDB","D4");
        //Directo.put("BCLR","15");
        Directo.put("BITA","95");
        Directo.put("BITB","D5");
        //Directo.put("BRCLR","13");
        //Directo.put("BRSET","12");
        //Directo.put("BSET","14");
        Directo.put("CMPA","91");
        Directo.put("CMPB","D1");
        Directo.put("CPD","1A93");
        Directo.put("CPX","9C");
        Directo.put("CPY","189C");
        Directo.put("EORA","98");
        Directo.put("EORB","D8");
        Directo.put("JSR","9D");
        Directo.put("IDAA","96");
        Directo.put("IDAB","D6");
        Directo.put("IDD","DC");
        Directo.put("IDS","9E");
        Directo.put("IDX","DE");
        Directo.put("IDY","18DE");
        Directo.put("ORAA","9A");
        Directo.put("ORAB","DA");
        Directo.put("SBCA","22");
        Directo.put("SBCB","D2");
        Directo.put("STAA","97");
        Directo.put("STAB","D7");
        Directo.put("STD","DD");
        Directo.put("STS","9F");
        Directo.put("STX","DF");
        Directo.put("STY","18DF");
        Directo.put("SUBA","90");
        Directo.put("SUBB","D0");
        Directo.put("SUBD","93");
        
        //OPCODE del método extendido
        Extendido.put("ADCA","B9");
        Extendido.put("ADCB","F9");
        Extendido.put("ADDA","BB");
        Extendido.put("ADDB","FB");
        Extendido.put("ADDD","F3");
        Extendido.put("ANDA","B4");
        Extendido.put("ANDB","F4");
        Extendido.put("ASL","78");
        Extendido.put("ASR","77");
        Extendido.put("BITA","B5");
        Extendido.put("BITB","F5");
        Extendido.put("CLR","7F");
        Extendido.put("CMPA","B1");
        Extendido.put("CMPB","F1");
        Extendido.put("COM","73");
        Extendido.put("CPD","1AB3");
        Extendido.put("CPX","BC");
        Extendido.put("CPY","18BC");
        Extendido.put("DEC","7A");
        Extendido.put("EORA","B8");
        Extendido.put("EORB","F8");
        Extendido.put("INC","7C");
        Extendido.put("JMP","7E");
        Extendido.put("JSR","BD");
        Extendido.put("IDAA","B6");
        Extendido.put("IDAB","F6");
        Extendido.put("IDD","FC");
        Extendido.put("IDS","BE");
        Extendido.put("IDX","FE");
        Extendido.put("IDY","18FE");
        Extendido.put("LSL","78");
        Extendido.put("LSR","74");
        Extendido.put("NEG","70");
        Extendido.put("ORAA","BA");
        Extendido.put("ORAB","FA");
        Extendido.put("ROL","79");
        Extendido.put("ROR","76");
        Extendido.put("SBCA","B2");
        Extendido.put("SBCB","F2");
        Extendido.put("STAA","B7");
        Extendido.put("STAB","F7");
        Extendido.put("STD","FD");
        Extendido.put("STS","BF");
        
        Extendido.put("STX","FF");
        Extendido.put("STY","18FF");
        
        Extendido.put("SUBA","B0");
        Extendido.put("SUBB","F0");
        Extendido.put("SUBD","B3");
        Extendido.put("TST","7D");
        
        
        
        
        //Número de bytes de los operandos que debería tener cada instrucción
        
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
        BytesInmediato.put("LDAA",1);
        BytesInmediato.put("LDAB",1);
        BytesInmediato.put("LDD",2);
        BytesInmediato.put("LDS",2);
        BytesInmediato.put("LDX",2);
        BytesInmediato.put("LDY",2);
        BytesInmediato.put("ORAA",1);
        BytesInmediato.put("ORAB",1);
        BytesInmediato.put("SBCA",1);
        BytesInmediato.put("SBCB",1);
        BytesInmediato.put("SUBA",1);
        BytesInmediato.put("SUBB",1);
        BytesInmediato.put("SUBD",2);
        
        //Numero de bytes del operando de las instricciones del modo directo
        BytesDirecto.put("ADCA",1);
        BytesDirecto.put("ADCB",1);
        BytesDirecto.put("ADDA",1);
        BytesDirecto.put("ADDB",1);
        BytesDirecto.put("ADDD",1);
        BytesDirecto.put("ANDA",1);
        BytesDirecto.put("ANDB",1);
        //BytesDirecto.put("BCLR",2);
        BytesDirecto.put("BITA",1);
        BytesDirecto.put("BITB",1);
        //BytesDirecto.put("BRCLR",3);
        //BytesDirecto.put("BRSET",3);
        //BytesDirecto.put("BSET",2);
        BytesDirecto.put("CMPA",1);
        BytesDirecto.put("CMPB",1);
        BytesDirecto.put("CPD",1);
        BytesDirecto.put("CPX",1);
        BytesDirecto.put("CPY",1);
        BytesDirecto.put("EORA",1);
        BytesDirecto.put("EORB",1);
        BytesDirecto.put("JSR",1);
        BytesDirecto.put("IDAA",1);
        BytesDirecto.put("IDAB",1);
        BytesDirecto.put("IDD",1);
        BytesDirecto.put("IDS",1);
        BytesDirecto.put("IDX",1);
        BytesDirecto.put("IDY",1);
        BytesDirecto.put("ORAA",1);
        BytesDirecto.put("ORAB",1);
        BytesDirecto.put("SBCA",1);
        BytesDirecto.put("SBCB",1);
        BytesDirecto.put("STAA",1);
        BytesDirecto.put("STAB",1);
        BytesDirecto.put("STD",1);
        BytesDirecto.put("STS",1);
        BytesDirecto.put("STX",1);
        BytesDirecto.put("STY",1);
        BytesDirecto.put("SUBA",1);
        BytesDirecto.put("SUBB",1);
        BytesDirecto.put("SUBD",1);
        
        
        //Numero de bytes de los operandos del modo Extendido
        
        BytesExtendido.put("ADCA",2);
        BytesExtendido.put("ADCB",2);
        BytesExtendido.put("ADDA",2);
        BytesExtendido.put("ADDB",2);
        BytesExtendido.put("ADDD",2);
        BytesExtendido.put("ANDA",2);
        BytesExtendido.put("ANDB",2);
        BytesExtendido.put("ASL",2);
        BytesExtendido.put("ASR",2);
        BytesExtendido.put("BITA",2);
        BytesExtendido.put("BITB",2);
        BytesExtendido.put("CLR",2);
        BytesExtendido.put("CMPA",2);
        BytesExtendido.put("CMPB",2);
        BytesExtendido.put("COM",2);
        BytesExtendido.put("CPD",2);
        BytesExtendido.put("CPX",2);
        BytesExtendido.put("CPY",2);
        BytesExtendido.put("DEC",2);
        BytesExtendido.put("EORA",2);
        BytesExtendido.put("EORB",2);
        BytesExtendido.put("INC",2);
        BytesExtendido.put("JMP",2);
        BytesExtendido.put("JSR",2);
        BytesExtendido.put("IDAA",2);
        BytesExtendido.put("IDAB",2);
        BytesExtendido.put("IDD",2);
        BytesExtendido.put("IDS",2);
        BytesExtendido.put("IDX",2);
        BytesExtendido.put("IDY",2);
        BytesExtendido.put("LSL",2);
        BytesExtendido.put("LSR",2);
        BytesExtendido.put("NEG",2);
        BytesExtendido.put("ORAA",2);
        BytesExtendido.put("ORAB",2);
        BytesExtendido.put("ROL",2);
        BytesExtendido.put("ROR",2);
        BytesExtendido.put("SBCA",2);
        BytesExtendido.put("SBCB",2);
        BytesExtendido.put("STAA",2);
        BytesExtendido.put("STAB",2);
        BytesExtendido.put("STD",2);
        BytesExtendido.put("STS",2);
        
        BytesExtendido.put("STX",2);
        BytesExtendido.put("STY",3);
        
        BytesExtendido.put("SUBA",2);
        BytesExtendido.put("SUBB",2);
        BytesExtendido.put("SUBD",2);
        BytesExtendido.put("TST",2);
        
        
        //Número de bytes que debería tener cada instrucción Indexado para X
        
        BytesIndexadoX.put("ADCA",2);
        BytesIndexadoX.put("ADCB",2);
        BytesIndexadoX.put("ADDA",2);
        BytesIndexadoX.put("ADDB",2);
        BytesIndexadoX.put("ADDD",2);
        BytesIndexadoX.put("ANDA",2);
        BytesIndexadoX.put("ANDB",2);
        BytesIndexadoX.put("ASL",2);
        BytesIndexadoX.put("ASR",2);
        BytesIndexadoX.put("BCLR",3);
        BytesIndexadoX.put("BITA",2);
        BytesIndexadoX.put("BITB",2);
        BytesIndexadoX.put("BRCLR",4);
        BytesIndexadoX.put("BRSET",4);
        BytesIndexadoX.put("BSET",3);
        BytesIndexadoX.put("CLR",2);
        BytesIndexadoX.put("CMPA",2);
        BytesIndexadoX.put("CMPB",2);
        BytesIndexadoX.put("COM",2);
        BytesIndexadoX.put("CPD",3);
        BytesIndexadoX.put("CPX",2);
        BytesIndexadoX.put("CPY",3);
        BytesIndexadoX.put("DEC",2);
        BytesIndexadoX.put("EORA",2);
        BytesIndexadoX.put("EORB",2);
        BytesIndexadoX.put("INC",2);
        BytesIndexadoX.put("JMP",2);
        BytesIndexadoX.put("JSR",2);
        BytesIndexadoX.put("LDAA",2);
        BytesIndexadoX.put("LDAB",2);
        BytesIndexadoX.put("LDD",2);
        BytesIndexadoX.put("LDS",2);
        BytesIndexadoX.put("LDX",2);
        BytesIndexadoX.put("LDY",3);
        BytesIndexadoX.put("LSL",2);
        BytesIndexadoX.put("LSR",2);
        BytesIndexadoX.put("NEG",2);
        BytesIndexadoX.put("ORAA",2);
        BytesIndexadoX.put("ORAB",2);
        BytesIndexadoX.put("ROL",2);
        BytesIndexadoX.put("ROR",2);
        BytesIndexadoX.put("SBCA",2);
        BytesIndexadoX.put("SBCB",2);
        BytesIndexadoX.put("STAA",2);
        BytesIndexadoX.put("STAB",2);
        BytesIndexadoX.put("STD",2);
        BytesIndexadoX.put("STS",2);
        BytesIndexadoX.put("STX",2);
        BytesIndexadoX.put("STY",2);
        BytesIndexadoX.put("SUBA",2);
        BytesIndexadoX.put("SUBB",2);
        BytesIndexadoX.put("SUBD",2);
        BytesIndexadoX.put("TST",2);
        
        //Número de bytes que debería tener cada instrucción Indexado para Y
        
        BytesIndexadoY.put("ADCA",3);
        BytesIndexadoY.put("ADCB",3);
        BytesIndexadoY.put("ADDA",3);
        BytesIndexadoY.put("ADDB",3);
        BytesIndexadoY.put("ADDD",3);
        BytesIndexadoY.put("ANDA",3);
        BytesIndexadoY.put("ANDB",3);
        BytesIndexadoY.put("ASL",3);
        BytesIndexadoY.put("ASR",3);
        BytesIndexadoY.put("BCLR",4);
        BytesIndexadoY.put("BITA",3);
        BytesIndexadoY.put("BITB",3);
        BytesIndexadoY.put("BRCLR",5);
        BytesIndexadoY.put("BRSET",5);
        BytesIndexadoY.put("BSET",4);
        BytesIndexadoY.put("CLR",3);
        BytesIndexadoY.put("CMPA",3);
        BytesIndexadoY.put("CMPB",3);
        BytesIndexadoY.put("COM",3);
        BytesIndexadoY.put("CPD",3);
        BytesIndexadoY.put("CPX",3);
        BytesIndexadoY.put("CPY",3);
        BytesIndexadoY.put("DEC",3);
        BytesIndexadoY.put("EORA",3);
        BytesIndexadoY.put("EORB",3);
        BytesIndexadoY.put("INC",3);
        BytesIndexadoY.put("JMP",3);
        BytesIndexadoY.put("JSR",3);
        BytesIndexadoY.put("LDAA",3);
        BytesIndexadoY.put("LDAB",3);
        BytesIndexadoY.put("LDD",3);
        BytesIndexadoY.put("LDS",3);
        BytesIndexadoY.put("LDX",3);
        BytesIndexadoY.put("LDY",3);
        BytesIndexadoY.put("LSL",3);
        BytesIndexadoY.put("LSR",3);
        BytesIndexadoY.put("NEG",3);
        BytesIndexadoY.put("ORAA",3);
        BytesIndexadoY.put("ORAB",3);
        BytesIndexadoY.put("ROL",3);
        BytesIndexadoY.put("ROR",3);
        BytesIndexadoY.put("SBCA",3);
        BytesIndexadoY.put("SBCB",3);
        BytesIndexadoY.put("STAA",3);
        BytesIndexadoY.put("STAB",3);
        BytesIndexadoY.put("STD",3);
        BytesIndexadoY.put("STS",3);
        BytesIndexadoY.put("STX",3);
        BytesIndexadoY.put("STY",3);
        BytesIndexadoY.put("SUBA",3);
        BytesIndexadoY.put("SUBB",3);
        BytesIndexadoY.put("SUBD",3);
        BytesIndexadoY.put("TST",3);
        
        //Número de bytes modo inherente
        
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
        //EXCEPCIONES*****************************************
        try{
            fileOut = new ObjectOutputStream(new FileOutputStream("ListaExcepDirecto.txt"));
            fileOut.writeObject(ExcepDirecto);
            fileOut.close();
        }catch(IOException e){
            System.out.println("No se pudo escribir el archivo ListaExcepDirecto.txt");
        }
        
        try{
            fileOut = new ObjectOutputStream(new FileOutputStream("ListaExcepIndexadoX.txt"));
            fileOut.writeObject(ExcepIndexadoX);
            fileOut.close();
        }catch(IOException e){
            System.out.println("No se pudo escribir el archivo ListaExcepIndexadoX.txt");
        }
        
        try{
            fileOut = new ObjectOutputStream(new FileOutputStream("ListaExcepIndexadoY.txt"));
            fileOut.writeObject(ExcepIndexadoY);
            fileOut.close();
        }catch(IOException e){
            System.out.println("No se pudo escribir el archivo ListaExcepIndexadoY.txt");
        }
        //EXCEPCIONES*****************************************
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
        
        try{
            fileOut = new ObjectOutputStream(new FileOutputStream("ListaIndexadoX.txt"));
            fileOut.writeObject(IndexadoX);
            fileOut.writeObject(BytesIndexadoX);
            fileOut.close();
        }catch(IOException e){
            System.out.println("No se pudo escribir el archivo ListaIndexadoX.txt");
        }
        
        try{
            fileOut = new ObjectOutputStream(new FileOutputStream("ListaIndexadoY.txt"));
            fileOut.writeObject(IndexadoY);
            fileOut.writeObject(BytesIndexadoY);
            fileOut.close();
        }catch(IOException e){
            System.out.println("No se pudo escribir el archivo ListaIndexadoY.txt");
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
        
        //Lista opcode y bytes modo de direccionamiento Directo
        try{
            fileOut = new ObjectOutputStream(new FileOutputStream("ListaDirecto.txt"));
            fileOut.writeObject(Directo);
            fileOut.writeObject(BytesDirecto);
            fileOut.close();
        }catch(IOException e){
            System.out.println("No se pudo escribir el archivo ListaDirecto.txt");
        }
        
        //Lista opcode y bytes modo de direccionamiento Extendido
        try{
            fileOut = new ObjectOutputStream(new FileOutputStream("ListaExtendido.txt"));
            fileOut.writeObject(Extendido);
            fileOut.writeObject(BytesExtendido);
            fileOut.close();
        }catch(IOException e){
            System.out.println("No se pudo escribir el archivo ListaExtendido.txt");
        }
    }
    
    /**
     * En este método se lee el archivo que contiene la tabla hash del OPCODE de cada instruccion del modo de direccionamiento inmediato.
     * @return Tabla hash con valores Instruccion, OPCODE.
     */
  
    
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
            }else if(file== "ListaDirecto.txt"){
                mod=(Hashtable)fileIn.readObject();
                fileIn.close(); 
            }else if(file== "ListaExtendido.txt"){
                mod=(Hashtable)fileIn.readObject();
                fileIn.close(); 
            }else if(file == "ListaExcepIndexadoX.txt"){
                mod=(Hashtable)fileIn.readObject();
                fileIn.close();
            }else if(file== "ListaExcepIndexadoY.txt"){
                mod=(Hashtable)fileIn.readObject();
                fileIn.close(); 
            }else if(file== "ListaExcepDirecto.txt"){
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
                modo=(Hashtable)fileIn.readObject();
                fileIn.close();
            }else if(file== "ListaIndexadoY.txt"){
                modo=(Hashtable)fileIn.readObject();
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
            }else if(file== "ListaDirecto.txt"){
                modo=(Hashtable)fileIn.readObject();
                modo=(Hashtable)fileIn.readObject();
                fileIn.close(); 
            }else if(file== "ListaExtendido.txt"){
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
