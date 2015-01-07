package at.kalaunerritter.rueckwaertssalto.attributes;

/**
 * Created by Mathias on 07.01.15.
 */
public class Unique extends Modifier {


    public Unique(BaseAttribute wrapper) {
        super(wrapper);
    }

    @Override
    public String getBeginTags() {
        return getWrapper().getBeginTags();
    }

    @Override
    public String getEndTags() {
        return getWrapper().getEndTags() + "[UNIQUE]";
    }

    @Override
    public String getValue() {
        return getWrapper().getValue();
    }

    @Override
    public boolean isPrimaryKey() {
        return getWrapper().isPrimaryKey();
    }

    @Override
    public boolean isForeignKey() {
        return getWrapper().isForeignKey();
    }

    @Override
    public boolean isUnique() {
        return true;
    }
}
