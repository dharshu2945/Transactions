package com.ground.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.ground.bean.SlotUsage;
import com.ground.util.DBUtil;

public class SlotUsageDAO {
	public int generateUsageID() {
		int id=1;
		try {
			Connection con=DBUtil.getDBConnection();
			PreparedStatement ps=con.prepareStatement("SELECT MAX(usageID) FROM SLOT_USAGE_TBL");
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				id=rs.getInt(1)+1;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	public boolean insertSlotUsage(SlotUsage usage) {
		try {
			Connection con=DBUtil.getDBConnection();
			PreparedStatement ps=con.prepareStatement("INSERT INTO SLOT_USAGE_TBL VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, usage.getUsageID());
			ps.setString(2, usage.getBookingID());
			ps.setString(3, usage.getActualSportPlayed());
			ps.setInt(4, usage.getActualPlayersCount());
			Date d=new Date(usage.getActualStartTime().getTime());
			ps.setDate(5, d);
			Date d1=new Date(usage.getActualEndTime().getTime());
			ps.setDate(6, d1);
			ps.setInt(7, usage.getOvertimeMinutes());
			ps.setDouble(8, usage.getOvertimeChargeAmount());
			ps.setString(9, usage.getGroundConditionRating());
			ps.setString(10, usage.getCleanlinessRating());
			ps.setString(11, usage.getCaretakerRemarks());
			ps.setString(12, usage.getUsageStatus());
			int rs=ps.executeUpdate();
			if(rs>0) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public SlotUsage findSlotUsageByBooking(String bookingID) {
		try {
			Connection con=DBUtil.getDBConnection();
			PreparedStatement ps=con.prepareStatement("SELECT * FROM SLOT_USAGE_TBL WHERE bookingID=?");
			ps.setString(1, bookingID);
			ResultSet rs=ps.executeQuery();
			if (rs.next()) {
			    SlotUsage usage = new SlotUsage(); 
			    usage.setBookingID(rs.getString("BOOKING_ID"));
			    return usage;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public java.util.List<SlotUsage> findUsageByDate(java.sql.Date bookingDate){
		ArrayList<SlotUsage> list=new ArrayList<>();
		try {
			Connection con=DBUtil.getDBConnection();
			PreparedStatement ps=con.prepareStatement("SELECT s.usageID,s.bookingID,s.usageDate,s.actualStartTime,s.actualEndTime,s.usageStatus FROM SLOT_USAGE_TBL s JOIN GROUND_SLOT_TBL g ON s.bookingID=g.bookingID WHERE g.bookingDate=?");
			ps.setDate(1, bookingDate);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				SlotUsage usage=new SlotUsage();
				usage.setUsageID(rs.getInt("usageID"));
				usage.setBookingID(rs.getString("bookingID"));
				usage.setUsageID(rs.getInt("usageID"));
				usage.setActualStartTime(rs.getTimestamp("actualStartTime"));
	            usage.setActualEndTime(rs.getTimestamp("actualEndTime"));
				list.add(usage);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list; 
	}
	public java.util.List<SlotUsage> findUsageBySport(String sportName){
		ArrayList<SlotUsage> list=new ArrayList<>();
		try {
			Connection con=DBUtil.getDBConnection();
			PreparedStatement ps=con.prepareStatement("SELECT * FROM SLOT_USAGE_TBL WHERE ActualSportPlayed=? ");
			ps.setString(1, sportName);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				SlotUsage usage=new SlotUsage();
				 usage.setUsageID(rs.getInt("usageID"));
				 usage.setBookingID(rs.getString("bookingID"));
				 usage.setActualStartTime(rs.getTimestamp("actualStartTime"));
				 usage.setActualEndTime(rs.getTimestamp("actualEndTime"));
			     usage.setUsageStatus(rs.getString("usageStatus"));
			     list.add(usage);
				}
			}catch(Exception e) {
				e.printStackTrace();
		}
		return list;
	}
	public java.util.List<SlotUsage> findUsageByTeam(String teamName){
		ArrayList<SlotUsage> list= new ArrayList<>();
		try {
			Connection con=DBUtil.getDBConnection();
			PreparedStatement ps=con.prepareStatement("SELECT s.usageID,s.bookingID,s.usageDate,s.actualStartTime,s.actualEndTime,s.usageStatus FROM SLOT_USAGE_TBL s JOIN GROUND_SLOT_TBL g ON s.bookingID=g.bookingID WHERE g.teamName=?");
			ps.setString(1, teamName);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
	            SlotUsage usage = new SlotUsage();
	            usage.setUsageID(rs.getInt("usageID"));
	            usage.setBookingID(rs.getString("bookingID"));
	            usage.setActualStartTime(rs.getTimestamp("actualStartTime"));
	            usage.setActualEndTime(rs.getTimestamp("actualEndTime"));
	            usage.setUsageStatus(rs.getString("usageStatus"));
	            list.add(usage);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public boolean updateSlotUsage(SlotUsage usage) {
		try {
			Connection con=DBUtil.getDBConnection();
			PreparedStatement ps=con.prepareStatement("UPDATE SLOT_USAGE_TBL SET actualPlayersCount = ?, overtimeMinutes = ?, groundConditionRating = ?, cleanlinessRating = ?, caretakerRemarks = ? WHERE usageID = ?");
			ps.setInt(1, usage.getActualPlayersCount());
			ps.setInt(2, usage.getOvertimeMinutes());
			ps.setString(3, usage.getGroundConditionRating());
			ps.setString(4, usage.getCleanlinessRating());
			ps.setString(5, usage.getCaretakerRemarks());
			ps.setInt(6, usage.getUsageID()); 
			int rs=ps.executeUpdate();
			if(rs>0) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public SlotUsage findSlotUsageByUsageID(int usageID) {
	    try {
	        Connection con = DBUtil.getDBConnection();
	        PreparedStatement ps =
	            con.prepareStatement("SELECT * FROM SLOT_USAGE_TBL WHERE usageID = ?");
	        ps.setInt(1, usageID);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            SlotUsage usage = new SlotUsage();
	            usage.setUsageID(rs.getInt("usageID"));
	            usage.setBookingID(rs.getString("bookingID"));
	            usage.setUsageStatus(rs.getString("usageStatus"));
	            return usage;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

}
