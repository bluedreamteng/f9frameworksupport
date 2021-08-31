import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
       File file = new File("E:\\tools\\frp_0.35.1_linux_amd64");
       System.out.println(file.toPath().toRealPath());
       List<Object> objects = Collections.emptyList();
    }
}
