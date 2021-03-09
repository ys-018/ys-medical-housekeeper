package com.ys.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author
 * @ClassName:
 * @Description:
 * @date
 */
public class ThreadWorker implements Runnable {

    private int start = 0;
    private int end = 0;
    private CountDownLatch latch;
    private List<String> sqlList;

    public ThreadWorker(CountDownLatch latch, int start, int end, List<String> sqlList) {
        this.start = start;
        this.end = end;
        this.latch = latch;
        this.sqlList = sqlList;
    }

    public void run() {
        System.out.println("线程" + Thread.currentThread().getName() + "正在执行。。线程数据量为:" + (end - start));
        long startTime = System.currentTimeMillis();
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = TestCon.getConnection();
            boolean autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            int rowNum = 0;
            for (int i = start; i < end; i++) {
                stmt.addBatch(sqlList.get(i));
                if (++rowNum % 10000 == 0) {
                    stmt.executeBatch();
                    conn.commit();
                    System.out.println("线程" + Thread.currentThread().getName() + "现已完成" + rowNum + "条记录插入");
                }
            }
            stmt.executeBatch();
            // 提交修改
            conn.commit();
            conn.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            TestCon.closeDataBaseResources(conn, stmt, null);
            long endTime = System.currentTimeMillis();
            System.out.println("线程" + Thread.currentThread().getName() + "数据写入时间：" + (endTime - startTime) + "ms");
        }

        latch.countDown();
    }
}
