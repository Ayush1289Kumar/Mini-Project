import java.util.Scanner;

// class Admin extends User{
//     private String name = "Ayush Kumar";
//     private String username = "erenYeager";
//     private int pass = 17080507;
//     private float balance = 1_50_000;
//     private boolean isAdmin = true;
// }

class User{
    String accountHolderName;
    String userName;
    private int userPassword;
    Float userBalance;
    boolean userRegistered = false;
    boolean isAdmin = false;

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public Float getUserBalance() {
        return userBalance;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserPassword() {
        return userPassword;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public void setUserBalance(Float userBalance) {
        this.userBalance = userBalance;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(int userPassword) {
        this.userPassword = userPassword;
    }

    Scanner sc = new Scanner(System.in);

    public void userRegister() {
        System.out.print("Enter your name: ");
        setAccountHolderName(sc.nextLine());

        System.out.print("Enter a username: ");
        setUserName(sc.nextLine());

        System.out.print("Enter password: ");
        setUserPassword(sc.nextInt());

        System.out.print("Enter your initial balance: ");
        setUserBalance(sc.nextFloat());

        System.out.println("\nPlease check your details: ");
        System.out.println("Name: "+getAccountHolderName());
        System.out.println("User name: "+getUserName());
        System.out.println("Password : "+getUserPassword());
        System.out.println("Balance: "+getUserBalance());
        String choice;
        System.out.print("Is there any changes?: ");
        choice = sc.next();

        sc.nextLine();

        if ("yes".equals(choice) || "Yes".equals(choice)){
            System.out.println("Please re-enter your details....");
            userRegister();
            return;
        }
        userRegistered = true;
        System.out.println("\nCongratualations!!!");
        System.out.println("\nYou registered as a new user");
    }

    public boolean userLogin() {
        int passAttempts = 0;
        System.out.print("Enter user name: ");
        if (!(getUserName().equals(sc.nextLine()))) {
            System.out.println("Invalid username!!!");
            return false;
        }

        while (true) {
            if (passAttempts==3){
                System.out.println("3 unsuccessful attempts...");
                return false;
            }
            
            System.out.print("Enter password: ");

            if (!(getUserPassword()==sc.nextInt())){
                System.out.println("Wrong password!!!");
                System.out.println("Only "+(3-(++passAttempts))+" Attempts left");
            }

            else{ 
                System.out.println("Login successful");
                return true;
            }
        }
    }

    public void depositMoney(float amount){
        setUserBalance(userBalance+amount);
        System.out.println(amount+" Deposited successfully.");
    }

    public void withdrawMoney(float amount){
        if (amount > getUserBalance())
            System.out.println("Insufficient balance");
        else {
            setUserBalance(userBalance-amount);
            System.out.println(amount+" Withdrawn Successfully.");
        }
    }

}

public class BankManagementSystem{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        User newUser = new User();
        boolean userLoggedIn = false;

        System.out.println("\n-----------------------------------------");
        System.out.println("          Bank Management System         ");
        System.out.println("-----------------------------------------\n");

        while (true) {
            System.out.println("1.Register");
            System.out.println("2.Login");
            System.out.println("3.Exit");
            
            int choose;
            System.out.print("Choose: ");
            choose = sc.nextInt();

            if (choose == 3)
                break;

            switch (choose) {

                case 1 -> {
                    if (!newUser.userRegistered)
                        newUser.userRegister();
                    
                    else { 
                        System.out.println("You are already registered.");
                        break;
                    }
                }
                case 2 -> {
                    if (newUser.userRegistered) {
                        sc.nextLine();
                        userLoggedIn = newUser.userLogin();
                    }
                    else {
                        System.out.println("You are not registered.");
                        break;
                    }

                    while (userLoggedIn) { 
                        System.out.println("1.Deposit");
                        System.out.println("2.Withdraw");
                        System.out.println("3.Balance");
                        System.out.println("4.Logout");
    
                        System.out.print("Choose: ");
                        choose = sc.nextInt();
    
                        if (choose==4) {
                            userLoggedIn = false;
                            break;
                    }
                        switch (choose) {
                            case 1 -> {
                                    System.out.print("Enter amount :");
                                    newUser.depositMoney(sc.nextFloat());
                                    sc.nextLine();
                                }
            
                            case 2 -> {
                                    System.out.print("Enter amount: ");
                                    newUser.withdrawMoney(sc.nextFloat());
                                    sc.nextLine();
                                }
                            case 3 -> {
                                    System.out.println("Your Balance: "+newUser.getUserBalance());
                            }
                            default -> System.out.println("Invalid Input!!!");
                        }
                    }
                }

                default -> System.out.println("Invalid input!!!");
            }
        }
        System.out.println("Thanks for using...");

    }
}