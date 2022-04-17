import java.io.*;
import java.util.*;



    

public class Cannibals_Missionaries {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
            System.out.println("Cannibals and Missionaries Problem");
            Scanner in = new Scanner(System.in);  
            int N, K, M ;
            System.out.print("Give Number of Cannibals/ Missionaries: ");
            N = in.nextInt();
            System.out.print("Give Capacity of the boat: ");
            M = in.nextInt();
            System.out.print("Give Max Number of Crossings: ");
            K = in.nextInt();
            
            System.out.println(" Cannibals/ Missionaries: "+ N);
            System.out.println(" Boat Capacity          : "+ M);
            System.out.println(" Max Crossings          : "+ K);
            System.out.println("______________________________");
            System.out.println(" Solution: ");
         
            
            State initialState = new State(N, M, K);  // our initial 2x2 state.
            System.out.println("Initial State: ");
            initialState.print();
            System.out.println("******************************");

            
            
           SpaceSearcher searcher = new SpaceSearcher();
        
            long start = System.currentTimeMillis();
           
           State terminalState = searcher.AClosedSet(initialState, 1);
           long end = System.currentTimeMillis();
           if(terminalState == null){ System.out.println("Could not find a solution.");}
		   else{
    			// print the path from beginning to start.
                State temp = terminalState; // begin from the end.
                ArrayList<State> path = new ArrayList<State>();
                path.add(terminalState);
              
                while(temp.getFather() != null )
                {
                    path.add(temp.getFather());             
                    temp = temp.getFather();
                }
    			// reverse the path and print.
               
                
                Collections.reverse(path);
                if (path.size()>K +1)System.out.println("Could not find a solution with crossings <="+ K+". There is one with "+(path.size()-1)+" crossing(s)!");
                else{
	                System.out.println("Finished in "+(path.size()-1)+" crossing(s)!");
	                System.out.println("___________________________________");
	                for(State item: path)
	                {
	                    item.print();
	                    System.out.println("___________________________________");
	                }
	                System.out.println();
	                System.out.println("Search time:" + (double)(end - start) / 1000 + " sec.");  // total time of searching in seconds.        
				}
			}

        }catch (Exception e) {                
           System.err.println(e.toString());
        }    
       

	}

}
