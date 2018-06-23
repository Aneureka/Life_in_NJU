package leftovers.controller.support;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by kevin on 2017/6/10.
 */
@Component
public class UserInfoProvider {
    public String getCurrentUser(){
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
