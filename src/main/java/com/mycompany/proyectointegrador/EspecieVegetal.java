/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador;

/**
 *
 * @author edward
 */


public class EspecieVegetal {
    private int id;
    private String codigoRegistro;
    private String nombre;
    private float densidad;
    private String descripcion;

    public EspecieVegetal(int id, String codigoRegistro, float densidad, String nombre) {
        this.id = id;
        this.codigoRegistro = codigoRegistro;
        this.nombre = nombre;
        this.densidad = densidad;
        this.descripcion = descripcion;
    }

    // Getters y setters
}
