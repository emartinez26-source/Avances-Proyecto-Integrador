/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * DAO: DetalleInspeccionDAO
 * Acceso a datos de detalles de inspecciones
 */
package com.mycompany.proyectointegradorfitosanitario.dao;

import com.mycompany.proyectointegradorfitosanitario.config.DatabaseConnection;
import com.mycompany.proyectointegradorfitosanitario.modelo.DetalleInspeccion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DetalleInspeccionDAO {

    /**
     * Inserta un nuevo detalle de inspección
     */
    public boolean insertar(DetalleInspeccion detalle) {
        String sql = "INSERT INTO detalles_inspeccion " +
                     "(id, inspeccion_id, especie_vegetal_id, plaga_codigo, total_plantas, " +
                     "plantas_afectadas, nivel_incidencia, observaciones) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, detalle.getId());
            stmt.setObject(2, detalle.getInspeccionId());
            stmt.setInt(3, detalle.getEspecieVegetalId());
            stmt.setString(4, detalle.getPlagaCodigo());
            stmt.setInt(5, detalle.getTotalPlantas());
            stmt.setInt(6, detalle.getPlantasAfectadas());
            stmt.setDouble(7, detalle.getNivelIncidencia());
            stmt.setString(8, detalle.getObservaciones());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✅ Detalle de inspección guardado");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar detalle: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }

    /**
     * Lista detalles de una inspección
     */
    public List<DetalleInspeccion> listarPorInspeccion(UUID inspeccionId) {
        List<DetalleInspeccion> detalles = new ArrayList<>();
        
        String sql = "SELECT id, inspeccion_id, especie_vegetal_id, plaga_codigo, " +
                     "total_plantas, plantas_afectadas, nivel_incidencia, observaciones " +
                     "FROM detalles_inspeccion WHERE inspeccion_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, inspeccionId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DetalleInspeccion detalle = new DetalleInspeccion(
                        (UUID) rs.getObject("id"),
                        (UUID) rs.getObject("inspeccion_id"),
                        rs.getInt("especie_vegetal_id"),
                        rs.getString("plaga_codigo"),
                        rs.getInt("total_plantas"),
                        rs.getInt("plantas_afectadas"),
                        rs.getDouble("nivel_incidencia"),
                        rs.getString("observaciones")
                    );
                    detalles.add(detalle);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al listar detalles: " + e.getMessage());
        }
        
        return detalles;
    }
}