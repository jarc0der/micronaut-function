package simple.function;

import io.micronaut.function.client.FunctionClient;
import io.micronaut.http.annotation.Body;
import io.reactivex.Single;
import simple.function.data.Data;
import simple.function.data.DummyRequest;

import javax.inject.Named;

@FunctionClient
public interface SimpleFunctionClient {

    @Named("simple-function")
    Single<Data> apply(@Body DummyRequest body);

}
