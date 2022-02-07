package strategy;

/**
 * Interfata ce declara o metoda void assignGifts() ce reprezinta
 * o strategie de impartire a cadourilor catre copii.
 */
public interface GiftsAssignStrategy {
    /***
     * Metoda prin intermediul careia se impart cadourile catre copii.
     * Aceasta va trebui implementata de fiecare clasa ce implementeaza interfata curenta,
     * in functie de strategia luata in calcul de fiecare in parte.
     */
    void assignGifts();

}
