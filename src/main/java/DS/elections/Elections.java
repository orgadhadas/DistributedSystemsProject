package DS.elections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

public class Elections {
    Hashtable<Integer, Integer> votes;
    int NUMBER_OF_CANDIDATES = 2;
    public int NUMBER_OF_STATES = 2;
    private int electorals;

    public Elections(int electorals){
        votes = new Hashtable<>();
        this.electorals = electorals;
    }

    public void setVote(int voter, int candid){
        synchronized (this){
            votes.put(voter, candid);
        }
    }

    private int getVote(int voter){
        return votes.get(voter);
    }

    public List<Integer> getResults(){
        synchronized (this) {
            List<Integer> results = new ArrayList<>(Collections.nCopies(NUMBER_OF_CANDIDATES, 0));
            for (int voter : votes.keySet()) {
                int cur = results.get(getVote(voter));
                results.set(getVote(voter), cur + 1);
            }

            return results;
        }
    }

    public int getElectorals(){
        return this.electorals;
    }
}
