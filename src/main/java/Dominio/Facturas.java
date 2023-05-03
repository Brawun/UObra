/**
 * Facturas.java
 */
package Dominio;

import Enumeradores.EstadoFactura;
import Enumeradores.MetodoPago;
import Escucha.FacturasEscucha;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
 * Esta entidad permite mapear una Facturas con todos sus atributos.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
@Entity
@EntityListeners({FacturasEscucha.class})
@Table(name = "Facturas")
public class Facturas implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Monto", nullable = false)
    private Float monto;
    
    @Column(name = "Descripcion", nullable = false)
    private String descripcion;

    // AUTOGENERADA
    @Column(name = "FechaCreada", nullable = true)
    @Temporal(TemporalType.DATE)
    private Calendar fechaCreada;
    
    @Column(name = "FechaPagada", nullable = true)
    @Temporal(TemporalType.DATE)
    private Calendar fechaPagada;

    @Column(name = "Estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoFactura estado = EstadoFactura.PENDIENTE;

    @Column(name = "MetodoPago", nullable = false)
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago = MetodoPago.NO_APLICA;

    // Llave foránea
    // Muchas facturas pueden ser generadas por un jefe
    @ManyToOne()
    @JoinColumn(name = "idJefe", referencedColumnName = "ID", nullable = false)
    private Jefes jefe;

    public Facturas() {
    }

    public Facturas(Long id, Float monto, String descripcion, Calendar fechaCreada, Calendar fechaPagada, Jefes jefe) {
        this.id = id;
        this.monto = monto;
        this.descripcion = descripcion;
        this.fechaCreada = fechaCreada;
        this.fechaPagada = fechaPagada;
        this.jefe = jefe;
    }

    public Facturas(Float monto, Calendar fecha, EstadoFactura estado, MetodoPago metodoPago, Jefes jefe) {
        this.monto = monto;
        this.fechaCreada = fecha;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.jefe = jefe;
    }

    public Facturas(Float monto, String descripcion, Jefes jefe) {
        this.monto = monto;
        this.descripcion = descripcion;
        this.jefe = jefe;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Calendar getFechaCreada() {
        return fechaCreada;
    }

    public void setFechaCreada(Calendar fechaCreada) {
        this.fechaCreada = fechaCreada;
    }

    public Calendar getFechaPagada() {
        return fechaPagada;
    }

    public void setFechaPagada(Calendar fechaPagada) {
        this.fechaPagada = fechaPagada;
    }

    public EstadoFactura getEstado() {
        return estado;
    }

    public void setEstado(EstadoFactura estado) {
        this.estado = estado;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Facturas)) {
            return false;
        }
        Facturas other = (Facturas) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "Facturas{" + "id=" + id + ", monto=" + monto + ", descripcion=" + descripcion + ", fechaCreada=" + fechaCreada + ", fechaPagada=" + fechaPagada + ", estado=" + estado + ", metodoPago=" + metodoPago + ", jefe=" + jefe + '}';
    }
}
