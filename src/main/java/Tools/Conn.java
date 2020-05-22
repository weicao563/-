package Tools;

import java.sql.*;
public class Conn {
    Connection con;
    public Connection getConnection() {
             try {
                      Class.forName("com.mysql.jdbc.Driver");  System.out.println("数据库驱动加载成功");
                  } catch(ClassNotFoundException e){
                      e.printStackTrace();
                   }
             try {
                      con = DriverManager.getConnection("jdbc:mysql://rm-uf6y476oqgf602d8a33150.mysql.rds.aliyuncs.com:3306/" +
                              "aiways_index?useUnicode=true&characterEncoding=utf8&useSSL=false","root_aiways","l7nTpY2U");
                       System.out.println("数据库连接成功");
                  } catch (SQLException e) {
                       e.printStackTrace();
                   }
               return con;
            }
    public static void main(String[] args) {
              Conn c = new Conn();
               c.getConnection();
          }
 }