package il.ac.shenkar.view.page;

import il.ac.shenkar.includes.Category;
import il.ac.shenkar.includes.Currency;
import il.ac.shenkar.view.page.component.ComponentException;

import javax.swing.*;

public interface IPage {

    public JPanel getMainPanel();
    public JButton[] getButtons();
    public JTextField[] getFields();
    public JComboBox<Integer>[] getIntComboBoxes();
    public JComboBox<Category>[] getCategoryComboBoxes();
    public JComboBox<Currency>[] getCurrencyComboBoxes();
    public JSpinner[] getSpinners();

    public void setData(Object data, Integer actionId) throws ComponentException;

}
