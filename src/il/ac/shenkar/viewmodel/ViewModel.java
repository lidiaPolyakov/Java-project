package il.ac.shenkar.viewmodel;

import il.ac.shenkar.includes.Category;
import il.ac.shenkar.includes.Currency;
import il.ac.shenkar.includes.Expense;
import il.ac.shenkar.includes.User;
import il.ac.shenkar.model.IModel;
import il.ac.shenkar.model.ModelException;
import il.ac.shenkar.view.IView;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


    /*  The ViewModel class represents the logic and state of the application's user interface.
        It connects the view and model components of the application, and provides methods for
        handling user interactions with the view and updating the model.
    */
public class ViewModel implements IViewModel{

    /* Variables declaration */
    private ExecutorService service;
    private IView view;
    private IModel model;
    private User user;


   /* Constructs a new ViewModel object with the specified view and model components. */
    public ViewModel(IView view, IModel model) {
        service = Executors.newFixedThreadPool(12);
        this.setView(view);
        this.setModel(model);
    }

    public ViewModel() {
        service = Executors.newFixedThreadPool(12);
    }

    @Override
    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    @Override
    public void createUser(String username, String password) {
        service.submit(() -> {
            try {
                model.createUser(username, password);
                view.setMessage("The user has been created successfully!");
            } catch (ModelException e) {
                e.printStackTrace();
                view.setMessage(e.getMessage());
            }
        });
    }

    @Override
    public boolean logUser(String username, String password) {

        try {
            user = model.findUser(username, password);
            if (user == null)
                return false;
        } catch (ModelException e) {
            e.printStackTrace();
            view.setMessage(e.getMessage());
        }
        return true;

    }

    @Override
    public void getUserName() {
        service.submit(() -> {
            String userName =  user != null ? user.getUsername() : "";
            view.setUserProfile(userName);
        });
    }

    @Override
    public void getCategories(int forPage) {
        service.submit(() -> {
            try {
                List<Category> categories = null;
                categories = model.getCategories();
                view.setCategories(categories, forPage);
            }
            catch (ModelException e) {
                e.printStackTrace();
                view.setMessage(e.getMessage());
            }
        });

    }

    @Override
    public void addCategory(String category) {
        service.submit(() -> {
            try {
                model.addCategory(category);
                new Thread(() -> view.setMessage("category " + category + " is added to the list")).start();
            } catch (ModelException e) {
                e.printStackTrace();
                view.setMessage(e.getMessage());
            }
        });
    }
    /* retrieves the category that the user has spent the most on */
    @Override
    public void getCommonCategory() {
        service.submit(() -> {
            try {
                Category category = null;
                category = model.getCommonCategory(user.getId());
                view.setCommonCategory(category);
            } catch (ModelException e) {
                e.printStackTrace();
                view.setMessage(e.getMessage());
            }
        });
    }

    /* retrieves the most expensive category for the user */
    @Override
    public void getExpensiveCategory() {

        service.submit(() -> {
            try {
                Category category = null;
                category = model.getExpensiveCategory(user.getId());
                view.setExpensiveCategory(category);
            } catch (ModelException e) {
                e.printStackTrace();
                view.setMessage(e.getMessage());
            }
        });

    }

    /* adds an expense record for the user with the specified category, sum, currency, and description */
    @Override
    public void addExpense(Integer catID, Integer sum, Currency currency, String description) {
        service.submit(() -> {
            try {
                model.addExpense(user.getId(), catID, sum, currency, description);
                view.setMessage("Added a record of an expense successfully!");
                getExpenses();
            } catch (ModelException e) {
                e.printStackTrace();
                view.setMessage(e.getMessage());
            }
        });
    }

    /* retrieves all expenses for the user */
    @Override
    public void getExpenses() {

        service.submit(() -> {
            try {
                List<Expense> expenses = model.getExpenses(user.getId());
                view.setExpenses(expenses);
            } catch (ModelException e) {
                e.printStackTrace();
                view.setMessage(e.getMessage());
            }
        });

    }

    /* retrieves all expenses for the user on a specific date */
    @Override
    public void getExpensesByDate(Integer day, Integer month, Integer year) {
        service.submit(() -> {
            try {
                List<Expense> expenses = model.getExpensesByDate(user.getId(), day, month, year);
                view.setExpenses(expenses);
                String formattedDay = String.format("%02d", day);
                String formattedMonth = String.format("%02d", month);
                view.setMessage("Here are all the expenses in " + year + "-" + formattedMonth + "-" + formattedDay);
            } catch (ModelException e) {
                e.printStackTrace();
                view.setMessage(e.getMessage());
            }
        });
    }
}
