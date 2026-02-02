package com.ground.bean;

import java.sql.Date;
import java.sql.Timestamp;

public class GroundSlot {
	private String bookingID;
	private java.sql.Date bookingDate;
	private String timeSlot;
	private String teamName;
	private String contactPersonName;
	private String contactMobile;
	private String userType;
	private String plannedSport;
	private int estimatedPlayersCount;
	private double baseSlotFee;
	private double calculatedBookingAmount;
	private String bookingStatus;
	private java.sql.Timestamp createdTimestamp;
	private String cancellationReason;
	
	public GroundSlot(String bookingID, Date bookingDate, String timeSlot, String team_name, String contactPersonName,
			String contactMobile, String userType, String plannedSport, int estimatedPlayersCount, double baseSlotFee,
			double calculatedBookingAmount, String bookingStatus, Timestamp createdTimestamp,
			String cancellationReason) {
		super();
		this.bookingID = bookingID;
		this.bookingDate = bookingDate;
		this.timeSlot = timeSlot;
		this.teamName = teamName;
		this.contactPersonName = contactPersonName;
		this.contactMobile = contactMobile;
		this.userType = userType;
		this.plannedSport = plannedSport;
		this.estimatedPlayersCount = estimatedPlayersCount;
		this.baseSlotFee = baseSlotFee;
		this.calculatedBookingAmount = calculatedBookingAmount;
		this.bookingStatus = bookingStatus;
		this.createdTimestamp = createdTimestamp;
		this.cancellationReason = cancellationReason;
	}
	public GroundSlot() {
		// TODO Auto-generated constructor stub
	}
	public String getBookingID() {
		return bookingID;
	}
	public void setBookingID(String bookingID) {
		this.bookingID = bookingID;
	}
	public java.sql.Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(java.sql.Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeam_name(String teamName) {
		this.teamName = teamName;
	}
	public String getContactPersonName() {
		return contactPersonName;
	}
	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}
	public String getContactMobile() {
		return contactMobile;
	}
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getPlannedSport() {
		return plannedSport;
	}
	public void setPlannedSport(String plannedSport) {
		this.plannedSport = plannedSport;
	}
	public int getEstimatedPlayersCount() {
		return estimatedPlayersCount;
	}
	public void setEstimatedPlayersCount(int estimatedPlayersCount) {
		this.estimatedPlayersCount = estimatedPlayersCount;
	}
	public double getBaseSlotFee() {
		return baseSlotFee;
	}
	public void setBaseSlotFee(double baseSlotFee) {
		this.baseSlotFee = baseSlotFee;
	}
	public double getCalculatedBookingAmount() {
		return calculatedBookingAmount;
	}
	public void setCalculatedBookingAmount(double calculatedBookingAmount) {
		this.calculatedBookingAmount = calculatedBookingAmount;
	}
	public String getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	public java.sql.Timestamp getCreatedTimestamp() {
		return createdTimestamp;
	}
	public void setCreatedTimestamp(java.sql.Timestamp createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	public String getCancellationReason() {
		return cancellationReason;
	}
	public void setCancellationReason(String cancellationReason) {
		this.cancellationReason = cancellationReason;
	}

}
