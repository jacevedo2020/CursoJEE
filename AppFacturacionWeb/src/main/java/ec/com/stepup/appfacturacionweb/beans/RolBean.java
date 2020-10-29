package ec.com.stepup.appfacturacionweb.beans;

import ec.com.stepup.appfacturacion.entity_bean.Opcion;
import ec.com.stepup.appfacturacion.entity_bean.Rol;
import ec.com.stepup.appfacturacion.session_bean.OpcionFacadeLocal;
import ec.com.stepup.appfacturacion.session_bean.RolFacadeLocal;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PrimeFaces;

@Named(value = "rolBean")
@ViewScoped
public class RolBean implements Serializable {

    private List<Rol> rolList;
    private Rol rol;
    @EJB
    private RolFacadeLocal rolFacadeLocal;
    private List<Opcion> opcionList;
    @EJB
    private OpcionFacadeLocal opcionFacadeLocal;

    public RolBean() {
    }

    @PostConstruct
    public void init() {
        rolList = rolFacadeLocal.findAll();
        rol = null;
    }

    public void nuevo() {
        rol = new Rol();
    }

    public void grabar() {
        if (rol.getId() == null) {
            rolFacadeLocal.create(rol);
        } else {
            rolFacadeLocal.edit(rol);
        }
        init();
    }
    public void eliminar(Rol rol){
        rolFacadeLocal.remove(rol);
        init();
    }

    public List<Rol> getRolList() {
        return rolList;
    }

    public void setRolList(List<Rol> rolList) {
        this.rolList = rolList;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Opcion> getOpcionList() {
        return opcionList;
    }

    public void setOpcionList(List<Opcion> opcionList) {
        this.opcionList = opcionList;
    }
    
    
    public void abrirSeleccionOpciones(){
        opcionList = opcionFacadeLocal.findAll()
                .stream()
                .filter(op->op.getOpcionPadre() != null)
                .collect(Collectors.toList());
        //PrimeFaces.current().executeScript("PF('dlgOpciones').show");
    }

    
}
