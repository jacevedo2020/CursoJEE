/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.stepup.appfacturacion.session_bean;

import ec.com.stepup.appfacturacion.entity_bean.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author admin
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "facturacionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    @Override
    public Usuario findByLogin(String login) {
        /*Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.login = :pLogin" );
        q.setParameter("pLogin", login);*/
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.login = ?1" );
        q.setParameter(1, login);
        try {
            return (Usuario) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
