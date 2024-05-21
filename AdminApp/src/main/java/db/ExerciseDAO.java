package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class ExerciseDAO {

	public ExerciseDAO() {
		// TODO Auto-generated constructor stub INSERT INTO `ipdb`.`exercise` (`id`, `category_id`, `name`) VALUES ('6', '1', 'pull up');
	}
	
	public static void addExercise(String name, String categoryName)
	 {
				Optional<Category> foundCategory = CategoryDAO.loadCategories().stream()
                .filter(category -> category.getName().contains(categoryName))
                .findFirst();
			
			if(foundCategory.isPresent())
			{
	
	        Connection c = null;
	        Statement s = null;
	        ResultSet rs = null;
	        PreparedStatement ps;
	        try {
	        	c = ConnectionPool.getInstance().checkOut();
	            s = c.createStatement();
	            ps = c.prepareStatement("insert into exercise (id, category_id, name) values (?, ?, ?);");
	            ps.setInt(1, countLastIdExercise()+1);
	            ps.setInt(2, foundCategory.get().getId());
	            ps.setString(3, name);
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
	 }
	
	public static int countLastIdExercise()
	{
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		 Connection c = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;

		    try {
		        c = ConnectionPool.getInstance().checkOut();
		        ps = c.prepareStatement("select * from exercise;");
		        
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
		    
	 Optional<Exercise> highestIdCategory = exercises.stream()
	                .sorted(Comparator.comparingInt(Exercise::getId).reversed()) // Sorting in descending order
	                .findFirst();        
	 
        return highestIdCategory.get().getId();
	}
	
	public static void editExercise(int id, String name)
	{
		Connection c = null;
        Statement s = null;
        ResultSet rs = null;
        PreparedStatement ps;

        try {
            c = ConnectionPool.getInstance().checkOut();
            s = c.createStatement();
            ps = c.prepareStatement("update exercise set name=? where (id=?);");
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
	public static void deleteExercise(int id)
	{
		 Connection c = null;
	        Statement s = null;
	        PreparedStatement ps;

	        try {
	            c = ConnectionPool.getInstance().checkOut();
	            s = c.createStatement();
	            ps = c.prepareStatement("delete from exercise where (id=?); ");
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

}
