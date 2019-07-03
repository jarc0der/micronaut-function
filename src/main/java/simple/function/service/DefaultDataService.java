package simple.function.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simple.function.data.Data;
import simple.function.data.Role;

import javax.inject.Singleton;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Singleton
public class DefaultDataService implements DataService {

    private Logger LOG = LoggerFactory.getLogger(DefaultDataService.class);

    @Override
    public Data generateData() {
        Data data = new Data(randomHexString(), currentTimestamp(), randomEnum(Role.class));
        simulateProcessingLatency();
        System.out.println("Random data object was generated.");
        return data;
    }

    @Override
    public List<Data> generateData(int count) {
        simulateProcessingLatency();
        return IntStream.range(0, count)
                .mapToObj(counter -> new Data(randomHexString(), currentTimestamp(), randomEnum(Role.class)))
                .collect(Collectors.toList());
    }

    private void simulateProcessingLatency() {
        try {
            Thread.sleep(randomInteger(500, 3000));
        } catch (InterruptedException e) {
            LOG.info("Exception in generate data process.");
        }
    }

    private String randomHexString() {
        return Integer.toHexString( ThreadLocalRandom.current()
                .nextInt( Integer.MAX_VALUE ) )
                .toUpperCase();
    }

    private  <T extends Enum<?>> T randomEnum( final Class<T> clazz ) {
        int x = ThreadLocalRandom.current().nextInt( clazz.getEnumConstants().length );
        return clazz.getEnumConstants()[x];
    }

    private int randomInteger( final int floor, final int ceiling ) {
        return ThreadLocalRandom.current().nextInt(floor, ceiling);
    }

    private long currentTimestamp() {
        return ZonedDateTime.now(ZoneId.of("UTC")).toInstant().getEpochSecond();
    }
}
