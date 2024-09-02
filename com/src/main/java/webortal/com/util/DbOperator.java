package webortal.com.util;

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
/**
 * 数据库的操作
 * @author lizhenwei
 *
 */
public class DbOperator {
	private static String dbUser = "root";
	private static String dbPassword ="root";
	/**
	 * 打开数据库连接
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        DbOperator dbOperator= new DbOperator();
        System.out.println(dbOperator.execSelectSQL("select * from insight_device_type"));
    }
	public Connection openDbConn() throws ClassNotFoundException, SQLException {
//		Class.forName("com.mysql.cj.jdbc.Driver");
		String dburl = "jdbc:mysql://localhost:3306/system";
		Connection conn = DriverManager.getConnection(dburl, dbUser, dbPassword);

		return conn;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @throws SQLException
	 */
	public void closeDbConn(Connection conn) throws SQLException {
		conn.close();
	}
	/**
	 * 将数据库查询的查询结果用list返回
	 * 
	 * @param singleQuerySql 需要执行的select语句
	 * @return list对象
	 * @throws ClassNotFoundException 
	 * @throws SQLException
	 * @author lizhenwei
	 */
	public List execSelectSQL(String singleQuerySql) throws ClassNotFoundException, SQLException {
		Connection conn = openDbConn();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(singleQuerySql);
			ResultSetMetaData md = rs.getMetaData(); // 得到结果集(rs)的结构信息，比如字段数、字段名等
			int columnCount = md.getColumnCount(); // 返回此 ResultSet 对象中的列数
			List list = new ArrayList();
			Map rowData = new HashMap();
			while (rs.next()) {
				rowData = new HashMap(columnCount);
				for (int i = 1; i <= columnCount; i++) {
					rowData.put(md.getColumnName(i), rs.getObject(i));
				}
				list.add(rowData);
			}
			rs.close();
			stmt.close();
			return list;
		} finally {
			if (conn != null) {
				closeDbConn(conn);
			}
		}

	}

	/**
	 * 执行数据库增、删、改的操作
	 * 
	 * @param singleIduSql：单条INSERT语句
	 *            或者 单条DELETE语句 或者 单条UPDATE语句
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @author lizhenwei
	 * @return：影响行数
	 */
	public int execIdu(String singleIduSql) throws SQLException, ClassNotFoundException {
		Connection conn = openDbConn();
		try {
			int rowCount = -1;
			PreparedStatement stmt = conn.prepareStatement(singleIduSql);
			rowCount = stmt.executeUpdate();
			conn.commit();
			stmt.close();
			return rowCount;
		} finally {
			if (conn != null) {
				closeDbConn(conn);
			}
		}

	}

	/**
	 * 批量执行 INSERT、DELETE、UPDATE 语句 或者 它们的组合
	 * 
	 * @param sqlList
	 *            : 需要执行的SQL语句列表
	 * @return ： 如果成功，则返回true;否则，返回false.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("finally")
	public boolean execBatch(List<String> sqlList) throws ClassNotFoundException, SQLException {
		Connection conn = openDbConn();
		int[] tmp = null;
		try {
			Statement stmt = conn.createStatement();
			for (String sql : sqlList) {
				stmt.addBatch(sql);
			}
			conn.setAutoCommit(false);
			tmp = stmt.executeBatch();
			conn.commit();
			stmt.close();
		} finally {
			conn.rollback();
			conn.setAutoCommit(true);
			conn.close();
			if (tmp == null) {
				return false;
			} else {
				return true;
			}
		}
	}

}
