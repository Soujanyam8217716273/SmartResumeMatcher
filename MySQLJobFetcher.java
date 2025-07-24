import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLJobFetcher {
    private static final Logger LOGGER = Logger.getLogger(MySQLJobFetcher.class.getName());

    public static List<Job> fetchJobsFromDatabase() {
        List<Job> jobs = new ArrayList<>();

        try {
            // Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect using try-with-resources (Connection only)
            try (Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/job_portal?useSSL=false&serverTimezone=UTC",
                    "root",
                    "your_password" // 🔁 Replace with your actual password
            )) {
                Statement stmt = null;
                ResultSet rs = null;

                try {
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery("SELECT * FROM jobs");

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String title = rs.getString("title");
                        String[] skills = rs.getString("skills").split(",");
                        jobs.add(new Job(id, title, skills));
                    }
                } finally {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                }
            }

        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "MySQL JDBC Driver not found.", e);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection error.", e);
        } 

        return jobs;
    }

    public static void main(String[] args) {
        List<Job> jobList = fetchJobsFromDatabase();
        for (Job job : jobList) {
            System.out.println(job);
        }
    }
}
