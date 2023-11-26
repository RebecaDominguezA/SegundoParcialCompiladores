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
  
}

