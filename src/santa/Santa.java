package santa;

import child.Child;
import common.Constants;
import gift.Gift;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Clasa ce implementeaza interfata Visitor si care
 * il reprezinta pe Mos Craciun deoarece intruneste atat o parte
 * din caracteristicile posibile ale acestuia(bugetul pe care il are,
 * unitatea de buget, o lista cu copii si o lista cu cadourile pe care
 * le poate oferi) cat si o serie de actiuni specifice acestuia(vizitarea
 * copiilor din lista sa, calculul mediei pentru fiecare pentru a sti cat
 * buget sa aloce pentru fiecare in parte, calculul categoriei de varsta
 * pentru fiecare, etc).
 */
public final class Santa implements Visitor {
    private double santaBudget;
    private double budgetUnit;
    private List<Child> childrens;
    private List<Gift> santaGiftsList;

    /***
     * Metoda prin care Mos Craciun viziteaza un copil si ii ofera acestuia cadourile
     * pe care si le doreste si care se incadreaza in bugetul alocat lui.
     * @param child copilul ce trebuie vizitat
     */
    @Override
    public void visit(final Child child) {
        double budgetUsed = 0;
        for (String category : child.getGiftsPreferences()) {
            List<Gift> gifts = Utils.searchGiftsByCategory(category, this.santaGiftsList);
            if (gifts != null) {
                Gift gift = Utils.findCheapestGiftAvailable(gifts);
                if (gift != null) {
                    if (budgetUsed + gift.getPrice() <= child.getAssignedBudget()) {
                        if (child.getReceivedGifts() == null) {
                            List<Gift> giftsReceived = new ArrayList<>();
                            giftsReceived.add(gift);
                            child.setReceivedGifts(giftsReceived);
                        } else {
                            child.getReceivedGifts().add(gift);
                        }
                        int quantity = gift.getQuantity() - 1;
                        gift.setQuantity(quantity);
                        budgetUsed += gift.getPrice();
                    }
                }
            }
        }
    }

    /***
     * Metoda prin care stergem din lista de copii toti cei care au categoria de varsta
     * YOUNG_ADULT, deci ei nu va mai trebui sa primeasca cadouri.
     */
    public void removeYoungAdults() {
        childrens.removeIf(child -> child.getAgeCategory().equals(Constants.YOUNG_ADULT));
    }

    /**
     * In functie de intervalul in care se afla varsta fiecarui copil din lista
     * ii setam acestuia o categorie de varsta din cele specificate in enunt.
     */
    public void calculateAgeCategoryForEveryChild() {
        for (Child child : childrens) {
            if (child.getAge() < Constants.BABY_AGE) {
                child.setAgeCategory(Constants.BABY);
            } else if (child.getAge() >= Constants.BABY_AGE
                    && child.getAge() < Constants.KID_AGE) {
                child.setAgeCategory(Constants.KID);
            } else if (child.getAge() >= Constants.KID_AGE
                    && child.getAge() <= Constants.TEEN_AGE) {
                child.setAgeCategory(Constants.TEEN);
            } else if (child.getAge() > Constants.TEEN_AGE) {
                child.setAgeCategory(Constants.YOUNG_ADULT);
            }
        }
    }

    /***
     * Metoda prin care Mosul calculeaza media pentru fiecare copil din lista dupa
     * regulile date, in functie de categoria de varsta a fiecaruia.
     */
    public void calculateNiceScoreAverageForEveryChild() {
        for (Child child : childrens) {
            switch (child.getAgeCategory()) {
                case Constants.BABY -> child.setAverageNiceScore(Constants.TEN_SCORE);
                case Constants.KID -> {
                    double sum = 0;
                    for (Double score : child.getNiceScoreHistory()) {
                        sum += score;
                    }
                    child.setAverageNiceScore(sum / child.getNiceScoreHistory().size());
                }
                case Constants.TEEN -> {
                    double sum1 = 0;
                    double sum2 = 0;
                    for (int i = 0; i < child.getNiceScoreHistory().size(); i++) {
                        sum1 += (child.getNiceScoreHistory().get(i) * (i + 1));
                        sum2 = sum2 + (i + 1);
                    }
                    child.setAverageNiceScore(sum1 / sum2);
                }
                default -> {
                }
            }
        }
    }

    /***
     * Metoda prin care Mosul calculeaza budgetUnit-ul.
     * Aduna averageNiceScore-ul pentru fiecare copil intr-o suma si pentru
     * a obtine budgetUnit-ul imparte bugetul sau(santaBudget) la suma obtinuta.
     */
    public void calculateBudgetUnit() {
        double sum = 0;
        for (Child copil : childrens) {
            sum += copil.getAverageNiceScore();
        }
        this.budgetUnit = this.santaBudget / sum;
    }

    /***
     * Metoda prin care Mosul calculeaza bugetul alocat pentru fiecare copil din lista.
     * O face in modul urmator: bugetul alocat unui copil este media sa inmultita cu
     * budgetUnit-ul calculat.
     */
    public void calculateAllocatedBudgetForEveryChild() {
        for (Child copil : childrens) {
            copil.setAssignedBudget(copil.getAverageNiceScore() * this.budgetUnit);
        }
    }

    /***
     * Metoda prin care Mosul adauga la media fiecarui copil in parte si bonusul
     * copilului. Daca media da un numar mai mare ca 10 atunci mosul va trunchia
     * aceasta valoare la valoarea maxima(10).
     */
    public void addNiceScoreBonusForEveryChild() {
        for (Child copil : childrens) {
            double modification = copil.getAverageNiceScore() * copil.getNiceScoreBonus()
                    / Constants.ONE_HUNDRED;
            modification += copil.getAverageNiceScore();
            copil.setAverageNiceScore(modification);
            if (copil.getAverageNiceScore() > Constants.TEN_SCORE) {
                copil.setAverageNiceScore(Constants.TEN_SCORE);
            }
        }

    }

    public Santa(final double santaBudget, final List<Child> childrens,
                 final List<Gift> santaGiftsList) {
        this.santaBudget = santaBudget;
        this.childrens = childrens;
        this.santaGiftsList = santaGiftsList;
    }

    public Santa() {
        this(0, null, null);
    }

    public double getSantaBudget() {
        return santaBudget;
    }

    public void setSantaBudget(final double santaBudget) {
        this.santaBudget = santaBudget;
    }

    public List<Child> getChildrens() {
        return childrens;
    }

    public void setChildrens(final List<Child> childrens) {
        this.childrens = childrens;
    }

    public List<Gift> getSantaGiftsList() {
        return santaGiftsList;
    }

    public void setSantaGiftsList(final List<Gift> santaGiftsList) {
        this.santaGiftsList = santaGiftsList;
    }

}
