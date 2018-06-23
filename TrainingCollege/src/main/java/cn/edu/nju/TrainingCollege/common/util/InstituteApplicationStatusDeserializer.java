package cn.edu.nju.TrainingCollege.common.util;

import cn.edu.nju.TrainingCollege.common.constant.InstituteApplicationStatus;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * @author hiki on 2018-04-01
 */

public class InstituteApplicationStatusDeserializer extends StdDeserializer<InstituteApplicationStatus> {


    public InstituteApplicationStatusDeserializer() {
        super(InstituteApplicationStatus.class);
    }

    public InstituteApplicationStatusDeserializer(Class<InstituteApplicationStatus> t) {
        super(t);
    }

    @Override
    public InstituteApplicationStatus deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return InstituteApplicationStatus.get(jsonParser.getText());
    }
}
