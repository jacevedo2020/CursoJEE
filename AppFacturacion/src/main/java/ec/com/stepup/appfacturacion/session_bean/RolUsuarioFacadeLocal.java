/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.stepup.appfacturacion.session_bean;

import ec.com.stepup.appfacturacion.entity_bean.RolUsuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author admin
 */
@Local
public interface RolUsuarioFacadeLocal {

    void create(RolUsuario rolUsuario);

    void edit(RolUsuario rolUsuario);

    void remove(RolUsuario rolUsuario);

    RolUsuario find(Object id);

    List<RolUsuario> findAll();

    List<RolUsuario> findRange(int[] range);

    int count();
    
}
