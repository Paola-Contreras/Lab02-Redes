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

    allZeros(binaryCode) === true ? console.log('Mensaje sin errores.') : console.log('@! Hay errores en el mensaje.');

}


fs.readFile('CRC32_msg.txt', 'utf8', (err, data) => {

    if (err) {
        console.error('Error reading the file:', err);
    } else {
        const binaryCode = Array.from(data).map(Number);
        crc32(binaryCode);
    }

});