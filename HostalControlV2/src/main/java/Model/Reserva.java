/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class Reserva {

    private String id;
    private String habitacionId;
    private int numeroHabitacion;
    private String nombreHuesped;
    private String documentoHuesped;
    private String telefonoHuesped;
    private String fechaIngreso;
    private String fechaSalida;

    public Reserva() {
    }

    public Reserva(String habitacionId, int numeroHabitacion, String nombreHuesped,
                   String documentoHuesped, String telefonoHuesped,
                   String fechaIngreso, String fechaSalida) {
        this.habitacionId = habitacionId;
        this.numeroHabitacion = numeroHabitacion;
        this.nombreHuesped = nombreHuesped;
        this.documentoHuesped = documentoHuesped;
        this.telefonoHuesped = telefonoHuesped;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
    }

    public String getId() {
        return id;
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

    public String getTelefonoHuesped() {
        return telefonoHuesped;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setTelefonoHuesped(String telefonoHuesped) {
        this.telefonoHuesped = telefonoHuesped;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
}