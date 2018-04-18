public class Table{
//aggregates information related to its unique name, maximum capacity, section, and the Party
   private final String name;
   private final int capacity;
   private Party guests;
   
   public Table(String unique_name, int n){
      name = unique_name;
      capacity = n; //Capacity can never change for a given table- tables can be removed or added instead
      guests = null;
   }
   
   public void seat(Party p){
      guests = p;
   }
   
   public Party getParty(){
      return guests;
   }
   
   public int getCapacity(){
      return capacity;
   }
   
   
   public Party removeParty(){
      //Party leaves the restaurant
      Party temp = guests;
      guests = null;
      return temp;
   }
   
   public String getName(){
      return name;
   }
   
   public String toString(){
      return "Name: "+name+"\nCapacity: "+capacity;
   }
}