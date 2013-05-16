import org.junit.*;
import java.util.*;

import play.db.jpa.JPABase;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}

	@Test
	public void aVeryImportantThingToTest() {
		assertEquals(2, 1 + 1);
	}

	@Test
	public void createAndRetrieveItem() {
		// Create a new item and save it
		Item test = new Item(99L, "E", "TestItem");
		test.save();

		// Retrieve the item with name
		Item bob = Item.find("name", "TestItem").first();

		// Test
		assertNotNull(bob);
		assertEquals("E", bob.getType());
	}

	@Test
	public void createItemWithOrder() {

		Item test = new Item(9L, "EL", "TestItem");
		test.save();	
		Item bob = Item.find("name", "TestItem").first();

		assertNotNull(bob.getId());
		assertEquals(String.valueOf(9L), String.valueOf(bob.getId()));
		
		Order order = new Order(122L, bob);
		assertNotNull(order.getItem());
		order.save();

		Order find = Order.findById(122L);
		Item found = find.getItem();

		assertNotNull(find);
		assertNotNull(found);
		assertEquals("TestItem", found.getName());
	}

}
