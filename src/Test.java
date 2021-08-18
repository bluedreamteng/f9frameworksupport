import com.intellij.openapi.util.text.CharFilter;
import com.intellij.openapi.util.text.StringUtil;

public class Test {
    public static void main(String[] args) {
        String s = "/hello/";
        String s1 = "/hello";
        String s2 = "/hello/hello/";
        String s3 = "/hello/hello";
        String trim = StringUtil.trim("s3", new CharFilter() {
            @Override
            public boolean accept(char ch) {
                if (ch == '/') {
                    return false;
                }
                return true;
            }
        });
        System.out.println(trim);
    }


}
