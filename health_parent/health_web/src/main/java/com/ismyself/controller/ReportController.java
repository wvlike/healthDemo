package com.ismyself.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ismyself.Utils.DateUtils;
import com.ismyself.Utils.StringUtils;
import com.ismyself.constant.MessageConstant;
import com.ismyself.entity.Result;
import com.ismyself.pojo.Menu;
import com.ismyself.pojo.Setmeal;
import com.ismyself.service.MemberService;
import com.ismyself.service.OrderService;
import com.ismyself.service.ReportService;
import com.ismyself.service.SetmealService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * package com.ismyself.controller;
 *
 * @auther txw
 * @create 2019-08-06  15:18
 * @description：
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private SetmealService setmealService;

    @Reference
    private OrderService orderService;

    @Reference
    private ReportService reportService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        try {
            Map<String, Object> map = getLastYearMap();
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    private Map<String, Object> getLastYearMap() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -12);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, 1);
            list.add(new SimpleDateFormat("yyyy.MM").format(calendar.getTime()));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("months", list);
        List<Integer> memberCount = memberService.findMemberCountByMonth(list);
        map.put("memberCount", memberCount);
        return map;
    }

    //getSetmealReport
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {

        try {
            List<Map<String, Object>> mapList = setmealService.findSetmealNamesAndCounts();
            Map<String, Object> map = new HashMap<>();
            map.put("setmealCount", mapList);

            List<String> setmealNames = new ArrayList<>();
            for (Map<String, Object> sOMap : mapList) {
                String name = (String) sOMap.get("name");
                setmealNames.add(name);
            }
            map.put("setmealNames", setmealNames);
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    //getBusinessReportData
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() throws Exception {
        try {
            Map<String, Object> map = reportService.findBusinessReportMap();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    /*

                hotSetmeal:*/

    //exportBusinessReport
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Map<String, Object> reportMap = reportService.findBusinessReportMap();
            String reportDate = (String) reportMap.get("reportDate");
            Integer todayNewMember = (Integer) reportMap.get("todayNewMember");
            Integer totalMember = (Integer) reportMap.get("totalMember");
            Integer thisWeekNewMember = (Integer) reportMap.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) reportMap.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) reportMap.get("todayOrderNumber");
            Integer todayVisitsNumber = (Integer) reportMap.get("todayVisitsNumber");
            Integer thisWeekOrderNumber = (Integer) reportMap.get("thisWeekOrderNumber");
            Integer thisWeekVisitsNumber = (Integer) reportMap.get("thisWeekVisitsNumber");
            Integer thisMonthOrderNumber = (Integer) reportMap.get("thisMonthOrderNumber");
            Integer thisMonthVisitsNumber = (Integer) reportMap.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) reportMap.get("hotSetmeal");

            //获取excel模板文件绝对路径
            String templateRealPath = request.getSession().getServletContext().getRealPath("template") +
                    File.separator + "report_template.xlsx";
            System.out.println(templateRealPath);
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(templateRealPath)));
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);
            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);
            row.getCell(7).setCellValue(totalMember);
            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);
            row.getCell(7).setCellValue(thisMonthNewMember);
            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);
            row.getCell(7).setCellValue(todayVisitsNumber);
            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);
            row.getCell(7).setCellValue(thisWeekVisitsNumber);
            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);
            row.getCell(7).setCellValue(thisMonthVisitsNumber);

            //循环
            int rowCount = 12;
            for (Map map : hotSetmeal) {
                String name = (String) map.get("name");
                long setmealCount = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                String remark = (String) map.get("remark");
                row = sheet.getRow(rowCount);
                row.getCell(4).setCellValue(name);
                row.getCell(5).setCellValue(setmealCount);
                row.getCell(6).setCellValue(proportion.doubleValue());
                row.getCell(7).setCellValue(remark);
                rowCount++;
            }

            ServletOutputStream os = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            workbook.write(os);
            os.flush();
            os.close();
            workbook.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL, null);
        }
    }

    @RequestMapping("/getOurselfTime")
    public Result getOurselfTime(String time) {
        if (!StringUtils.isEmpty(time)) {
            System.out.println(time);
            try {
                String firstTime = time.split(",")[0];
                String lastTime = time.split(",")[1];
                List<String> list = DateUtils.getMonthBetween(firstTime, lastTime, "yyyy.MM");
                Map<String, Object> map = new HashMap<>();
                map.put("months", list);
                List<Integer> memberCount = memberService.findMemberCountByMonth(list);
                map.put("memberCount", memberCount);
                return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, map);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
            }
        } else {
            try {
                Map<String, Object> map = getLastYearMap();
                return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
            }
        }
    }


    //getSexReport
    @RequestMapping("/getSexReport")
    public Result getSexReport() {
        try {
            Map<String,Object> map = reportService.findSexReportMap();
            return new Result(true,MessageConstant.GET_SEX_COUNT_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SEX_COUNT_REPORT_FAIL);
        }
    }


    //getAgeReport
    @RequestMapping("/getAgeReport")
    public Result getAgeReport() {
        try {
            Map<String,Object> map = reportService.findAgeReportMap();
            return new Result(true,MessageConstant.GET_SEX_COUNT_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SEX_COUNT_REPORT_FAIL);
        }
    }
}
