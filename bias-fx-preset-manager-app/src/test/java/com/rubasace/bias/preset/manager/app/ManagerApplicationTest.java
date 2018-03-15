package com.rubasace.bias.preset.manager.app;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ManagerApplication.class},
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ManagerApplicationTest {

    //TODO investigate why test fails with ClassDefNotFoundException
    @Ignore
    @Test
    public void shouldStartup() {
        //Nothing to do, just load the context
    }
}