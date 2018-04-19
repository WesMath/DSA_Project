public class Table{
//aggregates information related to its unique name, maximum capacity, section, and the Party
   private final String name;
   private final int capacity;
   private Party guests;
   
   /**
    * Create a new object of table.
    * @param unique_name Set unique_name to name of new object.
    * @param n Set n to capacity of new object.
    */
   public Table(String unique_name, int n){
      name = unique_name;
      capacity = n; //Capacity can never change for a given table- tables can be removed or added instead
      guests = null;
   }
   
   /**
    * Set p to guests field.
    * @param p Set p to guests field.
    */
   public void seat(Party p){
      guests = p;
   }
   
   /**
    * Return party of object.
    * @return Return party of object.
    */
   public Party getParty(){
      return guests;
   }
   
  /**
   * Return the capacity of Table.
   * @return Return the capacity of Table.
   */
   public int getCapacity(){
      return capacity;
   }
   
   /**
    * Return guests and set guests field to null.
    * @return Return guests and set guests field to null.
    */
   public Party removeParty(){
      //Party leaves the restaurant
      Party temp = guests;
      guests = null;
      return temp;
   }
   
   /**
    * Return name of Table.
    * @return Return name of Table.
    */
   public String getName(){
      return name;
   }
   
   /**
    * Display all the information of Table.
    */
   public String toString(){
      return "Name: "+name+"\nCapacity: "+capacity;
   }
}
