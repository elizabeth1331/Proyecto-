package proyecto1;

import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 *
 * @author Fernanda Jiménez
 */
public class Excepciones {
    
    Hashtable<String, String> ExcepDirecto;
    Hashtable<String, String> ExcepIndexadoX;
    Hashtable<String, String> ExcepIndexadoY;
    
    //se crea objeto para conectar con la lista de Mnemonicos de excepciones
    Mnemonicos m=new Mnemonicos();
    
    public Excepciones(){
        this.ExcepDirecto = new Hashtable();
        this.ExcepIndexadoX = new Hashtable();
        this.ExcepIndexadoY = new Hashtable();
    }
    
    /**
     * En este método se analiza la línea que contiene una instrucción supuestamente de tipo excepcion para 
     * observar el tipo de operando y mandar la linea al modo de direccionamiento que segun le corresponda.
     * @return OPCODE de la instrucción procesada o mensaje de error.
     */
    
    public String analizarLinea(String line, Mnemonicos m, Hashtable<String,Integer> variables){
        
        //Se recuperan las listas de instrucciones del modo indexado 
        metodosDeLectura lectura = new metodosDeLectura();
        ExcepDirecto=m.LeerOpcode("ListaExcepDirecto.txt");
        ExcepIndexadoX=m.LeerOpcode("ListaExcepIndexadoX.txt");
        ExcepIndexadoY=m.LeerOpcode("ListaExcepIndexadoY.txt");
        
        //Palabra nos sirve para separar la linea en palabras y contabilizarlas
        String palabra;
        String palabraNueva = ""; //Para poder recorrer una palabra y visualizar el operando para discernir que tipo de opcode tendra
        String palabraXY = ""; //Para poder visualizar si el operando tiene X o Y y poner el opcode de indexado.
        String palabra1 = ""; //Para poder recorrer las palabras facilmente con nextoken sin tener que esperar a que el ciclo wile lo recorra.
        int numPalabra=0;
        int numTotalPalabra=0; //Para contar el numero de total de palabras y este no sea mas ni menos de las requeridas
        int d=0;
        boolean imm=false, hex=false, C=false, cha=false;
        
        //Esta cadena será la que se devolverá si la sintaxis es correcta, la que guarda la primera palabra y la que nos ayuda a convertir el operando
        String newLine="", instruccion="", op="";;
        
        //Aquí se quitan las comas de la linea para poder contar las palabras y asi validar que sea un mnemonico de excepcion
        line = line.replace(',', ' ');
        System.out.println(line);
        
        //Se lee el numero total de palabras de la linea para validar que no tega mas ni menos operandos de los permitidos.
        StringTokenizer stt = new StringTokenizer (line);
        numTotalPalabra = stt.countTokens();
        System.out.println("Número total de palabras en la linea es: " + numTotalPalabra);
        
        /*Se verifica que el numero de palabras sea igual o mayor a 3 ya que las 4 
        excepciones tiene al menos 2 operandos, entonces el mnemonico mas los dos operandos
        son al menos 3 palabras que debe contener la linea para que sea una instrucción de excepcion*/
        if(numTotalPalabra==1){
            System.out.println("ERROR 005: INSTRUCCIÓN CARECE DE  OPERANDO(S)");
            return "ERROR 005: INSTRUCCIÓN CARECE DE  OPERANDO(S)";
        }else if(numTotalPalabra<=2){
            System.out.println("NO es mnemonico excepcional");
            return "NO es mnemonico excepcional";
        }else if(numTotalPalabra>=3 && numTotalPalabra<=5){
            System.out.println("SI es mnemonico excepcional");
        }else if(numTotalPalabra>=6){
            if(!line.contains("*")){
                System.out.println("Error: La instrucción solo puede tener hasta maximo 3 operandos");
                return "Error: La instrucción solo puede tener hasta maximo 3 operandos";
            }
        }
        
        //Se leen las palabras de la línea
        StringTokenizer st = new StringTokenizer (line);
        
        while (st.hasMoreTokens()){
            palabra = st.nextToken();
            numPalabra++;
            palabra=palabra.toUpperCase();
            if(numPalabra==1){
                if(palabra.startsWith("*")){
                    //No hace nada pues es un comentario
                    return " ";
                }else if(palabra.equals("BCLR")){
                    d=1;
                }else if(palabra.equals("BSET")){
                    d=2;
                }else if(palabra.equals("BRCLR")){
                    d=3;
                }else if(palabra.equals("BRSET")){
                    d=4;
                }
                
                switch(d){
                    case 1:
                        if(numTotalPalabra>=3 && numTotalPalabra<=4){
                            if(numTotalPalabra==3){ //Es directo pues si tiviera X o Y tendria 4 palabras
                                palabraNueva = palabra;
                                palabraNueva= st.nextToken();
                                System.out.println("La segunda palabra es: " +palabraNueva);
                                    if(palabraNueva.contains("#")){
                                        System.out.println("Error : Las excepciones SOLO estan catalogadas en indexado o directo");
                                        return " ";
                                    }else{
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BCLR
                                            newLine=newLine.concat(ExcepDirecto.get(palabra)); //Opcode directo
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }else if(!palabraNueva.startsWith("$")){
                                            //Convierte a hexadecimal
                                            System.out.println(palabraNueva+" Es un operando numérico (decimal)");
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            System.out.println(op+" Es el operando en hexadecimal");
                                            
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BCLR
                                            newLine=newLine.concat((ExcepDirecto.get(palabra)) +" "); //Opcode directo
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                    }
                            }
                            if(numTotalPalabra==4){
                                palabraNueva = palabra;
                                palabraNueva= st.nextToken(); //Tiene la segunda palabra
                                palabraXY = palabraNueva;
                                palabraXY= st.nextToken(); //Tiene la tercer palabra
                                System.out.println("La tercer palabra es: " +palabraXY);
                                palabraXY=palabraXY.toUpperCase();
                                    if(palabraXY.equals("X")){
                                        System.out.println("es indexado con respecto a X");
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BCLR
                                            newLine=newLine.concat((ExcepIndexadoX.get(palabra))); //Opcode indexado de X
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                        else if(!palabraNueva.startsWith("$")){
                                            //Convierte a hexadecimal
                                            System.out.println(palabraNueva+" Es un operando numérico (decimal)");
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            System.out.println(op+" Es el operando en hexadecimal");
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BCLR
                                            newLine=newLine.concat((ExcepIndexadoX.get(palabra)) +" "); //Opcode indexado de X
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                    }else if(palabraXY.equals("Y")){
                                        System.out.println("es indexado con respecto a Y");
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BCLR
                                            newLine=newLine.concat((ExcepIndexadoY.get(palabra))); //Opcode indexado de X
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }else if(!palabraNueva.startsWith("$")){
                                            //Convertir a hexadecimal
                                            System.out.println(palabraNueva+" Es un operando numérico (decimal)");
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            System.out.println(op+" Es el operando en hexadecimal");
                                            
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BCLR
                                            newLine=newLine.concat((ExcepIndexadoY.get(palabra)) +" "); //Opcode indexado de X
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                    }else{
                                        System.out.println("Error: La instruccion BCLR solo acepta 2 operandos.");
                                        return "";
                                    }
                            }
                        }else{
                            System.out.println("BCLR debe tener 2 operandos");
                        }
                    break;
                    
                    case 2:
                        if(numTotalPalabra>=3 && numTotalPalabra<=4){
                            if(numTotalPalabra==3){ //Es directo pues si tiviera X o Y tendria 4 palabras
                                palabraNueva = palabra;
                                palabraNueva= st.nextToken();
                                System.out.println("La segunda palabra es: " +palabraNueva);
                                    if(palabraNueva.contains("#")){
                                        System.out.println("Error : Las excepciones SOLO estan catalogadas en indexado o directo");
                                    }else{
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BSET
                                            newLine=newLine.concat(ExcepDirecto.get(palabra)); //Opcode directo
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }else if(!palabraNueva.startsWith("$")){
                                            //Convertir a hexadecimal
                                            System.out.println(palabraNueva+" Es un operando numérico (decimal)");
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            System.out.println(op+" Es el operando en hexadecimal");
                                            
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BCLR
                                            newLine=newLine.concat((ExcepDirecto.get(palabra)) +" "); //Opcode directo
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                    }
                            }
                            if(numTotalPalabra==4){
                                palabraNueva = palabra;
                                palabraNueva= st.nextToken(); //Tiene la segunda palabra
                                palabraXY = palabraNueva;
                                palabraXY= st.nextToken(); //Tiene la tercer palabra
                                System.out.println("La tercer palabra es: " +palabraXY);
                                palabraXY=palabraXY.toUpperCase();
                                    if(palabraXY.equals("X")){
                                        System.out.println("es indexado con respecto a X");
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BCLR
                                            newLine=newLine.concat((ExcepIndexadoX.get(palabra))); //Opcode directo
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }else if(!palabraNueva.startsWith("$")){
                                            //Convertir a hexadecimal
                                            System.out.println(palabraNueva+" Es un operando numérico (decimal)");
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            System.out.println(op+" Es el operando en hexadecimal");
                                            
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BCLR
                                            newLine=newLine.concat((ExcepIndexadoX.get(palabra)) +" "); //Opcode directo
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                    }else if(palabraXY.equals("Y")){
                                        System.out.println("es indexado con respecto a Y");
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BSET
                                            newLine=newLine.concat(ExcepIndexadoY.get(palabra)); //Opcode indexado de X
                                            newLine=newLine.concat(palabraNueva); //Operando
                                        System.out.println("La linea resultante es: " +newLine);
                                        }
                                        else if(!palabraNueva.startsWith("$")){
                                            //Convertir a hexadecimal
                                            System.out.println(palabraNueva+" Es un operando numérico (decimal)");
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            System.out.println(op+" Es el operando en hexadecimal");
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BSET
                                            newLine=newLine.concat((ExcepIndexadoY.get(palabra))+" "); //Opcode indexado de X
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                    }else{
                                        System.out.println("Error: La instruccion BSET solo acepta 2 operandos.");
                                        return "";
                                    }
                            }
                        }else{
                            System.out.println("BSET debe tener 2 operandos");
                        }
                    break;
                    
                    case 3:
                        if(numTotalPalabra>=4 && numTotalPalabra<=5){
                            if(numTotalPalabra==4){//Es directo pues si tiviera X o Y tendria 5 palabras
                                palabraNueva = palabra;
                                palabraNueva= st.nextToken();
                                System.out.println("La segunda palabra es: " +palabraNueva);
                                    if(palabraNueva.contains("#")){
                                        System.out.println("Error : Las excepciones SOLO estan catalogadas en indexado o directo");
                                    }else{
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BRCLR
                                            newLine=newLine.concat(ExcepDirecto.get(palabra)); //Opcode directo
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }else if(!palabraNueva.startsWith("$")){
                                            //Convertir a hexadecimal
                                            System.out.println(palabraNueva+" Es un operando numérico (decimal)");
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            System.out.println(op+" Es el operando en hexadecimal");
                                            
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BRCLR
                                            newLine=newLine.concat((ExcepDirecto.get(palabra))+ " "); //Opcode directo
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                    }
                            }
                            if((numTotalPalabra==5)){
                                System.out.println("La palabra 1 es: " +palabra);
                                palabraNueva = palabra;
                                palabraNueva= st.nextToken(); //Tiene la segunda palabra
                                palabraXY = palabraNueva;
                                palabraXY= st.nextToken(); //Tiene la tercer palabra
                                System.out.println("La tercer palabra es: " +palabraXY);
                                palabraXY=palabraXY.toUpperCase();
                                    if(palabraXY.equals("X")){
                                        System.out.println("es indexado con respecto a X");
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BRCLR
                                            newLine=newLine.concat(ExcepIndexadoX.get(palabra)); //Opcode indexado de X
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                        else if(!palabraNueva.startsWith("$")){
                                            System.out.println(palabraNueva+" Es un operando numérico (decimal)");
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            System.out.println(op+" Es el operando en hexadecimal");
                                            
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BRCLR
                                            newLine=newLine.concat((ExcepIndexadoX.get(palabra))+ " "); //Opcode indexado de X
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                    }else if(palabraXY.equals("Y")){
                                        System.out.println("es indexado con respecto a Y");
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BRCLR
                                            newLine=newLine.concat(ExcepIndexadoY.get(palabra)); //Opcode indexado de X
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                        else if(!palabraNueva.startsWith("$")){
                                            System.out.println(palabraNueva+" Es un operando numérico (decimal)");
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            System.out.println(op+" Es el operando en hexadecimal");
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BRCLR
                                            newLine=newLine.concat((ExcepIndexadoY.get(palabra)) +" "); //Opcode indexado de X
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                    }else{
                                            System.out.println("Error: La instruccion BRCLR solo acepta 3 operandos.");
                                            return " ";
                                        }
                            }
                        }else{
                            System.out.println("BRCLR debe tener 3 operandos");
                            return " ";
                        }
                    break;
                    
                    case 4:
                        if(numTotalPalabra>=4 && numTotalPalabra<=5){
                            if(numTotalPalabra==4){//Es directo pues si tiviera X o Y tendria 5 palabras
                                palabraNueva = palabra;
                                palabraNueva= st.nextToken();
                                System.out.println("La segunda palabra es: " +palabraNueva);
                                    if(palabraNueva.contains("#")){
                                        System.out.println("Error : Las excepciones SOLO estan catalogadas en indexado o directo");
                                    }else{
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BRSET
                                            newLine=newLine.concat(ExcepDirecto.get(palabra)); //Opcode directo
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }else if(!palabraNueva.startsWith("$")){
                                            //Convierte a hexadecimal
                                            System.out.println(palabraNueva+" Es un operando numérico (decimal)");
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            System.out.println(op+" Es el operando en hexadecimal");
                                            
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BRSET
                                            newLine=newLine.concat((ExcepDirecto.get(palabra))+" "); //Opcode directo
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                    }
                            }
                            if(numTotalPalabra==5){
                                System.out.println("La palabra 1 es: " +palabra);
                                palabraNueva = palabra;
                                palabraNueva= st.nextToken(); //Tiene la segunda palabra
                                palabraXY = palabraNueva;
                                palabraXY= st.nextToken(); //Tiene la tercer palabra
                                System.out.println("La tercer palabra es: " +palabraXY);
                                palabraXY=palabraXY.toUpperCase();
                                    if(palabraXY.equals("X")){
                                        System.out.println("es indexado con respecto a X");
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BRSET
                                            newLine=newLine.concat(ExcepIndexadoX.get(palabra)); //Opcode indexado de X
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                        else if(!palabraNueva.startsWith("$")){
                                            //Convierte a hexadecimal
                                            System.out.println(palabraNueva+" Es un operando numérico (decimal)");
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            System.out.println(op+" Es el operando en hexadecimal");
                                            
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BRSET
                                            newLine=newLine.concat((ExcepIndexadoX.get(palabra))+" "); //Opcode indexado de X
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                    }else if(palabraXY.equals("Y")){
                                        System.out.println("es indexado con respecto a Y");
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BRSET
                                            newLine=newLine.concat(ExcepIndexadoY.get(palabra)); //Opcode indexado de X
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                        else if(!palabraNueva.startsWith("$")){
                                            //Convierte a hexadecimal
                                            System.out.println(palabraNueva+" Es un operando numérico (decimal)");
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            System.out.println(op+" Es el operando en hexadecimal");
                                            instruccion=instruccion.concat(palabra+" ");
                                            instruccion=instruccion.concat(palabraNueva+" ");
                                            newLine=newLine.concat(palabra+" "); //Palabra BRSET
                                            newLine=newLine.concat((ExcepIndexadoY.get(palabra))+" "); //Opcode indexado de X
                                            newLine=newLine.concat(palabraNueva); //Operando
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                    }else{
                                        System.out.println("Error: La instruccion BRSET solo acepta 3 operandos.");
                                        return " ";
                                    }
                            }
                        }else{
                            System.out.println("BRSET debe tener 3 operandos");
                            return " ";
                        }
                    break;
                }
            }
            if(numPalabra==2){ //Segundo operando o tercer palabra
                //Ya esta concatenada desde el swich case, pues se necesitaba para elegir el opcode de la instruccion.
                System.out.println("La segunda palabra es: "+palabra);
                if(palabra.equals("X")||palabra.equals("X")){
                    String tercerPalabra = palabra;
                    tercerPalabra = st.nextToken();
                    System.out.println("La palabra despues de X o Y es: "+tercerPalabra);
                }else{
                    //Concatenar si $ o # y en hexadecimal
                    if(palabra.startsWith("$") || palabra.startsWith("#$")){
                        //Se quita $ o #
                        if (palabra.startsWith("$")){
                            System.out.println("Error: El segundo operando siempre debe ser inmediato.");
                            return "Error: El segundo operando siempre debe ser inmediato";
                        }else if((palabra.startsWith("#$"))){
                            palabra = palabra.replace('$', ' ');
                            palabra = palabra.replace('#', ' ');
                            palabra=palabra.substring(1);
                        }
                        instruccion=instruccion.concat(palabra+" ");
                        newLine=newLine.concat(palabra+" ");
                        System.out.println("La linea resultante es: " +newLine);
                    }else if(!palabra.contains("$")){// || palabra.startsWith("#$")){
                        if(palabra.startsWith("#")){
                            palabra=palabra.substring(1);
                            //Convertirla a hexadecimal
                            System.out.println(palabra+" Es un operando numérico (decimal)");
                            int opN=Integer.parseInt(palabra);
                            op=Integer.toHexString(opN).toUpperCase();
                            System.out.println(op+" Es el operando en hexadecimal");
                            instruccion=instruccion.concat(op+" ");
                            newLine=newLine.concat(" "+op+" ");
                            System.out.println("La linea resultante es: " +newLine);
                        }else{
                            System.out.println("Error: El segundo operando siempre debe ser inmediato.");
                            return "Error: El segundo operando siempre debe ser inmediato";
                        }
                    }
                }
            }
            if(numPalabra==3){
                //Aqui van los saltos
                System.out.println("La tercer palabra es: "+palabra);
            }
        }
    //System.out.println("El número total de palabras leídas es "+numPalabra);
    //newLine=newLine.concat("\t\t\t"+line);
    //System.out.println("La cadena generada es: \n\n"+newLine+"\n");
    return newLine;
    }   
}