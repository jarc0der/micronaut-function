package simple.function.service;

import simple.function.data.Data;

import java.util.List;

public interface DataService {

    Data generateData();

    List<Data> generateData(int count);

}
