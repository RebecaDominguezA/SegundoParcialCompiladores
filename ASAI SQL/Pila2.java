import java.util.Stack;

public class Pila2 {
    private Stack<String> pila2;

    public Pila2() {
        //Se inicializa la pila
        pila2 = new Stack<>();

    }   

    public String operacion(int accion, String Nuevo_elemento) {
        switch (accion) {
            case 0:
                pop();
                return "";
            case 1:
                push(Nuevo_elemento);
                return "";
            case 2:
                // Realizar una acción personalizada
                String elementoExtraido = pila2.pop();
                //System.out.println("Acción personalizada realizada");
                return elementoExtraido;
            default:
                System.out.println("Acción no reconocida");
                return "";
        }
    }

    private void pop() {
        if (!pila2.isEmpty()) {
            pila2.pop();
            //String elemento = pila2.pop();
            //System.out.println("Pop realizado. Valor extraído: " + elemento);
        } else {
            System.out.println("La pila2 está vacía. No se puede hacer pop.");
        }
    }

    private void push(String Nuevo_elemento) {
        pila2.push(Nuevo_elemento);
        //System.out.println("Push realizado. Nuevo valor agregado: " + Nuevo_elemento);
    }

}
