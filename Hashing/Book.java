/**
 * Arpad Szell arsz4862
 * Andreas Ährlund-Richter anah4939
 */

public class Book {

    private MyString title;
    private MyString author;
    private ISBN10 isbn;
    private MyString content;
    private int price;

    public Book(String title, String author, String isbn, String content, int price){
        this.title = new MyString(title);
        this.author = new MyString(author);
        this.isbn = new ISBN10(isbn);
        this.content = new MyString(content);
    }
    /**
     * Enkel hashkod, tar varje del av ISBN,
     * med 4 unika primtal, p1-p4, lite slumpvisa, ett större(107)
     * är en balans mellan alltför stora tal som gör hashen enorm,
     * och små som är vanliga i komposita-tal.
     * Iom att man har olika fält som har vissa begränsningar (finns begränsat med publishers osv),
     * så multiplicerar vi varje fält med ett primtal, för att använda all möjlig variation.
     * https://upload.wikimedia.org/wikipedia/commons/thumb/8/84/ISBN_Details.svg/330px-ISBN_Details.svg.png
     * exv:
     * 12 3142 483 8
     * Blir: 12*37 + 3142 * 107 + 483 * 17 + 8 * 11
     * Iom är bara enkla aritmetiska operationer(för en dator), så borde
     * den vara relativt snabb.
     * Pga bokstavsfrekvensen i engelskan är så skev, så använder vi inte titlar osv,
     * iom att man får ändå väldigt mycket upprepningar.
     * https://www3.nd.edu/~busiforc/handouts/cryptography/380px-English-slf2.png
     * @return
     */
    @Override
    public int hashCode(){
        int code = 0;

        int isbnNR = Integer.parseInt(getIsbn().toString());
        code= (isbnNR & 10) * 11 +
                ((isbnNR/10)%1000) * 17 +
                ((isbnNR/10000)%10000)*107 +
                ((isbnNR/100000000)%100)*37;
        return code;
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof Book){
            Book o = (Book)other;

            return o.getIsbn().equals(this.getIsbn());
        }
        return false;
    }
    public MyString getTitle() {
        return title;
    }

    public MyString getAuthor() {
        return author;
    }

    public ISBN10 getIsbn() {
        return isbn;
    }

    public MyString getContent() {
        return content;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("\"%s\" by %s Price: %d ISBN: %s lenght: %s", title, author, price, isbn, content.length());
    }

}
