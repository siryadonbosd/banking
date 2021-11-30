package com.cg.banking.dao;
import com.cg.banking.bean.Customer;
import com.cg.banking.exception.BankingException;
import com.cg.banking.util.DBUtilException;

public interface BankingDao {

	boolean createCustomer(Customer cust) throws DBUtilException  ;

	double deposit(long accNo, double amount) throws BankingException, DBUtilException ;

	double withdraw(long accNo, double amount) throws BankingException, DBUtilException;

	double getBalance(long accNo) throws DBUtilException, BankingException;

}
