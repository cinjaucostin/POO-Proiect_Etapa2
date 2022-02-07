package factory;

import child.Child;
import common.Constants;
import elf.BlackElf;
import elf.WhiteElf;
import elf.PinkElf;
import elf.YellowElf;
import elf.Elf;
import santa.Santa;

/**
 * Clasa de tip factory pentru un obiect de tip Elf.
 */
public class ElfFactory {
    /***
     *
     * @param type tipul elfului pe care trebuie sa il instantiem.
     * @param child copilul asociat elfului.
     * @return o instanta a unui obiect de extinde Elf, in functie
     * de tipul primit ca si parametru.
     */
    public Elf createElf(final Santa santa, final String type, final Child child) {
        return switch (type) {
            case Constants.BLACK -> new BlackElf(santa, type, child);
            case Constants.PINK -> new PinkElf(santa, type, child);
            case Constants.YELLOW -> new YellowElf(santa, type, child);
            case Constants.WHITE -> new WhiteElf(santa, type, child);
            default -> null;
        };
    }

}
