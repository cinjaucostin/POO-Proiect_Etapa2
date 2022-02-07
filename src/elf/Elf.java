package elf;

import child.Child;
import santa.Santa;

/**
 * Clasa ce modeleaza un elf, ea contine mai multe campuri precum:
 *  -santa(instanta lui Santa);
 *  -type(tipul elfului:black, pink, yellow, white);
 *  -child(copilul asociat elfului).
 */
public abstract class Elf {
    private Santa santa;
    private String type;
    private Child child;

    /***
     * Metoda prin care elf-ul modifica campurile unui obiect de tip Child dupa
     * regulile specificate in cerinta.
     */
    public abstract void elfAction();

    public Elf(final Santa santa, final String type, final Child child) {
        this.santa = santa;
        this.type = type;
        this.child = child;
    }

    /***
     * Getter pentru campul child.
     * @return obiectul de tip Child asociat elfului.
     */
    public Child getChild() {
        return child;
    }

    /***
     * Setter pentru campul child.
     * @param child obiectul de tip Child pe care dorim
     *              sa il introducem in campul corespunzator.
     */
    public void setChild(final Child child) {
        this.child = child;
    }

    /***
     * Getter pentru type-ul elfului.
     * @return un string ce reprezinta type-ul elfului.
     */
    public String getType() {
        return type;
    }

    /***
     * Setter pentru campul type al elfului.
     * @param type String ce reprezinta type-ul
     *             pe care dorim sa il setam elfului.
     */
    public void setType(final String type) {
        this.type = type;
    }

    /***
     * Getter pentru campul santa.
     * @return un obiect de tip Santa.
     */
    public Santa getSanta() {
        return santa;
    }

    /***
     * Setter pentru campul santa
     * @param santa Obiectul de tip santa pe care
     *              dorim sa il setam pe campul corespunzator.
     */
    public void setSanta(final Santa santa) {
        this.santa = santa;
    }

}
