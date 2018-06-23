package leftovers.controller;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created by kevin on 2017/6/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class StockControllerTest {
    @Autowired
    private StockController stockController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = standaloneSetup(stockController).build();
    }

    @Test
    public void getNP1Prices() throws Exception {
        mockMvc.perform(get("/api/stock/NP1Price/600000.SH")
                            .param("num", "1"))
                .andDo(print());
    }
}