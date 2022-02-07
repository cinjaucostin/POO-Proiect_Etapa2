package child;

import java.util.ArrayList;

/***
 * Clasa ce modeleaza schimbarile ce se pot produce asupra
 * unui copil:
 *  -un nou niceScore.
 *  -o lista cu noi preferinte a acestuia.
 *  -tipul de elf asociat copilului.
 */
public final class ChildrenUpdate {
    private int id;
    private Double niceScore;
    private ArrayList<String> giftsPreferences;
    private String elf;

    public ChildrenUpdate(final int id, final double niceScore,
                          final ArrayList<String> giftPreferences) {
        this.id = id;
        this.niceScore = niceScore;
        this.giftsPreferences = giftPreferences;
    }

    public ChildrenUpdate() {
        this(0, 0, null);
    }

    public int getId() {
        return id;
    }

    public Double getNiceScore() {
        return niceScore;
    }

    public ArrayList<String> getGiftsPreferences() {
        return giftsPreferences;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setNiceScore(final Double niceScore) {
        this.niceScore = niceScore;
    }

    public void setGiftsPreferences(final ArrayList<String> giftPreferences) {
        this.giftsPreferences = giftPreferences;
    }

    public String getElf() {
        return elf;
    }

    public void setElf(final String elf) {
        this.elf = elf;
    }
}
