package com.example.preexamenc1;

public class ReciboNomina{
    private int numRecibo;
    private String nombre;
    private float horasTrabNormales;
    private float horasTrabExtras;
    private int puesto;
    private float porcentajeImpuesto;

    public ReciboNomina(int numRecibo, String nombre, float horasTrabNormales, float horasTrabExtras, int puesto) {
        this.numRecibo = numRecibo;
        this.nombre = nombre;
        this.horasTrabNormales = horasTrabNormales;
        this.horasTrabExtras = horasTrabExtras;
        this.puesto = puesto;
        this.porcentajeImpuesto = 0.16f; // 16% de impuesto
    }

    public float calcularPagoBase(){
        float pagoBase = 200;
        switch (puesto) {
            case 1:
                pagoBase *= 1.20; // Incremento del 20%
                break;
            case 2:
                pagoBase *= 1.50; // Incremento del 50%
                break;
            case 3:
                pagoBase *= 2.00; // Incremento del 100%
                break;
        }
        return pagoBase;
    }

    public float calcularSubtotal() {
        float pagoBase = calcularPagoBase();
        return (pagoBase * horasTrabNormales) + (horasTrabExtras * pagoBase * 2);
    }

    public float calcularImpuesto() {
        return calcularSubtotal() * porcentajeImpuesto;
    }

    public float calcularTotalPagar() {
        return calcularSubtotal() - calcularImpuesto();
    }
}