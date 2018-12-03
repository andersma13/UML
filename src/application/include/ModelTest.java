package application.include;

import static org.junit.Assert.*;

import org.junit.Test;

public class ModelTest {
	
	@Test
	public void testModel() {
		int[] data = {0,0,0,10,10};
		String[] string1 = {"This is a test #1", "This is a test #2","This is a test #3","This is a test #4"};
		Model model = new Model();
		int x = model.addClassModel(data, string1);
		
		assertTrue("The classblock is made", x == 0);
		assertTrue("Height is correct",model.getClassModel(x).getHeight() == 10);
		assertTrue("Width is correct", model.getClassModel(x).getWidth() == 10);
		assertTrue("X pos is correct", model.getClassModel(x).getXPos() == 0 );
		assertTrue("Y pos is correct", model.getClassModel(x).getYPos() == 0);
		assertTrue("Name is correct", model.getClassModel(x).getName() == "This is a test #1" );
		assertTrue("Attribute is correct", model.getClassModel(x).getAttr() == "This is a test #2");
		assertTrue("Operations is correct", model.getClassModel(x).getOper() == "This is a test #3");
		assertTrue("Description is correct", model.getClassModel(x).getDesc() == "This is a test #4");
	}

	@Test
	public void testLink() {
		int[] data = {0,0,0,10,10};
		String[] string1 = {"This is a test #1.1", "This is a test #2.1","This is a test #3.1","This is a test #4.1"};
		Model model = new Model();
		int x = model.addClassModel(data, string1);
		
		int[] data2 = {1,0,0,10,10};
		String[] string2 = {"This is a test #1.2", "This is a test #2.2","This is a test #3.2","This is a test #4.2"};
		int y = model.addClassModel(data2, string2);
		
		int[] link = {0,0,0,1,0,1,0,1};
		int z = model.addLinkModel(link,"Test link");
		
		assertTrue("The classblock 0 is made", x == 0 );
		assertTrue("The classblock 1 is made", y == 1 );
		
		assertTrue("link is made correctly", z == 0  );
		assertTrue("Link is correctly connected to box 1", model.getLinkModel(z).getDest() == 1 );
		assertTrue("link is correctly connected to box 0", model.getLinkModel(z).getSource() == 0 );
		
	}
	
	@Test
	public void testRemove() {
		int[] data = {0,0,0,10,10};
		String[] string1 = {"This is a test #1.1", "This is a test #2.1","This is a test #3.1","This is a test #4.1"};
		Model model = new Model();
		int x = model.addClassModel(data, string1);
		
		int[] data2 = {1,0,0,10,10};
		String[] string2 = {"This is a test #1.2", "This is a test #2.2","This is a test #3.2","This is a test #4.2"};
		int y = model.addClassModel(data2, string2);
		
		int[] link = {0,0,0,1,0,1,0,1};
		int z = model.addLinkModel(link,"Test link");
		
		assertTrue("Two boxes exist", model.getClassTail() == 2);
		assertTrue("One link exists", model.getLinkTail() == 1);
		model.removeClassModel(x);
		assertTrue("one box exists", model.getClassTail() == 1);
		assertTrue("No links exists", model.getLinkTail() == 0);
		
	}
	
	@Test
	public void testEdit() {
		int[] data = {0,0,0,10,10};
		String[] string = {"This is a test #1.1", "This is a test #2.1","This is a test #3.1","This is a test #4.1"};
		Model model = new Model();
		int x = model.addClassModel(data, string);
		
		model.getClassModel(x).setHeight(20);
		model.getClassModel(x).setWidth(20);
		model.getClassModel(x).setXPos(10);
		model.getClassModel(x).setYPos(10);
		model.getClassModel(x).setName("This is changed 1");
		model.getClassModel(x).setAttr("This is changed 2");
		model.getClassModel(x).setOper("This is changed 3");
		model.getClassModel(x).setDesc("This is changed 4");
		
		assertTrue("Height has been sucessfully changed",model.getClassModel(x).getHeight() == 20);
		assertTrue("Width has been sucessfully changed", model.getClassModel(x).getWidth() == 20);
		assertTrue("X pos has been sucessfully changed" , model.getClassModel(x).getXPos() == 10);
		assertTrue("Y pos has been sucessfully changed", model.getClassModel(x).getYPos() == 10);
		assertTrue("Name has been sucessfully changed", model.getClassModel(x).getName() == "This is changed 1" );
		assertTrue("Attribute has been sucessfully changed", model.getClassModel(x).getAttr() == "This is changed 2");
		assertTrue("Operations has been sucessfully changed", model.getClassModel(x).getOper() == "This is changed 3");
		assertTrue("Description has been sucessfully changed", model.getClassModel(x).getDesc() == "This is changed 4");
	}
	

}
