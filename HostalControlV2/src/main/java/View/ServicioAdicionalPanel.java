/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.EstadoEstadia;
import Model.Estadia;
import Model.ServicioAdicional;
import Services.CheckInService;
import Services.ServicioAdicionalService;

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

public class ServicioAdicionalPanel extends JPanel {

    private JComboBox<Estadia> cmbEstadias;
    private JTextField txtNombreServicio;
    private JTextField txtCantidad;
    private JTextField txtValorUnitario;

    private JTable tablaServicios;
    private DefaultTableModel modeloTabla;

    private CheckInService checkInService;
    private ServicioAdicionalService servicioAdicionalService;

    public ServicioAdicionalPanel() {
        checkInService = new CheckInService();
        servicioAdicionalService = new ServicioAdicionalService();

        setLayout(new BorderLayout(10, 10));

        crearFormulario();
        crearTabla();

        cargarEstadiasActivas();
        cargarServicios();
    }

    private void crearFormulario() {
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));

        JLabel lblEstadia = new JLabel("Estadía activa:");
        cmbEstadias = new JComboBox<>();

        JLabel lblNombreServicio = new JLabel("Servicio:");
        txtNombreServicio = new JTextField();

        JLabel lblCantidad = new JLabel("Cantidad:");
        txtCantidad = new JTextField();

        JLabel lblValorUnitario = new JLabel("Valor unitario:");
        txtValorUnitario = new JTextField();

        JButton btnGuardar = new JButton("Agregar servicio");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnActualizar = new JButton("Actualizar estadías");
        JButton btnVerServicios = new JButton("Ver servicios de estadía");

        btnGuardar.addActionListener(e -> guardarServicio());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnActualizar.addActionListener(e -> cargarEstadiasActivas());
        btnVerServicios.addActionListener(e -> cargarServiciosPorEstadia());

        panelFormulario.add(lblEstadia);
        panelFormulario.add(cmbEstadias);

        panelFormulario.add(lblNombreServicio);
        panelFormulario.add(txtNombreServicio);

        panelFormulario.add(lblCantidad);
        panelFormulario.add(txtCantidad);

        panelFormulario.add(lblValorUnitario);
        panelFormulario.add(txtValorUnitario);

        panelFormulario.add(btnGuardar);
        panelFormulario.add(btnLimpiar);

        panelFormulario.add(btnActualizar);
        panelFormulario.add(btnVerServicios);

        add(panelFormulario, BorderLayout.NORTH);
    }

    private void crearTabla() {
        modeloTabla = new DefaultTableModel();

        modeloTabla.addColumn("Habitación");
        modeloTabla.addColumn("Servicio");
        modeloTabla.addColumn("Cantidad");
        modeloTabla.addColumn("Valor unitario");
        modeloTabla.addColumn("Subtotal");
        modeloTabla.addColumn("Fecha registro");

        tablaServicios = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaServicios);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarEstadiasActivas() {
        cmbEstadias.removeAllItems();

        List<Estadia> estadias = checkInService.listarEstadias();

        for (Estadia e : estadias) {
            if (e.getEstado() == EstadoEstadia.ACTIVA) {
                cmbEstadias.addItem(e);
            }
        }
    }

    private void guardarServicio() {
        try {
            Estadia estadiaSeleccionada = (Estadia) cmbEstadias.getSelectedItem();

            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            double valorUnitario = Double.parseDouble(txtValorUnitario.getText().trim());

            servicioAdicionalService.registrarServicio(
                    estadiaSeleccionada,
                    txtNombreServicio.getText(),
                    cantidad,
                    valorUnitario
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Servicio adicional registrado correctamente.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            limpiarCampos();
            cargarServicios();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "La cantidad y el valor unitario deben ser numéricos.",
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

    private void cargarServicios() {
        modeloTabla.setRowCount(0);

        List<ServicioAdicional> servicios = servicioAdicionalService.listarServicios();

        for (ServicioAdicional s : servicios) {
            Object[] fila = {
                    s.getNumeroHabitacion(),
                    s.getNombreServicio(),
                    s.getCantidad(),
                    s.getValorUnitario(),
                    s.getSubtotal(),
                    s.getFechaRegistro()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void cargarServiciosPorEstadia() {
        modeloTabla.setRowCount(0);

        Estadia estadiaSeleccionada = (Estadia) cmbEstadias.getSelectedItem();

        if (estadiaSeleccionada == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar una estadía activa.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        List<ServicioAdicional> servicios =
                servicioAdicionalService.listarServiciosPorEstadia(estadiaSeleccionada.getId());

        for (ServicioAdicional s : servicios) {
            Object[] fila = {
                    s.getNumeroHabitacion(),
                    s.getNombreServicio(),
                    s.getCantidad(),
                    s.getValorUnitario(),
                    s.getSubtotal(),
                    s.getFechaRegistro()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void limpiarCampos() {
        txtNombreServicio.setText("");
        txtCantidad.setText("");
        txtValorUnitario.setText("");

        if (cmbEstadias.getItemCount() > 0) {
            cmbEstadias.setSelectedIndex(0);
        }

        txtNombreServicio.requestFocus();
    }
}