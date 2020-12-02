package com.sinosoft.web.rest.kafka.Entity;

/**
 * Add Qy 2020-11-23
 * COMP回发iTraining系统Topic 数据响应实体
 */
public class TrainResultFeedbackEntity {

    private Long TIMESTAMP;

    private String USER_CODE;

    private String TRAINING_TYPE;

    private String SYS_SIGN;

    private Integer STATUS;

    public Long getTIMESTAMP() {
        return TIMESTAMP;
    }

    public void setTIMESTAMP(Long TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }

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

    public String getSYS_SIGN() {
        return SYS_SIGN;
    }

    public void setSYS_SIGN(String SYS_SIGN) {
        this.SYS_SIGN = SYS_SIGN;
    }

    public Integer getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(Integer STATUS) {
        this.STATUS = STATUS;
    }
}
