import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;

// Java program to calculate MD5 hash value
public class SaltVerifier {
    public static int UID;
    public static String hashtext;
    public static String Concat1;
    public static String password;
    public static String salt;//creating variables
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
        while(UID > 100){
            System.out.println("Input UID between 1-100");//gets UID
            Scanner ask1 = new Scanner(System.in);
            UID = Integer.parseInt(ask1.nextLine());
        }
        read(UID, "/Users/daniel_huang/Desktop/intelij/src/Sup/Password.txt", "Password: ");//reads password file
        password = line;
        read(UID, "/Users/daniel_huang/Desktop/intelij/src/Sup/Salt.txt", "Salt value: ");//reads salt file
        salt = line;
        Con(password, salt);
        String s = Concat1;//creates the concated string for hash function
        hashtext = getMd5(s);//gets hash value
        System.out.println("Hash function value: " + hashtext);
        Check("/Users/daniel_huang/Desktop/intelij/src/Sup/Hash.txt", UID, hashtext);
    }

    public static void Con(String password, String salt){
        Concat1 = password + salt;//creates concated salt for hash function
    }

    public static void read(int UID, String s, String Label) {
        try {
            //read txt file to get the password and salt for concatination
            FileReader file0 = new FileReader(s);
            BufferedReader buffer = new BufferedReader(file0);

            // iterate through the file
            for (int i = 1; i < 100; i++) {
                if (i == UID)
                    line = buffer.readLine();//reads the file at the specific line
                else
                    buffer.readLine();
            }
            System.out.println(Label + line);
        } catch (IOException e) {
            e.printStackTrace();//error catching
        }
    }
    public static void Check(String s, int number, String hashtext){
        try{

            String line = Files.readAllLines(Paths.get(s)).get(number-1);
            if(hashtext.equals(line)){//checks if the hastext matches at a certain line
                System.out.println("Hash value matches at line " + UID);

            } else {
                System.out.println("No found matched hash values");
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
}
