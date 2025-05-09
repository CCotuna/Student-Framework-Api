package com.example.studentframeworkapi;

import com.example.studentframeworkapi.testng.TestListeners;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Listeners;

@SpringBootTest
@Listeners(TestListeners.class)
public class BaseTest {
}
