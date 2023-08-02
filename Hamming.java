import java.util.*;
import java.util.ArrayList;

class Hamming {

    public static String convertirString(List<List<Object>> listaDeListas) {
        StringBuilder builder = new StringBuilder();

        for (List<Object> lista : listaDeListas) {
            for (Object valor : lista) {
                builder.append(valor.toString());
            }
            builder.append(" ");
        }

        return builder.toString().trim();
    }

    public static void main(String args[]) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("\nIngrese mensaje: ");

        String Data = myObj.nextLine();
        System.out.println("-> La data ingresada es: " + Data);

        List<List<Object>> temp = new ArrayList<>();

        if (Data.length() % 4 != 0) {
            int elementosFaltantes = 4 - (Data.length() % 4);
            System.out.println("La cadena no es divisible por 4." + elementosFaltantes);
            StringBuilder builder = new StringBuilder(Data);
            for (int l = 0; l < elementosFaltantes; l++) {
                builder.append("0");
        }
        
        Data = builder.toString();
        System.out.println(Data);

        } 

        int segmentLength = 4;
        for (int j = 0; j < Data.length(); j += segmentLength) {
            int endIndex = Math.min(j + segmentLength, Data.length());
            String segment = Data.substring(j, endIndex);
            int i = segment.length();

            Emisor emisor = new Emisor();
    
            int p = emisor.function(i);
            List p_num = emisor.position(i,p);
            List new_data = emisor.Message_data(p_num,i,p);
            List table = emisor.true_table(p_num,i,p);
            List message = emisor.message(segment,new_data);
            List one_index = emisor.position_ones(table,new_data);
            List finish_message = emisor.paridad(one_index,new_data);
            temp.add(finish_message);
        }

        Hamming main = new Hamming();
        String resultado = main.convertirString(temp);
        System.out.println("-> La data codificada es: " + resultado);

    }
}