/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.EstadoHabitacion;
import Model.Estadia;
import Model.Habitacion;
import Services.CheckInService;
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

public class CheckInPanel extends JPanel {

    private JComboBox<Habitacion> cmbHabitaciones;
    private JTextField txtNombreHuesped;
    private JTextField txtDocumentoHuesped;
    private JTextField txtTelefonoHuesped;

    private JTable tablaEstadias;
    private DefaultTableModel modeloTabla;

    private HabitacionService habitacionService;
    private CheckInService checkInService;

    public CheckInPanel() {
        habitacionService = new HabitacionService();
        checkInService = new CheckInService();

        setLayout(new BorderLayout(10, 10));

        crearFormulario();
        crearTabla();

        cargarHabitacionesDisponibles();
        cargarEstadias();
    }

    private void crearFormulario() {
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));

        JLabel lblHabitacion = new JLabel("Habitación disponible:");
        cmbHabitaciones = new JComboBox<>();

        JLabel lblNombre = new JLabel("Nombre huésped:");
        txtNombreHuesped = new JTextField();

        JLabel lblDocumento = new JLabel("Cédula/Pasaporte:");
        txtDocumentoHuesped = new JTextField();

        JLabel lblTelefono = new JLabel("Teléfono:");
        txtTelefonoHuesped = new JTextField();

        JButton btnCheckIn = new JButton("Registrar check-in");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnActualizar = new JButton("Actualizar habitaciones");

        btnCheckIn.addActionListener(e -> registrarCheckIn());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnActualizar.addActionListener(e -> cargarHabitacionesDisponibles());

        panelFormulario.add(lblHabitacion);
        panelFormulario.add(cmbHabitaciones);

        panelFormulario.add(lblNombre);
        panelFormulario.add(txtNombreHuesped);

        panelFormulario.add(lblDocumento);
        panelFormulario.add(txtDocumentoHuesped);

        panelFormulario.add(lblTelefono);
        panelFormulario.add(txtTelefonoHuesped);

        panelFormulario.add(btnCheckIn);
        panelFormulario.add(btnLimpiar);

        add(panelFormulario, BorderLayout.NORTH);
        add(btnActualizar, BorderLayout.SOUTH);
    }

    private void crearTabla() {
        modeloTabla = new DefaultTableModel();

        modeloTabla.addColumn("Habitación");
        modeloTabla.addColumn("Huésped");
        modeloTabla.addColumn("Documento");
        modeloTabla.addColumn("Teléfono");
        modeloTabla.addColumn("Ingreso");
        modeloTabla.addColumn("Estado");

        tablaEstadias = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaEstadias);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarHabitacionesDisponibles() {
        cmbHabitaciones.removeAllItems();

        List<Habitacion> habitaciones = habitacionService.listarHabitaciones();

        for (Habitacion h : habitaciones) {
            if (h.getEstado() == EstadoHabitacion.DISPONIBLE) {
                cmbHabitaciones.addItem(h);
            }
        }
    }

    private void registrarCheckIn() {
        try {
            Habitacion habitacionSeleccionada = (Habitacion) cmbHabitaciones.getSelectedItem();

            checkInService.registrarCheckIn(
                    habitacionSeleccionada,
                    txtNombreHuesped.getText(),
                    txtDocumentoHuesped.getText(),
                    txtTelefonoHuesped.getText()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Check-in registrado correctamente. La habitación ahora está ocupada.",
                    "Check-in exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            limpiarCampos();
            cargarHabitacionesDisponibles();
            cargarEstadias();

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

    private void cargarEstadias() {
        modeloTabla.setRowCount(0);

        List<Estadia> estadias = checkInService.listarEstadias();

        for (Estadia e : estadias) {
            Object[] fila = {
                    e.getNumeroHabitacion(),
                    e.getNombreHuesped(),
                    e.getDocumentoHuesped(),
                    e.getTelefonoHuesped(),
                    e.getFechaHoraIngreso(),
                    e.getEstado()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void limpiarCampos() {
        txtNombreHuesped.setText("");
        txtDocumentoHuesped.setText("");
        txtTelefonoHuesped.setText("");

        if (cmbHabitaciones.getItemCount() > 0) {
            cmbHabitaciones.setSelectedIndex(0);
        }

        txtNombreHuesped.requestFocus();
    }
}