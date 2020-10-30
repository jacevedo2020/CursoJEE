package ec.com.stepup.appfacturacionweb.beans;

import ec.com.stepup.appfacturacion.entity_bean.Usuario;
import ec.com.stepup.appfacturacion.session_bean.UsuarioFacadeLocal;
import ec.com.stepup.appfacturacionweb.util.Mensaje;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Named(value = "changePasswordBean")
@ViewScoped
public class ChangePasswordBean implements Serializable {

    @EJB
    private UsuarioFacadeLocal usuarioServicio;

    private String username;
    private String password;
    private boolean claveCambiada;

    public ChangePasswordBean() {
    }

    @PostConstruct
    public void init() {
        username = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username").toString();
    }

    public void cambiarContrasenia() {
        Usuario u = this.usuarioServicio.findByLogin(username);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        u.setClave(passwordEncoder.encode(this.password));
        u.setClaveExpirada(false);
        this.usuarioServicio.edit(u);
        claveCambiada = true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isClaveCambiada() {
        return claveCambiada;
    }

    public void setClaveCambiada(boolean claveCambiada) {
        this.claveCambiada = claveCambiada;
    }

}
