package il.ac.shenkar.includes;

public class Expense {

    private Integer id;
    private Integer userId;
    private String category;
    private Integer sum;
    private Currency currency;
    private String description;
    private String createdAt;

    public Expense(Integer id, Integer userId, String category, Integer sum, String currency, Float currencyRate, String description, String createdAt) {
        this.currency = new Currency(currency, currencyRate);
        this.setId(id);
        this.setUserId(userId);
        this.setCategory(category);
        this.setSum(sum);
        this.setDescription(description);
        this.setCreatedAt(createdAt);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Float getCurrencyRate() {
        return this.getCurrency().getRate();
    }

    public void setCurrencyRate(Float currencyRate) {
        this.currency.setRate(currencyRate);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", userId=" + userId +
                ", category='" + category + '\'' +
                ", sum=" + sum +
                ", currency=" + currency +
                ", description='" + description + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
