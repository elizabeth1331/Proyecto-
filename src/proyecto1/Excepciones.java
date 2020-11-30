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
    static Boolean error;
    
    //se crea objeto para conectar con la lista de Mnemonicos de excepciones
    Mnemonicos m=new Mnemonicos();
    
    public Excepciones(){
        this.ExcepDirecto = new Hashtable();
        this.ExcepIndexadoX = new Hashtable();
        this.ExcepIndexadoY = new Hashtable();
        error = false;
    }
    
    /**
     * En este método se analiza la línea que contiene una instrucción supuestamente de tipo excepcion para 
     * observar el tipo de operando y mandar la linea al modo de direccionamiento que segun le corresponda.
     * @return OPCODE de la instrucción procesada o mensaje de error.
     */
    
    public String analizarLinea(String line, Mnemonicos m, Var_Cons_Etiq VCE, Hashtable<String,String> variables, int numMemoria, int pasada, int numLinea, int ini){
        
        //Se recuperan las listas de instrucciones del modo indexado 
        metodosDeLectura lectura = new metodosDeLectura();
        ExcepDirecto=m.LeerOpcode("ListaExcepDirecto.txt");
        ExcepIndexadoX=m.LeerOpcode("ListaExcepIndexadoX.txt");
        ExcepIndexadoY=m.LeerOpcode("ListaExcepIndexadoY.txt");
        
        //Palabra nos sirve para separar la linea en palabras y contabilizarlas
        String palabra;
        String palabraNueva = ""; //Para poder recorrer una palabra y visualizar el operando para discernir que tipo de opcode tendra
        String palabraNuevaAux = "";
        String palabraXY = ""; //Para poder visualizar si el operando tiene X o Y y poner el opcode de indexado.
        String palabra1 = ""; //Para poder recorrer las palabras facilmente con nextoken sin tener que esperar a que el ciclo wile lo recorra.
        String mensaje = ""; //Para guardar los mensajes que se deberán mostrar en pantalla en la segunda pasada
        String lineAux = "" ; //Guardamos la línea sin las comas
        int numPalabra=0;
        int numTotalPalabra=0; //Para contar el numero de total de palabras y este no sea mas ni menos de las requeridas
        int d=0;
        boolean imm=false, hex=false, C=false, cha=false;
        
        //Esta cadena será la que se devolverá si la sintaxis es correcta, la que guarda la primera palabra y la que nos ayuda a convertir el operando
        String newLine="", instruccion="", op="",newLine2="";
        
        //Aquí se quitan las comas de la linea para poder contar las palabras y asi validar que sea un mnemonico de excepcion
        
        lineAux = line.replace(',', ' ');
        
        if(lineAux.contains("*")){
            int indice= lineAux.indexOf("*");
            System.out.println("Indice: " + indice);
            System.out.println("Tamaño: "+ lineAux.length());
            String comentario = lineAux.substring(indice, lineAux.length()-2);
            lineAux = lineAux.substring(0, indice);
            System.out.println("El comentario es: " + comentario);
            System.out.println("El lineAux: " + lineAux);
        }
        
        
        
        //System.out.println(line);
        
        //Se lee el numero total de palabras de la linea para validar que no tega mas ni menos operandos de los permitidos.
        StringTokenizer stt = new StringTokenizer (lineAux);
        numTotalPalabra = stt.countTokens();
        System.out.println("Número total de palabras en la linea es: " + numTotalPalabra);
        
        /*Si es la primer "pasada", se verifica que el numero de palabras sea igual o mayor a 3 ya que las 4 
        excepciones tiene al menos 2 operandos, entonces el mnemonico mas los dos operandos
        son al menos 3 palabras que debe contener la linea para que sea una instrucción de excepcion*/
        if(pasada == 1){
            if(numTotalPalabra==1){
                mensaje= "\u001B[31m ERROR 005: INSTRUCCIÓN CARECE DE  OPERANDO(S) \u001B[0m\n";
                //Guardamos la salida de la primer pasada
                Output outPut = new Output(mensaje);
                metodosDeLectura.salidas.add(outPut);
                return line + "\n\t\t\t^ERROR 005: INSTRUCCIÓN CARECE DE  OPERANDO(S)";
            }else if(numTotalPalabra<=2){
                System.out.println("");
                mensaje= "\u001B[31m Error: Los mnemonicos de excepción deben tener más de un operando. \u001B[0m\n";
                //Guardamos la salida de la primer pasada
                Output outPut = new Output(mensaje);
                metodosDeLectura.salidas.add(outPut);
                
                return line + "\n\t\t\t^Error: Los mnemonicos de excepción deben tener más de un operando.";
            }else if(numTotalPalabra>=3 && numTotalPalabra<=5){
                //System.out.println("SI es mnemonico excepcional");
                palabra = stt.nextToken();
                
            } /*else if(numTotalPalabra>=6){
                if(!lineAux.startsWith("*")){
                    mensaje = "\u001B[31m Error: La instrucción solo puede tener hasta maximo 3 operandos \u001B[0m\n";
                    //Guardamos la salida de la primer pasada
                    Output outPut = new Output(mensaje);
                    metodosDeLectura.salidas.add(outPut);
                    return line + "\n\t\t\t^Error: La instrucción solo puede tener hasta maximo 3 operandos";
                }
            }*/
                 
        
        
        //Se leen las palabras de la línea
        StringTokenizer st = new StringTokenizer (lineAux);
        
        while (st.hasMoreTokens()){
                       
            palabra = st.nextToken();
            numPalabra++;
            
            palabra=palabra.toUpperCase();
            if(numPalabra==1){
                if(palabra.equals("BCLR")){
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
                            palabraNueva = palabra;
                            palabraNueva= st.nextToken();//Segunda
                            
                            
                            if(numTotalPalabra==3){ //Es directo pues si tiviera X o Y tendria 4 palabras
                                
                                /* Como el opcode que le corresponde es el del modo DIRECTO,
                                al conteo de memoria le debemos sumar 3 (número de bytes que ocupa el opcode con el operando)
                                */
                                metodosDeLectura.numMemoria = metodosDeLectura.numMemoria + 3;
                                
                                    if(palabraNueva.contains("#")){
                                        mensaje = "\u001B[31m Error : Las excepciones SOLO estan catalogadas en indexado o directo \u001B[0m\n";
                                        
                                        //Guardamos la salida de la primer pasada
                                        Output outPut = new Output(mensaje);
                                        metodosDeLectura.salidas.add(outPut);
                                        
                                        return line + "\n\t\t\t^Error : Las excepciones SOLO estan catalogadas en indexado o directo \u001B[0m\n";
                                    }else{
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNuevaAux = palabraNueva.replace('$', ' ');
                                            newLine=newLine.concat("\033[43;31m"+ExcepDirecto.get(palabra)); //Opcode directo (color)
                                            newLine2=newLine2.concat(ExcepDirecto.get(palabra)); //Opcode directo
                                            
                                            palabraNuevaAux = palabraNueva.substring(1);
                                            
                                            newLine=newLine.concat("\u001B[41;33m"+palabraNuevaAux+"\u001B[0m"); //Operando (color)
                                            newLine2=newLine2.concat(palabraNuevaAux); //Operando
                                            
                                          
                                        }else if(!palabraNueva.startsWith("$")){
                                            //Convierte a hexadecimal
                                            
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            
                                            newLine=newLine.concat(("\033[43;31m"+ ExcepDirecto.get(palabra))); //Opcode directo (color)
                                            newLine2=newLine2.concat(ExcepDirecto.get(palabra)); //Opcode directo 
                                            
                                            palabraNueva = op;
                                            
                                            newLine=newLine.concat("\u001B[41;33m" + palabraNueva + "\u001B[0m"); //Operando (color)
                                            newLine2=newLine2.concat(palabraNueva); //Operando
                                        }
                                    }
                            }
                            if(numTotalPalabra==4){
                                
                                palabraXY = st.nextToken();//Tiene la tercer palara
                                palabraXY=palabraXY.toUpperCase();
                                    if(palabraXY.equals("X")){
                                        
                                        /* Como el opcode que le corresponde es el del modo INDEXADO con respecto a X,
                                        al conteo de memoria le debemos sumar 3 (número de bytes que ocupa el opcode con el operando)
                                        */
                                        metodosDeLectura.numMemoria = metodosDeLectura.numMemoria + 3;
                                        
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            palabraNueva = palabraNueva.substring(1);
                                            
                                            newLine=newLine.concat("\033[43;31m"+(ExcepIndexadoX.get(palabra))); //Opcode indexado de X (color)
                                            newLine2=newLine2.concat(ExcepIndexadoX.get(palabra)); //Opcode indexado de X
                                           
                                            newLine=newLine.concat("\u001B[41;33m"+palabraNueva+"\u001B[0m"); //Operando (color)
                                            newLine2=newLine2.concat(palabraNueva); //Operando
                                            
                                        }
                                        else if(!palabraNueva.startsWith("$")){
                                            //Convierte a hexadecimal
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            
                                            
                                            newLine=newLine.concat("\033[43;31m"+(ExcepIndexadoX.get(palabra))); //Opcode indexado de X (color)
                                            newLine2=newLine2.concat(ExcepIndexadoX.get(palabra)); //Opcode indexado de X
                                            
                                            palabraNueva = op;
                                            newLine=newLine.concat("\u001B[41;33m"+palabraNueva+"\u001B[0m"); //Operando (color)
                                            newLine2=newLine2.concat(palabraNueva); //Operando
                                            
                                        }
                                    }else if(palabraXY.equals("Y")){
                                        
                                        /* Como el opcode que le corresponde es el del modo INDEXADO con respecto a Y,
                                        al conteo de memoria le debemos sumar 4 (número de bytes que ocupa el opcode con el operando)
                                        */
                                        metodosDeLectura.numMemoria = metodosDeLectura.numMemoria + 4;
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            palabraNueva = palabraNueva.substring(1);
                                            
                                            
                                            newLine=newLine.concat("\033[43;31m"+(ExcepIndexadoY.get(palabra))); //Opcode indexado de X (color)
                                            newLine2=newLine2.concat(ExcepIndexadoY.get(palabra)); //Opcode indexado de X
                                            
                                            newLine=newLine.concat("\u001B[41;33m"+palabraNueva+"\u001B[0m"); //Operando (color)
                                            newLine2=newLine2.concat(palabraNueva); //Operando
                                            
                                        }else if(!palabraNueva.startsWith("$")){
                                            //Convertir a hexadecimal
                                            
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            
                                            newLine=newLine.concat("\033[43;31m"+(ExcepIndexadoY.get(palabra))); //Opcode indexado de X (color)
                                            newLine2=newLine2.concat(ExcepIndexadoY.get(palabra)); //Opcode indexado de X
                                            
                                            palabraNueva = op;
                                            newLine=newLine.concat("\u001B[41;33m"+palabraNueva+"\u001B[0m"); //Operando (color)
                                            newLine2=newLine2.concat(palabraNueva); //Operando
                                            
                                        }
                                    }else{
                                        
                                        mensaje ="\u001B[31m Error: Error de sintaxis. \u001B[0m\n";
                                        //Guardamos la salida de la primer pasada
                                        Output outPut = new Output(mensaje);
                                        metodosDeLectura.salidas.add(outPut);
                                        return line + "\n\t\t\t^Error: Error de sintaxis. ";
                                    }    
                            }
                        }else{
                                    mensaje = "\u001B[31m Error : BCLR debe tener 2 operandos.\u001B[0m\n";
                                    //Guardamos la salida de la primer pasada
                                    Output outPut = new Output(mensaje);
                                    metodosDeLectura.salidas.add(outPut);
                                    return line + "\n\t\t\t^Error : BCLR debe tener 2 operandos.";
                                }
                    break;
                    
                    case 2:
                        if(numTotalPalabra>=3 && numTotalPalabra<=4){
                            
                            palabraNueva = palabra;
                            palabraNueva= st.nextToken();
                            
                            if(numTotalPalabra==3){ //Es directo pues si tiviera X o Y tendria 4 palabras
                                
                                /* Como el opcode que le corresponde es el del modo DIRECTO,
                                al conteo de memoria le debemos sumar 3 (número de bytes que ocupa el opcode con el operando)
                                */
                                metodosDeLectura.numMemoria = metodosDeLectura.numMemoria + 3;
                                
                                    if(palabraNueva.contains("#")){
                                        mensaje = "\u001B[31m Error : Las excepciones SOLO estan catalogadas en indexado o directo. \u001B[0m \n";
                                        //Guardamos la salida de la primer pasada
                                        Output outPut = new Output(mensaje);
                                        metodosDeLectura.salidas.add(outPut);
                                        return line + "\n\t\t\t^Error : Las excepciones SOLO estan catalogadas en indexado o directo.";
                                    }else{
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            palabraNueva = palabraNueva.substring(1);
                                           
                                            newLine=newLine.concat("\033[43;31m"+ExcepDirecto.get(palabra)); //Opcode directo (color)
                                            newLine2=newLine2.concat(ExcepDirecto.get(palabra)); //Opcode directo
                                            
                                            newLine=newLine.concat("\u001B[41;33m"+palabraNueva+"\u001B[0m"); //Operando (color)
                                            newLine2=newLine2.concat(palabraNueva); //Operando
                                            
                                            
                                        }else if(!palabraNueva.startsWith("$")){
                                            //Convertir a hexadecimal
                                            
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            
                                            newLine=newLine.concat("\033[43;31m"+(ExcepDirecto.get(palabra))); //Opcode directo (color)
                                            newLine2=newLine2.concat(ExcepDirecto.get(palabra)); //Opcode directo
                                            
                                            palabraNueva = op;
                                            newLine=newLine.concat("\u001B[41;33m"+palabraNueva+"\u001B[0m"); //Operando (color)
                                            newLine2=newLine2.concat(palabraNueva); //Operando
                                            
                                        }
                                    }
                            }
                            if(numTotalPalabra==4){
                                palabraXY = st.nextToken();//Tiene la tercer palara
                                System.out.println("La tercer palabra es: " +palabraXY);
                                palabraXY=palabraXY.toUpperCase();
                                    if(palabraXY.equals("X")){
                                        
                                        /* Como el opcode que le corresponde es el del modo INDEXADO con respecto a X,
                                        al conteo de memoria le debemos sumar 3 (número de bytes que ocupa el opcode con el operando)
                                        */
                                        metodosDeLectura.numMemoria = metodosDeLectura.numMemoria + 3;
                                        
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            palabraNueva = palabraNueva.substring(1); //Quitamos el espacio
                                            
                                            newLine=newLine.concat("\033[43;31m"+(ExcepIndexadoX.get(palabra))); //Opcode directo (color)
                                            newLine2=newLine2.concat(ExcepIndexadoX.get(palabra)); //Opcode directo
                                            
                                            newLine=newLine.concat("\u001B[41;33m"+palabraNueva+"\u001B[0m"); //Operando (color)
                                            newLine2=newLine2.concat(palabraNueva); //Operando
                                            
                                        }else if(!palabraNueva.startsWith("$")){
                                            //Convertir a hexadecimal
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            
                                            newLine=newLine.concat("\033[43;31m"+(ExcepIndexadoX.get(palabra))); //Opcode directo (color)
                                            newLine2=newLine2.concat(ExcepIndexadoX.get(palabra)); //Opcode directo
                                            
                                            newLine=newLine.concat("\u001B[41;33m"+op+"\u001B[0m"); //Operando (color)
                                            newLine2=newLine2.concat(op); //Operando
                                            
                                        }
                                    }else if(palabraXY.equals("Y")){
                                        
                                        /* Como el opcode que le corresponde es el del modo INDEXADO con respecto a Y,
                                        al conteo de memoria le debemos sumar 4 (número de bytes que ocupa el opcode con el operando)
                                        */
                                        metodosDeLectura.numMemoria = metodosDeLectura.numMemoria + 4;
                                        
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            palabraNueva = palabraNueva.substring(1); //Quitamos el espacio
                                            
                                            newLine=newLine.concat("\033[43;31m"+ExcepIndexadoY.get(palabra)); //Opcode indexado de X (color)
                                            newLine2=newLine2.concat(ExcepIndexadoY.get(palabra)); //Opcode indexado de X
                                            
                                            newLine=newLine.concat("\u001B[41;33m"+palabraNueva+"\u001B[0m"); //Operando (color)
                                            newLine2=newLine2.concat(palabraNueva); //Operando
                                            
                                        }
                                        else if(!palabraNueva.startsWith("$")){
                                            //Convertir a hexadecimal
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                           
                                            newLine=newLine.concat("\033[43;31m"+(ExcepIndexadoY.get(palabra))); //Opcode indexado de X (color)
                                            newLine2=newLine2.concat(ExcepIndexadoY.get(palabra)); //Opcode indexado de X
                                            
                                            newLine=newLine.concat("\u001B[41;33m"+op+"\u001B[0m"); //Operando (color)
                                            newLine2=newLine2.concat(op); //Operando
                                            
                                        }
                                    }else{
                                        
                                        mensaje = "\u001B[31m Error: Error de sintaxis. \u001B[0m\n";
                                        //Guardamos la salida de la primer pasada
                                        Output outPut = new Output(mensaje);
                                        metodosDeLectura.salidas.add(outPut);
                                        
                                        return line + "\n\t\t\t^Error: Error de sintaxis.";
                                    }
                            }
                        }else{
                            mensaje = "\u001B[31m Error : BSET debe tener 2 operandos.\u001B[0m\n";
                            //Guardamos la salida de la primer pasada
                            Output outPut = new Output(mensaje);
                            metodosDeLectura.salidas.add(outPut);
                            return line + "\n\t\t\t^Error : BSET debe tener 2 operandos.";
                        }
                    break;
                    
                    case 3:
                        if(numTotalPalabra>=4 && numTotalPalabra<=5){
                            palabraNueva = palabra;
                            palabraNueva= st.nextToken();
                            System.out.println("La segunda palabra es: " +palabraNueva);
                            
                            if(numTotalPalabra==4){//Es directo pues si tiviera X o Y tendria 5 palabras
                                
                                /* Como el opcode que le corresponde es el del modo DIRECTO,
                                al conteo de memoria le debemos sumar 4 (número de bytes que ocupa el opcode con el operando)
                                */
                                metodosDeLectura.numMemoria = metodosDeLectura.numMemoria + 4;
                                
                               
                                    if(palabraNueva.contains("#")){
                                        mensaje = "\u001B[31m Error : Las excepciones SOLO estan catalogadas en indexado o directo. \u001B[0m\n";
                                        //Guardamos la salida de la primer pasada
                                        Output outPut = new Output(mensaje);
                                        metodosDeLectura.salidas.add(outPut);
                                        return line +"\n\t\t\t^Error : Las excepciones SOLO estan catalogadas en indexado o directo.";
                                    }else{
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            palabraNueva = palabraNueva.substring(1); //Quitamos el espacio
                                            
                                            newLine=newLine.concat("\033[43;31m"+ExcepDirecto.get(palabra)); //Opcode directo (color)
                                            newLine2=newLine2.concat(ExcepDirecto.get(palabra)); //Opcode directo
                                            
                                            newLine=newLine.concat("\u001B[41;33m"+palabraNueva+"\u001B[0m"); //Operando (color)
                                            newLine2=newLine2.concat(palabraNueva); //Operando
                                            
                                            System.out.println("La linea resultante es: " +newLine);
                                        }else if(!palabraNueva.startsWith("$")){
                                            //Convertir a hexadecimal
                                            System.out.println(palabraNueva+" Es un operando numérico (decimal)");
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            System.out.println(op+" Es el operando en hexadecimal");
                                           
                                            //newLine=newLine.concat(palabra+" "); //Palabra BRCLR
                                            newLine=newLine.concat("\033[43;31m"+(ExcepDirecto.get(palabra))); //Opcode directo (color)
                                            newLine2=newLine2.concat(ExcepDirecto.get(palabra)); //Opcode directo
                                            
                                            newLine=newLine.concat("\u001B[41;33m"+op+"\u001B[0m"); //Operando (color)
                                            newLine2=newLine2.concat(op); //Operando
                                            
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                    }
                            }
                            if((numTotalPalabra==5)){
                                
                                palabraXY = st.nextToken();//Tiene la tercer palara
                                System.out.println("La tercer palabra es: " +palabraXY);
                                palabraXY=palabraXY.toUpperCase();
                                
                                    if(palabraXY.equals("X")){
                                        System.out.println("es indexado con respecto a X");
                                        
                                        /* Como el opcode que le corresponde es el del modo INDEXADO con respecto a X,
                                        al conteo de memoria le debemos sumar 4 (número de bytes que ocupa el opcode con el operando)
                                        */
                                        metodosDeLectura.numMemoria = metodosDeLectura.numMemoria + 4;
                                        
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            palabraNueva = palabraNueva.substring(1); //Quitamos el espacio
                                            
                                            newLine=newLine.concat("\033[43;31m"+ExcepIndexadoX.get(palabra)); //Opcode indexado de X (color)
                                            newLine2=newLine2.concat(ExcepIndexadoX.get(palabra)); //Opcode indexado de X 
                                            
                                            newLine=newLine.concat("\u001B[41;33m"+palabraNueva+"\u001B[0m"); //Operando (color)
                                            newLine2=newLine2.concat(palabraNueva); //Operando 
                                            
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                        else if(!palabraNueva.startsWith("$")){
                                            
                                            System.out.println(palabraNueva+" Es un operando numérico (decimal)");
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            System.out.println(op+" Es el operando en hexadecimal");
                                            
                                            newLine=newLine.concat("\033[43;31m"+(ExcepIndexadoX.get(palabra))); //Opcode indexado de X (color)
                                            newLine2=newLine2.concat(ExcepIndexadoX.get(palabra)); //Opcode indexado de X
                                            
                                            newLine=newLine.concat("\u001B[41;33m"+op+"\u001B[0m"); //Operando (color)
                                            newLine2=newLine2.concat(op); //Operando
                                            
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                    }else if(palabraXY.equals("Y")){
                                        System.out.println("es indexado con respecto a Y");
                                        
                                        /* Como el opcode que le corresponde es el del modo INDEXADO con respecto a Y,
                                        al conteo de memoria le debemos sumar 5 (número de bytes que ocupa el opcode con el operando)
                                        */
                                        metodosDeLectura.numMemoria = metodosDeLectura.numMemoria + 5;
                                        
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            palabraNueva = palabraNueva.substring(1); //Quitamos el espacio
                                            
                                            newLine=newLine.concat("\033[43;31m"+ExcepIndexadoY.get(palabra)); //Opcode indexado de X (color)
                                            newLine2=newLine2.concat(ExcepIndexadoY.get(palabra)); //Opcode indexado de X
                                            
                                            newLine=newLine.concat("\u001B[41;33m"+palabraNueva+"\u001B[0m"); //Operando (color)
                                            newLine2=newLine2.concat(palabraNueva); //Operando
                                            
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                        else if(!palabraNueva.startsWith("$")){
                                            System.out.println(palabraNueva+" Es un operando numérico (decimal)");
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            System.out.println(op+" Es el operando en hexadecimal");
                                            
                                            newLine=newLine.concat("\033[43;31m"+(ExcepIndexadoY.get(palabra))); //Opcode indexado de X (color)
                                            newLine2=newLine2.concat(ExcepIndexadoY.get(palabra)); //Opcode indexado de X
                                            
                                            newLine=newLine.concat("\u001B[41;33m"+op+"\u001B[0m"); //Operando (color)
                                            newLine2=newLine2.concat(op); //Operando
                                            
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                    }else{
                                            //Creo que este error es una de sintaxis, porque entra a este caso cuando se entra al caso de indexado pero no tiene X o Y 
                                            mensaje = "\u001B[31m Error: Error de sintaxis. \u001B[0m\n";
                                            //Guardamos la salida de la primer pasada
                                            Output outPut = new Output(mensaje);
                                            metodosDeLectura.salidas.add(outPut);
                                            return line + "\n\t\t\t^Error: Error de sintaxis.";
                                        }
                            }
                        }else{
                            mensaje = "\u001B[31m Error: BRCLR debe tener 3 operandos. \u001B[0m\n";
                            //Guardamos la salida de la primer pasada
                            Output outPut = new Output(mensaje);
                            metodosDeLectura.salidas.add(outPut);
                            return line + "\n\t\t\t^Error: BRCLR debe tener 3 operandos.";
                            
                        }
                    break;
                    
                    case 4:
                        if(numTotalPalabra>=4 && numTotalPalabra<=5){
                            palabraNueva = palabra;
                            palabraNueva= st.nextToken();
                            System.out.println("La segunda palabra es: " +palabraNueva);
                            
                            if(numTotalPalabra==4){//Es directo pues si tiviera X o Y tendria 5 palabras
                                
                                /* Como el opcode que le corresponde es el del modo DIRECTO,
                                al conteo de memoria le debemos sumar 4 (número de bytes que ocupa el opcode con el operando)
                                */
                                metodosDeLectura.numMemoria = metodosDeLectura.numMemoria + 4;
                                
                                    if(palabraNueva.contains("#")){
                                        System.out.println("\u001B[31m Error : Las excepciones SOLO estan catalogadas en indexado o directo. \u001B[0m\n");
                                        //Guardamos la salida de la primer pasada
                                        Output outPut = new Output(mensaje);
                                        metodosDeLectura.salidas.add(outPut);
                                        return line + "\n\t\t\t^Error : Las excepciones SOLO estan catalogadas en indexado o directo.";
                                        
                                    }else{
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            palabraNueva = palabraNueva.substring(1); //Quitamos el espacio
                                            
                                            newLine=newLine.concat("\033[43;31m"+ExcepDirecto.get(palabra)); //Opcode directo (color)
                                            newLine2=newLine2.concat(ExcepDirecto.get(palabra)); //Opcode directo 
                                            
                                            newLine=newLine.concat("\u001B[41;33m"+palabraNueva+"\u001B[0m"); //Operando
                                            newLine2=newLine2.concat(palabraNueva); //Operando
                                            
                                            System.out.println("La linea resultante es: " +newLine);
                                        }else if(!palabraNueva.startsWith("$")){
                                            //Convierte a hexadecimal
                                            System.out.println(palabraNueva+" Es un operando numérico (decimal)");
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            System.out.println(op+" Es el operando en hexadecimal");
                                            
                                            newLine=newLine.concat("\033[43;31m"+(ExcepDirecto.get(palabra))); //Opcode directo (color)
                                            newLine2=newLine2.concat(ExcepDirecto.get(palabra)); //Opcode directo
                                            
                                            newLine=newLine.concat("\u001B[41;33m"+op+"\u001B[0m"); //Operando (color)
                                            newLine2=newLine2.concat(op); //Operando
                                            
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                    }
                            }
                            if(numTotalPalabra==5){
                                
                                palabraXY = st.nextToken();//Tiene la tercer palara
                                System.out.println("La tercer palabra es: " +palabraXY);
                                palabraXY=palabraXY.toUpperCase();
                                
                                    if(palabraXY.equals("X")){
                                        System.out.println("es indexado con respecto a X");
                                        
                                        /* Como el opcode que le corresponde es el del modo INDEXADO con respecto a X,
                                        al conteo de memoria le debemos sumar 4 (número de bytes que ocupa el opcode con el operando)
                                        */
                                        metodosDeLectura.numMemoria = metodosDeLectura.numMemoria + 4;
                                        
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            palabraNueva = palabraNueva.substring(1); //Quitamos el espacio
                                            
                                            newLine=newLine.concat("\033[43;31m"+ExcepIndexadoX.get(palabra)); //Opcode indexado de X
                                            newLine2=newLine2.concat(ExcepIndexadoX.get(palabra)); //Opcode indexado de X
                                            
                                            newLine=newLine.concat("\u001B[41;33m"+palabraNueva+"\u001B[0m"); //Operando
                                            newLine2=newLine2.concat(palabraNueva); //Operando
                                            
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                        else if(!palabraNueva.startsWith("$")){
                                            //Convierte a hexadecimal
                                            System.out.println(palabraNueva+" Es un operando numérico (decimal)");
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            System.out.println(op+" Es el operando en hexadecimal");
                                            
                                            newLine=newLine.concat("\033[43;31m"+(ExcepIndexadoX.get(palabra))); //Opcode indexado de X (color)
                                            newLine2=newLine2.concat(ExcepIndexadoX.get(palabra)); //Opcode indexado de X
                                            
                                            newLine=newLine.concat("\u001B[41;33m"+op+"\u001B[0m"); //Operando (color)
                                            newLine2=newLine2.concat(op); //Operando
                                            
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                    }else if(palabraXY.equals("Y")){
                                        System.out.println("es indexado con respecto a Y");
                                        
                                        /* Como el opcode que le corresponde es el del modo INDEXADO con respecto a Y,
                                        al conteo de memoria le debemos sumar 5 (número de bytes que ocupa el opcode con el operando)
                                        */
                                        metodosDeLectura.numMemoria = metodosDeLectura.numMemoria + 5;
                                        
                                        if(palabraNueva.startsWith("$")){
                                            //Se quita $
                                            palabraNueva = palabraNueva.replace('$', ' ');
                                            palabraNueva = palabraNueva.substring(1); //Quitamos el espacio
                                            
                                            newLine=newLine.concat("\033[43;31m"+ExcepIndexadoY.get(palabra)); //Opcode indexado de X (color)
                                            newLine2=newLine2.concat(ExcepIndexadoY.get(palabra)); //Opcode indexado de X
                                            
                                            newLine=newLine.concat("\u001B[41;33m"+palabraNueva+"\u001B[0m"); //Operando
                                            newLine2=newLine2.concat(palabraNueva); //Operando
                                            
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                        else if(!palabraNueva.startsWith("$")){
                                            //Convierte a hexadecimal
                                            System.out.println(palabraNueva+" Es un operando numérico (decimal)");
                                            int opN=Integer.parseInt(palabraNueva);
                                            op=Integer.toHexString(opN).toUpperCase();
                                            System.out.println(op+" Es el operando en hexadecimal");
                                            
                                            newLine=newLine.concat("\033[43;31m"+(ExcepIndexadoY.get(palabra))+" "); //Opcode indexado de X (color)
                                            newLine2=newLine2.concat(ExcepIndexadoY.get(palabra)); //Opcode indexado de X
                                            
                                            newLine=newLine.concat("\u001B[41;33m"+op+"\u001B[0m"); //Operando (color)
                                            newLine2=newLine2.concat(op); //Operando
                                            
                                            System.out.println("La linea resultante es: " +newLine);
                                        }
                                    }else{
                                        //Creo que este error es una de sintaxis, porque entra a este caso cuando se entra al caso de indexado pero no tiene X o Y 
                                        mensaje = "\u001B[31m Error: Error de sintaxis. \u001B[0m\n";
                                        //Guardamos la salida de la primer pasada
                                        Output outPut = new Output(mensaje);
                                        metodosDeLectura.salidas.add(outPut);
                                        return line + "\n\t\t\t^Error: Error de sintaxis.";
                                    }
                            }
                        }else{
                            mensaje = "\u001B[31m Error: BRSET debe tener 3 operandos.\u001B[0m\n";
                            //Guardamos la salida de la primer pasada
                            Output outPut = new Output(mensaje);
                            metodosDeLectura.salidas.add(outPut);
                            return line + "\n\t\t\t^Error: BRET debe tener 3 operandos.";
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
                            
                            mensaje = "\u001B[31m Error: El segundo operando siempre debe ser inmediato. \u001B[0m\n";
                            //Guardamos la salida de la primer pasada
                            Output outPut = new Output(mensaje);
                            metodosDeLectura.salidas.add(outPut);
                            return line + "\n\t\t\t^Error: El segundo operando siempre debe ser inmediato";
                            
                        }else if((palabra.startsWith("#$"))){
                            
                            palabra = palabra.replace('$', ' ');
                            palabra = palabra.replace('#', ' ');
                            palabra=palabra.substring(2); //Quitamos los dos espacios
                            //Comprobamos que el tamaño del operando sea un número par de bytes
                            if((palabra.length())%2!=0||(palabra.length())>2){
                                mensaje = "\u001B[31m Error 007: MAGNITUD DE  OPERANDO ERRONEA. \u001B[0m\n";
                                //Guardamos la salida de la primer pasada
                                Output outPut = new Output(mensaje);
                                metodosDeLectura.salidas.add(outPut);
                                return line + "\n\t\t\t^Error 007: MAGNITUD DE  OPERANDO ERRONEA.";
                                
                            }else
                            //Hay que incrementar en conteo de bytes de memoria según el tamaño del segundo operando
                            numMemoria = numMemoria + (palabra.length())/2;
                        }
                                                
                        newLine=newLine.concat("\u001B[41;33m"+palabra+"\u001B[0m"+" ");
                        newLine2=newLine2.concat(palabra+" ");
                        
                        System.out.println("La linea resultante es: " +newLine);
                    }else if(!palabra.contains("$")){
                        if(palabra.startsWith("#")){
                            palabra=palabra.substring(1);
                            //Convertirla a hexadecimal
                            System.out.println(palabra+" Es un operando numérico (decimal)");
                            int opN=Integer.parseInt(palabra);
                            op=Integer.toHexString(opN).toUpperCase();
                            //Comprobamos que el tamaño del operando sea un número par de bytes
                            if((op.length())%2!=0||(palabra.length())>2){
                                mensaje = "\u001B[31m Error 007: MAGNITUD DE  OPERANDO ERRONEA. \u001B[0m\n";
                                //Guardamos la salida de la primer pasada
                                Output outPut = new Output(mensaje);
                                metodosDeLectura.salidas.add(outPut);
                                return line + "\n\t\t\t^Error 007: MAGNITUD DE  OPERANDO ERRONEA.";
                            }else
                            //Hay que incrementar en conteo de bytes de memoria según el tamaño del segundo operando
                            numMemoria = numMemoria + (palabra.length())/2;
                            
                            System.out.println(op+"Es el operando en hexadecimal");
                            instruccion=instruccion.concat(op);
                            
                            newLine=newLine.concat("\u001B[41;33m"+op+"\u001B[0m\n");
                            newLine2=newLine2.concat(op);
                            
                            System.out.println("La linea resultante es: " +newLine);
                        }else{
                            mensaje = "\u001B[31m Error: El segundo operando siempre debe ser inmediato. \u001B[0m\n";
                            //Guardamos la salida de la primer pasada
                            Output outPut = new Output(mensaje);
                            metodosDeLectura.salidas.add(outPut);
                            
                            return line + "\n\t\t\t^Error: El segundo operando siempre debe ser inmediato"; 
                        }
                    }
                }
            }
            //System.out.println("******D es igual a :" + d);
            
            if(numPalabra==3&&(d==3 || d==4)){
                //System.out.println("------Hay salto y es de BRSET O BRCLR");
                /*
                Hay que incrementar en 1 el contador de bytes de memoria para dejar el espacios del salto
                que debe ser de 1 byte.
                */
                numMemoria = numMemoria + 1;
                
                mensaje = newLine + palabra; 
                /*Guardamos la salida de la primer pasada(que contiene el opcode y el valor en hexadecimal
                de los dos primeros operandos), y la etiqueta que hay que buscar en la segunda pasada.
                Además creamos el Output indicando que hay un salto*/
                Output outPut = new Output(mensaje+"\n",true,numLinea,true);
                metodosDeLectura.salidas.add(outPut);
                return newLine2 + palabra + line;
            }
            
        }
        //Guardamos la salida de la primer pasada
        Output outPut = new Output(newLine+"\n");
        metodosDeLectura.salidas.add(outPut);
        return newLine2 + line;
    }
    if (pasada == 2){
        //Nos ayuda a identificar si hay un error en la etiqueta
        
        StringTokenizer st = new StringTokenizer (line);
        String etiqueta = ""; //Nos sorve para guardar la etiqueta que vamos a verificar
        String inicio = ""; //Guardamos las pirmeras 4 palabras de la línea
        String Opcode = ""; //En esta variable guardamos el Opcode y los operandos que se obtuvieron en la primer pasada
        int posMem = 0;
        //Recuperamos la etiqueta que se guardó en la primer pasada
        for(int i=0; i<5;i++){
           palabra = st.nextToken();
           if (i<2){
               inicio = inicio + palabra + " "; 
           }else if (i==2){
                inicio = inicio + palabra + "\t";
                posMem = Integer.parseInt(palabra,16);
                System.out.println("PosMem= "+posMem);
                System.out.println("Inicio : " + ini);
           }else if (i==3){
               
               Opcode = palabra; 
           }else if (i==4){
               etiqueta = palabra;
           }
        }
        newLine = verificarEtiqueta(etiqueta, VCE, posMem);
        
        line = "";
        //Guardamos el resto de la línea que corresponde a la línea original
        while(st.hasMoreTokens()){
           line = line + st.nextToken() + " ";
        }
        System.out.println("Inicio: "+ inicio);
        System.out.println("Linea originial: " + line);
        System.out.println("Error/salto: " + newLine);
        System.out.println("---Hay error: "+ error);
        
        if(error){
            newLine = inicio + line + newLine;
        }else{
            System.out.print("\033[43;31m"+Opcode+"\u001B[41;33m"+newLine+"\u001B[0m"+"\t\t\t"+line+"\n");
            newLine = inicio + Opcode + newLine + "\t\t\t" + line;
        }
    }
        
    //System.out.println("El número total de palabras leídas es "+numPalabra);
    //newLine=newLine.concat("\t\t\t"+line);
    //System.out.println("La cadena generada es: \n\n"+newLine+"\n");
    return newLine;
    }
    
    public String verificarEtiqueta(String palabra, Var_Cons_Etiq VCE, int numMem){
        
        //Pos no ayuda a guardar la posición de la etiqueta
        int pos = 0;
        int salto = 0;
        
        //Buscar si existe esa etiqueta                    
        pos = VCE.buscarEtiqueta(palabra);
        System.out.println("pos: " + pos);
        if (pos == 0){
            error = true;
            System.out.println("\u001B[31m Error 003: ETIQUETA INEXISTENTE \u001B[0m");
            return "\n\t\t\t^Error 003: ETIQUETA INEXISTENTE";
        }else{
            
            //Arreflo de caracteres que nos ayudará a calcular el operando
            char[] aBinario = new char[8];
            
            if (pos<numMem){
                //Caso de salto negativo
                System.out.println("El salto es negativo");
                salto = (numMem+4)-pos;
                System.out.println("El salto es: "+ salto);
                if (salto <= 127){
                    error = false;
                    String binario = Integer.toBinaryString(salto);
                    System.out.println("Binario antes: "+binario);
                   
                //Pasamos el String a un arreglo de caracteres, llenando los espacios vacíos con ceros
                    int tamaño = binario.length();
                    //Llenamos los espacios vacios con 0
                    for(int j = 0; j < (8 - tamaño); j++){
                        aBinario[j]=0;
                    }
                    //Guardamos el resto del número binario
                    int j=0;
                    for (int k = (8-tamaño); k<8; k++){
                        aBinario[k]=binario.charAt(j);
                        j++;
                    }
                    //Se cambian los 1s por 0s
                    for(int i=0; i<aBinario.length; i++){
                        if(aBinario[i]=='1'){
                            aBinario[i]='0';
                        }else{
                            aBinario[i]='1';
                        }
                    }
                    
                    //Convertimos el arreglo nuevamente a cadena
                    StringBuffer bin = new StringBuffer();
                    for (int i=0;i<aBinario.length;i++){
                        bin =bin.append(aBinario[i]);
                        System.out.println(aBinario[i]);
                    }
                    binario = bin.toString();
                    System.out.println(bin);
                    System.out.println("Num bin: "+ binario);
                    //Convertimos la cadena binaria a decimal
                    int decimal=Integer.parseInt(binario,2);
                    //Le sumamos 1
                    decimal = decimal +1;
                    //Convertimos el decimal a una cadena haxadecimal
                    String hexadecimal = Integer.toHexString(decimal);
                    hexadecimal = hexadecimal.toUpperCase();
                    if(hexadecimal.length()==1){
                        hexadecimal = "0" + hexadecimal;
                    }
                    return hexadecimal;
                              
                }else{
                    error = true;
                    System.out.println("\u001B[31m Error 008: SALTO RELATIVO MUY LEJANO \u001B[0m");
                    return "\n\t\t\t^Error 008: SALTO RELATIVO MUY LEJANO";
                }
            }else{
                //Caso de salto positivo
                System.out.println("El salto es positivo");
                salto = pos - (numMem+4);
                if (salto <= 128){
                    error = false;
                    //Calcular el valor del operando
                    String hexadecimal = Integer.toHexString(salto);
                    hexadecimal = hexadecimal.toUpperCase();
                    if(hexadecimal.length()==1){
                        hexadecimal = "0" + hexadecimal;
                    }
                    return hexadecimal;
                }else{
                    error = true;
                    System.out.println("\u001B[31m Error 008: SALTO RELATIVO MUY LEJANO \u001B[0m");
                    return "\n\t\t\t^Error 008: SALTO RELATIVO MUY LEJANO";
                }
            }
        }
    } 
}