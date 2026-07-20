/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Model.EstadoHabitacion;
import Model.Habitacion;
import Model.MantenimientoHabitacion;
import Repository.MantenimientoRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MantenimientoService {

    private final MantenimientoRepository mantenimientoRepository;
    private final HabitacionService habitacionService;

    public MantenimientoService() {
        this.mantenimientoRepository = new MantenimientoRepository();
        this.habitacionService = new HabitacionService();
    }

    public void enviarAMantenimiento(Habitacion habitacion, String observacion) {

        if (habitacion == null) {
            throw new IllegalArgumentException("Debe seleccionar una habitación.");
        }

        if (habitacion.getEstado() == EstadoHabitacion.OCUPADA) {
            throw new IllegalArgumentException("No se puede enviar a mantenimiento una habitación ocupada.");
        }

        if (habitacion.getEstado() == EstadoHabitacion.MANTENIMIENTO) {
            throw new IllegalArgumentException("La habitación ya se encuentra en mantenimiento.");
        }

        if (observacion == null || observacion.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe registrar una observación del mantenimiento.");
        }

        String fechaRegistro = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        MantenimientoHabitacion mantenimiento = new MantenimientoHabitacion(
                habitacion.getId(),
                habitacion.getNumero(),
                habitacion.getTipo(),
                habitacion.getEstado().name(),
                observacion.trim(),
                fechaRegistro
        );

        mantenimientoRepository.guardar(mantenimiento);

        habitacionService.actualizarEstadoHabitacion(
                habitacion.getId(),
                EstadoHabitacion.MANTENIMIENTO
        );
    }

    public List<MantenimientoHabitacion> listarMantenimientos() {
        return mantenimientoRepository.listar();
    }
}