package com.ground.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.ground.bean.GroundSlot;
import com.ground.util.DBUtil;

public class GroundSlotDAO {
	public GroundSlot findBooking(String bookingID) {
		try {
			Connection con=DBUtil.getDBConnection();
			PreparedStatement ps=con.prepareStatement("SELECT * FROM GROUND_SLOT_TBL WHERE bookingID=?");
			ps.setString(1, bookingID);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				rs.getString("bookingID");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public boolean insertBooking(GroundSlot slot) {
		try {
			Connection con=DBUtil.getDBConnection();
			PreparedStatement ps=con.prepareStatement("INSERT INTO GROUND_SLOT_TBL(bookingID,bookingDate,timeSlot,teamName,contactPersonName,contactMobile,userType,plannedSport,estimatedPlayerCount,baseSlotFee,calculatedBookingAmount,bookingStatus,createdTimestamp,cancellationReason) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, slot.getBookingID());
			ps.setDate(2,  slot.getBookingDate());
			ps.setString(3, slot.getTimeSlot());
			ps.setString(4, slot.getTeamName());
			ps.setString(5, slot.getContactPersonName());
			ps.setString(6, slot.getContactMobile());
			ps.setString(7, slot.getUserType());
			ps.setString(8, slot.getPlannedSport());
			ps.setInt(9, slot.getEstimatedPlayersCount());
			ps.setDouble(10, slot.getBaseSlotFee());
			ps.setDouble(11, slot.getCalculatedBookingAmount());
			ps.setString(12, slot.getBookingStatus());
			ps.setTimestamp(13, slot.getCreatedTimestamp());
			ps.setString(14, slot.getCancellationReason());
			int rs=ps.executeUpdate();
			if(rs>0) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean updateBookingStatusAndCancellation(String bookingID,String newStatus,String cancellationReason,Connection con) {
		try {
			con=DBUtil.getDBConnection();
			PreparedStatement ps=con.prepareStatement("UPDATE GROUND_SLOT_TBL SET bookingStatus=?,cancellationReason=? WHERE bookingID=?");
			ps.setString(1, newStatus);
			ps.setString(2, cancellationReason);
			ps.setString(3, bookingID);
			int rs=ps.executeUpdate();
			if(rs>0) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean updateCalculatedBookingAmount(String bookingID,double amount) {
		try {
			Connection con=DBUtil.getDBConnection();
			PreparedStatement ps=con.prepareStatement("UPDATE GROUND_SLOT_TBL SET calculatedBookingAmount=? WHERE bookingID=?");
			ps.setDouble(1, amount);
			ps.setString(2, bookingID);
			int rs=ps.executeUpdate();
			if(rs>0) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	public java.util.List<GroundSlot>viewBookingsByDateAndStatus(java.sql.Date bookingDate,String status){
		ArrayList<GroundSlot> list=new ArrayList<>();
		try {
			Connection con=DBUtil.getDBConnection();
			PreparedStatement ps=con.prepareStatement("SELECT * FROM GROUND_SLOT_TBL WHERE bookingDate=? AND bookingStatus=?");
			ps.setDate(1, bookingDate);
			ps.setString(2, status);
			ResultSet rs=ps.executeQuery();
			 while (rs.next()) {
		       GroundSlot g = new GroundSlot();
		       g.setBookingID(rs.getString("BOOKING_ID"));
		       g.setBookingDate(rs.getDate("BOOKING_DATE"));
		       g.setBookingStatus(rs.getString("BOOKING_STATUS"));
		       list.add(g);
		        }
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public java.util.List<GroundSlot> findConfirmedBookingsForDateAndSlot(java.sql.Date bookingDate,String timeSlot){
		ArrayList<GroundSlot> list=new ArrayList<>();
		try {
			Connection con=DBUtil.getDBConnection();
			PreparedStatement ps=con.prepareStatement("SELECT * FROM GROUND_SLOT_TBL WHERE bookingDate=? AND timeSlot=? AND bookingStatus='CONFIRMED'");
			ps.setDate(1, bookingDate);
			ps.setString(2, timeSlot);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				GroundSlot g=new GroundSlot();
				g.setBookingID(rs.getString("Booking ID"));
				g.setBookingDate(rs.getDate("Booking Date"));
				g.setTimeSlot(rs.getString("Time Slot"));
				g.setBookingStatus(rs.getString("Booking Status"));
				list.add(g);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
