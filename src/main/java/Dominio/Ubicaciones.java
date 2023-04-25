/**
 * Ubicaciones.java
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Esta clase permite .
 *
 * @author Brandon Figueroa Ugalde
 * ID: 00000233295
 * 18 abr 2023 18:28:42
 */
@Entity
public class Ubicaciones implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Boolean disponible;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    // Llave foránea
    // Muchas ubicaciones pueden ser registradas por un cliente
    @ManyToOne()
    @JoinColumn(name = "idCliente", referencedColumnName = "ID", nullable = true)
    private Clientes cliente;
    
    // Muchas ubicaciones pertenecen a muchas obras
    @ManyToMany(mappedBy = "ubicaciones")
    List<Obras> obras;

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
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dominio.Ubicaciones[ id=" + id + " ]";
    }
    // Atributos
    
}
