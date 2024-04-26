package il.ac.shenkar.view;

import il.ac.shenkar.includes.Category;
import il.ac.shenkar.includes.Currency;
import il.ac.shenkar.includes.Expense;
import il.ac.shenkar.view.page.AppPage;
import il.ac.shenkar.view.page.IPage;
import il.ac.shenkar.view.page.LoginPage;
import il.ac.shenkar.view.page.component.ComponentException;
import il.ac.shenkar.viewmodel.IViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class View extends JFrame implements IView {

    // Variables declaration
    private IViewModel viewModel;
    private IPage page;
    private JPanel mainPanel;

    // Login page buttons
    private JButton signUpBtn, signInBtn;

    // Nav buttons
    JButton expensePageBtn;
    JButton categoryPageBtn;
    JButton reportPageBtn;
    JButton logOutBtn;

    // Section buttons
    JButton reportSubmitBtn;
    JButton expenseSubmitBtn;
    JButton categorySubmitBtn;

    // End of variables declaration

    public View() {
        this.loadLoginPage();
    }

    @Override
    public void start() {
        EventQueue.invokeLater(() -> this.setVisible(true));
    }

    @Override
    public void setViewModel(IViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void setMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public void setUserProfile(String userName)  {
        try {
            page.setData(userName,1);
        } catch (ComponentException e) {
            e.printStackTrace();
            this.setMessage(e.getMessage());
        }
    }

    @Override
    public void setCommonCategory(Category category) {
        try {
            this.page.setData(category, 6);
        } catch (ComponentException ex) {
            ex.printStackTrace();
            this.setMessage(ex.getMessage());
        }
    }

    @Override
    public void setExpensiveCategory(Category category) {
        try {
            this.page.setData(category, 8);
        } catch (ComponentException e) {
            e.printStackTrace();
            this.setMessage(e.getMessage());
        }
    }

    @Override
    public void setExpenses(List<Expense> expenses) {
        try {
            this.page.setData(expenses, 11);
        } catch (ComponentException ex) {
            ex.printStackTrace();
            this.setMessage(ex.getMessage());
        }
    }

    @Override
    public void setCategories(List<Category> categories, int forPage) {
        try {
            if(forPage == 0) {
                this.page.setData(categories, -1);
                this.page.setData(null, 2);
            }
            else
                this.page.setData(categories, 7);
        } catch (ComponentException ex) {
            ex.printStackTrace();
            this.setMessage(ex.getMessage());
        }
    }

    private void refresh() {
        this.getContentPane().removeAll();
        this.mainPanel = page.getMainPanel();
        this.initComponents();
    }

    private void loadLoginPage() {
        this.expensePageBtn = null;
        this.categoryPageBtn = null;
        this.reportPageBtn = null;
        this.logOutBtn = null;
        this.reportSubmitBtn = null;
        this.expenseSubmitBtn = null;
        this.categorySubmitBtn = null;

        this.page = new LoginPage();
        JButton[] buttons = page.getButtons();
        this.signUpBtn = buttons[0];
        this.signInBtn = buttons[1];
        //handling button events
        ButtonsListener listener = new ButtonsListener();
        this.signUpBtn.addActionListener(listener);
        this.signInBtn.addActionListener(listener);

        this.setTitle("Login");
        this.setSize(800, 400);
        refresh();
    }

    private void initAppPage() {
        this.signUpBtn = null;
        this.signInBtn = null;

        this.page = new AppPage();
        JButton[] buttons = page.getButtons();

        this.expensePageBtn = buttons[0];
        this.categoryPageBtn = buttons[1];
        this.reportPageBtn = buttons[2];
        this.logOutBtn = buttons[3];
        this.expenseSubmitBtn = buttons[4];
        this.categorySubmitBtn = buttons[5];
        this.reportSubmitBtn = buttons[6];

        //handling button events
        ButtonsListener listener = new ButtonsListener();
        this.expensePageBtn.addActionListener(listener);
        this.categoryPageBtn.addActionListener(listener);
        this.reportPageBtn.addActionListener(listener);
        this.logOutBtn.addActionListener(listener);
        this.reportSubmitBtn.addActionListener(listener);
        this.expenseSubmitBtn.addActionListener(listener);
        this.categorySubmitBtn.addActionListener(listener);

        this.setTitle("Cost Manager Application");
        this.setSize(1100, 750);
        refresh();
        viewModel.getUserName();

    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(this.mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(this.mainPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        pack();
    }

    private void loadExpensePage() {
        try {
            this.page.setData(this.expensePageBtn, -1);
            this.page.setData(null, 0);
            this.viewModel.getCategories(0);
        } catch (ComponentException ex) {
            this.setMessage(ex.getMessage());
        }
    }

    private void loadCategoryPage() {
        try {
            this.page.setData(this.categoryPageBtn, -1);
            this.page.setData(null, 5);
            this.viewModel.getCategories(1);
            this.viewModel.getCommonCategory();
            this.viewModel.getExpensiveCategory();
        } catch (ComponentException ex) {
            this.setMessage(ex.getMessage());
        }
    }

    private void loadReportPage() {

        try {
            this.page.setData(this.reportPageBtn, -1);
            this.page.setData(null, 10);
            this.viewModel.getExpenses();
        } catch (ComponentException ex) {
            this.setMessage(ex.getMessage());
        }

    }

    class ButtonsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == signUpBtn) {
                JTextField[] fields = page.getFields();
                String userName = fields[0].getText();
                String password = fields[1].getText();
                fields = null;
                viewModel.createUser(userName, password);
            }
            else if (e.getSource() == signInBtn) {
                JTextField[] fields = page.getFields();
                String userName = fields[0].getText();
                String password = fields[1].getText();
                fields = null;
                if(viewModel.logUser(userName, password))
                    initAppPage();
                else
                    setMessage("Invalid username or password!");
            }
            else if (e.getSource() == logOutBtn) {
                loadLoginPage();
            }
            else if (e.getSource() == expensePageBtn) {
                loadExpensePage();
            }
            else if (e.getSource() == categoryPageBtn) {
                loadCategoryPage();
                System.out.println("categoryPageBtn");
            }
            else if (e.getSource() == reportPageBtn) {
                loadReportPage();
                System.out.println("reportPageBtn");
            }
            else if (e.getSource() == reportSubmitBtn) {
                JComboBox<Integer>[] date = page.getIntComboBoxes();

                Integer year =  (Integer) date[0].getSelectedItem();
                Integer month = (Integer) date[1].getSelectedItem();
                Integer day =   (Integer) date[2].getSelectedItem();

                viewModel.getExpensesByDate(day, month, year);
            }
            else if (e.getSource() == expenseSubmitBtn) {
                JComboBox<Category>[] categoryComboBoxes = page.getCategoryComboBoxes();
                JComboBox<Currency>[] currencyComboBoxes = page.getCurrencyComboBoxes();
                JSpinner[] spinners = page.getSpinners();
                JTextField[] fields = page.getFields();

                Category category = (Category) categoryComboBoxes[0].getSelectedItem();
                Currency currency = (Currency) currencyComboBoxes[0].getSelectedItem();

                int sum = (int) spinners[0].getValue();
                String description = fields[0].getText();

                assert category != null;
                viewModel.addExpense(category.getId(), sum, currency, description);
                loadReportPage();
            }
            else if (e.getSource() == categorySubmitBtn) {
                JTextField[] fields = page.getFields();
                viewModel.addCategory(fields[1].getText());
                loadCategoryPage();
            }
        }
    }
}
