import java.time.ZonedDateTime;
import java.util.*;

public class T1 {

    public static void main(String[] args) {
        ZonedDateTime time = ZonedDateTime.now();
        System.out.println(time);
        replaceSqlParamByMap();
    }

    public static String replaceSqlParamByMap() {
        String sql = "insert into user(username,password) values(${username},${password})";
        Map m = new HashMap();
        m.put("username", "zs");
        m.put("password",123);

        Iterator it = m.keySet().iterator();
        while(it.hasNext()){
            String key = it.next().toString();
            String val = m.get(key).toString();
            //insert into om_clyy_prod_update(product_id,object_id) values(${PRODUCT_ID},${OBJECT_ID})
            String param = "${"+key+"}";
            if(sql.indexOf(param)>-1){
                sql = sql.replaceAll("\\$\\{"+key+"\\}",val);
            }
        }
        System.out.println(sql);
        return sql;
    }
}
