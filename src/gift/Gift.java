package gift;

/**
 * Clasa in care vom retine datele asociate unui cadou.
 */
public final class Gift {
    private String productName;
    private double price;
    private String category;
    private int quantity;

    public Gift(final String productName, final double price,
                final String category, final int quantity) {
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }

    public Gift() {
        this(null, 0, null, 0);
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

}
