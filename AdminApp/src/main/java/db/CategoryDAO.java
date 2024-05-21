package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


public class CategoryDAO {

	public CategoryDAO() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static void deleteCategory(int id)
	{
		deleteExercises(id);
		
		 Connection c = null;
	        Statement s = null;
	        PreparedStatement ps;

	        try {
	            c = ConnectionPool.getInstance().checkOut();
	            s = c.createStatement();
	            ps = c.prepareStatement("delete from category where (id=?); ");
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
	
	 public static void addCategory(String category)
	 {
	        Connection c = null;
	        Statement s = null;
	        ResultSet rs = null;
	        PreparedStatement ps;
	        try {
	        	c = ConnectionPool.getInstance().checkOut();
	            s = c.createStatement();
	            ps = c.prepareStatement("insert into category (id, category) values (?, ?);");
	            ps.setInt(1, countLastIdCategory()+1);
	            ps.setString(2, category);
	            ps.executeUpdate();
	            }
	         catch (SQLException e) {
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
	 }

	
	
	public static void editCategory(int id, String name)
	{
		Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        PreparedStatement ps;

        try {
            c = ConnectionPool.getInstance().checkOut();
            s = c.createStatement();
            ps = c.prepareStatement("update category set category=? where (id=?);");// 	update category set category="end" where id=4;
            ps.setString(1, name);
            ps.setInt(2, id);
            ps.executeUpdate();

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
	}
	
	
	public static void deleteExercises(int id)
	{
		 Connection c = null;
	        Statement s = null;
	        PreparedStatement ps;

	        try {
	            c = ConnectionPool.getInstance().checkOut();
	            s = c.createStatement();
	            ps = c.prepareStatement("delete from exercise where (category_id=?); ");
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
	
	public static List<Category> loadCategories()
	{
		ArrayList<Category> categories = new ArrayList<Category>();
		
		String sqlQuery = "SELECT * FROM category";

        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;

        try {
            c = ConnectionPool.getInstance().checkOut();
            s = c.prepareStatement(sqlQuery);
            rs = s.executeQuery();

            while (rs.next()) {
                Category cat=new Category(rs.getString("category"), rs.getInt("id"));
                
                ArrayList<Exercise> exercises = (ArrayList<Exercise>) loadExercises(rs.getInt("id"));
                
                cat.setExercise(exercises);
                
                categories.add(cat);
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
        
        return categories;
	}
	
	
	public static List<Exercise> loadExercises(int category_id)
	{
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		 Connection c = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;

		    try {
		        c = ConnectionPool.getInstance().checkOut();
		        ps = c.prepareStatement("select * from exercise where category_id=?;");
		        ps.setInt(1, category_id);
		        rs = ps.executeQuery();

            while (rs.next()) {
                exercises.add(new Exercise(rs.getInt("id"),rs.getInt("category_id"), rs.getString("name")));
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
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            ConnectionPool.getInstance().checkIn(c);
        }
        
        return exercises;
	}
	public static int countLastIdCategory()
	{
		ArrayList<Category> categories = new ArrayList<Category>();
		 Connection c = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;

		    try {
		        c = ConnectionPool.getInstance().checkOut();
		        ps = c.prepareStatement("select * from category;");
		        
		        rs = ps.executeQuery();

            while (rs.next()) {
                categories.add(new Category(rs.getString("category"), rs.getInt("id")));
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
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            ConnectionPool.getInstance().checkIn(c);
        }
		    
	 Optional<Category> highestIdCategory = categories.stream()
	                .sorted(Comparator.comparingInt(Category::getId).reversed())
	                .findFirst();        
	 
        return highestIdCategory.get().getId();
	}

}
