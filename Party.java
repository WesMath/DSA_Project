public class Party{
   private final String name;
   private final int size;
   private final boolean hasPet;
   //aggregates name, size, and section preference
   
  /**
   * Create a new object of Party.
   * @param n Set n to name field of Party. 
   * @param s Set s to size field of Party.
   * @param p Set p to hasPet field of Party.
   */
   public Party(String n, int s, boolean p){
      name = n;
      size = s;
      hasPet = p;
   }
   
  /**
   * Return the name of Object.
   * @return Return the name of Object.
   */
   public String getName(){
      return name;
   }
   
  /**
   * Return the size of Object.
   * @return Return the size of Object.
   */
   public int getSize(){
      return size;
   }
   
  /**
   * Return the hasPet of Object.
   * @return Return the hasPet of Object.
   */
   public boolean getHasPet(){
      return hasPet;
   }
   
  /**
   * Display all the information of Party.
   */   
   public String toString(){
      return "Name: "+name+"\nSize: "+size+"\nHas pets: "+hasPet;
   }

}
