
package org.onebeartoe.development.tools.titanic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.onebeartoe.development.tools.titanic.statistics.TitanicPassenger;

/**
 *
 */
public class TitanicDataSource 
{
    private static final String connectionUrl = "jdbc:derby:derby;";
    
    public static void createDatabaseTable() throws SQLException 
    {             
        boolean createDb = true;
        Connection conn = getConnection(createDb);
        
        System.out.println("Connected to database");

        Statement statement = conn.createStatement();

//        String create = "CREATE TABLE TITANIC_PASSENGER_LIST_CSV (name varchar(255))";
        
        String create = "CREATE TABLE TITANIC_PASSENGER_LIST (pclass varchar(255), " +                 
"survived varchar(255), " + 
"name varchar(255), " + 
"sex varchar(255), " + 
"age varchar(255), " + 
"sibsp varchar(255), " + 
"parch varchar(255), " + 
"ticket varchar(255), " +
"fare varchar(255), " +
"cabin varchar(255), " +
"embarked varchar(255), " +
"boat  varchar(255), " +
"body varchar(255), " +
"home_dest  varchar(255) " +
        ")";
        
        boolean execute = statement.execute(create);
        System.out.println("execute = " + execute);
        
//        String select = "SELECT * FROM app.TITANIC_PASSENGER_LIST_CSV ORDER BY NAME";        
        String select = "SELECT * FROM TITANIC_PASSENGER_LIST ORDER BY NAME";
        
        ResultSet rs = statement.executeQuery(select);            
    }
    
    private static Connection getConnection() throws SQLException
    {
        boolean createDb = false;
        
        Connection connection = getConnection(createDb);

        return connection;
    }
    
    private static Connection getConnection(boolean create) throws SQLException
    {
        String url = connectionUrl;
        
        if(create)
        {
            url +=  " create=true";
        }

        Connection conn = DriverManager.getConnection(url);
        
        return conn;
    }

    public static void persistPassengers(List<TitanicPassenger> passengers) throws SQLException 
    {
        Connection connection = getConnection();
        
        String SQL_INSERT = "INSERT INTO TITANIC_PASSENGER_LIST ("
                + ""
                + "pclass, "
                + "survived, "
                + "name, sex, age, sibsp, parch, ticket, fare, cabin, embarked, boat, body, home_dest"
                + ""
                + ""
                + ""
                + ""
                + ""
                + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        
        
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);

        for(TitanicPassenger p : passengers)
        {
            preparedStatement.setString(1, p.pClass);
            preparedStatement.setString(2, p.survived);
            preparedStatement.setString(3, p.name);
            preparedStatement.setString(4, p.sex);
            preparedStatement.setString(5, p.age);
            preparedStatement.setString(6, p.sibsp);
            preparedStatement.setString(7, p.parch);
            preparedStatement.setString(8, p.ticket);
            preparedStatement.setString(9, p.fare);
            preparedStatement.setString(10, p.cabin);
            preparedStatement.setString(11, p.embarked);
            preparedStatement.setString(12, p.boat);
            preparedStatement.setString(13, p.body);
            preparedStatement.setString(14, p.homeDest);

            int row = preparedStatement.executeUpdate();

            // rows affected
            System.out.println(row); //1
        }
    }
    
    public static List<TitanicPassenger> retrievePassengers() throws SQLException
    {
        Connection connection = getConnection();

        String SQL_RETRIEVE = "SELECT * FROM TITANIC_PASSENGER_LIST";
    
        Statement statement = connection.createStatement();
            
        ResultSet resultSet = statement.executeQuery(SQL_RETRIEVE);
    
        List<TitanicPassenger> passengers = new ArrayList();
        
        while(resultSet.next())
        {
            TitanicPassenger p = new TitanicPassenger();
            
            p.pClass = resultSet.getString("pclass");
            p.survived = resultSet.getString("survived");
            p.name = resultSet.getString("name");
            p.sex = resultSet.getString("sex");
            p.age = resultSet.getString("age");
            p.sibsp = resultSet.getString("sibsp");
            p.parch = resultSet.getString("parch");
            p.ticket = resultSet.getString("ticket");
            p.fare = resultSet.getString("fare");
            p.cabin = resultSet.getString("cabin");
            p.embarked = resultSet.getString("embarked");
            p.boat = resultSet.getString("boat");
            p.body = resultSet.getString("body");
            p.homeDest = resultSet.getString("home_dest");
            
            passengers.add(p);
        }
        
        return passengers;
    }
}
