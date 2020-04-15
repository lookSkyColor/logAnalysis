package com.example.demo.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出专用
 * @Author: zhangzhiqiang1
 * @Date: 2020/3/28 17:31
 */
@Component
public class PoiUtil {


    public String export(String fullPath, List list,Integer totalCount,Class<?> clazzDto){
        String fileFullName = null;
        try {
            File fileParent = new File(fullPath).getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();
            }
            fileFullName = fullPath;

            PageInfo pageInfo = new PageInfo();
            pageInfo.setPageSize(4000);

            Integer pageCount = totalCount != null ? totalCount % pageInfo.getPageSize() > 0 ? (totalCount / pageInfo.getPageSize()) + 1 : totalCount / pageInfo.getPageSize() : 0;
            ExcelWriter excelWriter = EasyExcel
                    .write(fileFullName, clazzDto)
                    .registerWriteHandler(ExcelStyleUtils.getHorizontalCellStyleStrategy())
                    .build();
            if (0 == totalCount) {
                WriteSheet writeSheet = EasyExcel.writerSheet("sheet").build();
                excelWriter.write(null, writeSheet);
            }
            for (int index = 0; index < pageCount; index++) {
                List DtoList = new ArrayList<>();
                pageInfo.setPageNum(index + 1);
                pageInfo.setPageNum(index + 1);
                list.stream().forEach(item -> { DtoList.add(BeanHelper.copyTo(item, clazzDto)); });
                WriteSheet writeSheet = EasyExcel.writerSheet(index, String.format("第%s条到第%s条", ((index) * pageInfo.getPageSize()) + 1, (index + 1) * pageInfo.getPageSize())).build();
                excelWriter.write(DtoList, writeSheet);
            }
            excelWriter.finish();
            return fileFullName;
        } catch (Exception exception) {
            ExcelWriter excelWriter = EasyExcel.write(fileFullName, clazzDto).registerWriteHandler(ExcelStyleUtils.getHorizontalCellStyleStrategy()).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("导出异常").build();
            excelWriter.write(null, writeSheet);
            exception.printStackTrace();
            excelWriter.finish();
            return fileFullName;
        }
    }
}
