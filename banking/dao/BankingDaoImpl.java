package com.cg.banking.dao;

import java.util.HashMap;
import java.util.Map;

import com.cg.banking.bean.Customer;
import com.cg.banking.exception.BankingException;


public class BankingDaoImpl implements BankingDao {
	private Map<Long, Customer> custMap = new HashMap<>();
	private static final double MIN_BALANCE = 1000;
	
	@Override
	public boolean createCustomer(Customer cust) {
		Customer oldCust = custMap.get(cust.getAccNo());
		if(oldCust!=null)
			return false;
		
		custMap.put(cust.getAccNo(), cust);
		cust.setBalance(MIN_BALANCE );
		//System.out.println(custMap);
		return true;
	}

	@Override
	public double deposit(long accNo, double amount) throws BankingException {
		Customer cust = custMap.get(accNo);
		if(cust==null) {
			throw new BankingException("Account does not exist for accNo: "+accNo);
		}
		cust.setBalance(cust.getBalance()+amount);
		return cust.getBalance();
	}

	@Override
	public double withdraw(long accNo, double amount) throws BankingException {
		Customer cust = custMap.get(accNo);
		double balance = cust.getBalance();
		double temp = balance-amount;
		if(temp<MIN_BALANCE) {
			throw new BankingException("Insufficient balance in accNo: "+accNo);
		}
		balance = temp;
		cust.setBalance(balance);
		return balance;
	}

	@Override
	public double getBalance(long accNo) {
		//System.out.println(custMap);
		Customer cust = custMap.get(accNo);
		//System.out.println(cust);
		return cust.getBalance();
	}

}
