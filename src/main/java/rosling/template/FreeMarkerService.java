package rosling.template;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import rosling.Settings;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author saml
 */
@Component(immediate = true)
@Service(FreeMarkerService.class)
public class FreeMarkerService {

    @Reference
    private Settings settings;

    private final Configuration cfg;

    public FreeMarkerService() throws IOException {
        cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(settings.getTemplateDir()));
        cfg.setObjectWrapper(new DefaultObjectWrapper());
    }

    public void render(String templatePath, Object templateVars, PrintWriter out) throws IOException, TemplateException {
        final Template tempalte = cfg.getTemplate(templatePath);
        tempalte.process(templateVars, out);
        out.flush();
    }
}
