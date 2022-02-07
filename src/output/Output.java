package output;

import java.util.ArrayList;
import java.util.List;

/**
 * Clasa creata de asemenea pentru a obtine un fisier de iesire de tip json
 * dupa format-ul cerut.
 * Contine o lista de AnnualOutput(pentru fiecare simulare vom avea un obiect
 * de tip AnnualOutput, care la randul lui va contine o lista de obiecte de tip
 * ChildOutput(informatiile despre un copil dupa simularea unei runde).
 */
public class Output {
    private List<AnnualOutput> annualChildren = new ArrayList<>();

    /***
     *
     * @return lista de obiecte de tip ChildrenOutput din clasa curenta
     */
    public List<AnnualOutput> getAnnualChildren() {
        return annualChildren;
    }

    /***
     *
     * @param annualChild o lista de obiecte de tip ChildrenOutput pe care o vom seta
     *                    pe campul corespunzator.
     */
    public void setAnnualChildren(final List<AnnualOutput> annualChild) {
        this.annualChildren = annualChild;
    }

}
