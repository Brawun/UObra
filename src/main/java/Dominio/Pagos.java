/**
 * Pagos.java
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Dominio;

import Enumeradores.MetodoPago;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "Pagos")
public class Pagos implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "Monto", nullable = true)
    private Float monto;
    
    @Column(name = "Fecha", nullable = true)
    @Temporal(TemporalType.DATE)
    private Calendar fecha;
    
    @Column(name = "MetodoPago", nullable = true)
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;
    
    // Llave foránea
    @ManyToOne()
    @JoinColumn(name = "idObrero", referencedColumnName = "ID", nullable = true)
    private Obreros obrero;
            
    // Llave foránea
    @ManyToOne()
    @JoinColumn(name = "idObra", referencedColumnName = "ID", nullable = true)
    private Obras obra;

    public Pagos() {
    }

    public Pagos(Long id, Float monto, Calendar fecha, MetodoPago metodoPago, Obreros obrero, Obras obra) {
        this.id = id;
        this.monto = monto;
        this.fecha = fecha;
        this.metodoPago = metodoPago;
        this.obrero = obrero;
        this.obra = obra;
    }

    public Pagos(Float monto, Calendar fecha, MetodoPago metodoPago, Obreros obrero, Obras obra) {
        this.monto = monto;
        this.fecha = fecha;
        this.metodoPago = metodoPago;
        this.obrero = obrero;
        this.obra = obra;
    }

    public Pagos(Float monto, Calendar fecha, MetodoPago metodoPago, Obras obra) {
        this.monto = monto;
        this.fecha = fecha;
        this.metodoPago = metodoPago;
        this.obra = obra;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Obreros getObrero() {
        return obrero;
    }

    public void setObrero(Obreros obrero) {
        this.obrero = obrero;
    }

    public Obras getObra() {
        return obra;
    }

    public void setObra(Obras obra) {
        this.obra = obra;
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
        if (!(object instanceof Pagos)) {
            return false;
        }
        Pagos other = (Pagos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pagos{" + "id=" + id + ", monto=" + monto + ", fecha=" + fecha + ", metodoPago=" + metodoPago + ", obrero=" + obrero + ", obra=" + obra + '}';
    }
}
