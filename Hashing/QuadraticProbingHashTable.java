public class QuadraticProbingHashTable<AnyType> extends ProbingHashTable<AnyType> {

    @Override
    protected int findPos(AnyType x){
        int offset = 1;
        int currentPos = myhash(x);
        while(continueProbing(currentPos, x)){
            currentPos += offset; //compute probe
            offset += 2;
            if(currentPos >= capacity())
                currentPos -= capacity();
        }
        return currentPos;
    }
}
