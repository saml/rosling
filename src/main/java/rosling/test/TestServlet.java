package rosling.test;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import rosling.util.SlingAdaptableUtil;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author saml
 */
@Component(immediate = true, metatype = false)
@Service(Servlet.class)
@Properties({@Property(name = "service.description", value = "Hello World Path Servlet"),
        @Property(name = "service.vendor", value = "The Apache Software Foundation"),
        @Property(name = "sling.servlet.paths", value = "/test")
})
public class TestServlet extends SlingAllMethodsServlet {
    protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws IOException, ServletException {
        try {
            String path = req.getParameter("path");
            if (path == null) {
                path = "/entry";
            }
            final Session session = req.getResourceResolver().adaptTo(Session.class);
            final Entry entry = SlingAdaptableUtil.convert(session.getNode(path), Entry.class);
            final PrintWriter out = resp.getWriter();
            out.append(entry.getName() + "\n");
            out.append(entry.getAge() + "\n");
            out.append(entry.getIsMale() + "\n");
            out.append(entry.getPage().getText() + "\n");
            out.flush();

        } catch (Exception e) {
            throw new ServletException(e);
        }

    }
}
