/**
 * Permisos.java
 */
package Dominio;

import Enumeradores.TipoPermiso;
import Escucha.PermisosEscucha;
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
 * Esta entidad permite mapear un Permiso con todos sus atributos.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
@Entity
@EntityListeners({PermisosEscucha.class})
@Table(name = "Permisos")
public class Permisos implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ENCRIPTAR
    @Column(name = "Folio", unique = true, nullable = false)
    private String folio;

    @Column(name = "Tipo", nullable = true)
    @Enumerated(EnumType.STRING)
    private TipoPermiso tipo;
    
    // AUTOGENERADA
    @Column(name = "FechaRegistro", nullable = true)
    @Temporal(TemporalType.DATE)
    private Calendar fechaRegistro;

    @Column(name = "FechaConcesion", nullable = true)
    @Temporal(TemporalType.DATE)
    private Calendar fechaConcesion;

    // Llave foránea
    // Muchos permisos pueden ser registrados por un jefe
    @ManyToOne()
    @JoinColumn(name = "idJefe", referencedColumnName = "ID", nullable = true)
    private Jefes jefe;

    // Muchos permsios pueden pertenecer a muchas obras
    @ManyToMany(mappedBy = "permisos")
    List<Obras> obras;

    public Permisos() {
    }

    public Permisos(Long id, String folio, TipoPermiso tipo, Calendar fechaRegistro, Calendar fechaConcesion, Jefes jefe, List<Obras> obras) {
        this.id = id;
        this.folio = folio;
        this.tipo = tipo;
        this.fechaRegistro = fechaRegistro;
        this.fechaConcesion = fechaConcesion;
        this.jefe = jefe;
        this.obras = obras;
    }

    public Permisos(String folio, TipoPermiso tipo, Calendar fechaConcesion) {
        this.folio = folio;
        this.tipo = tipo;
        this.fechaConcesion = fechaConcesion;
    }

    public Permisos(String folio, TipoPermiso tipo, Calendar fechaConcesion, Jefes jefe) {
        this.folio = folio;
        this.tipo = tipo;
        this.fechaConcesion = fechaConcesion;
        this.jefe = jefe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoPermiso getTipo() {
        return tipo;
    }

    public void setTipo(TipoPermiso tipo) {
        this.tipo = tipo;
    }

    public Calendar getFechaConcesion() {
        return fechaConcesion;
    }

    public void setFechaConcesion(Calendar fechaConcesion) {
        this.fechaConcesion = fechaConcesion;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Calendar getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Calendar fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Jefes getJefe() {
        return jefe;
    }

    public void setJefe(Jefes jefe) {
        this.jefe = jefe;
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
        if (!(object instanceof Permisos)) {
            return false;
        }
        Permisos other = (Permisos) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "Permisos{" + "id=" + id + ", folio=" + folio + ", tipo=" + tipo + ", fechaRegistro=" + fechaRegistro + ", fechaConcesion=" + fechaConcesion + ", jefe=" + jefe + ", obras=" + obras + '}';
    }
}
