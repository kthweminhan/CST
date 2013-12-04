package test.cst;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import prog.cst.LoginFrame;
import src.cst.Login;

public class LoginTest extends TestCase {

	public LoginTest() {
		
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void test() {
		Login lframe=new Login("Admin","admin");
		assertEquals("Admin",lframe.getUser());
		assertEquals("Admin",lframe.getPassword());
		
	}

}
