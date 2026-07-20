/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Model.EstadoEstadia;
import Model.Estadia;
import Model.ServicioAdicional;
import Repository.ServicioAdicionalRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ServicioAdicionalService {

    private final ServicioAdicionalRepository repository;

    public ServicioAdicionalService() {
        this.repository = new ServicioAdicionalRepository();
    }

    public void registrarServicio(Estadia estadia, String nombreServicio,
                                  int cantidad, double valorUnitario) {

        if (estadia == null) {
            throw new IllegalArgumentException("Debe seleccionar una estadía activa.");
        }

        if (estadia.getEstado() != EstadoEstadia.ACTIVA) {
            throw new IllegalArgumentException("Solo se pueden agregar servicios a estadías activas.");
        }

        if (nombreServicio == null || nombreServicio.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del servicio es obligatorio.");
        }

        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }

        if (valorUnitario < 0) {
            throw new IllegalArgumentException("El valor del servicio debe ser mayor o igual a cero.");
        }

        double subtotal = cantidad * valorUnitario;

        String fechaRegistro = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        ServicioAdicional servicio = new ServicioAdicional(
                estadia.getId(),
                estadia.getHabitacionId(),
                estadia.getNumeroHabitacion(),
                nombreServicio.trim(),
                cantidad,
                valorUnitario,
                subtotal,
                fechaRegistro
        );

        repository.guardar(servicio);
    }

    public List<ServicioAdicional> listarServicios() {
        return repository.listar();
    }

    public List<ServicioAdicional> listarServiciosPorEstadia(String estadiaId) {
        return repository.listarPorEstadia(estadiaId);
    }
}