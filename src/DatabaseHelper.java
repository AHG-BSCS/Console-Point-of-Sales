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

    public Item getItem(int itemId) {
        // Instantiate here to be access by finally statement
        Item item = new Item();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM item WHERE item_pk = " + itemId + " LIMIT 1";
        
        try {
            connection = DriverManager.getConnection(url);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                item.setItemPk(resultSet.getInt("item_pk"));
                item.setProduct(resultSet.getString("product"));
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
                if (connection != null) connection.close();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return item;
    }

    public ArrayList<Item> getItems(int classification) {
        ArrayList<Item> items = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM item WHERE classification_id = " + classification;
        
        try {
            connection = DriverManager.getConnection(url);
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Item item = new Item();
                item.setItemPk(resultSet.getInt("item_pk"));
                item.setProduct(resultSet.getString("product"));
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
                if (connection != null) connection.close();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return items;
    }

    public int saveTransaction(ArrayList<Item> items, float cash, float totalPrice) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
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
                preparedStatement = connection.prepareStatement(transactionItemSql);

                for (Item item : items) {
                    preparedStatement.setInt(1, generatedKey);
                    preparedStatement.setInt(2, item.getItemPk());
                    preparedStatement.setInt(3, item.getQuantity());
                    preparedStatement.setFloat(4, item.getPrice());
                    preparedStatement.setFloat(5, (item.getPrice() * item.getQuantity()));
                    preparedStatement.executeUpdate();
                }
            }
            else
                System.out.println("Something went wrong in the database!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                // Close to prevent memory leaks
                if (connection != null) connection.close();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return generatedKey;
    }
}
