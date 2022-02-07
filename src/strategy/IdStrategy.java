package strategy;

import child.Child;
import santa.Santa;
import utils.Utils;

public class IdStrategy implements GiftsAssignStrategy {
    private Santa santa;

    public IdStrategy(final Santa santa) {
        this.santa = santa;
    }

    /***
     * Strategie de impartire a cadourilor catre copii in ordinea
     * crescatoare a id-urilor acestora.
     */
    @Override
    public void assignGifts() {
        Utils.sortChildsById(santa.getChildrens());
        for (Child child : santa.getChildrens()) {
            child.accept(santa);
        }
    }

}
