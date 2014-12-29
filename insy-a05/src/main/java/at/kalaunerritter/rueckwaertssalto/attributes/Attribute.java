package at.kalaunerritter.rueckwaertssalto.attributes;

/**
 * Dieses Attribut stellt ein Standardattribut dar, welches nur einen Namen bzw. einen Wert besitzt,
 * welcher durch den Konstruktor gesetzt wird.
 * Dieses Attribut kann dekoriert werden, z.B. als Primary Key oder Foreign Key
 *
 * @author Mathias Ritter 4AHIT
 * @version 20141226.1
 */
public class Attribute extends BaseAttribute {

    /**
     * Der Superkonstruktor wird aufgerufen und der Name/Wert des Attributs wird gesetzt.
     *
     * @param value value des Attributs
     */
    public Attribute(String value) {
        super(value);
    }
}
