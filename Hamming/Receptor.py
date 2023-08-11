import sys
import socket
import numpy as np
import matplotlib.pyplot as plt

def string_to_List(new_data):
    new_dataList = []
    for k in reversed(new_data):
        new_dataList.append(k)
    return new_dataList

def paridad (one_index, data):
    # print(data)
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
    # print(sequence)
    if len(sequence)%7 != 0:
        ceros_a_anadir = 7 - len(sequence)
        cadena_con_zeros = '0' * ceros_a_anadir + sequence
        sequence = cadena_con_zeros
    else:
        pass

    print(sequence,"ddddd")
    error_rate = 0.03
    noisy_sequence = []
    for bit in sequence:
        if np.random.random() < error_rate:
            noisy_bit = '0' if bit == '1' else '1'
            noisy_sequence.append(noisy_bit)
        else:
            noisy_sequence.append(bit)
    noisy = "".join(noisy_sequence)
    return noisy

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
            print(f"Conexion Entrante del proceso {addr}\n")
            while True: #en caso se envien mas de 1024 bytes
                #recibir 1024 bytes
                data = conn.recv(1024)
                if not data:
                    break   #ya se recibio todo
                # print(f"Recibidoo: \n{data!s}")
                # print(data)
                Trama += str(data.decode("utf-8") )
    # Trama = Trama[2:-1]
    # Trama_decodificada = Trama
    return Trama

def get_org (binary_string):
    # print(binary_string,"ww")
    if len(binary_string)%7 != 0:
        ceros_a_anadir = 7 - len(binary_string)
        cadena_con_zeros = '0' * ceros_a_anadir + binary_string
        binary_string = cadena_con_zeros
    else:
        pass
    
    segments = [binary_string[i-7:i] for i in range(len(binary_string), 0, -7)]
    segments = [elemento for elemento in segments if elemento != '']
    # print(segments)
    get_bit = []
    word = []
    for segment in segments:
        # print(segment)
        new_seg = list(segment)
        new_seg.pop(6)
        new_seg.pop(5)
        new_seg.pop(3)
        # print(new_seg)
        new_bit = ''.join(new_seg)
        get_bit.append(new_bit)
    # print(get_bit)
    get_bits = ''.join(reversed(get_bit))
    # print(get_bits)

    segment_length = 8
    for i in range(0, len(get_bits), segment_length):
        Eigth_bits = get_bits[i:i+segment_length]
        # print("Segmento:", Eigth_bits)
        decimal = int(str(Eigth_bits), 2)
        ascii_character = chr(decimal)
        # print(ascii_character,'hh')
        word.append(ascii_character)
    F_word =''.join(word)
    # print(F_word)
    return F_word

def get_trama(segments): 
    # print(segments)
    get_bit = []
    word = []
    for segment in segments:
        #print(segment)
        new_seg = list(segment)
        new_seg.pop(6)
        new_seg.pop(5)
        new_seg.pop(3)
        # print(new_seg)
        new_bit = ''.join(new_seg)
        get_bit.append(new_bit)
    # print(get_bit)
    get_bits = ''.join(reversed(get_bit))
    # print(get_bits)

    segment_length = 8
    for i in range(0, len(get_bits), segment_length):
        Eigth_bits = get_bits[i:i+segment_length]
        #print("Segmento:", Eigth_bits)
        decimal = int(str(Eigth_bits), 2)
        ascii_character = chr(decimal)
        # print(ascii_character,'hh')
        word.append(ascii_character)
    F_word =''.join(word)
    print(F_word)
    return F_word

sys.stdout.reconfigure(encoding='utf-8')
Trama = conection()

# print(Trama)
Indices = "[[0, 2, 4, 6], [1, 2, 5, 6], [3, 4, 5, 6]]"
if ',' not in Trama:
    noisy_sequence1 = introduce_noise(Trama)
    # Imprimir las secuencias originales y las ruidosas
    # print("Trama Original:", Trama)
    print("Receptor recibio:", noisy_sequence1)
    original_trama = get_org(Trama)
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
            print('La cadena no cuenta con ningun error')
            right_message.append(segment)
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

    done = get_trama(right_message)        

    if done == original_trama:
        print('\n------- RESULTADO -------')
        print('-> Mensaje obtendio : ',done )
    else:
        print('\n------- RESULTADO -------')
        print('-> Mensaje obtendio : ',done )
        print("** El mensaje no se ha podido corregir **")
else:
    listOrg = []
    listRes = []
    lineas = Trama.split(',')
    lineas = [item for item in lineas if item != '']
    print(len(lineas))
    # print(lineas)
    for combination in lineas:
        # print(combination)
        noisy_sequence1 = introduce_noise(combination)
        print("Receptor recibio:", noisy_sequence1)
        original_trama = get_org(combination)
        # print(original_trama)
        listOrg.append(original_trama)
        new_data = noisy_sequence1
        one_index = eval(Indices)

        segments = []
        right_message = []

        # Recorre la new_data en pasos de 7 caracteres, empezando desde el final
        for i in range(len(new_data)-1, -1, -7):
            start_index = max(i-6, 0) 
            segmento = new_data[start_index:i+1]
            segments.append(segmento)
        Nsegments = [palabra for palabra in segments if palabra.strip() != '']
        print(Nsegments)
        Nsegments = [elemento for elemento in Nsegments if len(elemento) > 1]
        for segment in Nsegments:
            print("\n-> Actualmente se esta evaluando: ",segment)
            data = string_to_List(segment)
            newest_paridad = paridad(one_index, data)
            if newest_paridad == [0, 0, 0] or new_data == Trama:
                print('La cadena no cuenta con ningun error')
                right_message.append(segment)
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

        done = get_trama(right_message)        

        if done == original_trama:
            print('\n------- RESULTADO -------')
            print('-> Mensaje obtendio : ',done, '\n')
            listRes.append(done)
        else:
            print('\n------- RESULTADO -------')
            print('-> Mensaje obtendio : ',done )
            print("** El mensaje no se ha podido corregir correctamente **\n")
            listRes.append(done)
    # print(listOrg)
    # print(listRes)
    iguales = 0
    diferentes = 0
    print('----------- RESULTADOS SIMULACION -----------')
    for elemento1, elemento2 in zip(listOrg, listRes):
        if elemento1 == elemento2:
            # print(elemento1,elemento2)
            iguales += 1
        else:
            # print(elemento1,elemento2)
            diferentes += 1

    # Imprimir resultados
    
    print(f"Número de elementos iguales: {iguales}")
    print(f"Número de elementos diferentes: {diferentes}")
    # Etiquetas para las porciones del gráfico

    etiquetas = ['Correctas', 'Incorrectas']
    valores = [iguales, diferentes]

    # Colores de las porciones
    colores = ['orange', 'grey']

    plt.pie(valores, labels=etiquetas, colors=colores, autopct='%1.1f%%', startangle=140)
    plt.title('Resultados Hamming Simulacion')

    plt.axis('equal')  # Para asegurar que la gráfica sea circular
    plt.show()