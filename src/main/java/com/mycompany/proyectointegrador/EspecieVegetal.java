/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador;

/**
 *
 * @author edward
 */




/**
 * Representa una especie vegetal registrada para inspección fitosanitaria.
 * 
 * @author edward
 */
public class EspecieVegetal {

    private int id;
    private String codigoRegistro;
    private String nombre;
    private float densidad;
    private String descripcion;

    
    public EspecieVegetal(int id, String codigoRegistro, String nombre, float densidad, String descripcion) {
        this.id = id;
        this.codigoRegistro = codigoRegistro;
        this.nombre = nombre;
        this.densidad = densidad;
        this.descripcion = descripcion;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoRegistro() {
        return codigoRegistro;
    }

    public void setCodigoRegistro(String codigoRegistro) {
        this.codigoRegistro = codigoRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getDensidad() {
        return densidad;
    }

    public void setDensidad(float densidad) {
        this.densidad = densidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // 🔹 Método toString (útil para depuración o impresión)
    @Override
    public String toString() {
        return "EspecieVegetal{" +
                "id=" + id +
                ", codigoRegistro='" + codigoRegistro + '\'' +
                ", nombre='" + nombre + '\'' +
                ", densidad=" + densidad +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}