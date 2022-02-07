package elf;

import child.Child;
import common.Constants;
import santa.Santa;

public class PinkElf extends Elf {

    public PinkElf(final Santa santa, final String type, final Child child) {
        super(santa, type, child);
    }

    /***
     * In cazul elfului "pink" trebuie sa crestem bugetul alocat copilului asociat
     * acestuia cu 30%.
     */
    @Override
    public void elfAction() {
        double budget = this.getChild().getAssignedBudget();
        double modification = this.getChild().getAssignedBudget()
                * Constants.THIRTY / Constants.ONE_HUNDRED;
        this.getChild().setAssignedBudget(budget + modification);
    }

}
