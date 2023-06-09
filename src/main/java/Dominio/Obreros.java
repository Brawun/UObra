/**
 * Obreros.java
 */
package Dominio;

import Escucha.ObrerosEscucha;
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
 * Esta entidad permite mapear un Obrero con todos sus atributos.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
@Entity
@EntityListeners({ObrerosEscucha.class})
@Table(name = "Obreros")
public class Obreros implements Serializable {

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

    @Column(name = "DiasTrabajados", nullable = true)
    private Integer diasTrabajados = 0;
    
    @Column(name = "SueldoDiario", nullable = true)
    public Float sueldoDiario;
    
    @Column(name = "PorPagar", nullable = true)
    private Float porPagar = (float) 0;
    
    @Column(name = "Pagado", nullable = true)
    private Float Pagado = (float) 0;

    // Un obrero puede recibir muchos pagos
    @OneToMany(mappedBy = "obrero", cascade = {CascadeType.REMOVE})
    private List<Pagos> pagos;

    // Una obrero puede pertenecer a muchas obras
    @OneToMany(mappedBy = "obrero", cascade = {CascadeType.REMOVE})
    private List<ObrasObrero> obras;

    public Obreros() {
    }

    public Obreros(Long id, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, List<Pagos> pagos, List<ObrasObrero> obras) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.pagos = pagos;
        this.obras = obras;
    }

    public Obreros(String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, Integer diasTrabajados, Float sueldoDiario) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.diasTrabajados = diasTrabajados;
        this.sueldoDiario = sueldoDiario;
    }

    public Obreros(String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, Float sueldoDiario) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.sueldoDiario = sueldoDiario;
    }

    public Obreros(String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, String contrasena, String usuario, Float sueldoDiario) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.sueldoDiario = sueldoDiario;
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

    public Integer getDiasTrabajados() {
        return diasTrabajados;
    }

    public void setDiasTrabajados(Integer diasTrabajados) {
        this.diasTrabajados = diasTrabajados;
    }

    public Float getSueldoDiario() {
        return sueldoDiario;
    }

    public void setSueldoDiario(Float sueldoDiario) {
        this.sueldoDiario = sueldoDiario;
    }

    public List<Pagos> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pagos> pagos) {
        this.pagos = pagos;
    }

    public List<ObrasObrero> getObras() {
        return obras;
    }

    public void setObras(List<ObrasObrero> obras) {
        this.obras = obras;
    }

    public Float getPorPagar() {
        return porPagar;
    }

    public void setPorPagar(Float porPagar) {
        this.porPagar = porPagar;
    }

    public Float getPagado() {
        return Pagado;
    }

    public void setPagado(Float Pagado) {
        this.Pagado = Pagado;
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
        if (!(object instanceof Obreros)) {
            return false;
        }
        Obreros other = (Obreros) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "Obreros{" + "id=" + id + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", telefono=" + telefono + ", contrasena=" + contrasena + ", usuario=" + usuario + ", diasTrabajados=" + diasTrabajados + ", sueldoDiario=" + sueldoDiario + ", porPagar=" + porPagar + ", Pagado=" + Pagado + ", pagos=" + pagos + ", obras=" + obras + '}';
    }
}
