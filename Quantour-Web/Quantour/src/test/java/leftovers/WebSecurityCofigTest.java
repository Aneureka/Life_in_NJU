package leftovers;

import com.alibaba.fastjson.JSONObject;
import leftovers.controller.UserController;
import leftovers.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by kevin on 2017/6/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class WebSecurityCofigTest {

    private MockMvc mockMvc;

    @Autowired
    UserController userController;

    @Before
    public void setUp() {
        this.mockMvc = standaloneSetup(userController).build();
    }

    @Test
    public void createUser() throws Exception {
        User user = new User("hahaha", "123456");
        mockMvc.perform(post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSONObject.toJSONString(user)))
                .andDo(print());
    }

}