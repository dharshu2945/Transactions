package com.wipro.bank.util;

public class InsuffcientFundsException extends Exception{
	@Override
	public String toString()
	{
		return ("INSUFFICIENT FUNDS");
	}
}