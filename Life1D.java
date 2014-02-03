

public class Life1D {
	private Rule rule;
	private int stepCount;

	public static void main(String[] args) {
		Life1D simulation = new Life1D();
		simulation.processArgs(args);
		simulation.producePBM();
	}

	// Print, in Portable Bitmap format, the image corresponding to the rule and
	// step count
	// specified on the command line.
	public void producePBM() {
		System.out.println("P1" +  " " + (2*stepCount+1) + (stepCount+1));
		int[][] matrixArray = new int [2*stepCount+1][stepCount+1];
		
		for (int i = 0; i < 2*stepCount+1; i++) {
			if (i == stepCount) {
				matrixArray[0][i] = 1;
			} else {
				matrixArray[0][i] = 0;
			}
		}
		
		for (int r = 1; r < stepCount+1; r++) {
			for (int c = 1; c < 2*stepCount; c++) {
				matrixArray[r][c] = rule.output(matrixArray[r-1][c-1], matrixArray[r-1][c], matrixArray[r-1][c+1]);
			}
			matrixArray[r][0]= rule.output(0, matrixArray[r-1][0], matrixArray[r-1][1]);
			matrixArray[r][2*stepCount]= rule.output(matrixArray[r-1][2*stepCount-1], matrixArray[r-1][2*stepCount], 0);
		}
		
		for (int m = 0; m < stepCount+1; m++) {
			for (int n = 0; n < 2*stepCount+1; n++) {
				System.out.print(matrixArray[m][n]);
				System.out.print(" ");
			}
			System.out.println();
		}
		
	}

	// Retrieve the command-line arguments, and convert them to values for the
	// rule number
	// and the timestep count.
	private void processArgs(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage:  java Life1D rule# rowcount");
			System.exit(1);
		}
		try {
			rule = new Rule(Integer.parseInt(args[0]));
		} catch (Exception ex) {
			System.err
					.println("The first argument must specify a rule number.");
			System.exit(1);
		}
		try {
			stepCount=Integer.parseInt(args[1]);
		} catch (Exception ex) {
			System.err
					.println("The second argument must specify the number of lines in the output.");
			System.exit(1);
		}
		if (stepCount < 1) {
			System.err
					.println("The number of output lines must be a positive number.");
			System.exit(1);
		}
	}

	

}

class Rule {
	private int[] rowArray;
	

	// Whatever instance variables you need should be declared here.
	public Rule(int ruleNum) {
		// constructor.
		String binaryRuleNum = toBinary(ruleNum);
		rowArray = new int [8];
		for (int i = 0; i < 8; i++) {
			rowArray[i] = (int) binaryRuleNum.charAt(i);
		}
	}

	public String toBinary(int number) {
		return Integer.toBinaryString(number);
	
	}

	// Return the output that this rule prescribes for the given input.
	// a, b, and c are each either 1 or 0; 4*a+2*b+c is the input for the rule.
	public int output(int a, int b, int c) {
		
		if (a == 1 && b == 1 && c == 1) {
			return rowArray[0];
		} else if (a == 1 && b == 1 && c == 0) {
			return rowArray[1];
		} else if (a == 1 && b == 0 && c == 1) {
			return rowArray[2];
		} else if (a == 1 && b == 0 && c == 0) {
			return rowArray[3];
		} else if (a == 0 && b == 1 && c == 1) {
			return rowArray[4];
		} else if (a == 0 && b == 1 && c == 0) {
			return rowArray[5];
		} else if (a == 0 && b == 0 && c == 1) {
			return rowArray[6];
		} else {
			return rowArray[7];
		} 
	}
}
