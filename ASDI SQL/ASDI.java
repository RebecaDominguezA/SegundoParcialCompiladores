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
}
