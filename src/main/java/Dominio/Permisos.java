/**
 * Permisos.java
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Dominio;

import Enumeradores.TipoPermiso;
import java.util.Calendar;
import java.util.Objects;

/**
 * Esta clase permite .
 *
 * @author Brandon Figueroa Ugalde ID: 00000233295
 * @author Guimel Naely Rubio Morillon ID: 00000229324
 */
public class Permisos {
    // Atributos
    private Integer id; 
    private Integer folio;
    private TipoPermiso tipo;
    private Calendar fechaConcesion;

    public Permisos() {
    }

    public Permisos(Integer id, Integer folio, TipoPermiso tipo, Calendar fechaConcesion) {
        this.id = id;
        this.folio = folio;
        this.tipo = tipo;
        this.fechaConcesion = fechaConcesion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFolio() {
        return folio;
    }

    public void setFolio(Integer folio) {
        this.folio = folio;
    }

    public TipoPermiso getTipo() {
        return tipo;
    }

    public void setTipo(TipoPermiso tipo) {
        this.tipo = tipo;
    }

    public Calendar getFechaConcesion() {
        return fechaConcesion;
    }

    public void setFechaConcesion(Calendar fechaConcesion) {
        this.fechaConcesion = fechaConcesion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final Permisos other = (Permisos) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Permisos{" + "id=" + id + ", folio=" + folio + ", tipo=" + tipo + ", fechaConcesion=" + fechaConcesion + '}';
    }
}
