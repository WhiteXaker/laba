import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by omatikaya on 19/12/2016.
 */
public class Main {
    public static void main(String[] args) {
        String query = "Select * from `order`";
        MysqlJdbcTemplate jdbcTemplate = new MysqlJdbcTemplate();

        int count = 1;

        try {
            ResultSet rs = jdbcTemplate.query(query);
            while (rs.next()) {
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(count);
    }
}
