/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Realizar Inspección Fitosanitaria - COMPLETO
 * Asistente levanta datos en campo
 */
package com.mycompany.proyectointegradorfitosanitario.vista;

import com.mycompany.proyectointegradorfitosanitario.dao.*;
import com.mycompany.proyectointegradorfitosanitario.modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.UUID;

public class FrmRealizarInspeccion extends JFrame {
    private Usuario usuarioLogueado;
    private AsistenteTecnico asistenteActual;
    
    private JComboBox<String> comboInspecciones;
    private JComboBox<String> comboEspecies;
    private JTextField txtPlantasTotales;
    private JTextField txtPlantasAfectadas;
    private JComboBox<String> comboPlagas;
    private JTextArea txtObservaciones;
    private JLabel lblIncidencia;
    private JButton btnCalcular;
    private JButton btnGuardar;
    private JButton btnVolver;
    
    private InspeccionFitosanitariaDAO inspeccionDAO;
    private AsistenteTecnicoDAO asistenteDAO;
    private EspecieDAO especieDAO;
    private PlagaDAO plagaDAO;
    private DetalleInspeccionDAO detalleDAO;
    
    private List<InspeccionFitosanitaria> listaInspecciones;
    private List<Especie> listaEspecies;
    private List<Plaga> listaPlagas;
    
    public FrmRealizarInspeccion(Usuario usuario) {
        this.usuarioLogueado = usuario;
        this.inspeccionDAO = new InspeccionFitosanitariaDAO();
        this.asistenteDAO = new AsistenteTecnicoDAO();
        this.especieDAO = new EspecieDAO();
        this.plagaDAO = new PlagaDAO();
        this.detalleDAO = new DetalleInspeccionDAO();
        
        cargarAsistente();
        inicializarComponentes();
        cargarInspecciones();
        cargarEspecies();
        cargarPlagas();
    }
    
    private void cargarAsistente() {
        asistenteActual = asistenteDAO.buscarPorUsuarioId(usuarioLogueado.getId());
        if (asistenteActual == null) {
            JOptionPane.showMessageDialog(this, "❌ Asistente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }
    
    private void inicializarComponentes() {
        setTitle("🔍 Realizar Inspección Fitosanitaria");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(240, 240, 240));
        
        // TÍTULO
        JLabel lblTitulo = new JLabel("🔍 REALIZAR INSPECCIÓN FITOSANITARIA");
        lblTitulo.setBounds(20, 15, 660, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(255, 152, 0));
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        panel.add(lblTitulo);
        
        // SELECCIONAR INSPECCIÓN
        JLabel lblInspeccion = new JLabel("1️⃣ Selecciona inspección a realizar:");
        lblInspeccion.setBounds(20, 55, 300, 25);
        lblInspeccion.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lblInspeccion);
        
        comboInspecciones = new JComboBox<>();
        comboInspecciones.setBounds(20, 80, 660, 30);
        comboInspecciones.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(comboInspecciones);
        
        // SELECCIONAR ESPECIE
        JLabel lblEspecie = new JLabel("2️⃣ Especie vegetal a inspeccionar:");
        lblEspecie.setBounds(20, 120, 300, 25);
        lblEspecie.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lblEspecie);
        
        comboEspecies = new JComboBox<>();
        comboEspecies.setBounds(20, 145, 660, 30);
        comboEspecies.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(comboEspecies);
        
        // PLANTAS TOTALES
        JLabel lblTotales = new JLabel("3️⃣ Plantas totales en el lugar:");
        lblTotales.setBounds(20, 185, 300, 25);
        lblTotales.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lblTotales);
        
        txtPlantasTotales = new JTextField();
        txtPlantasTotales.setBounds(20, 210, 300, 30);
        txtPlantasTotales.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(txtPlantasTotales);
        
        // PLANTAS AFECTADAS
        JLabel lblAfectadas = new JLabel("4️⃣ Plantas afectadas por plagas:");
        lblAfectadas.setBounds(380, 185, 300, 25);
        lblAfectadas.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lblAfectadas);
        
        txtPlantasAfectadas = new JTextField();
        txtPlantasAfectadas.setBounds(380, 210, 300, 30);
        txtPlantasAfectadas.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(txtPlantasAfectadas);
        
        // CALCULAR INCIDENCIA
        btnCalcular = new JButton("📊 Calcular Incidencia");
        btnCalcular.setBounds(20, 250, 200, 30);
        btnCalcular.setFont(new Font("Arial", Font.BOLD, 12));
        btnCalcular.setBackground(new Color(33, 150, 243));
        btnCalcular.setForeground(Color.WHITE);
        btnCalcular.setFocusPainted(false);
        btnCalcular.addActionListener(e -> calcularIncidencia());
        panel.add(btnCalcular);
        
        // MOSTRAR INCIDENCIA
        lblIncidencia = new JLabel("Incidencia: ---");
        lblIncidencia.setBounds(230, 250, 150, 30);
        lblIncidencia.setFont(new Font("Arial", Font.BOLD, 14));
        lblIncidencia.setForeground(new Color(192, 0, 0));
        panel.add(lblIncidencia);
        
        // PLAGAS DETECTADAS
        JLabel lblPlaga = new JLabel("5️⃣ Plagas detectadas:");
        lblPlaga.setBounds(20, 295, 300, 25);
        lblPlaga.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lblPlaga);
        
        comboPlagas = new JComboBox<>();
        comboPlagas.setBounds(20, 320, 660, 30);
        comboPlagas.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(comboPlagas);
        
        // OBSERVACIONES
        JLabel lblObs = new JLabel("6️⃣ Observaciones adicionales:");
        lblObs.setBounds(20, 360, 300, 25);
        lblObs.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lblObs);
        
        txtObservaciones = new JTextArea();
        txtObservaciones.setFont(new Font("Arial", Font.PLAIN, 12));
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        
        JScrollPane scrollObs = new JScrollPane(txtObservaciones);
        scrollObs.setBounds(20, 385, 660, 100);
        panel.add(scrollObs);
        
        // BOTONES
        btnGuardar = new JButton("✅ Guardar Inspección");
        btnGuardar.setBounds(20, 495, 320, 40);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGuardar.setBackground(new Color(76, 175, 80));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(e -> guardarInspeccion());
        panel.add(btnGuardar);
        
        btnVolver = new JButton("⬅️ Volver");
        btnVolver.setBounds(360, 495, 320, 40);
        btnVolver.setFont(new Font("Arial", Font.BOLD, 14));
        btnVolver.setBackground(new Color(158, 158, 158));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.addActionListener(e -> this.dispose());
        panel.add(btnVolver);
        
        setContentPane(panel);
    }
    
    private void cargarInspecciones() {
        comboInspecciones.removeAllItems();
        comboInspecciones.addItem("-- Selecciona una inspección --");
        
        listaInspecciones = inspeccionDAO.listarPorAsistente(asistenteActual.getId());
        
        if (listaInspecciones.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "ℹ️ No tienes inspecciones EN_PROCESO.\n" +
                "Asígnate una desde 'Inspecciones Pendientes'",
                "Sin Inspecciones", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        for (InspeccionFitosanitaria insp : listaInspecciones) {
            comboInspecciones.addItem(insp.getNombreLugar() + " (" + insp.getNombrePropietario() + ")");
        }
    }
    
    private void cargarEspecies() {
        comboEspecies.removeAllItems();
        comboEspecies.addItem("-- Selecciona especie --");

        // Por ahora muestra todas, pero DEBERÍA venir del lugar
        listaEspecies = especieDAO.listarTodos();

        for (Especie esp : listaEspecies) {
            comboEspecies.addItem(esp.getNombreComun() + " (" + esp.getNombreCientifico() + ")");
        }

        // NOTA: Idealmente aquí debería cargar solo las especies 
        // del lugar_produccion seleccionado
    }

    
    private void cargarPlagas() {
        comboPlagas.removeAllItems();
        comboPlagas.addItem("-- Selecciona plaga --");
        
        listaPlagas = plagaDAO.listarTodos();
        
        for (Plaga plaga : listaPlagas) {
            comboPlagas.addItem(plaga.getNombreComun() + " (" + plaga.getNombreCientifico() + ")");
        }
    }
    
    private void calcularIncidencia() {
        try {
            int totales = Integer.parseInt(txtPlantasTotales.getText());
            int afectadas = Integer.parseInt(txtPlantasAfectadas.getText());
            
            if (totales <= 0) {
                JOptionPane.showMessageDialog(this, "⚠️ Plantas totales debe ser > 0", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (afectadas > totales) {
                JOptionPane.showMessageDialog(this, "⚠️ Plantas afectadas no puede ser > totales", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            double incidencia = (double) afectadas / totales * 100;
            lblIncidencia.setText(String.format("Incidencia: %.2f%%", incidencia));
            
            // Color según nivel
            if (incidencia > 50) {
                lblIncidencia.setForeground(new Color(192, 0, 0)); // Rojo - CRÍTICO
            } else if (incidencia > 20) {
                lblIncidencia.setForeground(new Color(255, 152, 0)); // Naranja - ALTO
            } else {
                lblIncidencia.setForeground(new Color(76, 175, 80)); // Verde - BAJO
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Ingresa números válidos", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void guardarInspeccion() {
        // Validaciones
        if (comboInspecciones.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "⚠️ Selecciona una inspección", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (comboEspecies.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "⚠️ Selecciona una especie", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (comboPlagas.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "⚠️ Selecciona una plaga", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int totales = Integer.parseInt(txtPlantasTotales.getText());
            int afectadas = Integer.parseInt(txtPlantasAfectadas.getText());
            
            // Obtener IDs
            InspeccionFitosanitaria inspSeleccionada = listaInspecciones.get(comboInspecciones.getSelectedIndex() - 1);
            Especie especieSeleccionada = listaEspecies.get(comboEspecies.getSelectedIndex() - 1);
            Plaga plagaSeleccionada = listaPlagas.get(comboPlagas.getSelectedIndex() - 1);
            
            double incidencia = (double) afectadas / totales * 100;
            String observaciones = txtObservaciones.getText();
            
            // Crear detalle de inspección
            DetalleInspeccion detalle = new DetalleInspeccion(
                UUID.randomUUID(),
                inspSeleccionada.getId(),
                especieSeleccionada.getId(),
                plagaSeleccionada.getCodigo(),
                totales,
                afectadas,
                incidencia,
                observaciones
            );
            
            // Guardar detalle
            boolean detalleGuardado = detalleDAO.insertar(detalle);
            
            if (!detalleGuardado) {
                JOptionPane.showMessageDialog(this, "❌ Error al guardar detalles", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Cambiar estado a COMPLETADA
            boolean inspeccionCompletada = inspeccionDAO.completarInspeccion(
                inspSeleccionada.getId(),
                "Inspección realizada. Incidencia: " + String.format("%.2f%%", incidencia)
            );
            
            if (inspeccionCompletada) {
                JOptionPane.showMessageDialog(this,
                    "✅ ¡Inspección guardada exitosamente!\n\n" +
                    "📊 Incidencia: " + String.format("%.2f%%", incidencia) + "\n" +
                    "🌱 Especie: " + especieSeleccionada.getNombreComun() + "\n" +
                    "🐛 Plaga: " + plagaSeleccionada.getNombreComun() + "\n" +
                    "Estado: COMPLETADA",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al completar inspección", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Ingresa números válidos en plantas", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}