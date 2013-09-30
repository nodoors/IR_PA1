import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Tokenize {

	public static void main(String[] args) {
		Scanner fileIn = null;
		Scanner readStop = null;
		ArrayList<String> stopList = new ArrayList<String>();
		try {
			fileIn = new Scanner(new FileInputStream("input.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			System.exit(0);
		}

		try {
			readStop = new Scanner(new FileInputStream("stoplist.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			System.exit(0);
		}

		try {
			// Create file
			FileWriter fstream = new FileWriter("output.txt");
			BufferedWriter fileOut = new BufferedWriter(fstream);

			while (readStop.hasNext()) {
				stopList.add(readStop.next());
			}

			while (fileIn.hasNext()) {
				Stemmer s = new Stemmer();
				String token = fileIn.next().toLowerCase().replaceAll("[^a-zA-Z_0-9]", "");
				
				boolean isStop = false;
				for (int i = 0; i < stopList.size(); i++) {
					if (token.equals(stopList.get(i)) ||  token.equals("")) {
						isStop = true;
						break;
					}
				}
				if (!isStop) {
					for (int k = 0; k < token.length(); k++) {
						s.add(token.charAt(k));
					}
					s.stem();

					isStop = false;
					for (int i = 0; i < stopList.size(); i++) {
						if (s.toString().equals(stopList.get(i)) ||  s.toString().equals("")) {
							isStop = true;
							break;
						}
					}
					if (!isStop) {
						fileOut.write("\"" + s.toString() + "\" ");
					}
				}
			}

			// Close the output stream
			fileIn.close();
			readStop.close();
			fileOut.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
}
