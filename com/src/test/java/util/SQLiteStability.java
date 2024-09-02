package util;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class SQLiteStability {
    public String            dbPath         = "stability_longrun.db";
    public static Connection dbConn         = null;
    String                   tableName      = "testresult";
    public static int        curResultIndex = 0;
    Logger                   logger         = Logger.getLogger("SQLiteStability");

    public SQLiteStability() {
        if (dbConn != null) {
            logger.info("return previous");
        }

        dbPath = Paths.get(Paths.get(System.getProperty("user.dir")).toString(), "bin", dbPath).toString();
        try {
            dbConn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            getLastResultIndex();
        } catch (SQLException e) {
            logger.info("init: " + e.getMessage());
        }
    }

    public int getLastResultIndex() {
        if (curResultIndex == 0) {
            String sql = "SELECT resultIndex FROM " + tableName + " order by resultIndex desc limit 1";
            Statement stmt;
            try {
                stmt = dbConn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    curResultIndex = rs.getInt("resultIndex") + 1;
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                logger.info("getLastResultIndex: " + e.getMessage());
            }

            logger.info("current test result index: " + curResultIndex);
        }
        return curResultIndex;
    }

    /**
     * @param stepInfo
     * @return true is exists
     */
    public boolean isStepExist(String tcName, String stepInfo) {
        String sql = "SELECT * FROM " + tableName + " where resultIndex = ? and tcName = ? and stepInfo = ?";
        PreparedStatement pstmt;
        try {
            pstmt = dbConn.prepareStatement(sql);
            pstmt.setInt(1, curResultIndex);
            pstmt.setString(2, tcName);
            pstmt.setString(3, stepInfo);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                logger.info("found tc&step: " + rs.getInt("resultIndex"));
                return true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.info("isStepExist: " + e.getMessage());
        }
        return false;
    }

    /**
     * @param tcName
     * @param stepInfo
     */
    public void syncResultIndex(String tcName, String stepInfo) {
        if (!isStepExist(tcName, stepInfo))
            return;
        curResultIndex++;
    }

    /**
     * @param tcName
     * @param stepInfo
     * @param testResult
     * @param expectResult
     * @param actualResult
     */
    public void writeResult(String tcName, String stepInfo, String testResult, String expectResult, String actualResult) {
        String sql = "INSERT INTO " + tableName + "(resultIndex,tcName,stepInfo,testResult,expectResult,actualResult) VALUES(?,?,?,?,?,?)";
        PreparedStatement pstmt;
        syncResultIndex(tcName, stepInfo);
        int aResLen = actualResult.length();
        logger.info(String.format("Index: (%s), Name: (%s), Step: (%s), Actual: (%s), Expected: (%s), Result: (%s)", curResultIndex, tcName,
                stepInfo, actualResult, expectResult, testResult));
        try {
            pstmt = dbConn.prepareStatement(sql);
            pstmt.setInt(1, curResultIndex);
            pstmt.setString(2, tcName);
            pstmt.setString(3, stepInfo);
            pstmt.setString(4, testResult);
            pstmt.setString(5, expectResult);
            pstmt.setString(6, actualResult);
            // pstmt.setString(6, actualResult.substring(0, aResLen > 40 ? 40 : aResLen));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.info("writeResult: " + e.getMessage());
        }
    }

    public void writeResultPass(String tcName, String stepInfo, String expectResult, String actualResult) {
        writeResult(tcName, stepInfo, "pass", expectResult, actualResult);
    }

    public void writeResultFail(String tcName, String stepInfo, String expectResult, String actualResult) {
        writeResult(tcName, stepInfo, "fail", expectResult, actualResult);
    }

    /**
     * @param bResult
     *            true is pass, false is fail
     * @param tcName
     * @param stepInfo
     * @param expectResult
     * @param actualResult
     */
    public void writeResult(boolean bResult, String tcName, String stepInfo, String expectResult, String actualResult) {
        if (bResult) {
            writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            writeResultFail(tcName, stepInfo, expectResult, actualResult);
        }
    }

}
