package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Model {
	public class ClassModel {

		/*
		 * intData: [index] [Position (x)] [Position (y)] [Width] [Height] Index is used
		 * for reference. Positions x & y are used for class block placement. I have
		 * them set to automatically round any given value to the nearest 10, but
		 * changing STEP will change the grid size if 10 doesn't look right. Width and
		 * Height should be obvious as well. They're using the same formula as X and Y,
		 * so they should adhere to the grid as well. stringData: [Name] [Attributes]
		 * [Operations] [Descriptions] The four elements in stringData should be pretty
		 * self explanatory. Depending on how we need to display the data, it might be
		 * easier to have a lone string (Name) and an array[3] of linked lists, so the
		 * lists of entries can be expanded indefinitely.
		 */
		private int[] intData = new int[5];
		private String[] stringData = new String[4];
		private final int STEP = 10;

		/**
		 * Constructs an instance of ClassModel
		 * 
		 * @constructor
		 * @param intsIn
		 *            the array of ints to be stored in the model
		 * @param stringsIn
		 *            the array of Strings to be stored in the model
		 */
		public ClassModel(int[] intsIn, String[] stringsIn) {
			if (intsIn.length == 5) {
				intData = intsIn;
			}

			if (stringsIn.length == 4) {
				stringData = stringsIn;
			}
		}

		/*****************************
		 * SETTERS
		 ****************************/

		/**
		 * Sets the index value of the ClassModel
		 * 
		 * @param i
		 *            the index to be stored
		 */
		public void setIndex(int i) {
			intData[0] = i;
		}

		/**
		 * Sets the x position value of the ClassModel
		 * 
		 * @param x
		 *            the x position value to be stored
		 */
		public void setXPos(int x) {
			if (x >= 0) {
				intData[1] = (x % STEP < (STEP / 2) ? x - (x % STEP) : x + STEP - (x % STEP));
			} else {
				intData[1] = 0;
			}
		}

		/**
		 * Sets the y position value of the ClassModel
		 * 
		 * @param y
		 *            the y position value to be stored
		 */
		public void setYPos(int y) {
			if (y >= 0) {
				intData[2] = (y % STEP < (STEP / 2) ? y - (y % STEP) : y + STEP - (y % STEP));
			} else {
				intData[2] = 0;
			}
		}

		/**
		 * Sets the width value of the ClassModel
		 * 
		 * @param w
		 *            the width value to be stored
		 */
		public void setWidth(int w) {
			intData[3] = (w % STEP < (STEP / 2) ? w - (w % STEP) : w + STEP - (w % STEP));
		}

		/**
		 * Sets the height value of the ClassModel
		 * 
		 * @param h
		 *            the height value to be stored
		 */
		public void setHeight(int h) {
			intData[4] = (h % STEP < (STEP / 2) ? h - (h % STEP) : h + STEP - (h % STEP));
		}

		/**
		 * Sets the name value of the ClassModel
		 * 
		 * @param n
		 *            the name value to be stored
		 */
		public void setName(String n) {
			stringData[0] = n;
		}

		/**
		 * Sets the attributes value of the ClassModel
		 * 
		 * @param a
		 *            the attributes value to be stored
		 */
		public void setAttr(String a) {
			stringData[1] = a;
		}

		/**
		 * Sets the operations value of the ClassModel
		 * 
		 * @param o
		 *            the operations valie to be stored
		 */
		public void setOper(String o) {
			stringData[2] = o;
		}

		/**
		 * Sets the description value of the ClassModel
		 * 
		 * @param d
		 *            the desctiption model to be stored
		 */
		public void setDesc(String d) {
			stringData[3] = d;
		}

		/*****************************
		 * GETTERS
		 ****************************/

		/**
		 * Returns the index value of the ClassModel
		 * 
		 * @return the index value of the ClassModel
		 */
		public int getIndex() {
			return intData[0];
		}

		/**
		 * Returns the x position value of the ClassModel
		 * 
		 * @return the x position value of the ClassModel
		 */
		public int getXPos() {
			return intData[1];
		}

		/**
		 * Returns the y position value of the ClassModel
		 * 
		 * @return the y position value of the ClassModel
		 */
		public int getYPos() {
			return intData[2];
		}

		/**
		 * Returns the width value of the ClassModel
		 * 
		 * @return the width value of the ClassModel
		 */
		public int getWidth() {
			return intData[3];
		}

		/**
		 * Returns the height value of the ClassModel
		 * 
		 * @return the height value of the ClassModel
		 */
		public int getHeight() {
			return intData[4];
		}

		/**
		 * Returns the name value of the ClassModel
		 * 
		 * @return the name value of the ClassModel
		 */
		public String getName() {
			return stringData[0];
		}

		/**
		 * Returns the attributes value of the ClassModel
		 * 
		 * @return the attributes value of the ClassModel
		 */
		public String getAttr() {
			return stringData[1];
		}

		/**
		 * Returns the operations value of the ClassModel
		 * 
		 * @return the operations value of the ClassModel
		 */
		public String getOper() {
			return stringData[2];
		}

		/**
		 * Returns the description value of the ClassModel
		 * 
		 * @return the description value of the ClassModel
		 */
		public String getDesc() {
			return stringData[3];
		}

		/**
		 * Returns the full array of integral data in the ClassModel
		 * 
		 * @return the full array of integral data in the ClassModel
		 */
		public int[] getInts() {
			return intData;
		}

		/**
		 * Returns the full array of String data in the ClassModel
		 * 
		 * @return the full array of String data in the ClassModel
		 */
		public String[] getStrings() {
			return stringData;
		}

	}

	public class LinkModel {
		/*
		 * intData: [Connection index] [Connection type] [Source] [Dest] [Source
		 * minimum][Source maximum] [Destination minimum] [Destination Maximum] Index is
		 * used for reference Connection type denotes the type of connection: 0 -
		 * general 1 - association 2 - aggregation 3 - composition 4 - generalization 5
		 * - dependency Source and Destination store the indices of the Source and
		 * Destination blocks respectively. Source minimum and source maximum denote the
		 * cardinality of the connection with the source class block (ie. 0 - 1, 0 - *).
		 * Use -1 (or any negative) to denote ANY (*). Destination minimum and
		 * destination maximum denote the cardinality of the connection with the
		 * destination block. Same rules apply. Label is pretty straightforward.
		 */
		private int[] intData = new int[8];
		private String label;

		/**
		 * Constructs an instance of LinkModel
		 * 
		 * @constructor
		 * @param data
		 *            the integral data to be stored
		 * @param l
		 *            the label to be stored
		 */
		public LinkModel(int[] data, String l) {
			if (data.length == 8) {
				intData = data;
			}
			label = l;
		}

		/*****************************
		 * SETTERS
		 ****************************/

		/**
		 * Sets the index value of the LinkModel
		 * 
		 * @param i
		 *            the index value to be stored
		 */
		public void setIndex(int i) {
			intData[0] = i;
		}

		/**
		 * Sets the type value of the LinkModel
		 * 
		 * @param t
		 *            the type value to be stored
		 */
		public void setType(int t) {
			intData[1] = t;
		}

		/**
		 * Sets the source index of the LinkModel
		 * 
		 * @param s
		 *            the source index to be stored
		 */
		public void setSource(int s) {
			intData[2] = s;
		}

		/**
		 * Sets the destination index of the LinkModel
		 * 
		 * @param d
		 *            the destination index to be stored
		 */
		public void setDest(int d) {
			intData[3] = d;
		}

		/**
		 * Sets the source minimum value of the LinkModel
		 * 
		 * @param s
		 *            the source minimum value to be stored
		 */
		public void setSourceMin(int s) {
			intData[4] = s;
		}

		/**
		 * Sets the source maximum value of the LinkModel
		 * 
		 * @param s
		 *            the source maximum value to be stored
		 */
		public void setSourceMax(int s) {
			intData[5] = s;
		}

		/**
		 * Sets the destination minimum value of the LinkModel
		 * 
		 * @param d
		 *            the destination minimum value to be stored
		 */
		public void setDestMin(int d) {
			intData[6] = d;
		}

		/**
		 * Sets the destination maximum value of the LinkModel
		 * 
		 * @param d
		 *            the destination maximum value to be stored
		 */
		public void setDestMax(int d) {
			intData[7] = d;
		}

		/**
		 * Sets the label of the LinkModel
		 * 
		 * @param l
		 *            the label to be stored
		 */
		public void setLabel(String l) {
			label = l;
		}

		/*****************************
		 * GETTERS
		 ****************************/

		/**
		 * Returns the index value of the LinkModel
		 * 
		 * @return the index value of the LinkModel
		 */
		public int getIndex() {
			return intData[0];
		}

		/**
		 * Returns the type value of the LinkModel
		 * 
		 * @return the type value of the LinkModel
		 */
		public int getType() {
			return intData[1];
		}

		/**
		 * Returns the source index of the LinkModel
		 * 
		 * @return the source index of the LinkModel
		 */
		public int getSource() {
			return intData[2];
		}

		/**
		 * Returns the destination index of the LinkModel
		 * 
		 * @return the destination index of the LinkModel
		 */
		public int getDest() {
			return intData[3];
		}

		/**
		 * Returns the source minimum value of the LinkModel
		 * 
		 * @return the source minimum value of the LinkModel
		 */
		public int getSourceMin() {
			return intData[4];
		}

		/**
		 * Returns the source maximum value of the LinkModel
		 * 
		 * @return the source maximum value of the LinkModel
		 */
		public int getSourceMax() {
			return intData[5];
		}

		/**
		 * Returns the destination minimum value of the LinkModel
		 * 
		 * @return the destination minimum value of the LinkModel
		 */
		public int getDestMin() {
			return intData[6];
		}

		/**
		 * Returns the destination maximum value of the LinkModel
		 * 
		 * @return the destination maximum value of the LinkModel
		 */
		public int getDestMax() {
			return intData[7];
		}

		/**
		 * Returns the label of the LinkModel
		 * 
		 * @return the label of the LinkModel
		 */
		public String getLabel() {
			return label;
		}
	}

	private List<ClassModel> classList;
	private List<LinkModel> linkList;

	/*
	 * This class uses the classList and connectionList classes to represent all the
	 * elements being stored in the diagram. Using two separate classes to store
	 * this data will, I think, make it easier to extend if we get to the point
	 * where we want to add additional features, as well as providing a relatively
	 * simple structure to dump and reload in order to save and load files.
	 */

	/**
	 * Constructs an instance of Model
	 * 
	 * @constructor
	 */
	public Model() {
		classList = new ArrayList<ClassModel>();
		linkList = new ArrayList<LinkModel>();
	}

	/**
	 * Returns the smallest index that isn't presently storing a ClassModel object.
	 * 
	 * @return an int corresponding with the tail of the list
	 */
	public int getClassTail() {
		return classList.size();
	}

	/**
	 * Returns the smallest index that isn't presently storing a LinkModel object,
	 * 
	 * @return an int corresponding with the tail of the list
	 */
	public int getLinkTail() {
		return linkList.size();
	}

	/**
	 * Returns the ClassModel object stored at index i
	 * 
	 * @param i
	 *            the index of the ClassModel object to be returned
	 * @return the ClassModel object stored at index i
	 */
	public ClassModel getClass(int i) {
		return classList.get(i);
	}

	/**
	 * Returns the LinkModel object stored at index i
	 * 
	 * @param i
	 *            the index of the LinkModel object to be returned
	 * @return the LinkModel object stored at index i
	 */
	public LinkModel getLink(int i) {
		return linkList.get(i);
	}

	/**
	 * Creates a new ClassModel object and places it at the end of the list.
	 * 
	 * @param ints
	 *            A list of int arguments to be passed to the ClassModel
	 *            constructor.
	 * @param strings
	 *            A list of String arguments to be passed to the ClassModel
	 *            constructor.
	 * @return the index of the new ClassModel object
	 */
	public int addBlock(int[] ints, String[] strings) {
		if (ints.length == 5 && strings.length == 4) {
			classList.add(new ClassModel(ints, strings));
		}
		return (classList.size() - 1);
	}

	/**
	 * Creates a new ConnectionModel object and places it at the end of the list.
	 * 
	 * @param ints
	 *            A list of int arguments to be passed to the ConnectionModel
	 *            constructor.
	 * @param label
	 *            The label to be passed to the ConnectionModel constructor.
	 * @return the index of the new ConnectionModel object
	 */
	public int addLink(int[] ints, String label) {
		if (ints.length == 8) {
			linkList.add(new LinkModel(ints, label));
		}
		return (linkList.size() - 1);
	}

	/**
	 * Generates a ClassBlock object based on the ClassModel stored at index i.
	 * 
	 * @param i
	 *            The index of the ClassModel object to be generated.
	 * @return a ClassBlock with the parameters stored at index i
	 */
	public ClassBlock generateClassBlock(int i) {
		return new ClassBlock(classList.get(i).getInts(), classList.get(i).getStrings());
	}

	/**
	 * Saves the model data in a format that can be reread later.
	 * 
	 * @param filename
	 *            The file to be written to.
	 * @throws IOException
	 *             Throws if the file can't be written to.
	 */
	public void save(String filename) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

		writer.write("CLASSLIST_START\n");
		writer.write(classList.size() + "\n");
		for (int i = 0; i != classList.size(); ++i) {
			writer.write(classList.get(i).getIndex() + " ");
			writer.write(classList.get(i).getXPos() + " ");
			writer.write(classList.get(i).getYPos() + " ");
			writer.write(classList.get(i).getWidth() + " ");
			writer.write(classList.get(i).getHeight() + " \n");
			writer.write(classList.get(i).getName() + "\n\n");
			writer.write(classList.get(i).getAttr() + "\n\n");
			writer.write(classList.get(i).getOper() + "\n\n");
			writer.write(classList.get(i).getDesc() + "\n\n");
		}
		writer.write("CLASSLIST_END\n");
		writer.write("LINKLIST_BEGIN\n");
		writer.write(linkList.size() + "\n");
		for (int i = 0; i != linkList.size(); ++i) {
			writer.write(linkList.get(i).getIndex() + " ");
			writer.write(linkList.get(i).getType() + " ");
			writer.write(linkList.get(i).getSource() + " ");
			writer.write(linkList.get(i).getDest() + " ");
			writer.write(linkList.get(i).getSourceMin() + " ");
			writer.write(linkList.get(i).getSourceMax() + " ");
			writer.write(linkList.get(i).getDestMin() + " ");
			writer.write(linkList.get(i).getDestMax() + " \n");
			writer.write(linkList.get(i).getLabel() + "\n");
		}
		writer.write("LINKLIST_END\n");

		writer.close();
	}

	/**
	 * Reads in the model data and rebuilds the model.
	 * 
	 * @param filename
	 *            The file to be read from.
	 * @throws IOException
	 *             Throws if the file can't be read from.
	 */
	public void load(String filename) throws IOException {
		Scanner reader = new Scanner(new File(filename));
		reader.next();

		int size = Integer.parseInt(reader.next().trim());
		for (int i = 0; i != size; ++i) {

			reader.useDelimiter(" ");

			int[] ints = { Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()),
					Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()),
					Integer.parseInt(reader.next().trim()) };
			reader.useDelimiter("\n\n");
			String[] strings = { reader.next().trim(), reader.next(), reader.next(), reader.next() };
			classList.add(new ClassModel(ints, strings));
			reader.useDelimiter("\n");
			reader.next();

		}

		reader.next();
		reader.next();

		// From here down will almost certainly need to be rewritten once links are
		// implemented.
		size = Integer.parseInt(reader.next().trim());
		for (int i = 0; i != size; ++i) {
			reader.useDelimiter(" ");
			int[] ints = { Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()),
					Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()),
					Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()),
					Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()) };
			reader.useDelimiter("\n");
			reader.next();
			String label = reader.next().trim();

			linkList.add(new LinkModel(ints, label));
		}

		reader.close();
	}

	/**
	 * Clears the model of all data.
	 */
	public void clear() {
		classList.clear();
		linkList.clear();
	}

	/**
	 * Removes the ClassModel object stored at index i and updates the linkList to
	 * reflect the index changes.
	 * 
	 * @param i
	 *            the index of the ClassModel to be removed
	 */
	public void removeBlock(int i) {

		// Remove block i
		classList.remove(i);

		int bound = linkList.size();

		// Clear links
		for (int l = 0; l != bound; ++l) {
			if (linkList.get(l).getSource() == i || linkList.get(l).getDest() == i) {
				linkList.remove(l);
				--l;
				--bound;
			} else {
				if (linkList.get(l).getSource() > i) {
					linkList.get(l).setSource(linkList.get(l).getSource() - 1);
				}
				if (linkList.get(l).getDest() > i) {
					linkList.get(l).setDest(linkList.get(l).getDest() - 1);
				}
			}
		}

		// Update remaining block indices
		for (int l = i; l != classList.size(); ++l) {
			classList.get(l).setIndex(l);
		}
	}
}
