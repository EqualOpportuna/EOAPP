    package com.example.equalopportuna;

    import android.os.StrictMode;
    import android.util.Log;

    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.sql.Statement;
    import java.util.ArrayList;
    import java.util.List;

    public class Database {
        Connection con;

        public Connection SQLConnection() {
            StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(tp);

            String ConURL = "jdbc:mysql://114.132.171.90:3306/equalopportuna";

            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(ConURL, "root", "um123456");
            } catch (ClassNotFoundException e) {
                Log.e("Error", "MySQL JDBC Driver not found: " + e.getMessage());
            } catch (SQLException e) {
                Log.e("Error", "Error connecting to the database: " + e.getMessage());
            }

            return con;
        }

        public String getUserIdByUsername(String username) {
            String userId = null;

            try {
                if (con == null || con.isClosed()) {
                    con = SQLConnection();
                }

                if (con != null) {
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT id FROM users WHERE full_name = '" + username + "'");

                    if (rs.next()) {
                        userId = rs.getString("id");
                    }

                    rs.close();
                    stmt.close();
                }
            } catch (SQLException e) {
                Log.e("Error", "Error getting user ID by username: " + e.getMessage());
            }

            return userId;
        }

        public void updateStatus(int userId1, int userId2, String newStatus) {
            try {
                if (con == null || con.isClosed()) {
                    con = SQLConnection();
                }

                if (con != null) {
                    Statement stmt = con.createStatement();
                    String query = "UPDATE friends SET status = '" + newStatus + "' WHERE (user_id1 = " + userId1 + " AND user_id2 = " + userId2 + ") OR (user_id1 = " + userId2 + " AND user_id2 = " + userId1 + ")";
                    stmt.executeUpdate(query);
                    stmt.close();
                }
            } catch (SQLException e) {
                Log.e("Error", "Error updating status in the database: " + e.getMessage());
            }
        }

        public void deleteEntry(int userId1, int userId2) {
            try {
                if (con == null || con.isClosed()) {
                    con = SQLConnection();
                }

                if (con != null) {
                    Statement stmt = con.createStatement();
                    String query = "DELETE FROM friends WHERE (user_id1 = " + userId1 + " AND user_id2 = " + userId2 + ") OR (user_id1 = " + userId2 + " AND user_id2 = " + userId1 + ")";
                    stmt.executeUpdate(query);
                    stmt.close();
                }
            } catch (SQLException e) {
                Log.e("Error", "Error deleting entry from the database: " + e.getMessage());
            }
        }

        public void insertStory(String username, String message) {
            try {
                if (con == null || con.isClosed()) {
                    con = SQLConnection();
                }

                if (con != null) {
                    // Get user_id from the username
                    String userId = getUserIdByUsername(username);

                    if (userId != null) {
                        Statement stmt = con.createStatement();
                        String query = "INSERT INTO stories (user_id, message) VALUES (" + userId + ", '" + message + "')";
                        stmt.executeUpdate(query);
                        stmt.close();
                    }
                }
            } catch (SQLException e) {
                Log.e("Error", "Error inserting story into the database: " + e.getMessage());
            }
        }

        public String getEmailFromName(String username) {
            String email = null;

            try {
                if (con == null || con.isClosed()) {
                    con = SQLConnection();
                }

                if (con != null) {
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT email FROM users WHERE full_name = '" + username + "'");

                    if (rs.next()) {
                        email = rs.getString("email");
                    }

                    rs.close();
                    stmt.close();
                }
            } catch (SQLException e) {
                Log.e("Error", "Error getting email from name: " + e.getMessage());
            }

            return email;
        }


    }
