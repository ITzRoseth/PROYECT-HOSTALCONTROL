/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class CuentaEstadia {

    private String estadiaId;
    private int numeroHabitacion;
    private String nombreHuesped;
    private String fechaHoraIngreso;
    private String fechaSalida;
    private long diasHospedaje;
    private double tarifaDiaria;
    private double subtotalHabitacion;
    private double totalServicios;
    private double totalPagar;

    public CuentaEstadia() {
    }

    public CuentaEstadia(String estadiaId, int numeroHabitacion, String nombreHuesped,
                         String fechaHoraIngreso, String fechaSalida, long diasHospedaje,
                         double tarifaDiaria, double subtotalHabitacion,
                         double totalServicios, double totalPagar) {
        this.estadiaId = estadiaId;
        this.numeroHabitacion = numeroHabitacion;
        this.nombreHuesped = nombreHuesped;
        this.fechaHoraIngreso = fechaHoraIngreso;
        this.fechaSalida = fechaSalida;
        this.diasHospedaje = diasHospedaje;
        this.tarifaDiaria = tarifaDiaria;
        this.subtotalHabitacion = subtotalHabitacion;
        this.totalServicios = totalServicios;
        this.totalPagar = totalPagar;
    }

    public String getEstadiaId() {
        return estadiaId;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public String getNombreHuesped() {
        return nombreHuesped;
    }

    public String getFechaHoraIngreso() {
        return fechaHoraIngreso;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public long getDiasHospedaje() {
        return diasHospedaje;
    }

    public double getTarifaDiaria() {
        return tarifaDiaria;
    }

    public double getSubtotalHabitacion() {
        return subtotalHabitacion;
    }

    public double getTotalServicios() {
        return totalServicios;
    }

    public double getTotalPagar() {
        return totalPagar;
    }
}