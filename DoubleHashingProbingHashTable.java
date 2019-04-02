public class DoubleHashingProbingHashTable<T> extends  ProbingHashTable<T> {

    @Override
    protected int findPos(T x){
        int currentPos = myhash(x);
        int offset = 0;

        while(continueProbing(currentPos, x)){
            int rest = myhash(x) % smallerPrimeThanCapacity();
            offset = smallerPrimeThanCapacity() - rest;
            currentPos += offset;

            if(currentPos >= capacity())
                currentPos -= capacity();
        }
        return currentPos;
    }

    protected int smallerPrimeThanCapacity(){
        int n = capacity() - 2;
        while (!isPrime(n)){
            n -= 2;
        }
        return n;
    }

}
