/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.stepup.appfacturacion.entity_bean;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "opcion")
@NamedQueries({
    @NamedQuery(name = "Opcion.findAll", query = "SELECT o FROM Opcion o"),
    @NamedQuery(name = "Opcion.findById", query = "SELECT o FROM Opcion o WHERE o.id = :id"),
    @NamedQuery(name = "Opcion.findByNombre", query = "SELECT o FROM Opcion o WHERE o.nombre = :nombre"),
    @NamedQuery(name = "Opcion.findByUrl", query = "SELECT o FROM Opcion o WHERE o.url = :url")})
public class Opcion implements Serializable, Comparable<Opcion> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "url")
    private String url;
    @OneToMany(mappedBy = "opcionPadre")
    private List<Opcion> opcionList;
    @JoinColumn(name = "id_padre", referencedColumnName = "id")
    @ManyToOne
    private Opcion opcionPadre;
    @OneToMany(mappedBy = "opcion")
    private List<RolOpcion> rolOpcionList;

    @Transient
    private boolean seleccionado;

    public Opcion() {
    }

    public Opcion(Integer id) {
        this.id = id;
    }

    public Opcion(Integer id, String nombre, String url) {
        this.id = id;
        this.nombre = nombre;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Opcion> getOpcionList() {
        return opcionList;
    }

    public void setOpcionList(List<Opcion> opcionList) {
        this.opcionList = opcionList;
    }

    public Opcion getOpcionPadre() {
        return opcionPadre;
    }

    public void setOpcionPadre(Opcion opcionPadre) {
        this.opcionPadre = opcionPadre;
    }

    public List<RolOpcion> getRolOpcionList() {
        return rolOpcionList;
    }

    public void setRolOpcionList(List<RolOpcion> rolOpcionList) {
        this.rolOpcionList = rolOpcionList;
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
        if (!(object instanceof Opcion)) {
            return false;
        }
        Opcion other = (Opcion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.stepup.appfacturacion.entity_bean.Opcion[ id=" + id + " ]";
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public boolean esOpcionPadre(Opcion opcion) {
        if (opcionPadre == null) {
            return false;
        }
        return opcionPadre.equals(opcion);
    }

    @Override
    public int compareTo(Opcion o) {
        return this.id.compareTo(o.id);
    }
}
