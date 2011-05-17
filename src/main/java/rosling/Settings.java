package rosling;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.osgi.OsgiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * application settings.
 */
@Component(metatype = true, immediate = true)
@Service(Settings.class)
public class Settings {
    private static final Logger log = LoggerFactory.getLogger(Settings.class);

    @Property(description = "absolute path to where templates are. ex, /mnt/media/", value = {})
    private static final String PROP_TEMPLATE_DIR = "templateDir";

    @Property(description = "static file server. ex, http://cdn.example.com/ If you're using filesystem resource, you could use /static/ or similar.", value = {})
    private static final String PROP_STATIC_URL = "staticUrl";

    private String templateDir;
    private String staticUrl;

    @Activate
    private void activate(Map<String, ?> config) {
        templateDir = OsgiUtil.toString(config.get(PROP_TEMPLATE_DIR), null);
        staticUrl = OsgiUtil.toString(config.get(PROP_STATIC_URL), null);
        log.debug("templateDir: {}", templateDir);
        log.debug("staticUrl: {}", staticUrl);
    }

    public String getTemplateDir() {
        return templateDir;
    }

    public String getStaticUrl() {
        return staticUrl;
    }
}
