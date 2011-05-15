package rosling.annotation;

import javax.jcr.nodetype.NodeType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author saml
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface JcrNode {
    String primaryType() default NodeType.NT_UNSTRUCTURED;
}
