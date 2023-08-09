import socket
import numpy as np

def string_to_List(new_data):
    new_dataList = []
    for k in reversed(new_data):
        new_dataList.append(k)
    return new_dataList

def paridad (one_index, data):
    new_paridad = []

    for i in one_index:
        temp = []
        countZero = 0
        countOne = 0
        # print(i)
        for j in i:
            val = data[j]
            temp.append(val)
            # print(temp,val,j)
        # print(temp)

        for k in temp:
            if k == '0':
                countZero += 1
            else: 
                countOne += 1
        
        # print( "zero",countZero)
        # print('one', countOne)

        if countZero == 3 or countOne ==3:
            new_paridad.append(1)
        elif countZero == 2 and countOne == 2:
            new_paridad.append(0)
        elif countZero == 4 or countOne == 4:
            new_paridad.append(0)
    #print(new_paridad)
    return new_paridad

def introduce_noise(sequence):
    error_rate = 0.1
    noisy_sequence = []
    for bit in sequence:
        if np.random.random() < error_rate:
            noisy_bit = '0' if bit == '1' else '1'
            noisy_sequence.append(noisy_bit)
        else:
            noisy_sequence.append(bit)
    return "".join(noisy_sequence)

def get_correccion(data):
    data.reverse()
    cadena_invertida = ''.join(data)
    return cadena_invertida

def conection():
    HOST = "192.168.1.5"  
    PORT = 65432       
    Trama = ""
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.bind((HOST, PORT))
        s.listen()
    
        conn, addr = s.accept()
        with conn:
            print(f"Conexion Entrante del proceso {addr}")
            while True: #en caso se envien mas de 1024 bytes
                #recibir 1024 bytes
                data = conn.recv(1024)
                if not data:
                    break   #ya se recibio todo
                print(f"Recibidoo: \n{data!s}")
                print(data)
                Trama = str(data)[2:-1]

    return Trama
Trama = conection()
noisy_sequence1 = introduce_noise(Trama)
Indices = "[[0, 2, 4, 6], [1, 2, 5, 6], [3, 4, 5, 6]]"
# Imprimir las secuencias originales y las ruidosas
print("Secuencia original 1:", Trama)
print("Secuencia ruidosa 1:", noisy_sequence1)

new_data = noisy_sequence1
one_index = eval(Indices)

segments = []
right_message = []

# Recorre la new_data en pasos de 7 caracteres, empezando desde el final
for i in range(len(new_data)-1, -1, -7):
    start_index = max(i-6, 0) 
    segmento = new_data[start_index:i+1]
    segments.append(segmento)

for segment in segments:
    print("\n-> Actualmente se esta evaluando: ",segment)
    data = string_to_List(segment)
    newest_paridad = paridad(one_index, data)
    if newest_paridad == [0, 0, 0] or new_data == Trama:
        print('La cadena no cuenta con ningun error, o esta no ha sido modificada')
    else:
        num_bit = int(''.join(map(str, newest_paridad)))
        num_decimal = int(str(num_bit), 2)

        # print(newest_paridad)
        # print(num_decimal)

        data_numDecimal = data[num_decimal-1]
        print('-> El error se encuentra en la posición: ',num_decimal)

        if data_numDecimal == "1":
            data[num_decimal-1] = "0"
        else:
            data[num_decimal-1] = "1"

        Fixed=get_correccion(data)
        right_message.append(Fixed)
        print("... Corrigiendo ...")

done = resultado = ''.join(right_message)
print('\n-> El mensaje ha sido corregido, originalmente era: ', done)