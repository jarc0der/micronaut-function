package simple.function;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehose;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehoseClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Configuration;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import io.micronaut.runtime.Micronaut;
import simple.function.configs.KinesisConfiguration;
import simple.function.service.KinesisStorageProcessor;
import simple.function.service.StorageProcessor;

import javax.inject.Singleton;

@Factory
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }

    @Singleton
    BasicAWSCredentials awsCredentials(final KinesisConfiguration kinesisConfiguration) {
        return new BasicAWSCredentials(kinesisConfiguration.getAccessKey(), kinesisConfiguration.getSecretKey());
    }

    @Singleton
    AmazonKinesisFirehose amazonKinesis(final BasicAWSCredentials basicAWSCredentials,
                                        final KinesisConfiguration kinesisConfiguration) {
        return AmazonKinesisFirehoseClientBuilder.standard()
                //.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(kinesisConfiguration.getRegion())
                .build();
    }

    @Singleton
    StorageProcessor storageProcessor(final AmazonKinesisFirehose amazonKinesisFirehose,
                                      final KinesisConfiguration kinesisConfiguration,
                                      final ObjectMapper objectMapper) {
        return new KinesisStorageProcessor(amazonKinesisFirehose, kinesisConfiguration, objectMapper);
    }
}