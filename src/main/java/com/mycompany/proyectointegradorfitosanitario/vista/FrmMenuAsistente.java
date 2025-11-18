/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Panel de Menú para Asistente - CORREGIDO
 * Layout de botones arreglado
 * 
 * 6 BOTONES ORGANIZADOS EN 2 FILAS:
 * Fila 1: Insp. Pendientes | Mis Insp. Antiguas
 * Fila 2: Realizar Insp.   | Ver Especies
 * Fila 3: Ver Plagas       | Ver Informes
 * Fila 4: Cerrar Sesión    (solo este)
 */
package com.mycompany.proyectointegradorfitosanitario.vista;

import com.mycompany.proyectointegradorfitosanitario.modelo.Usuario;
import com.mycompany.proyectointegradorfitosanitario.vista.FrmMisInspeccionesAsistente;
import com.mycompany.proyectointegradorfitosanitario.vista.FrmVerInspeccionesPendientes;
import com.mycompany.proyectointegradorfitosanitario.vista.FrmVerPlagasAsistente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Panel de Menú para Asistente Técnico
 * Interfaz NARANJA - Inspecciones Fitosanitarias
 */
public class FrmMenuAsistente extends JFrame {
    
    private JPanel panelPrincipal;
    private JPanel panelHeader;
    private JLabel lblBienvenida;
    private JLabel lblUsuario;
    
    // BOTONES
    private JButton btnVerInspeccionesPendientes;
    private JButton btnMisInspecciones;
    private JButton btnRealizarInspeccion;
    private JButton btnVerEspecies;
    private JButton btnVerPlagas;
    private JButton btnMisInformes;
    private JButton btnCerrarSesion;
    
    private Usuario usuarioLogueado;
    
    public FrmMenuAsistente(Usuario usuario) {
        this.usuarioLogueado = usuario;
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Panel de Asistente Técnico - Sistema de Inspección Fitosanitaria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 750);
        setLocationRelativeTo(null);
        setResizable(false);
        
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBackground(new Color(245, 245, 245));
        
        // ===== PANEL HEADER (NARANJA) =====
        panelHeader = new JPanel();
        panelHeader.setBounds(0, 0, 700, 100);
        panelHeader.setBackground(new Color(255, 152, 0));
        panelHeader.setLayout(null);
        
        lblBienvenida = new JLabel("PANEL DE ASISTENTE TÉCNICO");
        lblBienvenida.setBounds(20, 15, 660, 35);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 22));
        lblBienvenida.setForeground(Color.WHITE);
        panelHeader.add(lblBienvenida);
        
        lblUsuario = new JLabel("🔧 Logueado como: " + usuarioLogueado.getUsername() + " | Rol: " + usuarioLogueado.getRol());
        lblUsuario.setBounds(20, 50, 660, 25);
        lblUsuario.setFont(new Font("Arial", Font.ITALIC, 12));
        lblUsuario.setForeground(new Color(255, 255, 200));
        panelHeader.add(lblUsuario);
        
        panelPrincipal.add(panelHeader);
        
        // ===== FILA 1: INSPECCIONES =====
        

        
        // Botón 2: Mis Inspecciones Antiguas
        btnMisInspecciones = crearBoton(
            "📋 Mis Inspecciones",
            350, 130, 300, 80,
            new Color(33, 150, 243),
            "Ver tus inspecciones EN_PROCESO y COMPLETADAS"
        );
        btnMisInspecciones.addActionListener(e -> abrirMisInspecciones());
        panelPrincipal.add(btnMisInspecciones);
        
        // ===== FILA 2: OPERACIONES =====
        
        // Botón 3: Realizar Inspección
        btnRealizarInspeccion = crearBoton(
            "🔍 Realizar Inspección",
            50, 230, 300, 80,
            new Color(76, 175, 80),
            "Levanta datos de inspección en campo"
        );
        btnRealizarInspeccion.addActionListener(this::abrirRealizarInspeccion);
        panelPrincipal.add(btnRealizarInspeccion);
        
        // Botón 4: Ver Especies
        btnVerEspecies = crearBoton(
            "🌱 Ver Especies",
            350, 230, 300, 80,
            new Color(139, 69, 19),
            "Consulta cultivos a inspeccionar"
        );
        btnVerEspecies.addActionListener(this::abrirVerEspecies);
        panelPrincipal.add(btnVerEspecies);
        
        // ===== FILA 3: CONSULTAS =====
        
        // Botón 5: Ver Plagas
        btnVerPlagas = crearBoton(
            "🐛 Ver Plagas",
            50, 330, 300, 80,
            new Color(192, 0, 0),
            "Consulta plagas a detectar"
        );
        btnVerPlagas.addActionListener(this::abrirVerPlagas);
        panelPrincipal.add(btnVerPlagas);
        
        // Botón 6: Ver Mis Informes
        btnMisInformes = crearBoton(
            "📄 Ver Informes",
            350, 330, 300, 80,
            new Color(102, 102, 102),
            "Consulta reportes de tus inspecciones"
        );
        btnMisInformes.addActionListener(this::abrirVerInformes);
        panelPrincipal.add(btnMisInformes);
        
        // ===== FILA 4: SALIR =====
        
        // Botón 7: Cerrar Sesión
        btnCerrarSesion = crearBoton(
            "🚪 Cerrar Sesión",
            50, 430, 600, 80,
            new Color(211, 47, 47),
            "Salir del sistema"
        );
        btnCerrarSesion.addActionListener(this::cerrarSesion);
        panelPrincipal.add(btnCerrarSesion);
        
        setContentPane(panelPrincipal);
    }
    
    private JButton crearBoton(String texto, int x, int y, int ancho, int alto, 
                              Color color, String tooltip) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, ancho, alto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
        boton.setToolTipText(tooltip);
        boton.setFocusPainted(false);
        return boton;
    }
    
    // ===== ACCIONES DE BOTONES =====
    
    /**
     * Ver inspecciones PENDIENTES (para asignarse)
     */

    
    /**
     * Ver mis inspecciones ANTIGUAS (EN_PROCESO, COMPLETADA)
     */
    private void abrirMisInspecciones() {
        System.out.println("\n📋 Abriendo: Inspecciones PENDIENTES");
        SwingUtilities.invokeLater(() -> {
            FrmVerInspeccionesPendientes frame = new FrmVerInspeccionesPendientes(usuarioLogueado);
            frame.setVisible(true);
        });
    }

    
    private void abrirRealizarInspeccion(ActionEvent e) {
        System.out.println("\n🔍 Abriendo: Realizar Inspección");
        SwingUtilities.invokeLater(() -> {
            FrmRealizarInspeccion frame = new FrmRealizarInspeccion(usuarioLogueado);
            frame.setVisible(true);
        });
    }
    
    private void abrirVerEspecies(ActionEvent e) {
        System.out.println("\n🌱 Abriendo: Ver Especies");
        SwingUtilities.invokeLater(() -> {
            FrmVerEspeciesAsistente frame = new FrmVerEspeciesAsistente();
            frame.setVisible(true);
        });
    }
    
    private void abrirVerPlagas(ActionEvent e) {
        System.out.println("\n🐛 Abriendo: Ver Plagas");
        SwingUtilities.invokeLater(() -> {
            FrmVerPlagasAsistente frame = new FrmVerPlagasAsistente();
            frame.setVisible(true);
        });
    }
    
    private void abrirVerInformes(ActionEvent e) {
        System.out.println("\n📄 Abriendo: Ver mis Informes");
        JOptionPane.showMessageDialog(this,
            "Próximamente: Mis reportes de inspección\n" +
            "- Inspecciones completadas\n" +
            "- Plagas detectadas\n" +
            "- Nivel de incidencia",
            "Mis Informes",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void cerrarSesion(ActionEvent e) {
        System.out.println("\n🚪 Cerrando sesión para: " + usuarioLogueado.getUsername());
        
        int confirmar = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de que deseas cerrar sesión?",
            "Confirmar cierre de sesión",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmar == JOptionPane.YES_OPTION) {
            System.out.println("✅ Sesión cerrada. Volviendo a login...");
            this.dispose();
            
            SwingUtilities.invokeLater(() -> {
                FrmLogin loginFrame = new FrmLogin();
                loginFrame.setVisible(true);
            });
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Usuario asistente = new Usuario(
                java.util.UUID.randomUUID(),
                "dcaprio",
                "123456",
                "ASISTENTE",
                "dCa@udi.edu.co",
                true,
                java.time.LocalDateTime.now()
            );
            
            FrmMenuAsistente frame = new FrmMenuAsistente(asistente);
            frame.setVisible(true);
        });
    }
}