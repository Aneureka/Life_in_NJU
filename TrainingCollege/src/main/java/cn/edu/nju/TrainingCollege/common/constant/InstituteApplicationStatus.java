package cn.edu.nju.TrainingCollege.common.constant;

import cn.edu.nju.TrainingCollege.common.util.InstituteApplicationStatusDeserializer;
import cn.edu.nju.TrainingCollege.common.util.InstituteApplicationStatusSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author hiki on 2018-01-29
 */

@JsonSerialize(using = InstituteApplicationStatusSerializer.class)
@JsonDeserialize(using = InstituteApplicationStatusDeserializer.class)
public enum InstituteApplicationStatus {

    UNPROCESSED("未处理"),

    APPROVED("通过"),

    DISAPPROVED("驳回");

    private String status;

    InstituteApplicationStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }

    public static InstituteApplicationStatus get(String status) {
        for (InstituteApplicationStatus s : values()) {
            if (s.toString().equals(status))
                return s;
        }
        throw new IllegalArgumentException();
    }

}
