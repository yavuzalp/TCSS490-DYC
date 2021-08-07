/*
* TCSS 490 – Summer 2021
* Assignment: Defend Your Code!
* 
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * codeToDefend
 *
 * @author Jonathan Cho
 * @author Yavuzalp Turkoglu
 * @version Summer 2021
 */
public class codeToDefend {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        StringBuilder str = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        
        /**
         * Getting the name and last name.
         */
        String name = getUserName(sc);
        str.append("Name: " + name + "\n");

        String lastName = getUserLastName(sc);
        str.append("Last Name: " + lastName + "\n\n");


        /**
         * Getting the int values.
         */
        int firstInt = getInteger(sc, "first");
        int secondInt = getInteger(sc, "second");

        str.append("First Integer: " + firstInt + "\n");
        str.append("Second Integer: " + secondInt + "\n\n");

        BigInteger addedLargeValue = addIntegers(firstInt,secondInt);
        System.out.println("addedLargeValue: " + addedLargeValue);
        str.append("Addition of the input: " + addedLargeValue.toString() + "\n");

        BigInteger multipliedlargeValue = multiplyIntegers(firstInt,secondInt);
        System.out.println("multipliedlargeValue: " + multipliedlargeValue);
        str.append("Multiplication of the input: " + multipliedlargeValue.toString() + "\n\n");

        /**
         * Getting file names.
         * Read data from file and write to a file.
         */

        str.append("Data from input file:\n" + getInputFromFile(sc));

        getFileNameAndWrite(sc, str.toString());


        /**
         * Getting Password and verifying
         * */

        String password = getPasswordFromUser(sc);
        createPasswordAndSave(password);
        verifyPassword(sc);
    }

    public static String getUserName(Scanner sc) {
        String name = "";
        String pattern = "[a-zA-Z]+.{1,50}";
        System.out.print("Enter your name(1-50 Alphabetical Characters, No Spaces, No Special Characters):");
        while (!sc.hasNext(pattern)) {
            System.out.println("Please enter a valid name:");
            sc.nextLine();
        }
        ;
        name = sc.next(pattern);
        return name;
    }

    public static String getUserLastName(Scanner sc) {
        String lastName = null;
//        name = sc.next(Pattern.compile("^[a-zA-Z]+\s([a-zA-Z][.]?\s){0,}\\w+.{1,50}$"));
        System.out.print("Enter your last name(1-50 Alphabetical Characters, No Spaces, No Special Characters):");
        while (lastName == null) {
            try {
                lastName = sc.next("[a-zA-Z]+.{1,50}");
            } catch (InputMismatchException error) {
                System.out.println("Please enter a valid last name:");
                sc.nextLine();
            }
        }
        ;
        return lastName;
    }

    public static int getInteger(Scanner sc, String count) {
        int intValue;
        System.out.print("Please enter " + count + " an integer:");
        while (!sc.hasNextInt()) {
            System.out.println("Please enter a valid number:");
            sc.nextLine();
        }
        intValue = sc.nextInt();
        return intValue;
    }

    public static BigInteger addIntegers(int firstInt, int secondInt) {
        BigInteger addedLargeValue = new BigInteger(firstInt + "");
        addedLargeValue = addedLargeValue.add(new BigInteger(secondInt + ""));
        return addedLargeValue;
    }

    public static BigInteger multiplyIntegers(int firstInt, int secondInt) {
    	BigInteger multipliedlargeValue = new BigInteger(firstInt + "");
        multipliedlargeValue = multipliedlargeValue.multiply(new BigInteger(secondInt + ""));
        return multipliedlargeValue;
    }
    
    public static String readFromFile(String fileName) throws IOException {
        File file;
        if (new File(fileName).exists()) {
            file = new File(fileName);
        } else if (new File(".././" + fileName).exists()) {
            file = new File(".././" + fileName);
        } else if (new File("../" + fileName).exists()) {
            file = new File("../" + fileName);
        } else if (new File("./" + fileName).exists()) {
            file = new File("./" + fileName);
        } else {
            file = new File("./src/input.txt");
        }
        BufferedReader br = new BufferedReader(new FileReader(file));

        StringBuilder str = new StringBuilder();
        String st;
        while ((st = br.readLine()) != null) {
            str.append(st + "\n");
        }
        return str.toString();
    }

    public static String getInputFromFile(Scanner sc) throws IOException, FileNotFoundException {
        String theInputFromFile = "";
        String loadFileName;
        String pattern = ".[^\\s]*\\.txt{1,100}";
        System.out.print("Enter file name to load(File extension must be .txt, no spaces within the file name):");
        while (!sc.hasNext(pattern)) {
            System.out.println("Please enter a valid name(File extension must be .txt, no spaces within the file name):");
            sc.nextLine();
        }
        loadFileName = sc.next();
        while (true) {
            try {
                theInputFromFile = readFromFile(loadFileName);
                break;
            } catch (FileNotFoundException error) {
                System.out.println("No File Found, please try again (File extension must be .txt, no spaces within the file name).");
                sc.nextLine();
            }

        }

        return theInputFromFile;
    }

    public static void writeToFile(String theInput, String fileName) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(fileName);
        out.print(theInput);
        out.close();
    }


    public static void getFileNameAndWrite(Scanner sc, String theData) throws IOException {
        String saveFileName;
        String pattern = ".[^\\s]*\\.txt{1,100}";
        System.out.print("Enter file name to save (File extension must be .txt, no spaces within the file name):");
        while (!sc.hasNext(pattern)) {
            System.out.println("Please enter a valid name(File extension must be .txt, no spaces within the file name):");
            sc.nextLine();
        }
        saveFileName = sc.next();
        try {
            writeToFile(theData, saveFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getPasswordFromUser(Scanner sc) {
        String password = "";
        System.out.print("Enter a password(Passwords can contain alphanumeric and special characters, up to 50 characters): ");
        while(true) {
	        try {
	            password = sc.next("[a-zA-Z0-9@#$%^&+=*.\\-_]{1,50}");
	            break;
	        } catch (java.util.InputMismatchException error) {
	            System.out.println("Enter a valid password(Passwords can contain alphanumeric and special characters, up to 50 characters): ");
	            sc.nextLine();
	        }
        }
        return password;
    }
    
    public static void createPasswordAndSave(String theInput) throws FileNotFoundException, NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        byte[] hashedPassword = md.digest(theInput.getBytes(StandardCharsets.UTF_8));
        PrintWriter out = new PrintWriter("./password.txt");
        out.print(Arrays.toString(salt));
        out.print("\n");
        out.print(Arrays.toString(hashedPassword));
        out.close();
    }


    public static void verifyPassword(Scanner sc) throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
        String secondPassword = "";
        System.out.print("Please re-enter your password to verify:");

		Boolean isPatternMatched = false;
		Boolean isCorrectPassword = false;
        while (true) {
            try {
                secondPassword = sc.next("[a-zA-Z0-9@#$%^&+=*.\\-_]{1,50}");
                isPatternMatched = true;
	        } catch (java.util.InputMismatchException error) {
	            System.out.println("Invalid password, please try again:");
	            sc.nextLine();
	        }
            if(isPatternMatched) {
	            isCorrectPassword = getPasswordFromFileAndConvert(secondPassword);
	            if (isCorrectPassword) {
	                System.out.println("Password is verified!");
	                break;
	            } else {
	    			System.out.println("Password mismatch, please try again:");
	                sc.nextLine();
	            }
            }
        }
    }
    
    public static Boolean getPasswordFromFileAndConvert(String theInput)
          throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        File file = new File("./password.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String salt = br.readLine();
        String hash = br.readLine();
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        String[] byteValues = salt.substring(1, salt.length() - 1).split(",");
        byte[] bytes = new byte[byteValues.length];
        for (int i = 0, len = bytes.length; i < len; i++) {
            bytes[i] = Byte.parseByte(byteValues[i].trim());
        }
        md.update(bytes);
        byte[] hashedPassword = md.digest(theInput.getBytes(StandardCharsets.UTF_8));
        String passwordToCheck = Arrays.toString(hashedPassword);
        return hash.equals(passwordToCheck);
    }
}
