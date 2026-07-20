
package View;

import Model.EstadoHabitacion;
import Model.Habitacion;
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

public class HabitacionPanel extends JPanel {

    private JTextField txtNumero;
    private JTextField txtTipo;
    private JTextField txtTarifa;
    private JComboBox<EstadoHabitacion> cmbEstado;
    private JTable tablaHabitaciones;
    private DefaultTableModel modeloTabla;

    private HabitacionService habitacionService;

    public HabitacionPanel() {
        habitacionService = new HabitacionService();

        setLayout(new BorderLayout(10, 10));

        crearFormulario();
        crearTabla();
        cargarHabitaciones();
    }

    private void crearFormulario() {
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));

        JLabel lblNumero = new JLabel("Número:");
        txtNumero = new JTextField();

        JLabel lblTipo = new JLabel("Tipo:");
        txtTipo = new JTextField();

        JLabel lblTarifa = new JLabel("Tarifa:");
        txtTarifa = new JTextField();

        JLabel lblEstado = new JLabel("Estado:");
        cmbEstado = new JComboBox<>(EstadoHabitacion.values());

        JButton btnGuardar = new JButton("Guardar habitación");
        JButton btnLimpiar = new JButton("Limpiar");

        btnGuardar.addActionListener(e -> guardarHabitacion());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        panelFormulario.add(lblNumero);
        panelFormulario.add(txtNumero);

        panelFormulario.add(lblTipo);
        panelFormulario.add(txtTipo);

        panelFormulario.add(lblTarifa);
        panelFormulario.add(txtTarifa);

        panelFormulario.add(lblEstado);
        panelFormulario.add(cmbEstado);

        panelFormulario.add(btnGuardar);
        panelFormulario.add(btnLimpiar);

        add(panelFormulario, BorderLayout.NORTH);
    }

    private void crearTabla() {
        modeloTabla = new DefaultTableModel();

        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Número");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Tarifa");
        modeloTabla.addColumn("Estado");

        tablaHabitaciones = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaHabitaciones);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void guardarHabitacion() {
        try {
            int numero = Integer.parseInt(txtNumero.getText().trim());
            String tipo = txtTipo.getText().trim();
            double tarifa = Double.parseDouble(txtTarifa.getText().trim());
            EstadoHabitacion estado = (EstadoHabitacion) cmbEstado.getSelectedItem();

            habitacionService.registrarHabitacion(numero, tipo, tarifa, estado);

            JOptionPane.showMessageDialog(
                    this,
                    "Habitación registrada correctamente.",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            limpiarCampos();
            cargarHabitaciones();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "El número y la tarifa deben ser valores numéricos.",
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

    private void cargarHabitaciones() {
        modeloTabla.setRowCount(0);

        List<Habitacion> habitaciones = habitacionService.listarHabitaciones();

        for (Habitacion h : habitaciones) {
            Object[] fila = {
                    h.getId(),
                    h.getNumero(),
                    h.getTipo(),
                    h.getTarifa(),
                    h.getEstado()
            };

            modeloTabla.addRow(fila);
        }
    }

    private void limpiarCampos() {
        txtNumero.setText("");
        txtTipo.setText("");
        txtTarifa.setText("");
        cmbEstado.setSelectedIndex(0);
        txtNumero.requestFocus();
    }
}
