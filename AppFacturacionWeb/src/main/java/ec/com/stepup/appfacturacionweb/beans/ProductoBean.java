package ec.com.stepup.appfacturacionweb.beans;

import ec.com.stepup.appfacturacion.entity_bean.Producto;
import ec.com.stepup.appfacturacion.session_bean.ProductoFacadeLocal;
import ec.com.stepup.appfacturacionweb.util.Mensaje;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "productoBean")
@ViewScoped
public class ProductoBean implements Serializable {

    private List<Producto> productoList;
    private Producto producto;
    @EJB
    private ProductoFacadeLocal productoFacadeLocal;

    public ProductoBean() {
    }

    @PostConstruct
    public void init() {
        productoList = productoFacadeLocal.findAll();
        producto = null;
    }

    public void nuevo() {
        producto = new Producto();
    }

    public void seleccionar(Producto producto) {
        this.producto = producto;
    }

    public void grabar() {
        try {
            if (producto.getId() == null) {
                productoFacadeLocal.create(producto);
            } else {
                productoFacadeLocal.edit(producto);
            }
            init();
            Mensaje.mostrarExito("Producto grabado exitosamente.");
        } catch (Exception e) {
            Mensaje.mostrarError("Ocurrió un error al grabar el producto.");
        }
    }
    public void eliminar(Producto producto){
        try {
            productoFacadeLocal.remove(producto);
            init();
            Mensaje.mostrarExito("Producto eliminado exitosamente.");
        } catch (Exception e) {
            Mensaje.mostrarError("Ocurrió un error al eliminar el producto.");
        }
    }

    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    
}
