/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.EstadoHabitacion;
import Model.Habitacion;
import Services.HabitacionService;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

public class DisponibilidadPanel extends JPanel {

    private JTable tablaDisponibilidad;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> cmbFiltroEstado;

    private JLabel lblTotal;
    private JLabel lblDisponibles;
    private JLabel lblOcupadas;
    private JLabel lblLimpieza;
    private JLabel lblMantenimiento;

    private HabitacionService habitacionService;

    public DisponibilidadPanel() {
        habitacionService = new HabitacionService();

        setLayout(new BorderLayout(10, 10));

        crearPanelSuperior();
        crearTabla();
        crearPanelResumen();

        cargarHabitaciones();
    }

    private void crearPanelSuperior() {
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel lblFiltro = new JLabel("Filtrar por estado:");

        cmbFiltroEstado = new JComboBox<>();
        cmbFiltroEstado.addItem("TODOS");
        cmbFiltroEstado.addItem("DISPONIBLE");
        cmbFiltroEstado.addItem("OCUPADA");
        cmbFiltroEstado.addItem("LIMPIEZA");
        cmbFiltroEstado.addItem("MANTENIMIENTO");

        JButton btnActualizar = new JButton("Actualizar");
        JButton btnFiltrar = new JButton("Filtrar");

        btnActualizar.addActionListener(e -> cargarHabitaciones());
        btnFiltrar.addActionListener(e -> cargarHabitaciones());

        panelSuperior.add(lblFiltro);
        panelSuperior.add(cmbFiltroEstado);
        panelSuperior.add(btnFiltrar);
        panelSuperior.add(btnActualizar);

        add(panelSuperior, BorderLayout.NORTH);
    }

    private void crearTabla() {
        modeloTabla = new DefaultTableModel();

        modeloTabla.addColumn("Número");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Tarifa");
        modeloTabla.addColumn("Estado");

        tablaDisponibilidad = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaDisponibilidad);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void crearPanelResumen() {
        JPanel panelResumen = new JPanel(new FlowLayout(FlowLayout.LEFT));

        lblTotal = new JLabel("Total: 0");
        lblDisponibles = new JLabel("Disponibles: 0");
        lblOcupadas = new JLabel("Ocupadas: 0");
        lblLimpieza = new JLabel("Limpieza: 0");
        lblMantenimiento = new JLabel("Mantenimiento: 0");

        panelResumen.add(lblTotal);
        panelResumen.add(lblDisponibles);
        panelResumen.add(lblOcupadas);
        panelResumen.add(lblLimpieza);
        panelResumen.add(lblMantenimiento);

        add(panelResumen, BorderLayout.SOUTH);
    }

    private void cargarHabitaciones() {
        modeloTabla.setRowCount(0);

        List<Habitacion> habitaciones = habitacionService.listarHabitaciones();

        String filtro = cmbFiltroEstado.getSelectedItem().toString();

        int total = 0;
        int disponibles = 0;
        int ocupadas = 0;
        int limpieza = 0;
        int mantenimiento = 0;

        for (Habitacion h : habitaciones) {

            if (h.getEstado() == EstadoHabitacion.DISPONIBLE) {
                disponibles++;
            } else if (h.getEstado() == EstadoHabitacion.OCUPADA) {
                ocupadas++;
            } else if (h.getEstado() == EstadoHabitacion.LIMPIEZA) {
                limpieza++;
            } else if (h.getEstado() == EstadoHabitacion.MANTENIMIENTO) {
                mantenimiento++;
            }

            boolean mostrar = filtro.equals("TODOS") || h.getEstado().name().equals(filtro);

            if (mostrar) {
                Object[] fila = {
                        h.getNumero(),
                        h.getTipo(),
                        h.getTarifa(),
                        h.getEstado()
                };

                modeloTabla.addRow(fila);
                total++;
            }
        }

        lblTotal.setText("Total mostrado: " + total);
        lblDisponibles.setText("Disponibles: " + disponibles);
        lblOcupadas.setText("Ocupadas: " + ocupadas);
        lblLimpieza.setText("Limpieza: " + limpieza);
        lblMantenimiento.setText("Mantenimiento: " + mantenimiento);
    }
}