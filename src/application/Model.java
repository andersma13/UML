package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Model
{
	public class ClassModel 
	{
		
		/*
		 * intData:
		 * [index] [Position (x)] [Position (y)] [Width] [Height]
		 * Index is used for reference.
		 * Positions x & y are used for class block placement.  I have
		 * 	them set to automatically round any given value to the 
		 * 	nearest 10, but changing STEP will change the grid size if
		 * 	10 doesn't look right.
		 * Width and Height should be obvious as well.  They're using the
		 * 	same formula as X and Y, so they should adhere to the grid
		 * 	as well.
		 * stringData:
		 * [Name] [Attributes] [Operations] [Descriptions]
		 * The four elements in stringData should be pretty self 
		 * 	explanatory.  Depending on how we need to display the 
		 * 	data, it might be easier to have a lone string (Name) 
		 * 	and an array[3] of linked lists, so the lists of entries 
		 * 	can be expanded indefinitely.
		 */
		private int[] intData = new int[5];
		private String[] stringData = new String[4];
		private List<Integer> linksStart = new ArrayList<>();
		private List<Integer> linksEnd = new ArrayList<>();
		private final int STEP = 10;
		
		// Full constructors
		public ClassModel(int[] intsIn, String[] stringsIn)
		{
			if (intsIn.length == 5)
			{
				intData = intsIn;
			}
			
			if (stringsIn.length == 4)
			{
				stringData = stringsIn;
			}
		}

		// Setters
		public void setXPos(int x)
		{
			if(x >= 0)
			{
				intData[1] = (x % STEP < (STEP / 2) ?
						x - (x % STEP) : x + STEP - (x % STEP));
			}
			else
			{
				intData[1] = 0;
			}
		}
		
		public void setYPos(int y)
		{
			if(y >= 0)
			{
				intData[2] = (y % STEP < (STEP / 2) ?
						y - (y % STEP) : y + STEP - (y % STEP));
			}
			else
			{
				intData[2] = 0;
			}
		}
		
		public void setWidth(int w)
		{
			intData[3] = (w % STEP < (STEP / 2) ?
					w - (w % STEP) : w + STEP - (w % STEP));;
		}
		
		public void setHeight(int h)
		{
			intData[4] = (h % STEP < (STEP / 2) ?
					h - (h % STEP) : h + STEP - (h % STEP));;
		}
		
		public void setName(String n)
		{
			stringData[0] = n;
		}
		
		public void setAttr(String a)
		{
			stringData[1] = a;
		}
		
		public void setOper(String o)
		{
			stringData[2] = o;
		}
		
		public void setDesc(String d)
		{
			stringData[3] = d;
		}
		
		public void addLinkStart(int index)
		{
			linksStart.add(index);
		}
		
		public void addLinkEnd(int index)
		{
			linksEnd.add(index);
		}
		
		// Getters
		public int getIndex()
		{
			return intData[0];
		}
		
		public int getXPos()
		{
			return intData[1];
		}
		
		public int getYPos()
		{
			return intData[2];
		}
		
		public int getWidth()
		{
			return intData[3];
		}
		
		public int getHeight()
		{
			return intData[4];
		}
		
		public String getName()
		{
			return stringData[0];
		}
		
		public String getAttr()
		{
			return stringData[1];
		}
		
		public String getOper()
		{
			return stringData[2];
		}
		
		public String getDesc()
		{
			return stringData[3];
		}
		
		public int[] getInts()
		{
			return intData;
		}
		
		public String[] getStrings()
		{
			return stringData;
		}
		
		public List<Integer> getLinksStart()
		{
			return linksStart;
		}
		
		public List<Integer> getLinksEnd()
		{
			return linksEnd;
		}
		
	}
	
	public class LinkModel
	{
		/*
		 * intData:
		 * [Connection index] [Connection type] [Source] [Dest]
		 * 	[Source minimum][Source maximum] [Destination minimum] 
		 * 	[Destination Maximum]
		 * Index is used for reference
		 * Connection type denotes the type of connection:
		 * 	0 - general
		 * 	1 - association
		 * 	2 - aggregation
		 * 	3 - composition
		 * 	4 - generalization
		 * 	5 - dependency
		 * Source and Destination store the indices of the Source and
		 * 	Destination blocks respectively.
		 * Source minimum and source maximum denote the cardinality of
		 * 	the connection with the source class block (ie. 0 - 1, 0 - *).
		 * 	Use -1 (or any negative) to denote ANY (*).
		 * Destination minimum and destination maximum denote the
		 * 	cardinality of the connection with the destination block.
		 * 	Same rules apply.
		 * Label is pretty straightforward.
		 */
		private int[] intData = new int[8];
		private String label;
		
		// Full Constructors
		public LinkModel(int[] data, String l)
		{
			if (data.length == 8)
			{
				intData = data;
			}
			label = l;
		}

		public void setType(int t)
		{
			intData[1] = t;
		}
		
		public void setSource(int s)
		{
			intData[2] = s;
		}
		
		public void setDest(int d)
		{
			intData[3] = d;
		}
		
		public void setSourceMin(int s)
		{
			intData[4] = s;
		}
		
		public void setSourceMax(int s)
		{
			intData[5] = s;
		}
		
		public void setDestMin(int d)
		{
			intData[6] = d;
		}
		
		public void setDestMax(int d)
		{
			intData[7] = d;
		}
		
		public void setLabel(String l)
		{
			label = l;
		}
		
		public int getIndex() 
		{
			return intData[0];
		}
		
		public int getType()
		{
			return intData[1];
		}
		
		public int getSource()
		{
			return intData[2];
		}
		
		public int getDest()
		{
			return intData[3];
		}
		
		public int getSourceMin() 
		{
			return intData[4];
		}
		
		public int getSourceMax()
		{
			return intData[5];
		}
		
		public int getDestMin() 
		{
			return intData[6];
		}
		
		public int getDestMax()
		{
			return intData[7];
		}
		
		public String getLabel()
		{
			return label;
		}
	}	 
	
	private List<ClassModel> classList;
	private List<LinkModel> linkList;
	
	/*
	 * This class uses the classList and connectionList classes
	 * 	to represent all the elements being stored in the diagram.
	 * 	Using two separate classes to store this data will, I think, 
	 * 	make it easier to extend if we get to the point where we
	 * 	want to add additional features, as well as providing a
	 * 	relatively simple structure to dump and reload in order 
	 * 	to save and load files.
	 */
	public Model()
	{
		classList = new ArrayList<ClassModel>();
		linkList = new ArrayList<LinkModel>();
	}
	
	/**
	 * Returns the smallest index that isn't presently storing
	 * 	a ClassModel object.
	 * @return
	 * 	an int corresponding with the tail of the list
	 */
	public int getClassTail()
	{
		return classList.size();
	}
	
	public int getLinkTail()
	{
		return linkList.size();
	}
	
	public ClassModel getClass(int i)
	{
		return classList.get(i);
	}
	
	public LinkModel getLink(int i)
	{
		return linkList.get(i);
	}

	/**
	 * Creates a new ClassModel object and places it at the end
	 * 	of the list.
	 * @param ints
	 * 	A list of int arguments to be passed to the ClassModel
	 * 	constructor.
	 * @param strings
	 * 	A list of String arguments to be passed to the ClassModel
	 * 	constructor.
	 * @return
	 * 	the index of the new ClassModel object
	 */
	public int addBlock(int[] ints, String[] strings)
	{
		if(ints.length == 5 && strings.length == 4)
		{
			classList.add(new ClassModel(ints, strings));
		}
		return (classList.size() - 1);
	}
	
	/**
	 * Creates a new ConnectionModel object and places it at the end
	 * 	of the list.
	 * @param ints
	 * 	A list of int arguments to be passed to the ConnectionModel
	 * 	constructor.
	 * @param label
	 * 	The label to be passed to the ConnectionModel constructor.
	 * @return
	 * 	the index of the new ConnectionModel object
	 */
	public int addLink(int[] ints, String label)
	{
		if(ints.length == 8)
		{
			linkList.add(new LinkModel(ints, label));
		}
		return (linkList.size() - 1);
	}
	
	/**
	 * Generates a ClassBlock object based on the ClassModel
	 * 	stored at index i.
	 * @param i
	 * 	The index of the ClassModel object to be generated.
	 * @return
	 * 	a ClassBlock with the parameters stored at index i
	 */
	public ClassBlock generateClassBlock(int i)
	{
		return new ClassBlock(classList.get(i).getInts(), 
				classList.get(i).getStrings());
	}
	
	/**
	 * Saves the model data in a format that can be reread later.
	 * @param filename
	 * 	The file to be written to.
	 * @throws IOException
	 * 	Throws if the file can't be written to.
	 */
	public void save(String filename) throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		
		writer.write("CLASSLIST_START\n");
		writer.write(classList.size() + "\n");
		for(int i = 0; i != classList.size(); ++i)
		{
			writer.write(classList.get(i).getIndex() + "\n");
			writer.write(classList.get(i).getXPos() + "\n");
			writer.write(classList.get(i).getYPos() + "\n");
			writer.write(classList.get(i).getWidth() + "\n");
			writer.write(classList.get(i).getHeight() + "\n");
			writer.write(classList.get(i).getName() + "\n\n");
			writer.write(classList.get(i).getAttr() + "\n\n");
			writer.write(classList.get(i).getOper() + "\n\n");
			writer.write(classList.get(i).getDesc() + "\n\n");
		}
		writer.write("CLASSLIST_END\n");
		writer.write("LINKLIST_BEGIN\n");
		writer.write(linkList.size() + "\n");
		for(int i = 0; i != linkList.size(); ++i)
		{
			writer.write(linkList.get(i).getIndex() + "\n");
			writer.write(linkList.get(i).getType() + "\n");
			writer.write(linkList.get(i).getSource() + "\n");
			writer.write(linkList.get(i).getDest() + "\n");
			writer.write(linkList.get(i).getSourceMin() + "\n");
			writer.write(linkList.get(i).getSourceMax() + "\n");
			writer.write(linkList.get(i).getDestMin() + "\n");
			writer.write(linkList.get(i).getDestMax() + "\n");
			writer.write(linkList.get(i).getLabel() + "\n");
		}
		writer.write("LINKLIST_END\n");
		
		writer.close();
	}
	
	/**
	 * Reads in the model data and rebuilds the model.
	 * @param filename
	 * 	The file to be read from.
	 * @throws IOException
	 * 	Throws if the file can't be read from.
	 */
	public void load(String filename) throws IOException
	{
		Scanner reader = new Scanner(new File(filename));
		reader.next();
		
		int size = Integer.parseInt(reader.next().trim());
		for(int i = 0; i != size; ++i)
		{
			
			reader.useDelimiter("\n");
			
			int[] ints = {Integer.parseInt(reader.next().trim()),
					Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()),
					Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim())};
			reader.useDelimiter("\n\n");
			String[] strings = {reader.next().trim(), reader.next(), reader.next(), reader.next()};
			classList.add(new ClassModel(ints, strings));
			reader.useDelimiter("\n");
			reader.next();
			
		}
		
		reader.next();
		reader.next();
		
		// From here down will almost certainly need to be rewritten once links are implemented.
		size = Integer.parseInt(reader.next().trim());
		for(int i = 0; i != size; ++i)
		{
			int[] ints = {
					Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()),
					Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()),
					Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim()),
					Integer.parseInt(reader.next().trim()), Integer.parseInt(reader.next().trim())};
			String label = reader.next().trim();
			
			linkList.add(new LinkModel(ints, label));
			reader.next();
		}
		
		reader.close();
	}
	
	/**
	 * Clears the model of all data.
	 */
	public void clear()
	{
		classList.clear();
		linkList.clear();
	}
	
	public String[] getStringData()
	{
		return this.getStringData();
	}
	
	// generateConnection(i)
	public void removeBlock(int i, boolean removeLines)
	{
		classList.remove(i);
		
		if (removeLines)
		{
			//	Goes through each link and looks for references to given ClassBlock
			for (int l = 0; l < linkList.size(); ++l)
			{
				if (linkList.get(l).getSource() == i || linkList.get(l).getDest() == i)
				{
					linkList.remove(l);
					--l; //	This was necessary because if a link got removed, the size changed
				}
			}
		}
	}

}
