package il.ac.shenkar.view.page.component;

import il.ac.shenkar.includes.Category;
import il.ac.shenkar.includes.Currency;
import il.ac.shenkar.includes.ExchangeRates;
import il.ac.shenkar.includes.Expense;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Section implements IComponent {

    // Variables declaration
    JPanel section;
    JPanel container;

    // Report page
    JComboBox<Integer> yearComboBox;
    JComboBox<Integer> monthComboBox;
    JComboBox<Integer> dayComboBox;
    JButton reportSubmitBtn;
    JLabel totalExpenses;
    JLabel expensesCount;
    JTable expensesTable;

    // Category page
    JLabel expensiveCategory;
    JLabel commonCategory;
    JButton categorySubmitBtn;
    JTextField categoryField;
    JTable categoryTable;
    
    // Expense page
    JComboBox<Category> categoryComboBox;
    JComboBox<Currency> currencyCodeComboBox;
    JSpinner expenseSumField;
    JTextField descriptionField;
    JButton expenseSubmitBtn;
    // End of variables declaration

    public Section() {
        initComponent();
    }

    private void initComponent() {
        section = new JPanel();
        container = new JPanel();
        expenseSubmitBtn = new JButton();
        categorySubmitBtn = new JButton();
        reportSubmitBtn = new JButton();

        categoryComboBox = new JComboBox<>();
        currencyCodeComboBox = new JComboBox<>();
        expenseSumField = new JSpinner();
        descriptionField = new JTextField();

        categoryField = new JTextField();

        monthComboBox = new JComboBox<>();
        dayComboBox = new JComboBox<>();
        reportSubmitBtn = new JButton();
        expensesTable = new JTable();
        yearComboBox = new JComboBox<>();
        setWelcomePage();
    }

    public JButton getReportSubmitBtn() {
        return reportSubmitBtn;
    }

    public JButton getCategorySubmitBtn() {
        return categorySubmitBtn;
    }

    public JButton getExpenseSubmitBtn() {
        return expenseSubmitBtn;
    }

    public JTextField getCategoryField() {
        return categoryField;
    }

    public JTextField getDescriptionField() {
        return descriptionField;
    }

    @Override
    public JPanel getComponent() {
        return section;
    }

    @Override
    public JButton[] getButtons() {
        return new JButton[]{this.getExpenseSubmitBtn(), this.getCategorySubmitBtn(), this.getReportSubmitBtn()};
    }

    @Override
    public JTextField[] getFields() {
        return new JTextField[]{ this.getDescriptionField(), this.getCategoryField()};
    }



    @Override
    public List<JComboBox<Integer>> getIntComboBoxes() {
        List<JComboBox<Integer>> comboBoxList = new ArrayList<>();
        comboBoxList.add(yearComboBox);
        comboBoxList.add(monthComboBox);
        comboBoxList.add(dayComboBox);
        return comboBoxList;
    }

    @Override
    public List<JComboBox<Category>> getCategoryComboBoxes() {
        List<JComboBox<Category>> comboBoxList = new ArrayList<>();
        comboBoxList.add(categoryComboBox);
        return comboBoxList;
    }

    @Override
    public List<JComboBox<Currency>> getCurrencyComboBoxes() {
        List<JComboBox<Currency>> comboBoxList = new ArrayList<>();
        comboBoxList.add(currencyCodeComboBox);
        return comboBoxList;
    }

    @Override
    public JSpinner[] getSpinners() {
        return new JSpinner[] {expenseSumField};
    }

    @Override
    public void setData(Object data, Integer actionID) throws ComponentException {
        switch (actionID) {
            case 0 -> setExpensePage();
            case 1 -> setCategoryComboBox((List<Category>) data);
            case 2 -> setCurrencies();
            case 5 -> setCategoryPage();
            case 6 -> setCommonCategory((Category) data);
            case 7 -> setCategoryTable((List<Category>) data);
            case 8 -> setExpensiveCategory((Category) data);
            case 10 -> setReportPage();
            case 11 -> setExpensesData((List<Expense>) data);
            default -> throw new ComponentException("Section - unknown action ID in setData method");
        }
    }

    private void setExpensiveCategory(Category category) {
        if(category == null) {
            expensiveCategory.setText("non yet");
        }
        else {
            expensiveCategory.setText(category.getCategory());
        }
    }

    private void setCurrencies() {
        currencyCodeComboBox.setModel(new DefaultComboBoxModel<>(ExchangeRates.getCurrencies().toArray(new Currency[0])));
    }

    private void setCategoryComboBox(List<Category> categories) {
        categoryComboBox.setModel(new DefaultComboBoxModel<>(categories.toArray(new Category[0])));
    }

    private void setExpensesData(List<Expense> expenses) {
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        AtomicReference<Integer> count = new AtomicReference<>(0);
        String[][] tableData = expenses.stream()
                .map(expense -> new String[]{
                        expense.getCategory(),
                        expense.getSum().toString(),
                        expense.getCurrency().getCurrencyCode(),
                        expense.getCurrency().getRate().toString(),
                        Float.toString(expense.getSum() / expense.getCurrency().getRate()),
                        expense.getDescription(),
                        expense.getCreatedAt().toString()
                })
                .toArray(String[][]::new);

        expenses.forEach(expense -> {
            total.updateAndGet(v -> (double) (v + (expense.getSum() / expense.getCurrency().getRate())));
            count.getAndSet(count.get() + 1);
        });
        setExpenseTable(tableData);
        setTotalExpenses(total);
        setExpensesCount(count);

    }
    private void setExpenseTable(String[][] expensesData) {
        expensesTable.setModel(new javax.swing.table.DefaultTableModel(
                expensesData,
                new String [] {
                        "Category", "Sum", "Currency", "Rate", "ILS Total", "Description", "Date"
                }
        ));
    }

    private void setTotalExpenses(AtomicReference<Double> total) {
        double formattedTotal = Double.parseDouble(String.format("%.2f", total.get()));
        totalExpenses.setText(Double.toString(formattedTotal));
    }

    private void setExpensesCount(AtomicReference<Integer> count) {
        expensesCount.setText(count.toString());
    }

    private void setCategoryTable(List<Category> categories) {
        String[][] tableData = categories.stream()
                .map(category -> new String[]{category.getId().toString(), category.getCategory()})
                .toArray(String[][]::new);

        categoryTable.setModel(new javax.swing.table.DefaultTableModel(
                tableData,
                new String [] {
                        "ID", "Category"
                }
        ));
    }

    private void setCommonCategory(Category category) {
        if(category == null) {
            commonCategory.setText("non yet");
        }
        else {
            commonCategory.setText(category.getCategory());
        }
    }

    private void setWelcomePage() {

        JLabel text = new JLabel();
        JLabel text2 = new JLabel();
        JLabel text3 = new JLabel();

        JLabel java = new JLabel();
        JLabel rights = new JLabel();

        java.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/il/ac/shenkar/includes/images/java.png")))); // NOI18N

        text.setText("Welcome to our expense tracking application! ");

        text2.setText("With our user-friendly interface, you can easily record your expenses");

        text3.setText(" and keep track of your spending habits. Start managing your finances today!");
        text3.setVerifyInputWhenFocusTarget(false);
        text3.setVerticalTextPosition(SwingConstants.TOP);

        GroupLayout containerLayout = new GroupLayout(container);
        container.setLayout(containerLayout);
        containerLayout.setHorizontalGroup(
                containerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, containerLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(text2)
                                .addGap(250, 250, 250))
                        .addGroup(GroupLayout.Alignment.TRAILING, containerLayout.createSequentialGroup()
                                .addContainerGap(376, Short.MAX_VALUE)
                                .addComponent(java)
                                .addGap(376, 376, 376))
                        .addGroup(containerLayout.createSequentialGroup()
                                .addGroup(containerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(containerLayout.createSequentialGroup()
                                                .addGap(315, 315, 315)
                                                .addComponent(text))
                                        .addGroup(containerLayout.createSequentialGroup()
                                                .addGap(226, 226, 226)
                                                .addComponent(text3)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        containerLayout.setVerticalGroup(
                containerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(containerLayout.createSequentialGroup()
                                .addGap(181, 181, 181)
                                .addComponent(java)
                                .addGap(18, 18, 18)
                                .addComponent(text)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(text2)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(text3)
                                .addContainerGap(181, Short.MAX_VALUE))
        );
        rights.setFont(new Font("Helvetica Neue", 0, 10)); // NOI18N
        rights.setText("©Ebraheem Elnakeeb & Gilad Schneider. 2023 all rights reserved");

        GroupLayout sectionLayout = new GroupLayout(section);
        section.setLayout(sectionLayout);
        sectionLayout.setHorizontalGroup(
                sectionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(sectionLayout.createSequentialGroup()
                                .addGroup(sectionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(sectionLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(rights))
                                        .addGroup(sectionLayout.createSequentialGroup()
                                                .addGap(30, 30, 30)
                                                .addComponent(container, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(30, Short.MAX_VALUE))
        );
        sectionLayout.setVerticalGroup(
                sectionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, sectionLayout.createSequentialGroup()
                                .addContainerGap(40, Short.MAX_VALUE)
                                .addComponent(container, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(rights, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
    }
    private void setReportPage() {

        section.removeAll();
        container.removeAll();

        JPanel formPanel = new JPanel();
        JLabel formTitle = new JLabel();
        JLabel formTitle2 = new JLabel();
        JLabel yearLabel = new JLabel();
        JLabel monthLabel = new JLabel();
        JLabel dayLabel = new JLabel();
        JPanel totalPanel = new JPanel();
        JLabel totalLabel = new JLabel();
        totalExpenses = new JLabel();
        JPanel countPanel = new JPanel();
        JLabel countLabel = new JLabel();
        expensesCount = new JLabel();
        JScrollPane ScrollPane = new JScrollPane();
        JLabel rights = new JLabel();


        container.setBackground(new Color(238,238,238));
        formPanel.setBackground(new Color(255, 255, 255));

        formTitle.setFont(new Font("Helvetica Neue", 0, 18)); // NOI18N
        formTitle.setForeground(new Color(0, 142, 155));
        formTitle.setText("Filter your expenses");

        formTitle2.setFont(new Font("Helvetica Neue", 0, 18)); // NOI18N
        formTitle2.setForeground(new Color(0, 142, 155));
        formTitle2.setText("by month and year");

        yearLabel.setFont(new Font("Helvetica Neue", 0, 14)); // NOI18N
        yearLabel.setText("Year");

        yearComboBox.setModel(new DefaultComboBoxModel<>(new Integer[] { 2019, 2020, 2021, 2022,2023, 2024 }));

        monthLabel.setFont(new Font("Helvetica Neue", 0, 14)); // NOI18N
        monthLabel.setText("Month");

        monthComboBox.setModel(new DefaultComboBoxModel<>(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}));

        dayLabel.setFont(new Font("Helvetica Neue", 0, 14)); // NOI18N
        dayLabel.setText("Day - optional");

        dayComboBox.setModel(new DefaultComboBoxModel<>(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31}));

        reportSubmitBtn.setFont(new Font("Helvetica Neue", 0, 18)); // NOI18N
        reportSubmitBtn.setText("Filter");


        GroupLayout formPanelLayout = new GroupLayout(formPanel);
        formPanel.setLayout(formPanelLayout);
        formPanelLayout.setHorizontalGroup(
                formPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(formPanelLayout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addGroup(formPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(formTitle)
                                        .addComponent(formTitle2, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 50, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, formPanelLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(reportSubmitBtn, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72))
                        .addGroup(formPanelLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(formPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, formPanelLayout.createSequentialGroup()
                                                .addGroup(formPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(yearComboBox, GroupLayout.Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(monthComboBox, GroupLayout.Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(dayComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(20, 20, 20))
                                        .addGroup(formPanelLayout.createSequentialGroup()
                                                .addGroup(formPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(dayLabel)
                                                        .addComponent(monthLabel)
                                                        .addComponent(yearLabel))
                                                .addGap(0, 0, Short.MAX_VALUE))))
        );
        formPanelLayout.setVerticalGroup(
                formPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(formPanelLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(formTitle, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(formTitle2)
                                .addGap(23, 23, 23)
                                .addComponent(yearLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(yearComboBox, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(monthLabel)
                                .addGap(3, 3, 3)
                                .addComponent(monthComboBox, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(dayLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dayComboBox, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(reportSubmitBtn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16))
        );

        totalPanel.setBackground(new Color(255, 255, 255));

        totalLabel.setText("Total expenses");

        totalExpenses.setFont(new Font("Helvetica Neue", 0, 18)); // NOI18N
        totalExpenses.setForeground(new Color(0, 142, 155));

        GroupLayout totalPanelLayout = new GroupLayout(totalPanel);
        totalPanel.setLayout(totalPanelLayout);
        totalPanelLayout.setHorizontalGroup(
                totalPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(totalPanelLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(totalLabel)
                                .addContainerGap(16, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, totalPanelLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(totalExpenses)
                                .addGap(26, 26, 26))
        );
        totalPanelLayout.setVerticalGroup(
                totalPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(totalPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(totalLabel)
                                .addGap(51, 51, 51)
                                .addComponent(totalExpenses)
                                .addContainerGap(73, Short.MAX_VALUE))
        );

        countPanel.setBackground(new Color(255, 255, 255));

        countLabel.setText("Expenses count");

        expensesCount.setFont(new Font("Helvetica Neue", 0, 18)); // NOI18N
        expensesCount.setForeground(new Color(0, 142, 155));

        GroupLayout countPanelLayout = new GroupLayout(countPanel);
        countPanel.setLayout(countPanelLayout);
        countPanelLayout.setHorizontalGroup(
                countPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(countPanelLayout.createSequentialGroup()
                                .addGroup(countPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(countPanelLayout.createSequentialGroup()
                                                .addGap(13, 13, 13)
                                                .addComponent(countLabel))
                                        .addGroup(countPanelLayout.createSequentialGroup()
                                                .addGap(50, 50, 50)
                                                .addComponent(expensesCount)))
                                .addContainerGap(13, Short.MAX_VALUE))
        );
        countPanelLayout.setVerticalGroup(
                countPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(countPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(countLabel)
                                .addGap(49, 49, 49)
                                .addComponent(expensesCount)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ScrollPane.setBorder(null);

        expensesTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null, null, null, null, null, null, null},
                },
                new String [] {
                        "Category", "Sum", "Currency", "Rate", "ILS Total", "Description", "Date"
                }
        ));
        ScrollPane.setViewportView(expensesTable);

        GroupLayout containerLayout = new GroupLayout(container);
        container.setLayout(containerLayout);
        containerLayout.setHorizontalGroup(
                containerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(containerLayout.createSequentialGroup()
                                .addGroup(containerLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(containerLayout.createSequentialGroup()
                                                .addComponent(totalPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(countPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(formPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                                .addComponent(ScrollPane, GroupLayout.PREFERRED_SIZE, 608, GroupLayout.PREFERRED_SIZE))
        );
        containerLayout.setVerticalGroup(
                containerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(containerLayout.createSequentialGroup()
                                .addComponent(formPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)
                                .addGroup(containerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(totalPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(countPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(ScrollPane)
        );

        rights.setFont(new Font("Helvetica Neue", 0, 10)); // NOI18N
        rights.setText("©Ebraheem Elnakeeb & Gilad Schneider. 2023 all rights reserved");

        GroupLayout sectionLayout = new GroupLayout(section);
        section.setLayout(sectionLayout);
        sectionLayout.setHorizontalGroup(
                sectionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(sectionLayout.createSequentialGroup()
                                .addGroup(sectionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(sectionLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(rights))
                                        .addGroup(sectionLayout.createSequentialGroup()
                                                .addGap(30, 30, 30)
                                                .addComponent(container, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(30, Short.MAX_VALUE))
        );
        sectionLayout.setVerticalGroup(
                sectionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, sectionLayout.createSequentialGroup()
                                .addContainerGap(40, Short.MAX_VALUE)
                                .addComponent(container, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(rights, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

    }

    private void setCategoryPage() {

        section.removeAll();
        container.removeAll();

        JPanel form = new JPanel();
        JLabel formLabel = new JLabel();
        JLabel categoriesIcon = new JLabel();
        JScrollPane scrollPane = new JScrollPane();
        JPanel expensivePanel = new JPanel();
        JLabel expensiveLabel = new JLabel();
        JPanel commonPanel = new JPanel();
        JLabel commonLabel = new JLabel();
        JLabel rights = new JLabel();

        expensiveCategory = new JLabel();
        commonCategory = new JLabel();
        categoryTable = new JTable();


        container.setBackground(new Color(238,238,238));
        form.setBackground(new Color(255, 255, 255));

        formLabel.setFont(new Font("Helvetica Neue", 0, 24));
        formLabel.setForeground(new Color(0, 142, 155));
        formLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formLabel.setText("Add category");

        categoriesIcon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/il/ac/shenkar/includes/images/categories.png")))); // NOI18N

        categoryField.setText("category");


        categorySubmitBtn.setFont(new Font("Helvetica Neue", 0, 18));
        categorySubmitBtn.setText("Add");

        GroupLayout formLayout = new GroupLayout(form);
        form.setLayout(formLayout);
        formLayout.setHorizontalGroup(
                formLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(formLayout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(categoriesIcon)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(categoryField, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(65, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, formLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, formLayout.createSequentialGroup()
                                                .addComponent(formLabel)
                                                .addGap(126, 126, 126))
                                        .addGroup(GroupLayout.Alignment.TRAILING, formLayout.createSequentialGroup()
                                                .addComponent(categorySubmitBtn, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                                .addGap(133, 133, 133))))
        );
        formLayout.setVerticalGroup(
                formLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(formLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(formLabel)
                                .addGap(53, 53, 53)
                                .addGroup(formLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(categoryField, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(categoriesIcon))
                                .addGap(18, 18, 18)
                                .addComponent(categorySubmitBtn, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(50, Short.MAX_VALUE))
        );

        categoryTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null}
                },
                new String [] {
                        "ID", "Category"
                }
        ));
        scrollPane.setViewportView(categoryTable);

        expensivePanel.setBackground(new Color(255, 255, 255));
        expensiveLabel.setFont(new Font("Helvetica Neue", 0, 13));
        expensiveLabel.setText("Most expensive category");

        expensiveCategory.setFont(new Font("Helvetica Neue", 0, 14)); // NOI18N
        expensiveCategory.setForeground(new Color(0, 142, 155));
        expensiveCategory.setText("fuel");

        GroupLayout expensivePanelLayout = new GroupLayout(expensivePanel);
        expensivePanel.setLayout(expensivePanelLayout);
        expensivePanelLayout.setHorizontalGroup(
                expensivePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(expensivePanelLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(expensiveLabel, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, expensivePanelLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(expensiveCategory)
                                .addGap(73, 73, 73))
        );
        expensivePanelLayout.setVerticalGroup(
                expensivePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(expensivePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(expensiveLabel)
                                .addGap(30, 30, 30)
                                .addComponent(expensiveCategory)
                                .addContainerGap(50, Short.MAX_VALUE))
        );

        commonPanel.setBackground(new Color(255, 255, 255));

        commonLabel.setText("Common category");
        commonLabel.setFont(new Font("Helvetica Neue", 0, 13));

        commonCategory.setFont(new Font("Helvetica Neue", 0, 14)); // NOI18N
        commonCategory.setForeground(new Color(0, 142, 155));
        commonCategory.setText("");

        GroupLayout commandPanelLayout = new GroupLayout(commonPanel);
        commonPanel.setLayout(commandPanelLayout);
        commandPanelLayout.setHorizontalGroup(
                commandPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(commandPanelLayout.createSequentialGroup()
                                .addContainerGap(34, Short.MAX_VALUE)
                                .addComponent(commonLabel)
                                .addGap(35, 35, 35))
                        .addGroup(commandPanelLayout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(commonCategory)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        commandPanelLayout.setVerticalGroup(
                commandPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(commandPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(commonLabel)
                                .addGap(31, 31, 31)
                                .addComponent(commonCategory)
                                .addContainerGap(47, Short.MAX_VALUE))
        );

        GroupLayout containerLayout = new GroupLayout(container);
        container.setLayout(containerLayout);
        containerLayout.setHorizontalGroup(
                containerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(containerLayout.createSequentialGroup()
                                .addGroup(containerLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(form, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(containerLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(expensivePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(commonPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE))
        );
        containerLayout.setVerticalGroup(
                containerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(containerLayout.createSequentialGroup()
                                .addComponent(form, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addGroup(containerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(commonPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(expensivePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 158, Short.MAX_VALUE))
                        .addComponent(scrollPane)
        );

        rights.setFont(new Font("Helvetica Neue", 0, 10)); // NOI18N
        rights.setText("©Ebraheem Elnakeeb & Gilad Schneider. 2023 all rights reserved");

        GroupLayout sectionLayout = new GroupLayout(section);
        section.setLayout(sectionLayout);
        sectionLayout.setHorizontalGroup(
                sectionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(sectionLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(rights)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(sectionLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(container, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(30, 30, 30))
        );
        sectionLayout.setVerticalGroup(
                sectionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, sectionLayout.createSequentialGroup()
                                .addContainerGap(40, Short.MAX_VALUE)
                                .addComponent(container, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(rights, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

    }

    private void setExpensePage() {

        section.removeAll();
        container.removeAll();
        JPanel expenseForm = new JPanel();
        JLabel expenseLabel = new JLabel();
        JLabel categoryLabel = new JLabel();
        JLabel currencyCodeLabel = new JLabel();
        JLabel sumLabel = new JLabel();
        JLabel descriptionLabel = new JLabel();
        JLabel rights = new JLabel();

        container.setBackground(new Color(255, 255, 255));

        expenseForm.setBackground(new Color(255, 255, 255));

        expenseLabel.setFont(new Font("Helvetica Neue", 0, 18)); // NOI18N
        expenseLabel.setForeground(new Color(0, 142, 155));
        expenseLabel.setText("Add a new expense record");

        categoryLabel.setForeground(new Color(0, 142, 155));
        categoryLabel.setText("Category");

        categoryComboBox.setModel(new DefaultComboBoxModel<>());

        currencyCodeLabel.setForeground(new Color(0, 142, 155));
        currencyCodeLabel.setText("Currency code");

        currencyCodeComboBox.setModel(new DefaultComboBoxModel<>());


        sumLabel.setForeground(new Color(0, 142, 155));
        sumLabel.setText("Expense sum");

        descriptionField.setText("Description here");


        expenseSubmitBtn.setFont(new Font("Helvetica Neue", 0, 18)); // NOI18N
        expenseSubmitBtn.setText("Add record");

        descriptionLabel.setForeground(new Color(0, 142, 155));
        descriptionLabel.setText("Description");

        GroupLayout expesnseFormLayout = new GroupLayout(expenseForm);
        expenseForm.setLayout(expesnseFormLayout);
        expesnseFormLayout.setHorizontalGroup(
                expesnseFormLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, expesnseFormLayout.createSequentialGroup()
                                .addGap(0, 285, Short.MAX_VALUE)
                                .addComponent(expenseLabel)
                                .addGap(285, 285, 285))
                        .addGroup(expesnseFormLayout.createSequentialGroup()
                                .addGroup(expesnseFormLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(expesnseFormLayout.createSequentialGroup()
                                                .addGap(245, 245, 245)
                                                .addGroup(expesnseFormLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(currencyCodeComboBox, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(categoryLabel)
                                                        .addComponent(categoryComboBox, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(expenseSumField, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(descriptionField, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(currencyCodeLabel)
                                                        .addComponent(sumLabel)
                                                        .addComponent(descriptionLabel)))
                                        .addGroup(expesnseFormLayout.createSequentialGroup()
                                                .addGap(322, 322, 322)
                                                .addComponent(expenseSubmitBtn)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        expesnseFormLayout.setVerticalGroup(
                expesnseFormLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(expesnseFormLayout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(expenseLabel)
                                .addGap(43, 43, 43)
                                .addComponent(categoryLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(categoryComboBox, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(currencyCodeLabel, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(currencyCodeComboBox, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(sumLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(expenseSumField, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(descriptionLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(descriptionField, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(expenseSubmitBtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(67, Short.MAX_VALUE))
        );

        GroupLayout containerLayout = new GroupLayout(container);
        container.setLayout(containerLayout);
        containerLayout.setHorizontalGroup(
                containerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(containerLayout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(expenseForm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(56, Short.MAX_VALUE))
        );
        containerLayout.setVerticalGroup(
                containerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(containerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(expenseForm, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        rights.setFont(new Font("Helvetica Neue", 0, 10)); // NOI18N
        rights.setText("©Ebraheem Elnakeeb & Gilad Schneider. 2023 all rights reserved");

        GroupLayout sectionLayout = new GroupLayout(section);
        section.setLayout(sectionLayout);
        sectionLayout.setHorizontalGroup(
                sectionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(sectionLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(rights)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(sectionLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(container, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(30, 30, 30))
        );
        sectionLayout.setVerticalGroup(
                sectionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, sectionLayout.createSequentialGroup()
                                .addContainerGap(40, Short.MAX_VALUE)
                                .addComponent(container, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(rights, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

    }
}
