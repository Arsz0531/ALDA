/**
 * Arpad Szell arsz4862
 * Andreas Ährlund-Richter anah4939
 *
 *
 * Detta är den enda av de tre klasserna ni ska göra några ändringar i. (Om ni
 * inte vill lägga till fler testfall.) Det är också den enda av klasserna ni
 * ska lämna in. Glöm inte att namn och användarnamn ska stå i en kommentar
 * högst upp, och att paketdeklarationen måste plockas bort vid inlämningen för
 * att koden ska gå igenom de automatiska testerna.
 *
 * De ändringar som är tillåtna är begränsade av följande:
 * <ul>
 * <li>Ni får INTE byta namn på klassen.
 * <li>Ni får INTE lägga till några fler instansvariabler.
 * <li>Ni får INTE lägga till några statiska variabler.
 * <li>Ni får INTE använda några loopar någonstans. Detta gäller också alterntiv
 * till loopar, så som strömmar.
 * <li>Ni FÅR lägga till fler metoder, dessa ska då vara privata.
 * <li>Ni får INTE låta NÅGON metod ta en parameter av typen
 * BinarySearchTreeNode. Enbart den generiska typen (T eller vad ni väljer att
 * kalla den), String, StringBuilder, StringBuffer, samt primitiva typer är
 * tillåtna.
 * </ul>
 *
 * @author henrikbe
 *
 * @param <T>
 */
public class BinarySearchTreeNode<T extends  Comparable<T>> {
    private T data;
    private BinarySearchTreeNode<T> left;
    private BinarySearchTreeNode<T> right;

    public BinarySearchTreeNode(T data){
        this.data = data;
    }
    public boolean add(T data){
        //cant add null
        if(data == null)
            return false;

        if(0 > this.data.compareTo(data)){
            if(right == null){
                right = new BinarySearchTreeNode<>(data);
                return true;
            }else {
                return right.add(data);
            }
        }
        if(0 < this.data.compareTo(data)){
            if(left == null){
                left = new BinarySearchTreeNode<>(data);
                return true;
            }else{
                return left.add(data);
            }
        }
        return true;
    }

    private T findMin(){
        T smallest;
        smallest = this.data;
        if(left != null){
            smallest = left.findMin();

        }
        return smallest;
    }

    public BinarySearchTreeNode<T> remove(T data){

        int compareResult= this.data.compareTo(data);

        if(0 < compareResult){
            if(left !=null){
                left = left.remove(data);
            }
        }
        if(0 > compareResult){
            if(right !=null){
                right = right.remove(data);
            }
        }
        if( 0 == compareResult){
            if(right == null && left == null)
                return null;
        }
        if(left == null)
            return right;
        if(right == null)
            return left;
        else{
            T newData = right.findMin();
            remove(newData);
            this.data = newData;
        }

        return this;
    }

    public boolean contains(T data){
        if( 0 == this.data.compareTo(data))
            return true;
        if(0 < this.data.compareTo(data)){
            if(left == null){
                return false;
            }
            return left.contains(data);
        }
        if(0 > this.data.compareTo(data)){
            if(right == null){
                return false;
            }
        }

    }
}
