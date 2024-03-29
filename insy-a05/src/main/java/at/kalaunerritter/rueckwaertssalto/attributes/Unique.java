package at.kalaunerritter.rueckwaertssalto.attributes;

/**
 * Unique ist ein Decorator, welcher ein Attribut zu einem Unqiue-Attribut macht.
 *
 * @author Mathias Ritter 4AHIT
 * @version 20150107.1
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
        return getWrapper().getEndTags() + " &lt;UNIQUE&gt;";
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
}
