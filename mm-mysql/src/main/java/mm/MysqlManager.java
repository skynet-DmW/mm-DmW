package mm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlManager {


    /**
     * jdbc:mysql://localhost:3306/qq?allowMultiQueries=true&useSSL=false&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true
     */
    private String url;

    /**
     * com.mysql.cj.jdbc.Driver
     */
    private String driver;

    /**
     * root
     */
    private String user;

    /**
     * 123456
     */
    private String password;


    private Connection connection;


    public MysqlManager(String url, String driver, String user, String password) {
        this.url = url;
        this.driver = driver;
        this.user = user;
        this.password = password;
        try {
            Class.forName(this.driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(url, user, password);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public ResultSet executeQuery(String sql) {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }


    public long executeUpdate(String sql) {
        long id = 0;
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = st.getGeneratedKeys();
            resultSet.next();
            id = resultSet.getLong(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }


    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
