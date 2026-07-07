/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("HostalControl - Sistema de Gestión");
        setSize(1050, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Habitaciones", new HabitacionPanel());
        tabs.addTab("Disponibilidad", new DisponibilidadPanel());
        tabs.addTab("Reservas", new ReservaPanel());
        tabs.addTab("Check-in", new CheckInPanel());

        add(tabs);
    }
}