import java.util.*;
import java.util.ArrayList;

class Hamming {

    public static void main(String args[]) {
        Emisor emisor = new Emisor();
        Receptor receptor = new Receptor();
        
        Scanner myObj = new Scanner(System.in);
        System.out.println("\nIngrese mensaje: ");

        String Data = myObj.nextLine();

        List<List<Object>> temp = new ArrayList<>();
        List<Integer> missing =  new ArrayList<>();
        String resultado = "";
        String resultado_mod = "";
        List<List<Integer>> one_index = new ArrayList<>();
  

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

            
    
            int  p = emisor.function(i);
            List p_num = emisor.position(i,p);
            List new_data = emisor.Message_data(p_num,i,p);
            List message = emisor.message(segment,new_data);
                 missing = emisor.missingIndex(new_data);
            List table = emisor.true_table(p_num,i,p);
                 one_index = emisor.position_ones(table,new_data);
            List finish_message = emisor.paridad(one_index,new_data);
            temp.add(finish_message);
           
            
        }

        resultado = emisor.convertirString(temp);
        System.out.println("-> La data ingresada es: " + Data);
        System.out.println("-> La data codificada es: " + resultado);
        System.out.println(missing);
        resultado_mod = "1001100";

        receptor.paridado(one_index,missing,resultado_mod);

    }
}