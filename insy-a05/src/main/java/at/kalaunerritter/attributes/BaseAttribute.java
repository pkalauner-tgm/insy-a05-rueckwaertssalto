package at.kalaunerritter.attributes;

/**
 * Created by Mathias on 26.12.14.
 */
public abstract class BaseAttribute {

    private String value;
    private String beginTags;
    private String endTags;

    public BaseAttribute() {
        this.value="";
        this.endTags="";
        this.beginTags="";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getHTMLValue() {
        return beginTags + value + endTags;
    }

    public void setBeginTags(String beginTags) {
        this.beginTags = beginTags;
    }

    public void setEndTags(String endTags) {
        this.endTags = endTags;
    }

    public String getBeginTags() {
        return beginTags;
    }

    public String getEndTags() {
        return endTags;
    }
}
