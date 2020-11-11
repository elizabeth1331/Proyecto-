/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * Clase que analiza una instrucción del modo de direccionamiento inmediato.
 * @author Ari
 */
public class Inmediato {
    Hashtable<String, String> Inmediato;
    Hashtable<String, String> Directo;
    Hashtable<String, String> Extendido;
    Hashtable<String, Integer> BytesInmediato;
    Hashtable<String, Integer> BytesDirecto;
    Hashtable<String, Integer> BytesExtendido;
    
    public Inmediato(){
        this.Inmediato = new Hashtable();
        this.Directo = new Hashtable();
        this.Extendido = new Hashtable();
        this.BytesInmediato = new Hashtable();
        this.BytesDirecto = new Hashtable();
        this.BytesExtendido = new Hashtable();
    }
    /**
     * En este método se analiza la línea que supuestamente contiene una instrucción del modo de direccionamiento inmediato.
     * @param line linea que supuestamente contiene una instrucción del modo de direccionamiento INM.
     * @return OPCODE de la instrucción procesada o mensaje de error.
     */
    public String AnalizarLinea(String line, Mnemonicos m, Hashtable<String,Integer> variables){
        //Se llama a mnemónicos para recuperar la lista de instrucciones del modo Inmediato
        metodosDeLectura lectura = new metodosDeLectura();
        Inmediato=m.LeerOpcode("ListaInmediato.txt");
        Directo=m.LeerOpcode("ListaDirecto.txt");
        Extendido=m.LeerOpcode("ListaExtendido.txt");
        BytesInmediato=m.LeerBytes("ListaInmediato.txt");
        BytesDirecto=m.LeerBytes("ListaDirecto.txt");
        BytesExtendido=m.LeerBytes("ListaExtendido.txt");
        
        //System.out.println(Inmediato);
        //System.out.println(BytesInmediato);
        
        //Palabra nos sirve para separar la linea en palabras y contabilizarlas
        String palabra;
        int numPalabra=0;
        int d=0;
        boolean imm=false, hex=false, C=false, cha=false;
        
        //Esta cadena será la que se devolverá si la sintaxis es correcta, la que guarda la primera palabra y la que nos ayuda a convertir el operando
        String newLine="", instruccion="", op="";;
        
        //Se leen las palabras de la línea
        StringTokenizer st = new StringTokenizer (line);
        while (st.hasMoreTokens())
        {
            palabra = st.nextToken();
            numPalabra++;
            
            //System.out.println(numPalabra);
            //System.out.println("Palabra: "+palabra);
            
            palabra=palabra.toUpperCase();
            
            /*Se verifica que la primera palabra sea una instrucción del modo de direccionamiento inmediato, y de 
            ser así, se concatena al inicio de la cadena que se desea devolver*/
            if(numPalabra==1){
                if((Extendido.containsKey(palabra))&&!(Directo.containsKey(palabra))&&!(Inmediato.containsKey(palabra))){
                    instruccion=instruccion.concat(palabra);
                    newLine=newLine.concat(Extendido.get(palabra));
                    System.out.println(instruccion +" Es instruccion del modo de direccionamiento extendido");
                    d=1;
                }else if((Directo.containsKey(palabra))&&!(Extendido.containsKey(palabra))&&!(Inmediato.containsKey(palabra))){
                    instruccion=instruccion.concat(palabra);
                    newLine=newLine.concat(Directo.get(palabra));
                    System.out.println(instruccion +" Es instruccion del modo de direccionamiento directo");
                    d=2;
                }else if((Inmediato.containsKey(palabra))&&!(Directo.containsKey(palabra))&&!(Extendido.containsKey(palabra))){
                    instruccion=instruccion.concat(palabra);
                    newLine=newLine.concat(Inmediato.get(palabra));
                    System.out.println(instruccion +" Es instruccion del modo de direccionamiento inmediato");
                    d=3;
                }else{
                    instruccion=instruccion.concat(palabra);
                    newLine="";
                    System.out.println(instruccion +" Puede ser instruccion de los modos inmediato, directo o extendido");
                    d=0;
                }
                
                /*else if (lectura.EsInstruccion(palabra,m)==0){
                    //Instrucción de otro modo de direccionamiento con #
                    System.out.println("Error 000: ERROR DE SINTAXIS 1");
                    return "Error 000: ERROR DE SINTAXIS";
                }*/
            }
            
            //Se verifica el operando, el cual corresponde a la segunda palabra de la línea
            if((numPalabra==2)){
                //Si tiene gato, ya sea porque es constante o es modo inmediato, se quita
                if(palabra.startsWith("#")){
                    palabra=palabra.substring(1);
                    System.out.println("Palabra sin #: "+palabra);
                    imm=true;
                }
                
                //Si es numérica pero no hexadecimal, entonces es decimal
                if((esNumero(palabra))&&!(palabra.startsWith("$"))){
                    System.out.println(palabra+" Es un operando numérico (decimal)");
                    //Se convierte a número decimal
                    int opN=Integer.parseInt(palabra);
                    //System.out.println(op+" Es el operando decimal sin #");
                    //Se convierte el número decimal a hexadecimal y como cadena
                    op=Integer.toHexString(opN).toUpperCase();
                    System.out.println(op+" Es el operando en hexadecimal");
                }
                
                //Si es hexadecimal, se quita el signo de pesos
                if(palabra.startsWith("$")){
                    palabra=palabra.substring(1);
                    System.out.println("Palabra sin $:"+palabra);
                    op=palabra;
                    hex=true;
                }
                
                //En caso de ser caracter
                if(palabra.startsWith("'")){
                    //System.out.println(palabra+" Es operando ASCII");
                    
                    //Se utiliza la cadena aux para separar #' del operando
                    palabra=palabra.substring(1);
                    System.out.println(palabra+" Es el caracter o caracteres");
                    
                    //Se comprueba si el operando es un solo caracter
                    if(palabra.length()==1){
                        char character = palabra.charAt(0);
                        //System.out.println("El caracter es: "+character);
                        
                        //Se convierte el caracter a decimal
                        int ascii = (int)character;
                        //System.out.println("El valor en ASCII es: "+ ascii);
                        
                        //Se convierte el decimal a hexadecimal y se guarda como cadena de mayúsculas
                        op= Integer.toHexString(ascii).toUpperCase();
                        System.out.println(op+" Es el operando caracter en hexadecimal");
                        cha=true;
                        
                    //Se comprueba si el operando son dos caracteres    
                    }else if(palabra.length()==2){
                        char character = palabra.charAt(0);
                        char character2= palabra.charAt(1);
                        //System.out.println("El caracter es: "+character+character2);
                        
                        //Se convierte el caracter a decimal
                        int ascii = (int)character;
                        int ascii2 = (int)character2;
                        //System.out.println("El valor en ASCII es: "+ ascii+ascii2);
                        
                        //Se convierte el decimal a hexadecimal y se guarda como cadena de mayúsculas
                        op= Integer.toHexString(ascii).toUpperCase()+Integer.toHexString(ascii2).toUpperCase();
                        System.out.println(op+" Es el operando caracter en hexadecimal");
                        cha=true;
                        
                    }else{
                        System.out.println("Error 007: MAGNITUD DE  OPERANDO ERRONEA");
                        return "Error 007: MAGNITUD DE  OPERANDO ERRONEA";
                    }
                    
                }
                
                //En caso de ser constante u op del modo inmediato
                if(imm){
                    if(variables.containsKey(palabra)){
                        //El operando es una constante porque tenía #
                        op=variables.get(palabra).toString();
                        System.out.println("El operando "+palabra+" es una constante con valor: "+op);
                        C=true;
                    }else if(((!variables.containsKey(palabra))&&(!hex))&&(!esNumero(palabra))&&(!cha)){
                        System.out.println("Error 001: CONSTANTE INEXTISTENTE");
                        return "Error 001: CONSTANTE INEXTISTENTE";
                    }
                    //El caso en que tuviese # y no se encontrara en la lista de variables o constantes también 
                    //implica que puede ser del modo inmediato.
                    
                //En caso de ser variable u op del modo directo o extendido
                }else{
                    if(variables.containsKey(palabra)){
                        //El operando es una variable porque no tenía #
                        op=variables.get(palabra).toString();
                        System.out.println("El operando "+palabra+" es una constante con valor: "+op);
                    }else if(((!variables.containsKey(palabra))&&(!hex))&&(!esNumero(palabra))&&(!cha)){
                        System.out.println("Error 002: VARIABLE INEXTISTENTE");
                        return "Error 002: VARIABLE INEXTISTENTE";
                    }
                }
                
                switch (d){
                    case 1:
                        //Extendido
                        if(BytesExtendido.get(instruccion)==(op.length()/2)&&(op.length()%2==0)){
                            //Si coinciden, se agrega a la cadena que será regresada
                            
                            System.out.println(instruccion+" Es instrucción de Extendido porque el op: "+op+" coincide con la lista");
                            newLine=newLine.concat(op);
                        }
                        break;
                    case 2:
                        //Directo
                        if(BytesDirecto.get(instruccion)==(op.length()/2)&&(op.length()%2==0)){
                            //Si coinciden, se agrega a la cadena que será regresada
                            
                            System.out.println(instruccion+" Es instrucción de Directo porque el op: "+op+" coincide con la lista");
                            newLine=newLine.concat(op);
                        }
                        break;
                    case 3:
                        //Inmediato
                        if(BytesInmediato.get(instruccion)==(op.length()/2)&&(op.length()%2==0)){
                            //Si coinciden, se agrega a la cadena que será regresada
                            
                            System.out.println(instruccion+" Es instrucción de Inmediato porque el op: "+op+" coincide con la lista");
                            newLine=newLine.concat(op);
                        }
                        break;
                    case 0:
                        //Puede ser inmediato, directo o exacto
                        
                        if((imm)&&(!C)){
                            //Tenía # pero no era constante, entonces es del modo inmediato
                            newLine=newLine.concat(Inmediato.get(instruccion));
                            System.out.println(instruccion+" Es instrucción del modo inmediato porque el op tiene # y el op no es cte.");
                            //System.out.println("La longitud del operando "+op+" es: "+op.length());
                            //System.out.println("La longitud en bytes debe ser: +"+BytesInmediato.get(instruccion));
                            if((BytesInmediato.containsKey(instruccion))&&(BytesInmediato.get(instruccion)==(op.length()/2)&&(op.length()%2==0))){
                                //Si coinciden, se agrega a la cadena que será regresada
                                System.out.println("El operando coincide con la lista de Inmediato");
                                newLine=newLine.concat(op);
                            }else{
                                System.out.println("Error 007: MAGNITUD DE  OPERANDO ERRONEA if case 0");
                                return "Error 007: MAGNITUD DE  OPERANDO ERRONEA";
                            }
                        }else if((imm)&&(C)){
                            //Tenía # y es una constante, entonces puede ser de cualquier modo
                            if((BytesDirecto.containsKey(instruccion))&&(BytesDirecto.get(instruccion)==(op.length()/2)&&(op.length()%2==0))){
                                //Si coinciden, se agrega a la cadena que será regresada
                            
                                System.out.println(instruccion+" Es instrucción de Directo porque el op: "+op+" coincide con la lista");
                            
                                newLine=newLine.concat(Directo.get(instruccion));
                                newLine=newLine.concat(op);
                            }else if((BytesExtendido.containsKey(instruccion))&&(BytesExtendido.get(instruccion)==(op.length()/2)&&(op.length()%2==0))){
                                //Si coinciden, se agrega a la cadena que será regresada
                            
                                System.out.println(instruccion+" Es instrucción de Extendido porque el op: "+op+" coincide con la lista");
                            
                                newLine=newLine.concat(Extendido.get(instruccion));
                                newLine=newLine.concat(op);
                            }else if((BytesInmediato.containsKey(instruccion))&&(BytesInmediato.get(instruccion)==(op.length()/2)&&(op.length()%2==0))){
                                //Si coinciden, se agrega a la cadena que será regresada
                            
                                System.out.println(instruccion+" Es instrucción de Inmediato porque el op: "+op+" coincide con la lista");
                            
                                newLine=newLine.concat(Inmediato.get(instruccion));
                                newLine=newLine.concat(op);
                            }else{
                                System.out.println("Error 007: MAGNITUD DE  OPERANDO ERRONEA else if case 0");
                                return "Error 007: MAGNITUD DE  OPERANDO ERRONEA";
                            }
                        }else{
                            //No tenía #, entonces puede ser de los modos directo o extendido o variable de inmediato
                            
                            //System.out.println("Lista bytesdirecto: \n"+BytesDirecto);
                            //System.out.println("Lista bytesextendido: \n"+BytesExtendido);
                            
                            //System.out.println("La longitud del operando "+op+" es: "+op.length()/2);
                            
                            if((BytesDirecto.containsKey(instruccion))&(BytesDirecto.get(instruccion))==(op.length()/2)&(op.length()%2==0)){
                                //Si coinciden, se agrega a la cadena que será regresada
                            
                                System.out.println(instruccion+" Es instrucción de Directo porque el op: "+op+" coincide con la lista");
                            
                                newLine=newLine.concat(Directo.get(instruccion));
                                newLine=newLine.concat(op);
                            }else if((BytesExtendido.containsKey(instruccion))&&(BytesExtendido.get(instruccion)==(op.length()/2)&&(op.length()%2==0))){
                                //Si coinciden, se agrega a la cadena que será regresada
                            
                                System.out.println(instruccion+" Es instrucción de Extendido porque el op: "+op+" coincide con la lista");
                            
                                newLine=newLine.concat(Extendido.get(instruccion));
                                newLine=newLine.concat(op);
                            }else if((BytesInmediato.containsKey(instruccion))&&(BytesInmediato.get(instruccion)==(op.length()/2)&&(op.length()%2==0))){
                                //Si coinciden, se agrega a la cadena que será regresada
                            
                                System.out.println(instruccion+" Es instrucción de Directo porque el op: "+op+" coincide con la lista");
                            
                                newLine=newLine.concat(Inmediato.get(instruccion));
                                newLine=newLine.concat(op);
                            }else{
                                System.out.println("Error 007: MAGNITUD DE  OPERANDO ERRONEA else case 0");
                                return "Error 007: MAGNITUD DE  OPERANDO ERRONEA";
                            }
                        }
                        break;
                }
            }
            
            if((numPalabra==3)&&(palabra.startsWith("*"))){
                //Es un comentario, no es necesario realizar nada más
            }else if((numPalabra==3)&&(!palabra.startsWith("*"))){
                System.out.println("Error 000: ERROR DE SINTAXIS 2");
                return "Error 000: ERROR DE SINTAXIS";
            }
        }
        if (numPalabra<2){
            System.out.println("Error 005: INSTRUCCIÓN CARECE DE  OPERANDO(S)");
            return "Error 005: INSTRUCCIÓN CARECE DE  OPERANDO(S)";
        }
        //System.out.println("El número total de palabras leídas es "+numPalabra);
        newLine=newLine.concat("\t\t\t"+line);
        System.out.println("La cadena generada es: \n\n"+newLine+"\n");
        return newLine;
    }
    
    
    public boolean esNumero(String operando){
        try{
            Integer.parseInt(operando);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
}
