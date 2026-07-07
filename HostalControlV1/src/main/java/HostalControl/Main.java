/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HostalControl;

import Model.EstadoHabitacion;
import Model.Habitacion;
import Services.HabitacionService;
import View.MainFrame;
import java.util.List;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame ventana = new MainFrame();
            ventana.setVisible(true);
        });
    }
}