package tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * @author hiki on 2017-12-31
 */

public class SessionCheckHandler extends BodyTagSupport {

    @Override
    public int doEndTag() {
        HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) this.pageContext.getResponse();

        if (request.getSession().getAttribute("login") == null) {
            try {
                response.sendRedirect("/login");
                return SKIP_PAGE;
            } catch (IOException e) {
                System.err.println("error while checking logging status in SessionCheckHandler because of: " + e);;
            }
        }
        return EVAL_PAGE;
    }
}
