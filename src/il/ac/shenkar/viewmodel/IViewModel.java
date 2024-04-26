package il.ac.shenkar.viewmodel;

import il.ac.shenkar.includes.Category;
import il.ac.shenkar.includes.Currency;
import il.ac.shenkar.includes.Expense;
import il.ac.shenkar.model.IModel;
import il.ac.shenkar.view.IView;

import java.util.List;

public interface IViewModel {
    void setView(IView view);
    void setModel(IModel model);
    void createUser(String username, String password);
    boolean logUser(String username, String password);
    void getUserName();

    void getCategories(int forPage);
    void addCategory(String category);
    void getCommonCategory();
    void getExpensiveCategory();

    void addExpense(Integer catID, Integer sum, Currency currency, String description);

    void getExpenses();
    void getExpensesByDate(Integer day, Integer month, Integer year);
}
