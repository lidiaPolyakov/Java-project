package il.ac.shenkar.view.page;

import il.ac.shenkar.includes.Category;
import il.ac.shenkar.includes.Currency;
import il.ac.shenkar.view.page.component.ComponentException;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoginPage implements IPage{

    // Variables declaration
    private JButton signUpBtn, signInBtn;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel mainPanel;
    // End of variables declaration


    public LoginPage() {
        initComponents();
    }


    public void initComponents() {

        mainPanel = new JPanel();
        JPanel jPanel2 = new JPanel();
        JLabel javaIcon = new JLabel();
        JLabel slogan = new JLabel();
        JPanel jPanel3 = new JPanel();
        JPanel jPanel4 = new JPanel();
        JLabel userIcon = new JLabel();
        usernameField = new JTextField();
        JSeparator jSeparator1 = new JSeparator();
        JPanel jPanel6 = new JPanel();
        JLabel passwordIcon = new JLabel();
        passwordField = new JPasswordField();
        JSeparator jSeparator3 = new JSeparator();
        JPanel jPanel5 = new JPanel();
        signUpBtn = new JButton();
        signInBtn = new JButton();
        JLabel rights = new JLabel();


        jPanel2.setBackground(new Color(0, 142, 155));

        javaIcon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/il/ac/shenkar/includes/images/java.png"))));

        slogan.setFont(new Font("Helvetica Neue", 0, 20)); // NOI18N
        slogan.setText("Cost Manager");
        slogan.setSize(new Dimension(136, 24));

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(javaIcon)
                                .addContainerGap(100, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(slogan)
                                .addGap(112, 112, 112))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addComponent(javaIcon)
                                .addGap(18, 18, 18)
                                .addComponent(slogan)
                                .addContainerGap(124, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new Color(255, 255, 255));

        jPanel4.setBackground(new Color(255, 255, 255));
        jPanel4.setForeground(new Color(0, 142, 155));
        jPanel4.setFont(new Font("Helvetica Neue", 0, 36)); // NOI18N

        userIcon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/il/ac/shenkar/includes/images/userIcon.png")))); // NOI18N

        usernameField.setFont(new Font("Helvetica Neue", 0, 20)); // NOI18N
        usernameField.setForeground(new Color(0, 142, 155));
        usernameField.setText("username");
        usernameField.setBorder(null);

        jSeparator1.setForeground(new Color(0, 142, 155));

        GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(userIcon)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
                                .addGap(135, 135, 135))
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                                .addGap(3, 3, 3)
                                                .addComponent(jSeparator1))
                                        .addComponent(userIcon, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new Color(255, 255, 255));

        passwordIcon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/il/ac/shenkar/includes/images/passwordIcon.png")))); // NOI18N

        passwordField.setFont(new Font("Helvetica Neue", 0, 20)); // NOI18N
        passwordField.setForeground(new Color(0, 142, 155));
        passwordField.setText("password");
        passwordField.setBorder(null);


        jSeparator3.setForeground(new Color(0, 142, 155));

        javax.swing.GroupLayout jPanel6Layout = new GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(passwordIcon)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jSeparator3)
                                        .addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(passwordIcon, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jSeparator3, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );

        jPanel5.setBackground(new Color(255, 255, 255));

        signUpBtn.setForeground(new Color(0, 142, 155));
        signUpBtn.setText("Sign up");
        signUpBtn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(0, 142, 155)), null), null));
        signUpBtn.setBounds(new Rectangle(0, 142, 155, 155));

        signInBtn.setForeground(new Color(0, 142, 155));
        signInBtn.setText("Sign in");
        signInBtn.setBorder(BorderFactory.createLineBorder(new Color(0, 142, 155)));


        GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(signInBtn, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                                .addComponent(signUpBtn, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(signUpBtn)
                                .addComponent(signInBtn))
        );

        rights.setFont(new Font("Helvetica Neue", 0, 10));
        rights.setText("©Ebraheem Elnakeeb & Gilad Schneider. 2023 all rights reserved");

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addComponent(jPanel5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addGap(72, 72, 72)
                                                                .addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addGap(48, 48, 48)
                                                                .addComponent(jPanel6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(rights)))
                                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jPanel6, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jPanel5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rights))
        );

        GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(0, 0, 0))
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }


    public JButton getSignUpBtn() {
        return signUpBtn;
    }

    public JButton getSignInBtn() {
        return signInBtn;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public JButton[] getButtons() {
        return new JButton[]{getSignUpBtn(), getSignInBtn()};
    }

    @Override
    public JTextField[] getFields() {
        return new JTextField[]{getUsernameField(), getPasswordField()};
    }

    @Override
    public JComboBox<Integer>[] getIntComboBoxes() {
        return new JComboBox[0];
    }

    @Override
    public JComboBox<Category>[] getCategoryComboBoxes() {
        return new JComboBox[0];
    }

    @Override
    public JComboBox<Currency>[] getCurrencyComboBoxes() {
        return new JComboBox[0];
    }


    @Override
    public JSpinner[] getSpinners() {
        return null;
    }

    @Override
    public void setData(Object data, Integer actionId) throws ComponentException {}
}
