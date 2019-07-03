package simple.function;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import simple.function.data.Data;
import simple.function.data.DummyRequest;

import static org.junit.jupiter.api.Assertions.*;

public class HardWorkerFunctionTest {

    @Test
    public void testFunction() throws Exception {

        EmbeddedServer server = ApplicationContext.run(EmbeddedServer.class);

        SimpleFunctionClient client = server.getApplicationContext().getBean(SimpleFunctionClient.class);

        Data resutl = client.apply(new DummyRequest("Dummy text")).blockingGet();

        assertNotNull(resutl.getName());
        assertNotNull(resutl.getRole());

    }
}
