package cn.boyce.format;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商城自定义响应结构
 *
 * @Author: Yuan Baiyu
 * @Date: Created in 13:05 2019/4/28
 **/
@Data
public class MallResult implements Serializable {

    // 定义 jackson 对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static MallResult build(Integer status, String msg, Object data) {
        return new MallResult(status, msg, data);
    }

    public static MallResult ok(Object data) {
        return new MallResult(data);
    }

    public static MallResult ok() {
        return new MallResult(null);
    }

    public static MallResult build(Integer status, String msg) {
        return new MallResult(status, msg, null);
    }

    public MallResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public MallResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }

    /**
     * 将 json 结果集转化为 MallResult对象
     *
     * @param jsonData jsonData json数据
     * @param clazz    clazz MallResult 中的 object 类型
     * @return
     */
    public static MallResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, MallResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有 object 对象的转化
     *
     * @param json
     * @return
     */
    public static MallResult format(String json) {
        try {
            return MAPPER.readValue(json, MallResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object 是集合转化
     *
     * @param jsonData json 数据
     * @param clazz    集合中的类型
     * @return
     */
    public static MallResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }
}
