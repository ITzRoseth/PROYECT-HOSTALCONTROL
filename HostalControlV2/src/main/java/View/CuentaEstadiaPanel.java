/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;


import Model.CuentaEstadia;
import Model.EstadoEstadia;
import Model.Estadia;
import Model.ServicioAdicional;
import Services.CheckInService;
import Services.CuentaEstadiaService;
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
import java.time.LocalDate;
import java.util.List;

public class CuentaEstadiaPanel extends JPanel {

    private JComboBox<Estadia> cmbEstadias;
    private JTextField txtFechaSalida;

    private JLabel lblHabitacion;
    private JLabel lblHuesped;
    private JLabel lblIngreso;
    private JLabel lblSalida;
    private JLabel lblDias;
    private JLabel lblTarifa;
    private JLabel lblSubtotalHabitacion;
    private JLabel lblTotalServicios;
    private JLabel lblTotalPagar;

    private JTable tablaServicios;
    private DefaultTableModel modeloTabla;

    private CheckInService checkInService;
    private CuentaEstadiaService cuentaEstadiaService;
    private ServicioAdicionalService servicioAdicionalService;

    public CuentaEstadiaPanel() {
        checkInService = new CheckInService();
        cuentaEstadiaService = new CuentaEstadiaService();
        servicioAdicionalService = new ServicioAdicionalService();

        setLayout(new BorderLayout(10, 10));

        crearPanelSuperior();
        crearPanelResultado();
        crearTablaServicios();

        cargarEstadiasActivas();
    }

    private void crearPanelSuperior() {
        JPanel panelSuperior = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel lblEstadia = new JLabel("Estadía activa:");
        cmbEstadias = new JComboBox<>();

        JLabel lblFechaSalida = new JLabel("Fecha salida (yyyy-MM-dd):");
        txtFechaSalida = new JTextField(LocalDate.now().toString());

        JButton btnCalcular = new JButton("Calcular total");
        JButton btnActualizar = new JButton("Actualizar estadías");

        btnCalcular.addActionListener(e -> calcularCuenta());
        btnActualizar.addActionListener(e -> cargarEstadiasActivas());

        panelSuperior.add(lblEstadia);
        panelSuperior.add(cmbEstadias);

        panelSuperior.add(lblFechaSalida);
        panelSuperior.add(txtFechaSalida);

        panelSuperior.add(btnCalcular);
        panelSuperior.add(btnActualizar);

        add(panelSuperior, BorderLayout.NORTH);
    }

    private void crearPanelResultado() {
        JPanel panelResultado = new JPanel(new GridLayout(9, 1, 5, 5));

        lblHabitacion = new JLabel("Habitación: -");
        lblHuesped = new JLabel("Huésped: -");
        lblIngreso = new JLabel("Ingreso: -");
        lblSalida = new JLabel("Salida: -");
        lblDias = new JLabel("Días hospedaje: -");
        lblTarifa = new JLabel("Tarifa diaria: -");
        lblSubtotalHabitacion = new JLabel("Subtotal habitación: -");
        lblTotalServicios = new JLabel("Total servicios adicionales: -");
        lblTotalPagar = new JLabel("TOTAL A PAGAR: -");

        panelResultado.add(lblHabitacion);
        panelResultado.add(lblHuesped);
        panelResultado.add(lblIngreso);
        panelResultado.add(lblSalida);
        panelResultado.add(lblDias);
        panelResultado.add(lblTarifa);
        panelResultado.add(lblSubtotalHabitacion);
        panelResultado.add(lblTotalServicios);
        panelResultado.add(lblTotalPagar);

        add(panelResultado, BorderLayout.WEST);
    }

    private void crearTablaServicios() {
        modeloTabla = new DefaultTableModel();

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

    private void calcularCuenta() {
        try {
            Estadia estadiaSeleccionada = (Estadia) cmbEstadias.getSelectedItem();

            CuentaEstadia cuenta = cuentaEstadiaService.calcularCuenta(
                    estadiaSeleccionada,
                    txtFechaSalida.getText()
            );

            mostrarCuenta(cuenta);
            cargarServiciosDeEstadia(cuenta.getEstadiaId());

            JOptionPane.showMessageDialog(
                    this,
                    "Cuenta calculada correctamente.",
                    "Cálculo exitoso",
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

    private void mostrarCuenta(CuentaEstadia cuenta) {
        lblHabitacion.setText("Habitación: " + cuenta.getNumeroHabitacion());
        lblHuesped.setText("Huésped: " + cuenta.getNombreHuesped());
        lblIngreso.setText("Ingreso: " + cuenta.getFechaHoraIngreso());
        lblSalida.setText("Salida: " + cuenta.getFechaSalida());
        lblDias.setText("Días hospedaje: " + cuenta.getDiasHospedaje());
        lblTarifa.setText("Tarifa diaria: $" + cuenta.getTarifaDiaria());
        lblSubtotalHabitacion.setText("Subtotal habitación: $" + cuenta.getSubtotalHabitacion());
        lblTotalServicios.setText("Total servicios adicionales: $" + cuenta.getTotalServicios());
        lblTotalPagar.setText("TOTAL A PAGAR: $" + cuenta.getTotalPagar());
    }

    private void cargarServiciosDeEstadia(String estadiaId) {
        modeloTabla.setRowCount(0);

        List<ServicioAdicional> servicios =
                servicioAdicionalService.listarServiciosPorEstadia(estadiaId);

        for (ServicioAdicional s : servicios) {
            Object[] fila = {
                    s.getNombreServicio(),
                    s.getCantidad(),
                    s.getValorUnitario(),
                    s.getSubtotal(),
                    s.getFechaRegistro()
            };

            modeloTabla.addRow(fila);
        }
    }
}