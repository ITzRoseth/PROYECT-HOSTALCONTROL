
package Services;

import Model.EstadoHabitacion;
import Model.Habitacion;
import Repository.HabitacionRepository;
import java.util.List;

public class HabitacionService {

    private final HabitacionRepository repository;

    public HabitacionService() {
        this.repository = new HabitacionRepository();
    }

    public void registrarHabitacion(int numero, String tipo, double tarifa, EstadoHabitacion estado) {

        if (numero <= 0) {
            throw new IllegalArgumentException("El número de habitación debe ser mayor a cero.");
        }

        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de habitación es obligatorio.");
        }

        if (tarifa <= 0) {
            throw new IllegalArgumentException("La tarifa debe ser mayor a cero.");
        }

        if (estado == null) {
            throw new IllegalArgumentException("El estado de la habitación es obligatorio.");
        }

        if (repository.existeNumero(numero)) {
            throw new IllegalArgumentException("Ya existe una habitación con ese número.");
        }

        Habitacion habitacion = new Habitacion(numero, tipo.trim(), tarifa, estado);
        repository.guardar(habitacion);
    }

    public List<Habitacion> listarHabitaciones() {
        return repository.listar();
    }
    public void actualizarEstadoHabitacion(String idHabitacion, EstadoHabitacion nuevoEstado) {

    if (idHabitacion == null || idHabitacion.trim().isEmpty()) {
        throw new IllegalArgumentException("El ID de la habitación es obligatorio.");
    }

    if (nuevoEstado == null) {
        throw new IllegalArgumentException("El nuevo estado de la habitación es obligatorio.");
    }

    repository.actualizarEstado(idHabitacion, nuevoEstado);
}
}