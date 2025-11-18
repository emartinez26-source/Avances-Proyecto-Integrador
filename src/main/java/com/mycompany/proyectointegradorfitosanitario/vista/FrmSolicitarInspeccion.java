/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.vista;

import com.mycompany.proyectointegradorfitosanitario.dao.InspeccionFitosanitariaDAO;
import com.mycompany.proyectointegradorfitosanitario.dao.LugarProduccionDAO;
import com.mycompany.proyectointegradorfitosanitario.dao.PropietarioDAO;
import com.mycompany.proyectointegradorfitosanitario.modelo.InspeccionFitosanitaria;
import com.mycompany.proyectointegradorfitosanitario.modelo.LugarProduccion;
import com.mycompany.proyectointegradorfitosanitario.modelo.Propietario;
import com.mycompany.proyectointegradorfitosanitario.modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FrmSolicitarInspeccion extends JFrame {
    
    private Usuario usuarioLogueado;
    private Propietario propietario;
    
    private JComboBox<String> comboLugares;
    private JTextArea txtObservaciones;
    private JButton btnSolicitar;
    private JButton btnCancelar;
    
    private LugarProduccionDAO lugarDAO;
    private InspeccionFitosanitariaDAO inspeccionDAO;
    private PropietarioDAO propietarioDAO;
    
    private List<LugarProduccion> listaLugares;

    public FrmSolicitarInspeccion(Usuario usuario) {
        this.usuarioLogueado = usuario;
        this.lugarDAO = new LugarProduccionDAO();
        this.inspeccionDAO = new InspeccionFitosanitariaDAO();
        this.propietarioDAO = new PropietarioDAO();
        
        cargarPropietario();
        inicializarComponentes();
        cargarLugares();
    }

    private void cargarPropietario() {
        propietario = propietarioDAO.buscarPorId(usuarioLogueado.getId());
        
        if (propietario == null) {
            JOptionPane.showMessageDialog(this, 
                "❌ No se encontró el propietario asociado a tu usuario.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }

    private void inicializarComponentes() {
        setTitle("📋 Solicitar Inspección Fitosanitaria");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(240, 240, 240));
        
        JLabel lblTitulo = new JLabel("📋 SOLICITAR INSPECCIÓN FITOSANITARIA");
        lblTitulo.setBounds(20, 15, 560, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 128, 96));
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        panel.add(lblTitulo);
        
        JLabel lblPropietario = new JLabel("Propietario: " + 
            (propietario != null ? propietario.getNombre() : "No identificado"));
        lblPropietario.setBounds(20, 60, 560, 25);
        lblPropietario.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(lblPropietario);
        
        JLabel lblLugar = new JLabel("🏠 Lugar de Producción:");
        lblLugar.setBounds(20, 100, 200, 25);
        lblLugar.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lblLugar);
        
        comboLugares = new JComboBox<>();
        comboLugares.setBounds(20, 130, 560, 30);
        comboLugares.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(comboLugares);
        
        JLabel lblObservaciones = new JLabel("📝 Observaciones / Motivo de la solicitud:");
        lblObservaciones.setBounds(20, 175, 400, 25);
        lblObservaciones.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lblObservaciones);
        
        txtObservaciones = new JTextArea();
        txtObservaciones.setFont(new Font("Arial", Font.PLAIN, 13));
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        
        JScrollPane scrollObservaciones = new JScrollPane(txtObservaciones);
        scrollObservaciones.setBounds(20, 205, 560, 120);
        panel.add(scrollObservaciones);
        
        JLabel lblInfo = new JLabel("💡 Un asistente técnico revisará tu solicitud y realizará la inspección.");
        lblInfo.setBounds(20, 335, 560, 30);
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 12));
        lblInfo.setForeground(new Color(100, 100, 100));
        panel.add(lblInfo);
        
        btnSolicitar = new JButton("✅ Solicitar Inspección");
        btnSolicitar.setBounds(50, 375, 230, 40);
        btnSolicitar.setFont(new Font("Arial", Font.BOLD, 14));
        btnSolicitar.setBackground(new Color(0, 128, 96));
        btnSolicitar.setForeground(Color.WHITE);
        btnSolicitar.setFocusPainted(false);
        btnSolicitar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSolicitar.addActionListener(e -> solicitarInspeccion());
        panel.add(btnSolicitar);
        
        btnCancelar = new JButton("❌ Cancelar");
        btnCancelar.setBounds(320, 375, 230, 40);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setBackground(new Color(128, 128, 128));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(e -> this.dispose());
        panel.add(btnCancelar);
        
        setContentPane(panel);
    }

    private void cargarLugares() {
        if (propietario == null) return;
        
        comboLugares.removeAllItems();
        comboLugares.addItem("-- Selecciona un lugar de producción --");
        
        listaLugares = lugarDAO.listarPorPropietario(propietario.getId());
        
        if (listaLugares.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "⚠️ No tienes lugares de producción registrados.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            btnSolicitar.setEnabled(false);
            return;
        }
        
        for (LugarProduccion lugar : listaLugares) {
            comboLugares.addItem(lugar.getNombre());
        }
    }

    private void solicitarInspeccion() {
        if (comboLugares.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this,
                "⚠️ Debes seleccionar un lugar de producción",
                "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int indice = comboLugares.getSelectedIndex() - 1;
        LugarProduccion lugarSeleccionado = listaLugares.get(indice);
        
        String observaciones = txtObservaciones.getText().trim();
        
        if (observaciones.isEmpty()) {
            int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Deseas continuar sin observaciones?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
            
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
            
            observaciones = "Sin observaciones";
        }
        
        InspeccionFitosanitaria nuevaInspeccion = new InspeccionFitosanitaria(
            lugarSeleccionado.getId(),
            propietario.getId(),
            observaciones
        );
        
        if (inspeccionDAO.solicitarInspeccion(nuevaInspeccion)) {
            JOptionPane.showMessageDialog(this,
                "✅ ¡Solicitud de inspección creada exitosamente!\n\n" +
                "📍 Lugar: " + lugarSeleccionado.getNombre() + "\n" +
                "📅 Fecha: " + java.time.LocalDateTime.now().toLocalDate() + "\n" +
                "Estado: PENDIENTE",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "❌ Error al crear la solicitud.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

