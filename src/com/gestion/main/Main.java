package com.gestion.main;

import com.gestion.vista.VentanaPrincipal;

import javax.swing.*;

/**
 * Punto de entrada principal de la aplicacion "Gestion de Productos".
 */
public class Main {
    public static void main(String[] args) {
        /* Configuración de Look and Feel estándar estilo NetBeans IDE 8.2 */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("No se pudo aplicar el Look & Feel.");
            }
        }

        // Iniciar la interfaz gráfica en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
