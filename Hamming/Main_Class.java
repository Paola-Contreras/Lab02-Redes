import java.util.*;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.io.*;
import java.net.*;
import java.net.Socket;
import java.net.InetAddress;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;

class Main_Class {
    private static String	HOST = "192.168.1.5";
	private static int	PORT = 65432;

    public static void main(String args[]) throws IOException, UnknownHostException, InterruptedException {
        Hamming hamming = new Hamming();

        Scanner scanner = new Scanner(System.in);
        String resultado = "";


        while (true) {
            System.out.println("----- Menu ....");
            System.out.println("1. Simulacion");
            System.out.println("2. Unico Mensaje");
            System.out.print("Selecciona una opcion: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n"+ "Seleccionaste simulacion");
                    String rutaArchivo = "Palabras.txt";
            
                    File archivo = new File(rutaArchivo);
                    FileReader fr = new FileReader(archivo);
                    BufferedReader br = new BufferedReader(fr);
                    String Dato = "";
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        System.out.println("\n"+linea);
                        hamming.option1(linea);

                    }
                    String  rutaArchivoF = "HammingEmisor.txt"; 
                    hamming.conect(HOST,PORT,rutaArchivoF);
                    System.exit(0);

                case 2:
                    System.out.println("Seleccionaste enviar un mensaje");
                    hamming.option2(HOST,PORT);
                    System.exit(0);

                default:
                    System.out.println("Opción no válida. Por favor, selecciona una opción válida.");
            }
        }

    }
}