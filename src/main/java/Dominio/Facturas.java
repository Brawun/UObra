/**
 * Facturas.java
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Dominio;

import Enumeradores.EstadoFactura;
import Enumeradores.MetodoPago;
import java.util.Calendar;
import java.util.Objects;

/**
 * Esta clase permite .
 *
 * @author Brandon Figueroa Ugalde
 * ID: 00000233295
 * 22 mar 2023 10:14:20
 */
public class Facturas {
    // Atributos
    private Float monto; 
    private String descripcion;
    private Calendar fecha; 
    private EstadoFactura estado; 
    private MetodoPago metodoPago;

    public Facturas() {
    }

    public Facturas(Float monto, String descripcion, Calendar fecha, EstadoFactura estado, MetodoPago metodoPago) {
        this.monto = monto;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.estado = estado;
        this.metodoPago = metodoPago;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public EstadoFactura getEstado() {
        return estado;
    }

    public void setEstado(EstadoFactura estado) {
        this.estado = estado;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.monto);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Facturas other = (Facturas) obj;
        return Objects.equals(this.monto, other.monto);
    }

    @Override
    public String toString() {
        return "Facturas{" + "monto=" + monto + ", descripcion=" + descripcion + ", fecha=" + fecha + ", estado=" + estado + ", metodoPago=" + metodoPago + '}';
    }
}
