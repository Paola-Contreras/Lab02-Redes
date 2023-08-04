/*

    Universidad Del Valle de Guatemala
    Curso: Redes
    Laboratorio 2

    Paola Contreras 20213
    Paola De León 20361

    Algoritmo CRC-32 Receptor.
    How to Run en README.

*/

const fs = require('fs');


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


fs.readFile('CRC32_msg.txt', 'utf8', (err, data) => {

    if (err) {
        console.error('Error reading the file:', err);
    } else {
        const binaryCode = Array.from(data).map(Number);
        const res = crc32(binaryCode);
        console.log(res)
        res === true ? console.log('Mensaje sin errores. \n > Trama: ', data.slice(0, -32)) : console.log('@! Hay errores en el mensaje, por lo tanto se descarta.');
    }

});