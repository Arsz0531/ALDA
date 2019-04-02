/**
 * Arpad Szell arsz4862
 * Andreas Ährlund-Richter anah 4939
 */

public class ISBN10 {

    private char[] isbn;

    public ISBN10(String isbn){
        if(isbn.length() != 10)
            throw new IllegalArgumentException("Wrong length, must be 10");
        if (!checkDigit(isbn))
            throw new IllegalArgumentException("Not a valid isbn 10");
        this.isbn = isbn.toCharArray();
    }

    private boolean checkDigit(String isbn){
        int sum = 0;
        for(int i = 0; i <9; i++){
            sum += Character.getNumericValue(isbn.charAt(i)) * (10-i);
        }
        int checkDigit = (11-(sum % 11)) % 11;
        return isbn.endsWith(checkDigit == 10 ? "X": "" + checkDigit);
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof ISBN10){
            ISBN10 o = (ISBN10)other;

            //get out of here ISBN13
            if(this.isbn.length != o.isbn.length){
                return false;
            }

            for(int i = 0; i < isbn.length; i++) {
                if (o.isbn[i] != this.isbn[i])
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return new String(isbn);
    }
}
