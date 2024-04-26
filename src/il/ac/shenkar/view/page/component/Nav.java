package il.ac.shenkar.view.page.component;

import il.ac.shenkar.includes.Category;
import il.ac.shenkar.includes.Currency;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Nav implements IComponent{

    // Variables declaration
    JPanel nav;
    JButton expensePageBtn;
    JButton categoryPageBtn;
    JButton reportPageBtn;
    JButton logOutBtn;
    JButton selected;
    // End of variables declaration

    public Nav() {
        initComponent();
    }

    private void initComponent() {
        nav = new JPanel();
        expensePageBtn = new JButton();
        categoryPageBtn = new JButton();
        reportPageBtn = new JButton();
        logOutBtn = new JButton();
        selected = new JButton();


        nav.setBackground(new Color(255, 255, 255));

        selected.setForeground(new Color(255, 255, 255));
        selected.setBackground(new Color(0, 142, 155));

        expensePageBtn.setFont(new Font("Helvetica Neue", 0, 14));
        expensePageBtn.setBackground(new Color(255, 255, 255));
        expensePageBtn.setForeground(new Color(0, 142, 155));
        expensePageBtn.setText("Add expense");
        expensePageBtn.setBorder(BorderFactory.createLineBorder(new Color(0, 142, 155), 2));


        categoryPageBtn.setFont(new Font("Helvetica Neue", 0, 14));
        categoryPageBtn.setBackground(new Color(255, 255, 255));
        categoryPageBtn.setForeground(new Color(0, 142, 155));
        categoryPageBtn.setText("Add category");
        categoryPageBtn.setBorder(BorderFactory.createLineBorder(new Color(0, 142, 155), 2));


        reportPageBtn.setFont(new Font("Helvetica Neue", 0, 14));
        reportPageBtn.setBackground(new Color(255, 255, 255));
        reportPageBtn.setForeground(new Color(0, 142, 155));
        reportPageBtn.setText("View report");
        reportPageBtn.setBorder(BorderFactory.createLineBorder(new Color(0, 142, 155), 2));

        logOutBtn.setText("Log out");

        GroupLayout navLayout = new GroupLayout(nav);
        nav.setLayout(navLayout);
        navLayout.setHorizontalGroup(
                navLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(navLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(logOutBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addComponent(categoryPageBtn, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                        .addComponent(expensePageBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(reportPageBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        navLayout.setVerticalGroup(
                navLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, navLayout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(expensePageBtn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(categoryPageBtn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(reportPageBtn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(logOutBtn, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41))
        );

    }

    @Override
    public JPanel getComponent() {
        return nav;
    }

    @Override
    public void setData(Object data, Integer actionID) throws ComponentException {
        if(actionID == 0)
            this.switchSelectedBtn((JButton)data);
        else
            throw new ComponentException("Nav - unknown action ID in setData method");

    }

    public JButton getExpenseFormBtn() {
        return expensePageBtn;
    }

    public JButton getCategoryFormBtn() {
        return categoryPageBtn;
    }

    public JButton getReportBtn() {
        return reportPageBtn;
    }

    public JButton getLogOutBtn() {
        return logOutBtn;
    }

    public JButton[] getButtons() {
        return new JButton[]{getExpenseFormBtn(), getCategoryFormBtn(), getReportBtn(), getLogOutBtn()};
    }

    @Override
    public JTextField[] getFields() {
        return null;
    }

    @Override
    public List<JComboBox<Integer>> getIntComboBoxes() {
        return null;
    }

    @Override
    public List<JComboBox<Category>> getCategoryComboBoxes() {
        return null;
    }

    @Override
    public List<JComboBox<Currency>> getCurrencyComboBoxes() {
        return null;
    }


    @Override
    public JSpinner[] getSpinners() {
        return null;
    }

    void switchSelectedBtn(JButton selected) {

        if(!this.selected.equals(selected)) {
            Color tempBackground =  new Color(this.selected.getBackground().getRGB());
            Color tempForeground = new Color(this.selected.getForeground().getRGB());

            this.selected.setBackground(selected.getBackground());
            this.selected.setForeground(selected.getForeground());

            selected.setBackground(tempBackground);
            selected.setForeground(tempForeground);

            selected.setOpaque(true);
            this.selected.setOpaque(true);
        }
//        else {
//            if (selected.getForeground().equals(new Color(255, 255, 255))){
//                selected.setBackground(new Color(255, 255, 255));
//                selected.setForeground(new Color(0, 142, 155));
//            }
//            else {
//                selected.setBackground(new Color(0, 142, 155));
//                selected.setForeground(new Color(255, 255, 255));
//            }
//            selected.setOpaque(true);
//        }
        this.selected = selected;
    }
}
