package com.ground.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ground.bean.GroundSlot;
import com.ground.dao.GroundSlotDAO;
import com.ground.dao.SlotUsageDAO;
import com.ground.bean.SlotUsage;
import com.ground.util.DBUtil;
import com.ground.util.SlotOverlapException;
import com.ground.util.UsageAlreadyExistsException;
import com.ground.util.ValidationException;

public class GroundService {
	GroundSlotDAO d=new GroundSlotDAO(); 
	SlotUsageDAO s=new SlotUsageDAO();
	 public GroundSlot findBooking(String bookingID) {
	        if (bookingID==null || bookingID.isEmpty()) {
	            return null;
	        }
	        return d.findBooking(bookingID);
	    }
	 
	 public java.util.List<GroundSlot> viewBookingsByDateAndStatus(java.sql.Date bookingDate,String status){
		 return d.viewBookingsByDateAndStatus(bookingDate, status);
	 }
	 
	 public boolean registerNewBooking(GroundSlot slot,double residentMultiplier,double guestMultiplier,double externalMultiplier) throws ValidationException{
		 if(slot.getBookingID() == null || slot.getBookingID().isEmpty()||
			    slot.getTimeSlot() == null || slot.getTimeSlot().isEmpty()||
			    slot.getTeamName() == null || slot.getTeamName().isEmpty()||
			    slot.getContactPersonName() == null || slot.getContactPersonName().isEmpty()||
			    slot.getContactMobile() == null || slot.getContactMobile().isEmpty()||
			    slot.getUserType() == null || slot.getUserType().isEmpty()||
			    slot.getPlannedSport() == null || slot.getPlannedSport().isEmpty()||
			    slot.getBookingDate() == null||
			    slot.getCreatedTimestamp() == null) {
			 throw new ValidationException();
		 }
		 
		 if(slot.getEstimatedPlayersCount()<=0) {
			 throw new ValidationException();
		 }
		 
		 double multiplier;
		 switch(slot.getUserType()) {
		 case "RESIDENT":
			 multiplier=residentMultiplier;
			 break;
		 case "GUEST_OF_RESIDENT":
			 multiplier=guestMultiplier;
			 break;
		 case "EXTERNAL_TEAM":
			 multiplier=externalMultiplier;
			 break;
		default:
			throw new ValidationException();
		 }
		 
		 if(slot.getBaseSlotFee()<0) {
			 throw new ValidationException();
		 }
		 
		 double caluculatedAmount=slot.getBaseSlotFee()*multiplier;
		 slot.setCalculatedBookingAmount(caluculatedAmount);
		 
		 if(slot.getBookingStatus()==null||slot.getBookingStatus().isEmpty()) {
			 slot.setBookingStatus("PENDING");
		 }
		 try {
			boolean inserted = d.insertBooking(slot);
			if(inserted) {
				d.updateCalculatedBookingAmount(slot.getBookingID(),slot.getCalculatedBookingAmount());
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		 
	 }
	 
	 public boolean confirmBooking(String bookingID) throws ValidationException, SlotOverlapException {
		    if (bookingID == null || bookingID.isEmpty()) throw new ValidationException();

		    GroundSlot booking = d.findBooking(bookingID);
		    if (booking == null) return false;

		    if ("CONFIRMED".equalsIgnoreCase(booking.getBookingStatus())) return true;
		    if (!"PENDING".equalsIgnoreCase(booking.getBookingStatus())) throw new ValidationException();

		    List<GroundSlot> overlapping = d.findConfirmedBookingsForDateAndSlot(booking.getBookingDate(), booking.getTimeSlot());
		    if (!overlapping.isEmpty()) throw new SlotOverlapException();

		    Connection con = null;
		    try {
		        con = DBUtil.getDBConnection();
		        con.setAutoCommit(false);
		        
		        d.updateBookingStatusAndCancellation(bookingID, "CONFIRMED", null,con);

		        con.commit();
		        return true;
		    } catch (Exception e) {
		        if (con != null) {
		            try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
		        }
		        e.printStackTrace();
		        return false;
		    } finally {
		        if (con != null) {
		            try { con.close(); } catch (SQLException ex) { ex.printStackTrace(); }
		        }
		    }
		}

	 public boolean cancelBooking(String bookingID,String reason) throws ValidationException{
		 if(bookingID==null|| bookingID.isEmpty()||reason==null||reason.isEmpty()){
			 throw new ValidationException();
		 }
		 
		 GroundSlot booking=d.findBooking(bookingID);
		 if(booking==null) {
			 return false;
		 }
		 
		 String status=booking.getBookingStatus();
		 if("CANCELLED".equalsIgnoreCase(status)||"COMPLETED".equalsIgnoreCase(status)) {
			 throw new ValidationException();
		 }
		 
		 return d.updateBookingStatusAndCancellation(bookingID ,"CANCELLED", reason, null);
	 }
	 
	 public boolean recordSlotUsage(String bookingID,String actualSport,int actualPlayers,java.sql.Timestamp startTime,java.sql.Timestamp endTime,int overtimeMinutes,double overtimeRatePerMinutes,String groundCondition,String cleanliness,String remarks) throws ValidationException,UsageAlreadyExistsException{
		 if(bookingID==null||bookingID.isEmpty() || 
				 actualSport==null||actualSport.isEmpty() ||
				 startTime==null|| endTime==null ||endTime.before(startTime) ||
				 groundCondition==null||groundCondition.isEmpty() ||
				 cleanliness==null||cleanliness.isEmpty()) {
			 throw new ValidationException();
		 }
		 
		 if(actualPlayers<0|| overtimeMinutes<0 ||overtimeRatePerMinutes <0) {
			 throw new ValidationException();
		 }
		 
		 GroundSlot booking=d.findBooking(bookingID);
		 if(booking==null) {
			 return false;
		 }
		 
		 if (!"CONFIRMED".equalsIgnoreCase(booking.getBookingStatus())) {
		        throw new ValidationException();
		    }
		 
		 SlotUsage existingUsage = s.findSlotUsageByBooking(bookingID);
		    if (existingUsage != null &&
		            ("SUBMITTED".equalsIgnoreCase(existingUsage.getUsageStatus()) ||
		             "ADJUSTED".equalsIgnoreCase(existingUsage.getUsageStatus()))) {
		        throw new UsageAlreadyExistsException();
		    }
		    
		    double overtimeChargeAmount=overtimeMinutes*overtimeRatePerMinutes;
		    
		    try {
		    	Connection con=DBUtil.getDBConnection();
		    	con.setAutoCommit(false);
		    
		    int usageID=s.generateUsageID();
		    SlotUsage usage = new SlotUsage();
		    usage.setUsageID(usageID);
		    usage.setBookingID(bookingID);
		    usage.setActualSportPlayed(actualSport);
		    usage.setActualPlayersCount(actualPlayers);
		    usage.setActualStartTime(startTime);
		    usage.setActualEndTime(endTime);
		    usage.setUsageStatus("SUBMITTED");
		    usage.setOvertimeMinutes(overtimeMinutes);
		    usage.setOvertimeChargeAmount(overtimeChargeAmount);
		    usage.setGroundConditionRating(groundCondition);
		    usage.setCleanlinessRating(cleanliness);
		    usage.setCaretakerRemarks(remarks);
		    s.insertSlotUsage(usage);
		    d.updateBookingStatusAndCancellation(bookingID, "COMPLETED",booking.getCancellationReason(),con);
		    con.commit();
		    return true;
		    }catch (Exception e) {
		    	e.printStackTrace();
			}
		    
			return false;
	 }
	 
	 public boolean adjustSlotUsage(int usageID,int newActualPlayers,int newovertimeMinutes,double newOvertimeRatePerMinute,String newGroundCondition,String newCleanliness,String newRemarks) throws ValidationException{
		    if (usageID <= 0 || newActualPlayers <= 0 ||
		        newovertimeMinutes < 0 || newOvertimeRatePerMinute < 0) {
		        throw new ValidationException();
		    }

		    if (newGroundCondition == null || newGroundCondition.isEmpty() ||
		        newCleanliness == null || newCleanliness.isEmpty()) {
		        throw new ValidationException();
		    }

		    SlotUsage usage = s.findSlotUsageByUsageID(usageID);
		    if (usage == null) {
		        return false;
		    }

		    double overtimeChargeAmount =
		            newovertimeMinutes * newOvertimeRatePerMinute;

		    usage.setActualPlayersCount(newActualPlayers);
		    usage.setOvertimeMinutes(newovertimeMinutes);
		    usage.setOvertimeChargeAmount(overtimeChargeAmount);
		    usage.setGroundConditionRating(newGroundCondition);
		    usage.setCleanlinessRating(newCleanliness);
		    usage.setCaretakerRemarks(newRemarks);
		    usage.setUsageStatus("ADJUSTED");


		    return s.updateSlotUsage(usage);
		}

	 public List<SlotUsage> listUsageByDate(java.sql.Date bookingDate) {
		    return s.findUsageByDate(bookingDate);
		}
	 
	 public List<SlotUsage> listUsageBySport(String sportName) {
		    return s.findUsageBySport(sportName);
		}


	 }
