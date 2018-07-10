import java.sql.*;

public class jdbcconnect {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        final String USER = "postgres";
        final String PASS = "creditvidya";
        String url ="jdbc:postgresql://localhost:5433/bharat";
        Connection con = DriverManager.getConnection(url,USER,PASS);
        Statement st =con.createStatement();

        ResultSet rs = st.executeQuery("select m.\"MemberID\",m.\"ContractDuration\", rank() over(partition by m.\"ContractDuration\" order by m.\"MemberID\") from members m order by m.\"MemberID\";");
        while ( rs.next() ) {

           System.out.println(rs.getString(2));
        }
        rs.close();
        st.close();
        con.close();

    }
}
