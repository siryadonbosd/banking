package com.cg.banking.service;
import com.cg.banking.bean.Customer;
import com.cg.banking.dao.BankingDao;
import com.cg.banking.dao.BankingDaoJdbcImpl;
import com.cg.banking.exception.BankingException;
import com.cg.banking.util.DBUtilException;

public class BankingServiceImpl implements BankingService   {
	private BankingDao bDao;
	
	public BankingServiceImpl() {
		//bDao = new BankingDaoImpl();
		bDao = new BankingDaoJdbcImpl();
	}

	@Override
	public boolean createCustomer(Customer cust) throws DBUtilException  {		
		return bDao.createCustomer(cust);
	}

	@Override
	public double deposit(long accNo, double amount) throws BankingException, DBUtilException  {
		
		return bDao.deposit(accNo,amount);
	}

	@Override
	public double withdraw(long accNo, double amount) throws BankingException, DBUtilException  {
		
		return bDao.withdraw(accNo,amount);
	}

	@Override
	public double getBalance(long accNo) throws DBUtilException, BankingException {
		
		return bDao.getBalance(accNo);
	}

}

