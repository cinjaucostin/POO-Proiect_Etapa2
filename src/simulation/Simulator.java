package simulation;

import changes.AnnualChange;
import child.Child;
import common.Constants;
import elf.Elf;
import factory.AnnualOutputFactory;
import factory.ElfFactory;
import factory.GiftsAssignStrategyFactory;
import child.ChildrenUpdate;
import database.Database;
import output.AnnualOutput;
import output.Output;
import santa.Santa;
import strategy.GiftsAssignStrategy;
import utils.Utils;

/**
 * Clasa ce implementeaza interfata Simulation si cu ajutorul caruia
 * vom simula venirea mosului pe parcursul a mai multi ani.
 */
public final class Simulator implements Simulation {
    private final Santa santa;
    private final Output output = new Output();
    private int nrOfYears;

    /***
     *
     * @param santa obiectul de tip Santa pe care il vom introduce in campul corespunzator
     * @param nrOfYears numarul de ani pentru care vom face simulari.
     */
    public Simulator(final Santa santa, final int nrOfYears) {
        this.santa = santa;
        this.nrOfYears = nrOfYears;
    }

    @Override
    public void execute(final String strategyType) {
        Utils.sortChildsById(santa.getChildrens());
        santa.calculateAgeCategoryForEveryChild();
        santa.removeYoungAdults();
        santa.calculateNiceScoreAverageForEveryChild();
        santa.addNiceScoreBonusForEveryChild();
        santa.calculateBudgetUnit();
        santa.calculateAllocatedBudgetForEveryChild();
        ElfFactory elfFactory = new ElfFactory();
        for (Child child : santa.getChildrens()) {
            if (!child.getElf().equals(Constants.YELLOW)) {
                Elf elf = elfFactory.createElf(santa, child.getElf(), child);
                elf.elfAction();
            }
        }
        GiftsAssignStrategyFactory strategyFactory = new GiftsAssignStrategyFactory();
        GiftsAssignStrategy strategy = strategyFactory.createStrategy(strategyType, santa);
        strategy.assignGifts();
        for (Child child : santa.getChildrens()) {
            if (child.getElf().equals(Constants.YELLOW)) {
                Elf elf = elfFactory.createElf(santa, child.getElf(), child);
                elf.elfAction();
            }
        }
        AnnualOutputFactory annualOutputFactory = new AnnualOutputFactory();
        AnnualOutput annualOutput = annualOutputFactory.createAnnualOutput(santa.getChildrens());
        output.getAnnualChildren().add(annualOutput);
        for (Child child : santa.getChildrens()) {
            child.grow();
        }
        Utils.clearReceivedGiftsListForEveryChild(santa.getChildrens());
        nrOfYears--;
    }

    @Override
    public void updateAndExecute() {
        for (AnnualChange annualChange : Database.getDatabase().findAllAnnualChanges()) {
            if (nrOfYears >= 0) {
                santa.setSantaBudget(annualChange.getNewSantaBudget());
                santa.getSantaGiftsList().addAll(annualChange.getNewGifts());
                santa.getChildrens().addAll(annualChange.getNewChildren());
                Utils.addFirstNiceScoreToHistoryForChilds(
                        annualChange.getNewChildren());
                Utils.deleteDuplicatesFromGiftsPreferences(
                        Database.getDatabase().findAllChildrens());
                for (ChildrenUpdate childrenUpdate : annualChange.getChildrenUpdates()) {
                    Child child = Utils.searchChildById(childrenUpdate.getId());
                    if (child != null) {
                        child.setElf(childrenUpdate.getElf());
                        if (childrenUpdate.getNiceScore() != null) {
                            child.getNiceScoreHistory().add(childrenUpdate.getNiceScore());
                        }
                        Utils.updateGiftsPreferencesForChild(childrenUpdate.
                                getGiftsPreferences(), child);

                    }
                }
                execute(annualChange.getStrategy());
            }
        }
    }

    /***
     *
     * @return Obiectul de tip Output creat in urma simularii tuturor rundelor.
     */
    public Output getOutput() {
        return output;
    }

}
