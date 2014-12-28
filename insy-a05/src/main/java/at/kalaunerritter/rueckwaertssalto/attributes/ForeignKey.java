package at.kalaunerritter.rueckwaertssalto.attributes;

/**
 * Der Foreign-Key ist ein Decorator, welcher ein Datenbank-Attribut zum Foreign-Key macht.
 * Dabei werden die notwendigen HTML-Tags hinzugefuegt
 *
 * @author Mathias Ritter 4AHIT
 * @version 20141226.1
 */
public class ForeignKey extends Modifier {

    private String foreignTable, foreignAttribute;


    /**
     * Es werden die HTML-Tags für das Attribut hinzugefuegt und die Value geandert
     *
     * @param foreignTable die Tabelle, aus dem der Foreign Key stammt
     * @param foreignAttribute das Attribut, aus das der Foreign Key verweist
     * @param wrapper das Attribut, das dekoriert wird
     */
    public ForeignKey(String foreignTable, String foreignAttribute, BaseAttribute wrapper) {
        super(wrapper);

        this.foreignAttribute = foreignAttribute;
        this.foreignTable = foreignTable;

    }


    @Override
    public String getBeginTags() {
        return "<i>" + getWrapper().getBeginTags();
    }

    @Override
    public String getEndTags() {
        return getWrapper().getEndTags() + "</i>";
    }

    @Override
    public String getValue() {

        //Die Value wird geandert, entweder auf attr4: RelY.attrZ oder nur auf RelY.attrZ bei gleichnamigen Attributen
        if (super.getWrapper().getValue().contains(foreignAttribute))
            return foreignTable + "." + super.getWrapper().getValue();
        else
            return super.getWrapper().getValue() + ": " + foreignTable + "." + foreignAttribute;
    }

    public String getForeignTable() {
        return foreignTable;
    }
}
