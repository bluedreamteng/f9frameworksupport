import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        Pattern compile = Pattern.compile("^(view-source:)?https?://\\w+:\\d+/([\\w-]+)((/\\w+)+)(\\?.*)?");
        Matcher matcher = compile.matcher("http://localhost:8011/smart-site/frame/pages/szjs/zhgd/spjk/videosettings/videosettingslist?helloworldckjlvjsojiv");
        System.out.println(matcher.matches());
        String moduleName = matcher.group(2);
        String relativePath = matcher.group(3);
    }
}
