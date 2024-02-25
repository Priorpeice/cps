package server.cps.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import server.cps.model.Compiler;

@SpringBootTest
public class AppConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void printCompilerBeans() {
        Compiler cCompiler1 = context.getBean("c", Compiler.class);
        Compiler cCompiler2 = context.getBean("c", Compiler.class);

        System.out.println("cCompiler1: " + cCompiler1);
        System.out.println("cCompiler2: " + cCompiler2);

//        assertSame(cCompiler1, cCompiler2);
    }
}
