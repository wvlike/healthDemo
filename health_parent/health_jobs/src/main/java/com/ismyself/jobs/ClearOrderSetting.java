package com.ismyself.jobs;



import com.alibaba.dubbo.config.annotation.Reference;
import com.ismyself.Utils.DateUtils;
import com.ismyself.service.OrderSettingService;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * package com.ismyself.jobs;
 *
 * @auther txw
 * @create 2019-08-11  21:43
 * @description：
 */
//说明：预约设置（OrderSetting）数据是用来设置未来每天的可预约人数，
// 随着时间的推移预约设置表的数据会越来越多，而对于已经过去的历史数据可以定时来进行清理，
// 例如每月最后一天凌晨2点执行一次清理任务
@Component
public class ClearOrderSetting {

    @Reference
    private OrderSettingService orderSettingService;

    public void clearOrderSetting() {
        try {
            String date = DateUtils.parseDate2String(new Date());
            orderSettingService.deleteBeforeDate(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
