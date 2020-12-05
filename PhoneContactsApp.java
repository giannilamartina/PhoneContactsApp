import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class PhoneContactsApp {
// declare class variables
  static final int MAX_SIZE = 100;
  static final int MAX_FIELDS = 3;

	public static void main(String[] args) throws IOException {
		// declare variables
	  String[][]contacts = null;
	  Scanner input = new Scanner(System.in);
	  int choice;
	  int size = 0;

		do {
			// display menu
			System.out.println();
			System.out.println("1) read contacts from file");
			System.out.println("2) display contacts");
			System.out.println("3) show number of contacts");
			System.out.println("4) add a contact");
			System.out.println("5) remove a contact");
			System.out.println("6) update phone for a contact");
			System.out.println("7) sort contacts by last name");
			System.out.println("8) write contact list to file");
			System.out.println("9) exit\n");
			System.out.print("Enter your choice: ");
			choice = Integer.parseInt(input.nextLine());

			// get input from user

			// use switch to call desired methods based on choice
			// pay attention to updating variables based on the method
			// for example, when adding a contact,
			// we will assign the result of the method to the contacts array
			// add one case statement at a time as you write each method
			// test it and make sure it works before moving on to the next one

			if (size == 0 && (choice >= 2 && choice <= 8)) {
			  System.out.println("There are 0 contacts in your list\n");
			}

			//add else statement here

			switch(choice) {
				case 1:
					// enter the code for the first option
				  try {
				    contacts = readContactsFromFile(input);
				    size = countContacts(contacts);
				  }
				  catch (IOException e) {
				    System.out.println(e.getMessage());
				  }
					break;

				case 2:
				  displayContacts(contacts, size, input);
				  break;
				case 3:
				  System.out.println("There are " + size + " contacts in your list");
				  break;
				case 4:
				  size = addContact(contacts, size, input);
				  break;
				case 5:
				  size = deleteContact(contacts, size, input);
				  break;
				case 6:
				  updateContact(contacts, size, input);
				  break;
				case 7:
				  sortContacts(contacts, size);
				  break;
				case 8:
				  try {
				    writeContactsToFile(contacts, size, input);
				  }
				  catch(IOException ex) {
				    System.out.println(ex.getMessage());
				  }
				  break;
				case 9:
				  System.out.println("Program ended");
				  break;
				default:
				  System.out.println("Invalid Choice! Choice must be in range 1 to 9.");
				  break;
			}
			System.out.println();

		} while(choice != 9);// finish the while here

	}


	// start writing methods here

	public static int addContact(String[][] contacts, int size, Scanner input) {
	  String fname, lname;
    String phone;
    System.out.print("Enter the first name: ");
    fname = input.nextLine();
    System.out.print("Enter the last name: ");
    lname = input.nextLine();
    System.out.print("Enter the phone number: ");
    phone = input.nextLine();
    contacts[size] = new String[] {fname, lname, phone};
    return ++size;
  }

  public static int countContacts(String[][] contacts) {
    int count = 0;
    if (contacts == null) {
      return 0;
    }
    for (int i = 0; i < contacts.length; ++i) {
      if(contacts[i][0] != null) {
        ++count;
      }
    }
    return count;
  }

  public static int deleteContact(String[][] contacts, int size, Scanner input) {
    String fname, lname;
    System.out.print("Enter the first name of the contact: ");
    fname = input.nextLine();
    System.out.println("Enter the last name of the contact: ");
    lname = input.nextLine();
    int index = -1;
    for (int i = 0; i < size; ++i) {
      if (contacts[i][0].equalsIgnoreCase(fname) && contacts[i][1].equalsIgnoreCase(lname)) {
        index = i;
        break;
      }
    }
    if (index != -1) {
      System.arraycopy(contacts, index+1, contacts, index, MAX_SIZE-1-index);
      System.out.println("Deleting contact");
    }
    else {
      System.out.println("Requested contact was not found!");
    }
    return --size;
  }

  public static void displayContacts(String[][] contacts, int size, Scanner input) {
    input.useDelimiter("");
    for (int i = 0; i < size; ++i) {
      System.out.println(Arrays.toString(contacts[i]));
      if((i + 1) % 5 == 0) {
        System.out.println("Enter any key to continue...");
        input.nextLine();
      }
    }
  }

  public static String[][] readContactsFromFile(Scanner input) throws IOException {
    String[][] contacts = new String[MAX_SIZE][MAX_FIELDS];
    System.out.print("Enter the name of the input text file: ");
    String filename = input.nextLine() + ".txt";
    FileInputStream is = new FileInputStream(filename);
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    String line = br.readLine();
    int index = 0;
    while (line != null) {
      String[] tokens = line.split(",");
      contacts[index] = tokens;
      ++index;
      line = br.readLine();
    }
    is.close();
    br.close();
    return contacts;
}

  public static void sortContacts(String[][] contacts, int size) {
    String[] temp;
    for (int i = 0; i < size - 1; ++i) {
      for (int k = 0; k < size - i - 1; ++k) {
        String lname1 = contacts[k][1];
        String lname2 = contacts[k+1][1];
        if (lname1.compareTo(lname2) > 1) {
          temp = contacts[k];
          contacts[k] = contacts[k+1];
          contacts[k+1] = temp;
        }
      }
    }
  }

  public static void updateContact(String[][] contacts, int size, Scanner input) {
    String fname, lname;
    String newphone;
    int index = -1;
    System.out.println("Updating contact");
    System.out.print("Enter the first name of the contact: ");
    fname = input.nextLine();
    System.out.print("Enter the last name of the contact: ");
    lname = input.nextLine();
    for (int i = 0; i < size; ++i) {
      if (contacts[i][0].equalsIgnoreCase(fname) && contacts[i][1].equalsIgnoreCase(lname)) {
        index = i;
        break;
      }
    }
    if (index != -1) {
      System.out.print("Enter new number: ");
      newphone = input.nextLine();
      contacts[index][2] = newphone;
      System.out.println("The contact has been updated successfully!");
    }
    else {
      System.out.println("Sorry! There is no contact with that name");
    }
  }

  public static void writeContactsToFile(String[][] contacts, int size, Scanner input) throws IOException {
    String outfilename;
    System.out.print("Enter the filename to write: ");
    outfilename = input.nextLine() + ".txt";
    FileOutputStream writer = new FileOutputStream(outfilename);
    for (int i = 0; i < size; ++i) {
      String dataTowrite = String.format("%s, %s, %s", contacts[i][0], contacts[i][1], contacts[i][2]);
      byte[] dataInbytes = dataTowrite.getBytes();
      writer.write(dataInbytes);
      if (i < size - 1) {
        writer.write("\n".getBytes());
      }
      writer.flush();
      writer.close();
    }
  }
}
