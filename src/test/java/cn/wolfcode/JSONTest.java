package cn.wolfcode;

import cn.wolfcode.domain.Department;
import cn.wolfcode.domain.Employee;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.HashMap;

public class JSONTest {

    @Test
    public void testJSON() {

        //员工数据 : id=1 name=张三 age=18
        //{"id":1,"name":"张三","age":18},直接拷贝下去,就会自动转义变成下面的代码
        String jsonStr = "{\"id\":1,\"name\":\"张三\",\"age\":18}";
        System.out.println(jsonStr);

    }

    //Fastjson：阿里出品，号称是 Java 领域中转换 JSON 最快的一个插件，中文文档比较齐全。
    @Test
    public void testFastjson() {
        Department department = new Department();
        department.setId(1L);
        department.setName("开发部");
        department.setSn("DEV");
        // Java 对象转 JSON
        System.out.println(JSON.toJSONString(department));
        // JSON 转 Java 对象
        System.out.println(JSON.parseObject("{\"id\":1,\"name\":\"开发部 \",\"sn\":\"DEV\"}", Department.class));
    }

    //Jackson：在 Spring MVC 中内置支持她，速度也挺快，稳定性比较好。
        @Test
        public void testJackson ()throws Exception {

            //Java 对象转成 JSON
            Department department = new Department();
            department.setName("开发部");
            department.setSn("DEV");
            department.setId(1L);

            ObjectMapper objectMapper = new ObjectMapper();
            // Java 对象转 JSON
            System.out.println(objectMapper.writeValueAsString(department));
            // JSON 转 Java 对象
            System.out.println(objectMapper.readValue("{\"id\":1,\"name\":\"开发部 \",\"sn\":\"DEV\"}", Department.class));
        }

        @Test
    public void test1() throws Exception{
            Employee employee=new Employee();
            employee.setId(1L);
            employee.setName("huhu");
            HashMap<Object, Object> map = new HashMap<>();
            map.put("id",1 );
            map.put("name",2 );
            map.put("number",3 );
            map.put("employee",employee);
            System.out.println(JSON.toJSONString(map));
        }
    }
