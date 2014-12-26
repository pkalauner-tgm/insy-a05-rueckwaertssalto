package at.kalaunerritter.attributes;

/**
 * Diese Klasse beinhaelt alle Eigenschaften eines Datenbank-Attributes als HTML Tags:
 * Ein Decorator fuegt die HTML Tags zum Namen bzw. zur Value des Datenbank-Attributes hinzu.
 *
 * @author Mathias Ritter 4AHIT
 * @version 20141226.1
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


    /**
     * Zusammenbauen des HTML-Strings:
     * Diese besteht aus den Anfang-Tags, dem Namen/Value des Datenbank-Attributes und den End-Tags
     *
     * @return HTML-Darstellung des Datenbank-Attributes
     */
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
