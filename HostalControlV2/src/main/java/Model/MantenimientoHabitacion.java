/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class MantenimientoHabitacion {

    private String id;
    private String habitacionId;
    private int numeroHabitacion;
    private String tipoHabitacion;
    private String estadoAnterior;
    private String observacion;
    private String fechaRegistro;

    public MantenimientoHabitacion() {
    }

    public MantenimientoHabitacion(String habitacionId, int numeroHabitacion,
                                   String tipoHabitacion, String estadoAnterior,
                                   String observacion, String fechaRegistro) {
        this.habitacionId = habitacionId;
        this.numeroHabitacion = numeroHabitacion;
        this.tipoHabitacion = tipoHabitacion;
        this.estadoAnterior = estadoAnterior;
        this.observacion = observacion;
        this.fechaRegistro = fechaRegistro;
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

    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public String getEstadoAnterior() {
        return estadoAnterior;
    }

    public String getObservacion() {
        return observacion;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
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

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public void setEstadoAnterior(String estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}