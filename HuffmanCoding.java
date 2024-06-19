import java.io.*;
import java.util.*;

class Node implements Comparable<Node> {
    char character;
    int frequency;
    Node left;
    Node right;

    public Node(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.frequency, other.frequency);
    }
}

public class HuffmanCoding {
    private static final String INPUT_FILE = "input.txt";
    private static final String OUTPUT_FILE = "output.txt";
    private static final String DECOMPRESSED_FILE = "decompressed.txt";

    private static Map<Character, String> huffmanCodes;
    private static Node root;

    public static void main(String[] args) throws IOException {
        calculateFrequency(INPUT_FILE);
        buildHuffmanTree();
        assignHuffmanCodes(root, "");
        compress(INPUT_FILE, OUTPUT_FILE);
        decompress(OUTPUT_FILE, DECOMPRESSED_FILE);
    }

    private static void calculateFrequency(String inputFile) throws IOException {
        Map<Character, Integer> frequency = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            int c;
            while ((c = reader.read())!= -1) {
                char character = (char) c;
                frequency.put(character, frequency.getOrDefault(character, 0) + 1);
            }
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {
            Node node = new Node(entry.getKey(), entry.getValue());
            pq.add(node);
        }

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            Node newNode = new Node('\0', left.frequency + right.frequency);
            newNode.left = left;
            newNode.right = right;
            pq.add(newNode);
        }

        root = pq.poll();
    }

    private static void assignHuffmanCodes(Node node, String code) {
        if (node == null) {
            return;
        }

        if (node.character!= '\0') {
            huffmanCodes.put(node.character, code);
        }

        assignHuffmanCodes(node.left, code + "0");
        assignHuffmanCodes(node.right, code + "1");
    }

    private static void compress(String inputFile, String outputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            int c;
            while ((c = reader.read())!= -1) {
                char character = (char) c;
                writer.write(huffmanCodes.get(character));
            }
        }
    }

    private static void decompress(String inputFile, String outputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            StringBuilder code = new StringBuilder();
            int c;
            while ((c = reader.read())!= -1) {
                code.append((char) c);
                for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
                    if (code.toString().startsWith(entry.getValue())) {
                        writer.write(entry.getKey());
                        code.delete(0, entry.getValue().length());
                        break;
                    }
                }
            }
        }
    }
}
