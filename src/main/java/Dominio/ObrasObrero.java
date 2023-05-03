/**
 * ObrasObrero.java
 */
package Dominio;

import Escucha.ObrasObreroEscucha;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Esta entidad permite mapear la relación entre Obras y Obreros con todos sus atributos.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
@Entity
@EntityListeners({ObrasObreroEscucha.class})
@Table(name = "ObrasObrero")
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
    
    // AUTOGENERADA
    @Column(name = "FechaInicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Calendar fechaInicio;
    
    @Column(name = "FechaFin", nullable = true)
    @Temporal(TemporalType.DATE)
    private Calendar fechaFin; 
    
    // Solamente se genera al insertar una fecha fin
    @Column(name = "DiasTrabajados", nullable = true)
    private Integer diasTrabajados = 0; 
    
    @Column(name = "Activo", nullable = false)
    private Boolean activa = true; 

    public ObrasObrero() {
    }

    public ObrasObrero(Long id, Obreros obrero, Obras obra, Calendar fechaInicio, Calendar fechaFin) {
        this.id = id;
        this.obrero = obrero;
        this.obra = obra;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public ObrasObrero(Obreros obrero, Obras obra, Boolean activa) {
        this.obrero = obrero;
        this.obra = obra;
        this.activa = activa;
    }

    public ObrasObrero(Obreros obrero, Obras obra) {
        this.obrero = obrero;
        this.obra = obra;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Obreros getObrero() {
        return obrero;
    }

    public void setObrero(Obreros obrero) {
        this.obrero = obrero;
    }

    public Obras getObra() {
        return obra;
    }

    public void setObra(Obras obra) {
        this.obra = obra;
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

    public Boolean getActivo() {
        return activa;
    }

    public void setActivo(Boolean activa) {
        this.activa = activa;
    }

    public Integer getDiasTrabajados() {
        return diasTrabajados;
    }

    public void setDiasTrabajados(Integer diasTrabajados) {
        this.diasTrabajados = diasTrabajados;
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
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ObrasObrero{" + "id=" + id + ", obrero=" + obrero + ", obra=" + obra + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", diasTrabajados=" + diasTrabajados + ", activa=" + activa + '}';
    }
}
