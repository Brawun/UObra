/**
 * Ubicaciones.java
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Dominio;

import Enumeradores.TipoUbicacion;
import java.util.Objects;

/**
 * Esta clase permite .
 *
 * @author Brandon Figueroa Ugalde ID: 00000233295
 * @author Guimel Naely Rubio Morillon ID: 00000229324
 */
public class Ubicaciones {
    // Atributos
    private String direccion; 
    private TipoUbicacion tipo;
    private Float largo; 
    private Float ancho;
    private Float area;

    public Ubicaciones() {
    }

    public Ubicaciones(String direccion, TipoUbicacion tipo, Float largo, Float ancho, Float area) {
        this.direccion = direccion;
        this.tipo = tipo;
        this.largo = largo;
        this.ancho = ancho;
        this.area = area;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public TipoUbicacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoUbicacion tipo) {
        this.tipo = tipo;
    }

    public Float getLargo() {
        return largo;
    }

    public void setLargo(Float largo) {
        this.largo = largo;
    }

    public Float getAncho() {
        return ancho;
    }

    public void setAncho(Float ancho) {
        this.ancho = ancho;
    }

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.direccion);
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
        final Ubicaciones other = (Ubicaciones) obj;
        return Objects.equals(this.direccion, other.direccion);
    }

    @Override
    public String toString() {
        return "Ubicaciones{" + "direccion=" + direccion + ", tipo=" + tipo + ", largo=" + largo + ", ancho=" + ancho + ", area=" + area + '}';
    }
}
