/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador;

/**
 *
 * @author edward
 */


public class Departamento {
    private int id;
    private String codigoDane;
    private String nombre;

    public Departamento() {
    }

    public Departamento(int id, String codigoDane, String nombre) {
        this.id = id;
        this.codigoDane = codigoDane;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoDane() {
        return codigoDane;
    }

    public void setCodigoDane(String codigoDane) {
        this.codigoDane = codigoDane;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "id=" + id +
                ", codigoDane='" + codigoDane + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}



