import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ClientSecurityTest {

    Logger logger = LoggerFactory.getLogger(ClientSecurityTest.class);
    @Test
    public void getEncryptionPassword(){
        String password = "asdf";
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword=encoder.encode(password);
        Assertions.assertNotEquals(password, encodedPassword);
        logger.info("encoded password: {} ",encodedPassword);
    }

}
