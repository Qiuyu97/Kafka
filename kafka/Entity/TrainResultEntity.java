package com.sinosoft.web.rest.kafka.Entity;

/**
 * Add Qy 2020-11-23
 * iTraining系统发送COMP培训结果数据实体
 */
public class TrainResultEntity {

    private String USER_CODE; // 工号

    private String TRAINING_TYPE; //培训体系

    private String TRAINING_RESULT; // 是否结训 PASS :通过 NOPASS:未通过

    private String RESULT_TIME; // 结训时间

    private Long DATETIME;  // 请求时间 Timestamp

    private String SYS_SIGN; // 请求系统  iTraining

    public String getUSER_CODE() {
        return USER_CODE;
    }

    public void setUSER_CODE(String USER_CODE) {
        this.USER_CODE = USER_CODE;
    }

    public String getTRAINING_TYPE() {
        return TRAINING_TYPE;
    }

    public void setTRAINING_TYPE(String TRAINING_TYPE) {
        this.TRAINING_TYPE = TRAINING_TYPE;
    }

    public String getTRAINING_RESULT() {
        return TRAINING_RESULT;
    }

    public void setTRAINING_RESULT(String TRAINING_RESULT) {
        this.TRAINING_RESULT = TRAINING_RESULT;
    }

    public String getRESULT_TIME() {
        return RESULT_TIME;
    }

    public void setRESULT_TIME(String RESULT_TIME) {
        this.RESULT_TIME = RESULT_TIME;
    }

    public Long getDATETIME() {
        return DATETIME;
    }

    public void setDATETIME(Long DATETIME) {
        this.DATETIME = DATETIME;
    }

    public String getSYS_SIGN() {
        return SYS_SIGN;
    }

    public void setSYS_SIGN(String SYS_SIGN) {
        this.SYS_SIGN = SYS_SIGN;
    }
}
