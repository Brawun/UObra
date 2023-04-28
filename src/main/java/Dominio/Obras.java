/**
 * Obras.java
 */
package Dominio;

import Enumeradores.EstadoObra;
import java.util.List;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Esta entidad permite mapear una Obra con todos sus atributos.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
@Entity
@Table(name = "Obras")
public class Obras implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Estado", nullable = true)
    @Enumerated(EnumType.STRING)
    EstadoObra estado = EstadoObra.EN_ESPERA;

    @Column(name = "CostoArranque", nullable = false)
    private Float costoArranque;

    @Column(name = "Inversion", nullable = false)
    private Float inversion;
    
    // AUTOGENERADA
    @Column(name = "CostoTotal", nullable = true)
    private Float costoTotal;
    
    // AUTOGENERADA
    @Column(name = "Deuda", nullable = true)
    private Float deuda;

    @Column(name = "Pagada", nullable = false)
    private Boolean estaPagada = false;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    // AUTOGENERADA
    @Column(name = "FechaSolicitada", nullable = true)
    @Temporal(TemporalType.DATE)
    private Calendar fechaSolicitada;

    @Column(name = "FechaInicio", nullable = true)
    @Temporal(TemporalType.DATE)
    private Calendar fechaInicio;

    @Column(name = "FechaFin", nullable = true)
    @Temporal(TemporalType.DATE)
    private Calendar fechaFin;

    // Llave foránea
    // Muchas obras pueden ser aceptadas por un jefe
    @ManyToOne()
    @JoinColumn(name = "idJefe", referencedColumnName = "ID", nullable = true)
    private Jefes jefe;

    // Llave foránea
    // Muchas obras pueden ser solictadas por un cliente
    @ManyToOne()
    @JoinColumn(name = "idCliente", referencedColumnName = "ID", nullable = true)
    private Clientes cliente;

    // Una obra tiene muchos pagos
    @OneToMany(mappedBy = "obra")
    private List<Pagos> pagos;

    // Una obra tiene muchos obreros
    @OneToMany(mappedBy = "obra")
    private List<ObrasObrero> obreros;

    // Muchas obras pueden tener muchos planos
    @ManyToMany()
    @JoinTable(
            name = "planosObra",
            joinColumns = @JoinColumn(name = "idObra", referencedColumnName = "ID", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "idPlano", referencedColumnName = "ID", nullable = false)
    )
    private List<Planos> planos;

    // Muchas obras pueden tener muchos permisos
    @ManyToMany()
    @JoinTable(
            name = "permisosObra",
            joinColumns = @JoinColumn(name = "idObra", referencedColumnName = "ID", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "idPermiso", referencedColumnName = "ID", nullable = false)
    )
    private List<Permisos> permisos;

    // Muchas obras pueden tener muchas ubicaciones
    @ManyToMany()
    @JoinTable(
            name = "ubicacionesObra",
            joinColumns = @JoinColumn(name = "idObra", referencedColumnName = "ID", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "idUbicacion", referencedColumnName = "ID", nullable = false)
    )
    private List<Ubicaciones> ubicaciones;

    public Obras() {
    }

    public Obras(Long id, Float costoArranque, Float inversion, Float costoTotal, Float deuda, String nombre, Calendar fechaSolicitada, Calendar fechaInicio, Calendar fechaFin, Jefes jefe, Clientes cliente, List<Pagos> pagos, List<ObrasObrero> obreros, List<Planos> planos, List<Permisos> permisos, List<Ubicaciones> ubicaciones) {
        this.id = id;
        this.costoArranque = costoArranque;
        this.inversion = inversion;
        this.costoTotal = costoTotal;
        this.deuda = deuda;
        this.nombre = nombre;
        this.fechaSolicitada = fechaSolicitada;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.jefe = jefe;
        this.cliente = cliente;
        this.pagos = pagos;
        this.obreros = obreros;
        this.planos = planos;
        this.permisos = permisos;
        this.ubicaciones = ubicaciones;
    }

    public Obras(Float costoArranque, Float inversion, Float deuda, String nombre, Calendar fechaSolicitada, Calendar fechaInicio, Calendar fechaFin, Jefes jefe, Clientes cliente, List<Pagos> pagos, List<ObrasObrero> obreros, List<Planos> planos, List<Permisos> permisos, List<Ubicaciones> ubicaciones) {
        this.costoArranque = costoArranque;
        this.inversion = inversion;
        this.deuda = deuda;
        this.nombre = nombre;
        this.fechaSolicitada = fechaSolicitada;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.jefe = jefe;
        this.cliente = cliente;
        this.pagos = pagos;
        this.obreros = obreros;
        this.planos = planos;
        this.permisos = permisos;
        this.ubicaciones = ubicaciones;
    }

    public Obras(Float costoArranque, Float inversion, String nombre, Jefes jefe, Clientes cliente) {
        this.costoArranque = costoArranque;
        this.inversion = inversion;
        this.nombre = nombre;
        this.jefe = jefe;
        this.cliente = cliente;
    }

    public Obras(Float costoArranque, Float inversion, String nombre, Clientes cliente) {
        this.costoArranque = costoArranque;
        this.inversion = inversion;
        this.nombre = nombre;
        this.cliente = cliente;
    }

    public Obras(Float costoArranque, Float inversion, String nombre) {
        this.costoArranque = costoArranque;
        this.inversion = inversion;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstadoObra getEstado() {
        return estado;
    }

    public void setEstado(EstadoObra estado) {
        this.estado = estado;
    }

    public Float getCostoArranque() {
        return costoArranque;
    }

    public void setCostoArranque(Float costoArranque) {
        this.costoArranque = costoArranque;
    }

    public Float getInversion() {
        return inversion;
    }

    public void setInversion(Float inversion) {
        this.inversion = inversion;
    }

    public Float getDeuda() {
        return deuda;
    }

    public void setDeuda(Float deuda) {
        this.deuda = deuda;
    }

    public Boolean getEstaPagada() {
        return estaPagada;
    }

    public void setEstaPagada(Boolean estaPagada) {
        this.estaPagada = estaPagada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Calendar getFechaSolicitada() {
        return fechaSolicitada;
    }

    public void setFechaSolicitada(Calendar fechaSolicitada) {
        this.fechaSolicitada = fechaSolicitada;
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

    public Jefes getJefe() {
        return jefe;
    }

    public void setJefe(Jefes jefe) {
        this.jefe = jefe;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public List<Pagos> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pagos> pagos) {
        this.pagos = pagos;
    }

    public List<Planos> getPlanos() {
        return planos;
    }

    public void setPlanos(List<Planos> planos) {
        this.planos = planos;
    }

    public List<Permisos> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permisos> permisos) {
        this.permisos = permisos;
    }

    public List<Ubicaciones> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<Ubicaciones> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public List<ObrasObrero> getObreros() {
        return obreros;
    }

    public void setObreros(List<ObrasObrero> obreros) {
        this.obreros = obreros;
    }

    public Float getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(Float costoTotal) {
        this.costoTotal = costoTotal;
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
        if (!(object instanceof Obras)) {
            return false;
        }
        Obras other = (Obras) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "Obras{" + "id=" + id + ", estado=" + estado + ", costoArranque=" + costoArranque + ", inversion=" + inversion + ", costoTotal=" + costoTotal + ", deuda=" + deuda + ", estaPagada=" + estaPagada + ", nombre=" + nombre + ", fechaSolicitada=" + fechaSolicitada + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", jefe=" + jefe + ", cliente=" + cliente + ", pagos=" + pagos + ", obreros=" + obreros + ", planos=" + planos + ", permisos=" + permisos + ", ubicaciones=" + ubicaciones + '}';
    }
}
