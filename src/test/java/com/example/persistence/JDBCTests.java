package com.example.persistence;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;

public class JDBCTests {
    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnection() {

        try(Connection con =
                    DriverManager.getConnection(
                            // Oracle19 버전인 경우 => "jdbc:oracle:thin:@localhost:1521:orcl"
                            // Oracle11 버전인 경우 => "jdbc:oracle:thin:@localhost:1521:XE"
                            "jdbc:oracle:thin:@dblab_tp?TNS_ADMIN=/Users/juhee/Oracle/Wallet_DBLab",
                            "ss230203",
                            "Spring_125811")){
            System.out.println(con);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }
}
