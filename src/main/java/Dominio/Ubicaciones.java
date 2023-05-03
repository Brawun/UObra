/**
 * Ubicaciones.java
 */
package Dominio;

import Enumeradores.TipoUbicacion;
import Escucha.UbicacionesEscucha;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
 * Esta entidad permite mapear una Ubicación con todos sus atributos.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
@Entity
@EntityListeners({UbicacionesEscucha.class})
@Table(name = "Ubicaciones")
public class Ubicaciones implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EstaDisponible", nullable = false)
    private Boolean disponible;
    
    @Column(name = "Direccion", nullable = false)
    private String direccion;

    @Column(name = "Tipo", nullable = true)
    @Enumerated(EnumType.STRING)
    private TipoUbicacion tipo;

    @Column(name = "Largo", nullable = false)
    private Float largo;

    @Column(name = "Ancho", nullable = false)
    private Float ancho;
    
    // AUTOGENERADA
    @Column(name = "FechaRegistro", nullable = true)
    @Temporal(TemporalType.DATE)
    private Calendar fechaRegistro;
    
    @Column(name = "FechaOcupacion", nullable = true)
    @Temporal(TemporalType.DATE)
    private Calendar fechaOcupacion;

    // AUTOGENERADA
    @Column(name = "Area", nullable = false)
    private Float area;
    
    // Llave foránea
    // Muchas ubicaciones pueden ser registradas por un cliente
    @ManyToOne()
    @JoinColumn(name = "idCliente", referencedColumnName = "ID", nullable = true)
    private Clientes cliente;

    // Muchas ubicaciones pertenecen a muchas obras
    @ManyToMany(mappedBy = "ubicaciones")
    List<Obras> obras;

    public Ubicaciones() {
    }

    public Ubicaciones(Long id, Boolean disponible, String direccion, TipoUbicacion tipo, Float largo, Float ancho, Calendar fechaRegistro, Calendar fechaOcupacion, Float area, Clientes cliente, List<Obras> obras) {
        this.id = id;
        this.disponible = disponible;
        this.direccion = direccion;
        this.tipo = tipo;
        this.largo = largo;
        this.ancho = ancho;
        this.fechaRegistro = fechaRegistro;
        this.fechaOcupacion = fechaOcupacion;
        this.area = area;
        this.cliente = cliente;
        this.obras = obras;
    }

    public Ubicaciones(Boolean disponible, String direccion, TipoUbicacion tipo, Float largo, Float ancho, Clientes cliente) {
        this.disponible = disponible;
        this.direccion = direccion;
        this.tipo = tipo;
        this.largo = largo;
        this.ancho = ancho;
        this.cliente = cliente;
    }

    public Ubicaciones(String direccion, TipoUbicacion tipo, Float largo, Float ancho, Clientes cliente) {
        this.direccion = direccion;
        this.tipo = tipo;
        this.largo = largo;
        this.ancho = ancho;
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
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

    public Calendar getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Calendar fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Calendar getFechaOcupacion() {
        return fechaOcupacion;
    }

    public void setFechaOcupacion(Calendar fechaOcupacion) {
        this.fechaOcupacion = fechaOcupacion;
    }
    
    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public List<Obras> getObras() {
        return obras;
    }

    public void setObras(List<Obras> obras) {
        this.obras = obras;
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
        if (!(object instanceof Ubicaciones)) {
            return false;
        }
        Ubicaciones other = (Ubicaciones) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "Ubicaciones{" + "id=" + id + ", disponible=" + disponible + ", direccion=" + direccion + ", tipo=" + tipo + ", largo=" + largo + ", ancho=" + ancho + ", fechaRegistro=" + fechaRegistro + ", fechaOcupacion=" + fechaOcupacion + ", area=" + area + ", cliente=" + cliente + ", obras=" + obras + '}';
    }
}
