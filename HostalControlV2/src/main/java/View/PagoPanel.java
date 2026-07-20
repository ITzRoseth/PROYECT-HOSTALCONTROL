/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.EstadoEstadia;
import Model.Estadia;
import Model.MetodoPago;
import Model.Pago;
import Services.CheckInService;
import Services.PagoService;

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

public class PagoPanel extends JPanel {

    private JComboBox<Estadia> cmbEstadias;
    private JTextField txtFechaSalida;
    private JComboBox<MetodoPago> cmbMetodoPago;

    private JTable tablaPagos;
    private DefaultTableModel modeloTabla;

    private CheckInService checkInService;
    private PagoService pagoService;

    public PagoPanel() {
        checkInService = new CheckInService();
        pagoService = new PagoService();

        setLayout(new BorderLayout(10, 10));

        crearFormulario();
        crearTabla();

        cargarEstadiasActivas();
        cargarPagos();
    }

    private void crearFormulario() {
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel lblEstadia = new JLabel("Estadía activa:");
        cmbEstadias = new JComboBox<>();

        JLabel lblFechaSalida = new JLabel("Fecha salida (yyyy-MM-dd):");
        txtFechaSalida = new JTextField(LocalDate.now().toString());

        JLabel lblMetodoPago = new JLabel("Método de pago:");
        cmbMetodoPago = new JComboBox<>(MetodoPago.values());

        JButton btnRegistrarPago = new JButton("Registrar pago");
        JButton btnActualizar = new JButton("Actualizar estadías");

        btnRegistrarPago.addActionListener(e -> registrarPago());
        btnActualizar.addActionListener(e -> cargarEstadiasActivas());

        panelFormulario.add(lblEstadia);
        panelFormulario.add(cmbEstadias);

        panelFormulario.add(lblFechaSalida);
        panelFormulario.add(txtFechaSalida);

        panelFormulario.add(lblMetodoPago);
        panelFormulario.add(cmbMetodoPago);

        panelFormulario.add(btnRegistrarPago);
        panelFormulario.add(btnActualizar);

        add(panelFormulario, BorderLayout.NORTH);
    }

    private void crearTabla() {
        modeloTabla = new DefaultTableModel();

        modeloTabla.addColumn("Habitación");
        modeloTabla.addColumn("Huésped");
        modeloTabla.addColumn("Documento");
        modeloTabla.addColumn("Salida");
        modeloTabla.addColumn("Días");
        modeloTabla.addColumn("Habitación $");
        modeloTabla.addColumn("Servicios $");
        modeloTabla.addColumn("Total pagado");
        modeloTabla.addColumn("Método");
        modeloTabla.addColumn("Fecha pago");

        tablaPagos = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaPagos);
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

    private void registrarPago() {
        try {
            Estadia estadiaSeleccionada = (Estadia) cmbEstadias.getSelectedItem();
            MetodoPago metodoPago = (MetodoPago) cmbMetodoPago.getSelectedItem();

            pagoService.registrarPago(
                    estadiaSeleccionada,
                    txtFechaSalida.getText(),
                    metodoPago
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Pago registrado correctamente. La estadía fue marcada como pagada y la habitación pasó a limpieza.",
                    "Pago exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            cargarEstadiasActivas();
            cargarPagos();

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

    private void cargarPagos() {
        modeloTabla.setRowCount(0);

        List<Pago> pagos = pagoService.listarPagos();

        for (Pago p : pagos) {
            Object[] fila = {
                    p.getNumeroHabitacion(),
                    p.getNombreHuesped(),
                    p.getDocumentoHuesped(),
                    p.getFechaSalida(),
                    p.getDiasHospedaje(),
                    p.getSubtotalHabitacion(),
                    p.getTotalServicios(),
                    p.getTotalPagado(),
                    p.getMetodoPago(),
                    p.getFechaPago()
            };

            modeloTabla.addRow(fila);
        }
    }
}