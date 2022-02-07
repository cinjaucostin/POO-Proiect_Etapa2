package utils;

import child.Child;
import com.fasterxml.jackson.databind.JsonNode;
import common.Constants;
import database.Database;
import enums.Category;
import enums.Cities;
import gift.Gift;
import gift.GiftOutput;
import input.Input;
import input.InputLoader;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import output.Output;
import output.WriteOutput;
import santa.Santa;
import simulation.Simulator;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Clasa cu metode statice pe care le-am folosit pe tot parcursul
 * implementarii.
 */
public final class Utils {

    private Utils() {
    }

    /***
     * Realizeaza simularea pentru un anume test.
     * @param i numarul testului caruia trebuie sa ii realizam simularea.
     * @throws IOException exceptie pentru citirea/scrierea in fisiere.
     * @throws ParseException exceptie pentru parsare.
     */
    public static void simulateTest(final int i) throws IOException, ParseException {
        InputLoader inputLoader = new InputLoader(Constants.INPUT_PATH
                + i + Constants.FILE_EXTENSION);

        Input input = inputLoader.readData();

        Database.getDatabase().initInformationInDatabase(input);

        Santa santa = new Santa(Database.getDatabase().findSantaBudget(),
                Database.getDatabase().findAllChildrens(),
                Database.getDatabase().findAllSantaGifts());

        Utils.addFirstNiceScoreToHistoryForChilds(Database.getDatabase().findAllChildrens());

        Utils.deleteDuplicatesFromGiftsPreferences(Database.getDatabase().findAllChildrens());

        Simulator simulator = new Simulator(santa, Database.getDatabase().findNumberOfYears());

        simulator.execute(Constants.ID);

        simulator.updateAndExecute();

        Output output = simulator.getOutput();
        JsonNode node = WriteOutput.toJson(output);
        WriteOutput.writeStringToFile(WriteOutput.stringify(node), Constants.OUTPUT_PATH
                + i + Constants.FILE_EXTENSION);
        Database.getDatabase().clearInformationsFromDatabase();
    }

    /***
     *
     * @param array array-ul de tip JSONArray pe care il vom converti in array de string-uri
     * @return array-ul de string-uri asociat array-ului de obiecte JSON.
     */
    public static ArrayList<String> convertJSONArray(final JSONArray array) {
        if (array != null) {
            ArrayList<String> finalArray = new ArrayList<>();
            for (Object object : array) {
                finalArray.add((String) object);
            }
            return finalArray;
        } else {
            return null;
        }
    }

    /***
     * Metoda cu ajutorul careia actualizam preferintele de cadouri ale unui
     * copil.
     * @param giftsPreferences lista de String-uri ce reprezinta noile preferinte
     *                         ale copilului in ceea ce priveste cadourile.
     * @param child copilul caruia trebuie sa ii facem actualizarea.
     */
    public static void updateGiftsPreferencesForChild(final List<String> giftsPreferences,
                                                      final Child child) {
        for (int i = giftsPreferences.size() - 1; i >= 0;
             i--) {
            if (child.getGiftsPreferences().contains(
                    giftsPreferences.get(i))) {
                child.getGiftsPreferences().remove(
                        giftsPreferences.get(i));
            }
            child.getGiftsPreferences().add(
                    0, giftsPreferences.get(i));
        }
    }

    /***
     *
     * @param id id-ul copilului pe care trebuie sa il cautam
     * @return instanta copilului al carui id coincide cu cel cautat
     *         null daca nu este gasit un copil cu id-ul cautat
     */
    public static Child searchChildById(final int id) {
        for (Child child : Database.getDatabase().findAllChildrens()) {
            if (child.getId() == id) {
                return child;
            }
        }
        return null;
    }

    /***
     *
     * @param childs lista de copii pentru care trebuie sa facem modificarea
     */
    public static void addFirstNiceScoreToHistoryForChilds(final List<Child> childs) {
        for (Child child : childs) {
            child.addFirstNiceScoreToHistory();
        }
    }

    /***
     *
     * @param category categoria dupa care trebuie sa selectam cadourile
     * @param gifts lista de cadouri din care trebuie sa le extragem doar pe cele
     *              cu categoria cautata
     * @return lista de cadouri ce fac parte din categoria cautata
     */
    public static List<Gift> searchGiftsByCategory(final String category,
                                                   final List<Gift> gifts) {
        List<Gift> giftsByCategory = new ArrayList<>();
        for (Gift gift : gifts) {
            if (gift.getCategory().equals(category) && gift.getQuantity() != 0) {
                giftsByCategory.add(gift);
            }
        }

        if (giftsByCategory.size() != 0) {
            giftsByCategory.sort((o1, o2) -> {
                if (o1.getPrice() < o2.getPrice()) {
                    return -1;
                } else if (o1.getPrice() > o2.getPrice()) {
                    return 1;
                }
                return 0;
            });
            return giftsByCategory;
        }
        return null;
    }

    /***
     *
     * @param childs lista de copii ce trebuie sortata crescator dupa id-ul acestora
     */
    public static void sortChildsById(final List<Child> childs) {
        childs.sort((o1, o2) -> {
            if (o1.getId() < o2.getId()) {
                return -1;
            } else if (o1.getId() > o2.getId()) {
                return 1;
            }
            return 0;
        });
    }

    /***
     *
     * @param childs lista cu copii carora trebuie sa le golim lista de cadouri primite.
     */
    public static void clearReceivedGiftsListForEveryChild(final List<Child> childs) {
        for (Child child : childs) {
            if (child.getReceivedGifts() != null) {
                child.getReceivedGifts().clear();
            }
        }
    }

    /***
     *
     * @param categories lista de String-uri ce reprezinta categoriile.
     * @return lista de obiecte de tip Category asociate cu string-urile din lista
     * pe care am primit-o ca parametru.
     */
    public static List<Category> stringListToCategoryList(final List<String> categories) {
        List<Category> categoryList = new ArrayList<>();
        for (String category : categories) {
            categoryList.add(Utils.stringToCategory(category));
        }
        return categoryList;
    }

    /***
     *
     * @param city un string ce reprezinta numele unui oras
     * @return un obiect de tip enum(Cities) ce reprezinta obiectul
     * din enum asociat string-ului primit ca si parametru.
     */
    public static Cities stringToCities(final String city) {
        return switch (city.toLowerCase()) {
            case "bucuresti" -> Cities.BUCURESTI;
            case "constanta" -> Cities.CONSTANTA;
            case "buzau" -> Cities.BUZAU;
            case "timisoara" -> Cities.TIMISOARA;
            case "cluj-napoca" -> Cities.CLUJ;
            case "iasi" -> Cities.IASI;
            case "craiova" -> Cities.CRAIOVA;
            case "brasov" -> Cities.BRASOV;
            case "braila" -> Cities.BRAILA;
            case "oradea" -> Cities.ORADEA;
            default -> null;
        };
    }

    /***
     *
     * @param category un string ce reprezinta categoria unui cadou
     * @return un obiect de tip Category asociat string-ului primit ca
     * si parametru.
     */
    public static Category stringToCategory(final String category) {
        return switch (category.toLowerCase()) {
            case "board games" -> Category.BOARD_GAMES;
            case "books" -> Category.BOOKS;
            case "clothes" -> Category.CLOTHES;
            case "sweets" -> Category.SWEETS;
            case "technology" -> Category.TECHNOLOGY;
            case "toys" -> Category.TOYS;
            default -> null;
        };
    }

    /***
     * Primeste o lista de obiecte de tip Gift si intoarce o lista de obiect
     * de tip GiftOutput.
     * @param gifts lista de obiecte de tip Gift.
     * @return lista de obiecte de tip GiftOutput.
     */
    public static List<GiftOutput> giftListToGiftOutputList(final List<Gift> gifts) {
        List<GiftOutput> giftsOutput = new ArrayList<>();
        for (Gift gift : gifts) {
            giftsOutput.add(new GiftOutput(gift.getProductName(),
                    gift.getPrice(), Utils.stringToCategory(gift.getCategory())));
        }
        return giftsOutput;
    }

    /***
     *
     * @param gifts O lista de obiecte de tip Gift
     * @return prima instanta a carui camp quantity este mai mare ca 0(adica primul cadou
     * care mai este disponibil).
     */
    public static Gift findCheapestGiftAvailable(final List<Gift> gifts) {
        if (gifts != null) {
            for (Gift gift : gifts) {
                if (gift.getQuantity() > 0) {
                    return gift;
                }
            }
        }
        return null;
    }

    /***
     * Metoda care ne intoarce cel mai ieftin cadou dintr-o lista de
     * cadouri data ca parametru.
     * @param gifts lista de cadouri din care trebuie sa il extragem
     *              pe cel mai ieftin.
     * @return cel mai ieftin cadou gasit in lista.
     */
    public static Gift findCheapestGift(final List<Gift> gifts) {
        Gift cheapest = gifts.get(0);
        for (Gift gift : gifts) {
            if (gift.getPrice() < cheapest.getPrice()) {
                cheapest = gift;
            }
        }
        return cheapest;
    }

    /***
     * Metoda de sortare a unei liste de obiecte de tip Child
     * avand ca si prim criteriu media acestora(descrescator) si
     * ca si criteriu secundar id-ul lor(crescator).
     * @param childs lista de copii ce trebuie sortata.
     */
    public static void sortChildsByAvgAndId(final List<Child> childs) {
        childs.sort((o1, o2) -> {
            if (o1.getAverageNiceScore() < o2.getAverageNiceScore()) {
                return 1;
            } else if (o1.getAverageNiceScore() > o2.getAverageNiceScore()) {
                return -1;
            } else {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });
    }

    /***
     * Metoda ce creeaza un map in care pe post de key avem un string ce
     * reprezinta numele unui oras si pe post de value o lista de obiecte
     * de tip Child ce reprezinta o lista cu toti copii ce locuiesc
     * in respectivul oras.
     * @param childs lista de copii ce trebuie introdusi in map in functie
     *               de orasul in care locuiesc.
     * @return HashMap-ul cu informatiile detaliate mai sus.
     */
    public static Map<String, List<Child>> createMapWithCities(final List<Child> childs) {
        Map<String, List<Child>> map = new HashMap<>();
        for (Child child : childs) {
            if (map.containsKey(child.getCity())) {
                map.get(child.getCity()).add(child);
            } else {
                List<Child> childsByCity = new ArrayList<>();
                childsByCity.add(child);
                map.put(child.getCity(), childsByCity);
            }
        }
        for (Map.Entry<String, List<Child>> entry : map.entrySet()) {
            Utils.sortChildsById(entry.getValue());
        }
        return map;
    }

    /***
     * Metoda ce creeaza un hashmap care are pe post de key numele unui oras si
     * pe post de value media acelui oras(media aritmetica a niceScoreAverage-ul
     * copiilor ce locuiesc in acel oras).
     * @param map un hashmap care are pe post de key numele orasului si pe post
     *            de value o lista cu toti copii ce locuiesc in acel oras, astfel
     *            avand posibilitatea ca noi sa parcurgem fiecare entry din acest
     *            hashmap si sa calculam media pentru fiecare oras in parte.
     * @return un hashmap ce are pe post de key un string ce reprezinta numele orasului
     * si pe post de value un double ce reprezinta media orasului.
     */
    public static Map<String, Double> createMapWithCitiesAndTheirAvg(
            final Map<String, List<Child>> map) {
        Map<String, Double> avgCitiesMap = new HashMap<>();
        for (Map.Entry<String, List<Child>> entry : map.entrySet()) {
            avgCitiesMap.put(entry.getKey(), findAvgOfACity(entry.getValue()));
        }
        return avgCitiesMap;
    }

    /***
     * O metoda ce calculeaza media unui oras.
     * @param childs lista de copii dintr-un oras(nu ne intereseaza ce oras,
     *               noi doar dorim sa calculam media aritmetica a niceScoreAverage-urilor
     *               unei liste de copii).
     * @return un double ce reprezinta media orasului.
     */
    public static double findAvgOfACity(final List<Child> childs) {
        double average = 0;
        for (Child child : childs) {
            average += child.getAverageNiceScore();
        }
        return average / childs.size();
    }

    /***
     * Metoda prin care sortam descrescator dupa value un map.
     * @param unsortedMap map-ul nesortat.
     * @return map-ul sortat descrescator dupa value.
     */
    public static Map<String, Double> sortMapByValue(final Map<String, Double> unsortedMap) {
        List<Map.Entry<String, Double>> list = new LinkedList<>(unsortedMap.entrySet());
        list.sort((o1, o2) -> {
            if (o1.getValue() > o2.getValue()) {
                return -1;
            } else if (o1.getValue() < o2.getValue()) {
                return 1;
            } else {
                if (o1.getKey().compareTo(o2.getKey()) < 0) {
                    return -1;
                } else if (o1.getKey().compareTo(o2.getKey()) > 0) {
                    return 1;
                }
                return 0;
            }
        });
        Map<String, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    /***
     * Metoda prin care stergem categoriile care apar de mai multe ori in lista
     * de preferinte de cadouri a unui copil.
     * @param childs lista de copii pentur care dorim sa aplicam acest lucru.
     */
    public static void deleteDuplicatesFromGiftsPreferences(final List<Child> childs) {
        for (Child child : childs) {
            Set<String> set = new LinkedHashSet<>(child.getGiftsPreferences());
            child.getGiftsPreferences().clear();
            child.getGiftsPreferences().addAll(set);
        }
    }

    /***
     * Metoda ce intoarce cel mai ieftin cadou dintr-o lista
     * data ca parametru si ce apartine unei anumite categorii.
     * @param gifts lista de cadouri.
     * @param category categoria dupa care cautam cadoul.
     * @return cel mai ieftin cadou pe care l-am gasit.
     */
    public static Gift searchCheapestGiftFromCategory(final String category,
                                                      final List<Gift> gifts) {
        List<Gift> giftsByCategory = new ArrayList<>();
        for (Gift gift : gifts) {
            if (gift.getCategory().equals(category)) {
                giftsByCategory.add(gift);
            }
        }
        if (giftsByCategory != null) {
            return findCheapestGift(giftsByCategory);
        }
        return null;
    }

}
