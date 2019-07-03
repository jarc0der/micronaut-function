package simple.function.service;

import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehose;
import com.amazonaws.services.kinesisfirehose.model.PutRecordBatchRequest;
import com.amazonaws.services.kinesisfirehose.model.PutRecordRequest;
import com.amazonaws.services.kinesisfirehose.model.PutRecordResult;
import com.amazonaws.services.kinesisfirehose.model.Record;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import simple.function.configs.KinesisConfiguration;
import simple.function.data.Data;

import javax.inject.Singleton;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class KinesisStorageProcessor implements StorageProcessor {

    private AmazonKinesisFirehose amazonKinesisFirehose;

    private KinesisConfiguration kinesisConfiguration;

    private ObjectMapper objectMapper;

    public KinesisStorageProcessor(final AmazonKinesisFirehose amazonKinesisFirehose,
                                   final KinesisConfiguration kinesisConfiguration,
                                   final ObjectMapper objectMapper) {
        this.amazonKinesisFirehose = amazonKinesisFirehose;
        this.kinesisConfiguration = kinesisConfiguration;
        this.objectMapper = objectMapper;
    }

    @Override
    public String processSinge(final Data data) {
        PutRecordRequest request = generateRequest(data);

        //should be provided for better testing
        PutRecordResult result = amazonKinesisFirehose.putRecord(request);

        return result.getRecordId();
    }

    @Override
    public void processMultiple(List<Data> dataList) {
        amazonKinesisFirehose.putRecordBatch(generateBatch(dataList));
    }

    private PutRecordBatchRequest generateBatch(final List<Data> data) {
        PutRecordBatchRequest batchRequest = new PutRecordBatchRequest();
        List<Record> records = data.stream()
                .map(d -> new Record().withData(ByteBuffer.wrap(mapObject(d))))
                .collect(Collectors.toList());

        batchRequest.setRecords(records);
        batchRequest.setDeliveryStreamName(kinesisConfiguration.getDeliveryStream());

        return batchRequest;
    }

    private PutRecordRequest generateRequest(final Data data) {
        PutRecordRequest putRecordRequest = new PutRecordRequest();
        putRecordRequest.setDeliveryStreamName(kinesisConfiguration.getDeliveryStream());
        putRecordRequest.withRecord(new Record().withData(ByteBuffer.wrap(mapObject(data))));

        return putRecordRequest;
    }

    private byte[] mapObject(final Data data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while mapping object " + data.getName() + " with JSON objectMapper.");
        }
    }
}
