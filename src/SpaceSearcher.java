import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;



public class SpaceSearcher
{
    private ArrayList<State> frontier;
    private HashSet<State> closedSet;

    SpaceSearcher()
    {
        this.frontier = new ArrayList<State>();
        this.closedSet = new HashSet<State>();
    }

    public State AClosedSet(State initialState, int heuristic)
    {
    	
        if(initialState.isFinal()) return initialState;
        this.frontier.add(initialState);
        while(this.frontier.size() > 0)
        {
            State currentState = this.frontier.remove(0);
            if(currentState.isFinal()) return currentState;
            if(!this.closedSet.contains(currentState))
            {
                this.closedSet.add(currentState);
	            this.frontier.addAll(currentState.getChildren(heuristic));
                Collections.sort(this.frontier); 
            }
        }
        return null;
    }
    
}
