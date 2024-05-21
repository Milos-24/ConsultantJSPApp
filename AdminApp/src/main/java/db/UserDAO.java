package db;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class UserDAO {

	

		
		
		public static void deleteUser(int id)
		{
			Connection c = null;
	        Statement s = null;
	        PreparedStatement ps;

	        try {
	            c = ConnectionPool.getInstance().checkOut();
	            s = c.createStatement();
	            ps = c.prepareStatement("delete from user where (id=?); ");
	            ps.setInt(1, id);
	            ps.executeUpdate();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            if (s != null)
	                try {
	                    s.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            ConnectionPool.getInstance().checkIn(c);
	        }
		}
		
		public static List<User> loadUsersFromDatabase() {
	        List<User> userList = new ArrayList<>();
	        String sqlQuery = "SELECT * FROM user";

	        Connection c = null;
	        PreparedStatement s = null;
	        ResultSet rs = null;

	        try {
	            c = ConnectionPool.getInstance().checkOut();
	            s = c.prepareStatement(sqlQuery);
	            rs = s.executeQuery();

	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String firstname = rs.getString("firstname");
	                String lastname = rs.getString("lastname");
	                String username = rs.getString("username");
	                String password = rs.getString("password");
	                String email = rs.getString("email");
	                boolean locked = rs.getBoolean("locked");
	                boolean enabled = rs.getBoolean("enabled");
	                
	                String city = rs.getString("city");

	                User user = new User(id, firstname, lastname, username, password, email, locked, enabled, "", 0, city);
	                userList.add(user);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            if (rs != null) {
	                try {
	                    rs.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	            if (s != null) {
	                try {
	                    s.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	            ConnectionPool.getInstance().checkIn(c);
	        }

	        return userList;
	    }

		
		public static void editUser(User user) {
		    Connection c = null;
		    PreparedStatement ps = null;

		    try {
		        c = ConnectionPool.getInstance().checkOut();
		        
		        ps = c.prepareStatement("UPDATE user SET firstname=?, lastname=?, username=?, email=?, locked=?, enabled=?, city=? WHERE id=?");
		        ps.setString(1, user.getFirstname());
		        ps.setString(2, user.getLastname());
		        ps.setString(3, user.getUsername());
		        ps.setString(4, user.getEmail());
		        ps.setBoolean(5, user.isLocked());
		        ps.setBoolean(6, user.isEnabled());
		        ps.setString(7, user.getCity());		        
		        ps.setInt(8, user.getId());

		        ps.executeUpdate();

		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (ps != null) {
		                ps.close();
		            }
		            ConnectionPool.getInstance().checkIn(c);
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		}
		
		
		public static int getHighestId()
		{
			List<User> users = loadUsersFromDatabase();
			
			Optional<User> highestIdUser = users.stream()
	                .sorted(Comparator.comparingInt(User::getId).reversed())
	                .findFirst();
			
			return highestIdUser.get().getId();
		}

	    public static boolean loadUserCredentialsFromDatabase(String username, String password) {
	        String sqlQuery = "SELECT * FROM user";
	        boolean success=false;
	        Connection c = null;
	        PreparedStatement s = null;
	        ResultSet rs = null;

	        try {
	            c = ConnectionPool.getInstance().checkOut();
	            s = c.prepareStatement(sqlQuery);
	            rs = s.executeQuery();

	            while (rs.next()) {
	                String user = rs.getString("username");
	                String pass = rs.getString("password");

	                if(username.equals(user) && password.equals(pass) && rs.getInt("user_type_id")==1)
	                {
	                	success=true;
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            if (rs != null)
	                try {
	                    rs.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            if (s != null)
	                try {
	                    s.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            ConnectionPool.getInstance().checkIn(c);
	        }
	        
	        return success;
	    }

	    public UserDAO() {
			// TODO Auto-generated constructor stub
		}

	    public static void addUser(User user) {
	        Connection c = null;
	        PreparedStatement ps = null;
	        System.out.println(user);
	        try {
	            c = ConnectionPool.getInstance().checkOut();
	            
	            ps = c.prepareStatement("INSERT INTO user (id, firstname, lastname, username, password, email, locked, enabled, avatar, user_type_id, city) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	            ps.setInt(1, getHighestId()+1);
	            ps.setString(2, user.getFirstname());
	            ps.setString(3, user.getLastname());
	            ps.setString(4, user.getUsername());
	            ps.setString(5, user.getPassword());
	            ps.setString(6, user.getEmail());
	            ps.setBoolean(7, user.isLocked());
	            ps.setBoolean(8, user.isEnabled());
	            ps.setNull(9, java.sql.Types.BLOB); 
	            ps.setInt(10, user.getUser_type_id());
	            ps.setString(11, user.getCity());

	            ps.executeUpdate();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (ps != null) {
	                    ps.close();
	                }
	                ConnectionPool.getInstance().checkIn(c);
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

}
