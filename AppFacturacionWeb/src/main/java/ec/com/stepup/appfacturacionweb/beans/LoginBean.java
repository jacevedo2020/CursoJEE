package ec.com.stepup.appfacturacionweb.beans;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Named
@ViewScoped
public class LoginBean implements Serializable {
    
    private String username;
    private String password;
    private boolean claveCambiada;

    public void login() throws ServletException, IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.getSessionMap().put("username", username);
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/login");
        dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
        FacesContext.getCurrentInstance().responseComplete();
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
