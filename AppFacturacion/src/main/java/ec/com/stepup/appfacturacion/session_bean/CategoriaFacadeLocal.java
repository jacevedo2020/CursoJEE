package ec.com.stepup.appfacturacion.session_bean;

import ec.com.stepup.appfacturacion.entity_bean.Categoria;
import java.util.List;

@javax.ejb.Local
public interface CategoriaFacadeLocal {

    void create(Categoria categoria);

    void edit(Categoria categoria);

    void remove(Categoria categoria);

    Categoria find(Object id);

    List<Categoria> findAll();

    List<Categoria> findRange(int[] range);

    int count();
    
    List<Categoria> findActivos();
    
}
