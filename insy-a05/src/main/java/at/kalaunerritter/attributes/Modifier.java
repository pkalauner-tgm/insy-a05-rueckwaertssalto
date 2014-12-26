package at.kalaunerritter.attributes;

/**
 * Ein Modifier ist ein Decorator fuer ein Datenbank-Attribut.
 * Es beinhaelt das eingepackte Datenbank-Attribut, welches dekoriert wird (z.b. als Primary Key, Foreign Key etc)
 *
 * @author Mathias Ritter 4AHIT
 * @version 20141226.1
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
