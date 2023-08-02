import java.util.*;
import java.util.ArrayList;

class Receptor {
    public void paridado(List<List<Integer>> ones_pos,List<Integer> missing, String data) {
        List<Character> listaData = new ArrayList<>();
        List<Character> original = new ArrayList<>();
        List<Object> resultado = new ArrayList<>();
        
        for (char c : data.toCharArray()) {
            listaData.add(c);
            original.add(c);
        }
        
        System.out.println(data);
        System.out.println(ones_pos);

        for (int posicion : missing) {
            if (posicion >= 0 && posicion < listaData.size()) {
                listaData.set(posicion, 'x');
            }
        }


        for (List<Integer> lista : ones_pos) {
            List<Object> temp2 = new ArrayList<>();
            
            // Crear una copia de la lista original para evitar ConcurrentModificationException
            List<Integer> listaCopy = new ArrayList<>(lista);
            
            for (int i = 0; i < lista.size(); i++) {
                int val = lista.get(i);
                Object text = listaData.get(val);
                temp2.add(text);
                
                if (text.equals('x')) {
                    listaCopy.remove(Integer.valueOf(val));
                }
            }
            
            lista = listaCopy; // Actualizar la lista original sin los valores 'x'
            
            System.out.println(lista);
            System.out.println(temp2);
            System.out.println(resultado);
            //Parity Check & Replace
            int countZero = 0;
            int countOne = 0;
    
            for (Object obj : temp2) {
                if (obj instanceof Character) {  
                    char c = (char) obj; 
                    if (c == '0') { 
                        countZero++;
                    } else if (c == '1') { 
                        countOne++;
                    }
                }
            }
            System.out.println(temp2);
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
                if (listaData.get(val).equals("x")) {
                    xIndex = val;
                    break;
                }
            }
            System.out.println("NUM: " +num);
            resultado.add(num);
           
            System.out.println("Temp2: " + temp2);
            System.out.println("countZero " + countZero);
            System.out.println("countOne " + countOne);
    
            countZero = 0;
            countOne = 0;
         }
        System.out.println(listaData);
        System.out.println(ones_pos);
        System.out.println(resultado);
    }

}