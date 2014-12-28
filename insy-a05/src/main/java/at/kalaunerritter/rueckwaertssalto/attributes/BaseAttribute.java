package at.kalaunerritter.rueckwaertssalto.attributes;

/**
 * Diese Klasse beinhaelt alle Eigenschaften eines Datenbank-Attributes als HTML Tags:
 * Ein Decorator fuegt die HTML Tags zum Namen bzw. zur Value des Datenbank-Attributes hinzu.
 *
 * @author Mathias Ritter 4AHIT
 * @version 20141226.1
 */
public abstract class BaseAttribute {

    private String value;

    /**
     * Attribute werden standardmaessig auf leeren Text gesetzt
     */
    public BaseAttribute() {
        this.value = "";
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
        return getBeginTags() + getValue() + getEndTags();
    }


    public String getBeginTags() {
        return "";
    }

    public String getEndTags() {

        return "";
    }

    @Override
    public String toString() {
        return getValue();
    }
}
