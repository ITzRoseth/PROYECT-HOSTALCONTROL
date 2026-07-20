/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;


import Model.Huesped;
import Repository.HuespedRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HuespedService {

    private final HuespedRepository repository;

    public HuespedService() {
        this.repository = new HuespedRepository();
    }

    public void registrarOActualizarHuesped(String nombre, String documento, String telefono) {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del huésped es obligatorio.");
        }

        if (documento == null || documento.trim().isEmpty()) {
            throw new IllegalArgumentException("La cédula o pasaporte del huésped es obligatorio.");
        }

        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono del huésped es obligatorio.");
        }

        String fechaRegistro = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Huesped huesped = new Huesped(
                nombre.trim(),
                documento.trim(),
                telefono.trim(),
                fechaRegistro
        );

        repository.guardarOActualizar(huesped);
    }

    public Huesped buscarHuespedPorDocumento(String documento) {

        if (documento == null || documento.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar una cédula o pasaporte.");
        }

        return repository.buscarPorDocumento(documento.trim());
    }

    public List<Huesped> listarHuespedes() {
        return repository.listar();
    }
}