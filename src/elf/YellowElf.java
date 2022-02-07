package elf;

import child.Child;
import database.Database;
import gift.Gift;
import santa.Santa;
import utils.Utils;

import java.util.ArrayList;

public class YellowElf extends Elf {

    public YellowElf(final Santa santa, final String type, final Child child) {
        super(santa, type, child);
    }

    /***
     *  Verifica daca copilul asociat lui a primit vreun cadou din partea mosului.
     *  Daca nu a primit niciun cadou alege cel mai ieftin cadou din categoria
     *  preferata de copil. Daca cel mai ieftin cadou din categoria preferata
     *  s-a epuizat atunci acesta nu va asigna niciun cadou copilului.
     */
    @Override
    public void elfAction() {
        if (getChild().getReceivedGifts() == null || getChild().
                getReceivedGifts().isEmpty()) {
            Gift gift = Utils.searchCheapestGiftFromCategory(
                    getChild().getGiftsPreferences().get(0),
                    Database.getDatabase().findAllSantaGifts()
            );
            if (gift != null && gift.getQuantity() > 0) {
                getChild().setReceivedGifts(new ArrayList<>());
                getChild().getReceivedGifts().add(gift);
                int quantity = gift.getQuantity() - 1;
                gift.setQuantity(quantity);
            }
        }
    }

}
