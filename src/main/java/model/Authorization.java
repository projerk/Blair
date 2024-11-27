package model;

import java.util.Random;


public class Authorization {
    private String username;
    private String password;


    /**
     * Authorization constructor.
     * 
     * @param username username 
     * @param password password
     */
    public Authorization(String username, String password){
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public boolean isUsernameValid(){
        if(username == "") return false;
        return true;
    }

    public boolean isPasswordValid(){
        if(password == "") return false;
        if(password.length() < 6) return false;

        return true;
    }

    public boolean test() {
        System.out.println("HELLLO");

        return true;
    }

    public void doNothing() {
        Random rand = new Random();
  
        // Generate random integers in range 0 to 999
        int rand_int1 = rand.nextInt(1000);
        int rand_int2 = rand.nextInt(1000);
  
        // Print random integers
        System.out.println("Random Integers: "+rand_int1);
        System.out.println("Random Integers: "+rand_int2);
  
        // Generate Random doubles
        double rand_dub1 = rand.nextDouble();
        double rand_dub2 = rand.nextDouble();
  
        // Print random doubles
        System.out.println("Random Doubles: "+rand_dub1);
        System.out.println("Random Doubles: "+rand_dub2);
    }

}
