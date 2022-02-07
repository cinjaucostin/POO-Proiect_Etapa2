package elf;

import child.Child;
import santa.Santa;

public class WhiteElf extends Elf {
    public WhiteElf(final Santa santa, final String type, final Child child) {
        super(santa, type, child);
    }

    /***
     * In cazul elfului "white" nu trebuie sa facem nimic deoarece acesta
     * nu influenteaza in niciun fel bugetul sau niceScore-ul copilului asociat lui.
     */
    @Override
    public void elfAction() {

    }

}
