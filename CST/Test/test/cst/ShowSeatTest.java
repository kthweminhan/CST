package test.cst;

import org.junit.Before;
import org.junit.Test;

import src.cst.ShowSeat;
import junit.framework.TestCase;

public class ShowSeatTest extends TestCase {

	public ShowSeatTest(String name) {
		super(name);
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void test() {
		ShowSeat sst= new ShowSeat(1,"A001",true,"09:00:00");
		//assertEquals(1,sst.getId());
		assertEquals("A001",sst.getSeat_Number());
		//assertEquals(true,sst.getOccupied());
		assertEquals("09:00:00",sst.getShowtime());
	}

}
