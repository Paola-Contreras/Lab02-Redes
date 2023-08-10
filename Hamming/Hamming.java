import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.net.Socket;
import java.net.InetAddress;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;

class Hamming {
    private static String	HOST = "192.168.1.5";
	private static int	PORT = 65432;

    public static void main(String args[]) throws IOException, UnknownHostException, InterruptedException {
        Emisor emisor = new Emisor();
        Conversor conversor = new Conversor();
        
        String data = "";
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

        char[] charArray = Data.toCharArray();
        List<Character> charList = new ArrayList<>();
        
        for (char c : charArray) {
            charList.add(c);
        }
        
        List<String> BinaryList = new ArrayList<>();
        // Convertir la lista de caracteres a una cadena binaria
        for (char c : charList) {
            // System.out.println(c);
            int valorAscii = (int) c;
            String valorBinario = conversor.convertirABinario(valorAscii);
            // System.out.println(valorBinario);
            BinaryList.add(valorBinario.toString());
        }

        // System.out.println(BinaryList);

        // System.out.println("Cadena binaria: " + Data);
        StringBuilder accumulatedResult = new StringBuilder();
        for (int item = 0; item < BinaryList.size(); item ++) {
            String ND = BinaryList.get(item);
            System.out.println(BinaryList.get(item));
            if (ND.length() % 4 != 0) {
                int elementosFaltantes = 4 - (ND.length() % 4);
                // System.out.println("Elementos faltantes: " + elementosFaltantes);
                String ceros = "0".repeat(elementosFaltantes);
        
                // Concatenar los ceros a la izquierda de Data
                data = ceros + ND;
                accumulatedResult.append(data);
                // System.out.println("Datos con ceros agregados: " + data); 
            }
        }
        // System.out.println(accumulatedResult);
        Data = accumulatedResult.toString();
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


        OutputStreamWriter writer = null;
        ObjectInputStream ois = null;
        System.out.println("Emisor Java Sockets\n");

        //crear socket/conexion
        Socket socketCliente = new Socket( InetAddress.getByName(HOST), PORT);

        //mandar data 
        System.out.println("Enviando Data\n");
        writer = new OutputStreamWriter(socketCliente.getOutputStream());
        String payload = resultado;
        writer.write(payload);	//enviar payload
        Thread.sleep(100);

        //limpieza
        System.out.println("Liberando Sockets\n");
        writer.close();
        socketCliente.close();

    }
}