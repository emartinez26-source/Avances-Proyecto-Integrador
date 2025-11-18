/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.vista;

import com.mycompany.proyectointegradorfitosanitario.dao.DepartamentoDAO;
import com.mycompany.proyectointegradorfitosanitario.dao.MunicipioDAO;
import com.mycompany.proyectointegradorfitosanitario.dao.VeredaDAO;
import com.mycompany.proyectointegradorfitosanitario.modelo.Municipio;
import com.mycompany.proyectointegradorfitosanitario.modelo.Usuario;
import com.mycompany.proyectointegradorfitosanitario.modelo.Vereda;
import com.mycompany.proyectointegradorfitosanitario.modelo.Departamento;
import com.mycompany.proyectointegradorfitosanitario.dao.PredioDAO;
import com.mycompany.proyectointegradorfitosanitario.modelo.Predio;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FrmCrearPredio extends JFrame {
    private Usuario propietario;
    private DepartamentoDAO deptDAO;
    private MunicipioDAO munDAO;
    private VeredaDAO veredaDAO;
    
    private JTextField txtNombre;
    private JTextField txtArea;
    private JComboBox<Departamento> comboDepartamentos;
    private JComboBox<Municipio> comboMunicipios;
    private JComboBox<Object> comboVeredas;
    private Integer municipioIdActual;  // ← Para saber a qué municipio pertenece la vereda

    
    public FrmCrearPredio(Usuario usuario) {
        this.propietario = usuario;
        this.deptDAO = new DepartamentoDAO();
        this.munDAO = new MunicipioDAO();
        this.veredaDAO = new VeredaDAO();
        inicializarComponentes();
        cargarDepartamentos();
    }
    
    private void inicializarComponentes() {
        setTitle("Crear Predio");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 450); // Reducimos altura
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(240, 240, 240));

        int y = 20;
        int altura = 30;
        int espacio = 45;

        JLabel lbl1 = new JLabel("Nombre del Predio:");
        lbl1.setBounds(20, y, 460, 20);
        panel.add(lbl1);
        txtNombre = new JTextField();
        txtNombre.setBounds(20, y += 25, 460, altura);
        panel.add(txtNombre);

        // ELIMINAMOS el campo de Número Registro
        // El sistema lo genera automáticamente

        JLabel lbl2 = new JLabel("Área (hectáreas):");
        lbl2.setBounds(20, y += espacio, 460, 20);
        panel.add(lbl2);
        txtArea = new JTextField();
        txtArea.setBounds(20, y += 25, 460, altura);
        panel.add(txtArea);

        JLabel lbl3 = new JLabel("Departamento:");
        lbl3.setBounds(20, y += espacio, 460, 20);
        panel.add(lbl3);
        comboDepartamentos = new JComboBox<>();
        comboDepartamentos.setBounds(20, y += 25, 460, altura);
        comboDepartamentos.addActionListener(e -> actualizarMunicipios());
        panel.add(comboDepartamentos);

        JLabel lbl4 = new JLabel("Municipio:");
        lbl4.setBounds(20, y += espacio, 460, 20);
        panel.add(lbl4);
        comboMunicipios = new JComboBox<>();
        comboMunicipios.setBounds(20, y += 25, 460, altura);
        comboMunicipios.addActionListener(e -> actualizarVeredas());
        panel.add(comboMunicipios);

        JLabel lbl5 = new JLabel("Vereda:");
        lbl5.setBounds(20, y += espacio, 460, 20);
        panel.add(lbl5);
        comboVeredas = new JComboBox<>();
        comboVeredas.setEditable(true);
        comboVeredas.setBounds(20, y += 25, 460, altura);
        panel.add(comboVeredas);

        JButton btnGuardar = new JButton("💾 Guardar");
        btnGuardar.setBounds(100, y += espacio, 150, 35);
        btnGuardar.setBackground(new Color(76, 175, 80));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.addActionListener(e -> guardar());
        panel.add(btnGuardar);

        JButton btnCancelar = new JButton("❌ Cancelar");
        btnCancelar.setBounds(260, y, 150, 35);
        btnCancelar.setBackground(new Color(244, 67, 54));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.addActionListener(e -> this.dispose());
        panel.add(btnCancelar);

        setContentPane(panel);
    }

    
    private void cargarDepartamentos() {
        List<Departamento> deptos = deptDAO.listarTodos();
        for (Departamento d : deptos) {
            comboDepartamentos.addItem(d);
        }
    }
    
    private void actualizarMunicipios() {
        Departamento depto = (Departamento) comboDepartamentos.getSelectedItem();
        if (depto == null) return;
        
        comboMunicipios.removeAllItems();
        List<Municipio> municipios = munDAO.listarPorDepartamento(depto.getId());
        for (Municipio m : municipios) {
            comboMunicipios.addItem(m);
        }
    }
    
    private void actualizarVeredas() {
        Municipio municipio = (Municipio) comboMunicipios.getSelectedItem();
        if (municipio == null) return;

        this.municipioIdActual = municipio.getId();  // ← AGREGAR ESTA LÍNEA

        comboVeredas.removeAllItems();
        List<Vereda> veredas = veredaDAO.listarPorMunicipio(municipio.getId());
        for (Vereda v : veredas) {
            comboVeredas.addItem(v);
        }
    }

    
    private void guardar() {
        if (txtNombre.getText().trim().isEmpty() || 
            txtArea.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double area = Double.parseDouble(txtArea.getText());

            // Obtener vereda (escrita o seleccionada)
            Object itemSeleccionado = comboVeredas.getSelectedItem();
            Integer veredaId;

            if (itemSeleccionado instanceof Vereda) {
                // Usuario seleccionó de la lista
                Vereda vereda = (Vereda) itemSeleccionado;
                veredaId = vereda.getId();
            } else if (itemSeleccionado instanceof String) {
                // Usuario escribió manualmente
                String veredaNombre = ((String) itemSeleccionado).trim();

                if (veredaNombre.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debes escribir o seleccionar una vereda", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Buscar si existe
                Vereda veredaExistente = veredaDAO.buscarPorNombre(veredaNombre, municipioIdActual);

                if (veredaExistente == null) {
                    // Crear nueva vereda
                    // Crear nueva vereda directamente con constructor
                    Vereda nuevaVereda = new Vereda(0, veredaNombre, municipioIdActual);
                    veredaId = veredaDAO.insertar(nuevaVereda);


                    if (veredaId == null) {
                        JOptionPane.showMessageDialog(this, "❌ Error al crear vereda", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    System.out.println("✅ Nueva vereda creada: " + veredaNombre);
                } else {
                    veredaId = veredaExistente.getId();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debes seleccionar o escribir una vereda", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear objeto Predio
            Predio nuevoPredio = new Predio(
                txtNombre.getText().trim(),
                "", // Vacío, lo genera el DAO
                area,
                propietario.getId(),
                veredaId
            );

            // Guardar en BD
            PredioDAO dao = new PredioDAO();
            if (dao.insertar(nuevoPredio)) {
                JOptionPane.showMessageDialog(this, 
                    "✅ Predio creado correctamente\n" +
                    "El sistema generó automáticamente\n" +
                    "el número de registro ICA", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "❌ Error al crear predio", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Área debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



}

