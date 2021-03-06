package pojo;

import annotations.CsvBindByNameOrder;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvIgnore;
import com.opencsv.bean.CsvNumber;

import java.io.Serializable;
import java.util.Objects;

//annotation from
@CsvBindByNameOrder({"produktId","name","beschreibung","preis","summeBestand"})
public class Product {

    @CsvBindByName(column = "produktId")
    public String productId;

    @CsvBindByName(column = "name")
    public String name;

    @CsvBindByName(column = "beschreibung")
    public String description;

    @CsvBindByName(column = "preis", locale = "en-US")
    @CsvNumber("###0.00")
    public Float price;

    @CsvBindByName(column = "summeBestand")
    public Integer sumStockCount;

    @CsvIgnore
    private Integer sortKey;

    public Product(String productId, String name, String description, Float price, Integer sumStockCount) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.sumStockCount = sumStockCount;
    }

    public Product(Article article){
        this.productId = article.productId;
        this.name = article.name;
        this.description = article.description;
        this.price = article.price;
        this.sumStockCount = article.stockCount;
        this.sortKey = article.sortKey;
    }

    public Product() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getSortKey() {
        return sortKey;
    }

    public void setSortKey(Integer sortKey) {
        this.sortKey = sortKey;
    }

    public Integer getSumStockCount() {
        return sumStockCount;
    }

    public void setSumStockCount(Integer sumStockCount) {
        this.sumStockCount = sumStockCount;
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
