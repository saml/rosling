package rosling.template;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rosling.Settings;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author saml
 */
@Component(immediate = true)
@Service(FreeMarkerService.class)
public class FreeMarkerService {
    private static final Logger log = LoggerFactory.getLogger(FreeMarkerService.class);


    @Reference
    private Settings settings;

    private static final Configuration cfg;

    static {
        cfg = new Configuration();

    }

    private String templateDir;

    @Activate
    private void activate(Map<String, ?> config) throws IOException {
        templateDir = settings.getTemplateDir();
        cfg.setDirectoryForTemplateLoading(new File(templateDir));
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        log.info("templateDir: {}", templateDir);
    }


    public String render(String templatePath, Object templateVars, PrintWriter out) throws IOException, TemplateException {
        final Template template = cfg.getTemplate(templatePath);
        template.process(templateVars, out);
        return template.toString();
    }
}
