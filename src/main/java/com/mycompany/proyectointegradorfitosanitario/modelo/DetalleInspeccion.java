/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Modelo: DetalleInspeccion
 * Representa los datos levantados en una inspección
 */
package com.mycompany.proyectointegradorfitosanitario.modelo;

import java.util.UUID;

public class DetalleInspeccion {
    private UUID id;
    private UUID inspeccionId;
    private Integer especieVegetalId;
    private String plagaCodigo;
    private Integer totalPlantas;
    private Integer plantasAfectadas;
    private Double nivelIncidencia;
    private String observaciones;

    public DetalleInspeccion(UUID id, UUID inspeccionId, Integer especieVegetalId,
                            String plagaCodigo, Integer totalPlantas, Integer plantasAfectadas,
                            Double nivelIncidencia, String observaciones) {
        this.id = id;
        this.inspeccionId = inspeccionId;
        this.especieVegetalId = especieVegetalId;
        this.plagaCodigo = plagaCodigo;
        this.totalPlantas = totalPlantas;
        this.plantasAfectadas = plantasAfectadas;
        this.nivelIncidencia = nivelIncidencia;
        this.observaciones = observaciones;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getInspeccionId() { return inspeccionId; }
    public void setInspeccionId(UUID inspeccionId) { this.inspeccionId = inspeccionId; }

    public Integer getEspecieVegetalId() { return especieVegetalId; }
    public void setEspecieVegetalId(Integer especieVegetalId) { this.especieVegetalId = especieVegetalId; }

    public String getPlagaCodigo() { return plagaCodigo; }
    public void setPlagaCodigo(String plagaCodigo) { this.plagaCodigo = plagaCodigo; }

    public Integer getTotalPlantas() { return totalPlantas; }
    public void setTotalPlantas(Integer totalPlantas) { this.totalPlantas = totalPlantas; }

    public Integer getPlantasAfectadas() { return plantasAfectadas; }
    public void setPlantasAfectadas(Integer plantasAfectadas) { this.plantasAfectadas = plantasAfectadas; }

    public Double getNivelIncidencia() { return nivelIncidencia; }
    public void setNivelIncidencia(Double nivelIncidencia) { this.nivelIncidencia = nivelIncidencia; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    @Override
    public String toString() {
        return "DetalleInspeccion{" +
                "id=" + id +
                ", inspeccionId=" + inspeccionId +
                ", nivelIncidencia=" + nivelIncidencia +
                '}';
    }
}
