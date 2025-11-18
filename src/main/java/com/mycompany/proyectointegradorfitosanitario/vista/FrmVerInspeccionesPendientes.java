/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Formulario: Ver Inspecciones PENDIENTES
 * Para que el ASISTENTE se asigne inspecciones
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
 * Tabla de inspecciones PENDIENTES para que asistente se asigne
 */
public class FrmVerInspeccionesPendientes extends JFrame {
    
    private Usuario usuarioLogueado;
    private AsistenteTecnico asistenteActual;
    
    private JTable tablaInspecciones;
    private DefaultTableModel modeloTabla;
    private JButton btnAsignarme;
    private JButton btnActualizar;
    private JButton btnVolver;
    
    private InspeccionFitosanitariaDAO inspeccionDAO;
    private AsistenteTecnicoDAO asistenteDAO;
    
    private List<InspeccionFitosanitaria> listaInspecciones;

    public FrmVerInspeccionesPendientes(Usuario usuario) {
        this.usuarioLogueado = usuario;
        this.inspeccionDAO = new InspeccionFitosanitariaDAO();
        this.asistenteDAO = new AsistenteTecnicoDAO();
        
        cargarAsistente();
        inicializarComponentes();
        cargarInspeccionesPendientes();
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
        setTitle("📋 Inspecciones Pendientes - Asistente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(240, 240, 240));
        
        // ========== TÍTULO ==========
        JLabel lblTitulo = new JLabel("📋 INSPECCIONES PENDIENTES (SIN ASIGNAR)");
        lblTitulo.setBounds(20, 15, 860, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(255, 152, 0));
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        panel.add(lblTitulo);
        
        // ========== INFO ASISTENTE ==========
        JLabel lblAsistente = new JLabel("👤 Asistente: " + 
            (asistenteActual != null ? asistenteActual.getNombre() : "N/A"));
        lblAsistente.setBounds(20, 55, 860, 25);
        lblAsistente.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(lblAsistente);
        
        // ========== TABLA ==========
        String[] columnas = {"ID", "Lugar", "Propietario", "Fecha Solicitud", "Observaciones"};
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
        scrollPane.setBounds(20, 95, 860, 380);
        panel.add(scrollPane);
        
        // ========== MENSAJE INFORMATIVO ==========
        JLabel lblInfo = new JLabel("💡 Selecciona una inspección y haz clic en 'Asignarme' para realizarla.");
        lblInfo.setBounds(20, 485, 860, 20);
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 12));
        lblInfo.setForeground(new Color(100, 100, 100));
        panel.add(lblInfo);
        
        // ========== BOTONES ==========
        btnAsignarme = new JButton("✅ Asignarme esta Inspección");
        btnAsignarme.setBounds(20, 515, 250, 40);
        btnAsignarme.setFont(new Font("Arial", Font.BOLD, 14));
        btnAsignarme.setBackground(new Color(76, 175, 80));
        btnAsignarme.setForeground(Color.WHITE);
        btnAsignarme.setFocusPainted(false);
        btnAsignarme.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAsignarme.addActionListener(e -> asignarInspeccion());
        panel.add(btnAsignarme);
        
        btnActualizar = new JButton("🔄 Actualizar");
        btnActualizar.setBounds(320, 515, 250, 40);
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 14));
        btnActualizar.setBackground(new Color(33, 150, 243));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);
        btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnActualizar.addActionListener(e -> cargarInspeccionesPendientes());
        panel.add(btnActualizar);
        
        btnVolver = new JButton("⬅️ Volver");
        btnVolver.setBounds(630, 515, 250, 40);
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setBackground(new Color(158, 158, 158));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> this.dispose());
        panel.add(btnVolver);
        
        setContentPane(panel);
    }

    private void cargarInspeccionesPendientes() {
        modeloTabla.setRowCount(0);
        
        listaInspecciones = inspeccionDAO.listarPendientes();
        
        if (listaInspecciones.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "✅ No hay inspecciones pendientes.\n" +
                "Todas las solicitudes ya tienen asistente asignado.",
                "Sin Pendientes", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        for (InspeccionFitosanitaria inspeccion : listaInspecciones) {
            Object[] fila = {
                inspeccion.getId(),
                inspeccion.getNombreLugar(),
                inspeccion.getNombrePropietario(),
                inspeccion.getFechaSolicitud().toLocalDate(),
                inspeccion.getObservaciones()
            };
            
            modeloTabla.addRow(fila);
        }
        
        System.out.println("✅ " + listaInspecciones.size() + " inspecciones pendientes cargadas");
    }

    private void asignarInspeccion() {
        int filaSeleccionada = tablaInspecciones.getSelectedRow();
        
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this,
                "⚠️ Debes seleccionar una inspección de la tabla",
                "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        InspeccionFitosanitaria inspeccionSeleccionada = listaInspecciones.get(filaSeleccionada);
        
        int confirmar = JOptionPane.showConfirmDialog(this,
            "¿Deseas asignarte la inspección del lugar:\n" +
            inspeccionSeleccionada.getNombreLugar() + "?\n\n" +
            "Propietario: " + inspeccionSeleccionada.getNombrePropietario() + "\n" +
            "Fecha solicitud: " + inspeccionSeleccionada.getFechaSolicitud().toLocalDate(),
            "Confirmar Asignación",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmar != JOptionPane.YES_OPTION) {
            return;
        }
        
        boolean exito = inspeccionDAO.asignarAsistente(
            inspeccionSeleccionada.getId(),
            asistenteActual.getId()
        );
        
        if (exito) {
            JOptionPane.showMessageDialog(this,
                "✅ ¡Inspección asignada exitosamente!\n\n" +
                "Estado: EN_PROCESO\n" +
                "Ahora puedes ir al lugar y realizar la inspección.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            cargarInspeccionesPendientes();
        } else {
            JOptionPane.showMessageDialog(this,
                "❌ Error al asignar la inspección.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

