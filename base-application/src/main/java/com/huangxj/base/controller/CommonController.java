package com.huangxj.base.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName CommonController
 * @Description TODO
 * @Author: huangxj
 * @Create: 2019-09-24 19:46
 * @Version V1.0
 **/
@Controller
@RequestMapping("common")
@Slf4j
public class CommonController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    /**
     * 下载批量导入的Excel格式模板
     *
     * @throws IOException
     */
    @GetMapping("loadExcel")
    @ApiOperation("下载批量导入的Excel格式模板")
    @ResponseBody
    public void loadExcel(String name) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:/template/" + name + ".xlsx");
        InputStream inputStream = resource.getInputStream();
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + name + ".xlsx\"");
        byte[] bit = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(bit)) != -1) {
                response.getOutputStream().write(bit, 0, length);
            }
        } catch (IOException e) {
            log.error("下载Excel格式文件出错：{}", e);
            throw e;
        } finally {
            inputStream.close();
        }
    }
}
