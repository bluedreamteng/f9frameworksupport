package com.tengfei.f9framework.sytaxpattern;

/**
 * @author ztf
 */
public class F9JsMethod {
    public static final String INITPAGE = "epoint.initPage";
    public static final String EXECUTE = "epoint.execute";

    public static String[] getSupportedJsMethod(){
        return new String[] {EXECUTE};
    }

    public static String[] getSupportedJsAction(){
        return new String[] {INITPAGE};
    }
}
