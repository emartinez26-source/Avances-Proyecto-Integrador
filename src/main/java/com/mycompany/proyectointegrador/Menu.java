/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectointegrador;

/**
 *
 * @author edward
 */




import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private ArrayList<Departamento> departamentos;
    private ArrayList<Municipio> municipios;
    private final Scanner sc;

    public Menu() {
        departamentos = new ArrayList<>();
        municipios = new ArrayList<>();
        sc = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;
        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Registrar Departamento");
            System.out.println("2. Registrar Municipio");
            System.out.println("3. Mostrar Departamentos");
            System.out.println("4. Mostrar Municipios");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> registrarDepartamento();
                case 2 -> registrarMunicipio();
                case 3 -> mostrarDepartamentos();
                case 4 -> mostrarMunicipios();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 0);
    }

    private void registrarDepartamento() {
        System.out.println("\n--- Registrar Departamento ---");
        System.out.print("ID: ");
        String id = sc.nextLine();
        System.out.print("Código DANE: ");
        String codigo = sc.nextLine();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        Departamento d = new Departamento(1, "05", "Antioquia");
        departamentos.add(d);
        System.out.println("✅ Departamento registrado correctamente.");
    }

    private void registrarMunicipio() {
        if (departamentos.isEmpty()) {
            System.out.println("⚠️ Primero debe registrar un Departamento.");
            return;
        }

        System.out.println("\n--- Registrar Municipio ---");
        System.out.print("ID: ");
        String id = sc.nextLine();
        System.out.print("Código DANE: ");
        String codigo = sc.nextLine();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        // Mostrar departamentos disponibles
        System.out.println("Seleccione el Departamento:");
        for (int i = 0; i < departamentos.size(); i++) {
            System.out.println((i + 1) + ". " + departamentos.get(i).getNombre());
        }
        System.out.print("Opción: ");
        int index = sc.nextInt() - 1;
        sc.nextLine();

        Departamento dep = departamentos.get(index);
        Municipio m = new Municipio(id, codigo, nombre, dep);
        municipios.add(m);

        System.out.println("✅ Municipio registrado correctamente.");
    }

    private void mostrarDepartamentos() {
        System.out.println("\n--- Lista de Departamentos ---");
        for (Departamento d : departamentos) {
            System.out.println(d);
        }
    }

    private void mostrarMunicipios() {
        System.out.println("\n--- Lista de Municipios ---");
        for (Municipio m : municipios) {
            System.out.println(m);
        }
    }
}


