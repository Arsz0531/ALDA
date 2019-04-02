/**
 * @author Arpad Szell arsz4862, Andreas Ährlund-Richter anah4939
 *
 */

public class MyString {

    private char[] data;

    public MyString(String title) {
        data = title.toCharArray();
    }

    public Object length() {
        return data.length;
    }

    @Override
    public String toString() {
        return new String(data);
    }

}
