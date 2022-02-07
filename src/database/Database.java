package database;

import changes.AnnualChange;
import child.Child;
import gift.Gift;
import input.Input;

import java.util.ArrayList;
import java.util.List;

/**
 * Database-ul unde vor fi stocate toate informatiile necesare simularilor.
 */
public final class Database {
    private static Database instance = null;
    private static int numberOfYears;
    private static double santaBudget;
    private static final List<Child> CHILDRENS = new ArrayList<>();
    private static final List<Gift> SANTA_GIFTS_LIST = new ArrayList<>();
    private static final List<AnnualChange> ANNUAL_CHANGES = new ArrayList<>();

    private Database() {
    }

    /***
     *
     * @return o instanta noua a lui Database daca nu exista deja una, sau instanta
     * deja existenta.
     */
    public static Database getDatabase() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /***
     * Metoda de initializare a informatiilor din Database cu cele
     * existente intr-un obiect de tip Input.
     * @param input obiectul de tip Input din care vom extrage informatiile
     *              si le vom introduce in Database-ul nostru.
     */
    public void initInformationInDatabase(final Input input) {
        Database.getDatabase().addNumberOfYears(input.getNumberOfYears());
        Database.getDatabase().addSantaBudget(input.getSantaBudget());
        Database.getDatabase().addChildrens(input.getInitialData().getChildren());
        Database.getDatabase().addSantaGiftsList(input.getInitialData().getSantaGiftsList());
        Database.getDatabase().addAnnualChanges(input.getAnnualChanges());
    }

    /***
     * Metoda de golire a Database-ul.
     * Mai exact stergem toate informatiile din listele
     * de annualChanges, childrens si santaGifts.
     */
    public void clearInformationsFromDatabase() {
        Database.getDatabase().findAllAnnualChanges().clear();
        Database.getDatabase().findAllChildrens().clear();
        Database.getDatabase().findAllSantaGifts().clear();
    }

    /***
     *
     * @param nrOfYears numarul de ani pentru care vom realiza simulari
     */
    public void addNumberOfYears(final int nrOfYears) {
        Database.numberOfYears = nrOfYears;
    }

    /***
     *
     * @param budget bugetul lui Mos Craciun pentru anul curent
     */
    public void addSantaBudget(final double budget) {
        Database.santaBudget = budget;
    }

    /***
     *
     * @param childrens o lista de copii pe care o vom adauga in baza de date(copii
     *                  ce trebuie vizitati de Mos Craciun).
     */
    public void addChildrens(final List<Child> childrens) {
        Database.CHILDRENS.addAll(childrens);
    }

    /***
     *
     * @param santaGiftsList lista de cadouri pe care mosul le are disponibile si le
     *                       poate oferi copiilor
     */
    public void addSantaGiftsList(final List<Gift> santaGiftsList) {
        Database.SANTA_GIFTS_LIST.addAll(santaGiftsList);
    }

    /***
     *
     * @param annualChanges o lista de obiecte de tip AnnualChange ce reprezinta
     *                      schimbarile pentru urmatorii ani pentru care vom face simulari.
     */
    public void addAnnualChanges(final List<AnnualChange> annualChanges) {
        Database.ANNUAL_CHANGES.addAll(annualChanges);
    }

    /***
     *
     * @return intoarce numarul de ani pentru care vom face simulari.
     */
    public int findNumberOfYears() {
        return Database.numberOfYears;
    }

    /***
     *
     * @return intoarce bugetul mosului.
     */
    public double findSantaBudget() {
        return Database.santaBudget;
    }

    /***
     *
     * @return intoarce lista de copii din baza de date.
     */
    public List<Child> findAllChildrens() {
        return Database.CHILDRENS;
    }

    /***
     *
     * @return intoarce lista de cadouri a mosului din baza de date.
     */
    public List<Gift> findAllSantaGifts() {
        return Database.SANTA_GIFTS_LIST;
    }

    /***
     *
     * @return intoarce lista de schimbari anuale din baza de date.
     */
    public List<AnnualChange> findAllAnnualChanges() {
        return Database.ANNUAL_CHANGES;
    }

}
