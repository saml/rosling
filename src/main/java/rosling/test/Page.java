package rosling.test;

import rosling.annotation.JcrNode;
import rosling.annotation.JcrProp;

/**
 * @author saml
 */
@JcrNode
public class Page {
    @JcrProp
    private String text;

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
