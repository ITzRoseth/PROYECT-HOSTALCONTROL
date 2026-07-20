/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.Huesped;
import Services.HuespedService;

import javax.swing.JButton;
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

public class BusquedaHuespedPanel extends JPanel {

    private JTextField txtDocumentoBuscar;
    private JTextField txtNombre;
    private JTextField txtDocumento;
    private JTextField txtTelefono;

    private JTable tablaHuespedes;
    private DefaultTableModel modeloTabla;

    private HuespedService huespedService;

    public BusquedaHuespedPanel() {
        huespedService = new HuespedService();

        setLayout(new BorderLayout(10, 10));

        crearFormulario();
        crearTabla();

        cargarHuespedes();
    }

    private void crearFormulario() {
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));

        JLabel lblDocumentoBuscar = new JLabel("Buscar por cédula/pasaporte:");
        txtDocumentoBuscar = new JTextField();

        JButton btnBuscar = new JButton("Buscar huésped");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnActualizar = new JButton("Actualizar listado");

        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();
        txtNombre.setEditable(false);

        JLabel lblDocumento = new JLabel("Documento:");
        txtDocumento = new JTextField();
        txtDocumento.setEditable(false);

        JLabel lblTelefono = new JLabel("Teléfono:");
        txtTelefono = new JTextField();
        txtTelefono.setEditable(false);

        btnBuscar.addActionListener(e -> buscarHuesped());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnActualizar.addActionListener(e -> cargarHuespedes());

        panelFormulario.add(lblDocumentoBuscar);
        panelFormulario.add(txtDocumentoBuscar);

        panelFormulario.add(btnBuscar);
        panelFormulario.add(btnLimpiar);

        panelFormulario.add(lblNombre);
        panelFormulario.add(txtNombre);

        panelFormulario.add(lblDocumento);
        panelFormulario.add(txtDocumento);

        panelFormulario.add(lblTelefono);
        panelFormulario.add(txtTelefono);

        panelFormulario.add(btnActualizar);
        panelFormulario.add(new JLabel(""));

        add(panelFormulario, BorderLayout.NORTH);
    }

    private void crearTabla() {
        modeloTabla = new DefaultTableModel();

        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Documento");
        modeloTabla.addColumn("Teléfono");
        modeloTabla.addColumn("Fecha registro");

        tablaHuespedes = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaHuespedes);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void buscarHuesped() {
        try {
            Huesped huesped = huespedService.buscarHuespedPorDocumento(
                    txtDocumentoBuscar.getText()
            );

            if (huesped == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "No se encontraron resultados para el documento ingresado.",
                        "Sin resultados",
                        JOptionPane.INFORMATION_MESSAGE
                );

                txtNombre.setText("");
                txtDocumento.setText("");
                txtTelefono.setText("");
                return;
            }

            txtNombre.setText(huesped.getNombre());
            txtDocumento.setText(huesped.getDocumento());
            txtTelefono.setText(huesped.getTelefono());

            JOptionPane.showMessageDialog(
                    this,
                    "Huésped encontrado correctamente.",
                    "Resultado encontrado",
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

    private void cargarHuespedes() {
        modeloTabla.setRowCount(0);

        List<Huesped> huespedes = huespedService.listarHuespedes();

        for (Huesped h : huespedes) {
            Object[] fila = {
                    h.getNombre(),
                    h.getDocumento(),
                    h.getTelefono(),
                    h.getFechaRegistro()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void limpiarCampos() {
        txtDocumentoBuscar.setText("");
        txtNombre.setText("");
        txtDocumento.setText("");
        txtTelefono.setText("");
        txtDocumentoBuscar.requestFocus();
    }
}