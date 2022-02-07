package factory;

import child.Child;
import output.AnnualOutput;
import output.ChildOutput;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Clasa de tip factory pentru un obiect de tip AnnualOutput.
 * Se creeaza cate un ChildOutput pentru fiecare Child din
 * lista primita ca parametru si se introduce in lista din AnnualOutput.
 */
public class AnnualOutputFactory {
    /***
     * Metoda cu ajutorul careia cream obiectul de tip AnnualOutput ce contine
     * o lista de obiecte de tip ChildOutput.
     * @param childs lista de copii pe care o vom parcurge si pentru fiecare
     *               Child pe care il contine vom crea un obiect ChildOutput asociat
     *               pe care il vom introduce din lista din obiectul AnnualOutput.
     * @return obiectul de tip AnnualOutput creat.
     */
    public AnnualOutput createAnnualOutput(final List<Child> childs) {
        AnnualOutput annualOutput = new AnnualOutput();
        for (Child child : childs) {
            if (child.getReceivedGifts() != null) {
                annualOutput.getChildren().add(new ChildOutput(
                        child.getId(), child.getLastName(), child.getFirstName(),
                        Utils.stringToCities(child.getCity()), child.getAge(),
                        Utils.stringListToCategoryList(child.getGiftsPreferences()),
                        child.getAverageScore(), new ArrayList<>(child.getNiceScoreHistory()),
                        child.getAssignedBudget(),
                        Utils.giftListToGiftOutputList(child.getReceivedGifts())
                ));
            } else {
                annualOutput.getChildren().add(new ChildOutput(
                        child.getId(), child.getLastName(), child.getFirstName(),
                        Utils.stringToCities(child.getCity()), child.getAge(),
                        Utils.stringListToCategoryList(child.getGiftsPreferences()),
                        child.getAverageScore(), new ArrayList<>(child.getNiceScoreHistory()),
                        child.getAssignedBudget(), new ArrayList<>()
                ));
            }
        }
        return annualOutput;
    }

}
