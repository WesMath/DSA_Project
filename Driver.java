import java.io.*;
 import java.lang.Math.*;

class Driver{

   static BufferedReader stdin = new BufferedReader (new InputStreamReader(System.in));



   public static void main(String[] args) throws IOException{
      String input = "";
      int temp_size, amount;//Prevents repeated method calls for every loop evaluation
      String output;//Collects relevant output and waits to perform file I/O once
      boolean table_found;
      Table temp_table;
      String temp_name;
      /*
      FullTables, EmptyNoPets, EmptyPets, PartiesInLine
      */
      ListArrayBasedPlus<Table> FullTables = new ListArrayBasedPlus<Table>();//will remain empty until restaurant opens
      ListArrayBasedPlus<Table> EmptyNoPets = new ListArrayBasedPlus<Table>();
      ListArrayBasedPlus<Table> EmptyPets = new ListArrayBasedPlus<Table>();
      ListArrayBasedPlus<Table> lref;//Reference variable to improve code readability, at the small cost of 4 bytes
      ListArrayBasedPlus<Party> PartiesInLine = new ListArrayBasedPlus<Party>();//will remain empty until restaurant opens
      System.out.println("How many tables does your pet-friendly section have?");
      amount = Integer.parseInt(stdin.readLine().trim());
      System.out.println(amount);//echo input
      for(int i = 0; i < amount; i++){//No tables have been placed in the No-pets section yet, so only check this section for uniqueness
         table_found = true;
         while(table_found){
            System.out.println("Please give the name for Table #"+i+": ");
            input = stdin.readLine().trim();
            System.out.println(input);//echoed input
            table_found = seqSearchTableName(EmptyPets, input);
            if(table_found){
               System.out.println("That table already exists; please try again.");
            }else{
               System.out.println("Please indicate the seating capacity for this table: ");
               temp_size = Integer.parseInt(stdin.readLine().trim());
               System.out.println(temp_size);//Echo input
               if(temp_size <= 0){
                  System.out.println("Invalid table size.");
               }else{
                  temp_table = new Table(input, temp_size);
                  EmptyPets.add(binarySearchCapacity(EmptyPets, temp_size), temp_table);
                  System.out.println("Table successfully added.");
               }
            }
         }
      }
      System.out.println("How many tables does your no-pets section have?");
      amount = Integer.parseInt(stdin.readLine().trim());
      System.out.println(amount);//echo input
      for(int i = 0; i < amount; i++){//check tables in both this section and the pet section for uniqueness
         table_found = true;
         while(table_found){
            System.out.println("Please give the name for Table #"+i+": ");
            input = stdin.readLine().trim();
            System.out.println(input);//echoed input
            table_found = seqSearchTableName(EmptyPets, input) || seqSearchTableName(EmptyNoPets, input);
            if(table_found){
               System.out.println("That table already exists; please try again.");
            }else{
               System.out.println("Please indicate the seating capacity for this table: ");
               temp_size = Integer.parseInt(stdin.readLine().trim());
               System.out.println(temp_size);//Echo input
               if(temp_size <= 0){
                  System.out.println("Invalid table size.");
               }else{
                  temp_table = new Table(input, temp_size);
                  EmptyNoPets.add(binarySearchCapacity(EmptyNoPets, temp_size), temp_table);
                  System.out.println("Table successfully added.");
               }
            }
         }
      }
      
      String menu = "0.  Close the restaurant.\n1.   Customer party enters the restaurant.\n"+
      "2.   Customer party is seated and served.\n3.   Customer party leaves the restaurant.\n"+
      "4.   Add a table.\n5.   Remove a table.\n6.   Display available tables.\n"+
      "7.   Display info about waiting customer parties.\n8.   Display info about customer parties being served.\n";
      
      
      System.out.println(menu);
      
      while(!input.equals("0")){
         System.out.println("Please make your menu selection now: ");
         input = stdin.readLine().trim();
         System.out.println(input);//echoed input
         switch(input){
            case "0"://0.  Close the restaurant.
               break;
            case "1"://Customer party enters the restaurant.
             System.out.print("Enter customer name : ");
				String nameForParty = stdin.readLine();
				System.out.println(nameForParty);
				int index2  = binarySearchPartyName(FullTables, nameForParty);
				boolean find = false;
				if(FullTables.get(index2).getName().compareTo(nameForParty) == 0) {
					find = true; 
				}
				int number = 0;
				boolean hasPet = false;
				while(find) {
					System.out.println("There already exists a customer with this name in the restaurant.");
					System.out.println("			Please select another name.");
					System.out.print("Enter customer name: ");
					nameForParty = stdin.readLine();
					System.out.println(nameForParty);
					index2 = binarySearchPartyName(FullTables, nameForParty);
					if(FullTables.get(index2).getName().compareTo(nameForParty) == 0) {
						find = true; 
					}
				}
				if(!find) {
					System.out.print("Enter number of seats for customer " + nameForParty + ": ");
					String numberOfString = stdin.readLine();
					number = Integer.parseInt(numberOfString);
					System.out.println(number);
					System.out.print("Does your part have pets (Y/N)?");
					String withPet = stdin.readLine();
					System.out.println(withPet);
					if(withPet.equals("Y")) 
						hasPet = true;	
				}
				Party newParty = new Party(nameForParty, number, hasPet);
				//If there is an available table, be seated immediately, otherwise waiting in line.
				if(EmptyNoPets.isEmpty() && EmptyPets.size() == 0) {
					PartiesInLine.add(PartiesInLine.size()-1, newParty);
				}
				else {
					//Checking there is a line or not and there is an available table or not.
					if(hasPet && PartiesInLine.isEmpty()) {
						int numberForAvilable = binarySearchCapacity(EmptyPets, number);
						if(numberForAvilable != -1) {//TODO change this condition
							//Add to FullTables(Sorted);
							Table table = EmptyPets.get(numberForAvilable);
							FullTables = insertTable(FullTables, table);
							EmptyPets.remove(numberForAvilable);	
						}
						else {
							PartiesInLine.add(PartiesInLine.size()-1, newParty);
						}
					}
					else if(!hasPet && PartiesInLine.isEmpty()) {
						int numberForAvilable = binarySearchCapacity(EmptyNoPets, number);
						if(numberForAvilable != -1) {//TODO change condition
							Table table = EmptyNoPets.get(numberForAvilable);
							FullTables = insertTable(FullTables, table);
							EmptyNoPets.remove(numberForAvilable);	
						}
						else {
							PartiesInLine.add(PartiesInLine.size()-1, newParty);
						}
					}
					else {
						PartiesInLine.add(PartiesInLine.size()-1, newParty);
					}
				}
   				break;
            case "2":
			 //Customer party is seated and served.
			 if(!PartiesInLine.isEmpty()) {
					boolean served = false;
					int index = 0;
					while(!served && index < PartiesInLine.size()) {
						Party party = PartiesInLine.get(index);
						boolean petSection = party.getHasPet();
						int numberOfPeople = party.getSize();
						if(petSection) {
							int index1 = binarySearchCapacity(EmptyPets, numberOfPeople);
							if(index1 != -1) {//TODO change condition
								Table table = EmptyPets.get(index1);
								FullTables = insertTable(FullTables, table);
								EmptyPets.remove(index1);
								served = true;
								System.out.println("Serving Customer " + 
										party.getName() + " party of " + 
										party.getSize() + "(Pet) at table " + 
										table.getName() + " with " + table.getCapacity() + " seats.");
							}
							else {
								index++;
								System.out.println("Could not find a table with " + 
										party.getSize() + " seats for customer " + 
										party.getName() + " !");
							}
						}
						else {
							int index1 = binarySearchCapacity(EmptyNoPets, numberOfPeople);
							if(index1 != -1) {//TODO change condition
								Table table = EmptyNoPets.get(index1);
								FullTables = insertTable(FullTables, table);
								EmptyNoPets.remove(index1);
								served = true;
								System.out.println("Serving Customer " + 
										party.getName() + " party of " + 
										party.getSize() + "(No Pet) at table " + 
										table.getName() + " with " + table.getCapacity() + " seats.");
							}
							else {
								index++;
								System.out.println("Could not find a table with " + 
										party.getSize() + " seats for customer " + 
										party.getName() + " !");
							}
						}
					}
					if(!served) {
						System.out.println("No party can be served!");
					}
				}
				else {
					System.out.println("No customers to serve!");
				}
				break;
            case "3":
			 //Customer party leaves the restaurant.
			if(FullTables.isEmpty()) {
					System.out.println("No customer is being served");
				}
				else {
					System.out.print("Enter the name of the customer that wants to leave: ");
					String name = stdin.readLine();
					System.out.println(name);
					int index = binarySearchPartyName(FullTables, name);
					if(index == -1) {//TODO change condition
						boolean find11 = false;
						int sizeOfFullTable = PartiesInLine.size();
						int low = 0;
						int high = sizeOfFullTable -1;
						int mid = 0;
						while(low < high) {
							mid = (low + high)/2;
							if(name.compareTo(PartiesInLine.get(mid).getName()) < 0 || name.compareTo(PartiesInLine.get(mid).getName()) == 0) {
								high = mid;
							}
							else {
								low = mid +1;
							}
						}
						if(name.compareTo(PartiesInLine.get(mid).getName()) == 0) {
							find11 = true;
						}
						
						if(find11) {
							System.out.println("Customer " + name + "is not being served but waiting to be seated");
						}
						else {
							System.out.println("No That Person!");
						}
					}
					else {
						Table table = FullTables.get(index);
						System.out.println("Table " + table.getName() + " with " + table.getCapacity() + " seats has been freed");
						Party party = table.getParty();
						boolean hasPets = party.getHasPet();
						String ss = "";
						if(hasPets) {
							ss = "(Pet)";
							EmptyPets = insertTable(EmptyPets, table);
						}
						else {
							ss = "(No Pet)";
							EmptyNoPets = insertTable(EmptyNoPets, table);	
						}
						System.out.println("Customer Customer " + 
								party.getName() + " party of " + 
								party.getSize() + ss + " is leaving the restaurant." );
						FullTables.remove(index);
					}
				}
				break;
            
            case "4"://Add a table.
               System.out.println("To which section would you like to add this table?(P/N)");
					output = stdin.readLine().trim();
					System.out.println(output);//echo their decision
					if(!(output.equals("P")	||	output.equals("N"))){//Validate input
						System.out.println("Invalid section selection; please try again.");
						break;
					}
					table_found	= true;
				   while(table_found){
                  table_found = false;
   					System.out.println("Please provide a unique name for this table: ");
   					temp_name =	stdin.readLine().trim();
   					System.out.println(temp_name);//echo input
   					
                  //The filled tables are kept sorted by PARTY name, not TABLE name, so sequential search is necessary
                  table_found = seqSearchTableName(EmptyPets, temp_name) || seqSearchTableName(EmptyNoPets, temp_name)|| seqSearchTableName(FullTables, temp_name);
                  
                  if(!table_found){//We know the name is unique and can go ahead and add it to the proper section
                     System.out.println("Please give the seating capacity for this table: ");
                     temp_size = Integer.parseInt(stdin.readLine().trim());
                     System.out.println(temp_size);//echo input
                     if(output.equals("P")){
                        lref = EmptyPets;
                     }else{//Already validated input, so no need for second conditional check
                        lref = EmptyNoPets;
                     }
                     //insert new table into the correct spot, maintaining order of capacity
                     temp_table = new Table(temp_name, temp_size);
                     lref.add((binarySearchCapacity(lref, temp_size)), temp_table);
                     System.out.println("Table successfully added.");
                  }
               }
               break;
            case "5"://Remove a table.
               //Cannot remove a table that currently has a Party seated there
               //Report that the removal operation failed after only checking the two collections of available tables
               //Both of these collections are kept sorted by capacity, not name, so we must sequentially search
               System.out.println("Please enter the name of the table to remove: ");
               output = stdin.readLine().trim();
               System.out.println(output);//echo the inputted value
               temp_size = EmptyNoPets.size();
               table_found = false;
               for(int i = 0; i < temp_size; i++){
                  if(EmptyNoPets.get(i).getName().equals(output)){
                     EmptyNoPets.remove(i);
                     table_found = true;
                     System.out.println("Table "+output+" was removed.");
                     break;//Stops needless execution of the loop- table already removed
                  }
               }
               if(!table_found){
                  temp_size = EmptyPets.size();
                  for(int i = 0; i < temp_size; i++){
                     if(EmptyPets.get(i).getName().equals(output)){
                        EmptyPets.remove(i);
                        table_found = true;
                        System.out.println("Table "+output+" was removed.");
                        break;//Stops needless execution of the loop- table already removed
                     }
                  }
                  if(!table_found){
                     System.out.println("No such available table was found.");
                  }
                }
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
                     output += EmptyNoPets.get(i).toString() + "\nSection: No Pets\n\n";
                  }
                  System.out.println();
                  temp_size = EmptyPets.size();
                  for(int i = 0; i < temp_size; i++){
                     output += EmptyPets.get(i).toString() + "\nSection: Pets\n\n";
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
         System.out.println();//flush buffer before next iteration
      }
      System.out.println("The restaurant is closing...");
   }
   
   public static boolean seqSearchTableName(ListArrayBasedPlus<Table> section, String searchKey){
      int temp_size = section.size();
      for(int i = 0; i < temp_size; i++){
         if(section.get(i).getName().equals(searchKey)){
            return true;
         }
      }
      return false;
   }
   
   public static boolean seqSearchPartyName(ListArrayBasedPlus<Party> line, String searchKey){
      int temp_size = line.size();
      for(int i = 0; i < temp_size; i++){
         if(line.get(i).getName().equals(searchKey)){
            return true;
         }
      }
      return false;
   }
  
    //Given a number, use that number to search whether there is a table in collection is exist.
   public static int binarySearchCapacity(ListArrayBasedPlus<Table> tables, int number) {
		int size = tables.size();
      if(size == 0){
         return 0;
      }
		int low = 0;
		int high = size-1;
		int mid = 0;
		while(low < high) {
			mid = (low + high)/2;
			if(number <= tables.get(mid).getCapacity()) {
         
				high = mid;
			}
			else {
				low = mid+1;
			}
		}
	   if(number <= tables.get(mid).getCapacity()){
		   return mid;//Calling method can decide whether to check for equality or use this position to insert
      }else{
         return mid+1;
      }
   }
   //Insert a table in sorted collections, using binary search to find particular position.
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
	
   //This method is designed for Option Three, return a integer, if it is -1, it means not searchKey in the collection, 
   //otherwise it will return position of searchKey.
   public static int binarySearchPartyName(ListArrayBasedPlus<Table> fullTable, String searchKey) {
		int sizeOfFullTable = fullTable.size();
		int low = 0;
		int high = sizeOfFullTable -1;
		int mid = 0;
		while(low < high) {
			mid = (low + high)/2;
			if(searchKey.compareTo(fullTable.get(mid).getParty().getName()) <= 0) {
				high = mid;
			}
			else {
				low = mid +1;
			}
		}
      return mid;
  }
   
   
}
