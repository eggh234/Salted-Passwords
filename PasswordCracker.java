import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class PasswordCracker {
    public static String UID;
    public static int flag = 0;
    public static String Temp;//creating global variables
    public static String[] Pass = new String[100];
    public static String[] Salt = new String[100];
    public static String[] Hash = new String[100];
    public static String line;

    // Java program to calculate MD5 hash value
    public static String getMd5(String input) {
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
        while(flag == 0){
            System.out.println("Input UID between 1-100: "); //gets user input
            Scanner ask1 = new Scanner(System.in);
            UID = ask1.nextLine();
            Usercheck(UID);
        }
        Fill("/Users/daniel_huang/Desktop/intelij/src/Sup/Password.txt", Pass);//path names
        Fill("/Users/daniel_huang/Desktop/intelij/src/Sup/Salt.txt", Salt);
        Fill("/Users/daniel_huang/Desktop/intelij/src/Sup/Hash.txt", Hash);//filling in array for comparing values
        Check("/Users/daniel_huang/Desktop/intelij/src/Sup/UID.txt", Pass, Salt);
    }

    public static int Usercheck(String UID) {
        int val = 0;
        try {
            val = Integer.parseInt(UID);
            if(Integer.parseInt(UID) > 100) {
                System.out.println("Invalid Input");
            }
            else{
                flag = 1;
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid Input");
        }
        return val;
    }

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
    public static void Check(String s, String[] Pass, String[] Salt){

        String line = "";
        int Counter = 0;
        try {
            //read file.txt
            FileReader file0 = new FileReader(s);
            BufferedReader buffer = new BufferedReader(file0);

            // iterate through the file
            for (int i = 1; i < 101; i++) {
                    for(int a = 0; a < 100; a++){
                        Temp = Pass[i-1] + Salt[a];
                        getMd5(Temp);
                        Integer number = Integer.valueOf(UID);
                        Counter = Counter + 1;
                        System.out.println("Cracking Combination Number: " + Counter);
                        System.out.println("Line Number: " + i);//debugging print statement so we know the code is running
                        System.out.println("Salt Number: " + Salt[a]);
                        if(getMd5(Temp).equals(Hash[number-1])){//printing out information if hash matches
                            System.out.println("----------------------------------------------");
                            System.out.println("UID Number: " + UID);
                            System.out.println("Salt Number: " + Salt[i-1]);
                            System.out.println("Password Number: " + Pass[i-1]);
                            System.out.println("Hash Value: " + getMd5(Temp));
                            return;//breaks the loop
                    }//loops through every single combination of password and salt
                }//then compares the hash value to the hash value of the hash function of the text file
            }//if there is a match then it returns value and prints out information and breaks loop
            System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
