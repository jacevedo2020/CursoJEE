package ec.com.stepup.appfacturacion.session_bean;

import ec.com.stepup.appfacturacion.entity_bean.Categoria;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@javax.ejb.Stateless
public class CategoriaFacade extends AbstractFacade<Categoria> implements CategoriaFacadeLocal {

    @PersistenceContext(unitName = "facturacionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoriaFacade() {
        super(Categoria.class);
    }

    @Override
    public List<Categoria> findAll() {
        Query q = em.createQuery("SELECT c FROM Categoria c ORDER BY c.nombre DESC");
        return q.getResultList();
    }

    @Override
    public List<Categoria> findActivos() {
        Query q = em.createQuery("SELECT c FROM Categoria c WHERE UPPER(c.estado) = 'A' ORDER BY c.nombre");
        return q.getResultList();
    }
    
}
