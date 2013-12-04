package test.cst;

import org.junit.Before;
import org.junit.Test;

import src.cst.SaleMember;
import src.cst.ShowMovie;
import junit.framework.TestCase;

public class ShowMovieTest extends TestCase {

	public ShowMovieTest(String name) {
		super(name);
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void test() {
		ShowMovie sm=new ShowMovie("Iron Man3","J Joe","M Sche","Elizbeth","Action","100","Coming Soon");
		assertEquals("Iron Man3",sm.getName());
		assertEquals("J Joe",sm.getDirector());
		assertEquals("M Sche",sm.getActor());
		assertEquals("Elizbeth",sm.getActress());
		assertEquals("Action",sm.getType());
		assertEquals("100",sm.getPrice());
		assertEquals("Coming Soon",sm.getDescription());
	}

}
