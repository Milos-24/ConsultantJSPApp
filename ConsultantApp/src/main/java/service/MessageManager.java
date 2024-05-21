package service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import beans.MessageBean;
import db.ConnectionPool;

public class MessageManager implements Serializable {

	private List<MessageBean> messages;
	
	public MessageManager() {
		messages=new ArrayList<MessageBean>();
		loadMessagesFromDB();
	}
	
	public MessageBean getMessageById(int id)
	{
		return messages.stream().filter(m->m.getId()==id).collect(Collectors.toList()).get(0);
	}
	
	
	public String getRecieverMail(int id)
	{
		Connection c = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        c = ConnectionPool.getInstance().checkOut();
	        ps = c.prepareStatement("SELECT email FROM user WHERE id = ?");
	        ps.setInt(1, id);
	        rs = ps.executeQuery();

	        if (rs.next()) {
	            return rs.getString("email");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (c != null) ConnectionPool.getInstance().checkIn(c);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return "";
	}
	
	public void readMessage(int id)
	{
		 Connection c = null;
		    PreparedStatement ps = null;

		    try {
		        c = ConnectionPool.getInstance().checkOut();
		        
		        ps = c.prepareStatement("UPDATE message SET message.read=? where id=?");
		        ps.setBoolean(1, true);
		        ps.setInt(2, id);
		        

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
	
	
	public void loadMessagesFromDB()
	{
		messages.clear();
		messages=new ArrayList<MessageBean>();
		String sqlQuery = "SELECT * FROM message where 'read'=0";

        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;

        try {
            c = ConnectionPool.getInstance().checkOut();
            s = c.prepareStatement(sqlQuery);
            rs = s.executeQuery();

            while (rs.next()) {
                messages.add(new MessageBean(rs.getString("content"), rs.getInt("id"), rs.getInt("sender_Id"), rs.getInt("reciever_Id"), 
                		rs.getDate("timestamp"), rs.getString("subject"), rs.getBoolean("read")));
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
	}
	
	public void deleteMessage(int id)
	{
		 Connection c = null;
	        Statement s = null;
	        PreparedStatement ps;

	        try {
	            c = ConnectionPool.getInstance().checkOut();
	            s = c.createStatement();
	            ps = c.prepareStatement("delete from message where (id=?); ");
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
	
	
	
	public List<MessageBean> getMessages()
	{
		messages=new ArrayList<MessageBean>();
		String sqlQuery = "SELECT * FROM message where 'read'=0";

        Connection c = null;
        PreparedStatement s = null;
        ResultSet rs = null;

        try {
            c = ConnectionPool.getInstance().checkOut();
            s = c.prepareStatement(sqlQuery);
            rs = s.executeQuery();

            while (rs.next()) {
                messages.add(new MessageBean(rs.getString("content"), rs.getInt("id"), rs.getInt("sender_Id"), rs.getInt("reciever_Id"), 
                		rs.getDate("timestamp"), rs.getString("subject"), rs.getBoolean("read")));
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
        
        return messages;
	}
	
	public int getUser(String username) {
	    int id = 0;
	    
	    Connection c = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        c = ConnectionPool.getInstance().checkOut();
	        ps = c.prepareStatement("SELECT id FROM user WHERE username = ?");
	        ps.setString(1, username);
	        rs = ps.executeQuery();
	        
	        while (rs.next()) {
	            id = rs.getInt("id");
	            System.out.println("logovan user: " + id);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (c != null) ConnectionPool.getInstance().checkIn(c);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return id;
	}
	
	public String getUser(int id) {
	
		String firstName="";
	    Connection c = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        c = ConnectionPool.getInstance().checkOut();
	        ps = c.prepareStatement("SELECT firstname FROM user WHERE id = ?");
	        ps.setInt(1, id);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            firstName = rs.getString("firstname");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (c != null) ConnectionPool.getInstance().checkIn(c);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return firstName;
	}

}
