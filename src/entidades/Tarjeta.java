package entidades;

public class Tarjeta {
    private int dni;
    private int numero;
    private double disponible;
    private double saldoPagar;
    
    public Tarjeta(int dni, int numero, double disponible, double saldoPagar) {
        this.dni = dni;
        this.numero = numero;
        this.disponible = disponible;
        this.saldoPagar = saldoPagar;
    }

    public Tarjeta() {
    }

    public int getDni() {
        return dni;
    }

    public int getNumero() {
        return numero;
    }

    public double getDisponible() {
        return disponible;
    }

    public double getSaldoPagar() {
        return saldoPagar;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setDisponible(double disponible) {
        this.disponible = disponible;
    }

    public void setSaldoPagar(double saldoPagar) {
        this.saldoPagar = saldoPagar;
    }
    
    public String toString() {
        return "Tarjeta [numero=" + numero + ", disponible=" + disponible + ", saldoPagar=" + saldoPagar + "]";
    }
}