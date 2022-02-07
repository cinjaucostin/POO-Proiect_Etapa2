package strategy;

import child.Child;
import santa.Santa;
import utils.Utils;

import java.util.List;
import java.util.Map;

public class NiceScoreCityStrategy implements GiftsAssignStrategy {
    private Santa santa;

    public NiceScoreCityStrategy(final Santa santa) {
        this.santa = santa;
    }

    /***
     * Strategie de impartire a cadourilor catre copii in functie de orasul in
     * care se afla, mai exact media acestuia. Se sorteaza descrescator orasele
     * in functie de media acestora si se viziteaza mai intai orasele cu mediile
     * cele mai mari. In cazul in care sunt mai multi copii ce apartin de acelasi
     * oras acestia vor fi vizitati in ordinea crescatoare a id-urilor lor.
     */
    @Override
    public void assignGifts() {
        Map<String, List<Child>> mapWithCitiesAndTheirChilds =
                Utils.createMapWithCities(santa.getChildrens());

        Map<String, Double> mapWithCitiesAndTheirAvgSorted =
                Utils.sortMapByValue(Utils.createMapWithCitiesAndTheirAvg(
                        mapWithCitiesAndTheirChilds));

        for (Map.Entry<String, Double> entry : mapWithCitiesAndTheirAvgSorted.entrySet()) {
            for (Child child : mapWithCitiesAndTheirChilds.get(entry.getKey())) {
                child.accept(santa);
            }
        }
    }

}
