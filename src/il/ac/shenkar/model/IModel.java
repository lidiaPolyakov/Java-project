package il.ac.shenkar.model;

import il.ac.shenkar.includes.Category;
import il.ac.shenkar.includes.Currency;
import il.ac.shenkar.includes.Expense;
import il.ac.shenkar.includes.User;

import java.util.List;
/* This interface represents the model layer of an expense tracking application. It defines methods for creating and finding users,
adding and retrieving expenses, and managing categories. It throws a ModelException if an error occurs while interacting with the model. */

public interface IModel {

    void createUser(String username, String password) throws ModelException;
    User findUser(String username, String password) throws ModelException;

    void addExpense(Integer userID, Integer catID, Integer sum, Currency currency, String description) throws ModelException;
    List<Expense> getExpenses(Integer userID) throws ModelException;
    List<Expense> getExpensesByDate(Integer userID, Integer day, Integer month, Integer year) throws ModelException;

    void addCategory(String category) throws ModelException;
    List<Category> getCategories() throws ModelException;
    Category getCommonCategory(Integer userID) throws ModelException;
    Category getExpensiveCategory(Integer userID) throws ModelException;
}
