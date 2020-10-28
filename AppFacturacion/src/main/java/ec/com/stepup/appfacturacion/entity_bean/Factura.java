/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.stepup.appfacturacion.entity_bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "factura")
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "numero")
    private String numero;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cliente cliente;
    /*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,  mappedBy = "factura", orphanRemoval = true)
    private List<DetalleFactura> detalleFacturaList;*/
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,  orphanRemoval = true)
    @JoinColumn(name = "id_factura")
    private List<DetalleFactura> detalleFacturaList;

    public Factura() {
    }

    public Factura(Integer id) {
        this.id = id;
    }

    public Factura(Integer id, Date fecha, String numero) {
        this.id = id;
        this.fecha = fecha;
        this.numero = numero;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<DetalleFactura> getDetalleFacturaList() {
        return detalleFacturaList;
    }

    public void setDetalleFacturaList(List<DetalleFactura> detalleFacturaList) {
        this.detalleFacturaList = detalleFacturaList;
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
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.stepup.appfacturacion.entity_bean.Factura[ id=" + id + " ]";
    }

    public BigDecimal getSubtotal() {
        return BigDecimal.valueOf(detalleFacturaList.stream().mapToDouble(d -> d.getSubtotal().doubleValue()).sum());
    }

    public BigDecimal getTotal() {
        return getSubtotal().multiply(BigDecimal.valueOf(1.12));
    }

    public void agregarDetalle(DetalleFactura detalleFactura) {
        if (detalleFacturaList == null) {
            detalleFacturaList = new ArrayList<>();
        }
        //detalleFactura.setFactura(this);
        detalleFacturaList.add(detalleFactura);
    }
    
    public void eliminarDetalle(DetalleFactura detalleFactura){
        detalleFacturaList.remove(detalleFactura);
    }

    public boolean esProductoDulicado(Producto producto) {
        if (detalleFacturaList == null) {
            return false;
        }
        return detalleFacturaList.stream().anyMatch(d -> d.getProducto().equals(producto));
    }
}
