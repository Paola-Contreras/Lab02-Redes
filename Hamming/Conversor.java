class Conversor {
    public String convertirABinario(int numeroDecimal) {
        if (numeroDecimal == 0) {
            return "0";
        }
        
        StringBuilder binario = new StringBuilder();
        while (numeroDecimal > 0) {
            int residuo = numeroDecimal % 2;
            binario.insert(0, residuo); // Agregar el residuo al principio
            numeroDecimal /= 2;
        }
        
        return binario.toString();
    }
}