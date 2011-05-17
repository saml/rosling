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

    private final Configuration cfg;
    private String templateDir;

    @Activate
    private void activate(Map<String, ?> config) {
        templateDir = settings.getTemplateDir();
        log.info("templateDir: {}", templateDir);
    }

    public FreeMarkerService() throws IOException {
        //settings = slingScriptHelper.getService(Settings.class);
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
