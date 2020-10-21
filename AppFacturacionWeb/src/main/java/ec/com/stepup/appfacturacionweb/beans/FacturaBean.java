package ec.com.stepup.appfacturacionweb.beans;

import ec.com.stepup.appfacturacion.entity_bean.Factura;
import ec.com.stepup.appfacturacion.session_bean.FacturaFacadeLocal;
import ec.com.stepup.appfacturacionweb.util.Mensaje;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;

@Named(value = "facturaBean")
@ViewScoped
public class FacturaBean implements Serializable {

    private List<Factura> facturaList;
    private Factura factura;
    @EJB
    private FacturaFacadeLocal facturaFacadeLocal;

    public FacturaBean() {
    }

    @PostConstruct
    public void init() {
        facturaList = facturaFacadeLocal.findAll();
        factura = null;
    }

    public void nuevo() {
        factura = new Factura();
    }

    public void grabar() {
        try {
            if (factura.getId() == null) {
                facturaFacadeLocal.create(factura);
            } else {
                facturaFacadeLocal.edit(factura);
            }
            Mensaje.mostrarExito("Factura grabada exitosamente.");
        } catch (Exception e) {
            Mensaje.mostrarError("Ocurrió un error al grabar la factura");
        }
    }

    public List<Factura> getFacturaList() {
        return facturaList;
    }

    public void setFacturaList(List<Factura> facturaList) {
        this.facturaList = facturaList;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }
    
    public void abrirSeleccionCliente(){
        Map<String, Object> opciones = new HashMap<>();
        opciones.put("contentHeigth", 500);
        opciones.put("contentWidth", 800);
        opciones.put("modal", true);
        PrimeFaces.current().dialog().openDynamic("seleccionCliente", opciones, null);
    }
    

}
