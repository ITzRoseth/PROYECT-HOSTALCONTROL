/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Model.CuentaEstadia;
import Model.EstadoEstadia;
import Model.Estadia;
import Model.Habitacion;
import Model.ServicioAdicional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CuentaEstadiaService {

    private final HabitacionService habitacionService;
    private final ServicioAdicionalService servicioAdicionalService;

    public CuentaEstadiaService() {
        this.habitacionService = new HabitacionService();
        this.servicioAdicionalService = new ServicioAdicionalService();
    }

    public CuentaEstadia calcularCuenta(Estadia estadia, String fechaSalidaTexto) {

        if (estadia == null) {
            throw new IllegalArgumentException("Debe seleccionar una estadía activa.");
        }

        if (estadia.getEstado() != EstadoEstadia.ACTIVA) {
            throw new IllegalArgumentException("Solo se puede calcular la cuenta de una estadía activa.");
        }

        if (fechaSalidaTexto == null || fechaSalidaTexto.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de salida es obligatoria.");
        }

        LocalDate fechaSalida;

        try {
            fechaSalida = LocalDate.parse(fechaSalidaTexto.trim());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("La fecha de salida debe tener el formato yyyy-MM-dd.");
        }

        DateTimeFormatter formatoIngreso = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fechaHoraIngreso = LocalDateTime.parse(estadia.getFechaHoraIngreso(), formatoIngreso);

        LocalDate fechaIngreso = fechaHoraIngreso.toLocalDate();

        if (fechaSalida.isBefore(fechaIngreso)) {
            throw new IllegalArgumentException("La fecha de salida no puede ser anterior a la fecha de ingreso.");
        }

        long diasHospedaje = ChronoUnit.DAYS.between(fechaIngreso, fechaSalida);

        if (diasHospedaje <= 0) {
            diasHospedaje = 1;
        }

        Habitacion habitacion = habitacionService.buscarHabitacionPorId(estadia.getHabitacionId());

        double tarifaDiaria = habitacion.getTarifa();
        double subtotalHabitacion = diasHospedaje * tarifaDiaria;

        List<ServicioAdicional> servicios =
                servicioAdicionalService.listarServiciosPorEstadia(estadia.getId());

        double totalServicios = 0;

        for (ServicioAdicional servicio : servicios) {
            totalServicios += servicio.getSubtotal();
        }

        double totalPagar = subtotalHabitacion + totalServicios;

        return new CuentaEstadia(
                estadia.getId(),
                estadia.getNumeroHabitacion(),
                estadia.getNombreHuesped(),
                estadia.getFechaHoraIngreso(),
                fechaSalida.toString(),
                diasHospedaje,
                tarifaDiaria,
                subtotalHabitacion,
                totalServicios,
                totalPagar
        );
    }
}