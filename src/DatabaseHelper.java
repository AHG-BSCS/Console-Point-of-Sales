import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
