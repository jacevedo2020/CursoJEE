package ec.com.stepup.appfacturacionweb.beans;

import ec.com.stepup.appfacturacion.entity_bean.Categoria;
import ec.com.stepup.appfacturacion.session_bean.CategoriaFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "categoriaBean")
@ViewScoped
public class CategoriaBean implements Serializable {

    private List<Categoria> categoriaList;
    private List<Categoria> categoriaActivosList;
    private Categoria categoria;
    @EJB
    private CategoriaFacadeLocal categoriaFacadeLocal;      //segundo
    private boolean modoEdicion;

    public CategoriaBean() {   //primero
    }

    @PostConstruct
    public void init() {     //tercero
        categoriaList = categoriaFacadeLocal.findAll();
        categoriaActivosList  = categoriaFacadeLocal.findActivos();
    }

    public List<Categoria> getCategoriaList() {
        return categoriaList;
    }

    public void setCategoriaList(List<Categoria> categoriaList) {
        this.categoriaList = categoriaList;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void nuevo() {
        categoria = new Categoria();
        modoEdicion = false;
    }

    public void grabar() {
        if (modoEdicion) {
            categoriaFacadeLocal.edit(categoria);
        } else {
            Categoria cat = categoriaFacadeLocal.find(categoria.getId());
            if (cat != null) {
                FacesContext
                        .getCurrentInstance()
                        .addMessage("form1:txtId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Id ya existe", "Id ya existe"));
                return;
            }
            categoriaFacadeLocal.create(categoria);

        }
        categoria = null;
        init();
    }

    public void seleccionar(Categoria categoria) {
        this.categoria = categoria;
        modoEdicion = true;
    }

    public void eliminar(Categoria categoria) {
        categoriaFacadeLocal.remove(categoria);
        init();
    }

    public void cancelar() {
        init();
        categoria = null;
    }

    public boolean isModoEdicion() {
        return modoEdicion;
    }

    public void setModoEdicion(boolean modoEdicion) {
        this.modoEdicion = modoEdicion;
    }

    public void validarId() {
        Categoria cat = categoriaFacadeLocal.find(categoria.getId());
        if (cat != null) {
            FacesContext
                    .getCurrentInstance()
                    .addMessage("form1:txtId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Id ya existe", "Id ya existe"));
        }
    }

    public List<Categoria> getCategoriaActivosList() {
        return categoriaActivosList;
    }

    public void setCategoriaActivosList(List<Categoria> categoriaActivosList) {
        this.categoriaActivosList = categoriaActivosList;
    }
    
    

}
