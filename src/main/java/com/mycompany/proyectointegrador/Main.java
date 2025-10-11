package com.mycompany.proyectointegrador;

import com.mycompany.proyectointegrador.Vistas.FrmPropietario;
import java.awt.EventQueue;

/**
 *
 * @author edward
 */
public class Main {
    public static void main(String[] args) {
        // Ejecutar en el hilo de eventos de Swing
        EventQueue.invokeLater(() -> {
            FrmPropietario frame = new FrmPropietario();
            frame.setVisible(true);
        });
    }
}