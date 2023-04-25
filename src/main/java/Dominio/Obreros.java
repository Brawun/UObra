/**
 * Obreros.java
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "Obreros")
public class Obreros implements Serializable {

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
    
    @Column(name = "DiasTrabajados", nullable = true)
    private Integer diasTrabajados = 0;
    
    @Column(name = "SueldoDiario", nullable = true)
    private Float sueldoDiario = (float) 200;
    
    @OneToMany(mappedBy = "obrero")
    private List<Pagos> pagos;
    
    @ManyToMany()
    @JoinTable(
        name = "obrasObrero",
        joinColumns = @JoinColumn(name = "idObrero", referencedColumnName = "ID", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "idObra", referencedColumnName = "ID", nullable = false)
    )
    private List<Planos> planos;

    public Obreros() {
    }

    public Obreros(Long id, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, Integer diasTrabajados, Float sueldoDiario) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.diasTrabajados = diasTrabajados;
        this.sueldoDiario = sueldoDiario;
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

    public Obreros(String nombre, String apellidoPaterno, String apellidoMaterno, String telefono) {
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
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Obreros{" + "id=" + id + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", telefono=" + telefono + ", diasTrabajados=" + diasTrabajados + ", sueldoDiario=" + sueldoDiario + '}';
    }
}
