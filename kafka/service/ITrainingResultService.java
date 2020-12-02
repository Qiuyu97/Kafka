package com.sinosoft.web.rest.kafka.service;

import com.sinosoft.web.rest.kafka.Entity.TrainResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;


@Service
@Transactional
public class ITrainingResultService {
    private final Logger log = LoggerFactory.getLogger(ITrainingResultService.class);

    private final EntityManager entityManager;

    public ITrainingResultService(EntityManager entityManager){
        this.entityManager = entityManager;
    }


    // 核心落地处理
    public boolean save(TrainResultEntity trainResultEntity){
        boolean flag = false;
        if (trainResultEntity!=null){
            try{
                String USER_CODE = trainResultEntity.getUSER_CODE();// 工号
                String TRAINING_TYPE = trainResultEntity.getTRAINING_TYPE();//培训体系
                if (USER_CODE==null||"".equals(USER_CODE)){
                    throw new IllegalArgumentException("工号不能为空，需要核对信息！");
                }
                if (TRAINING_TYPE==null||"".equals(TRAINING_TYPE)){
                    throw new IllegalArgumentException("培训体系不能为空，需要核对信息！");
                }
                StringBuffer sql = new StringBuffer();
                // 复合主键查询是否存在数据
                sql.append("SELECT COUNT(*) FROM AGENT_TRAINING_RESULT_DETAIL WHERE USER_CODE=:USER_CODE AND TRAINING_TYPE=:TRAINING_TYPE ");
                Query query = entityManager.createNativeQuery(sql.toString())
                    .setParameter("USER_CODE",USER_CODE)
                    .setParameter("TRAINING_TYPE",TRAINING_TYPE);
                Integer count = (Integer)query.getSingleResult();
                if (count!=null && count>0){
                    log.info("Newcomp已存在代理人为[{}],培训体系为[{}]的数据，进行备份删除重新落地操作。",USER_CODE,TRAINING_TYPE);
                    // 备份原始数据
                    sql.setLength(0);
                    sql.append("INSERT INTO AGENT_TRAINING_RESULT_DETAILB ");
                    sql.append("(USER_CODE,TRAINING_TYPE,TRAINING_RESULT,RESULT_TIME,DATETIME,SYS_SIGN," +
                        "BACKUP_V1,BACKUP_V2,BACKUP_V3,BACKUP_V4,BACKUP_V5," +
                        "OPERATOR2,MAKEDATE2,MAKETIME2,MODIFYDATE2,MODIFYTIME2," +
                        "OPERATOR,MAKEDATE,MAKETIME,MODIFYDATE,MODIFYTIME) ");
                    sql.append(" SELECT USER_CODE,TRAINING_TYPE,TRAINING_RESULT,RESULT_TIME,DATETIME,SYS_SIGN," +
                        "BACKUP_V1,BACKUP_V2,BACKUP_V3,BACKUP_V4,BACKUP_V5," +
                        "OPERATOR,MAKEDATE,MAKETIME,MODIFYDATE,MODIFYTIME," +
                        "'SYS',convert(char(10),GETDATE(),120),convert(char(10),GETDATE(),8),convert(char(10),GETDATE(),120),convert(char(10),GETDATE(),8) " +
                        "FROM AGENT_TRAINING_RESULT_DETAIL A WHERE USER_CODE=:USER_CODE AND TRAINING_TYPE=:TRAINING_TYPE ");
                    query = entityManager.createNativeQuery(sql.toString())
                        .setParameter("USER_CODE",USER_CODE)
                        .setParameter("TRAINING_TYPE",TRAINING_TYPE);
                    query.executeUpdate();
                    // 删除原始数据
                    sql.setLength(0);
                    sql.append("DELETE FROM AGENT_TRAINING_RESULT_DETAIL WHERE USER_CODE=:USER_CODE AND TRAINING_TYPE=:TRAINING_TYPE ");
                    query = entityManager.createNativeQuery(sql.toString())
                        .setParameter("USER_CODE",USER_CODE)
                        .setParameter("TRAINING_TYPE",TRAINING_TYPE);
                    query.executeUpdate();
                    // 重新落地数据
                    sql.setLength(0);
                    sql.append("INSERT INTO AGENT_TRAINING_RESULT_DETAIL ");
                    sql.append(" (USER_CODE,TRAINING_TYPE,TRAINING_RESULT,RESULT_TIME,DATETIME,SYS_SIGN,OPERATOR,MAKEDATE,MAKETIME,MODIFYDATE,MODIFYTIME) ");
                    sql.append(" VALUES(:USER_CODE,:TRAINING_TYPE,:TRAINING_RESULT,:RESULT_TIME,:DATETIME,:SYS_SIGN,'SYS',convert(char(10),GETDATE(),120),convert(char(10),GETDATE(),8),convert(char(10),GETDATE(),120),convert(char(10),GETDATE(),8))");
                    query = entityManager.createNativeQuery(sql.toString())
                        .setParameter("USER_CODE",USER_CODE)
                        .setParameter("TRAINING_TYPE",TRAINING_TYPE)
                        .setParameter("TRAINING_RESULT",trainResultEntity.getTRAINING_RESULT())
                        .setParameter("RESULT_TIME",trainResultEntity.getRESULT_TIME())
                        .setParameter("DATETIME",trainResultEntity.getDATETIME())
                        .setParameter("SYS_SIGN",trainResultEntity.getSYS_SIGN());
                    query.executeUpdate();

                }else{
                    // 直接落地数据
                    sql.setLength(0);
                    sql.append("INSERT INTO AGENT_TRAINING_RESULT_DETAIL ");
                    sql.append(" (USER_CODE,TRAINING_TYPE,TRAINING_RESULT,RESULT_TIME,DATETIME,SYS_SIGN,OPERATOR,MAKEDATE,MAKETIME,MODIFYDATE,MODIFYTIME) ");
                    sql.append(" VALUES(:USER_CODE,:TRAINING_TYPE,:TRAINING_RESULT,:RESULT_TIME,:DATETIME,:SYS_SIGN,'SYS',convert(char(10),GETDATE(),120),convert(char(10),GETDATE(),8),convert(char(10),GETDATE(),120),convert(char(10),GETDATE(),8))");
                    query = entityManager.createNativeQuery(sql.toString())
                        .setParameter("USER_CODE",USER_CODE)
                        .setParameter("TRAINING_TYPE",TRAINING_TYPE)
                        .setParameter("TRAINING_RESULT",trainResultEntity.getTRAINING_RESULT())
                        .setParameter("RESULT_TIME",trainResultEntity.getRESULT_TIME())
                        .setParameter("DATETIME",trainResultEntity.getDATETIME())
                        .setParameter("SYS_SIGN",trainResultEntity.getSYS_SIGN());
                    query.executeUpdate();
                }
                flag = true;
            }
            catch (Exception e){
                flag = false;
                e.printStackTrace();
            }
        }
        return flag;
    }


}
