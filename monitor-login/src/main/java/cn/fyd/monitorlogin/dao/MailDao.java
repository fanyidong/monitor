package cn.fyd.monitorlogin.dao;

import cn.fyd.model.Mail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 操作mail表的dao
 * @author fanyidong
 * @date Created in 2018-12-14
 */
@Repository
public interface MailDao {

    /**
     * 插入一条数据
     * @param mail
     * @return
     */
    int saveMail(Mail mail);

    /**
     * 根据存在的条件查询
     * @param mail
     * @return
     */
    Mail queryBySelective(Mail mail);

    /**
     * 根据userId查询mail信息，并取最新一条记录
     * @param userId
     * @return
     */
    Mail queryByUserIdOrderByOutTime(@Param("userId") String userId);
}
