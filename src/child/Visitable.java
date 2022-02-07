package child;

import santa.Visitor;

public interface Visitable {
    /***
     * Metoda necesara pentru a folosi design pattern-ul Visitor.
     * @param v obiectul visitor.
     */
    void accept(Visitor v);
}
