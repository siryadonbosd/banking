package com.cg.banking.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.cg.banking.bean.Customer;
import com.cg.banking.exception.BankingException;
import com.cg.banking.util.DBUtil;
import com.cg.banking.util.DBUtilException;

public class BankingDaoJdbcImpl implements BankingDao {
	private static final double MIN_BALANCE = 1000;
	private String insertQuery = "insert into customer(accno, name, mobile, acctype, balance) values(?,?,?,?,?)";
	// private double balance;

	@Override
	public boolean createCustomer(Customer cust) throws DBUtilException {
		Logger logger = Logger.getLogger(BankingDaoJdbcImpl.class);
		Connection conn = DBUtil.getConnection();
		try {
			logger.info("Query"+  insertQuery);
			PreparedStatement stmt = conn.prepareStatement(insertQuery);
			stmt.setLong(1, cust.getAccNo());
			stmt.setString(2, cust.getName());
			stmt.setLong(3, cust.getMobile());
			stmt.setString(4, cust.getAccType());
			stmt.setDouble(5, MIN_BALANCE);
			int rows = stmt.executeUpdate();
			if (rows > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Error inserting data in table");
			throw new DBUtilException("Error inserting data in table");
		}
		return false;
	}

	@Override
	public double deposit(long accNo, double amount) throws BankingException, DBUtilException  {
		String selectQuery = "select balance from customer where accno=?";
		String updateQuery = "update customer set balance = ? where accno=?";
		Connection conn = DBUtil.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(selectQuery);
			stmt.setLong(1, accNo);
			ResultSet rs = stmt.executeQuery(); 
			if (rs.next()) {
				double balance = rs.getDouble(1);
				balance +=amount;
				stmt = conn.prepareStatement(updateQuery);
				stmt.setDouble(1, balance );
				stmt.setLong(2, accNo);
				int row = stmt.executeUpdate();
				if (row>0) {
					return balance;
				}
				else {
					throw new BankingException(" could not update the data");
				}
				}
			else {
				throw new BankingException(" no data found for accNo" + accNo);
			}
			
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DBUtilException("error update data in table");
			}
	}


	@Override
	public double getBalance(long accNo) throws DBUtilException, BankingException {
		String selectQuery = "select balance from customer where accNo=?";
		Connection conn = DBUtil.getConnection();
		try {
			PreparedStatement stmt = conn.prepareStatement(selectQuery);
			stmt.setLong(1, accNo);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				double balance = rs.getDouble(1);
				return balance;
			}
			else {
				throw new BankingException("no record found for accNo" + accNo);
				
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BankingException("no record found for accNo" + accNo);
		}
	}

@Override
public double withdraw(long accNo, double amount) throws BankingException, DBUtilException {
	System.out.println(accNo+", "+ amount );
	String selectQuery = "select balance from customer where accno=?";
	String updateQuery = "update customer set balance = ? where accno=?";
	Connection conn = DBUtil.getConnection();
	try {
		PreparedStatement stmt = conn.prepareStatement(selectQuery);
		stmt.setLong(1, accNo);
		ResultSet rs = stmt.executeQuery(); 
		if (rs.next()) {
			double balance = rs.getDouble(1);
			balance -=amount;
			System.out.println("balance = " +balance);
			System.out.println(updateQuery);
			stmt = conn.prepareStatement(updateQuery);
			stmt.setDouble(1, balance );
			stmt.setLong(2, accNo);
			int row = stmt.executeUpdate();
			if (row>0) {
				return balance;
			}
			else {
				throw new BankingException(" insufficient balance");
			}
			}
		else {
			throw new BankingException(" no data found for accNo" + accNo);
		}
		
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBUtilException("error update data in table");
		}
}

}
