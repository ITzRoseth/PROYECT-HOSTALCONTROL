/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Model.CuentaEstadia;
import Model.EstadoEstadia;
import Model.EstadoHabitacion;
import Model.Estadia;
import Model.MetodoPago;
import Model.Pago;
import Repository.EstadiaRepository;
import Repository.PagoRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PagoService {

    private final PagoRepository pagoRepository;
    private final EstadiaRepository estadiaRepository;
    private final HabitacionService habitacionService;
    private final CuentaEstadiaService cuentaEstadiaService;

    public PagoService() {
        this.pagoRepository = new PagoRepository();
        this.estadiaRepository = new EstadiaRepository();
        this.habitacionService = new HabitacionService();
        this.cuentaEstadiaService = new CuentaEstadiaService();
    }

    public void registrarPago(Estadia estadia, String fechaSalida, MetodoPago metodoPago) {

        if (estadia == null) {
            throw new IllegalArgumentException("Debe seleccionar una estadía activa.");
        }

        if (estadia.getEstado() != EstadoEstadia.ACTIVA) {
            throw new IllegalArgumentException("Solo se puede registrar el pago de una estadía activa.");
        }

        if (metodoPago == null) {
            throw new IllegalArgumentException("Debe seleccionar un método de pago.");
        }

        CuentaEstadia cuenta = cuentaEstadiaService.calcularCuenta(estadia, fechaSalida);

        String fechaPago = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Pago pago = new Pago(
                estadia.getId(),
                estadia.getHabitacionId(),
                estadia.getNumeroHabitacion(),
                estadia.getNombreHuesped(),
                estadia.getDocumentoHuesped(),
                estadia.getFechaHoraIngreso(),
                cuenta.getFechaSalida(),
                cuenta.getDiasHospedaje(),
                cuenta.getSubtotalHabitacion(),
                cuenta.getTotalServicios(),
                cuenta.getTotalPagar(),
                metodoPago,
                fechaPago
        );

        pagoRepository.guardar(pago);

        estadiaRepository.actualizarEstadoYSalida(
                estadia.getId(),
                EstadoEstadia.PAGADA,
                cuenta.getFechaSalida(),
                cuenta.getTotalPagar()
        );

        habitacionService.actualizarEstadoHabitacion(
                estadia.getHabitacionId(),
                EstadoHabitacion.LIMPIEZA
        );
    }

    public List<Pago> listarPagos() {
        return pagoRepository.listar();
    }
}