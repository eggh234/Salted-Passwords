//Daniel Huang + Hridhoy Ahmond
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

// Java program to calculate MD5 hash value
public class PasswordCracker {
    public static int UID;
    public static String hashtext;
    public static String Concat1;
    public static String password;
    public static String salt;
    public static String Temp;//creating global variables
    public static String Pass[] = new String[100];
    public static String Salt[] = new String[100];
    public static String Hash[] = new String[100];
    public static String line = new String();
    public static String getMd5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Driver code

    public static void main(String[] args) {
        System.out.println("What is the UID?");//gets UID
        Scanner ask = new Scanner(System.in);
        UID = Integer.parseInt(ask.nextLine());
        read(UID, "/Users/daniel_huang/Desktop/intelij/src/Sup/Password.txt");//reads password file
        password = line;
        read(UID, "/Users/daniel_huang/Desktop/intelij/src/Sup/Salt.txt");//reads salt file
        salt = line;
        Con(password, salt);
        String s = Concat1;//creates the concated string for hash function
        hashtext = getMd5(s);//gets hash value
        System.out.println(hashtext);
        Fill("/Users/daniel_huang/Desktop/intelij/src/Sup/Password.txt", Pass);
        Fill("/Users/daniel_huang/Desktop/intelij/src/Sup/Salt.txt", Salt);
        Fill("/Users/daniel_huang/Desktop/intelij/src/Sup/Hash.txt", Hash);//filling in array for comparing values
        Check("/Users/daniel_huang/Desktop/intelij/src/Sup/UID.txt", Pass, Salt);
    }
    //was going to just run file checker to save memory space but needed a running model
    public static void Fill(String s, String[] array) {
        try {
            //read file.txt
            FileReader file0 = new FileReader(s);
            BufferedReader buffer = new BufferedReader(file0);

            // iterate through the file
            for (int i = 1; i < 101; i++) {
                // If the line number = 5 retrieve the line
                line = buffer.readLine();
                array[i-1] = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void Con(String password, String salt){
        Concat1 = password + salt;//creates concated salt for hash function
    }

    public static void read(int UID, String s) {
        try {
            //read txt file to get the password and salt for concatination
            FileReader file0 = new FileReader(s);
            BufferedReader buffer = new BufferedReader(file0);

            // iterate through the file
            for (int i = 1; i < 100; i++) {
                if (i == UID)
                    line = buffer.readLine();
                else
                    buffer.readLine();
            }
            System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();//error catching
        }
    }
    //take UID salt + UID password and check hash function and return if it matches UID hash
    //get md5 creates salt
    public static void Check(String s, String[] Pass, String[] Salt){
        String line = new String();
        try {
            //read file.txt
            FileReader file0 = new FileReader(s);
            BufferedReader buffer = new BufferedReader(file0);

            // iterate through the file
            for (int i = 1; i < 101; i++) {
                while(i < 101){
                    for(int a = 0; a < 100; a++){
                        Temp = Pass[i-1] + Salt[a];
                        getMd5(Temp);
                        Integer number = Integer.valueOf(UID);
                        System.out.println("Cracking Combination Number: " + i);//debugging print statement so we know the code is running
                        if(getMd5(Temp) == Hash[number-1]){
                            System.out.println("Password is: " + Pass[i-1]);
                        }
                    }//loops through every single combination of password and salt
                }//then compares the hash value to the hash value of the hash function of the text file
            }
            System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
//the print statement that prints i constantly is to check if the program is runnning
//so that we dont have to sit around for hours
