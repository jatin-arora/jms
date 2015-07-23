package com.jeet.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jeet.api.Login;
import com.jeet.api.Pipe;
import com.jeet.api.User;

public class DAO {

	private static DAO instance;
	private ConnectionManager mgr;

	private DAO() {
		mgr = new ConnectionManager();
	}

	public static DAO instance() {
		if (instance == null) {
			instance = new DAO();
		}
		return instance;
	}

	public Login getLoginDetail(String loginName) {
		Connection con = mgr.getConnection();
		try {
			PreparedStatement pStm = con
					.prepareStatement("select * from login where userId=?");
			pStm.setString(1, loginName);
			ResultSet rs = pStm.executeQuery();
			if (rs.next()) {
				String userId = rs.getString(1);
				String password = rs.getString(2);
				return new Login(userId, password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean updateLoginDeails(Login login) {
		Connection con = mgr.getConnection();
		try {
			PreparedStatement pStm = con
					.prepareStatement("insert into login values(?,?)");
			pStm.setString(1, login.getUserId());
			pStm.setString(2, login.getPassword());
			pStm.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateUserDetails(User user) {
		Connection con = mgr.getConnection();
		try {
			PreparedStatement pStm = con
					.prepareStatement("insert into user values(?,?,?,?,?,?,?)");
			pStm.setString(1, user.getUserId());
			pStm.setString(2, user.getFirstName());
			pStm.setString(3, user.getLastName());
			pStm.setString(4, user.getGender());
			pStm.setInt(5, user.getAge());
			pStm.setString(6, user.getAddress());
			pStm.setInt(7, user.getPhoneNumber());
			pStm.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<String> getAllUserNames(String userId){
		List<String> users = new ArrayList<String>();
		Connection con = mgr.getConnection();
		try {
			PreparedStatement pStm = con
					.prepareStatement("select userId from User where userId != ?");
			pStm.setString(1, userId);
			ResultSet rSet = pStm.executeQuery();
			while (rSet.next()) {
				users.add(rSet.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	public List<Pipe> getAllPipes() {
		List<Pipe> pipes = new ArrayList<Pipe>();
		Connection con = mgr.getConnection();
		try {
			PreparedStatement pStm = con
					.prepareStatement("select * from pipes");
			ResultSet rSet = pStm.executeQuery();
			while (rSet.next()) {
				pipes.add(new Pipe(rSet.getString(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pipes;
	}
	
	public Pipe getPipe(String userId){
		Pipe pipe = null;
		Connection con = mgr.getConnection();
		try {
			PreparedStatement pStm = con
					.prepareStatement("select pipeName from userpipe where userId=?");
			pStm.setString(1, userId);
			ResultSet rSet = pStm.executeQuery();
			if (rSet.next()) {
				pipe = new Pipe(rSet.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pipe;
	}
	
	public boolean updateUsePipes(String userId, String pipeName){
		Connection con = mgr.getConnection();
		try {
			PreparedStatement pStm = con
					.prepareStatement("insert into userPipe values(?,?)");
			pStm.setString(1, userId);
			pStm.setString(2, pipeName);
			pStm.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	

	public boolean isPipeAvailable(String pipeName) {
		Connection con = mgr.getConnection();
		try {
			PreparedStatement pStm = con
					.prepareStatement("select * from userPipe where pipeName=?");
			pStm.setString(1, pipeName);
			ResultSet rSet = pStm.executeQuery();
			if (rSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
