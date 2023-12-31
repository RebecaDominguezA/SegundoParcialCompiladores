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
      // Estado E8
    private void E8(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "A3and"+ Y+ ".");
        //FROM x 8, nos da: 8-> R7
        if ((palabra.equals(String.valueOf(TipoToken.FROM)))&&(Y.equals("8"))) {
            reduce(7);
        }
        //COMA x 8, nos da: 8-> R7
        else if ((palabra.equals(String.valueOf(TipoToken.COMA)))&&(Y.equals("8"))){
            reduce(7);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    //Estado E9
    private void E9(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "Tand"+ Y+ ".");
        if((Memoria.equals("A2"))&&(Y.equals("9"))){
            Memoria="";
            miPila.operacion(1, "13");
            quien_sigue("13");
        }
        //COMA x 9, nos da: 9-> R10
        else if ((palabra.equals(String.valueOf(TipoToken.COMA)))&&(Y.equals("9"))){
            reduce(10);
        }
        //PUNTO x 9, nos da: 9-> S14
        else if ((palabra.equals(String.valueOf(TipoToken.PUNTO)))&&(Y.equals("9"))){
            SHIFT("14",palabra);
        }
        //FROM x 9, nos da: 9-> R10
        else if ((palabra.equals(String.valueOf(TipoToken.FROM)))&&(Y.equals("9"))){
            reduce(10);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    //Estado E10
    private void E10(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "Tand"+ Y+ ".");
        //T x 10, nos da: E10-> E15
        if((Memoria.equals("T"))&&(Y.equals("10"))){
            Memoria="";
            miPila.operacion(1, "15");
            quien_sigue("15");
        }
        //T1 x 10, nos da: E10-> E16
        else if((Memoria.equals("T1"))&&(Y.equals("10"))){
            Memoria="";
            miPila.operacion(1, "16");
            quien_sigue("16");
        }
        //IDENTIFICADOR x 10, nos da: 10-> S17
        else if ((palabra.equals(String.valueOf(TipoToken.IDENTIFICADOR)))&&(Y.equals("10"))){
            SHIFT("17",palabra);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // Estado E11
    private void E11(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "A2and"+ Y+ ".");
        //FROM x 11, nos da: 11-> R2
        if ((palabra.equals(String.valueOf(TipoToken.FROM)))&&(Y.equals("11"))) {
            reduce(2);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
      //Estado E12
    private void E12(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "A2and"+ Y+ ".");
        //IDENTIFICADOR x 12, nos da: 12-> S9
        if((Memoria.equals("A1"))&&(Y.equals("12"))){
            Memoria="";
            miPila.operacion(1, "18");
            quien_sigue("18");
        }
        else if ((palabra.equals(String.valueOf(TipoToken.IDENTIFICADOR)))&&(Y.equals("12"))) {
            SHIFT("9",palabra);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // Estado E13
    private void E13(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "A2and"+ Y+ ".");
        //COMA x 13, nos da: 13-> R8
        if ((palabra.equals(String.valueOf(TipoToken.COMA)))&&(Y.equals("13"))) {
            reduce(8);
        }
        //FROM x 13, nos da: 13-> R8
        else if ((palabra.equals(String.valueOf(TipoToken.FROM)))&&(Y.equals("13"))) {
            reduce(8);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
     // Estado E14
    private void E14(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "A2and"+ Y+ ".");
        //IDENTIFICADOR x 14, nos da: 14-> S19
        if ((palabra.equals(String.valueOf(TipoToken.IDENTIFICADOR)))&&(Y.equals("14"))) {
            SHIFT("19",palabra);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // Estado E15
    private void E15(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "A2and"+ Y+ ".");
        //COMA x 15, nos da: 15-> S20
        if ((palabra.equals(String.valueOf(TipoToken.COMA)))&&(Y.equals("15"))) {
            SHIFT("20",palabra);
        }
        else if ((palabra.equals(String.valueOf(TipoToken.EOF)))&&(Y.equals("15"))) {
            reduce(1);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // Estado E16
    private void E16(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "A2and"+ Y+ ".");
        //COMA x 16, nos da: 16-> R12
        if ((palabra.equals(String.valueOf(TipoToken.COMA)))&&(Y.equals("16"))){
            reduce(12);
        }
        //EOF x 16, nos da: 16-> R12
        else if ((palabra.equals(String.valueOf(TipoToken.EOF)))&&(Y.equals("16"))){
            reduce(12);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // Estado E17
    private void E17(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "A2and"+ Y+ ".");
        //T2 x 17, nos da: 17-> E21
        if((Memoria.equals("T2"))&&(Y.equals("17"))){
            Memoria="";
            miPila.operacion(1, "21");
            quien_sigue("21");
        }
        //IDENTIFICADOR x 17, nos da: 17-> S19
        else if ((palabra.equals(String.valueOf(TipoToken.IDENTIFICADOR)))&&(Y.equals("17"))){
            SHIFT("22",palabra);
        }
        //COMA x 17, nos da: 17-> R15
        else if ((palabra.equals(String.valueOf(TipoToken.COMA)))&&(Y.equals("17"))){
            reduce(15);
        }
        //EOF x 17, nos da: 17-> R15
        else if ((palabra.equals(String.valueOf(TipoToken.EOF)))&&(Y.equals("17"))){
            reduce(15);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
  // Estado E18
    private void E18(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "A2and"+ Y+ ".");
        //COMA x 18, nos da: 18-> R6
        if ((palabra.equals(String.valueOf(TipoToken.COMA)))&&(Y.equals("18"))){
            reduce(6);
        }
        //FROM x 18, nos da: 18-> R6
        else if ((palabra.equals(String.valueOf(TipoToken.FROM)))&&(Y.equals("18"))){
            reduce(6);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // Estado E19
    private void E19(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "A2and"+ Y+ ".");
        //COMA x 19, nos da: 19-> R9
        if ((palabra.equals(String.valueOf(TipoToken.COMA)))&&(Y.equals("19"))){
            reduce(9);
        }
        //FROM x 19, nos da: 19-> R9
        else if ((palabra.equals(String.valueOf(TipoToken.FROM)))&&(Y.equals("19"))){
            reduce(9);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
      // Estado E20
    private void E20(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "Tand"+ Y+ ".");
        //T1 x 20, nos da: E20-> E23
        if((Memoria.equals("T1"))&&(Y.equals("20"))){
            Memoria="";
            miPila.operacion(1, "23");
            quien_sigue("23");
        }
        //IDENTIFICADOR x 20, nos da: 20-> S17
        else if ((palabra.equals(String.valueOf(TipoToken.IDENTIFICADOR)))&&(Y.equals("10"))){
            SHIFT("17",palabra);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // Estado E21
    private void E21(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "A2and"+ Y+ ".");
        //COMA x 21, nos da: 21-> R13
        if ((palabra.equals(String.valueOf(TipoToken.COMA)))&&(Y.equals("21"))){
            reduce(13);
        }
        //EOF x 21, nos da: 21-> R13
        else if ((palabra.equals(String.valueOf(TipoToken.EOF)))&&(Y.equals("21"))){
            reduce(13);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
  // Estado E22
    private void E22(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "A2and"+ Y+ ".");
        //COMA x 22, nos da: 22-> R14
        if ((palabra.equals(String.valueOf(TipoToken.COMA)))&&(Y.equals("22"))){
            reduce(14);
        }
        //EOF x 22, nos da: 22-> R14
        else if ((palabra.equals(String.valueOf(TipoToken.EOF)))&&(Y.equals("22"))){
            reduce(14);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // Estado E23
    private void E23(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "A2and"+ Y+ ".");
        //COMA x 23, nos da: 23-> R11
        if ((palabra.equals(String.valueOf(TipoToken.COMA)))&&(Y.equals("23"))){
            reduce(11);
        }
        //EOF x 23, nos da: 23-> R11
        else if ((palabra.equals(String.valueOf(TipoToken.EOF)))&&(Y.equals("23"))){
            reduce(11);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    private void reduce(int metodo){
       String anterior="";
        if (metodo==1) {
            //      4   3  2   1
            //Q->select D from T
            String primero=Pila_Acarreo.operacion(2, "");
            String segundo=Pila_Acarreo.operacion(2, "");
            String tercero=Pila_Acarreo.operacion(2, "");
            String cuarto=Pila_Acarreo.operacion(2, "");
            if ((primero.equals("T"))&&(segundo.equals("FROM"))&&(tercero.equals("D"))&&(cuarto.equals("SELECT"))){
                miPila.operacion(0,""); //Eliminamos elemento de pila 1
                miPila.operacion(0,""); //Eliminamos elemento de pila 2
                miPila.operacion(0,""); //Eliminamos elemento de pila 3
                miPila.operacion(0,""); //Eliminamos elemento de pila 4
                Pila_Acarreo.operacion(1, "Q");//Aplicamos reduce, se convierte en Q
                this.Memoria="Q";
                anterior=miPila.operacion(2, "");//Sacamos
                quien_sigue(anterior);
            }
        }
        //Reduce 2
        else if(metodo==2) {
            //       2    1
            //D->DISTINCT P
            String primero=Pila_Acarreo.operacion(2, "");
            String segundo=Pila_Acarreo.operacion(2, "");
            if ((primero.equals("P"))&&(segundo.equals("DISTINCT"))){
                miPila.operacion(0,""); //Eliminamos elemento de pila 1
                miPila.operacion(0,""); //Eliminamos elemento de pila 2
                Pila_Acarreo.operacion(1, "D");//Aplicamos reduce, se convierte en D
                this.Memoria="D";
                anterior=miPila.operacion(2, "");//Sacamos ELEMENTO de pila existente, lo guardamos
                
                quien_sigue(anterior);
            }
        }
        //Reduce 3
        else if(metodo==3) {
            //    1
            //D->P
            String primero=Pila_Acarreo.operacion(2, "");
            if ((primero.equals("P"))){
                miPila.operacion(0,""); //Eliminamos elemento de pila 1
                Pila_Acarreo.operacion(1, "D");//Aplicamos reduce, se convierte en D
                this.Memoria="D";
                anterior=miPila.operacion(2, "");//Sacamos ELEMENTO de pila existente, lo guardamos
                
                quien_sigue(anterior);
            }
            
        }
        //Reduce 4
        else if(metodo==4) {
            //   1
            //P->*
            String primero=Pila_Acarreo.operacion(2, "");
            if ((primero.equals("ASTERISCO"))){
                miPila.operacion(0,""); //Eliminamos elemento de pila 1
                Pila_Acarreo.operacion(1, "P");//Aplicamos reduce, se convierte en P
                this.Memoria="P";
                anterior=miPila.operacion(2, "");//Sacamos ELEMENTO de pila existente, lo guardamos
                quien_sigue(anterior);
            }
        }
        //Reduce 5
        else if(metodo==5) {
            //   1
            //P->A
            String primero=Pila_Acarreo.operacion(2, "");
            if ((primero.equals("A"))){
                miPila.operacion(0,""); //Eliminamos elemento de pila 1
                Pila_Acarreo.operacion(1, "P");//Aplicamos reduce, se convierte en A
                this.Memoria="P";
                anterior=miPila.operacion(2, "");//Sacamos ELEMENTO de pila existente, lo guardamos
    
                quien_sigue(anterior);
            }
        }
        //Reduce 6
        else if(metodo==6) {
            //   3 2  1
            //A->A , A1
            String primero=Pila_Acarreo.operacion(2, "");
            String segundo=Pila_Acarreo.operacion(2, "");
            String tercero=Pila_Acarreo.operacion(2, "");
            if ((primero.equals("A1"))&&(segundo.equals("COMA"))&&(tercero.equals("A"))){
                miPila.operacion(0,""); //Eliminamos elemento de pila 1
                miPila.operacion(0,""); //Eliminamos elemento de pila 2
                miPila.operacion(0,""); //Eliminamos elemento de pila 3
                Pila_Acarreo.operacion(1, "A");//Aplicamos reduce, se convierte en A
                this.Memoria="A";
                anterior=miPila.operacion(2, "");//Sacamos
    
                quien_sigue(anterior);
            }
        }
        //Reduce 7
        else if(metodo==7) {
            //    1
            //A->A1
            String primero=Pila_Acarreo.operacion(2, "");
            if ((primero.equals("A1"))){
                miPila.operacion(0,""); //Eliminamos elemento de pila 1
                Pila_Acarreo.operacion(1, "A");//Aplicamos reduce, se convierte en A
                this.Memoria="A";
                anterior=miPila.operacion(2, "");//Sacamos ELEMENTO de pila existente, lo guardamos
    
                quien_sigue(anterior);
            }
        }
        //Reduce 8
        else if(metodo==8) {
            //    2  1
            //A1->id A2
            String primero=Pila_Acarreo.operacion(2, "");
            String segundo=Pila_Acarreo.operacion(2, "");
            if ((primero.equals("A2"))&&(segundo.equals("IDENTIFICADOR"))){
                miPila.operacion(0,""); //Eliminamos elemento de pila 1
                miPila.operacion(0,""); //Eliminamos elemento de pila 2
                Pila_Acarreo.operacion(1, "A1");//Aplicamos reduce, se convierte en A1
                this.Memoria="A1";
                anterior=miPila.operacion(2, "");//Sacamos ELEMENTO de pila existente, lo guardamos
    
                quien_sigue(anterior);
            }
        }
        //Reduce 9
        else if(metodo==9) {
            //    2  1
            //A2-> . id
            String primero=Pila_Acarreo.operacion(2, "");
            String segundo=Pila_Acarreo.operacion(2, "");
            if ((primero.equals("IDENTIFICADOR"))&&(segundo.equals("PUNTO"))){
                miPila.operacion(0,""); //Eliminamos elemento de pila 1
                miPila.operacion(0,""); //Eliminamos elemento de pila 2
                Pila_Acarreo.operacion(1, "A2");//Aplicamos reduce, se convierte en A2
                this.Memoria="A2";
                anterior=miPila.operacion(2, "");//Sacamos ELEMENTO de pila existente, lo guardamos
    
                quien_sigue(anterior);
            }
        }
        //Reduce 10
        else if(metodo==10) {
            //Al vacio le agregamos la prod.
            //A2->E
            Pila_Acarreo.operacion(1, "A2");//Aplicamos reduce, se convierte en A2
            this.Memoria="A2";
            anterior=miPila.operacion(2, "");//Sacamos ELEMENTO de pila existente, lo guardamos

            quien_sigue(anterior);
        }
        //Reduce 11
        else if(metodo==11) {
            //   3 2  1
            //T->T , T1
            String primero=Pila_Acarreo.operacion(2, "");
            String segundo=Pila_Acarreo.operacion(2, "");
            String tercero=Pila_Acarreo.operacion(2, "");
            if ((primero.equals("T1"))&&(segundo.equals("COMA"))&&(tercero.equals("T"))){
                miPila.operacion(0,""); //Eliminamos elemento de pila 1
                miPila.operacion(0,""); //Eliminamos elemento de pila 2
                miPila.operacion(0,""); //Eliminamos elemento de pila 3
                Pila_Acarreo.operacion(1, "T");//Aplicamos reduce, se convierte en T
                this.Memoria="T";
                anterior=miPila.operacion(2, "");//Sacamos
    
                quien_sigue(anterior);
            }
        }
        //Reduce 12
        else if(metodo==12) {
            //    1
            //T->T1
            String primero=Pila_Acarreo.operacion(2, "");
            if ((primero.equals("T1"))){
                miPila.operacion(0,""); //Eliminamos elemento de pila 1
                Pila_Acarreo.operacion(1, "T");//Aplicamos reduce, se convierte en T
                this.Memoria="T";
                anterior=miPila.operacion(2, "");//Sacamos ELEMENTO de pila existente, lo guardamos
    
                quien_sigue(anterior);
            }
        }
        //Reduce 13
        else if(metodo==13) {
            //    2  1 
            //T1->id T2
            String primero=Pila_Acarreo.operacion(2, "");
            String segundo=Pila_Acarreo.operacion(2, "");
            if ((primero.equals("T2"))&&(segundo.equals("IDENTIFICADOR"))){
                miPila.operacion(0,""); //Eliminamos elemento de pila 1
                miPila.operacion(0,""); //Eliminamos elemento de pila 2
                Pila_Acarreo.operacion(1, "T1");//Aplicamos reduce, se convierte en T1
                this.Memoria="T1";
                anterior=miPila.operacion(2, "");//Sacamos ELEMENTO de pila existente, lo guardamos
    
                quien_sigue(anterior);
            }
        }
        //Reduce 14
        else if(metodo==14) {
            //      1
            //T2-> id
            String primero=Pila_Acarreo.operacion(2, "");
            if ((primero.equals("IDENTIFICADOR"))){
                miPila.operacion(0,""); //Eliminamos elemento de pila 1
                Pila_Acarreo.operacion(1, "T2");//Aplicamos reduce, se convierte en T2
                this.Memoria="T2";
                anterior=miPila.operacion(2, "");//Sacamos ELEMENTO de pila existente, lo guardamos
    
                quien_sigue(anterior);
            }
        }
        //Reduce 15
        else if(metodo==15) {
            //Al vacio le agregamos la prod.
            //T2->E
            Pila_Acarreo.operacion(1, "T2");//Aplicamos reduce, se convierte en T2
            this.Memoria="T2";
            anterior=miPila.operacion(2, "");//Sacamos ELEMENTO de pila existente, lo guardamos

            quien_sigue(anterior);
        }
      
    }
    private void SHIFT(String estado,String palabra){
        //this.Acarreo=Acarreo+(ObtenerPrimerPalabra.obtener(LINEA))+" ";//Se agrega acarreo
        Pila_Acarreo.operacion(1,palabra);
        this.LINEA=QuitarPrimerPalabra.quitar(LINEA);//Quitamos primer palabra de ENTRADA actual
        miPila.operacion(1,estado);//Agregamos elemento a la pila
        quien_sigue(estado);
    }
    private void quien_sigue(String estado){
        if (estado.equals("0")){
            E0((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("1")){
            E1((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("2")){
            E2((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("3")){
            E3((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("4")){
            E4((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("5")){
            E5((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("6")){
            E6((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("7")){
            E7((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("8")){
            E8((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("9")){
            E9((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("10")){
            E10((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("11")){
            E11((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("12")){
            E12((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("13")){
            E13((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("14")){
            E14((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("15")){
            E15((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("16")){
            E16((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("17")){
            E17((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("18")){
            E18((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("19")){
            E19((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("20")){
            E20((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("21")){
            E21((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("22")){
            E22((ObtenerPrimerPalabra.obtener(LINEA)));
        }
        else if(estado.equals("23")){
            E23((ObtenerPrimerPalabra.obtener(LINEA)));
        }
    }
}

