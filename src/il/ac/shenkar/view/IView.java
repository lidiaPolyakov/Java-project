package il.ac.shenkar.view;

import il.ac.shenkar.includes.Category;
import il.ac.shenkar.includes.Expense;
import il.ac.shenkar.viewmodel.IViewModel;

import java.util.List;

public interface IView {
    void start();
    void setViewModel(IViewModel viewModel);
    void setMessage(String message);
    void setUserProfile(String username);
    void setCategories(List<Category> categories, int forPage);
    void setCommonCategory(Category category);
    void setExpensiveCategory(Category category);
    void setExpenses(List<Expense> expenses);
}
