package simple.function.configs;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("kinesis")
public class KinesisConfiguration {
    private String accessKey;

    private String secretKey;

    private String region;

    private String deliveryStream;

    public String getDeliveryStream() {
        return deliveryStream;
    }

    public void setDeliveryStream(String deliveryStream) {
        this.deliveryStream = deliveryStream;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
