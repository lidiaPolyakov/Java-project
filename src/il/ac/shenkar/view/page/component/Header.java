package il.ac.shenkar.view.page.component;

import il.ac.shenkar.includes.Category;
import il.ac.shenkar.includes.Currency;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Header implements IComponent{

    // Variables declaration
    private JPanel header;
    JLabel userName;
    // End of variables declaration

    public Header() {
        initComponent();
    }

    private void initComponent() {
        header = new JPanel();
        JPanel jPanel3 = new JPanel();
        JLabel time = new JLabel();
        userName = new JLabel();
        JPanel logo = new JPanel();
        JLabel jLabel4 = new JLabel();
        JLabel jLabel5 = new JLabel();
        JLabel userProfile = new JLabel();

        header.setBackground(new Color(0, 142, 155));

        jPanel3.setBackground(new Color(0, 142, 155));

        time.setFont(new Font("Helvetica Neue", 0, 18)); // NOI18N
        time.setForeground(new Color(255, 255, 255));
        time.setText(getTimeOfDay());

        userName.setFont(new Font("Helvetica Neue", 0, 18)); // NOI18N
        userName.setForeground(new Color(255, 255, 255));
        userName.setText("");

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(time)
                        .addComponent(userName)
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(time)
                                .addGap(2, 2, 2)
                                .addComponent(userName))
        );

        logo.setBackground(new Color(0, 142, 155));

        jLabel4.setFont(new Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel4.setForeground(new Color(255, 255, 255));
        jLabel4.setText("Cost");

        jLabel5.setFont(new Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel5.setForeground(new Color(255, 255, 255));
        jLabel5.setText("Manager");

        GroupLayout logoLayout = new GroupLayout(logo);
        logo.setLayout(logoLayout);
        logoLayout.setHorizontalGroup(
                logoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(logoLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(logoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel5))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        logoLayout.setVerticalGroup(
                logoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(logoLayout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(jLabel4)
                                .addGap(0, 0, 0)
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        userProfile.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/il/ac/shenkar/includes/images/heisenberg.png")))); // NOI18N

        GroupLayout headerLayout = new GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
                headerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(logo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(userProfile))
        );
        headerLayout.setVerticalGroup(
                headerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(headerLayout.createSequentialGroup()
                                .addGroup(headerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(headerLayout.createSequentialGroup()
                                                .addGap(14, 14, 14)
                                                .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(headerLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(logo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(10, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(userProfile))
        );

    }

    @Override
    public JPanel getComponent() {
        return this.header;
    }

    @Override
    public JButton[] getButtons() {
        return null;
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


    public void setUserName(String userName) {
        this.userName.setText(userName);
    }

    @Override
    public void setData(Object data, Integer actionID) throws ComponentException {
        if(actionID == 0)
            this.setUserName((String)data);
        else
            throw new ComponentException("Header - unknown action ID in setData method");
    }

    /**
     * Returns the time of day as a string based on the current time.
     * @return a string representing the time of day
     */
    private String getTimeOfDay() {
        LocalTime time = LocalTime.now();

        if (time.isBefore(LocalTime.NOON) && time.isAfter(LocalTime.of(5, 0))) {
            return "Morning";
        } else if (time.isBefore(LocalTime.of(15, 0)) && time.isAfter(LocalTime.NOON)) {
            return "Noon";
        } else if (time.isBefore(LocalTime.of(17, 0)) && time.isAfter(LocalTime.of(15, 0))) {
            return "Afternoon";
        } else if (time.isBefore(LocalTime.of(22, 0)) && time.isAfter(LocalTime.of(17, 0))) {
            return "Evening";
        }
        return "Night";
    }
}
