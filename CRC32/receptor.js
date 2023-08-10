/*

    Universidad Del Valle de Guatemala
    Curso: Redes
    Laboratorio 2

    Paola Contreras 20213
    Paola De León 20361

    Algoritmo CRC-32 Receptor.
    How to Run en README.

*/

const net = require('net');
const fs = require('fs');
const HOST = '192.168.5.227';
const PORT = 3000;

const allZeros = (binaryCode) => {
    return binaryCode.every((value) => value === 0);
}


const crc32 = (binaryCode) => {

    // Polinomio generador
    const polynomial = [1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1];
    const padding = polynomial.length - 1;

    // División de código dentro de polinomio
    for (let i = 0; i < binaryCode.length - padding; i++) {
        if (binaryCode[i] === 1) {
            for (let j = 0; j < polynomial.length; j++) {
                binaryCode[i + j] ^= polynomial[j];
            }
        }
    }

    return allZeros(binaryCode)

}

const binaryToString = (binary) => {
  let string = '';
  const binaryValues = binary.match(/.{1,8}/g);

  for (const binaryValue of binaryValues) {
      const decimalValue = parseInt(binaryValue, 2);
      const character = String.fromCharCode(decimalValue);
      string += character;
  }

  return string;
}


const server = net.createServer((socket) => {
  console.log(`Conexion Entrante del proceso ${socket.remoteAddress}:${socket.remotePort}`);
  
  socket.on('data', (data) => {
    if (!data) {
      socket.end();
    } else {
      const info = data.toString()
      console.log(`Recibido: ${info}`);
      const binaryCode = Array.from(info).map(Number);
      const res = crc32(binaryCode);
      res === true ? console.log('Mensaje sin errores. \n > Mensaje: ', binaryToString(info.slice(0, -32))) : console.log('@! Hay errores en el mensaje,\n   por lo tanto se descarta.');
    }
  });
  
  socket.on('error', (err) => {
    console.log(`\nError: ${err}`);
  });
  
  socket.on('end', () => {
    console.log('\nCliente desconectado');
  });
});

server.listen(PORT, HOST, () => {
  console.log(`\nServidor escuchando en ${HOST}:${PORT}`);
});