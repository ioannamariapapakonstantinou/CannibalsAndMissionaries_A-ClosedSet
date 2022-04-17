import java.util.ArrayList;
/*Ston pinaka matrix[2][2] exoume 
 *	 0 1
 *0 [i][ii]   Sto i ->Cannibals on the left side, ii ->Cannibals on the right side
 *1 [iii][iv] Sto iii ->Missionaries on the left side, iv ->Missionaries on the right side
 * 
 * 
 * */

public class State implements Comparable<State>{

	private static int N, M , K ;
	private int C1, M1;// c1/m1: cannibals/missionaries on the boat
	
	private boolean boat= false ; //false-> left, true-> right
	int matrix[][];
	private int score=0;
    private State father = null;
 

	
	
	State(int n, int M, int k)
	{
		this.N = n;
		this.M = M;
		this.K = k;
	
		this.matrix = new int[2][2]; 
		this.matrix[0][0] = this.N;
		this.matrix[1][0] = this.N;
		this.matrix[0][1] = 0;	
		this.matrix[1][1] = 0;
	}
	
	 State(int[][] matrix)
	    {	
	        this.setMatrix(matrix);
	    }
	
	int[][] getMatrix() 
	{
		return this.matrix;
    }
	
	
	

	void setMatrix(int[][] matrix) 
	{
		this.matrix = new int[2][2];
	    for(int i = 0; i < 2; i++)
	    {
	    	for(int j = 0; j < 2; j++)
	        {
	    		this.matrix[i][j] = matrix[i][j];
	               
	        }
	    }
	        
	 }
	

	 
	 void setFather(State father)
	{
	   this.father = father;
    }	    
	    
	    
	boolean isFinal()
 	{
 	//all + boat on the right shore
		if (matrix[0][0]!=0)return false;
		if (matrix[1][0]!=0)return false;
		
		
		if (boat!=true)
			return false;
		return true;
 	}
	
	 public void setBoat(boolean a){this.boat = a;}
	 public boolean getBoat(){return this.boat;}
  
	 boolean isValid()
  	{
  	//check validity
		// numCrossings, Cannibals-Missionaries number on both shores and boat
	  if (K==1 && 2*N>M)return false;
	  if (M<1 || K<=0)return false;
	  if ((this.matrix[0][0]<0) ||(this.matrix[0][1]<0)||(this.matrix[1][0]<0)||(this.matrix[1][1]<0))return false;
	  if (this.C1 + this.M1 ==0)return false;
  	  if ((this.matrix[0][0]>this.matrix[1][0]) && this.matrix[1][0]!=0)return false;
  	  if ((this.matrix[0][1]>this.matrix[1][1])&& this.matrix[1][1]!=0 )return false;
  	  if (this.C1 > this.M1 && this.M1!=0)return false;
 
  	  return true;
  	}
	
	
	 void print()
	 {	
		 System.out.println("____________________________________");
		 if (this.getFather()!=null){
			
			 if (boat){
		    	 System.out.println("\t\t    |---->>>---|");
			 }else {
			     System.out.println("\t\t    |----<<<---|");
			 }
		     System.out.println("\t\t      "+ this.C1 + "C "+ this.M1 +"M");//boat   
		 }		 
	 	
			System.out.print(this.matrix[0][0] + " C |----------|"+ this.matrix[0][1]+" C" );
		System.out.println("\n"+this.matrix[1][0]+ " M |----------|" +  this.matrix[1][1]  +" M");
	  }
	
	 State getFather()
	{ 
		 return this.father;
	}
	 private void updateMatrix(int val, int i, int j) 
	 {
		 this.matrix[i][j] = val;
	 }
	 
	boolean goRight(int a, int b)
	{
		this.C1= a;
		this.M1 = b;
	    updateMatrix((a+ this.matrix[0][1]), 0,1);
	    updateMatrix( this.matrix[0][0] - a, 0,0);	
	    updateMatrix(b+ this.matrix[1][1], 1,1);
	    updateMatrix(this.matrix[1][0] - b, 1,0);
	 
	    if (!isValid())return false;
		setBoat(true);
		
	     return true;
	}
	boolean goLeft(int a, int b)
	{
		this.C1 = a;
		this.M1 = b;
		updateMatrix(a+ this.matrix[0][0], 0,0);
	    updateMatrix( this.matrix[0][1] - a, 0,1);
		updateMatrix(b+ this.matrix[1][0], 1,0);
	    updateMatrix(this.matrix[1][1] - b, 1,1);
	 
	    if (!isValid())return false;
	    setBoat(false);

		return true;
	 }
	 int getScore() {return this.score;}

	 void setScore(int score) {this.score = score;}
	 
	 ArrayList<State> getChildren(int heuristic)
	 {
		 ArrayList<State> children = new ArrayList<State>();
	     if(!this.boat)//an to boat einai aristera meta 8a paei deksia
	     {
	    	 for (int a= 0; a<=M; a++){
	    		 for (int b=0; b<=M-a;b++){
	    			
					 State child = new State(this.matrix); 
					
					 if (child.goRight(a, b)){
						 child.evaluate(heuristic);
					            
					    
						 children.add(child);
					     child.setFather(this);
					     child.getFather().evaluate1(heuristic, this.matrix[0][1],this.matrix[1][1]);	
						 this.score += child.getFather().score ;
					
					        	
					        	
					 }
				 }
	         }
	     }
	     else{
	    	 for (int a= 0; a<=M; a++){
	    		 for (int b=0; b<=M-a;b++){
					 State child = new State(this.matrix); 
				     if(child.goLeft(a, b)){
				    	 child.evaluate(heuristic);	
				        
				         children.add(child);
				         child.setFather(this); 
				         child.getFather().evaluate1(heuristic, this.matrix[0][1],this.matrix[1][1]);
				       	 this.score += child.getFather().score ;
					     
				     }
				 }
	       	 }

	     }
	        
	     return children;
	 }
	 private void evaluate(int heuristic)
	 {
		 switch (heuristic)
		 {
		 	case 1:
		 		this.heuristic1();
				break;
			case 2:
				this.heuristic2();
				break;
			default:
				break;
		}
	 }
	 private void evaluate1(int heuristic,int a,int b)
	 {
		 switch (heuristic)
		 {
		 	case 1:
		 		this.g1(a,b);
				break;
			case 2:
				this.g2(a,b);
				break;
			default:
				break;
		}
	 }
	 private void g1(int a, int b)
	 {
		 int w = a+b;
		 if (boat && w>0)this.score = 2*w;
		 else if (!boat && w==1)this.score = 1;
		 else if (!boat && w>1)this.score = 2*w-3;
		 else if (w==0)this.score = 0;	
	 }
	 private void g2(int a, int b){
		 int w = a+b;
		 if (boat && w>0)this.score = 2*w;
		 else if (!boat && w==0)this.score = 1;
		 else if (w==0)this.score = 0;
	 }
	 private void heuristic1()
	 {
		 int w = this.matrix[0][0]+ this.matrix[1][0];
		 if (boat && w>0)this.score = 2*w;
		 else if (!boat && w==1)this.score = 1;
		 else if (!boat && w>1)this.score = 2*w-3;
		 else if (w==0)this.score = 0;	
	 }
	 private void heuristic2(){
		 int w = this.matrix[0][0]+ this.matrix[1][0];
		 if (boat && w>0)this.score = 2*w;
		 else if (!boat && w==0)this.score = 1;
		 else if (w==0)this.score = 0;
	 }
	 @Override
	 public int compareTo(State s)
	    {
	        return Double.compare(this.score, s.score); // compare based on the heuristic score.
	 }
	 @Override
	    public boolean equals(Object obj)
	    {
	       
		 if(	this.boat !=((State)obj).boat)return false;
	        // check for equality of numbers in the tiles.
	        for(int i = 0; i < 2; i++)
	        {
	            for(int j = 0; j < 2; j++)
	            {
	                if(this.matrix[i][j] != ((State)obj).matrix[i][j])
	                {
	                    return false;
	                }
	            }
	        }

	        return true;
	    }

	    // override this for proper hash set comparisons.
	    @Override
	    public int hashCode()
	    {
	        return this.score * 2*M *K + this.identifier();
	    }
	    int identifier()
	    {
	        int result = 0;
	        for(int i = 0; i < 2; i++)
	        {
	            for(int j = 0; j < 2; j++)
	            {
	                // a unique sum based on the numbers in each state.
	                // e.g., for i=j=0 in the fixed initial state --> 3^( (0*0) + 0) * 8 = 1 + 8 = 9
	                // for another state, this will not be the same
	                result += Math.pow(this.matrix[i][j], (this.matrix[i][j] * i) + j) * this.matrix[i][j];
	            }
	        }
	        return result;
	    }
	 
}
