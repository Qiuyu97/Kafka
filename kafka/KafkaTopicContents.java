package com.sinosoft.web.rest.kafka;


/**
 * topic配置类
 */
public class KafkaTopicContents {

   // 6.7 外勤人员结训结果
   //                                             Topic名称
   //        SIT	                            UAT	                                            PROD
   // ilearning_train_result-sit            ilearning_train_result-uat	           ilearning_train_result
   // ilearning_train_result_feedback-sit	ilearning_train_result_feedback-uat	   ilearning_train_result_feedback


    // prod
    protected static final String ILEARNING_TRAIN_RESULT = "ilearning_train_result";

    protected static final String ILEARNING_TRAIN_RESULT_FEEDBACK = "ilearning_train_result_feedback";

    // sit 需要此Topic的时候放开注释
    //protected static final String ILEARNING_TRAIN_RESULT = "ilearning_train_result-sit";

    //protected static final String ILEARNING_TRAIN_RESULT_FEEDBACK = "ilearning_train_result_feedback-sit";

    // uat 需要此Topic的时候放开注释
    //protected static final String ILEARNING_TRAIN_RESULT = "ilearning_train_result-uat";

    //protected static final String ILEARNING_TRAIN_RESULT_FEEDBACK = "ilearning_train_result_feedback-uat";

    // 分组名称
    protected static final String ITRAINING_COMP_CONSUMER_GROUP1 = "iTraining_comp-consumer-group1";



}
