/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador;

/**
 *
 * @author edward
 */




public class Municipio {
    private String id;
    private String codigoDane;
    private String nombre;
    private Departamento departamento;

    public Municipio(String id, String codigoDane, String nombre, Departamento departamento) {
        this.id = id;
        this.codigoDane = codigoDane;
        this.nombre = nombre;
        this.departamento = departamento;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCodigoDane() { return codigoDane; }
    public void setCodigoDane(String codigoDane) { this.codigoDane = codigoDane; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Departamento getDepartamento() { return departamento; }
    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }

    @Override
    public String toString() {
        return "Municipio{" + "id=" + id + ", codigoDane=" + codigoDane + ", nombre=" + nombre +
                ", departamento=" + departamento.getNombre() + '}';
    }
}


