package at.kalaunerritter.attributes;

/**
 * Der Foreign-Key ist ein Decorator, welcher ein Datenbank-Attribut zum Foreign-Key macht.
 * Dabei werden die notwendigen HTML-Tags hinzugefuegt
 *
 * @author Mathias Ritter 4AHIT
 * @version 20141226.1
 */
public class ForeignKey extends Modifier {


    public ForeignKey(String foreignTable, String foreignAttribute, BaseAttribute wrapper) {
        super(wrapper);

        //HTML-Tags hinzufuegen
        this.setBeginTags("<i>" + super.getWrapper().getBeginTags());
        this.setEndTags(super.getWrapper().getEndTags() + "</i>");

        //Die Value wird geandert, entweder auf attr4: RelY.attrZ oder nur auf RelY.attrZ bei gleichnamigen Attributen
        if (super.getWrapper().getValue().contains(foreignAttribute))
            this.setValue(foreignTable + "." + super.getWrapper().getValue());
        else
            this.setValue(super.getWrapper().getValue() + ": " + foreignTable + "." + foreignAttribute);

    }

    @Override
    public String getHTMLValue() {

        return this.getBeginTags() + this.getValue() + this.getEndTags();

    }
}
