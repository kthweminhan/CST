package test.cst;


import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTest extends TestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.totalbeginner.tutorial");
		//$JUnit-BEGIN$
		suite.addTestSuite(LoginTest.class);
		suite.addTestSuite(SaleTest.class);
		suite.addTestSuite(PromotionTest.class);
		suite.addTestSuite(SaleMemberTest.class);
		suite.addTestSuite(ShowMovieTest.class);
		//$JUnit-END$
		return suite;
		}

}
