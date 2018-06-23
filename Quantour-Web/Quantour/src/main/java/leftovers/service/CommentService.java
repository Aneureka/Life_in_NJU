package leftovers.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import leftovers.model.User;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;
import java.util.HashMap;

/**
 * Created by kevin on 2017/6/9.
 */
@Service
public class CommentService {
    /*
    This script will calculate the Disqus SSO payload package
    Please see the Integrating SSO guide to find out how to configure your account first:
    http://help.disqus.com/customer/portal/articles/236206
    This example uses the Jackson JSON processor: http://jackson.codehaus.org/Home
    */
    private String DISQUS_SECRET_KEY = "SwuL7hzF40jByAZuEAJbZ6t21QloxTziUUMf5a4UQNXanP9oNnD9AQoEUr09nYcZ"; // Your Disqus secret key from http://disqus.com/api/applications/

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    // User data, replace values with authenticated user data
    private HashMap<String, String> message = new HashMap();

    public String getRemoteAuthS3(User user) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, JsonProcessingException {
        message.put("id",user.getUsername());
        message.put("username",user.getUsername());
        message.put("email","charlie.chaplin@example.com");
//    message.put("avatar","http://example.com/path-to-avatar.jpg"); // User's avatar URL (optional)
//    message.put("url","http://example.com/"); // User's website or profile URL (optional)

        // Encode user data
        ObjectMapper mapper = new ObjectMapper();

        String jsonMessage = mapper.writeValueAsString(message);

        String base64EncodedStr = new String(Base64.encodeBase64(jsonMessage.getBytes()));

        // Get the timestamp
        long timestamp = System.currentTimeMillis() / 1000;

        // Assemble the HMAC-SHA1 signature
        String signature = calculateRFC2104HMAC(base64EncodedStr + " " + timestamp, DISQUS_SECRET_KEY);

        // Output string to use in remote_auth_s3 variable
        System.out.println(base64EncodedStr +" "+signature +" "+timestamp);
        return base64EncodedStr +" "+signature +" "+timestamp;
    }

    private static String calculateRFC2104HMAC(String data, String key)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signingKey);
        return toHexString(mac.doFinal(data.getBytes()));
    }

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
}
