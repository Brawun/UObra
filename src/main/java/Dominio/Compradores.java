/**
 * Compradores.java
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Dominio;

import java.util.Objects;

/**
 * Esta clase permite .
 *
 * @author Brandon Figueroa Ugalde id: 00000233295
 * @author Guimel Naely Rubio Morillon id: 00000229324
 */
public class Compradores {
    // Atributos
    private Integer id;
    private String nombre; 
    private String apellidoPaterno; 
    private String apellidoMaterno;
    private String telefono;

    public Compradores() {
    }

    public Compradores(Integer ID, String Nombre, String ApellidoPaterno, String ApellidoMaterno, String Telefono) {
        this.id = ID;
        this.nombre = Nombre;
        this.apellidoPaterno = ApellidoPaterno;
        this.apellidoMaterno = ApellidoMaterno;
        this.telefono = Telefono;
    }

    public Integer getID() {
        return id;
    }

    public void setID(Integer ID) {
        this.id = ID;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String Nombre) {
        this.nombre = Nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.apellidoPaterno = ApellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.apellidoMaterno = ApellidoMaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String Telefono) {
        this.telefono = Telefono;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Compradores other = (Compradores) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Compradores{" + "ID=" + id + ", Nombre=" + nombre + ", ApellidoPaterno=" + apellidoPaterno + ", ApellidoMaterno=" + apellidoMaterno + ", Telefono=" + telefono + '}';
    }
}
