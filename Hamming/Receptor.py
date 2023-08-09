import socket

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

def get_variables(txt):
    # Obtener los datos del emisor 
    with open(f"{txt}.txt", "r") as archivo:
        doc = archivo.readlines()

    # Elimina los saltos de línea de cada línea y crea variables en base a ellas
    for linea in doc:
        nombre_variable, contenido = linea.strip().split(": ")
        globals()[nombre_variable] = contenido
    return Mensaje_emisor , Indices

def get_correccion(data):
    data.reverse()
    cadena_invertida = ''.join(data)
    return cadena_invertida

HOST = "192.168.1.5"  
PORT = 65432       

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
            print(f"Recibidoo: \n{data!r}\n{data!s}\n{data!a}") #!r !s !a, repr() str() ascii()
            ##echo
            #conn.sendall(data)


# Mensaje_emisor, Indices = get_variables("HammingEmisor")
# print(f"\tPor favor, ingrese el nuevo mensaje cambiandole un bit a {Mensaje_emisor}")
# print("**Nota: si este es mayor a 7 bits hacer un cambio dentro de un bloque de 7 caracteres**\n")
# new_data = nuevos_datos = input("Ingrese mensaje: ")
# #new_data = "10111001011100"
# one_index = eval(Indices)

# segments = []
# right_message = []
# for i in range(0, len(new_data), 7):
#     segmento = new_data[i:i+7]
#     segments.append(segmento)


# for segment in segments:
#     print("\n-> Actualmente se esta evaluando: ",segment)
#     data = string_to_List(segment)
#     newest_paridad = paridad(one_index, data)
#     if newest_paridad == [0, 0, 0] or new_data == Mensaje_emisor:
#         print('La cadena no cuenta con ningun error, o esta no ha sido modificada')
#     else:
#         num_bit = int(''.join(map(str, newest_paridad)))
#         num_decimal = int(str(num_bit), 2)

#         # print(newest_paridad)
#         # print(num_decimal)

#         data_numDecimal = data[num_decimal-1]
#         print('-> El error se encuentra en la posición: ',num_decimal)

#         if data_numDecimal == "1":
#             data[num_decimal-1] = "0"
#         else:
#             data[num_decimal-1] = "1"

#         Fixed=get_correccion(data)
#         right_message.append(Fixed)
#         print("... Corrigiendo ...")

# done = resultado = ''.join(right_message)
# print('\n-> El mensaje ha sido corregido, originalmente era: ', done)