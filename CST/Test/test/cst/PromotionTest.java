package test.cst;

import org.junit.Before;
import org.junit.Test;

import src.cst.Promotion;
import src.cst.Sale;
import junit.framework.TestCase;

public class PromotionTest extends TestCase {

	public PromotionTest(String name) {
		super(name);
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void test() {
		Promotion pm=new Promotion(1,"Key Chain","11/11/2013",100,"Summer season promotion");
		assertEquals(1,pm.getPromotion_id());
		assertEquals("Key Chain",pm.getPromotion_gift());
		assertEquals("11/11/2013",pm.getPromotion_valid_date());
		assertEquals(100,pm.getPromotion_total_amount());
		assertEquals("Summer season promotion",pm.getPromotion_description());
		
	}

}
