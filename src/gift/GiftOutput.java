package gift;

import enums.Category;

/**
 * Clasa in care vom retine datele despre un cadou ce trebuie
 *      scrise in fisierul de output.
 *         Nu m-am mai putut folosi de clasa Gift deoarece aceasta contine
 *      si campul quantity, informatie pe care noi nu avem nevoie sa o
 *      scriem in fisierele de output.
 */
public final class GiftOutput {
    private String productName;
    private double price;
    private Category category;

    public GiftOutput(final String productName, final double price,
                final Category category) {
        this.productName = productName;
        this.price = price;
        this.category = category;
    }

    public GiftOutput() {
        this(null, 0, null);
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

}
