public class Party{
   private final String name;
   private final int size;
   private final boolean hasPet;
   //aggregates name, size, and section preference
   
   public Party(String n, int s, boolean p){
      name = n;
      size = s;
      hasPet = p;
   }
   
   public String getName(){
      return name;
   }
   
   public int getSize(){
      return size;
   }
   
   public boolean getHasPet(){
      return hasPet;
   }
   
   public String toString(){
      return "Name: "+name+"\nSize: "+size+"\nHas pets: "+hasPet;
   }

}
