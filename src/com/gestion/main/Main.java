package com.gestion.main;

import com.gestion.vista.VentanaPrincipal;

import javax.swing.*;

/**
 * Punto de entrada principal de la aplicacion "Gestion de Productos".
 */
public class Main {
    public static void main(String[] args) {
        // Establecer Look and Feel nativo del sistema operativo para una apariencia moderna y limpia
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo aplicar el Look & Feel del sistema. Usando apariencia por defecto.");
        }

        // Iniciar la interfaz grafica en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
