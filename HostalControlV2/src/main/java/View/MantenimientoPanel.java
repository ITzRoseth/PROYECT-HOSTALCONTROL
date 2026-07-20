/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;


import Model.Habitacion;
import Model.MantenimientoHabitacion;
import Services.HabitacionService;
import Services.MantenimientoService;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

public class MantenimientoPanel extends JPanel {

    private JComboBox<Habitacion> cmbHabitaciones;
    private JTextArea txtObservacion;

    private JTable tablaMantenimientos;
    private DefaultTableModel modeloTabla;

    private HabitacionService habitacionService;
    private MantenimientoService mantenimientoService;

    public MantenimientoPanel() {
        habitacionService = new HabitacionService();
        mantenimientoService = new MantenimientoService();

        setLayout(new BorderLayout(10, 10));

        crearFormulario();
        crearTabla();

        cargarHabitaciones();
        cargarMantenimientos();
    }

    private void crearFormulario() {
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel lblHabitacion = new JLabel("Habitación:");
        cmbHabitaciones = new JComboBox<>();

        JLabel lblObservacion = new JLabel("Observación:");
        txtObservacion = new JTextArea(3, 20);

        JButton btnEnviarMantenimiento = new JButton("Enviar a mantenimiento");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnActualizar = new JButton("Actualizar habitaciones");

        btnEnviarMantenimiento.addActionListener(e -> enviarAMantenimiento());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnActualizar.addActionListener(e -> cargarHabitaciones());

        panelFormulario.add(lblHabitacion);
        panelFormulario.add(cmbHabitaciones);

        panelFormulario.add(lblObservacion);
        panelFormulario.add(new JScrollPane(txtObservacion));

        panelFormulario.add(btnEnviarMantenimiento);
        panelFormulario.add(btnLimpiar);

        panelFormulario.add(btnActualizar);
        panelFormulario.add(new JLabel(""));

        add(panelFormulario, BorderLayout.NORTH);
    }

    private void crearTabla() {
        modeloTabla = new DefaultTableModel();

        modeloTabla.addColumn("Habitación");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Estado anterior");
        modeloTabla.addColumn("Observación");
        modeloTabla.addColumn("Fecha registro");

        tablaMantenimientos = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaMantenimientos);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarHabitaciones() {
        cmbHabitaciones.removeAllItems();

        List<Habitacion> habitaciones = habitacionService.listarHabitaciones();

        for (Habitacion h : habitaciones) {
            cmbHabitaciones.addItem(h);
        }
    }

    private void enviarAMantenimiento() {
        try {
            Habitacion habitacionSeleccionada = (Habitacion) cmbHabitaciones.getSelectedItem();

            mantenimientoService.enviarAMantenimiento(
                    habitacionSeleccionada,
                    txtObservacion.getText()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "La habitación fue enviada a mantenimiento correctamente.",
                    "Mantenimiento registrado",
                    JOptionPane.INFORMATION_MESSAGE
            );

            limpiarCampos();
            cargarHabitaciones();
            cargarMantenimientos();

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

    private void cargarMantenimientos() {
        modeloTabla.setRowCount(0);

        List<MantenimientoHabitacion> mantenimientos = mantenimientoService.listarMantenimientos();

        for (MantenimientoHabitacion m : mantenimientos) {
            Object[] fila = {
                    m.getNumeroHabitacion(),
                    m.getTipoHabitacion(),
                    m.getEstadoAnterior(),
                    m.getObservacion(),
                    m.getFechaRegistro()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void limpiarCampos() {
        txtObservacion.setText("");

        if (cmbHabitaciones.getItemCount() > 0) {
            cmbHabitaciones.setSelectedIndex(0);
        }

        txtObservacion.requestFocus();
    }
}