huffman
=======

This is a huffman encoder,  decoder, input and output streams written in java. 

HuffmanDemo
=======
HuffmanDemo program demonstrates the basic principles of usage of Huffman input and output streams (see its source code for
more details):

    % mvn clean package
      ...
    % java -jar target/huffman-1.0-SNAPSHOT.jar
    USAGE: HuffmanDemo enc|dec|entropy
       enc <input-file> <output-file>: encode input file and save
                        the results to output file
       dec <input-file> <output-file>: decode input file and save
                        the results to output file
       entropy <input-file>: calculate an entropy of the symbols in input file
       
A book of 5000+ pages was used to demonstrate how HuffmanDemo works.
So, as you can see from the program output above, HuffmanDemo can do a few things:
