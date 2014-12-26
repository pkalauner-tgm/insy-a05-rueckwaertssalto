package at.kalaunerritter.attributes;

/**
 * Der Primary-Key ist ein Decorator, welcher ein Datenbank-Attribut zum Primary-Key macht.
 * Dabei werden die notwendigen HTML-Tags hinzugefuegt
 *
 * @author Mathias Ritter 4AHIT
 * @version 20141226.1
 */
public class PrimaryKey extends Modifier {

    public PrimaryKey(BaseAttribute wrapper) {
        super(wrapper);

        //HTML-Tags hinzufuegen (unterstrichen)
        this.setBeginTags("<u>" + super.getWrapper().getBeginTags());
        this.setEndTags(super.getWrapper().getEndTags() + "</u>");

        this.setValue(super.getWrapper().getValue());
    }

    @Override
    public String getHTMLValue() {
        return this.getBeginTags() + this.getValue() + this.getEndTags();
    }
}
