/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.proyectointegrador.Vistas;

/**
 *
 * @author edward
 */


import com.mycompany.proyectointegrador.EspecieVegetal;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Formulario para registrar especies vegetales.
 * @author edward
 */
public class FrmEspecieVegetal extends JFrame {

    private JTextField txtId, txtCodigo, txtNombre, txtDensidad;
    private JTextArea txtDescripcion;
    private JButton btnGuardar, btnLimpiar, btnVolver;
    private ArrayList<EspecieVegetal> listaEspecies;

    public FrmEspecieVegetal() {
        listaEspecies = new ArrayList<>();
        initComponents();
    }

    private void initComponents() {
        setTitle("Registro de Especie Vegetal");
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ===== Título =====
        JLabel lblTitulo = new JLabel("Registro de Especie Vegetal", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(41, 128, 185));
        add(lblTitulo, BorderLayout.NORTH);

        // ===== Panel Central =====
        JPanel panelCentral = new JPanel(new GridLayout(6, 2, 10, 10));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        panelCentral.add(new JLabel("ID:"));
        txtId = new JTextField();
        panelCentral.add(txtId);

        panelCentral.add(new JLabel("Código de Registro:"));
        txtCodigo = new JTextField();
        panelCentral.add(txtCodigo);

        panelCentral.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelCentral.add(txtNombre);

        panelCentral.add(new JLabel("Densidad (kg/m³):"));
        txtDensidad = new JTextField();
        panelCentral.add(txtDensidad);

        panelCentral.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextArea(3, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        panelCentral.add(new JScrollPane(txtDescripcion));

        add(panelCentral, BorderLayout.CENTER);

        // ===== Panel Inferior (Botones) =====
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");
        btnVolver = new JButton("Volver");

        configurarBoton(btnGuardar);
        configurarBoton(btnLimpiar);
        configurarBoton(btnVolver);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);

        // ===== Eventos =====
        btnGuardar.addActionListener(e -> guardarEspecie());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnVolver.addActionListener(e -> dispose());
    }

    private void configurarBoton(JButton boton) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(new Color(41, 128, 185));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(31, 97, 141));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(41, 128, 185));
            }
        });
    }

    private void guardarEspecie() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String codigo = txtCodigo.getText();
            String nombre = txtNombre.getText();
            float densidad = Float.parseFloat(txtDensidad.getText());
            String descripcion = txtDescripcion.getText();

            EspecieVegetal especie = new EspecieVegetal(id, codigo, nombre, densidad, descripcion);
            listaEspecies.add(especie);

            JOptionPane.showMessageDialog(this,
                    "✅ Especie registrada con éxito:\n" + especie.toString(),
                    "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);

            limpiarCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "❌ Verifica los campos numéricos (ID y Densidad).",
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtCodigo.setText("");
        txtNombre.setText("");
        txtDensidad.setText("");
        txtDescripcion.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmEspecieVegetal().setVisible(true));
    }
}

