/**
 *
 * Arpad Szell arsz4862
 * Andreas Ährlund-Richter anah4939
 *
 */

public class LinearProbingHashTable<T> extends  ProbingHashTable<T> {

    @Override
    protected int findPos(T x){

        boolean done = false;
        int offset = 1;
        int currentPos = myhash(x);
        while(continueProbing(currentPos, x)){
            currentPos += offset;

            if(currentPos >= capacity())
                currentPos -= capacity();
        }
        return currentPos;
    }
}
