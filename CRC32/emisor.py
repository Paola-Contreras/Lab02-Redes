"""
    Universidad Del Valle de Guatemala
    Curso: Redes
    Laboratorio 2

    Paola Contreras 20213
    Paola De León 20361

    Algoritmo CRC-32.
    How to Run en README.
"""


import random
import sys
import socket

HOST = "192.168.5.227"
PORT = 3000

def crc32(binaryCode: list[int]) -> list[int]:
    '''
        Algoritmo CRC-32.
    '''
    # Polinomio generador
    polynomial = [1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1]

    # Guardar la cadena original
    originalCode = binaryCode[:]

    # Añadir 0 para llegar a longitud +32
    padding = len(polynomial) - 1
    binaryCode = binaryCode + [0] * padding

    # Realizar la operación de división usando XOR
    for i in range(len(binaryCode) - padding):
        if binaryCode[i] == 1:
            for j in range(len(polynomial)):
                binaryCode[i + j] ^= polynomial[j]


    # Devolver la cadena original + bits de paridad
    return originalCode + binaryCode[-padding:]


def convertToList(stringBinario) -> list[int]:
    '''
        Convertir cadena a list de enteros.
    '''
    return [int(bit) for bit in stringBinario]


def createFile(content:str):
    '''
        Crear txt con resultado.
    '''
    with open('./CRC32_msg.txt', 'w') as file:
        file.write(content)


def binaryString(string):
    binary_representation = ""
    
    for char in string:
        ascii_value = ord(char)
        binary_value = bin(ascii_value)[2:]
        padding = 8 - len(binary_value)
        binary_with_padding = '0' * padding + binary_value
        
        binary_representation += binary_with_padding
    
    return binary_representation


def apply_noise(frame, probability):
    noisy_frame = []
    
    for bit in frame:
        if random.random() < probability:
            noisy_bit = int(not bit)
            print("RUIDO APLICADO")
        else:
            noisy_bit = bit
        noisy_frame.append(noisy_bit)
    
    return noisy_frame


def main():
    print("Emisor Python Sockets\n")
    
    # Crear socket/conexión
    socket_cliente = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    socket_cliente.connect((HOST, PORT))
    
    # Enviar data
    probability = 0.01
    string = input("Ingrese el mensaje a transmitir: ")
    trama = binaryString(string)
    print('\nTrama ingresada:', trama)
    trama = apply_noise(trama, probability)
    trama = convertToList(trama)
    res = crc32(trama)
    tramaRes = ''.join(map(str, res))
    print("Resultado de CRC-32:\n", tramaRes)
    print()
    socket_cliente.sendall(tramaRes.encode()) 
    
    # Limpieza
    print("Liberando Sockets\n")
    socket_cliente.close()

if __name__ == "__main__":
    main()
