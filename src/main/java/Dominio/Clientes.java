/**
 * Compradores.java
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
 * Esta entidad permite mapear # con todos sus atributos.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
@Entity
@Table(name = "Clientes")
public class Clientes implements Serializable {

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
            
    @Column(name = "DeudaTotal", nullable = true)
    private Float deudaTotal = (float) 0; 
    
    // Un cliente puede solicitar muchas obras
    @OneToMany(mappedBy = "cliente")
    private List<Obras> obras;
    
    // Un cliente puede registrar muchas ubicaciones
    @OneToMany(mappedBy = "cliente")
    private List<Ubicaciones> ubicaciones;

    public Clientes() {
    }

    public Clientes(Long id, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, List<Obras> obras, List<Ubicaciones> ubicaciones) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.obras = obras;
        this.ubicaciones = ubicaciones;
    }

    public Clientes(Long id, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
    }

    public Clientes(String nombre, String apellidoPaterno, String apellidoMaterno, String telefono) {
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

    public Float getDeudaTotal() {
        return deudaTotal;
    }

    public void setDeudaTotal(Float deudaTotal) {
        this.deudaTotal = deudaTotal;
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
        return "Clientes{" + "id=" + id + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", telefono=" + telefono + ", deudaTotal=" + deudaTotal + ", obras=" + obras + ", ubicaciones=" + ubicaciones + '}';
    }
}