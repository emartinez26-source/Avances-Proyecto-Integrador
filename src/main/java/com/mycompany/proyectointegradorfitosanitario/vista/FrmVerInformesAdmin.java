/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Ver Informes de Inspecciones - ADMIN
 * Visualiza todas las inspecciones completadas del sistema
 */
package com.mycompany.proyectointegradorfitosanitario.vista;

import com.mycompany.proyectointegradorfitosanitario.dao.*;
import com.mycompany.proyectointegradorfitosanitario.modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel de informes para ADMIN
 * Ve todas las inspecciones completadas con detalles
 */
public class FrmVerInformesAdmin extends JFrame {
    
    private JTable tablaInformes;
    private DefaultTableModel modeloTabla;
    private JLabel lblEstadisticas;
    private JButton btnVolver;
    
    private InspeccionFitosanitariaDAO inspeccionDAO;
    private DetalleInspeccionDAO detalleDAO;
    
    public FrmVerInformesAdmin() {
        this.inspeccionDAO = new InspeccionFitosanitariaDAO();
        this.detalleDAO = new DetalleInspeccionDAO();
        
        inicializarComponentes();
        cargarInformes();
    }
    
    private void inicializarComponentes() {
        setTitle("📊 Informes de Inspecciones - Admin");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(240, 240, 240));
        
        // ========== TÍTULO ==========
        JLabel lblTitulo = new JLabel("📊 INFORMES DE INSPECCIONES FITOSANITARIAS");
        lblTitulo.setBounds(20, 15, 960, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(0, 51, 102));
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        panel.add(lblTitulo);
        
        // ========== ESTADÍSTICAS ==========
        lblEstadisticas = new JLabel("Cargando estadísticas...");
        lblEstadisticas.setBounds(20, 55, 960, 25);
        lblEstadisticas.setFont(new Font("Arial", Font.PLAIN, 13));
        lblEstadisticas.setForeground(new Color(100, 100, 100));
        panel.add(lblEstadisticas);
        
        // ========== TABLA ==========
        String[] columnas = {"ID", "Lugar", "Propietario", "Asistente", "Especie", 
                            "Total Plantas", "Plantas Afectadas", "Incidencia %", "Fecha Inspección"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        
        tablaInformes = new JTable(modeloTabla);
        tablaInformes.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaInformes.setRowHeight(25);
        tablaInformes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Ocultar columna ID
        tablaInformes.getColumnModel().getColumn(0).setMinWidth(0);
        tablaInformes.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaInformes.getColumnModel().getColumn(0).setWidth(0);
        
        // Ajustar ancho de columnas
        tablaInformes.getColumnModel().getColumn(1).setPreferredWidth(120);
        tablaInformes.getColumnModel().getColumn(2).setPreferredWidth(120);
        tablaInformes.getColumnModel().getColumn(3).setPreferredWidth(100);
        tablaInformes.getColumnModel().getColumn(7).setPreferredWidth(80);
        
        JScrollPane scrollPane = new JScrollPane(tablaInformes);
        scrollPane.setBounds(20, 90, 960, 450);
        panel.add(scrollPane);
        
        // ========== BOTÓN VOLVER ==========
        btnVolver = new JButton("⬅️ Volver");
        btnVolver.setBounds(820, 550, 160, 40);
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setBackground(new Color(158, 158, 158));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> this.dispose());
        panel.add(btnVolver);
        
        setContentPane(panel);
    }
    
    private void cargarInformes() {
        modeloTabla.setRowCount(0);
        
        // Obtener todas las inspecciones completadas
        List<InspeccionFitosanitaria> inspeccionesCompletadas = 
            inspeccionDAO.listarCompletadas();
        
        if (inspeccionesCompletadas.isEmpty()) {
            lblEstadisticas.setText("📈 No hay inspecciones completadas aún");
            return;
        }
        
        int totalInspecciones = 0;
        double sumIncidencia = 0;
        int maxIncidencia = 0;
        
        for (InspeccionFitosanitaria insp : inspeccionesCompletadas) {
            // Obtener detalles de la inspección
            List<DetalleInspeccion> detalles = detalleDAO.listarPorInspeccion(insp.getId());
            
            for (DetalleInspeccion detalle : detalles) {
                double incidencia = detalle.getNivelIncidencia() != null ? detalle.getNivelIncidencia() : 0.0;
                sumIncidencia += incidencia;
                maxIncidencia = (int) Math.max(maxIncidencia, incidencia);
                
                Object[] fila = {
                    insp.getId(),
                    insp.getNombreLugar(),
                    insp.getNombrePropietario(),
                    insp.getNombreAsistente() != null ? insp.getNombreAsistente() : "---",
                    "---", // Especie (agregar en modelo)
                    detalle.getTotalPlantas(),
                    detalle.getPlantasAfectadas(),
                    String.format("%.2f%%", incidencia),
                    insp.getFechaInspeccion() != null ? insp.getFechaInspeccion() : "---"
                };
                
                modeloTabla.addRow(fila);
                totalInspecciones++;
            }
        }
        
        // Calcular estadísticas
        double promIncidencia = totalInspecciones > 0 ? sumIncidencia / totalInspecciones : 0;
        
        String stats = String.format(
            "📈 Total inspecciones: %d | Incidencia Promedio: %.2f%% | Máxima: %d%% | " +
            "Última actualización: %s",
            totalInspecciones, promIncidencia, maxIncidencia,
            java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        );
        
        lblEstadisticas.setText(stats);
        
        System.out.println("✅ " + totalInspecciones + " detalles de inspección cargados");
    }
}
