package com.wipro.bank.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wipro.bank.bean.TransferBean;
import com.wipro.bank.util.DBUtil;
public class BankDAO {
 public int generateSequenceNumber() {
	 try {
	        Connection connection = DBUtil.getDBConnection();
	        PreparedStatement ps = connection.prepareStatement("SELECT transactionId_seq.NEXTVAL from Dual");
	        ResultSet rs = ps.executeQuery();
	        if(rs.next()) {
	          return rs.getInt(1);
	        }
	        }   
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	return 0;
 }
 public boolean validateAccount(String accountNumber) {
	    try {
	        Connection connection = DBUtil.getDBConnection();
	        PreparedStatement ps = connection.prepareStatement("SELECT 1 FROM ACCOUNT_TBL WHERE Account_Number = ?");
	        ps.setString(1, accountNumber);
	        ResultSet rs = ps.executeQuery();
	        if(rs.next())
	        	return true;
	        return false;
	    }   
	    catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
 public float findBalance(String accountNumber) {
	 if(validateAccount(accountNumber)) {
		try {
			Connection connection = DBUtil.getDBConnection();
		 PreparedStatement ps= connection.prepareStatement("SELECT Balance FROM ACCOUNT_TBL WHERE Account_Number = ?");
			ps.setString(1, accountNumber);
			 ResultSet rs = ps.executeQuery();
			 if(rs.next()) {
				 return rs.getFloat(1);
			 }
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	 }
	 return -1;
 }
 public boolean updateBalance(String accountNumber,float newBalance) {
	 try {
			Connection connection = DBUtil.getDBConnection();
		 PreparedStatement ps= connection.prepareStatement("UPDATE ACCOUNT_TBL SET Balance=? where Account_Number=?");
		ps.setFloat(1, newBalance);	
		 ps.setString(2, accountNumber);
			 int rowsupdate= ps.executeUpdate();
			 if(rowsupdate>0) {
				 return true;
			 }
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	return false;
 }
 public boolean transferMoney(TransferBean transferBean) {
	 try {
		   transferBean.setTransactionID(generateSequenceNumber());
			Connection connection = DBUtil.getDBConnection();
		 PreparedStatement ps= connection.prepareStatement("INSERT INTO TRANSFER_TBL VALUES (?,?,?,?,?)");
	ps.setInt(1,transferBean.getTransactionID());
	ps.setString(2, transferBean.getFromAccountNumber());
	ps.setString(3, transferBean.getToAccountNumber());
	Date d=new Date(transferBean.getDateOfTransaction().getTime());
	ps.setDate(4,d);
	ps.setFloat(5,transferBean.getAmount());
			 int rs= ps.executeUpdate();
			 if(rs>0) {
				 return true;
			 }
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	return false;
 }
 
}