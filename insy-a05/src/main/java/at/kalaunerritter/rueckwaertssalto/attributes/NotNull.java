package at.kalaunerritter.rueckwaertssalto.attributes;

/**
 * Unique ist ein Decorator, welcher ein Attribut als NOT NULL kennzeichnet
 *
 * @author Mathias Ritter 4AHIT
 * @version 20150107.1
 */
public class NotNull extends Modifier {


    public NotNull(BaseAttribute wrapper) {
        super(wrapper);
    }

    @Override
    public String getBeginTags() {
        return getWrapper().getBeginTags();
    }

    @Override
    public String getEndTags() {
        return getWrapper().getEndTags() + " &lt;NOT NULL&gt;";
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
