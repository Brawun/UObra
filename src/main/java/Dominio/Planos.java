/**
 * Planos.java
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Dominio;

import Enumeradores.Escala;
import Enumeradores.TipoPlano;
import java.util.Calendar;
import java.util.Objects;

/**
 * Esta clase permite .
 *
 * @author Brandon Figueroa Ugalde ID: 00000233295
 * @author Guimel Naely Rubio Morillon ID: 00000229324
 */
public class Planos {
    // Atributos
    private Integer id; 
    private TipoPlano tipo; 
    private Escala escala;
    private Calendar fechaRealizacion;

    public Planos() {
    }

    public Planos(Integer id, TipoPlano tipo, Escala escala, Calendar fechaRealizacion) {
        this.id = id;
        this.tipo = tipo;
        this.escala = escala;
        this.fechaRealizacion = fechaRealizacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoPlano getTipo() {
        return tipo;
    }

    public void setTipo(TipoPlano tipo) {
        this.tipo = tipo;
    }

    public Escala getEscala() {
        return escala;
    }

    public void setEscala(Escala escala) {
        this.escala = escala;
    }

    public Calendar getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(Calendar fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final Planos other = (Planos) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Planos{" + "id=" + id + ", tipo=" + tipo + ", escala=" + escala + ", fechaRealizacion=" + fechaRealizacion + '}';
    }
}
