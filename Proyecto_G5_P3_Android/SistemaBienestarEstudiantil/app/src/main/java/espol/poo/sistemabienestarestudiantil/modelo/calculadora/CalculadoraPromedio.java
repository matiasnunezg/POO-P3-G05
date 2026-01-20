package espol.poo.sistemabienestarestudiantil.modelo.calculadora;

public class CalculadoraPromedio {
    private double parcial1, parcial2, practico, mejoramiento;
    private int porcentajeTeorico;

    public CalculadoraPromedio(double p1, double p2, double prac, double mej, int porc) {
        this.parcial1 = p1;
        this.parcial2 = p2;
        this.practico = prac;
        this.mejoramiento = mej;
        this.porcentajeTeorico = porc;
    }

    public double calcularNotaFinal() {
        double notaP1 = parcial1;
        double notaP2 = parcial2;

        // Mejoramiento reemplaza la más baja solo si es mayor
        if (mejoramiento > parcial1 || mejoramiento > parcial2) {
            if (parcial1 <= parcial2) {
                notaP1 = mejoramiento;
            } else {
                notaP2 = mejoramiento;
            }
        }

        double promedioTeorico = (notaP1 + notaP2) / 2.0;
        double pesoTeorico = porcentajeTeorico / 100.0;
        double pesoPractico = 1.0 - pesoTeorico;

        return (promedioTeorico * pesoTeorico) + (practico * pesoPractico);
    }

    public String determinarEstado(double notaFinal) {
        // CAMBIO: Ahora comparamos contra 60.0
        return (notaFinal >= 60.0) ? "¡Aprobaste! :D" : "Reprobaste :c";
    }
}