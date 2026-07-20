/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class Pago {

    private String id;
    private String estadiaId;
    private String habitacionId;
    private int numeroHabitacion;
    private String nombreHuesped;
    private String documentoHuesped;
    private String fechaIngreso;
    private String fechaSalida;
    private long diasHospedaje;
    private double subtotalHabitacion;
    private double totalServicios;
    private double totalPagado;
    private MetodoPago metodoPago;
    private String fechaPago;

    public Pago() {
    }

    public Pago(String estadiaId, String habitacionId, int numeroHabitacion,
                String nombreHuesped, String documentoHuesped,
                String fechaIngreso, String fechaSalida, long diasHospedaje,
                double subtotalHabitacion, double totalServicios,
                double totalPagado, MetodoPago metodoPago, String fechaPago) {

        this.estadiaId = estadiaId;
        this.habitacionId = habitacionId;
        this.numeroHabitacion = numeroHabitacion;
        this.nombreHuesped = nombreHuesped;
        this.documentoHuesped = documentoHuesped;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.diasHospedaje = diasHospedaje;
        this.subtotalHabitacion = subtotalHabitacion;
        this.totalServicios = totalServicios;
        this.totalPagado = totalPagado;
        this.metodoPago = metodoPago;
        this.fechaPago = fechaPago;
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

    public String getNombreHuesped() {
        return nombreHuesped;
    }

    public String getDocumentoHuesped() {
        return documentoHuesped;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public long getDiasHospedaje() {
        return diasHospedaje;
    }

    public double getSubtotalHabitacion() {
        return subtotalHabitacion;
    }

    public double getTotalServicios() {
        return totalServicios;
    }

    public double getTotalPagado() {
        return totalPagado;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public String getFechaPago() {
        return fechaPago;
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

    public void setNombreHuesped(String nombreHuesped) {
        this.nombreHuesped = nombreHuesped;
    }

    public void setDocumentoHuesped(String documentoHuesped) {
        this.documentoHuesped = documentoHuesped;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public void setDiasHospedaje(long diasHospedaje) {
        this.diasHospedaje = diasHospedaje;
    }

    public void setSubtotalHabitacion(double subtotalHabitacion) {
        this.subtotalHabitacion = subtotalHabitacion;
    }

    public void setTotalServicios(double totalServicios) {
        this.totalServicios = totalServicios;
    }

    public void setTotalPagado(double totalPagado) {
        this.totalPagado = totalPagado;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }
}