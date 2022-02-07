package factory;

import common.Constants;
import santa.Santa;
import strategy.GiftsAssignStrategy;
import strategy.IdStrategy;
import strategy.NiceScoreCityStrategy;
import strategy.NiceScoreStrategy;

/**
 * Clasa de tip factory pentru un strategy de asignare a cadourilor
 * pentru copii.
 */
public final class GiftsAssignStrategyFactory {
    /***
     * Metoda de tip factory a unei strategii de asignare a cadourilor
     * catre copii.
     * @param strategyType un String ce descrie tipul strategiei ce dorim
     *                     sa o instantiem.
     * @param santa un obiect de tip Santa necesar pentru a-l da ca parametru
     *              constructorului cu ajutorul caruia vom instantia strategia.
     * @return o instanta a unui obiect de tip declarat GiftsAssignStrategy.
     */
    public GiftsAssignStrategy createStrategy(final String strategyType, final Santa santa) {
        return switch (strategyType) {
            case Constants.ID -> new IdStrategy(santa);
            case Constants.NICE_SCORE -> new NiceScoreStrategy(santa);
            case Constants.NICE_SCORE_CITY -> new NiceScoreCityStrategy(santa);
            default -> null;
        };
    }

}
