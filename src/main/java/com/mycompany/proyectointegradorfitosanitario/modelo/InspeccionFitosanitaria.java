/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Modelo InspeccionFitosanitaria
 * CORREGIDO para usar snake_case en BD
 */
package com.mycompany.proyectointegradorfitosanitario.modelo;

import java.time.LocalDateTime;
import java.util.UUID;

public class InspeccionFitosanitaria {
    private UUID id;
    private UUID lugarProduccionId;
    private UUID asistenteTecnicoId;
    private UUID propietarioId;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaInspeccion;
    private String estado;
    private String observaciones;
    private LocalDateTime fechaRegistro;
    
    // Campos adicionales para mostrar información
    private String nombreLugar;
    private String nombreAsistente;
    private String nombrePropietario;

    /**
     * Constructor completo
     */
    public InspeccionFitosanitaria(UUID id, UUID lugarProduccionId, UUID asistenteTecnicoId, 
                                   UUID propietarioId, LocalDateTime fechaSolicitud, 
                                   LocalDateTime fechaInspeccion, String estado, 
                                   String observaciones, LocalDateTime fechaRegistro) {
        this.id = id;
        this.lugarProduccionId = lugarProduccionId;
        this.asistenteTecnicoId = asistenteTecnicoId;
        this.propietarioId = propietarioId;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaInspeccion = fechaInspeccion;
        this.estado = estado;
        this.observaciones = observaciones;
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Constructor para crear nueva solicitud (solo datos necesarios)
     */
    public InspeccionFitosanitaria(UUID lugarProduccionId, UUID propietarioId, String observaciones) {
        this.lugarProduccionId = lugarProduccionId;
        this.propietarioId = propietarioId;
        this.observaciones = observaciones;
        this.estado = "PENDIENTE";
        this.fechaSolicitud = LocalDateTime.now();
        this.fechaRegistro = LocalDateTime.now();
    }

    // ==================== GETTERS Y SETTERS ====================
    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public UUID getLugarProduccionId() { return lugarProduccionId; }
    public void setLugarProduccionId(UUID lugarProduccionId) { this.lugarProduccionId = lugarProduccionId; }
    
    public UUID getAsistenteTecnicoId() { return asistenteTecnicoId; }
    public void setAsistenteTecnicoId(UUID asistenteTecnicoId) { this.asistenteTecnicoId = asistenteTecnicoId; }
    
    public UUID getPropietarioId() { return propietarioId; }
    public void setPropietarioId(UUID propietarioId) { this.propietarioId = propietarioId; }
    
    public LocalDateTime getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }
    
    public LocalDateTime getFechaInspeccion() { return fechaInspeccion; }
    public void setFechaInspeccion(LocalDateTime fechaInspeccion) { this.fechaInspeccion = fechaInspeccion; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    public String getNombreLugar() { return nombreLugar; }
    public void setNombreLugar(String nombreLugar) { this.nombreLugar = nombreLugar; }
    
    public String getNombreAsistente() { return nombreAsistente; }
    public void setNombreAsistente(String nombreAsistente) { this.nombreAsistente = nombreAsistente; }
    
    public String getNombrePropietario() { return nombrePropietario; }
    public void setNombrePropietario(String nombrePropietario) { this.nombrePropietario = nombrePropietario; }

    @Override
    public String toString() {
        return "InspeccionFitosanitaria{" +
                "id=" + id +
                ", estado='" + estado + '\'' +
                ", fechaSolicitud=" + fechaSolicitud +
                '}';
    }
}
