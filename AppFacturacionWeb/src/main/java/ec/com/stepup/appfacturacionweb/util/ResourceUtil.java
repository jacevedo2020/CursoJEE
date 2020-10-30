package ec.com.stepup.appfacturacionweb.util;

import java.util.ResourceBundle;

public class ResourceUtil {

    
    private static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("ec.com.stepup.appfacturacionweb.recursos.messages");
    }
    
    public static String getValue(String key){
        return getResourceBundle().getString(key);
    }

}
