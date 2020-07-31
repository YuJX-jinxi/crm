package cn.wolfcode;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerTest {

    @Test
    public void test() throws  Exception{
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        // 指定模板文件从何处加载的数据源，这里设置文件目录位置。
        cfg.setDirectoryForTemplateLoading(new File("templates"));
        //-----------------提供数据（需要手动修改）-------------
        Map root = new HashMap();
        String domainName = "Courseorder"; //domain类名
        root.put("chinese","课程订单"); //中文,用于权限注解
        //------------------------------------------------------
        root.put("capitalize",domainName); //大写开头
        root.put("uncapitalize", StringUtils.uncapitalize(domainName)); //小写开头
        String packageDir = "src/main/java/cn/wolfcode/"; //项目目录
        // //--------------------controller控制器---------------
        Template temp = cfg.getTemplate("controller.ftl");
        // 设置输出为新的文件
        Writer out = new OutputStreamWriter(new FileOutputStream(packageDir+"/web/controller/"+domainName+"Controller.java"));
        temp.process(root, out);
        out.flush();
        out.close();
        //--------------------service接口------------------------
        temp = cfg.getTemplate("Iservice.ftl");
        // 设置输出为新的文件
        out = new OutputStreamWriter(new FileOutputStream(packageDir+"/service/"+"I"+domainName+"Service.java"));
        temp.process(root, out);
        out.flush();
        out.close();
        //--------------------service实现类------------------------
        temp = cfg.getTemplate("service.ftl");
        // 设置输出为新的文件
        out = new OutputStreamWriter(new FileOutputStream(packageDir+"/service/impl/"+domainName+"ServiceImpl.java"));
        // 执行输出
        temp.process(root, out);
        out.flush();
        out.close();
    }
}
