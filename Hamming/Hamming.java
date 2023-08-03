import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class Hamming {

    public static void main(String args[]) {
        Emisor emisor = new Emisor();
        
        String resultado = "";
        String resultado_mod = "";
        List<List<Object>> temp = new ArrayList<>();
        List<Integer> missing =  new ArrayList<>();
        List<List<Object>> paridad_wished = new ArrayList<>();
        List<List<Object>> paridad_calculated = new ArrayList<>();
        List<List<Integer>> one_index = new ArrayList<>();
        
        Scanner myObj = new Scanner(System.in);
        System.out.println("\nIngrese mensaje: ");
        String Data = myObj.nextLine();

        if (Data.length() % 4 != 0) {
            int elementosFaltantes = 4 - (Data.length() % 4);
            StringBuilder builder = new StringBuilder(Data);
            for (int l = 0; l < elementosFaltantes; l++) {
                builder.append("0");
        }
        
        Data = builder.toString();
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
            List table = emisor.true_table(p_num,i,p);
                 one_index = emisor.position_ones(table,new_data);
            List finish_message = emisor.paridad(one_index,new_data);
            List paridad_MC = emisor.getParList();
            temp.add(finish_message);
            paridad_wished.add(paridad_MC);
           
            
        }
        resultado = emisor.convertirString(temp);
        System.out.println("-> La data ingresada es: " + Data);
        System.out.println("-> La data codificada es: " + resultado);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("HammingEmisor.txt"))) {
            // Escribir las variables en el archivo
            writer.write("Mensaje_emisor: " + resultado);
            writer.newLine();  // Agregar una nueva línea
            writer.write("Indices: " + one_index);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}