import com.intellij.openapi.util.text.StringUtil;

import javax.swing.*;

public class Test {
    public static void main(String[] args) {
        String name = "xmgl_wdprojectinfo";
        String s = StringUtil.toTitleCase(name).replaceAll("_","");
        char[] chars = s.toCharArray();
        chars[0] = StringUtil.toLowerCase(chars[0]);
        System.out.println(String.valueOf(chars));
    }
}
