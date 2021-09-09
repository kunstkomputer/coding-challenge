package pojo;

import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

public class Product {

    @CsvBindByName(column = "produktId")
    public String productId;
    @CsvBindByName
    public String name;
    @CsvBindByName(column = "beschreibung")
    public String description;
    @CsvBindByName(column = "preis")
    public Float price;
    @CsvBindByName(column = "summeBestand")
    public Integer sumStockCount;

    public Product(String productId, String name, String description, Float price, Integer sumStockCount) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.sumStockCount = sumStockCount;
    }

    public Product() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId.equals(product.productId) &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                price.equals(product.price) &&
                sumStockCount.equals(product.sumStockCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, description, price, sumStockCount);
    }
}
