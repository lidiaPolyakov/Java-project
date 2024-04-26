package il.ac.shenkar.includes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExchangeRates {

    private static List<Currency> currencyList;

    /* Method to get an array of currency codes */
    public static List<Currency> getCurrencies() {
        /* updates the list of Currency objects */
        updateList();
        return currencyList;
    }

    /* Method to create a list of Currency objects from the exchange rates data */
    private static void updateList() {
        /* fetch the exchange rates JSON data */
        JsonNode ratesNode = null;
        try {
            ratesNode = fetch();

            /* iterate over the currency/rate pairs in the conversion_rates object */
            currencyList = new ArrayList<>();
            assert ratesNode != null;
            Iterator<String> currencyIterator = ratesNode.fieldNames();
            while (currencyIterator.hasNext()) {
                String currencyCode = currencyIterator.next();
                Float rate = ratesNode.get(currencyCode).floatValue();
                Currency currency = new Currency(currencyCode, rate);
                currencyList.add(currency);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            useDefaultList();
        }
    }


    private static void useDefaultList() {
        currencyList = List.of(new Currency[]{new Currency("ILS", (float) 1.0), new Currency("GBP", (float) 0.231), new Currency("EUR", (float) 0.2614), new Currency("USD", (float) 0.2776)});
    }

    /* Method to fetch exchange rates data from the API */
    private static JsonNode fetch() throws IOException {
        URL url = new URL("https://v6.exchangerate-api.com/v6/4557961eba4f1d9c9778d2a5/latest/ILS");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(url);
        /*  extract the conversion_rates object from the JSON data */
        return rootNode.get("conversion_rates");
    }



}
