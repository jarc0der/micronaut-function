package simple.function.service;

import simple.function.data.Data;

import java.util.List;

public interface StorageProcessor {

    String processSinge(Data data);

    void processMultiple(List<Data> dataList);

}
