import java.util.List;


public class AssassinManager {
    private AssassinNode aliveFront; //stores alive and front of kill ring
    private AssassinNode graveyardFront; //stores alive + front list of eliminated assassins


    //produces linked list of nodes (assassin)
    public AssassinManager(List<String> names){
        if(names == null || names.isEmpty())
            throw new IllegalArgumentException("List may not be null or empty."); //throws exception

        //initializes graveyard front list
        graveyardFront = null;

        //initializes alive list
        aliveFront = null;
        for (int i = names.size() - 1 ; i >= 0 ; i--) { //begins backwards
            aliveFront = new AssassinNode(names.get(i), aliveFront);
        }
    }


    //method for print different kill ring outcomes
    public void printKillRing(){
        if(isGameOver()){
            System.out.println("    " + aliveFront.name + " won the game!"); //prints kill ring of winner
        }
        else{
            AssassinNode current = aliveFront;
            while(current != null){
                System.out.println(current);
                if(current.next == null) {
                    System.out.println(aliveFront.name);
                }
                current = current.next;
            }
        }
    }


    //method prints graveyard + killer + or if nothing happens
    public void printGraveyard(){
        AssassinNode current = graveyardFront;
        while(current != null){
            System.out.println("    " + current.name + " was killed by " + current.killer);
            current = current.next;
        }
    }

    //method determines if the game is over
    //true or false
    //true: 1 person in kill ring
    //false
    public boolean isGameOver(){
        return (aliveFront.next == null);
    }

    // method searches for the name given the starting node
    //method also determine if the linkedlists contains the desired name

    private boolean containsHelper(String name, AssassinNode start){ //helper method
        AssassinNode current = start;
        while(current != null){
            if(current.name.equalsIgnoreCase(name)){  //returns true if name is in the linkedlist
                return true;
            }
            current = current.next;
        }
        return false; //otherwise false
    }

    //method checks for name in the kill ring
    public boolean killRingContains(String name){
        return containsHelper(name, aliveFront);  //returns true if name is found; otherwise false
    }

    //method checks if a name is in the graveyard
    public boolean graveyardContains(String name){
        return containsHelper(name, graveyardFront); //returns true if name is found; otherwise false
    }

    //method checks for winner
    public String winner(){
        if(!isGameOver())
            return null;
        return aliveFront.name; //returns the name in the aliveFront of the linked list if the game is over; else false
    }

    //method takes input name of person to be killed + kills the name of the person passed
    //removes name of the person from the kill list to the graveyard;
    public void kill(String name){
        if(isGameOver()){
            throw new IllegalStateException("The game is over.");
        }
        else if(!killRingContains(name)){
            throw new IllegalArgumentException("This name is not in the kill ring."); //exception thrown if game over or kill ring does not contain the input name
        }
        else{
            AssassinNode current = aliveFront;
            //if name is in aliveFront
            if(current.name.equalsIgnoreCase(name)){
                aliveFront = aliveFront.next;
                current.next = graveyardFront;
                graveyardFront = current;
                current = aliveFront;
                while(current.next != null){
                    current = current.next;
                }
                graveyardFront.killer = current.name;
            }

            else {
                while (current.next != null) {
                    if (current.next.name.equalsIgnoreCase(name)) {
                        AssassinNode temp = current.next.next;
                        current.next.next = graveyardFront;
                        graveyardFront = current.next;
                        graveyardFront.killer = current.name;
                        current.next = temp;
                        break;
                    }
                    current = current.next;
                }
            }
        }
    }








    //////// DO NOT MODIFY AssassinNode.  You will lose points if you do. ////////
    /**
     * Each AssassinNode object represents a single node in a linked list
     * for a game of Assassin.
     */
    private static class AssassinNode {
        public final String name;  // this person's name
        public String killer;      // name of who killed this person (null if alive)
        public AssassinNode next;  // next node in the list (null if none)
        
        /**
         * Constructs a new node to store the given name and no next node.
         */
        public AssassinNode(String name) {
            this(name, null);
        }

        /**
         * Constructs a new node to store the given name and a reference
         * to the given next node.
         */
        public AssassinNode(String name, AssassinNode next) {
            this.name = name;
            this.killer = null;
            this.next = next;
        }
    }
}
