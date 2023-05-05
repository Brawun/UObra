/**
 * Jefes.java
 */
package Dominio;

import Escucha.JefesEscucha;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
@EntityListeners({JefesEscucha.class})
@Table(name = "Jefes")
public class Jefes implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    
    @Column(name = "Nombre", nullable = true)
    public String nombre;
    
    @Column(name = "ApellidoPaterno", nullable = true)
    public String apellidoPaterno;
    
    @Column(name = "ApellidoMaterno", nullable = true)
    public String apellidoMaterno;

    // ENCRIPTAR
    @Column(name = "Telefono", nullable = false)
    public String telefono;
    
    // ENCRIPTAR
    @Column(name = "Contraseña", nullable = false, unique = false)
    public String contrasena;
    
    // ENCRIPTAR
    @Column(name = "Usuario", nullable = false, unique = true)
    public String usuario;

    // Un jefe puede registrar muchas facturas
    @OneToMany(mappedBy = "jefe", cascade = {CascadeType.REMOVE})
    private List<Facturas> facturas;

    // Un jefe puede aceptar muchas obras
    @OneToMany(mappedBy = "jefe", cascade = {CascadeType.REMOVE})
    private List<Obras> obras;

    // Un jefe puede registrar muchos permisos
    @OneToMany(mappedBy = "jefe", cascade = {CascadeType.REMOVE})
    private List<Permisos> permisos;

    // Un jefe puede registrar muchos planos
    @OneToMany(mappedBy = "jefe", cascade = {CascadeType.REMOVE})
    private List<Planos> planos;

    public Jefes() {
    }

    public Jefes(Long id, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, String contrasena, String usuario, List<Facturas> facturas, List<Obras> obras, List<Permisos> permisos, List<Planos> planos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.usuario = usuario;
        this.facturas = facturas;
        this.obras = obras;
        this.permisos = permisos;
        this.planos = planos;
    }

    public Jefes(String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, String contrasena, String usuario) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.usuario = usuario;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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
        return "Jefes{" + "id=" + id + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", telefono=" + telefono + ", contrasena=" + contrasena + ", usuario=" + usuario + ", facturas=" + facturas + ", obras=" + obras + ", permisos=" + permisos + ", planos=" + planos + '}';
    }
}
