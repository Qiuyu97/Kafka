package com.sinosoft.web.rest.kafka;

import com.sinosoft.utility.GsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @Description: Kafka生产类
 * @Author:QiuYu
 * @Since: 2020-11-23
 *
 */
@Component
public class KafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);


    @Autowired
    private KafkaTemplate kafkaTemplate;


    /**
     * @Author：Qy
     * 全参型调用，可以使用时去重载方法
     * @param topic     not null
     * @param partition ==null || >0
     * @param timestamp ==null || >0
     * @param key
     * @param obj
     */
    public void sendMessage(String topic,Integer partition,Long timestamp,Object key,Object obj) {
        String obj2String = GsonUtil.object2Json(obj);
        log.info("准备发送消息为：{}", obj2String);
        //发送消息
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic,partition,timestamp,key,obj2String);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                //发送失败的处理
                doFailure(topic,obj);
                log.info(topic + " - 生产者 发送消息失败：" + throwable.getMessage());
            }
            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                //成功的处理
                doSuccess(topic,obj,stringObjectSendResult);
                log.info(topic + " - 生产者 发送消息成功：" + stringObjectSendResult.toString());
            }
        });
    }


    /**
     * 发送失败回调
     * @param topic
     * @param obj  发送kafka实体
     * @return
     */
    private Object doFailure(String topic,Object obj){
        Object object;
        switch (topic){
            // 响应Itraining结果
            case KafkaTopicContents.ILEARNING_TRAIN_RESULT_FEEDBACK:
                // do something...
                log.info("failure do something...");
                // 比如失败重发。。
                object = null;
                break;
            // add more type in the following ....

            default:
                object = null;
        }

        return object;
    }

    /**
     * 发送成功回调
     * @param topic  topic
     * @param obj    发送kafka实体
     * @param stringObjectSendResult 成功kafka回调结果
     * @return
     */
    private Object doSuccess(String topic,Object obj,SendResult<String, Object> stringObjectSendResult){
        Object object = null;
        switch (topic){
            // 响应Itraining结果
            case KafkaTopicContents.ILEARNING_TRAIN_RESULT_FEEDBACK:
                // do something...
                log.info("success do something...");
                object = null;
                break;
            // add more type in the following ....

            default:
                object = null;
        }

        return object;
    }

    /**
     * NewComp 接收后发送Kafka响应结果，Itraining使用
     * @param obj
     */
    public void feedbackITrainingMessage(Object obj) {
        sendMessage(KafkaTopicContents.ILEARNING_TRAIN_RESULT_FEEDBACK,null,null,null,obj);
    }



}


