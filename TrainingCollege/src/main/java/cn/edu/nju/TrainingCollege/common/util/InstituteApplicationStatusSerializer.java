package cn.edu.nju.TrainingCollege.common.util;

import cn.edu.nju.TrainingCollege.common.constant.InstituteApplicationStatus;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @author hiki on 2018-04-01
 */

public class InstituteApplicationStatusSerializer extends StdSerializer<InstituteApplicationStatus> {

    public InstituteApplicationStatusSerializer() {
        super(InstituteApplicationStatus.class);
    }

    public InstituteApplicationStatusSerializer(Class<InstituteApplicationStatus> t) {
        super(t);
    }

    @Override
    public void serialize(InstituteApplicationStatus instituteApplicationStatus, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(instituteApplicationStatus.toString());
    }

}
