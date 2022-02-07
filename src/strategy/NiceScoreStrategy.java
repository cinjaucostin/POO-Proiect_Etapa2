package strategy;

import child.Child;
import santa.Santa;
import utils.Utils;

public class NiceScoreStrategy implements GiftsAssignStrategy {
    private Santa santa;

    public NiceScoreStrategy(final Santa santa) {
        this.santa = santa;
    }

    /***
     * Strategie de impartire a cadourilor catre copii in functie de media
     * acestora: copii cu mediile mari au prioritate in fata mosului.
     */
    @Override
    public void assignGifts() {
        /*
            De aceasta data va trebui sa ordonam descrescator lista de copii
        a mosului dupa media acestora si crescator dupa id(cazul in care
        doi copii au medii egale). Practic nu ne intereseaza daca sunt din acelasi
        oras, in cazul in care vor fi doi copii din acelasi oras cu aceeasi medie
        ei vor fi oricum sortati crescator dupa id, deci mosul va trece in ordinea
        corecta pe la ei.
         */
        Utils.sortChildsByAvgAndId(santa.getChildrens());
        for (Child child : santa.getChildrens()) {
            child.accept(santa);
        }
        /*
            Dupa ce mosul a vizitat fiecare copil va trebui sa sortam inapoi
         lista crescator dupa id, pentru a fi corect introdusa in output.
         */
        Utils.sortChildsById(santa.getChildrens());
    }

}
