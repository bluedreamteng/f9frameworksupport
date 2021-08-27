import com.google.gson.Gson;
import com.intellij.codeInsight.daemon.impl.analysis.HtmlFileReferenceHelper;
import com.intellij.openapi.options.Configurable;
import com.intellij.terminal.JBTerminalStarter;
import com.intellij.terminal.TerminalSettingsListener;
import com.jediterm.terminal.ui.TerminalPanel;
import com.tengfei.f9framework.completioncontributor.dictionary.F9HtmlAttributeValueDocumentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Map<String, List<F9HtmlAttributeValueDocumentation>> map = new HashMap<>();
        F9HtmlAttributeValueDocumentation documentation = new F9HtmlAttributeValueDocumentation();
        List<F9HtmlAttributeValueDocumentation> documentations = new ArrayList<>();
        documentation.setAttributeValue("hello");
        documentation.setDescription("world");
        documentations.add(documentation);
        map.put("ddd",documentations);
        Gson gson  = new Gson();
        String s = gson.toJson(map);
        System.out.println(s);
    }


}
