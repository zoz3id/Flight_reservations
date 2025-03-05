import java.io.*;
import java.util.*;
class Admin extends Person {
    String rank;
    //constructor
    public Admin(String firstName, String lastName, String ssn, String id, String password, String phoneNumber, String email, String rank) {
        super(firstName, lastName, ssn, id, password, phoneNumber, email);
        this.rank = rank;
    }
    // getters
    public String getRank() {
        return rank;
    }

    public String getlastname2() {
        return lastName;
    }
    public String getssn2() {
        return ssn;
    }
    public String getfirstname2() {
        return firstName;
    }
    public String getid2() {
        return id;
    }public String getphonenum2() {
        return phoneNumber;
    }public String getpassword2() {
        return password;
    }public String getemail2() {
        return email;
    }

    //method to save data of addmin
    public void saveAdminData(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(this.toString());
            writer.newLine();
        }
    }
    //method to crate and read data form file
    public static List<Admin> readAdminData(String filePath) throws IOException {
        List<Admin> admins = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                admins.add(new Admin(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]));
            }
        }
        return admins;
    }
    //method to login as admin
    public static Admin loginAdmin(Scanner scanner, List<Admin> admins) {
        System.out.println("Admin Login:");
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        // essintial admin
        if (id.equals("admin") && password.equals("admin123")) {
            System.out.println("Welcome Back Boss!");
            return new Admin("Boss", "Admin", "000000000", "admin", "admin123", "0000000000", "admin@example.com", "boss Admin");
        }

        // Check another admins acc.
        try {
            for (Admin admin : admins) {
                if (admin.id.equals(id) && admin.password.equals(password)) {
                    System.out.println("Welcome Back "+ admin.firstName);
                    return admin;
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred during admin login.");
        }

        System.out.println("Invalid admin credentials.");
        return null;
    }
    // Method to register a new admin
    public static Admin registerAdmin(Scanner scanner, String filePath)throws IOException  {
        // Load existing admin from
        List<Admin> admins = readAdminData(filePath);
        System.out.println("Admin Registration:");
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter SSN: ");
        String ssn = scanner.nextLine();
        // Check if SSN already exists
        for (Admin newadmin : admins) {
            if (newadmin.getssn2().equals(ssn)) {
                System.out.println("This SSN Is Already exist!    Please Try Again");
                return Admin.registerAdmin(scanner, filePath);
            }
        }
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        // Check if id already exists
        for (Admin newadmin : admins) {
            if (newadmin.getid2().equals(id)) {
                System.out.println("This id Is Already exist!    Please Try Again");
                return Admin.registerAdmin(scanner, filePath);
            }
        }
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        if (password.length()<8){
            System.out.println("Password Must Be At Least 8 Characters");
            return registerAdmin(scanner,filePath);
        }
        System.out.print("Confirmatory Password: ");
        String conPassword = scanner.nextLine();
        if (!password.equals(conPassword)){
            System.out.println("Password Dose not Match,Please try again ");
            return registerAdmin(scanner,filePath);
        }
        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        if(!email.contains("@gmail.com") && !email.contains("@yahoo.com")){
            System.out.println("You Have Entered Invalid E-Mail!");
            System.out.println("Please Make Sure that Your E-Mail contains: @gmail.com OR @yahoo.com");
            return registerAdmin(scanner, filePath);
        }
        System.out.print("Enter Rank: ");
        String rank = scanner.nextLine();

        Admin newAdmin = new Admin(firstName, lastName, ssn, id, password, phoneNumber, email, rank);

        // Save the new admin to the file
            newAdmin.saveAdminData(filePath);
        System.out.println("Admin registered successfully!");
        return newAdmin;
    }

    // method to join the attributes with ',' in the array
    public String toString() {
        return String.join(",", firstName, lastName, ssn, id, password, phoneNumber, email, rank);
    }
}