/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Model.EstadoEstadia;
import Model.EstadoHabitacion;
import Model.Estadia;
import Model.Habitacion;
import Repository.EstadiaRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CheckInService {

    private final EstadiaRepository estadiaRepository;
    private final HabitacionService habitacionService;

    public CheckInService() {
        this.estadiaRepository = new EstadiaRepository();
        this.habitacionService = new HabitacionService();
    }

    public void registrarCheckIn(Habitacion habitacion, String nombreHuesped,
                                 String documentoHuesped, String telefonoHuesped) {

        if (habitacion == null) {
            throw new IllegalArgumentException("Debe seleccionar una habitación.");
        }

        if (habitacion.getEstado() != EstadoHabitacion.DISPONIBLE) {
            throw new IllegalArgumentException("Solo se puede hacer check-in en habitaciones disponibles.");
        }

        if (nombreHuesped == null || nombreHuesped.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del huésped es obligatorio.");
        }

        if (documentoHuesped == null || documentoHuesped.trim().isEmpty()) {
            throw new IllegalArgumentException("La cédula o pasaporte del huésped es obligatorio.");
        }

        if (telefonoHuesped == null || telefonoHuesped.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono del huésped es obligatorio.");
        }

        String fechaHoraIngreso = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Estadia estadia = new Estadia(
                habitacion.getId(),
                habitacion.getNumero(),
                nombreHuesped.trim(),
                documentoHuesped.trim(),
                telefonoHuesped.trim(),
                fechaHoraIngreso,
                EstadoEstadia.ACTIVA
        );

        estadiaRepository.guardar(estadia);

        habitacionService.actualizarEstadoHabitacion(
                habitacion.getId(),
                EstadoHabitacion.OCUPADA
        );
    }

    public List<Estadia> listarEstadias() {
        return estadiaRepository.listar();
    }
}