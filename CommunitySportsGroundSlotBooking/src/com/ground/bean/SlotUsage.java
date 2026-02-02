package com.ground.bean;

import java.sql.Timestamp;

public class SlotUsage {
	private int usageID;
	private String bookingID;
	private String actualSportPlayed;
	private int actualPlayersCount;
	private java.sql.Timestamp actualStartTime;
	private java.sql.Timestamp actualEndTime;
	private int overtimeMinutes;
	private double overtimeChargeAmount;
	private String groundConditionRating;
	private String cleanlinessRating;
	private String caretakerRemarks;
	private String usageStatus;
	public SlotUsage(int usageID, String bookingID, String actualSportPlayed, int actualPlayersCount,
			Timestamp actualStartTime, Timestamp actualEndTime, int overtimeMinutes, double overtimeChargeAmount,
			String groundConditionRating, String cleanlinessRating, String caretakerRemarks, String usageStatus) {
		super();
		this.usageID = usageID;
		this.bookingID = bookingID;
		this.actualSportPlayed = actualSportPlayed;
		this.actualPlayersCount = actualPlayersCount;
		this.actualStartTime = actualStartTime;
		this.actualEndTime = actualEndTime;
		this.overtimeMinutes = overtimeMinutes;
		this.overtimeChargeAmount = overtimeChargeAmount;
		this.groundConditionRating = groundConditionRating;
		this.cleanlinessRating = cleanlinessRating;
		this.caretakerRemarks = caretakerRemarks;
		this.usageStatus = usageStatus;
	}
	public SlotUsage() {
		// TODO Auto-generated constructor stub
	}
	public int getUsageID() {
		return usageID;
	}
	public void setUsageID(int usageID) {
		this.usageID = usageID;
	}
	public String getBookingID() {
		return bookingID;
	}
	public void setBookingID(String bookingID) {
		this.bookingID = bookingID;
	}
	public String getActualSportPlayed() {
		return actualSportPlayed;
	}
	public void setActualSportPlayed(String actualSportPlayed) {
		this.actualSportPlayed = actualSportPlayed;
	}
	public int getActualPlayersCount() {
		return actualPlayersCount;
	}
	public void setActualPlayersCount(int actualPlayersCount) {
		this.actualPlayersCount = actualPlayersCount;
	}
	public java.sql.Timestamp getActualStartTime() {
		return actualStartTime;
	}
	public void setActualStartTime(java.sql.Timestamp actualStartTime) {
		this.actualStartTime = actualStartTime;
	}
	public java.sql.Timestamp getActualEndTime() {
		return actualEndTime;
	}
	public void setActualEndTime(java.sql.Timestamp actualEndTime) {
		this.actualEndTime = actualEndTime;
	}
	public int getOvertimeMinutes() {
		return overtimeMinutes;
	}
	public void setOvertimeMinutes(int overtimeMinutes) {
		this.overtimeMinutes = overtimeMinutes;
	}
	public double getOvertimeChargeAmount() {
		return overtimeChargeAmount;
	}
	public void setOvertimeChargeAmount(double overtimeChargeAmount) {
		this.overtimeChargeAmount = overtimeChargeAmount;
	}
	public String getGroundConditionRating() {
		return groundConditionRating;
	}
	public void setGroundConditionRating(String groundConditionRating) {
		this.groundConditionRating = groundConditionRating;
	}
	public String getCleanlinessRating() {
		return cleanlinessRating;
	}
	public void setCleanlinessRating(String cleanlinessRating) {
		this.cleanlinessRating = cleanlinessRating;
	}
	public String getCaretakerRemarks() {
		return caretakerRemarks;
	}
	public void setCaretakerRemarks(String caretakerRemarks) {
		this.caretakerRemarks = caretakerRemarks;
	}
	public String getUsageStatus() {
		return usageStatus;
	}
	public void setUsageStatus(String usageStatus) {
		this.usageStatus = usageStatus;
	}

}
