package rosling.example.blog.servlet;

import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import rosling.example.blog.model.Entry;
import rosling.template.FreeMarkerService;
import rosling.util.SlingAdaptableUtil;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author saml
 */
@Component(immediate = true, metatype = false)
@Service(Servlet.class)
@Properties({@Property(name = "service.description", value = "blog entry"),
        @Property(name = "service.vendor", value = "The Apache Software Foundation"),
        @Property(name = "sling.servlet.resourceTypes", value = {"rosling_blog_entry"}),
        @Property(name = "sling.servlet.extensions", value = {"html"}),
        @Property(name = "sling.servlet.methods", value = {"GET"})

})
public class EntryServlet extends SlingAllMethodsServlet {
    @Reference
    private FreeMarkerService freeMarkerService;

    protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws IOException, ServletException {
        try {
            resp.setCharacterEncoding("utf-8");
            resp.setContentType("text/html");
            final Resource resource = req.getResource();
            final Node node = resource.adaptTo(Node.class);
            final Entry entry = SlingAdaptableUtil.adaptTo(node, Entry.class);
            final Map<String, Object> map = new HashMap<String, Object>();
            map.put("entry", entry);
            final PrintWriter out = resp.getWriter();
            freeMarkerService.render("entry.ftl", entry, out);
            out.flush();
        } catch (Exception e) {
            throw new ServletException(e);
        }

    }
}
