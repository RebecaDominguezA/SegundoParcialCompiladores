import java.util.List;

public class ASDI implements Parser{
Pila miPila = new Pila();
    private boolean hayErrores = false;
    private String LINEA; //Aqui se guarda entrada

    public ASDI(List<Token> tokens,String linea){
        this.LINEA=linea;
    }

    @Override
    public boolean parse() {
        String primeraPalabra = ObtenerPrimerPalabra.obtener(LINEA);
        Q(primeraPalabra);
        //System.out.println(primeraPalabra+ "\n");
        //preanalisis = this.tokens.get(i);
        // = this.tokens.primeraPalabra;
        if(!hayErrores){
            System.out.println("Consulta correcta");
            return  true;
        }else {
            System.out.println("Se encontraron errores");
        }
        return false;
    }
    // Q
    private void Q(String palabra){
        //Consumimos un elemento de la pila,
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "and"+ Y+ ".");
        //System.out.println(String.valueOf(TipoToken.SELECT)+ ".");
        //SELECT x Q, nos da: Q -> select D from T
        if((palabra.equals(String.valueOf(TipoToken.SELECT)))&&(Y.equals("Q"))){
            //System.out.println("check");
            // y agregamos su produccion corresponciente.
            miPila.operacion(1,"T"); // Add un elemento (push)
            miPila.operacion(1,"FROM"); // Add un elemento (push)
            miPila.operacion(1,"D"); // Add un elemento (push)
            miPila.operacion(1,"SELECT"); // Add un elemento (push)
            
            //Sabemos que palabra de entrada es SELECT y lo ultimo de pila es SELECT, match
            match(palabra, "SELECT");
        }else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // D 
    private void D(String palabra){
        
        //Consumimos un elemento de la pila,
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        System.out.println(palabra+ "Dand"+ Y+ ".");
        //DISTINCT x D, nos da: D-> distinct P 
        if((palabra.equals(String.valueOf(TipoToken.DISTINCT)))&&(Y.equals("D"))){
            // y agregamos su produccion corresponciente.
            miPila.operacion(1,"P"); 
            miPila.operacion(1,"DISTINCT"); 
            match(palabra, "DISTINCT");
        }
        //ASTERISCO x D, nos da: D-> P
        else if ((palabra.equals(String.valueOf(TipoToken.ASTERISCO)))&&(Y.equals("D"))) {
            miPila.operacion(1,"P");
            P(palabra);
        }
        //IDENTIFICADOR x D, nos da: D-> P
        else if ((palabra.equals(String.valueOf(TipoToken.IDENTIFICADOR)))&&(Y.equals("D"))) {
            miPila.operacion(1,"P");
            P(palabra);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }

    // P
    private void P(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        System.out.println(palabra+ "Pand"+ Y+ ".");
        //ASTERISCO x P, nos da: P-> *
        if ((palabra.equals(String.valueOf(TipoToken.ASTERISCO)))&&(Y.equals("P"))) {
            miPila.operacion(1,"ASTERISCO");
            //Sabemos que palabra de entrada es ASTERISCO y lo ultimo de pila es ASTERISCO, match
            match(palabra,"ASTERISCO");
        }
        //IDENTIFICADOR x P, nos da: P-> A
        else if ((palabra.equals(String.valueOf(TipoToken.IDENTIFICADOR)))&&(Y.equals("P"))) {
            miPila.operacion(1,"A");
            A(palabra);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // A
    private void A(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        System.out.println(palabra+ "Aand"+ Y+ ".");
        //IDENTIFICADOR x A, nos da: A-> A2 A1
        if ((palabra.equals(String.valueOf(TipoToken.IDENTIFICADOR)))&&(Y.equals("A"))) {
            miPila.operacion(1,"A1");
            miPila.operacion(1,"A2");
            A2(palabra);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }

    // A1
    private void A1(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        System.out.println(palabra+ "A2and"+ Y+ ".");
        //FROM x A1, nos da: A1-> E
        if ((palabra.equals(String.valueOf(TipoToken.FROM)))&&(Y.equals("A1"))) {
            
            // Sacamos el nuevo Top de pila, lo guardamos y volvemos
            //a meter para que no se pierda...
            Y=miPila.operacion(2,""); //SACAMOS y actualizamos
            miPila.operacion(1,Y); //metemos 

            //AQUI mandamos a match porque no sabemos que hay debajo de pila actual, y a donde
            //nos llevara... match lo resolvera.
            match(palabra,Y);
        }
        //COMA x A1, nos da: P-> , A
        else if ((palabra.equals(String.valueOf(TipoToken.COMA)))&&(Y.equals("A1"))) {
            miPila.operacion(1,"A");
            miPila.operacion(1,"COMA");
            //Sabemos que palabra de entrada es COMA y lo ultimo de pila es COMA, match
            match(palabra,"COMA");
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // A2
    private void A2(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        System.out.println(palabra+ "A1and"+ Y+ ".");
        //IDENTIFICADOR x A2, nos da: A2-> id A3
        if ((palabra.equals(String.valueOf(TipoToken.IDENTIFICADOR)))&&(Y.equals("A2"))) {
            miPila.operacion(1,"A3");
            miPila.operacion(1,"IDENTIFICADOR");
            //Sabemos que palabra de entrada es IDENTIFICADOR y lo ultimo de pila es IDENTIFICADOR, match
            match(palabra,"IDENTIFICADOR");
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
}
