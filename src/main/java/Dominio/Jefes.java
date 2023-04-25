/**
 * Jefes.java
 */
package Dominio;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Esta entidad permite mapear un Jefe con todos sus atributos.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
@Entity
@Table(name = "Jefes")
public class Jefes implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Nombre", nullable = true)
    private String nombre;

    @Column(name = "ApellidoPaterno", nullable = true)
    private String apellidoPaterno;

    @Column(name = "ApellidoMaterno", nullable = true)
    private String apellidoMaterno;

    @Column(name = "Telefono", nullable = true)
    private String telefono;

    // Un jefe puede registrar muchas facturas
    @OneToMany(mappedBy = "jefe")
    private List<Facturas> facturas;

    // Un jefe puede aceptar muchas obras
    @OneToMany(mappedBy = "jefe")
    private List<Obras> obras;

    // Un jefe puede registrar muchos permisos
    @OneToMany(mappedBy = "jefe")
    private List<Permisos> permisos;

    // Un jefe puede registrar muchos planos
    @OneToMany(mappedBy = "jefe")
    private List<Planos> planos;

    public Jefes() {
    }

    public Jefes(Long id, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, List<Facturas> facturas, List<Obras> obras, List<Permisos> permisos, List<Planos> planos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.facturas = facturas;
        this.obras = obras;
        this.permisos = permisos;
        this.planos = planos;
    }

    public Jefes(String nombre, String apellidoPaterno, String apellidoMaterno, String telefono) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Facturas> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Facturas> facturas) {
        this.facturas = facturas;
    }

    public List<Obras> getObras() {
        return obras;
    }

    public void setObras(List<Obras> obras) {
        this.obras = obras;
    }

    public List<Permisos> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permisos> permisos) {
        this.permisos = permisos;
    }

    public List<Planos> getPlanos() {
        return planos;
    }

    public void setPlanos(List<Planos> planos) {
        this.planos = planos;
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
        if (!(object instanceof Jefes)) {
            return false;
        }
        Jefes other = (Jefes) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "Jefes{" + "id=" + id + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", telefono=" + telefono + ", facturas=" + facturas + ", obras=" + obras + ", permisos=" + permisos + ", planos=" + planos + '}';
    }
}
