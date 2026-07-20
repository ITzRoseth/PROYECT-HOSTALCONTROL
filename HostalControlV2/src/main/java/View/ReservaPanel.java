/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;


import Model.Habitacion;
import Model.Reserva;
import Services.HabitacionService;
import Services.ReservaService;
import Model.Huesped;
import Services.HuespedService;
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

public class ReservaPanel extends JPanel {

    private JComboBox<Habitacion> cmbHabitaciones;
    private JTextField txtNombreHuesped;
    private JTextField txtDocumentoHuesped;
    private JTextField txtTelefonoHuesped;
    private JTextField txtFechaIngreso;
    private JTextField txtFechaSalida;

    private JTable tablaReservas;
    private DefaultTableModel modeloTabla;

    private HabitacionService habitacionService;
    private ReservaService reservaService;
    private HuespedService huespedService;
    
    private void buscarHuesped() {
    try {
        Huesped huesped = huespedService.buscarHuespedPorDocumento(
                txtDocumentoHuesped.getText()
        );

        if (huesped == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se encontró un huésped con ese documento.",
                    "Sin resultados",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        txtNombreHuesped.setText(huesped.getNombre());
        txtTelefonoHuesped.setText(huesped.getTelefono());

        JOptionPane.showMessageDialog(
                this,
                "Datos del huésped recuperados correctamente.",
                "Huésped encontrado",
                JOptionPane.INFORMATION_MESSAGE
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

    public ReservaPanel() {
        habitacionService = new HabitacionService();
        reservaService = new ReservaService();
        huespedService = new HuespedService();

        setLayout(new BorderLayout(10, 10));

        crearFormulario();
        crearTabla();

        cargarHabitaciones();
        cargarReservas();
        
    }

    private void crearFormulario() {
        JPanel panelFormulario = new JPanel(new GridLayout(8, 2, 10, 10));

        JLabel lblHabitacion = new JLabel("Habitación:");
        cmbHabitaciones = new JComboBox<>();

        JLabel lblNombre = new JLabel("Nombre huésped:");
        txtNombreHuesped = new JTextField();

        JLabel lblDocumento = new JLabel("Cédula/Pasaporte:");
        txtDocumentoHuesped = new JTextField();

        JLabel lblTelefono = new JLabel("Teléfono:");
        txtTelefonoHuesped = new JTextField();

        JLabel lblFechaIngreso = new JLabel("Fecha ingreso (yyyy-MM-dd):");
        txtFechaIngreso = new JTextField();

        JLabel lblFechaSalida = new JLabel("Fecha salida (yyyy-MM-dd):");
        txtFechaSalida = new JTextField();

        JButton btnGuardar = new JButton("Registrar reserva");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnActualizar = new JButton("Actualizar habitaciones");
        JButton btnBuscarHuesped = new JButton("Buscar huésped");
        btnBuscarHuesped.addActionListener(e -> buscarHuesped());

        btnGuardar.addActionListener(e -> guardarReserva());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnActualizar.addActionListener(e -> cargarHabitaciones());

        panelFormulario.add(lblHabitacion);
        panelFormulario.add(cmbHabitaciones);

        panelFormulario.add(lblNombre);
        panelFormulario.add(txtNombreHuesped);

        panelFormulario.add(lblDocumento);
        panelFormulario.add(txtDocumentoHuesped);
        
        panelFormulario.add(new JLabel("Recuperar datos:"));
        panelFormulario.add(btnBuscarHuesped);

        panelFormulario.add(lblTelefono);
        panelFormulario.add(txtTelefonoHuesped);

        panelFormulario.add(lblFechaIngreso);
        panelFormulario.add(txtFechaIngreso);

        panelFormulario.add(lblFechaSalida);
        panelFormulario.add(txtFechaSalida);

        panelFormulario.add(btnGuardar);
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
        modeloTabla.addColumn("Salida");

        tablaReservas = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaReservas);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarHabitaciones() {
        cmbHabitaciones.removeAllItems();

        List<Habitacion> habitaciones = habitacionService.listarHabitaciones();

        for (Habitacion h : habitaciones) {
            cmbHabitaciones.addItem(h);
        }
    }

    private void guardarReserva() {
        try {
            Habitacion habitacionSeleccionada = (Habitacion) cmbHabitaciones.getSelectedItem();

            reservaService.registrarReserva(
                    habitacionSeleccionada,
                    txtNombreHuesped.getText(),
                    txtDocumentoHuesped.getText(),
                    txtTelefonoHuesped.getText(),
                    txtFechaIngreso.getText(),
                    txtFechaSalida.getText()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Reserva registrada correctamente.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            limpiarCampos();
            cargarReservas();

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

    private void cargarReservas() {
        modeloTabla.setRowCount(0);

        List<Reserva> reservas = reservaService.listarReservas();

        for (Reserva r : reservas) {
            Object[] fila = {
                    r.getNumeroHabitacion(),
                    r.getNombreHuesped(),
                    r.getDocumentoHuesped(),
                    r.getTelefonoHuesped(),
                    r.getFechaIngreso(),
                    r.getFechaSalida()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void limpiarCampos() {
        txtNombreHuesped.setText("");
        txtDocumentoHuesped.setText("");
        txtTelefonoHuesped.setText("");
        txtFechaIngreso.setText("");
        txtFechaSalida.setText("");

        if (cmbHabitaciones.getItemCount() > 0) {
            cmbHabitaciones.setSelectedIndex(0);
        }

        txtNombreHuesped.requestFocus();
    }
}