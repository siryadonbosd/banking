package com.cg.banking.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.banking.bean.Customer;
import com.cg.banking.exception.BankingException;

public class BankingDaoTest {
	private BankingDaoImpl bDao = new BankingDaoImpl();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClass");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("tearDownAfterClass");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("setUp");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown");
	}

	@Test
	public void testCreateCustomer() {
		boolean success = bDao.createCustomer(new Customer(11223344L, "Mrunal", 9011223344L, "savings"));
		assertTrue(success);
	}

	@Test
	public void testDeposit() throws BankingException {
		bDao.createCustomer(new Customer(11223344L, "Mrunal", 9011223344L, "savings"));
		 double balance = bDao.deposit(11223344L, 5000);
		 assertEquals(6000, balance, 0.1);
	}

	@Test
	public void testWithdraw() throws BankingException {
		 bDao.createCustomer(new Customer(11223344L, "Mrunal", 9011223344L, "savings"));
		 bDao.deposit(11223344L, 5000);
		 double balance = bDao.withdraw(11223344L, 2500);
		 assertEquals(3500, balance, 0.1);
	}
	@Test
	public void testGetBalance() {
		bDao.createCustomer(new Customer(11223344L, "Mrunal", 9011223344L, "savings"));
		 double balance = bDao.getBalance(11223344L);
		 assertEquals(1000, balance, 0.1);
	}

}
