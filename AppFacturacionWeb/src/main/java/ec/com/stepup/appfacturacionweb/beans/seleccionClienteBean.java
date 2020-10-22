package ec.com.stepup.appfacturacionweb.beans;

import ec.com.stepup.appfacturacion.entity_bean.Cliente;
import ec.com.stepup.appfacturacion.session_bean.ClienteFacadeLocal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.PrimeFaces;

@Named(value = "seleccionClienteBean")
@RequestScoped
public class seleccionClienteBean {
    private List<Cliente> clienteList;
    @EJB
    private ClienteFacadeLocal clienteFacadeLocal;
    
    public seleccionClienteBean() {
    }
    
    @PostConstruct
    public void init(){
        clienteList=clienteFacadeLocal.findAll();
    }

    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }
    
    public void retornarCliente(Cliente cliente){
        PrimeFaces.current().dialog().closeDynamic(cliente);
    }
    
}
