# Huffman-Compression

<h2> Introduction </h2>

Huffman algorithm is code designed to compress files (decrease their size). The Basic idea is chaning the bit representation of each character depending on how frequent this character appear in the file. The algorithm creates a trie that assigns a bit representation for each character. The more this character appears the less bit in its representation it has. 

<br>
<h2>Files in the program</h2>
All helper files that helps in converting the files to binary representation are provided by University of rochester Computer Science department<br>
BinaryIn.java: converts the files into binary representation<br>
BinaryOut.java: converts the binary representation into an output file<br>
Huffman.java: has the interface for the main program <br>
<strong>HuffmanSubmit.java </strong>: contain main algorithm and functions <br>
<h2>Simple explanation </h2>

The program uses the Huffman algorithm to create a trie for characters. The trie is used to encode the file that is needed to be compressed. The trie itself is NOT encoded in the encoded file but instead a frequency file is generated to help regenerate the trie while decoding. 

The program can decode the file using the encoded file and frequency file to retrieve the original file.
  <br>

<h2>Output example</h2>
Input file: <br>
The text file in the project “alice30.txt” is originally 149kb
After running the code on this file the encoded file “ur.enc” becomes only 85kb with a frequency file of only 3kb. 


<br>
Output file: <br>
And then using the frequency file, the encoded file can be decoded to go back to original with the same size:


<br>
Note: The method to get this example to run is shown in next section


<h2>How to run </h2>
In the folder there is “sources.txt” file this file has all java files you need to run.
In command line or terminal window, run:<br>

if you are on mac or linux 
```powershell
find "${dir:-.}" -name "*.java" > sources.txt
Javac @sources.txt
```

if you are on windows: <br>
```powershell
dir /s /B *.java > sources.txt
Javac @sources.txt
```

That compiles all files in the project.
Then run
```powershell
Java HuffmanSubmit [input file name] [output file name]
```
For example: 
```powershell
Java HuffmanSubmit alice30.txt output.txt
```
Then you get the example mentioned last section.




