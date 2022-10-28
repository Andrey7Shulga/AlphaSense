package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HelperAPI {

    private static final Logger log = LoggerFactory.getLogger(HelperAPI.class);

    public <T> List<T> jsonArrayToObjectList(String json, Class<T> klass) {
        ObjectMapper mapper = new ObjectMapper();
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, klass);
        List<T> objectList = new ArrayList<>();
            try {
                objectList = mapper.readValue(json, listType);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        log.info("class name: {}", objectList.get(0).getClass().getName());
        return objectList;
    }
}
