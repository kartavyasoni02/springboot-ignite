package com.teacheron.sales.repositories.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.teacheron.sales.entities.UserEntry;
import com.teacheron.sales.repositories.UserStore;


public class IgniteUserStore implements UserStore {
	
	/*
	 * @Autowired private Ignite ignite;
	 */
	
	@Override
    public UserEntry createUserEntry(UserEntry userEntry) throws ClassNotFoundException, SQLException {
		
		try (PreparedStatement stmt =
				getConnection().prepareStatement("INSERT INTO Users (id, firstName, lastName, address) VALUES (?, ?, ?, ?)")) {

		    stmt.setInt(1, userEntry.getId());
		    stmt.setString(2, userEntry.getFirstName());
		    stmt.setString(3, userEntry.getLastName());
		    stmt.setString(4, userEntry.getAddress());
		    stmt.executeUpdate();
		}
		return userEntry;
    }
	
	@Override
    public List<UserEntry> getAllUsers() throws ClassNotFoundException, SQLException {
		 List<UserEntry> userList = null;
			// Get data
	        try (Statement stmt = getConnection().createStatement()) {
	            try (ResultSet rs =
	                         stmt.executeQuery("SELECT firstName, lastName, address FROM Users")) {

	                userList = new ArrayList<>();
	                while (rs.next()) {
	                	UserEntry user = new UserEntry();
	                	user.setFirstName(rs.getString("firstName"));
	                	user.setLastName(rs.getString("lastName"));
	                	user.setAddress(rs.getString("address"));
	                	userList.add(user);
	                }
	                    
	            }
	        }
			return userList;

    }
	
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
		// Open JDBC connection
		return DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1:10800;user=ignite;password=ignite");
    }

}
