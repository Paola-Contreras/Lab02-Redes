import java.util.*;

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
                System.out.println(p_num);
                System.out.println(i);
                int new_i = i-1;
                if( new_i <= size){
                    message.set(new_i, "x");
                }
            }
        }
        System.out.println(message);
        return message;
    }

    public void true_table(){
        
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
    }
}

/* 2^p >= p + i + 1 
 * donde p es el bit de paridad
 * donde i es la cantidad de bits 
*/