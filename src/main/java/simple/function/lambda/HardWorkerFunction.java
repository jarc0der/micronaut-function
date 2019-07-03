package simple.function.lambda;

import io.micronaut.function.executor.FunctionInitializer;
import io.micronaut.function.FunctionBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simple.function.data.Data;
import simple.function.data.DummyRequest;
import simple.function.data.Role;
import simple.function.service.DataService;
import simple.function.service.StorageProcessor;

import java.util.List;
import java.util.function.Function;

@FunctionBean("simple-function")
public class HardWorkerFunction extends FunctionInitializer implements Function<DummyRequest, Data> {

    private Logger LOG = LoggerFactory.getLogger(HardWorkerFunction.class);

    private DataService dataService;

    private StorageProcessor storageProcessor;

    public HardWorkerFunction(final DataService dataService,
                              final StorageProcessor storageProcessor) {
        this.dataService = dataService;
        this.storageProcessor = storageProcessor;
    }

    @Override
    public Data apply(DummyRequest msg) {
        LOG.info("Lambda function was triggered.");
        System.out.println("Lambda function was triggered.");
        final List<Data> data = dataService.generateData(500);

        storageProcessor.processMultiple(data);

        return new Data("Process finished", data.get(0).getTimestamp(), Role.INFORMER);
    }

}

