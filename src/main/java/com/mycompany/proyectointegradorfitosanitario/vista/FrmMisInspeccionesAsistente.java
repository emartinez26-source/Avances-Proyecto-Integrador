/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Formulario: Ver Mis Inspecciones Antiguas (Asistente)
 * Muestra inspecciones EN_PROCESO y COMPLETADAS del asistente
 */
package com.mycompany.proyectointegradorfitosanitario.vista;

import com.mycompany.proyectointegradorfitosanitario.dao.InspeccionFitosanitariaDAO;
import com.mycompany.proyectointegradorfitosanitario.dao.AsistenteTecnicoDAO;
import com.mycompany.proyectointegradorfitosanitario.modelo.InspeccionFitosanitaria;
import com.mycompany.proyectointegradorfitosanitario.modelo.AsistenteTecnico;
import com.mycompany.proyectointegradorfitosanitario.modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Tabla de inspecciones completadas o en proceso del asistente
 */
public class FrmMisInspeccionesAsistente extends JFrame {
    
    private Usuario usuarioLogueado;
    private AsistenteTecnico asistenteActual;
    
    private JTable tablaInspecciones;
    private DefaultTableModel modeloTabla;
    private JButton btnVolver;
    
    private InspeccionFitosanitariaDAO inspeccionDAO;
    private AsistenteTecnicoDAO asistenteDAO;

    public FrmMisInspeccionesAsistente(Usuario usuario) {
        this.usuarioLogueado = usuario;
        this.inspeccionDAO = new InspeccionFitosanitariaDAO();
        this.asistenteDAO = new AsistenteTecnicoDAO();
        
        cargarAsistente();
        inicializarComponentes();
        cargarMisInspecciones();
    }

    private void cargarAsistente() {
        asistenteActual = asistenteDAO.buscarPorUsuarioId(usuarioLogueado.getId());
        
        if (asistenteActual == null) {
            JOptionPane.showMessageDialog(this,
                "❌ No se encontró el asistente técnico asociado a tu usuario.",
                "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }

    private void inicializarComponentes() {
        setTitle("📋 Mis Inspecciones - Asistente Técnico");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(240, 240, 240));
        
        // ========== TÍTULO ==========
        JLabel lblTitulo = new JLabel("📋 MIS INSPECCIONES (EN_PROCESO y COMPLETADAS)");
        lblTitulo.setBounds(20, 15, 860, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(25, 118, 210));
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        panel.add(lblTitulo);
        
        // ========== INFO ASISTENTE ==========
        JLabel lblAsistente = new JLabel("👤 Asistente: " + 
            (asistenteActual != null ? asistenteActual.getNombre() : "N/A"));
        lblAsistente.setBounds(20, 55, 860, 25);
        lblAsistente.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(lblAsistente);
        
        // ========== TABLA ==========
        String[] columnas = {"ID", "Lugar", "Propietario", "Fecha Solicitud", "Fecha Inspección", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        
        tablaInspecciones = new JTable(modeloTabla);
        tablaInspecciones.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaInspecciones.setRowHeight(25);
        tablaInspecciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Ocultar columna ID
        tablaInspecciones.getColumnModel().getColumn(0).setMinWidth(0);
        tablaInspecciones.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaInspecciones.getColumnModel().getColumn(0).setWidth(0);
        
        JScrollPane scrollPane = new JScrollPane(tablaInspecciones);
        scrollPane.setBounds(20, 95, 860, 420);
        panel.add(scrollPane);
        
        // ========== BOTÓN VOLVER ==========
        btnVolver = new JButton("⬅️ Volver");
        btnVolver.setBounds(700, 525, 180, 40);
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setBackground(new Color(158, 158, 158));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> this.dispose());
        panel.add(btnVolver);
        
        setContentPane(panel);
    }

    private void cargarMisInspecciones() {
        modeloTabla.setRowCount(0);
        
        if (asistenteActual == null) {
            JOptionPane.showMessageDialog(this,
                "❌ No se pudo cargar las inspecciones.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Obtener todas las inspecciones asignadas a este asistente
        // (Estado: EN_PROCESO o COMPLETADA)
        List<InspeccionFitosanitaria> misInspecciones = 
            inspeccionDAO.listarPorAsistente(asistenteActual.getId());
        
        if (misInspecciones.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "✅ No tienes inspecciones en progreso o completadas.",
                "Sin Inspecciones", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        for (InspeccionFitosanitaria inspeccion : misInspecciones) {
            Object[] fila = {
                inspeccion.getId(),
                inspeccion.getNombreLugar(),
                inspeccion.getNombrePropietario(),
                inspeccion.getFechaSolicitud().toLocalDate(),
                inspeccion.getFechaInspeccion() != null ? inspeccion.getFechaInspeccion() : "---",
                inspeccion.getEstado()
            };
            
            modeloTabla.addRow(fila);
        }
        
        System.out.println("✅ " + misInspecciones.size() + " inspecciones cargadas");
    }
}
