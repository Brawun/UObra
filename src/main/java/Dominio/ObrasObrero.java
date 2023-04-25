/**
 * ObrasObrero.java
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Dominio;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Esta entidad permite mapear la relación entre Obras y Obreros con todos sus atributos.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
@Entity
public class ObrasObrero implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "idObrero", referencedColumnName = "ID")
    private Obreros obrero;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "idObra", referencedColumnName = "ID")
    private Obras obra;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof ObrasObrero)) {
            return false;
        }
        ObrasObrero other = (ObrasObrero) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dominio.ObrasObrero[ id=" + id + " ]";
    }
    // Atributos
    
}
