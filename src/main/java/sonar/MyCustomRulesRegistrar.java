package sonar;import org.sonar.api.Plugin;public class MyCustomRulesRegistrar implements Plugin {    @Override    public void define(Context context) {        context.addExtension(MyCustomRulesDefinition.class);        context.addExtension(MyCustomJavaRule.class);    }}