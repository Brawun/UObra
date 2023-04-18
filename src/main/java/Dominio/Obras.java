/**
 * Obras.java
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Dominio;

import Enumeradores.EstadoObra;
import java.util.Calendar;
import java.util.Objects;

/**
 * Esta clase permite .
 *
 * @author Brandon Figueroa Ugalde id: 00000233295
 * @author Guimel Naely Rubio Morillon id: 00000229324
 */
public class Obras {
    // Atributos
    private Integer id;
    private EstadoObra estado;
    private Float inversion;
    private String nombre; 
    private Calendar fechaInicio; 
    private Calendar fechaFin;

    public Obras() {
    }

    public Obras(Integer id, EstadoObra estado, Float inversion, String nombre, Calendar fechaInicio, Calendar fechaFin) {
        this.id = id;
        this.estado = estado;
        this.inversion = inversion;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EstadoObra getEstado() {
        return estado;
    }

    public void setEstado(EstadoObra estado) {
        this.estado = estado;
    }

    public Float getInversion() {
        return inversion;
    }

    public void setInversion(Float inversion) {
        this.inversion = inversion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Calendar getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Calendar fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Calendar getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Calendar fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.id);
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
        final Obras other = (Obras) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Obras{" + "id=" + id + ", estado=" + estado + ", inversion=" + inversion + ", nombre=" + nombre + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + '}';
    }
}
