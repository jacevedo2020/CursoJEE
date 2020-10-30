package ec.com.stepup.appfacturacionweb.security;

import ec.com.stepup.appfacturacion.entity_bean.Rol;
import ec.com.stepup.appfacturacion.entity_bean.Usuario;
import ec.com.stepup.appfacturacion.session_bean.UsuarioFacadeLocal;
import ec.com.stepup.appfacturacionweb.util.ResourceUtil;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = null;
        try {
            Context c = new InitialContext();
            UsuarioFacadeLocal usuarioFacadeLocal = (UsuarioFacadeLocal) c.lookup("java:global/" + ResourceUtil.getValue("nombreAplicacion") + "/UsuarioFacade!ec.com.stepup.appfacturacion.session_bean.UsuarioFacadeLocal");
            usuario = usuarioFacadeLocal.findByLogin(username);

        } catch (Exception e) {
            throw new UsernameNotFoundException("");
        }
        if (usuario == null) {
            throw new UsernameNotFoundException("");
        }

        List<String> roles = new ArrayList<>();
        for (Rol rol : usuario.getRolesList()) {
            roles.add(rol.getNombre());
        }
        return new LoggedUser(usuario, roles);
    }

}
