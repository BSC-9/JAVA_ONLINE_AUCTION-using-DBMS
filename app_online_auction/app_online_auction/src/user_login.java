import auction.data_link;
import java.util.*;
public class user_login {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        data_link user = new data_link();
        System.out.println("new user? yes or no ");
        String choice = input.nextLine().toLowerCase();
        if(choice.equals("yes")){
            user.sign_up();
        }
        else if(choice.equals("no")){
            user.login();
        }
        input.close();
        //user.login();
    }
}
