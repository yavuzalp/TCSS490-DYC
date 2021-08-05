/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.util.regex.Pattern;

import java.util.regex.Matcher;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class codeToDefend {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
		StringBuilder str = new StringBuilder();
		Scanner sc = new Scanner(System.in);
		/**
		 * Getting the name and last name.
		 */
		String name = "";
		System.out.print("Enter your name:");
		while(name.compareTo("")==0) {
			try {
				name = sc.next("^[a-zA-Z]+.{1,50}$");
				System.out.println("name: " + name);
			}catch(InputMismatchException error) {
				System.out.println("Please enter a valid name:");
				sc.nextLine();
			}
		};
		System.out.println(name);
		

		String lastName = null;
//            name = sc.next(Pattern.compile("^[a-zA-Z]+\s([a-zA-Z][.]?\s){0,}\\w+.{1,50}$"));
		do {
			try {
				System.out.print("Enter your last name:");
				lastName = sc.next("^[a-zA-Z]+.{1,50}$");
			}catch(InputMismatchException error) {
				System.out.println("mismatched");	
			}
		}while(lastName == null);
		System.out.println(lastName);

		/**
		 * Getting the int values.
		 */
//           #TODO 4 bit int
		int firstInt;
		int secondInt;
		System.out.print("Enter first integer:");
		firstInt = sc.nextInt();
		System.out.println(firstInt);
		System.out.print("Enter second integer:");
		secondInt = sc.nextInt();
		System.out.println(secondInt);

		/**
		 * Getting file names.
		 */
//		String loadFileName;
//		String saveFileName;
//		System.out.print("Enter file name to load:");
//		loadFileName = sc.next();
//		System.out.println(loadFileName);
//		String theInputFromFile = readFromFile(loadFileName);
//		str.append(theInputFromFile);
//		System.out.println("theInputFromFile: " + theInputFromFile);
//		System.out.print("Enter file name to save:");
//		saveFileName = sc.next();
//		System.out.println(saveFileName);
//		try {
//			writeToFile(str.toString(), saveFileName);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String password = "";
		try {
			System.out.print("Enter your password:");
			password = sc.next();
		}catch(java.util.InputMismatchException error) {
			System.out.println("mismatched");		
		}
		String secondPassword = "";
		try {
			System.out.print("Please re-enter your password to verify:");
			secondPassword = sc.next();
		}catch(java.util.InputMismatchException error) {
			System.out.println("mismatched");
			
		}
		createPasswordAndSave(password);
		Boolean isCorrectPassword = getPasswordFromFileAndConvert(secondPassword);
		System.out.println(isCorrectPassword);
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

	public static void writeToFile(String theInput, String fileName) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(fileName);
		out.print(theInput);
		out.close();
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
