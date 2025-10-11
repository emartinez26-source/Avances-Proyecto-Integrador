/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador;

/**
 *
 * @author edward
 */



public class Predio {
    private String id;
    private String numeroRegistro;
    private String nombre;
    private float area;
    private Vereda vereda;

    public Predio(String id, String numeroRegistro, String nombre, float area, Vereda vereda) {
        this.id = id;
        this.numeroRegistro = numeroRegistro;
        this.nombre = nombre;
        this.area = area;
        this.vereda = vereda;
    }

    public String getId() { return id; }
    public String getNumeroRegistro() { return numeroRegistro; }
    public String getNombre() { return nombre; }
    public float getArea() { return area; }
    public Vereda getVereda() { return vereda; }

    public void setId(String id) { this.id = id; }
    public void setNumeroRegistro(String numeroRegistro) { this.numeroRegistro = numeroRegistro; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setArea(float area) { this.area = area; }
    public void setVereda(Vereda vereda) { this.vereda = vereda; }

    @Override
    public String toString() {
        return "Predio{" + "id=" + id + ", numeroRegistro=" + numeroRegistro + ", nombre=" + nombre +
                ", area=" + area + ", vereda=" + vereda.getNombre() + '}';
    }
}
