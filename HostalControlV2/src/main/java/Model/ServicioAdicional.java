/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class ServicioAdicional {

    private String id;
    private String estadiaId;
    private String habitacionId;
    private int numeroHabitacion;
    private String nombreServicio;
    private int cantidad;
    private double valorUnitario;
    private double subtotal;
    private String fechaRegistro;

    public ServicioAdicional() {
    }

    public ServicioAdicional(String estadiaId, String habitacionId, int numeroHabitacion,
                             String nombreServicio, int cantidad, double valorUnitario,
                             double subtotal, String fechaRegistro) {
        this.estadiaId = estadiaId;
        this.habitacionId = habitacionId;
        this.numeroHabitacion = numeroHabitacion;
        this.nombreServicio = nombreServicio;
        this.cantidad = cantidad;
        this.valorUnitario = valorUnitario;
        this.subtotal = subtotal;
        this.fechaRegistro = fechaRegistro;
    }

    public String getId() {
        return id;
    }

    public String getEstadiaId() {
        return estadiaId;
    }

    public String getHabitacionId() {
        return habitacionId;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEstadiaId(String estadiaId) {
        this.estadiaId = estadiaId;
    }

    public void setHabitacionId(String habitacionId) {
        this.habitacionId = habitacionId;
    }

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}