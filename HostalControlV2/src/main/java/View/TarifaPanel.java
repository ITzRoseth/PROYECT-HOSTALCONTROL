/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;


import Model.Habitacion;
import Model.TemporadaTarifa;
import Services.HabitacionService;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

public class TarifaPanel extends JPanel {

    private JComboBox<Habitacion> cmbHabitaciones;
    private JTextField txtTarifaActual;
    private JTextField txtNuevaTarifa;
    private JComboBox<TemporadaTarifa> cmbTemporada;

    private JTable tablaHabitaciones;
    private DefaultTableModel modeloTabla;

    private HabitacionService habitacionService;

    public TarifaPanel() {
        habitacionService = new HabitacionService();

        setLayout(new BorderLayout(10, 10));

        crearFormulario();
        crearTabla();

        cargarHabitaciones();
    }

    private void crearFormulario() {
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));

        JLabel lblHabitacion = new JLabel("Habitación:");
        cmbHabitaciones = new JComboBox<>();

        JLabel lblTarifaActual = new JLabel("Tarifa actual:");
        txtTarifaActual = new JTextField();
        txtTarifaActual.setEditable(false);

        JLabel lblNuevaTarifa = new JLabel("Nueva tarifa:");
        txtNuevaTarifa = new JTextField();

        JLabel lblTemporada = new JLabel("Temporada:");
        cmbTemporada = new JComboBox<>(TemporadaTarifa.values());

        JButton btnActualizarTarifa = new JButton("Actualizar tarifa");
        JButton btnRecargar = new JButton("Recargar habitaciones");

        cmbHabitaciones.addActionListener(e -> mostrarDatosHabitacion());
        btnActualizarTarifa.addActionListener(e -> actualizarTarifa());
        btnRecargar.addActionListener(e -> cargarHabitaciones());

        panelFormulario.add(lblHabitacion);
        panelFormulario.add(cmbHabitaciones);

        panelFormulario.add(lblTarifaActual);
        panelFormulario.add(txtTarifaActual);

        panelFormulario.add(lblNuevaTarifa);
        panelFormulario.add(txtNuevaTarifa);

        panelFormulario.add(lblTemporada);
        panelFormulario.add(cmbTemporada);

        panelFormulario.add(btnActualizarTarifa);
        panelFormulario.add(btnRecargar);

        add(panelFormulario, BorderLayout.NORTH);
    }

    private void crearTabla() {
        modeloTabla = new DefaultTableModel();

        modeloTabla.addColumn("Número");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Estado");
        modeloTabla.addColumn("Tarifa");
        modeloTabla.addColumn("Temporada");

        tablaHabitaciones = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaHabitaciones);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarHabitaciones() {
        cmbHabitaciones.removeAllItems();
        modeloTabla.setRowCount(0);

        List<Habitacion> habitaciones = habitacionService.listarHabitaciones();

        for (Habitacion h : habitaciones) {
            cmbHabitaciones.addItem(h);

            Object[] fila = {
                    h.getNumero(),
                    h.getTipo(),
                    h.getEstado(),
                    h.getTarifa(),
                    h.getTemporadaTarifa()
            };

            modeloTabla.addRow(fila);
        }

        mostrarDatosHabitacion();
    }

    private void mostrarDatosHabitacion() {
        Habitacion habitacion = (Habitacion) cmbHabitaciones.getSelectedItem();

        if (habitacion == null) {
            txtTarifaActual.setText("");
            txtNuevaTarifa.setText("");
            cmbTemporada.setSelectedIndex(0);
            return;
        }

        txtTarifaActual.setText(String.valueOf(habitacion.getTarifa()));
        txtNuevaTarifa.setText(String.valueOf(habitacion.getTarifa()));
        cmbTemporada.setSelectedItem(habitacion.getTemporadaTarifa());
    }

    private void actualizarTarifa() {
        try {
            Habitacion habitacionSeleccionada = (Habitacion) cmbHabitaciones.getSelectedItem();

            if (habitacionSeleccionada == null) {
                throw new IllegalArgumentException("Debe seleccionar una habitación.");
            }

            double nuevaTarifa = Double.parseDouble(txtNuevaTarifa.getText().trim());
            TemporadaTarifa temporada = (TemporadaTarifa) cmbTemporada.getSelectedItem();

            habitacionService.actualizarTarifaHabitacion(
                    habitacionSeleccionada.getId(),
                    nuevaTarifa,
                    temporada
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Tarifa actualizada correctamente.",
                    "Actualización exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

            cargarHabitaciones();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "La tarifa debe ser un valor numérico.",
                    "Error de formato",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error de validación",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ocurrió un error inesperado: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}