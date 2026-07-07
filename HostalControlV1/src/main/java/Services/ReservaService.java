/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Model.EstadoHabitacion;
import Model.Habitacion;
import Model.Reserva;
import Repository.ReservaRepository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ReservaService {

    private final ReservaRepository repository;

    public ReservaService() {
        this.repository = new ReservaRepository();
    }

    public void registrarReserva(Habitacion habitacion, String nombreHuesped,
                                 String documentoHuesped, String telefonoHuesped,
                                 String fechaIngresoTexto, String fechaSalidaTexto) {

        if (habitacion == null) {
            throw new IllegalArgumentException("Debe seleccionar una habitación.");
        }

        if (habitacion.getEstado() == EstadoHabitacion.MANTENIMIENTO) {
            throw new IllegalArgumentException("No se puede reservar una habitación en mantenimiento.");
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

        LocalDate fechaIngreso;
        LocalDate fechaSalida;

        try {
            fechaIngreso = LocalDate.parse(fechaIngresoTexto.trim());
            fechaSalida = LocalDate.parse(fechaSalidaTexto.trim());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Las fechas deben tener el formato yyyy-MM-dd.");
        }

        if (!fechaSalida.isAfter(fechaIngreso)) {
            throw new IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de ingreso.");
        }

        boolean existeCruce = repository.existeReservaEnRango(
                habitacion.getId(),
                fechaIngreso.toString(),
                fechaSalida.toString()
        );

        if (existeCruce) {
            throw new IllegalArgumentException("Ya existe una reserva para esa habitación en el rango de fechas indicado.");
        }

        Reserva reserva = new Reserva(
                habitacion.getId(),
                habitacion.getNumero(),
                nombreHuesped.trim(),
                documentoHuesped.trim(),
                telefonoHuesped.trim(),
                fechaIngreso.toString(),
                fechaSalida.toString()
        );

        repository.guardar(reserva);
    }

    public List<Reserva> listarReservas() {
        return repository.listar();
    }
}