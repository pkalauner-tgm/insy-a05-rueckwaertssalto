package at.kalaunerritter.attributes;

/**
 * Created by Mathias on 26.12.14.
 */
public abstract class Modifier extends BaseAttribute {

    private BaseAttribute wrapper;

    public Modifier(BaseAttribute wrapper) {
        this.wrapper = wrapper;
    }

    public abstract String getHTMLValue();

    public BaseAttribute getWrapper() {
        return wrapper;
    }
}
