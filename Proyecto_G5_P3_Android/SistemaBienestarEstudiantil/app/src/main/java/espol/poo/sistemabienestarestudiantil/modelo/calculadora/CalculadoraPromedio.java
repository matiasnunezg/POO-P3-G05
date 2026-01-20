package espol.poo.sistemabienestarestudiantil.modelo.calculadora;


public class CalculadoraPromedio {
    // Las notas de entrada son enteras según tu requerimiento
    private int parcial1, parcial2, practico, mejoramiento;
    private int porcentajeTeorico;

    public CalculadoraPromedio(int p1, int p2, int prac, int mej, int porc) {
        this.parcial1 = p1;
        this.parcial2 = p2;
        this.practico = prac;
        this.mejoramiento = mej;
        this.porcentajeTeorico = porc;
    }

    public double calcularNotaFinal() {
        int notaP1 = parcial1;
        int notaP2 = parcial2;

        // Lógica de mejoramiento: reemplaza la más baja solo si la mejora
        if (mejoramiento > parcial1 || mejoramiento > parcial2) {
            if (parcial1 <= parcial2) {
                notaP1 = mejoramiento;
            } else {
                notaP2 = mejoramiento;
            }
        }

        // Promedio teórico (usamos 2.0 para forzar que el resultado sea decimal)
        double promedioTeorico = (notaP1 + notaP2) / 2.0;

        double pesoTeorico = porcentajeTeorico / 100.0;
        double pesoPractico = 1.0 - pesoTeorico;

        // Retorna el cálculo exacto con decimales
        return (promedioTeorico * pesoTeorico) + (practico * pesoPractico);
    }

    public String determinarEstado(double notaFinal) {
        // Regla de oro: 60.00 es el límite. 59.99 es reprobado.
        return (notaFinal >= 60.0) ? "¡Aprobaste! :D" : "Reprobaste :c";
    }
}