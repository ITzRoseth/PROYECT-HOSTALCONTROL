
package Model;


public class Habitacion {

    private String id;
    private int numero;
    private String tipo;
    private double tarifa;
    private EstadoHabitacion estado;

    public Habitacion() {
    }

    public Habitacion(int numero, String tipo, double tarifa, EstadoHabitacion estado) {
        this.numero = numero;
        this.tipo = tipo;
        this.tarifa = tarifa;
        this.estado = estado;
    }

    public Habitacion(String id, int numero, String tipo, double tarifa, EstadoHabitacion estado) {
        this.id = id;
        this.numero = numero;
        this.tipo = tipo;
        this.tarifa = tarifa;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    public double getTarifa() {
        return tarifa;
    }

    public EstadoHabitacion getEstado() {
        return estado;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    public void setEstado(EstadoHabitacion estado) {
        this.estado = estado;
    }
    
    @Override
        public String toString() {
        return "Hab. " + numero + " - " + tipo + " - " + estado;
    }
}