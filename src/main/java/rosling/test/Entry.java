package rosling.test;

import rosling.annotation.JcrNode;
import rosling.annotation.JcrProp;

/**
 * @author saml
 */
@JcrNode
public class Entry {
    @JcrProp
    private String name;

    @JcrProp
    private Boolean isMale;

    @JcrProp
    private Long age;

    @JcrNode
    private Page page;

    public Boolean getIsMale() {
        return isMale;
    }

    public void setIsMale(Boolean male) {
        isMale = male;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return page;
    }
}
