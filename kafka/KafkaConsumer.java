package com.sinosoft.web.rest.kafka;

import com.sinosoft.utility.GsonUtil;
import com.sinosoft.web.rest.kafka.Entity.TrainResultEntity;
import com.sinosoft.web.rest.kafka.Entity.TrainResultFeedbackEntity;
import com.sinosoft.web.rest.kafka.service.ITrainingResultService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * @Description: Kafka消费类
 * @Author:QiuYu
 * @Since: 2020-11-23
 *
 */
@Component
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private ITrainingResultService iTrainingResultService;

    @Autowired
    private KafkaProducer kafkaProducer;


    /**
     * 用于监听Topic为ilearning_train_result
     * @param record
     * @param ack
     * @param topic
     */
    @KafkaListener(topics = KafkaTopicContents.ILEARNING_TRAIN_RESULT,groupId = KafkaTopicContents.ITRAINING_COMP_CONSUMER_GROUP1)
    public void Itraining2CompTopic(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("topic_test 消费了： Topic:" + topic + ",Message:" + msg);
            // 添加业务逻辑，转化实体，修改保存,响应结果发送Kafka
            TrainResultEntity resultEntity;
            try{
                resultEntity = GsonUtil.json2Object(msg.toString(),TrainResultEntity.class);
                TrainResultFeedbackEntity feedbackEntity = new TrainResultFeedbackEntity();
                feedbackEntity.setUSER_CODE(resultEntity.getUSER_CODE());
                feedbackEntity.setTRAINING_TYPE(resultEntity.getTRAINING_TYPE());
                feedbackEntity.setSYS_SIGN("Newcomp");
                if(iTrainingResultService.save(resultEntity)){
                    log.info("落地成功代理人为[{}],培训体系为[{}]的数据。",resultEntity.getUSER_CODE(),resultEntity.getTRAINING_TYPE());
                    feedbackEntity.setSTATUS(1);
                    feedbackEntity.setTIMESTAMP(System.currentTimeMillis());
                    kafkaProducer.feedbackITrainingMessage(feedbackEntity);
                }
                else {
                    log.warn("落地失败代理人为[{}],培训体系为[{}]的数据。",resultEntity.getUSER_CODE(),resultEntity.getTRAINING_TYPE());
                    feedbackEntity.setSTATUS(0);
                    feedbackEntity.setTIMESTAMP(System.currentTimeMillis());
                    kafkaProducer.feedbackITrainingMessage(feedbackEntity);
                }
            }catch (Exception e){
                e.getStackTrace();
            }

            ack.acknowledge();//手动提交offset
        }
    }



}
