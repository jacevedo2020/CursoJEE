/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.stepup.appfacturacion.session_bean;

import ec.com.stepup.appfacturacion.entity_bean.RolOpcion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author admin
 */
@Local
public interface RolOpcionFacadeLocal {

    void create(RolOpcion rolOpcion);

    void edit(RolOpcion rolOpcion);

    void remove(RolOpcion rolOpcion);

    RolOpcion find(Object id);

    List<RolOpcion> findAll();

    List<RolOpcion> findRange(int[] range);

    int count();
    
}
