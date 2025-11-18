/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * DAO InspeccionFitosanitaria
 * CORREGIDO para usar snake_case en BD (lugar_produccion_id, etc)
 */
package com.mycompany.proyectointegradorfitosanitario.dao;

import com.mycompany.proyectointegradorfitosanitario.config.DatabaseConnection;
import com.mycompany.proyectointegradorfitosanitario.modelo.InspeccionFitosanitaria;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InspeccionFitosanitariaDAO {

    /**
     * Inserta una nueva solicitud de inspección
     */
    public boolean solicitarInspeccion(InspeccionFitosanitaria inspeccion) {
        String sql = "INSERT INTO inspecciones_fitosanitarias " +
                     "(lugar_produccion_id, propietario_id, fecha_solicitud, estado, observaciones) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, inspeccion.getLugarProduccionId());
            stmt.setObject(2, inspeccion.getPropietarioId());
            stmt.setTimestamp(3, Timestamp.valueOf(inspeccion.getFechaSolicitud()));
            stmt.setString(4, inspeccion.getEstado());
            stmt.setString(5, inspeccion.getObservaciones());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✅ Solicitud de inspección creada correctamente");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al solicitar inspección: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }

    /**
     * Lista las inspecciones de un propietario
     */
    public List<InspeccionFitosanitaria> listarPorPropietario(UUID propietarioId) {
        List<InspeccionFitosanitaria> inspecciones = new ArrayList<>();
        
        String sql = "SELECT i.id, i.lugar_produccion_id, i.asistente_tecnico_id, i.propietario_id, " +
                     "i.fecha_solicitud, i.fecha_inspeccion, i.estado, i.observaciones, i.fecha_registro, " +
                     "l.nombre AS lugar_nombre, " +
                     "a.nombre AS asistente_nombre " +
                     "FROM inspecciones_fitosanitarias i " +
                     "LEFT JOIN lugares_produccion l ON i.lugar_produccion_id = l.id " +
                     "LEFT JOIN asistentes_tecnicos a ON i.asistente_tecnico_id = a.id " +
                     "WHERE i.propietario_id = ? " +
                     "ORDER BY i.fecha_solicitud DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, propietarioId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    InspeccionFitosanitaria inspeccion = mapearInspeccion(rs);
                    inspeccion.setNombreLugar(rs.getString("lugar_nombre"));
                    inspeccion.setNombreAsistente(rs.getString("asistente_nombre"));
                    inspecciones.add(inspeccion);
                }
            }
            
            System.out.println("✅ Se encontraron " + inspecciones.size() + " inspecciones");
            
        } catch (SQLException e) {
            System.err.println("❌ Error al listar inspecciones: " + e.getMessage());
        }
        
        return inspecciones;
    }

    /**
     * Lista las inspecciones pendientes (para asistentes)
     */
    public List<InspeccionFitosanitaria> listarPendientes() {
        List<InspeccionFitosanitaria> inspecciones = new ArrayList<>();
        
        String sql = "SELECT i.id, i.lugar_produccion_id, i.asistente_tecnico_id, i.propietario_id, " +
                     "i.fecha_solicitud, i.fecha_inspeccion, i.estado, i.observaciones, i.fecha_registro, " +
                     "l.nombre AS lugar_nombre, " +
                     "p.nombre AS propietario_nombre " +
                     "FROM inspecciones_fitosanitarias i " +
                     "LEFT JOIN lugares_produccion l ON i.lugar_produccion_id = l.id " +
                     "LEFT JOIN propietarios p ON i.propietario_id = p.id " +
                     "WHERE i.estado = 'PENDIENTE' " +
                     "ORDER BY i.fecha_solicitud ASC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                InspeccionFitosanitaria inspeccion = mapearInspeccion(rs);
                inspeccion.setNombreLugar(rs.getString("lugar_nombre"));
                inspeccion.setNombrePropietario(rs.getString("propietario_nombre"));
                inspecciones.add(inspeccion);
            }
            
            System.out.println("✅ " + inspecciones.size() + " inspecciones pendientes");
            
        } catch (SQLException e) {
            System.err.println("❌ Error al listar pendientes: " + e.getMessage());
        }
        
        return inspecciones;
    }

    /**
     * Asigna un asistente técnico a una inspección pendiente
     */
    public boolean asignarAsistente(UUID inspeccionId, UUID asistenteId) {
        String sql = "UPDATE inspecciones_fitosanitarias " +
                     "SET asistente_tecnico_id = ?, estado = 'EN_PROCESO' " +
                     "WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, asistenteId);
            stmt.setObject(2, inspeccionId);
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✅ Asistente asignado a la inspección");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al asignar asistente: " + e.getMessage());
        }
        
        return false;
    }

    /**
     * Completa una inspección (cuando el asistente la finaliza)
     */
    public boolean completarInspeccion(UUID inspeccionId, String observaciones) {
        String sql = "UPDATE inspecciones_fitosanitarias " +
                     "SET estado = 'COMPLETADA', fecha_inspeccion = ?, observaciones = ? " +
                     "WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, Date.valueOf(java.time.LocalDate.now()));
            stmt.setString(2, observaciones);
            stmt.setObject(3, inspeccionId);
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✅ Inspección completada correctamente");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al completar inspección: " + e.getMessage());
        }
        
        return false;
    }

    /**
     * Cancela una inspección
     */
    public boolean cancelarInspeccion(UUID inspeccionId) {
        String sql = "UPDATE inspecciones_fitosanitarias " +
                     "SET estado = 'CANCELADA' " +
                     "WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, inspeccionId);
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✅ Inspección cancelada");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al cancelar inspección: " + e.getMessage());
        }
        
        return false;
    }

    /**
     * Busca una inspección por su ID
     */
    public InspeccionFitosanitaria buscarPorId(UUID id) {
        String sql = "SELECT id, lugar_produccion_id, asistente_tecnico_id, propietario_id, " +
                     "fecha_solicitud, fecha_inspeccion, estado, observaciones, fecha_registro " +
                     "FROM inspecciones_fitosanitarias WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearInspeccion(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar inspección: " + e.getMessage());
        }
        
        return null;
    }

    /**
     * Mapea un ResultSet a un objeto InspeccionFitosanitaria
     * USA NOMBRES DE COLUMNAS CON GUION BAJO (snake_case)
     */
    private InspeccionFitosanitaria mapearInspeccion(ResultSet rs) throws SQLException {
        UUID id = (UUID) rs.getObject("id");
        UUID lugarId = (UUID) rs.getObject("lugar_produccion_id");
        UUID asistenteId = (UUID) rs.getObject("asistente_tecnico_id");
        UUID propietarioId = (UUID) rs.getObject("propietario_id");
        LocalDateTime fechaSolicitud = rs.getTimestamp("fecha_solicitud").toLocalDateTime();
        
        Date fechaInspeccionDate = rs.getDate("fecha_inspeccion");
        LocalDateTime fechaInspeccion = (fechaInspeccionDate != null) 
                ? fechaInspeccionDate.toLocalDate().atStartOfDay() : null;
        
        String estado = rs.getString("estado");
        String observaciones = rs.getString("observaciones");
        LocalDateTime fechaRegistro = rs.getTimestamp("fecha_registro").toLocalDateTime();
        
        return new InspeccionFitosanitaria(
            id, lugarId, asistenteId, propietarioId,
            fechaSolicitud, fechaInspeccion, estado,
            observaciones, fechaRegistro
        );
    }
    
    /**
     * Lista todas las inspecciones asignadas a un asistente (EN_PROCESO y COMPLETADA)
     * @param asistenteId UUID del asistente
     * @return Lista de inspecciones del asistente
     */
    public List<InspeccionFitosanitaria> listarPorAsistente(UUID asistenteId) {
        List<InspeccionFitosanitaria> inspecciones = new ArrayList<>();
        
        String sql = "SELECT i.id, i.lugar_produccion_id, i.asistente_tecnico_id, i.propietario_id, " +
                     "i.fecha_solicitud, i.fecha_inspeccion, i.estado, i.observaciones, i.fecha_registro, " +
                     "l.nombre AS lugar_nombre, " +
                     "p.nombre AS propietario_nombre " +
                     "FROM inspecciones_fitosanitarias i " +
                     "LEFT JOIN lugares_produccion l ON i.lugar_produccion_id = l.id " +
                     "LEFT JOIN propietarios p ON i.propietario_id = p.id " +
                     "WHERE i.asistente_tecnico_id = ? " +
                     "AND i.estado = 'EN_PROCESO'  " +
                     "ORDER BY i.fecha_inspeccion DESC NULLS LAST, i.fecha_solicitud DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, asistenteId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    InspeccionFitosanitaria inspeccion = mapearInspeccion(rs);
                    inspeccion.setNombreLugar(rs.getString("lugar_nombre"));
                    inspeccion.setNombrePropietario(rs.getString("propietario_nombre"));
                    inspecciones.add(inspeccion);
                }
            }
            
            System.out.println("✅ Se encontraron " + inspecciones.size() + " inspecciones para el asistente");
            
        } catch (SQLException e) {
            System.err.println("❌ Error al listar inspecciones por asistente: " + e.getMessage());
        }
        
        return inspecciones;
    }
    
    /**
    * Lista todas las inspecciones COMPLETADA del sistema
    * @return Lista de inspecciones completadas
    */
   public List<InspeccionFitosanitaria> listarCompletadas() {
       List<InspeccionFitosanitaria> inspecciones = new ArrayList<>();

       String sql = "SELECT i.id, i.lugar_produccion_id, i.asistente_tecnico_id, i.propietario_id, " +
                    "i.fecha_solicitud, i.fecha_inspeccion, i.estado, i.observaciones, i.fecha_registro, " +
                    "l.nombre AS lugar_nombre, " +
                    "p.nombre AS propietario_nombre, " +
                    "a.nombre AS asistente_nombre " +
                    "FROM inspecciones_fitosanitarias i " +
                    "LEFT JOIN lugares_produccion l ON i.lugar_produccion_id = l.id " +
                    "LEFT JOIN propietarios p ON i.propietario_id = p.id " +
                    "LEFT JOIN asistentes_tecnicos a ON i.asistente_tecnico_id = a.id " +
                    "WHERE i.estado = 'COMPLETADA' " +
                    "ORDER BY i.fecha_inspeccion DESC NULLS LAST";

       try (Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

           while (rs.next()) {
               InspeccionFitosanitaria inspeccion = mapearInspeccion(rs);
               inspeccion.setNombreLugar(rs.getString("lugar_nombre"));
               inspeccion.setNombrePropietario(rs.getString("propietario_nombre"));
               inspeccion.setNombreAsistente(rs.getString("asistente_nombre"));
               inspecciones.add(inspeccion);
           }

           System.out.println("✅ Se encontraron " + inspecciones.size() + " inspecciones completadas");

       } catch (SQLException e) {
           System.err.println("❌ Error al listar completadas: " + e.getMessage());
       }

       return inspecciones;
   }

    
    

}