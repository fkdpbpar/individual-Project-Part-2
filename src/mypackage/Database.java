package mypackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    private static final String DB_URL = "localhost:3306";
    private static final String FULL_DB_URL = "jdbc:mysql://" + DB_URL + "/individual_project_partb_theofanis_kollias?zeroDateTimeBehavior=CONVERT_TO_NULL&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWD = "root";
    private static Connection connection = null;
    private static Statement statement = null;
    private static PreparedStatement pst = null;

    public Database() {
        getConnection();
    }

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(FULL_DB_URL, DB_USER, DB_PASSWD);
            return connection;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static ResultSet getResults(String query) {
        try {
            setStatement();
            ResultSet rs = statement.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static ResultSet getOneResult(String tableName, int id) {
        try {
            setStatement();
            String query = "SELECT * FROM `" + tableName + "` WHERE `id`=" + id;
            ResultSet rs = statement.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static ResultSet getOneResultByField(String tableName, String fieldName, int fieldValue) {

        try {
            setStatement();
            String query = "SELECT * FROM `" + tableName + "` WHERE `" + fieldName + "`=" + fieldValue;
            ResultSet rs = statement.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
    
    public static ResultSet getOneResultByField2(String tableName, String fieldValue, String fieldValue2, String fieldValue3, int fieldValue4) {

        try {
            setStatement();
            String query = "(SELECT * FROM "+ tableName + " WHERE first_name='"+fieldValue+"' AND `last_name`='"+fieldValue2+"' AND `date_of_birth`='"+fieldValue3+"' AND `tuition_fees`="+fieldValue4+");";
            ResultSet rs = statement.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
    

    public static Statement getStatement() {
        return statement;
    }

    public Statement getStatementNonStatic() {
        return statement;
    }

    public static void setStatement() {
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setStatementNonStatic() {
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static PreparedStatement getPreparedStatement() {
        return pst;
    }

    public static void setPreparedStatement(String query) {
        try {
            pst = connection.prepareStatement(query);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
