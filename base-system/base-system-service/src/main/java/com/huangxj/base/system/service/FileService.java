package com.huangxj.base.system.service;

import com.huangxj.common.core.service.BaseService;
import com.huangxj.base.system.dto.SysFileDto;
import com.huangxj.base.system.entity.SysFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件资源管理 服务类
 *
 * @author huangxj
 * @date 2020-02-20
 */
public interface FileService extends BaseService<SysFile> {

    /**
     * 上传图片，记录上传数据，返回图片路径
     *
     * @param inputStream 文件输入流
     * @param type 文件类型
     * @param description 文件描述说明
     * @param originName 原始名称
     * @return
     * @throws IOException
     */
    SysFileDto upload(InputStream inputStream, String type, String description, String originName) throws IOException;

    /**
     * 上传图片，记录上传数据，返回图片路径
     *
     * @param inputStream 文件输入流
     * @param type 文件类型
     * @param description 文件描述说明
     * @param originName 原始名称
     * @param attachmentCode 附件编码
     * @return
     * @throws IOException
     */
    SysFileDto upload(InputStream inputStream, String type, String description, String originName, String attachmentCode) throws IOException;


    /**
     * 删除系统文件
     * @param ids
     * @return
     */
    boolean deleteFile(String ids);

    void deleteFileByAttachmentCode(String code);

    /**
     * 清理系统文件
     * @param startTime
     * @param endTime
     * @return
     */
    boolean clearFile(String startTime, String endTime);

    /**
     * 清理系统文件
     */
    void clearFile();

}
