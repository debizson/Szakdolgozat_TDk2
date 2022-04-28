package BranchABound;

import java.util.StringTokenizer;

import java.util.Set;
import java.util.HashSet;
import java.util.*;


public class BranchAndBound {


    protected  String treeNode;
    protected  int currentLength;
    protected  int kapcsolatitomb [] [];
    protected  LinkedList<Object> list0;
    protected  int varosSzam;
    protected HashSet<Integer> SetAll;
    protected int point;              // in convertStringToSet function it contain last point of current node
    protected int globHeurisztik;   // current heuristic value at the iterator's status
    protected int [] minimumEdgesOfNodesArray;
    protected int bestFoundHeuristicValue;
    protected int j;
    protected HashSet<Integer> SetCurrent;

    public void segedSetAllMaker(){     // 0 is 1., 1 is 2., 2 is 3 point etc. accordingly to index low of array's syntax

        for (int i = 0; i < varosSzam; i++) {
             SetAll.add(i);
        }

    }


    /**
     * 
     * @param list0 given level of tree with nodes
     * @param varosSzam count of graf ponits
     * @param kapcsolatitomb edges of graf
     */
    public BranchAndBound(LinkedList<Object> list0, int varosSzam, int kapcsolatitomb[] [], int [] minimumEdgesOfNodesArray, int j){

        this.list0 = list0;
        this.varosSzam = varosSzam;
        this.kapcsolatitomb = kapcsolatitomb;
        this.minimumEdgesOfNodesArray = minimumEdgesOfNodesArray;
        this.j = j;

    }



    /**
     * 
     * 
     */
    public void checkLeaves(){

        int cutByLevel = 0;

        ListIterator<Object> iter0;

        SetAll = new HashSet<Integer>();

        String leafCurrent;
      
        iter0=list0.listIterator();

        SetCurrent = new HashSet<Integer>();  // at beginnig the set of all points then will be cut with the set of points of current node
 
        segedSetAllMaker();  // makes set of all points of graf
        
        SetCurrent = (HashSet<Integer>)SetAll.clone();  //this will be cut with points of current node
        bestFoundHeuristicValue = 2000000000;

        ///System.out.println(" sc" + SetCurrent);

		while(iter0.hasNext())
		{
            iter0.next();   
            int partuthossz = (int)iter0.next(); // lenght of node
            leafCurrent = String.valueOf(iter0.next()); //  original row of node
            
            int x;
            int newLengthWithGFunction = 0;   

            differeceOfAllAndCurrentPoints(leafCurrent);   // need to get the all ponits sub points of current node 

            newLengthWithGFunction = gFunction(partuthossz);  // first count the above limit of all nodes because
                                                                                    // SetAll has one copy and heuristic fuction modifie it but we need it untoudched

            if(bestFoundHeuristicValue > (x = heurisztik(partuthossz)))  // then count heuristic value of current node
                bestFoundHeuristicValue = x;
           
            int index = list0.indexOf(leafCurrent);   // !!!!!  HERE can be a bug because of similaritiy between ordered node and the original row node for example 1 2 5 7 and
                                                        // 1 2 5 7
            if(index % 3 == 2)
                list0.set(index - 1,newLengthWithGFunction);
            else 
                list0.set(index + 1,newLengthWithGFunction);


            SetCurrent = (HashSet<Integer>)SetAll.clone();

		}
        var iter2= list0.listIterator();

        while(iter2.hasNext()){           // cut if heuristic value is lower then g value 

           
            iter2.next();   
            int gValue = (int)iter2.next(); // g lenght of node
            //System.out.println("gvalue: " + gValue);
            if(gValue >= bestFoundHeuristicValue){

                iter2.previous();
                iter2.previous();
                iter2.remove();
                iter2.next();
                iter2.remove();
                iter2.next();
                iter2.remove();

                cutByLevel++;

            }
            else
                iter2.next();

            
        }
        System.out.println("heurisztikusan levágott csucsuk a  " + (j+2) + ". szinten:  " + cutByLevel) ;

    }
    /**
     * 
     * @param set
     * 
     * add minumum edges of points not in current node, so a circule in the graf will be created 
     * 
     */
    public int heurisztik(int partuthossz){
        int minEdge = 0;
        int lastPoint = point;                              // protected variable which get value in convertStringToSet metod, last point of original row of current node
        int partuthosszPlusHeuristic = partuthossz;

        while(!SetCurrent.isEmpty()){

            int y = 2000000000;
            for (int item: SetCurrent) {

                int x = kapcsolatitomb[lastPoint][item];
                if(x < y){

                    y = x; 
                    minEdge = item;    

                }
            } 
            partuthosszPlusHeuristic += y;
            lastPoint = minEdge;
            SetCurrent.remove(lastPoint);       
        }

        return partuthosszPlusHeuristic;
    }


    /**
     * 
     * this could be added to every node length
     * 
     * @return
     */
    int gFunction(int partuthossz){

        int x = partuthossz;

        for (int item: SetCurrent) {

            x += minimumEdgesOfNodesArray[item];

        } 

        return x;

    }


    /**
     * 
     * @param treeNodeOrd ordered point of the current node
     * @return  a set of points of the current node  
     * 
     */
    public HashSet<Integer> convertStringToSet(String treeNodeOrd)  
	{

        HashSet<Integer> Set = new HashSet<>();

	 	StringTokenizer stz = new StringTokenizer(treeNodeOrd," ");
		point=Integer.valueOf(stz.nextToken());
		while(stz.hasMoreTokens())
		{
		  	point=Integer.valueOf(stz.nextToken());
            Set.add(point);
        }

        //System.out.println("Set: " + Set);
        return Set;


	}//end 

    /**
     * 
     * @param leafOrd String that represents current node's points
     * 
     * @param xSet SetAll's copy
     * 
     *  function that results remain points of graf that not belongs to current node
     */
    public void differeceOfAllAndCurrentPoints(String leafOrd){

            //System.out.println(" SetCurr in differeceOfAll method: " + SetCurrent);

           SetCurrent.removeAll(convertStringToSet(leafOrd));  

    }

}
