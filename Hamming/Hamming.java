import java.io.*;
import java.net.*;

import java.util.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;

import java.net.Socket;
import java.net.InetAddress;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;


public class Hamming {
    public void conect(String HOST, int	PORT, String doc)throws IOException, UnknownHostException, InterruptedException {
        OutputStreamWriter writer = null;
        ObjectInputStream ois = null;
        System.out.println("\n * Emisor Java Sockets *");

        //crear socket/conexion
        Socket socketCliente = new Socket( InetAddress.getByName(HOST), PORT);

        //mandar data 
        System.out.println("- Enviando Data");
        writer = new OutputStreamWriter(socketCliente.getOutputStream());
        String payload = doc;
        writer.write(payload);	//enviar payload
        Thread.sleep(100);

        //limpieza
        System.out.println("* Liberando Sockets *");
        writer.close();
        socketCliente.close();
    }

    public void option2(String	HOST,  int	PORT) throws IOException, UnknownHostException, InterruptedException{
        Emisor emisor = new Emisor();
        Conversor conversor = new Conversor();

        String data = "";
        String resultado = "";
        List<List<Object>> temp = new ArrayList<>();
        List<List<Object>> paridad_wished = new ArrayList<>();
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
            // System.out.println(BinaryList.get(item));

            if (ND.length() % 4 != 0) {
                int elementosFaltantes = 4 - (ND.length() % 4);
                // System.out.println("Elementos faltantes: " + elementosFaltantes);
                String ceros = "0".repeat(elementosFaltantes);
        
                //Concatenar los ceros a la izquierda de Data
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
        System.out.println("\n * Emisor Java Sockets *");

        //crear socket/conexion
        Socket socketCliente = new Socket( InetAddress.getByName(HOST), PORT);

        //mandar data 
        System.out.println("- Enviando Data");
        writer = new OutputStreamWriter(socketCliente.getOutputStream());
        String payload = resultado;
        writer.write(payload);	//enviar payload
        Thread.sleep(100);

        //limpieza
        System.out.println("* Liberando Sockets *");
        writer.close();
        socketCliente.close();
    }

    public String option1(String Words){
        Emisor emisor = new Emisor();
        Conversor conversor = new Conversor();

        String data = "";
        String resultado = "";
        List<List<Object>> temp = new ArrayList<>();
        List<List<Object>> paridad_wished = new ArrayList<>();
        List<List<Integer>> one_index = new ArrayList<>();
        
        String Data = Words;

        
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
            // System.out.println(BinaryList.get(item));

            if (ND.length() % 4 != 0) {
                int elementosFaltantes = 4 - (ND.length() % 4);
                // System.out.println("Elementos faltantes: " + elementosFaltantes);
                String ceros = "0".repeat(elementosFaltantes);
        
                //Concatenar los ceros a la izquierda de Data
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
        System.out.println("-> La data codificada es: " + resultado + "\n");

        return resultado;
    }
}
