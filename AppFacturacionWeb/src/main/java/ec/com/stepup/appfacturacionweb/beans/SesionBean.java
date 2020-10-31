package ec.com.stepup.appfacturacionweb.beans;

import ec.com.stepup.appfacturacion.entity_bean.Opcion;
import ec.com.stepup.appfacturacion.entity_bean.Rol;
import ec.com.stepup.appfacturacion.entity_bean.Usuario;
import ec.com.stepup.appfacturacion.session_bean.UsuarioFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

@Named("sesionBean")
@SessionScoped
public class SesionBean implements Serializable {

    private MenuModel menuModel;
    private Usuario usuario;
    @EJB
    private UsuarioFacadeLocal usuarioFacadeLocal;

    public SesionBean() {
    }

    @PostConstruct
    public void init() {
        try {
            String login  = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getUserPrincipal().getName();
            usuario = usuarioFacadeLocal.findByLogin(login);
            this.cargarMenuPrincipal();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene la lista de opciones del menu principal y genera el menu
     * correspondiente para el usuario y su perfil.
     */
    private void cargarMenuPrincipal() {
        this.menuModel = new DefaultMenuModel();
        try {
            List<Opcion> opcionesNivelCero = new ArrayList<>();
            for (Rol p : usuario.getRolesList()) {
                opcionesNivelCero.addAll(p.getOpcionList().stream()
                        .map(op -> op.getOpcionNivelCero())
                        .distinct()
                        .collect(Collectors.toList()));
            }

            opcionesNivelCero = opcionesNivelCero.stream().distinct().sorted().collect(Collectors.toList());

            for (Opcion opcion : opcionesNivelCero) {
                DefaultSubMenu submenu = new DefaultSubMenu();
                submenu.setLabel(opcion.getNombre());
                this.cargarMenuHijos(opcion, submenu);
                this.menuModel.addElement(submenu);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Genera de manera recursiva las opciones del submenu, a las que tiene acceso el usuario.
     */
    public void cargarMenuHijos(Opcion opcionPadre, DefaultSubMenu menuPadre) {
        try {
            List<Opcion> opcionesHijas = new ArrayList<>();

            for (Rol p : usuario.getRolesList()) {
                opcionesHijas.addAll(p.getOpcionList().stream()
                        .filter(op -> op.esOpcionPadre(opcionPadre))
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList()));
            }
            opcionesHijas = opcionesHijas.stream().sorted().distinct().collect(Collectors.toList());

            long nroHijos = 0;
            for (Opcion opcion : opcionesHijas) {
                for (Rol p : usuario.getRolesList()) {
                    nroHijos = p.getOpcionList().stream()
                            .filter(op -> op.esOpcionPadre(opcion))
                            .count();
                    if (nroHijos > 0) {
                        break;
                    }
                }

                if (nroHijos > 0) {
                    DefaultSubMenu itemHijo = new DefaultSubMenu();
                    itemHijo.setLabel(opcion.getNombre());
                    this.cargarMenuHijos(opcion, itemHijo);
                    menuPadre.getElements().add(itemHijo);
                } else {
                    DefaultMenuItem itemHijo = new DefaultMenuItem();
                    itemHijo.setValue(opcion.getNombre());
                    itemHijo.setUrl(((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getContextPath() + opcion.getUrl());
                    itemHijo.setImmediate(true);
                    menuPadre.getElements().add(itemHijo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MenuModel getMenuModel() {
        return menuModel;
    }

    public void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;
    }
}
