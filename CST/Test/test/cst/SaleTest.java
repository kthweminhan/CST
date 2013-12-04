package test.cst;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import src.cst.Login;
import src.cst.Sale;

public class SaleTest extends TestCase{

	public SaleTest() {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Sale sale=new Sale(1,1,"11/11/2013","1",10,1);
		assertEquals(1,sale.getTicketid());
		assertEquals(1,sale.getSeatid());
		assertEquals(11/11/2013,sale.getTicketdate());
		assertEquals("1",sale.getMovie());
		assertEquals(10,sale.getPrice());
		assertEquals(1,sale.getQuantity());
		
	}

}
