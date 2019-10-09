import com.sun.deploy.util.IconEncoder;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * I (Ryan) assume that ProductDetails will be used as a middle man between the user and the database, where you pass
 * in the item you want to update, and ProductDetails will change the information necessary.
 * I viewed this as a singleton/static class that would be accessed anywhere in the project to update the item anywhere necessary
 */
public class ProductDetails
{
    /**
     * this is the connection to the database that will be used throughout the class
     */
    Connection connection;

    ProductDetails()
    {
        // https://docs.oracle.com/javase/tutorial/jdbc/basics/
        // sql database stuff
        try
        {
            connection = DriverManager.getConnection("stuff for database", "username", "password");
        } catch (Exception e)
        {
            System.out.println(e);
        }

    }

    /**
     * checks the database for this item, and changes any parameters that have changed on it
     */
    void UpdateItem(DisplayItem i)
    {
        try
        {
            //reachout to database using this sql statment, not sure how its done atm
            String getObjectSQL = "SELECT * WHERE id EQUALS " + i.id;
            PreparedStatement p = connection.prepareStatement(getObjectSQL);
            ResultSet result = p.executeQuery();

            String itemReceivedFromSQL;
            while (result.next())
            {
                //if done correctly, this loop should only go through once,
                // and s should contain the info recived from the query
                itemReceivedFromSQL = result.getString(1);

                String updateSQL;
                if (itemReceivedFromSQL.param1 != i.param1)
                    updateSQL = "UPDATE DisplayItem SET param1 = " + i.param1 + " WHERE id EQUALS " + i.id;
                if (itemReceivedFromSQL.param2 != i.param2)
                    updateSQL = "UPDATE DisplayItem SET param2 = " + i.param2 + " WHERE id EQUALS " + i.id;
                //... so on
                // these updates can probably be done in a more effecient way, but this is the general idea
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * assuming every DisplayItem has their own tag list, this will return it, in uses like tag searching
     */
    ArrayList<String> ShowItemTags(DisplayItem i) throws SQLException
    {
        //connect to database with this sql statement
        String getObjectSQL = "SELECT * WHERE id EQUALS " + i.id;
        PreparedStatement p = connection.prepareStatement(getObjectSQL);
        ResultSet result = p.executeQuery();

        String tagsFromSQL;
        //should only execute once
        tagsFromSQL = result.getString(1);

        return new ArrayList<String>(Arrays.asList(tagsFromSQL.split("whatever the split would be")));
    }
}
