package il.ac.shenkar.includes;

import java.util.Objects;

/* The Currency class represents a currency and its exchange rate. */
public class Currency {

    private String currencyCode;
    private Float rate;

    public Currency(String currencyCode, Float rate) {
        this.setCurrency(currencyCode);
        this.setRate(rate);
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrency(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency that = (Currency) o;
        return currencyCode.equals(that.currencyCode) && rate.equals(that.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyCode, rate);
    }

    @Override
    public String toString() {
        return "Code=" + currencyCode + ", Rate=" + rate;
    }

}
