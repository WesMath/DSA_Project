import java.io.*;
 import java.lang.Math.*;

class Driver{

   static BufferedReader stdin = new BufferedReader (new InputStreamReader(System.in));


   public static void main(String[] args) throws IOException{
      String menu = "0.  Close the restaurant.\n1.   Customer party enters the restaurant.\n"+
      "2.   Customer party is seated and served.\n3.   Customer party leaves the restaurant.\n"+
      "4.   Add a table.\n5.   Remove a table.\n6.   Display available tables.\n"+
      "7.   Display info about waiting customer parties.\n8.   Display info about customer parties being served.";
      String input = "";
      System.out.println(menu);
      while(input.equals("0")){
         System.out.println("Please make your menu selection now: ");
         input = stdin.readLine().trim();
         System.out.println(input);//echoed input
         switch(input){
            case "0"://0.  Close the restaurant.
               break;
            case "1"://Customer party enters the restaurant.
            case "2"://Customer party is seated and served.
            case "3"://Customer party leaves the restaurant.
            case "4"://Add a table.
            case "5"://Remove a table.
            case "6"://Display available tables.
            case "7"://Display info about waiting customer parties.
            case "8"://Display info about customer parties being served.
            default:
               System.out.println("Invalid menu selection; please try again.");
               break;
         }
      }
      System.out.println("The restaurant is closing...");
   }
   
}