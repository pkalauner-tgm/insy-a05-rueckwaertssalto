package at.kalaunerritter.attributes;

/**
 * Created by Mathias on 26.12.14.
 */
public class ForeignKey extends Modifier {

    private String foreignTable, foreignAttribute;

    public ForeignKey(String foreignTable, String foreignAttribute, BaseAttribute wrapper) {
        super(wrapper);
        this.foreignAttribute = foreignAttribute;
        this.foreignTable = foreignTable;

        this.setBeginTags("<i>" + super.getWrapper().getBeginTags());
        this.setEndTags(super.getWrapper().getEndTags() + "</i>");

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
