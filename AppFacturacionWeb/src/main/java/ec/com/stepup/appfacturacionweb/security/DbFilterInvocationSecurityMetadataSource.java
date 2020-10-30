package ec.com.stepup.appfacturacionweb.security;

import ec.com.stepup.appfacturacion.entity_bean.Opcion;
import ec.com.stepup.appfacturacion.entity_bean.Rol;
import ec.com.stepup.appfacturacion.entity_bean.RolOpcion;
import ec.com.stepup.appfacturacion.session_bean.RolFacadeLocal;
import ec.com.stepup.appfacturacion.session_bean.RolOpcionFacadeLocal;
import ec.com.stepup.appfacturacionweb.util.ResourceUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

@Component
public class DbFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource, InitializingBean {

    private HashMap<String, List<String>> urlRoles = new HashMap<>();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //Cada vez que se requiera acceder a un recurso se ejecuta este metodo
        //y verifica si es q el rol tiene o ono acceso a dicho recurso
        FilterInvocation fi = (FilterInvocation) object;
        String url = fi.getRequestUrl();
        int pos = url.indexOf("?");
        if (pos != -1) {
            url = url.substring(0, pos);
        }

        //verificar cuales roles tienen acceso a la url solicitada
        List<String> roles = urlRoles.get(url);
        if (roles == null) {
            if (url.contains(".xhtml") && !url.contains("resource") && !url.equals("/login.xhtml") && !url.equals("/changePassword.xhtml")) {
                roles = new ArrayList<>();
                roles.add("NO_ROLE");
            } else {
                return null;
            }

        }
        String[] stockArr = new String[roles.size()];
        stockArr = roles.toArray(stockArr);
        return SecurityConfig.createList(stockArr);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //Cargar por una sola vez, al iniciar la aplicacion
        //todos los roles del sistema con sus respectivos accesos
        Context c = new InitialContext();
        RolOpcionFacadeLocal rolOpcionFacadeLocal = (RolOpcionFacadeLocal) c.lookup("java:global/" + ResourceUtil.getValue("nombreAplicacion") + "/RolOpcionFacade!ec.com.stepup.appfacturacion.session_bean.RolOpcionFacadeLocal");
        List<RolOpcion> rolOpcionList = rolOpcionFacadeLocal.findAll();

        RolFacadeLocal rolFacadeLocal = (RolFacadeLocal) c.lookup("java:global/" + ResourceUtil.getValue("nombreAplicacion") + "/RolFacade!ec.com.stepup.appfacturacion.session_bean.RolFacadeLocal");
        List<Rol> roles = rolFacadeLocal.findAll();

        for (Rol rol : roles) {
            RolOpcion op = new RolOpcion();
            Opcion opcion = new Opcion();
            opcion.setUrl("/plantilla.xhtml");
            op.setRol(rol);
            op.setOpcion(opcion);
            rolOpcionList.add(op);
        }
        mapUrlToRoles(rolOpcionList);
    }

    /**
     *Mapea la Url con los roles que tienen acceso a dicha url
     * @param rolOpcionList 
     */
    public void mapUrlToRoles(List<RolOpcion> rolOpcionList) {
        for (RolOpcion rolOpcion : rolOpcionList) {
            String url = rolOpcion.getOpcion().getUrl();
            if (url != null && !url.trim().isEmpty()) {
                if (this.urlRoles.containsKey(url)) {
                    List<String> roles = this.urlRoles.get(url);
                    roles.add(rolOpcion.getRol().getNombre());
                } else {
                    List<String> roles = new ArrayList<>();
                    roles.add(rolOpcion.getRol().getNombre());
                    this.urlRoles.put(url, roles);
                }
            }
        }
    }
}
