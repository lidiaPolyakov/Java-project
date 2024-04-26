package il.ac.shenkar.view.page.component;

import il.ac.shenkar.includes.Category;
import il.ac.shenkar.includes.Currency;

import javax.swing.*;
import java.util.List;

public interface IComponent {

    public JPanel getComponent();

    public JButton[] getButtons();
    public JTextField[] getFields();
    public List<JComboBox<Integer>> getIntComboBoxes();
    public List<JComboBox<Category>> getCategoryComboBoxes();
    public List<JComboBox<Currency>> getCurrencyComboBoxes();

    public JSpinner[] getSpinners();
    public void setData(Object data, Integer actionID) throws ComponentException;

}
