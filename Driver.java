import java.io.*;
 import java.lang.Math.*;

class Driver{

   static BufferedReader stdin = new BufferedReader (new InputStreamReader(System.in));


//TODO add addTable helper method

   public static void main(String[] args) throws IOException{
   
      //TODO set up the restaurant before opening
      String input = "";
      /*
      FullTables, EmptyNoPets, EmptyPets, PartiesInLine
      */
      ListArrayBasedPlus<Table> FullTables = new ListArrayBasedPlus<Table>();
      ListArrayBasedPlus<Table> EmptyNoPets = new ListArrayBasedPlus<Table>();
      ListArrayBasedPlus<Table> EmptyPets = new ListArrayBasedPlus<Table>();
      ListArrayBasedPlus<Party> PartiesInLine = new ListArrayBasedPlus<Party>();

      
      
      String menu = "0.  Close the restaurant.\n1.   Customer party enters the restaurant.\n"+
      "2.   Customer party is seated and served.\n3.   Customer party leaves the restaurant.\n"+
      "4.   Add a table.\n5.   Remove a table.\n6.   Display available tables.\n"+
      "7.   Display info about waiting customer parties.\n8.   Display info about customer parties being served.";
      
      
      System.out.println(menu);
      int temp_size;//Prevents repeated method calls for every loop evaluation
      String output;//Collects relevant output and waits to perform file I/O once
      while(!input.equals("0")){
         System.out.println("Please make your menu selection now: ");
         input = stdin.readLine().trim();
         System.out.println(input);//echoed input
         switch(input){
            case "0"://0.  Close the restaurant.
               break;
            case "1"://Customer party enters the restaurant.
           System.out.println("Welcome to XXX Restaurant!");
				System.out.println("Can you give me a name for your party?");
				String nameForParty = stdin.readLine();
				System.out.println("The name is " + nameForParty);
				boolean find  = binarySearchForName(FullTables, nameForParty);
				int number = 0;
				boolean hasPet = false;
				while(find) {
					System.out.println("Sorry, This name is used by the other party, please use another one");
					nameForParty = stdin.readLine();
					System.out.println("The name will be used: " + nameForParty);
					find = binarySearchForName(FullTables, nameForParty);
				}
				if(!find) {
					System.out.println("How many people are there in your party?");
					String numberOfString = stdin.readLine();
					number = Integer.parseInt(numberOfString);
					System.out.println("Would you like to have dinner in pet section? Y/N");
					String withPet = stdin.readLine();
					if(withPet.equals("Y")) 
						hasPet = true;	
				}
				Party newParty = new Party(nameForParty, number, hasPet);
				//If there is an available table, be seated immediately, otherwise waiting in line.
				if(EmptyNoPets.size() == 0 && EmptyPets.size() == 0) {
					PartiesInLine.add(PartiesInLine.size()-1, newParty);
				}
				else {
					//Checking there is a line or not and there is an available table or not.
					if(hasPet && PartiesInLine.size() == 0) {
						Table avilableTable = binarySearchForTable(EmptyPets, number);
						if(avilableTable != null) {
							//Add to FullTables(Sorted);

						}
					}

				}
				break;
            case "2"://Customer party is seated and served.
            case "3"://Customer party leaves the restaurant.
            case "4"://Add a table.
            case "5"://Remove a table.
               //Cannot remove a table that currently has a Party seated there
               //Report that the removal operation failed after only checking the two collections of available tables
               //Both of these collections are kept sorted by capacity, not name, so we must sequentially search
               break;
            case "6"://Display available tables.
               System.out.println("Current available tables:");
               if(EmptyNoPets.isEmpty() && EmptyPets.isEmpty()){
                  System.out.println("There are no available tables left.");
               }else{
                  output = "";
                  //Section information is maintained by which collection the Table is in, not as a data field, so we manually append
                  temp_size = EmptyNoPets.size();
                  for(int i = 0; i < temp_size; i++){
                     output += EmptyNoPets.get(i).toString() + "\nSection: No Pets\n";
                  }
                  temp_size = EmptyPets.size();
                  for(int i = 0; i < temp_size; i++){
                     output += EmptyPets.get(i).toString() + "\nSection: Pets\n";
                  }
                  System.out.println(output);
               }
               break;
            case "7"://Display info about waiting customer parties.
               //We can use the toString method of the collection, as it directly aggregates the desired objects
               System.out.println("The following parties are waiting to be served:");
               if(PartiesInLine.isEmpty()){
                  System.out.println("No parties are currently waiting to be served.");
               }else{
                  System.out.println(PartiesInLine.toString());
               }
               break;
            case "8"://Display info about customer parties being served.
               System.out.println("Parties currently served:");
               //traverse FullTables, get party, party.toString()
               temp_size = FullTables.size();
               if(temp_size > 0){
                  output = "";
                  for(int i = 0; i < temp_size; i++){
                     output += FullTables.get(i).getParty().toString() + "\n";
                  }
               }else{
                  output = "No parties are currently being served.";
               }
               System.out.println(output);
               break;
            default:
               System.out.println("Invalid menu selection; please try again.");
               break;
         }
      }
      System.out.println("The restaurant is closing...");
   }
 public static boolean binarySearchForName(ListArrayBasedPlus<Table> fullTable, String searchKey) {
		boolean find = false;
		int sizeOfFullTable = fullTable.size();
		int low = 0;
		int high = sizeOfFullTable -1;
		int mid = 0;
		while(low < high) {
			mid = (low + high)/2;
			if(searchKey.compareTo(fullTable.get(mid).getName()) < 0 || searchKey.compareTo(fullTable.get(mid).getName()) == 0) {
				high = mid;
			}
			else {
				low = mid +1;
			}
		}
		if(searchKey.compareTo(fullTable.get(mid).getName()) == 0) {
			find = true;
		}
		else {
			find = false;
		}
		return find;
	}

	public static Table binarySearchForTable(ListArrayBasedPlus<Table> tables, int number) {
		int size = tables.size();
		int low = 0;
		int high = size-1;
		int mid = 0;
		while(low < high) {
			mid = (low + high)/2;
			if(number <= tables.get(mid).capacity) {
				high = mid;
			}
			else {
				low = mid+1;
			}
		}
		if(number <= tables.get(mid).capacity) {
			return tables.get(mid);
		}
		else {
			return null;
		}
	}

	public static ListArrayBasedPlus<Table> insertTable(ListArrayBasedPlus<Table> tables, Table table){
		int size = tables.size();
		int low = 0;
		int high = size-1;
		int mid = 0;
		while(low < high) {
			mid = (low + high)/2;
			if(table.getName().compareTo(tables.get(mid).getName()) <= 0) {
				high = mid;
			}
			else {
				low = mid+1;
			}
		}
		if(table.getName().compareTo(tables.get(mid).getName()) <= 0) {
			tables.add(mid, table);
		}
		else {
			tables.add(mid+1, table);
		}
		return tables;
	}
   
}
