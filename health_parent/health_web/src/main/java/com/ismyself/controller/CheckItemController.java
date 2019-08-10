package com.ismyself.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ismyself.constant.MessageConstant;
import com.ismyself.entity.PageResult;
import com.ismyself.entity.QueryPageBean;
import com.ismyself.entity.Result;
import com.ismyself.pojo.CheckItem;
import com.ismyself.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * package com.ismyself.controller;
 *
 * @auther txw
 * @create 2019-07-27  10:09
 * @description：
 */
@RestController
@RequestMapping("/checkItem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    /**
     * 保存检查项
     * @param checkItem
     * @return
     */
    @RequestMapping("/save")
    @PreAuthorize("hasAnyAuthority('CHECKITEM_ADD')")
    public Result saveCheckItem(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.save(checkItem);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 分页查询检查项
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findList")
    @PreAuthorize("hasAnyAuthority('CHECKITEM_QUERY')")
    public Result findCheckItemList(@RequestBody QueryPageBean queryPageBean) {
        Result result = new Result(true);
        try {
            PageResult pageResult = checkItemService.findCheckItemList(queryPageBean);
            result.setData(pageResult);
            result.setMessage(MessageConstant.QUERY_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.QUERY_CHECKITEM_FAIL);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除检查项
     * @param id
     * @return
     */
    @RequestMapping("/deleteById")
    @PreAuthorize("hasAnyAuthority('CHECKITEM_DELETE')")
    public Result deleteById(Integer id) {
        Result result = new Result(true);
        try {
            checkItemService.deleteById(id);
            result.setMessage(MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (RuntimeException r) {
            result.setFlag(false);
            result.setMessage(MessageConstant.DELETE_CHECKITEM_FAIL);
            result.setData(r.getMessage());
            r.printStackTrace();
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.DELETE_CHECKITEM_FAIL);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据id回显检查项信息
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    @PreAuthorize("hasAnyAuthority('CHECKITEM_QUERY')")
    public Result findCheckItemById(Integer id) {
        Result result = new Result(true);
        try {
            CheckItem checkItem = checkItemService.findCheckItemById(id);
            result.setData(checkItem);
            result.setMessage(MessageConstant.QUERY_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            result.setFlag(false);
            result.setMessage(MessageConstant.QUERY_CHECKITEM_FAIL);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更新回显数据
     * @param checkItem
     * @return
     */
    @RequestMapping("/update")
    @PreAuthorize("hasAnyAuthority('CHECKITEM_EDIT')")
    public Result updateCheckItem(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.update(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        }
        return new Result(true, MessageConstant.EDIT_CHECKITEM_FAIL);
    }


}
