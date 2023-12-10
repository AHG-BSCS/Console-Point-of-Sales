import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DatabaseHelper {
    private final static String url = "jdbc:sqlite:data/AHG-BSCS-POS-System.db";
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public Item getItem(int itemId) {
        String sql = "SELECT * FROM item WHERE item_pk = " + itemId + " LIMIT 1";
        Item item = new Item();
        
        try {
            connection = DriverManager.getConnection(url);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                item.setItemId(resultSet.getInt("item_pk"));
                item.setProductName(resultSet.getString("product"));
                item.setPrice(resultSet.getFloat("price"));
                item.setStock(resultSet.getInt("stock"));
                item.setClassification(resultSet.getInt("classification_id"));
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                // Close to prevent memory leaks
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return item;
    }

    public ArrayList<Item> getItems(String keyword) {
        String sql = "SELECT * FROM item WHERE product LIKE ?";
        ArrayList<Item> items = new ArrayList<>();
        
        try {
            connection = DriverManager.getConnection(url);
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, "%" + keyword + "%");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Item item = new Item();
                item.setItemId(resultSet.getInt("item_pk"));
                item.setProductName(resultSet.getString("product"));
                item.setPrice(resultSet.getFloat("price"));
                item.setStock(resultSet.getInt("stock"));
                item.setClassification(resultSet.getInt("classification_id"));
                items.add(item);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                // Close to prevent memory leaks
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return items;
    }

    public ArrayList<Item> getItems(int classification) {
        ArrayList<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM item WHERE classification_id = " + classification;
        
        try {
            connection = DriverManager.getConnection(url);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Item item = new Item();
                item.setItemId(resultSet.getInt("item_pk"));
                item.setProductName(resultSet.getString("product"));
                item.setPrice(resultSet.getFloat("price"));
                item.setStock(resultSet.getInt("stock"));
                item.setClassification(resultSet.getInt("classification_id"));
                items.add(item);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                // Close to prevent memory leaks
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return items;
    }

    public int saveTransaction(ArrayList<Item> items, float cash, float totalPrice) {
        int generatedKey = 0;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm:ss a");
        String transactionSql = "INSERT INTO transactions (employee_id, branch_id, date_time, total_price, cash, change) " +
                    "VALUES(?, ?, ?, ?, ?, ?)";
        String transactionItemSql = "INSERT INTO transaction_item (transaction_id, item_id, quantity, item_price, item_total_price) " +
                    "VALUES(?, ?, ?, ?, ?)";
        
        try {
            connection = DriverManager.getConnection(url);
            preparedStatement = connection.prepareStatement(transactionSql);

            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, 1);
            preparedStatement.setString(3, LocalDateTime.now().format(dateTimeFormatter));
            preparedStatement.setFloat(4, totalPrice);
            preparedStatement.setFloat(5, cash);
            preparedStatement.setFloat(6, (cash - totalPrice));

            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("SELECT last_insert_rowid()");
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                generatedKey = resultSet.getInt(1);

                for (Item item : items) {
                    preparedStatement = connection.prepareStatement(transactionItemSql);
                    preparedStatement.setInt(1, generatedKey);
                    preparedStatement.setInt(2, item.getItemId());
                    preparedStatement.setInt(3, item.getQuantity());
                    preparedStatement.setFloat(4, item.getPrice());
                    preparedStatement.setFloat(5, (item.getPrice() * item.getQuantity()));
                    preparedStatement.executeUpdate();

                    preparedStatement = connection.prepareStatement("UPDATE item SET stock = ? WHERE item_pk = ?");
                    preparedStatement.setInt(1, (item.getStock() - item.getQuantity()));
                    preparedStatement.setInt(2, item.getItemId());
                    preparedStatement.executeUpdate();
                }
            }
            else
                System.out.println(Terminal.MAGENTA + "Something went wrong in the database!" + Terminal.DEFAULT);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                // Close to prevent memory leaks
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return generatedKey;
    }

    public ArrayList<Transaction> getTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        
        try {
            connection = DriverManager.getConnection(url);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionPk(resultSet.getInt("transaction_pk"));
                transaction.setEmployeeId(resultSet.getInt("employee_id"));
                transaction.setBranchId(resultSet.getInt("branch_id"));
                transaction.setDateTime(resultSet.getString("date_time"));
                transaction.setTotalPrice(resultSet.getFloat("total_price"));
                transaction.setCash(resultSet.getFloat("cash"));
                transaction.setChange(resultSet.getFloat("change"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                // Close to prevent memory leaks
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return transactions;
    }

    public ArrayList<TransactionItem> getTransactionItem(int transactionId) {
        ArrayList<TransactionItem> transactionItems = new ArrayList<>();
        String sql = "SELECT * FROM transaction_item WHERE transaction_id = " + transactionId;
        
        try {
            connection = DriverManager.getConnection(url);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                TransactionItem transactionItem = new TransactionItem();
                transactionItem.setTransactionId(resultSet.getInt("transaction_id"));
                transactionItem.setItemId(resultSet.getInt("item_id"));
                transactionItem.setQuantity(resultSet.getInt("quantity"));
                transactionItem.setPrice(resultSet.getFloat("item_price"));
                transactionItem.setItemTotalPrice(resultSet.getFloat("item_total_price"));
                transactionItems.add(transactionItem);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                // Close to prevent memory leaks
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return transactionItems;
    }

    public int countTableRow(String tableName) {
        String sql = "SELECT COUNT (*) FROM " + tableName;
        int count = 0;
        
        try {
            connection = DriverManager.getConnection(url);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getByte(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                // Close to prevent memory leaks
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return count;
    }

    public double getGrossAmount() {
        String sql = "SELECT total_price FROM transactions";
        double grossAmount = 0;
        
        try {
            connection = DriverManager.getConnection(url);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                grossAmount += resultSet.getFloat("total_price");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                // Close to prevent memory leaks
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return grossAmount;
    }
}
