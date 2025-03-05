import java.io.*;
import java.util.*;
class User extends Person  {
    int age;
    String gender, nationality;
    // constructor
    public User(String firstName, String lastName, String ssn, String id, String password, String phoneNumber, String email, int age, String gender, String nationality) {
        super(firstName, lastName, ssn, id, password, phoneNumber, email);
        this.age = age;
        this.gender = gender;
        this.nationality = nationality;
    }
    //getters
    public int getAge() {
        return age;
    }

    public String getNationality() {
        return nationality;
    }
    public String getGender() {
        return gender;
    }
    public String getlastname() {
        return lastName;
    }
    public String getssn() {
        return ssn;
    }
    public String getfirstname() {
        return firstName;
    }
    public String getid() {
        return id;
    }public String getphonenum() {
        return phoneNumber;
    }public String getpassword() {
        return password;
    }public String getemail() {
        return email;
    }
    //method to save all data of user in file
    public void saveUserData(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(this.toString());
            writer.newLine();
        }
    }
    // method to create and load data from file
    public static List<User> readUserData(String filePath) throws IOException {
        List<User> users = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            file.createNewFile();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                users.add(new User(data[0], data[1], data[2], data[3], data[4], data[5], data[6], Integer.parseInt(data[7]), data[8], data[9]));
            }
        }
        return users;
    }
    // method to sign up a new user
    public static User registerUser(Scanner scanner,String filePath) throws IOException {
        // Load existing users from file
        List<User> users = readUserData(filePath);
        System.out.println("Register as User:");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("SSN: ");
        String ssn = scanner.nextLine();
            // Check if SSN already exists
            for (User user : users) {
                if (user.getssn().equals(ssn)) {
                    System.out.println("This SSN Is Already exist!    Please Try Again");
                    return User.registerUser(scanner, filePath);
                }
            }
            System.out.print("ID: ");
            String id = scanner.nextLine();
        // Check if SSN already exists
        for (User user : users) {
            if (user.getid().equals(id)) {
                System.out.println("This iD Is Already exist! Please Try Again");
                return User.registerUser(scanner, filePath);
            }
        }
                System.out.print("Password: ");
                String password = scanner.nextLine();
                if (password.length()<8){
                    System.out.println("Password Must Be At Least 8 Characters");
                    return registerUser(scanner,filePath);
                }
                System.out.print("Confirmatory Password: ");
                String conPassword = scanner.nextLine();
                if (!password.equals(conPassword)){
                    System.out.println("Password Dose not Match,Please try again ");
                    return registerUser(scanner,filePath);
                }
            System.out.print("Phone Number: ");
            String phoneNumber = scanner.nextLine();
            if(phoneNumber.length()!=11){
                System.out.println("You Have Entered Wrong Phone NUmber!Please Try Again");
                return User.registerUser(scanner, filePath);
            }
            System.out.print("Email: ");
            String email = scanner.nextLine();
            if(!email.contains("@gmail.com") && !email.contains("@yahoo.com")){
                System.out.println("You Have Entered Invalid E-Mail!");
                System.out.println("Please Make Sure that Your E-Mail contains: @gmail.com OR @yahoo.com");
                return User.registerUser(scanner, filePath);
            }
            System.out.print("Age: ");
            int age = scanner.nextInt();
            scanner.nextLine();
            if(age<21){
                System.out.println("You Are Under The legal Age");
                return User.registerUser(scanner, filePath);
            }
            System.out.print("Gender: ");
            String gender = scanner.nextLine();

            System.out.print("Nationality: ");
            String nationality = scanner.nextLine();


        return new User(firstName, lastName, ssn, id, password, phoneNumber, email, age, gender, nationality);
    }
    //method to login
    public static User loginUser(Scanner scanner, List<User> users) {
        System.out.println("User Login:");
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.id.equals(id) && user.password.equals(password)) {
                System.out.println("Welcome Back "+user.firstName);
                return user;
            }
        }
        System.out.println("Invalid user credentials.");
        return null;
    }
    // method to viwe user data_info
    public void viewUserData() {
        System.out.println("User Information:-");
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("SSN: " + ssn);
        System.out.println("ID: " + id);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Email: " + email);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Nationality: " + nationality);
    }

    // method to join the attributes with ',' in the array
    public String toString() {
        return String.join(",", firstName, lastName, ssn, id, password, phoneNumber, email, String.valueOf(age), gender, nationality);
    }
}