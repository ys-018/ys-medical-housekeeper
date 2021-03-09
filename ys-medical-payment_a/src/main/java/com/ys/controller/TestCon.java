package com.ys.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @author
 * @ClassName:
 * @Description:
 * @date
 */
public class TestCon {

    private static final String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
    private static final String driver = "oracle.jdbc.driver.OracleDriver";
    private static final String userName = "simulation";
    private static final String password = "simulation";

    /**
     * 获取数据库连接
     * @return 数据库连接
     */
    public static Connection getConnection() {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭数据库操作资源
     * @param conn 数据库连接
     * @param stmt 数据库游标
     * @param rs   结果集
     */
    public static void closeDataBaseResources(Connection conn, Statement stmt, ResultSet rs) {
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs = null;
        }

        if (null != stmt) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stmt = null;
        }

        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn = null;
        }
    }

    /**
     * 读取表信息
     * @param tableName
     * @return
     */
    public static List<Map<String, String>> readTable(String tableName) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Map<String, String>> row = null;
        try {
            conn = getConnection();
            //ResultSet.TYPE_SCROLL_INSENSITIVE 结果集的游标可以上下移动，当数据库变化时，当前结果集不变。
            //ResultSet.CONCUR_READ_ONLY 不能用结果集更新数据库中的表。
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "select * from " + tableName;
            rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount(); //ResultSet的总列数

            row = new ArrayList<Map<String, String>>();
            while (rs.next()) {
                Map<String, String> column = new HashMap<String, String>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    column.put(rsmd.getColumnName(columnIndex).toLowerCase(), rs.getString(columnIndex));
                }
                row.add(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDataBaseResources(conn, stmt, rs);
        }
        return row;
    }

    /**
     * 多线程将集合map数据写入表
     * @param data 集合map数据
     * @param tableName    表名
     */
    public static void multiThreadWriteTable(List<Map<String, String>> data, String tableName) {
        long startTime = System.currentTimeMillis();
        System.out.println("数据总量：" + data.size());
        List<String> sqlList = new ArrayList<String>();
        for (int i = 0; i < data.size(); i++) {
            Map<String, String> entity = data.get(i);
            StringBuffer frontSql = new StringBuffer();
            StringBuffer values = new StringBuffer();
            frontSql.append("insert into ");
            frontSql.append(tableName);
            frontSql.append(" (");
            for (String key : entity.keySet()) {
                frontSql.append(key + ",");
                values.append("'" + entity.get(key) + "',");
            }
            String sql = frontSql.substring(0, frontSql.length() - 1) + ") values(" + values.substring(0, values.length() - 1) + ")";
            sqlList.add(sql);
        }

        int threadCount = 20;
        int every = data.size() / threadCount;
        final CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            if (i == threadCount - 1) {
                new Thread(new ThreadWorker(latch, i * every, (i + 1) * every + (data.size() % threadCount), sqlList)).start();
            } else {
                new Thread(new ThreadWorker(latch, i * every, (i + 1) * every, sqlList)).start();
            }
        }
        try {
            latch.await();
            long endTimes = System.currentTimeMillis();
            System.out.println("所有线程执行完毕:" + (endTimes - startTime));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
