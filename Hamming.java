import java.util.*;
import java.util.ArrayList;

class Hamming {
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

        System.out.println(message);
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

        System.out.println(tableList);
        return tableList;
    }
        
    public void paridad (List<List<Integer>> table,  String Data, List new_data){
        List<Integer> message = new ArrayList<>();
        List<List<Integer>> temp_index = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();

        for (int i = 0; i < Data.length(); i++) {
            char c = Data.charAt(i);
            int text = Character.getNumericValue(c);
            message.add(text);
        }
        
        int k = 0;
        for (int j=new_data.size() - 1; j >= 0; j--){
            Object old_val = new_data.get(j);
            
            if (old_val == "o"){ 
            
                int val = message.get(k); 
                new_data.set(j,val);
                k +=1;
            }
        }
        // For para obtener los indices en donde hay 1 dentro de mi tabla 
        for (List<Integer> lista : table) {
            temp = new ArrayList<>(); 
        
            for (int i = 0; i < lista.size(); i++) {
                int valo = lista.get(i);
        
                if (valo== 1) {
                    temp.add(i-1); 
                }
                 
            }
            temp_index.add(temp);
        }
        System.out.println( new_data);


        for (List<Integer> lista_index : temp_index) {
           List<Object> temp2 = new ArrayList<>(); 
           new_data = new_data;
            for (int i = 0; i < lista_index.size(); i++) {
                int valor = lista_index.get(i);
                Object message_text = new_data.get(valor);
                temp2.add(message_text);
                System.out.println(valor);
            }
            System.out.println(temp2);

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
        
            // if (countZero % 2 == 0 && countOne %2 != 0){
            //     int num = 1;
            //     new_data.set(valor,num);
            //     System.out.println("Cantidad de '0': " + countZero);
            // } else if ( countOne % 2 == 0 && countZero == 3) {
            //     int num = 0;
            //     new_data.set(valor,num);

            //     System.out.println("Cantidad de '1': " + countOne);
            //     System.out.println("Cantidad de '1': " + countZero);
            // }else if (countOne % 2 == 0 && countZero %2 != 0) {
            //     int num = 0;
            //     new_data.set(valor,num);

            //     System.out.println("Cantidad : " + countOne);

            // }

            System.out.println( new_data);
        }
        System.out.println();
        System.out.println(temp_index);
        System.out.println();
        System.out.println( new_data);


    }

    public static void main(String args[]) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Ingrese mensaje: ");

        String Data = myObj.nextLine();
        System.out.println("\nLa data ingresada es: " + Data);

        int i = Data.length();
        Hamming hamming = new Hamming();

        int p = hamming.function(i);
        List p_num = hamming.position(i,p);
        List new_data = hamming.Message_data(p_num,i,p);
        List table = hamming.true_table(p_num,i,p);

        hamming.paridad(table,Data,new_data);
    }
}