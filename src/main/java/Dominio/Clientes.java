/**
 * Compradores.java
 */
package Dominio;

import Escucha.ClientesEscucha;
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
 * Esta entidad permite mapear un Cliente con todos sus atributos.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
@Entity
@EntityListeners({ClientesEscucha.class})
@Table(name = "Clientes")
public class Clientes implements Serializable {

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
    
    @Column(name = "DeudaTotal", nullable = true)
    private Float deudaTotal = (float) 0;
    
    @Column(name = "InversionTotal", nullable = false)
    private Float inversionTotal = (float) 0;
    
    // Un cliente puede solicitar muchas obras
    @OneToMany(mappedBy = "cliente", cascade = {CascadeType.REMOVE})
    private List<Obras> obras;

    // Un cliente puede registrar muchas ubicaciones
    @OneToMany(mappedBy = "cliente", cascade = {CascadeType.REMOVE})
    private List<Ubicaciones> ubicaciones;
    
    // Un cliente puede realizar muchos pagos
    @OneToMany(mappedBy = "cliente", cascade = {CascadeType.REMOVE})
    private List<Pagos> pagos;

    public Clientes() {
    }

    public Clientes(Long id, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, String contrasena, String usuario, List<Obras> obras, List<Ubicaciones> ubicaciones, List<Pagos> pagos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.usuario = usuario;
        this.obras = obras;
        this.ubicaciones = ubicaciones;
        this.pagos = pagos;
    }

    public Clientes(Long id, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
    }

    public Clientes(String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, String contrasena, String usuario) {
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Float getDeudaTotal() {
        return deudaTotal;
    }

    public void setDeudaTotal(Float deudaTotal) {
        this.deudaTotal = deudaTotal;
    }

    public Float getInversionTotal() {
        return inversionTotal;
    }

    public void setInversionTotal(Float inversionTotal) {
        this.inversionTotal = inversionTotal;
    }

    public List<Obras> getObras() {
        return obras;
    }

    public void setObras(List<Obras> obras) {
        this.obras = obras;
    }

    public List<Ubicaciones> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<Ubicaciones> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public List<Pagos> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pagos> pagos) {
        this.pagos = pagos;
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
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "Clientes{" + "id=" + id + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + '}';
    }
}
