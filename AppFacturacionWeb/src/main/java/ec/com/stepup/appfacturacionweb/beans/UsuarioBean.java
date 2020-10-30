package ec.com.stepup.appfacturacionweb.beans;

import ec.com.stepup.appfacturacion.entity_bean.Rol;
import ec.com.stepup.appfacturacion.entity_bean.Usuario;
import ec.com.stepup.appfacturacion.session_bean.RolFacadeLocal;
import ec.com.stepup.appfacturacion.session_bean.UsuarioFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable {
    
    private List<Usuario> usuarioList;
    private Usuario usuario;
    @EJB
    private UsuarioFacadeLocal usuarioFacadeLocal;
    private List<Rol> rolList;
    @EJB
    private RolFacadeLocal rolFacadeLocal;
    
    public UsuarioBean() {
    }
    
    @PostConstruct
    public void init() {
        usuarioList = usuarioFacadeLocal.findAll();
        usuario = null;
    }
    
    public void nuevo() {
        usuario = new Usuario();
    }
    
    public void grabar() {
        if (usuario.getId() == null) {
            usuarioFacadeLocal.create(usuario);
        } else {
            usuarioFacadeLocal.edit(usuario);
        }
        init();
    }

    public void eliminar(Usuario usuario) {
        usuarioFacadeLocal.remove(usuario);
        init();
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Rol> getRolList() {
        return rolList;
    }

    public void setRolList(List<Rol> rolList) {
        this.rolList = rolList;
    }
    
    public void abrirSeleccionRoles() {
        rolList = rolFacadeLocal.findAll();
    }
    
    public void confirmarSeleccionRoles() {
        rolList.stream().filter(r -> r.isSeleccionado()).forEach(r -> usuario.agregarRol(r));
    }
    
}
