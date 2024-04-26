package il.ac.shenkar.view.page;

import il.ac.shenkar.includes.Category;
import il.ac.shenkar.includes.Currency;
import il.ac.shenkar.view.page.component.ComponentException;
import il.ac.shenkar.view.page.component.Header;
import il.ac.shenkar.view.page.component.Nav;
import il.ac.shenkar.view.page.component.Section;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AppPage implements IPage {

    // Variables declaration
    private JPanel mainPanel;
    private Nav nav;
    private Header header;
    private Section section;

    private ArrayList<JButton> buttons;
    private List<JTextField> textFields;
    // End of variables declaration


    public AppPage() {
        mainPanel = new JPanel();
        nav = new Nav();
        header = new Header();
        section = new Section();
        buttons = new ArrayList<>(List.of(nav.getButtons()));
        buttons.addAll(List.of(section.getButtons()));
        textFields = List.of(section.getFields());
        initComponents();
    }

    private void initComponents() {

        JPanel bodyPanel = new JPanel();
        GroupLayout bodyPanelLayout = new GroupLayout(bodyPanel);
        bodyPanel.setLayout(bodyPanelLayout);
        bodyPanelLayout.setHorizontalGroup(
                bodyPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, bodyPanelLayout.createSequentialGroup()
                                .addComponent(nav.getComponent(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(section.getComponent(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bodyPanelLayout.setVerticalGroup(
                bodyPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(bodyPanelLayout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addGroup(bodyPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(nav.getComponent(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(section.getComponent(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(header.getComponent(), GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bodyPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(header.getComponent(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(bodyPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public JButton[] getButtons() {
        JButton[] buttonArray = new JButton[buttons.size()];
        return buttons.toArray(buttonArray);
    }

    @Override
    public JTextField[] getFields() {
        JTextField[] textFieldArray = new JTextField[textFields.size()];
        return textFields.toArray(textFieldArray);
    }

    @Override
    public JComboBox<Integer>[] getIntComboBoxes() {
        List<JComboBox<Integer>> comboBoxList =  section.getIntComboBoxes();
        return comboBoxList.toArray(new JComboBox[comboBoxList.size()]);
    }

    @Override
    public JComboBox<Category>[] getCategoryComboBoxes() {
        List<JComboBox<Category>> comboBoxList =  section.getCategoryComboBoxes();
        return comboBoxList.toArray(new JComboBox[comboBoxList.size()]);
    }

    @Override
    public JComboBox<Currency>[] getCurrencyComboBoxes() {
        List<JComboBox<Currency>> comboBoxList =  section.getCurrencyComboBoxes();
        return comboBoxList.toArray(new JComboBox[comboBoxList.size()]);
    }


    @Override
    public JSpinner[] getSpinners() {
        return section.getSpinners();
    }

    @Override
    public void setData(Object data, Integer actionId) throws ComponentException {
        if (data != null && data.getClass() == JButton.class) {
            nav.setData(data, 0);
        }
        else if (actionId == 0) {
            section.setData(null, 0);
        }
        else if (actionId == 1) {
            assert data instanceof String;
            header.setUserName((String) data);
        }
        else if (actionId == 2) {
            section.setData(null, 2);
        }
        else if (actionId == 5) {
            section.setData(null, 5);
        }
        else if (actionId == 6) {
            section.setData(data, 6);
        }
        else if (actionId == 8) {
            section.setData(data, 8);
        }
        else if (actionId == 10) {
            section.setData(null, 10);
        }
        else if (actionId == 11) {
            section.setData(data, 11);
        }
        else if(data instanceof List<?> dataList) {
            if (!dataList.isEmpty() && dataList.get(0) instanceof Category) {
                if (actionId == 7) {
                    section.setData(dataList, 7);
                } else {
                    section.setData(dataList, 1);
                }

            }
        }
    }
}
