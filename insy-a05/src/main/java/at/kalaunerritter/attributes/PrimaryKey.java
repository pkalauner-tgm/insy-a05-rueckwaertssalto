package at.kalaunerritter.attributes;

/**
 * Created by Mathias on 26.12.14.
 */
public class PrimaryKey extends Modifier {

    public PrimaryKey(BaseAttribute wrapper) {
        super(wrapper);
        this.setBeginTags("<u>" + super.getWrapper().getBeginTags());
        this.setEndTags(super.getWrapper().getEndTags() + "</u>");
        this.setValue(super.getWrapper().getValue());
    }

    @Override
    public String getHTMLValue() {

        return this.getBeginTags() + super.getWrapper().getValue() + this.getEndTags();
    }
}
