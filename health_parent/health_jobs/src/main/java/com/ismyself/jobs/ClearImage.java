package com.ismyself.jobs;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ismyself.Utils.QiniuUtils;
import com.ismyself.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * package com.ismyself.jobs;
 *
 * @auther txw
 * @create 2019-07-31  15:54
 * @description：
 */
@Component
public class ClearImage {

    @Autowired
    private JedisPool jedisPool;


    public void clear(){
        Set<String> trashImgs = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);

        for (String trashImg : trashImgs) {
            //先清理七牛云
            QiniuUtils.deleteFileFromQiniu(trashImg);
            //在清理redis
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,trashImg);

            System.out.println("图片已清理");
        }
    }



}
