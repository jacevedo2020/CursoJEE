/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.stepup.appfacturacionweb.soap2;

import ec.com.stepup.appfacturacion.entity_bean.Categoria;
import ec.com.stepup.appfacturacion.session_bean.CategoriaFacadeLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author admin
 */
@WebService(serviceName = "Servicio2")
public class Servicio2 {

    @EJB
    private CategoriaFacadeLocal ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Web Service > Add Operation"

    @WebMethod(operationName = "create")
    @Oneway
    public void create(@WebParam(name = "categoria") Categoria categoria) {
        ejbRef.create(categoria);
    }

    @WebMethod(operationName = "edit")
    @Oneway
    public void edit(@WebParam(name = "categoria") Categoria categoria) {
        ejbRef.edit(categoria);
    }

    @WebMethod(operationName = "remove")
    @Oneway
    public void remove(@WebParam(name = "categoria") Categoria categoria) {
        ejbRef.remove(categoria);
    }

    @WebMethod(operationName = "find")
    public Categoria find(@WebParam(name = "id") Integer id) {
        return ejbRef.find(id);
    }

    @WebMethod(operationName = "findAll")
    public List<Categoria> findAll() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findRange")
    public List<Categoria> findRange(@WebParam(name = "range") int[] range) {
        return ejbRef.findRange(range);
    }

    @WebMethod(operationName = "count")
    public int count() {
        return ejbRef.count();
    }

    @WebMethod(operationName = "findActivos")
    public List<Categoria> findActivos() {
        return ejbRef.findActivos();
    }
    
}
