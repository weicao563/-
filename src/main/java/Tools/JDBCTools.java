package Tools;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 封装了JDBC的连接(createConnnection、close、query、dml)等常用的方法
 */
public class JDBCTools {

    private static String driverName = "";
    private static String url = "";
    private static String username = "";
    private static String password = "";

    // 从res下的jdbc.properties读取内容，初始化连接参数
    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("res/jdbc.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        driverName = properties.getProperty("driverName");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    /**
     * 创建一个数据库连接
     */
    public static Connection createConnection() {
        try {
            Class.forName(driverName);
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 封装的查询方法，示例代码：
     * {@code
     * 		List<Object> lst = JDBCTools.query("select * from emp", new Object[] {});
     *		for (Object object : lst) {
     *			HashMap<String, Object> map = (HashMap<String, Object>) object;
     *			System.out.println(map.get("ename"));
     *		}
     * @param sql 需要查询的SQL
     * @param obj 传入SQL的参数
     * @return 返回查询结果的List集合
     */
    public static List<Object> query(String sql, Object[] obj) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        List<Object> list = new ArrayList<Object>();
        try {
            conn = JDBCTools.createConnection();
            stat = conn.prepareStatement(sql);

            for (int i = 0; i < obj.length; i++) {
                stat.setObject(i + 1, obj[i]);
            }
            rs = stat.executeQuery();

            rsmd = rs.getMetaData();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    map.put(rsmd.getColumnLabel(i).toLowerCase(), rs.getObject(i));
                }
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.close(conn, stat, rs);
        }
        return list;
    }

    /**
     * 所有的dml都可以传入
     * @param sql 传入操作SQL
     * @param obj SQL对应的参数，当传入的参数为空时写new Object[]
     * @return 影响行数
     */
    public static int dml(String sql, Object[] obj) {
        Connection conn = null;
        PreparedStatement stat = null;
        try {
            conn = JDBCTools.createConnection();
            stat = conn.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++) {
                stat.setObject(i + 1, obj[i]);
            }
            return stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.close(conn, stat, null);
        }
        return 0;
    }

    /**
     * 关闭数据库连接、声明、结果集
     *
     * @param conn
     *            连接Connection
     * @param stat
     *            声明Statement
     * @param rs
     *            结果集ResultSet
     */
    public static void close(Connection conn, Statement stat, ResultSet rs) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}