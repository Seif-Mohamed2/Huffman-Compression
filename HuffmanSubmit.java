// Auther: Seif Mohamed 

// A some algorithms were taken from the textbook Alogirthms by Robert Sedgewick and Kevin Wayne


import java.io.File;
import java.io.FileWriter;   
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;



public class HuffmanSubmit implements Huffman {
  
	private static class Node implements Comparable<Node> {
		private char ch; 
		private int freq; 
		private final Node left, right;
			
		//The node class used to create the trie for huffman algorithm 
		//each node character representing the node, frequency of this character, left and right child.  
		Node(char ch, int freq, Node left, Node right)
			{
				this.ch = ch;
				this.freq = freq;
				this.left = left;
				this.right = right;
			}

		//Method to check if this node is leaf or not	
		public boolean isLeaf() {
			return left == null && right == null;
		}
			
			public int compareTo(Node that){ 

				return this.freq - that.freq; 
			}
		}

		//the main method to build a trie from frequency array
		private static Node buildTrie(int[] freq) {
	
			MinPQ<Node> pq = new MinPQ<Node>(256);
			for (char c = 0; c < 256; c++)
				if (freq[c] > 0)
					pq.insert(new Node(c, freq[c], null, null));
			while (pq.size() > 1){ 
				Node x = pq.delMin();
				Node y = pq.delMin();
				Node parent = new Node('\0', x.freq + y.freq, x, y);
				pq.insert(parent);
			}
		return pq.delMin();
		}


		//class implementing priority queue to build the trie
		private static class MinPQ<Key extends Comparable<Key>> {
			private Key[] pq; 
			private int N = 0; 
			public MinPQ(int MinN){ 
				pq = (Key[]) new Comparable[MinN+1]; }

				public MinPQ(){ 
				}
			public boolean isEmpty(){ 
				return N == 0; }
			public int size(){ 
				return N; }
			public void insert(Key v) {
			pq[++N] = v;
			swim(N);
			}

			private boolean less(int i, int j) { 
				return pq[i].compareTo(pq[j]) > 0; }
			private void exch(int i, int j) { 
				Key t = pq[i]; pq[i] = pq[j]; pq[j] = t; }

				private void swim(int k) {
					while (k > 1 && less(k/2, k)) {
							exch(k/2, k);
							k = k/2;
						}
				}

				private void sink(int k) {
					while (2*k <= N)
					{
						int j = 2*k;
						if (j < N && less(j, j+1)) j++;
						if (!less(k, j)) break;
						exch(k, j);
						k = j;
					}
				}				

			public Key delMin() {
				Key min = pq[1]; 
				exch(1, N--); 
				pq[N+1] = null;
				sink(1); 
			return min;
			}

			
		}


		private static void writeTrie(Node x , BinaryOut m) {
				
				if (x.isLeaf())
				{
					m.write(true);
					m.write(x.ch);
				return;
				}
						m.write(false);
						writeTrie(x.left, m);
				writeTrie(x.right, m);
			}

		private static String[] buildCode(Node root) {
				String[] st = new String[256];
				buildCode(st, root, "");
				return st;
				}
			private static void buildCode(String[] st, Node x, String s) { 
				if (x.isLeaf()) { 
					st[x.ch] = s; 
					return; 
				}
					buildCode(st, x.left, s + '0');
					buildCode(st, x.right, s + '1');
			}
 	//the function used to encode the encoded file
	public void encode(String inputFile, String outputFile, String freqFile){

		BinaryIn in = new BinaryIn(inputFile);

		String s = in.readString();
		char[] input = s.toCharArray();

		int[] freq = new int[256];
		for (int i = 0; i < input.length; i++)
			freq[input[i]]++;

		try {
     		 FileWriter myWriter = new FileWriter(freqFile);
     		for (int i = 0; i < freq.length; i++){
     		String binary = Integer.toBinaryString(i);
 			String binaryFormat = String.format("%8s", binary).replaceAll(" ", "0");	
      		myWriter.write(binaryFormat +  ": "  + freq[i] + "\n");
      		}
      		myWriter.close();
     		
   			 } 
   				catch (IOException e) {
      			System.out.println("An error occurred.");
     			e.printStackTrace();
    		}


    		Node root = buildTrie(freq);
    		String[] st = new String[256];
			buildCode(st, root, "");

			BinaryOut out = new BinaryOut(outputFile);
			//writeTrie(root, out);

			out.write(input.length);
			

			for (int i = 0; i < input.length; i++) {
				String code = st[input[i]];
				for (int j = 0; j < code.length(); j++)
				if (code.charAt(j) == '1')
				out.write(true);
				else out.write(false);
			}
			
			out.flush();
			
  			

   }
   //This function builds the tree from the encoded file
   private static Node readTrie(BinaryIn x) {
   	
		if (x.readBoolean())
		return new Node(x.readChar(), 0, null, null);
		return new Node('\0', 0, readTrie(x), readTrie(x));
		
}

	//This function decodes the encoded file using the trie		
   public void decode(String inputFile, String outputFile, String freqFile){


   	BinaryIn in = new BinaryIn(inputFile);
   	int [] freq = new int[256];

   	
   	 try {
      File myObj = new File(freqFile);
      Scanner scnr = new Scanner(myObj);


		String temp;
      	
      	int freq1;

      for (int i = 0; i < 256; i++){
      	
      	temp = scnr.next();
      	
      	freq1 = scnr.nextInt();

      	freq[i] = freq1;

      }

      scnr.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
      e.printStackTrace();
	}

		

		BinaryOut out = new BinaryOut(outputFile);
		



			Node root = buildTrie(freq);
    		String[] st = new String[256];
			buildCode(st, root, "");

  // 	Node root = readTrie(in);
		int N = in.readInt();
		for (int i = 0; i < N; i++) { 
			Node x = root;
			while (!x.isLeaf())
				if (in.readBoolean())
				x = x.right;
				else x = x.left;
				out.write(x.ch);

	}

	out.flush();


}
		
   



	//main function
   public static void main(String[] args) {
      Huffman  huffman = new HuffmanSubmit();
		huffman.encode(args[0], "ur.enc", "freq.txt");
		huffman.decode("ur.enc", args[1], "freq.txt");
		 
   }

}