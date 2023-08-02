import java.util.*;
import java.util.ArrayList;

class Emisor {
    public int function(int length_data) {
        List<Integer> possible = new ArrayList<>();
        boolean verify = true;
        int num = length_data + 1;

        for (int i = 0; i <= 100; i++) {
            int pow = (int) Math.pow(2, i);
            int operation = i + num;

            if (pow >= operation) {
                possible.add(i);
            }
        }

        if (!possible.isEmpty()) {
            int p = Collections.min(possible);
            return p;
        } else {
            System.out.println("No hay valores posibles. Array vacío");
            return -1;
        }
    }

    public List position (int length_data, int p){
        int size = length_data + p ;
        List<Integer> pos = new ArrayList<>();

        for (int i =0 ; i < p; i++ ){
            int pow = (int) Math.pow(2, i);
            pos.add(pow);
        }
        return pos;
    }

    public List Message_data(List p_num, int length_data, int p){
        int size = length_data + p ;
        List<String> message = new ArrayList(size);

        for (int i=0 ; i< size; i++){
            message.add(i, "o");
        }

        for (int i=0 ; i< size; i++){
            if (p_num.contains(i)){
                int new_i = i-1;
                if( new_i <= size){
                    message.set(new_i, "x");
                }
            }
        }

        int n = message.size();

        for (int i = 0; i < n / 2; i++) {
            String temp = message.get(i);
            message.set(i, message.get(n - i - 1));
            message.set(n - i - 1, temp);
        }
        // System.out.println( message);
        return message;
    }

    public List true_table(List p_num, int length_data, int p) {
        int column = length_data + p;
        int fila = p_num.size();

        List<Integer> temp = new ArrayList<>();
        List<List<Integer>> tableList = new ArrayList<>();

        for (int j = 0; j < p_num.size(); j++) {
            Object  n = p_num.get(j);
            int N = (int) n;

            for (int i = 0; i <= column; i++) {
                if ((i / N) % 2 == 0) {
                    temp.add(0);
                } else {
                    temp.add(1);
                }
            }
            tableList.add(temp);
            temp = new ArrayList<>();
        }

        // System.out.println(tableList);
        return tableList;
    }
        
    public List message(String data, List new_data){
        List<Integer> message = new ArrayList<>();
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            int text = Character.getNumericValue(c);
            message.add(text);
        }
        
        int k = 0;
        for (int j = 0; j < new_data.size(); j++) {
            Object old_val = new_data.get(j);
            
            if (old_val.equals("o")) { // Cambiar la comparación a equals
    
                int val = message.get(k); // Obtener el valor correspondiente de message
                new_data.set(j, val);
                k += 1;
            }
        }
        
        // System.out.println(new_data);
        // System.out.println(message);
        return (List<Integer>) new_data;
    }

    public List missingIndex(List new_data){
        List<Integer> messageIndex = new ArrayList<>();
        for (int k =0; k<new_data.size();k++){
            Object value = new_data.get(k);

            if (value.equals("x")){
                messageIndex.add(k);
            }
        }
       // System.out.println( messageIndex);
       return messageIndex;
        
    }
    
    public List position_ones (List<List<Integer>> table, List new_data){ 
        List<List<Integer>> temp_index = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();

         for (List<Integer> lista : table) {
            temp = new ArrayList<>(); 
            for (int i = 0; i < lista.size(); i++) {
                int val = lista.get(i);
                if (val == 1) {
                    temp.add(i-1); 
                }
            }
            temp_index.add(temp);
        }

        return temp_index;
    }
    
    public List paridad(List<List<Integer>> ones_pos, List<Object> new_data) {
        //System.out.println( new_data);
        for (List<Integer> lista : ones_pos) {
            List<Object> temp2 = new ArrayList<>();
            for (int i = 0; i < lista.size(); i++) {
                int val = lista.get(i);
                Object text = new_data.get(val);
                temp2.add(text);
            }
    
            int countZero = 0;
            int countOne = 0;
    
            for (Object obj : temp2) {
                if (obj instanceof Integer) {
                    int c = (int) obj;
                    if (c == 0) {
                        countZero++;
                    } else if (c == 1) {
                        countOne++;
                    }
                }
            }
    
            int num;
            if (countOne % 2 == 0 && countZero % 2 != 0) {
                num = 0;
            } else if (countZero % 2 == 0 && countOne % 2 != 0) {
                num = 1;
            } else if (countZero  == 3 ) {
                num = 0;
            } else if (countOne  == 3 ) {
                num = 1;
            } else {
                num = 0;
            }
    
            // Find the index of 'x' in new_data for this specific sublist in ones_pos and replace it with the determined odd number
            int xIndex = -1;
            for (int i = 0; i < lista.size(); i++) {
                int val = lista.get(i);
                if (new_data.get(val).equals("x")) {
                    xIndex = val;
                    break;
                }
            }
            if (xIndex != -1) {
                new_data.set(xIndex, num);
            }
    
            // System.out.println("Temp2: " + temp2);
            // System.out.println("Updated new_data: " + new_data);
    
            countZero = 0;
            countOne = 0;
        }

        return new_data;
    }

    public String convertirString(List<List<Object>> listaDeListas) {
        StringBuilder builder = new StringBuilder();

        for (List<Object> lista : listaDeListas) {
            for (Object valor : lista) {
                builder.append(valor.toString());
            }
            builder.append(" ");
        }

        return builder.toString().trim();
    }
}

// 1001100