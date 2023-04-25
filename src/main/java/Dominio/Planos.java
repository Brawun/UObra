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
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Esta entidad permite mapear # con todos sus atributos.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
@Entity
@Table(name = "Planos")
public class Planos implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "Folio", unique = true, nullable = false)
    private String folio;

    @Column(name = "Tipo", nullable = true)
    @Enumerated(EnumType.STRING)
    private TipoPlano tipo;
    
    @Column(name = "Escala", nullable = true)
    @Enumerated(EnumType.STRING)
    private Escala escala;
    
    @Column(name = "FechaRealización", nullable = true)
    @Temporal(TemporalType.DATE)
    private Calendar fechaRealización;
    
    // Llave foránea
    @ManyToOne()
    @JoinColumn(name = "idJefe", referencedColumnName = "ID", nullable = true)
    private Jefes jefe;
    
    @ManyToMany(mappedBy = "planos")
    List<Obras> obras;

    public Planos(Long id, TipoPlano tipo, Escala escala, Calendar fechaRealización) {
        this.id = id;
        this.tipo = tipo;
        this.escala = escala;
        this.fechaRealización = fechaRealización;
    }

    public Planos(TipoPlano tipo, Escala escala, Calendar fechaRealización) {
        this.tipo = tipo;
        this.escala = escala;
        this.fechaRealización = fechaRealización;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
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

    public Calendar getFechaRealización() {
        return fechaRealización;
    }

    public void setFechaRealización(Calendar fechaRealización) {
        this.fechaRealización = fechaRealización;
    }

    public Jefes getJefe() {
        return jefe;
    }

    public void setJefe(Jefes jefe) {
        this.jefe = jefe;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Planos)) {
            return false;
        }
        Planos other = (Planos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Planos{" + "id=" + id + ", folio=" + folio + ", tipo=" + tipo + ", escala=" + escala + ", fechaRealizaci\u00f3n=" + fechaRealización + ", jefe=" + jefe + '}';
    }
}
