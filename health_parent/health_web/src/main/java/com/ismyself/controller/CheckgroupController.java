package com.ismyself.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ismyself.constant.MessageConstant;
import com.ismyself.entity.PageResult;
import com.ismyself.entity.QueryPageBean;
import com.ismyself.entity.Result;
import com.ismyself.pojo.CheckGroup;
import com.ismyself.pojo.CheckItem;
import com.ismyself.service.CheckgroupService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * package com.ismyself.controller;
 *
 * @auther txw
 * @create 2019-07-28  19:58
 * @description：
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckgroupController {

    @Reference
    private CheckgroupService checkgroupService;
    //findCheckItemList

    /**
     * 显示检查项列表信息
     *
     * @return
     */
    @RequestMapping("/findCheckItemList")
    @PreAuthorize("hasAnyAuthority('CHECKGROUP_QUERY')")
    public Result findCheckItemList() {
        try {
            List<CheckItem> list = checkgroupService.findCheckItemList();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 保存表单
     *
     * @param checkGroup
     * @param ids
     * @return
     */
    @RequestMapping("/save")
    @PreAuthorize("hasAnyAuthority('CHECKGROUP_ADD')")
    public Result save(@RequestBody CheckGroup checkGroup, Integer[] ids) {
        System.out.println(checkGroup);
        System.out.println(Arrays.toString(ids));
        try {
            checkgroupService.save(checkGroup, ids);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    /**
     * 查询检查组列表
     *
     * @return
     */
    @RequestMapping("/findList")
    @PreAuthorize("hasAnyAuthority('CHECKGROUP_QUERY')")
    public Result findList(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = checkgroupService.findList(queryPageBean);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    //deleteById

    /**
     * @param id
     * @return
     */
    @RequestMapping("/deleteById")
    @PreAuthorize("hasAnyAuthority('CHECKGROUP_DELETE')")
    public Result deleteById(Integer id) {
        try {
            checkgroupService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    /**
     * 根据id查询CheckGroup
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    @PreAuthorize("hasAnyAuthority('CHECKGROUP_QUERY')")
    public Result findById(Integer id) {
        try {
            CheckGroup checkGroup = checkgroupService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    //findItemIdsById

    /**
     * 根据groupId查询关联的ItemIds
     * @param id
     * @return
     */
    @RequestMapping("/findItemIdsById")
    @PreAuthorize("hasAnyAuthority('CHECKGROUP_QUERY')")
    public Result findItemIdsById(Integer id) {
        try {
            Integer[] ids = checkgroupService.findItemIdsById(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    //update

    /**
     * 编辑保存
     * @param checkGroup
     * @param ids
     * @return
     */
    @RequestMapping("/update")
    @PreAuthorize("hasAnyAuthority('CHECKGROUP_QUERY')")
    public Result update(@RequestBody CheckGroup checkGroup,Integer[] ids) {
        try {
            checkgroupService.update(checkGroup,ids);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }
}
