package BranchABound;

import java.util.*;


public class BranchAndBoundController {

    protected BranchAndBound BBObj;
    protected int varosSzam;
    protected int [] minimumEdgesOfNodesArray;
    protected int [] [] kapcsolatitomb;
    protected LinkedList<Object> list0;

  


    public BranchAndBoundController(LinkedList<Object> list0, int varosSzam, int [] [] kapcsolatitomb, int [] minimumEdgesOfNodesArray){

        this.varosSzam = varosSzam;

        this.kapcsolatitomb = kapcsolatitomb;

        this.minimumEdgesOfNodesArray = minimumEdgesOfNodesArray;

        this.list0 = list0;


    }

    public void runCurrentLevel(int j){

        BBObj = new BranchAndBound(list0, varosSzam, kapcsolatitomb, minimumEdgesOfNodesArray, j);

        BBObj.checkLeaves();
    }
    
    
}
