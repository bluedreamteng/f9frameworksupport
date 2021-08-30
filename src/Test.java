import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        String s = "http://localhost:8011/smart-site/szjs/index/videoinfo/environmentalMonitoring";
        String regex = "^https?://\\w+:\\d+/([\\w-]+)((/\\w+)+$)";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(s);
        System.out.println(matcher.matches());
        System.out.println(matcher.group(0));
        System.out.println(matcher.group(1));
        System.out.println(matcher.group(2));


    }
}
