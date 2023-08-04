"""
    Universidad Del Valle de Guatemala
    Curso: Redes
    Laboratorio 2

    Paola Contreras 20213
    Paola De León 20361

    Algoritmo CRC-32.
    How to Run en README.
"""


import sys

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



if __name__ == "__main__":
    trama = "1011101010"
    print('Trama ingresada:', trama)
    trama = convertToList(trama)
    res = crc32(trama)
    tramaRes = ''.join(map(str, res))
    createFile(tramaRes)
    print("Resultado de CRC-32:", len(tramaRes))