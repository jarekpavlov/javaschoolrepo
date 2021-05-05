import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientSecurityTest {

    @Test
    public void getEncryptionPassword(){
        String password = "asdf";
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword=encoder.encode(password);
        Assertions.assertNotEquals(password, encodedPassword);
        System.out.println("encoded password: "+encodedPassword);
    }
//    @Test
//    public void testList(){
//        List<Integer> list1 = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
//        Map<String,String> map2 = new HashMap<>();
//        map2.put("1","7");
//        map2.put("2","8");
//        map2.put("3","9");
//        for (Integer i : list1){
//            System.out.println(i);
//            System.out.println(map2.get("1"));
//            System.out.println(map2.get("2"));
//            System.out.println(map2.get("3"));
//        }
//    }


}
