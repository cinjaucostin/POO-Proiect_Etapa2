package input;

import changes.AnnualChange;

import java.util.List;

/**
 * Clasa creata pentru a putea parsa din fisierul de intrare json si
 * a retine informatiile din acesta.
 * Ea este create exact dupa format-ul pe care ni-l sugereaza input-ul:
 * ->un int numberOfYears(numarul de ani pentru care vom simula).
 * ->un double santaBudget ce reprezinta bugetul mosului.
 * ->un obiect de tip InitialData.
 * ->o lista annualChanges.
 */
public final class Input {
    private int numberOfYears;
    private double santaBudget;
    private InitialData initialData;
    private List<AnnualChange> annualChanges;

    public Input(final int numberOfYears, final double santaBudget,
                 final InitialData initialData, final List<AnnualChange> annualChanges) {
        this.numberOfYears = numberOfYears;
        this.santaBudget = santaBudget;
        this.initialData = initialData;
        this.annualChanges = annualChanges;
    }

    public Input() {
        this(0, 0, null, null);
    }

    public int getNumberOfYears() {
        return numberOfYears;
    }

    public void setNumberOfYears(final int numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public double getSantaBudget() {
        return santaBudget;
    }

    public void setSantaBudget(final double santaBudget) {
        this.santaBudget = santaBudget;
    }

    public InitialData getInitialData() {
        return initialData;
    }

    public void setInitialData(final InitialData initialData) {
        this.initialData = initialData;
    }

    public List<AnnualChange> getAnnualChanges() {
        return annualChanges;
    }

    public void setAnnualChanges(final List<AnnualChange> annualChanges) {
        this.annualChanges = annualChanges;
    }

}
