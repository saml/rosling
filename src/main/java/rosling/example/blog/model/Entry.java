package rosling.example.blog.model;

import rosling.annotation.JcrNode;
import rosling.annotation.JcrProp;

import java.util.Calendar;

/**
 * @author saml
 */
@JcrNode
public class Entry {
    @JcrProp
    private String text;

    @JcrProp
    private String title;

    @JcrProp
    private Calendar lastModified;

    @JcrProp
    private String author;

    public Entry() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Calendar getLastModified() {
        return lastModified;
    }

    public void setLastModified(Calendar lastModified) {
        this.lastModified = lastModified;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
