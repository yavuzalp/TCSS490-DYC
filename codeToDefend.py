import re
import os.path
import os
import hashlib

"""
    TCSS 490 – Summer 2021
    Assignment: Defend Your Code!
"""
__author__ = "Jonathan Cho, Yavuzalp Turkoglu"
__version__ = "Summer 2021"


class codeToDefend():

    def getUserName():
        isValid = False
        name = input("Enter your name: (1-50 Alphabetical Characters, No spaces)")
        pattern = r"^[a-zA-Z]+.{1,50}$"
        pat = re.compile(pattern)    
        while(not isValid):
            if re.fullmatch(pat, name):
                # print(f"'{name}' is an alphanumeric string!") #TODO: Delete Later
                isValid = True
            else:
                name = input("Please enter a valid name:")
        return name

    def getUserLastName():
        isValid = False
        name = input("Enter your last name(1-50 Alphabetical Characters, No spaces allowed): ")
        pattern = r"^[a-zA-Z]+.{1,50}$"
        pat = re.compile(pattern)    
        while(not isValid):
            if re.fullmatch(pat, name):
                # print(f"'{name}' is an alphanumeric string!") #TODO: Delete Later
                isValid = True
            else:
                name = input("Please enter a valid last name(1-50 Alphabetical Characters, No spaces allowed): ")
        return name


    def getInteger(count):
        intValue = None
        while True:
            num = input("Please enter " + count + " an integer: ")
            try:
                intValue = int(num)
                # print("Input is an integer number.") #TODO: Delete Later
                # print("Input number is: ", intValue) #TODO: Delete Later
                break;
            except ValueError:
                print("Please enter a valid number: ")
        return intValue

    # TODO: maybe use ^/.*\.txt$ for validation of fileName
    def readFromFile(fileName=None):
        isValid = False
        pattern = r"^.[^\s]*\.txt{1,100}$"
        pat = re.compile(pattern)   
        fileName = input("Enter file name to load(File extension must be .txt, no spaces within the file name): ")
        dataFromFile = ""
        while not isValid:
            if re.fullmatch(pat, fileName):
                # print(f"'{fileName}' is a valid file name!") #TODO: Delete Later
                isValid = True
            else:
                name = input("Please enter a valid file name(File extension must be .txt, no spaces within the file name): ") 
            if isValid:
                if os.path.isfile(fileName): 
                    reader = open(fileName, 'r')
                    try:
                        dataFromFile = reader.read()
                    finally:
                        reader.close()
                        break;
                else:
                    fileName = input("No File Found! Please try again: ")
        return dataFromFile

    # TODO: maybe a regex for file name
    def writeToFile(dataToWrite):
        isValid = False
        pattern = r"^.[^\s]*\.txt{1,100}$"
        pat = re.compile(pattern)   
        fileName = input("Enter file name to save(File extension must be .txt, no spaces within the file name): ")
        isValid = False
        while(not isValid):
            if re.fullmatch(pat, fileName):
                isValid = True
            else:
                name = input("Please enter a valid file name(File extension must be .txt, no spaces within the file name): ") 
            if isValid:
                if os.path.isfile(fileName): 
                    writer = open(fileName, 'w')
                    try:
                        writer.write(dataToWrite)
                    finally:
                        writer.close()
                        break;
                else:
                    fileName = input("Invalid file name. Please try again(File extension must be .txt, no spaces within the file name): ")
	
    # TODO: maybe a regex for password
    def savePassword(password=None):
        password = input("Enter a password(Passwords can contain alphanumeric and special characters, up to 50 characters): ")
        
        pattern = r"^[a-zA-Z0-9@#$%^&+=*.\-_]{1,50}$"
        pat = re.compile(pattern) 
        while True:
            if re.fullmatch(pat, password):
                break;
            else:
                password = input("Enter a valid password(Passwords can contain alphanumeric and special characters, up to 50 characters): ")
        salt = os.urandom(32)
        key = hashlib.pbkdf2_hmac('sha256', password.encode('utf-8'), salt, 100000)
        storage = salt + key 
        writer = open("passwordPY.txt", 'wb')
        try:
            writer.write(storage)
        finally:
            writer.close()
	
    def validatePassword(passwordToCheck=None):
        passwordToCheck = input("Enter password to verify:")
        reader = open("passwordPY.txt", 'rb')
        try:
            dataFromFile = reader.read()
        finally:
            reader.close()
        salt_from_storage = dataFromFile[:32] # 32 is the length of the salt
        key_from_storage = dataFromFile[32:]
        
        while True:
            new_key = hashlib.pbkdf2_hmac(
                'sha256',
                passwordToCheck.encode('utf-8'),
                salt_from_storage, 
                100000
            )
            if new_key == key_from_storage:
                print('Password is correct')
                break;
            else:
                passwordToCheck = input("Password is incorrect! Please enter again:")
        
        return new_key == key_from_storage


""" Getting the name and last name. """
sb = ""
sb = "Name: " + codeToDefend.getUserName() + "\n"
sb += "Last Name: " + codeToDefend.getUserLastName() + "\n\n"

""" Getting the int values. """
firstInt = codeToDefend.getInteger("first")
secondInt = codeToDefend.getInteger("second")

sb += "First Integer: " + str(firstInt) + "\n"
sb += "Second Integer: " + str(secondInt) + "\n\n"

addedLargeValue = firstInt + secondInt
# print("addedLargeValue: " + str(addedLargeValue))
sb +="Addition of the inputs: " + str(addedLargeValue) + "\n"

multipliedlargeValue = firstInt * secondInt
# print("multipliedlargeValue: " + str(multipliedlargeValue))
sb +="Multiplication of the inputs: " + str(multipliedlargeValue) + "\n\n"
		
"""
Getting file names.
Read data from file and write to a file.
"""
sb += "Data from input file:\n" + codeToDefend.readFromFile() + "\n\n"
codeToDefend.writeToFile(sb)

		
""" Getting Password and verifying """
codeToDefend.savePassword()
codeToDefend.validatePassword()
