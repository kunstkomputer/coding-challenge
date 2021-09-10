package pojo;

import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

public class Article {
    int sortKey = 0;
    static int sortKeyCounter = 0;

    public Article(String id, String productId, String name, String description, float price, Integer stockCount) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockCount = stockCount;
        sortKey = sortKeyCounter;
        sortKeyCounter++;
    }

    public Article() {
        sortKey = sortKeyCounter;
        sortKeyCounter++;
    }

    @CsvBindByName
    public String id;
    @CsvBindByName(column = "produktId")
    public String productId;
    @CsvBindByName
    public String name;
    @CsvBindByName(column = "beschreibung")
    public String description;
    @CsvBindByName(column = "preis")
    public Float price;
    @CsvBindByName(column = "bestand")
    public Integer stockCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getSortKey() {
        return sortKey;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public boolean isInStock() {
        return this.stockCount > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return getId().equals(article.getId()) &&
                getProductId().equals(article.getProductId()) &&
                getName().equals(article.getName()) &&
                getDescription().equals(article.getDescription()) &&
                getPrice().equals(article.getPrice()) &&
                getStockCount().equals(article.getStockCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProductId(), getName(), getDescription(), getPrice(), getStockCount());
    }

}
