import java.util.List;

public class ASAI implements Parser{
  Pila miPila = new Pila();
    Pila2 Pila_Acarreo = new Pila2();
    private boolean hayErrores = false;
    private String LINEA; //Aqui se guarda entrada
    private String Memoria="";

    public ASAI(List<Token> tokens,String linea){
        this.LINEA=linea;
    }

    @Override
    public boolean parse() {
        String primeraPalabra = ObtenerPrimerPalabra.obtener(LINEA);
        E0(primeraPalabra);
        //System.out.println(primeraPalabra+ "\n");
        //preanalisis = this.tokens.get(i);
        // = this.tokens.primeraPalabra;
        if(!hayErrores){
            System.out.println("Consulta correcta");
            return  true;
        }else {
            System.out.println("Se encontraron errores");
        }
        return false;
    }
  
    // Estado E0
    private void E0(String palabra){
        //Consumimos un elemento de la pila,
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) para verlo, guardarlo y lo volvemos a meter
        //miPila.operacion(1, Y);//volvemos a meter
        //System.out.println(palabra+ "and"+ Y+ ".");
        //System.out.println(String.valueOf(TipoToken.SELECT)+ ".");
        
        //Q x 0, nos da: E0 -> E1
        if((Memoria.equals("Q"))&&(Y.equals("0"))){
            Memoria="";
            miPila.operacion(1, "1");
            quien_sigue("1");
        }
        //SELECT x 0, nos da: E0 -> S2
        else if((palabra.equals(String.valueOf(TipoToken.SELECT)))&&(Y.equals("0"))){
            //System.out.println("check");
            // Se hace shift...
            SHIFT("2",palabra);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // Estado E1
    private void E1(String palabra){
        //Consumimos un elemento de la pila,
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "Dand"+ Y+ ".");
        //EOF x 1, nos da: 1-> ACCEPT
        if((palabra.equals(String.valueOf(TipoToken.EOF)))&&(Y.equals("1"))){
            // y agregamos su produccion corresponciente.
            //En este caso solo ACEPTA y regresamos
            return;
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
   // Estado E2
    private void E2(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "Pand"+ Y+ ".");
        //D x 2, nos da: E2 -> E3
        if((Memoria.equals("D"))&&(Y.equals("2"))){
            Memoria="";
            miPila.operacion(1, "3");
            quien_sigue("3");
        }
        //P x 2, nos da: E2 -> E5
        else if((Memoria.equals("P"))&&(Y.equals("2"))){
            Memoria="";
            miPila.operacion(1, "5");
            quien_sigue("5");
        }
        //A x 2, nos da: E2 -> E7
        else if((Memoria.equals("A"))&&(Y.equals("2"))){
            Memoria="";
            miPila.operacion(1, "7");
            quien_sigue("7");
        }
        //A1 x 2, nos da: E2 -> E8
        else if((Memoria.equals("A1"))&&(Y.equals("2"))){
            Memoria="";
            miPila.operacion(1, "8");
            quien_sigue("8");
        }
        //IDENTIFICADOR x 2, nos da: 2-> S9
        else if ((palabra.equals(String.valueOf(TipoToken.IDENTIFICADOR)))&&(Y.equals("2"))) {
            SHIFT("9",palabra);
        }
        //ASTERISCO x 2, nos da: 2-> S6
        else if ((palabra.equals(String.valueOf(TipoToken.ASTERISCO)))&&(Y.equals("2"))) {
            SHIFT("6",palabra);
        }
        else if ((palabra.equals(String.valueOf(TipoToken.DISTINCT)))&&(Y.equals("2"))) {
            SHIFT("4",palabra);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // Estado E3
    private void E3(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "Aand"+ Y+ ".");
        //FROM x 3, nos da: 3-> S10
        if ((palabra.equals(String.valueOf(TipoToken.FROM)))&&(Y.equals("3"))) {
            SHIFT("10",palabra);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // Estado E4
    private void E4(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "A1and"+ Y+ ".");
        //P x 4, nos da: E4 -> E11
        if((Memoria.equals("P"))&&(Y.equals("4"))){
            Memoria="";
            miPila.operacion(1, "11");
            quien_sigue("11");
        }
        //A x 4, nos da: E4 -> E7
        else if((Memoria.equals("A"))&&(Y.equals("4"))){
            Memoria="";
            miPila.operacion(1, "7");
            quien_sigue("7");
        }
        //A1 x 4, nos da: E4 -> E8
        else if((Memoria.equals("A1"))&&(Y.equals("4"))){
            Memoria="";
            miPila.operacion(1, "8");
            quien_sigue("8");
        }
        //IDENTIFICADOR x 4, nos da: 4-> S9
        else if ((palabra.equals(String.valueOf(TipoToken.IDENTIFICADOR)))&&(Y.equals("4"))) {
            SHIFT("9",palabra);
        }
        //ASTERISCO x 4, nos da: 4-> S6
        else if ((palabra.equals(String.valueOf(TipoToken.ASTERISCO)))&&(Y.equals("4"))) {
            SHIFT("6",palabra);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // Estado E5
    private void E5(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "A2and"+ Y+ ".");
        //FROM x 5, nos da: 5-> R3
        if ((palabra.equals(String.valueOf(TipoToken.FROM)))&&(Y.equals("5"))) {
            reduce(3);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
     // Estado E6
    private void E6(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "A3and"+ Y+ ".");
        //FROM x 6, nos da: 6-> R4
        if ((palabra.equals(String.valueOf(TipoToken.FROM)))&&(Y.equals("6"))) {
            reduce(4);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // Estado E7
    private void E7(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "A3and"+ Y+ ".");
        //FROM x 7, nos da: 7-> R5
        if ((palabra.equals(String.valueOf(TipoToken.FROM)))&&(Y.equals("7"))) {
            reduce(5);
        }
        //COMA x 7, nos da: 7-> S12
        else if ((palabra.equals(String.valueOf(TipoToken.COMA)))&&(Y.equals("7"))){
            SHIFT("12",palabra);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
}

