package il.ac.shenkar.model;

import il.ac.shenkar.includes.Category;
import il.ac.shenkar.includes.Currency;
import il.ac.shenkar.includes.Expense;
import il.ac.shenkar.includes.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Model implements IModel{
    private final String protocol = "jdbc:derby:CMDB;create=true";


    /* Constructor for the Model class. Initializes the database by creating necessary tables and inserting sample data.
     Throws a ModelException if an error occurs while creating or inserting data into the tables.
     Closes all database resources after initialization. */
    public Model() throws ClassNotFoundException, ModelException {
        /* These constants define the database driver and protocol used to connect to the database */
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        Class.forName(driver);
        initDB();
    }

    /*
        * Initializes the database by creating necessary tables and inserting sample data if tables do not exist.
        * Throws ModelException if an error occurs while creating or inserting data into the tables.
        * Closes all database resources after initialization.
     */
    private void initDB() throws ModelException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            /* Connect to the database using the specified protocol */
            conn = DriverManager.getConnection(protocol);
            /* Create a Statement object for executing SQL queries */
            stmt = conn.createStatement();
            /* check if the 'Users' table exists, create it and add some data if it doesn't exist */
            rs = stmt.executeQuery("SELECT COUNT(*) FROM SYS.SYSTABLES WHERE TABLENAME = 'USERS'");
            rs.next();
            int count = rs.getInt(1);
            if (count == 0) {
                String createUsersTable = """
                CREATE TABLE Users (
                  id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
                  name VARCHAR(45) UNIQUE,
                  password VARCHAR(45)
                )""";
                stmt.executeUpdate(createUsersTable);
                stmt.executeUpdate("INSERT INTO Users (name, password) VALUES ('John', 'password123')");
            }

            /* check if the 'Categories' table exists, create it and add some data if it doesn't exist */
            rs = stmt.executeQuery("SELECT COUNT(*) FROM SYS.SYSTABLES WHERE TABLENAME = 'CATEGORIES'");
            rs.next();
            count = rs.getInt(1);
            if (count == 0) {
                String createCategoriesTable = """
                    CREATE TABLE Categories (
                      id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
                      name VARCHAR(45) UNIQUE
                    )""";
                stmt.executeUpdate(createCategoriesTable);
                stmt.executeUpdate("INSERT INTO Categories (name) VALUES ('Food'), ('Housing'), ('Transportation'), ('Entertainment'), ('Healthcare')");
            }

            /* check if the 'Expenses' table exists, create it and add some data if it doesn't exist */
            rs = stmt.executeQuery("SELECT COUNT(*) FROM SYS.SYSTABLES WHERE TABLENAME = 'EXPENSES'");
            rs.next();
            count = rs.getInt(1);
            if (count == 0) {
                String createExpensesTable = """
                    CREATE TABLE Expenses (
                      id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
                      userID INT,
                      catID INT,
                      expensesSum INT,
                      currency VARCHAR(45),
                      currencyRate DOUBLE,
                      description VARCHAR(255),
                      createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      FOREIGN KEY (userID) REFERENCES Users (id),
                      FOREIGN KEY (catID) REFERENCES Categories (id)
                    )""";
                stmt.executeUpdate(createExpensesTable);
                stmt.executeUpdate("""
                            INSERT INTO Expenses (userID, catID, expensesSum, currency, currencyRate, description) VALUES
                                (1, 1, 10, 'USD', 1, 'Groceries for the week'),
                                (1, 2, 1000, 'USD', 1, 'Rent payment for March'),
                                (1, 3, 50, 'USD', 1, 'Gasoline refill'),
                                (1, 1, 20, 'USD', 1, 'Lunch with friends'),
                                (1, 5, 150, 'USD', 1, 'Dentist appointment'),
                                (1, 2, 500, 'USD', 1, 'Home maintenance'),
                                (1, 1, 30, 'USD', 1, 'Takeout for dinner'),
                                (1, 3, 100, 'USD', 1, 'Car repair')
                            """);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModelException("Error creating tables: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ModelException("Error closing database resources: " + e.getMessage());
            }
        }
    }

    /*
     * Creates a new user with the specified username and password in the database.
     throws ModelException if there is an error creating the user in the database
     */
    @Override
    public void createUser(String username, String password) throws ModelException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(protocol);
            stmt = conn.prepareStatement("INSERT INTO Users (name, password) VALUES (?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModelException("Error creating user: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ModelException("Error closing database resources: " + e.getMessage());
            }
        }
    }


    /*
      Finds a user with the specified username and password in the database.
      returns a User object representing the found user, or null if no user is found with the specified username and password
      throws ModelException if there is an error finding the user in the database
     */
    @Override
    public User findUser(String username, String password) throws ModelException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(protocol);
            stmt = conn.prepareStatement("SELECT id, name, password FROM Users WHERE name = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String hashedPassword = rs.getString("password");
                return new User(id, name, hashedPassword);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModelException("Error finding user: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ModelException("Error closing database resources: " + e.getMessage());
            }
        }
    }

    /* This method adds an expense to the database for a specific user with a given category, sum, currency, and description.
        It creates a connection to the database and executes an INSERT statement with the provided values. */
    @Override
    public void addExpense(Integer userID, Integer catID, Integer sum, Currency currency, String description) throws ModelException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(protocol);
            String sql = "INSERT INTO Expenses (userID, catID, expensesSum, currency, currencyRate, description) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);
            stmt.setInt(2, catID);
            stmt.setInt(3, sum);
            stmt.setString(4, currency.getCurrencyCode());
            stmt.setFloat(5, currency.getRate()); // currency rate is hardcoded to 1 for now
            stmt.setString(6, description);
            stmt.executeUpdate();
            System.out.println("Expense added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModelException("Error adding expense: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ModelException("Error closing database resources: " + e.getMessage());
            }
        }
    }

    /*  This method retrieves a list of expenses for a specific user from the database.
        It creates a connection to the database and executes a SELECT statement with a JOIN on the Categories table to retrieve the category name.
        It then loops through the ResultSet to create Expense objects and adds them to a list, which is returned at the end of the method. */
    @Override
    public List<Expense> getExpenses(Integer userID) throws ModelException {
        List<Expense> expenses = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(protocol);

            String sql = """
                SELECT exs.id, cat.name, exs.expensesSum, exs.currency, exs.currencyRate, exs.description, exs.createdAt
                FROM Expenses exs
                INNER JOIN Categories cat
                ON exs.catID = cat.id
                WHERE exs.userID = ?
                ORDER BY exs.createdAt DESC
                """;
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String cateName = rs.getString("name");
                int sum = rs.getInt("expensesSum");
                String currencyCode = rs.getString("currency");
                Float currencyRate = rs.getFloat("currencyRate");
                String description = rs.getString("description");
                String createdAt = rs.getString("createdAt");
                Expense expense = new Expense(id, userID, cateName, sum, currencyCode, currencyRate, description, createdAt);
                expenses.add(expense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModelException("Error retrieving expenses: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ModelException("Error closing database resources: " + e.getMessage());
            }
        }
        return expenses;
    }

    /*
    This method takes in three integers representing the day, month, and year of a date
    and formats them into a string in the format of "YYYY-MM-DD".
    The formatted date string is then returned.
    */
    private String formatDate(int day, int month, int year) {
        String formattedDay = String.format("%02d", day);
        String formattedMonth = String.format("%02d", month);
        return year + "-" + formattedMonth + "-" + formattedDay;
    }




    /*
        This method retrieves a list of expenses by date for a given user.
        The method throws a ModelException if there is an error retrieving the expenses.
    */
    @Override
    public List<Expense> getExpensesByDate(Integer userID, Integer day, Integer month, Integer year) throws ModelException {
        List<Expense> expenses = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(protocol);

            if (day == 0) {
                String sql = """
                    SELECT exs.id, cat.name, exs.expensesSum, exs.currency, exs.currencyRate, exs.description, exs.createdAt
                    FROM Expenses exs
                    INNER JOIN Categories cat
                    ON exs.catID = cat.id
                    WHERE exs.userID = ?
                    AND
                    YEAR(createdAt) = ?
                    AND
                    MONTH(createdAt) = ?
                    ORDER BY exs.createdAt DESC
                """;
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, userID);
                stmt.setInt(2, year);
                stmt.setInt(3, month);
            }
            else {
                String sql = """
                    SELECT exs.id, cat.name, exs.expensesSum, exs.currency, exs.currencyRate, exs.description, exs.createdAt
                    FROM Expenses exs
                    INNER JOIN Categories cat
                    ON exs.catID = cat.id
                    WHERE exs.userID = ?
                    AND
                    DATE(createdAt) = ?
                    ORDER BY exs.createdAt DESC
                """;
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, userID);
                stmt.setString(2, formatDate(day, month, year));
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String cateName = rs.getString("name");
                int sum = rs.getInt("expensesSum");
                String currencyCode = rs.getString("currency");
                Float currencyRate = rs.getFloat("currencyRate");
                String description = rs.getString("description");
                String createdAt = rs.getString("createdAt");
                Expense expense = new Expense(id, userID, cateName, sum, currencyCode, currencyRate, description, createdAt);
                expenses.add(expense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModelException("Error retrieving expenses: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ModelException("Error closing database resources: " + e.getMessage());
            }
        }
        return expenses;
    }



    /*  Method for adding a new category to the database */
    @Override
    public void addCategory(String category) throws ModelException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(protocol);
            String sql = "INSERT INTO Categories (name) VALUES (?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, category);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModelException("Error adding category: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ModelException("Error closing database resources: " + e.getMessage());
            }
        }
    }

    /* Method for retrieving all categories from the database */
    @Override
    public List<Category> getCategories() throws ModelException {
        List<Category> categories = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(protocol);
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT * FROM Categories");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                categories.add(new Category(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModelException("Error retrieving categories: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ModelException("Error closing database resources: " + e.getMessage());
            }
        }
        return categories;
    }

    /*
      Retrieves the most common category among expenses of a given user
      throws ModelException if there is an error retrieving the category
     */
    @Override
    public Category getCommonCategory(Integer userID) throws ModelException {
        Category category = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(protocol);
            pstmt = conn.prepareStatement("""
                    SELECT cat.id, cat.name
                    FROM Categories cat
                    INNER JOIN Expenses exs
                    ON cat.id = exs.catID
                    WHERE exs.userID = ?
                    GROUP BY cat.id, cat.name
                    ORDER BY COUNT(cat.id) DESC
            """);
            pstmt.setInt(1, userID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                category = new Category(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModelException("Error getting common category: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ModelException("Error closing database resources: " + e.getMessage());
            }
        }

        return category;
    }

    /*
        Retrieves the most expensive category among expenses of a given user
        returns the Category object representing the most expensive category, or null if there are no expenses
        throws ModelException if there is an error retrieving the category
     */
    @Override
    public Category getExpensiveCategory(Integer userID) throws ModelException {
        Category category = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(protocol);
            pstmt = conn.prepareStatement("""
                    SELECT cat.id, cat.name
                     FROM Categories cat
                     INNER JOIN Expenses exs
                     ON cat.id = exs.catID
                     WHERE exs.userID = ?
                     GROUP BY cat.id, cat.name
                     ORDER BY SUM(exs.expensesSum/exs.currencyRate) DESC
            """);
            pstmt.setInt(1, userID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                category = new Category(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ModelException("Error getting common category: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ModelException("Error closing database resources: " + e.getMessage());
            }
        }

        return category;
    }
}
