package test.cst;

import org.junit.Before;
import org.junit.Test;

import src.cst.Promotion;
import src.cst.SaleMember;
import junit.framework.TestCase;

public class SaleMemberTest extends TestCase {

	public SaleMemberTest(String name) {
		super(name);
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void test() {
		SaleMember sm=new SaleMember(1,"Kay Thwe Min Han","Germany",0176363636,200);
		assertEquals(1,sm.getMemberId());
		assertEquals("Kay Thwe Min Han",sm.getMemberName());
		assertEquals("Germany",sm.getAddress());
		assertEquals(0176363636,sm.getPhone());
		assertEquals(200,sm.getTotal());
	}

}
