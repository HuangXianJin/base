package com.huangxj.base.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huangxj.common.core.service.impl.BaseServiceImpl;
import com.huangxj.common.core.utils.BeanConverter;
import com.huangxj.common.core.utils.DateUtils;
import com.huangxj.common.core.utils.FileUtil;
import com.huangxj.base.system.dto.SysFileDto;
import com.huangxj.base.system.entity.SysFile;
import com.huangxj.base.system.enums.FileType;
import com.huangxj.base.system.mapper.FileMapper;
import com.huangxj.base.system.service.FileService;
import com.huangxj.base.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 文件资源管理 服务实现类
 *
 * @author huangxj
 * @date 2020-02-20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FileServiceImpl extends BaseServiceImpl<FileMapper, SysFile> implements FileService {

    @Autowired
    UserService userService;

    @Override
    public SysFileDto upload(InputStream inputStream, String type, String description, String originName, String attachmentCode) throws IOException {
        Integer tempSize = inputStream.available() / 1024;
        //存放路径
        String filePath = getPath(type);
        String url = FileUtil.saveFile(inputStream, filePath, originName);

        //InputStream is = ImgaeCompressUtil.imageResize(inputStream);
        // String url = FtpFileUtil.upload(inputStream, filePath, FtpFileUtil.createName(originName));
        //将上传的图片加入文件系统管理表
        SysFile sysFile = new SysFile();
        sysFile.setAttachmentCode(attachmentCode);
        sysFile.setOriginName(originName);
        sysFile.setFileSize(tempSize.toString());
        sysFile.setFileType(type);
        sysFile.setUrl(url);
        sysFile.setDescription(description);
        sysFile.setOperateTime(new SimpleDateFormat(DateUtils.formatCurrentDateTime()).format(new Date()));
        baseMapper.insert(sysFile);
        SysFileDto sysFileDto = BeanConverter.convert(sysFile, SysFileDto.class);
        sysFileDto.setFileUrl(url);
        return sysFileDto;
    }

    @Override
    public SysFileDto upload(InputStream inputStream, String type, String description, String originName) throws IOException {

        return upload(inputStream, type, description, originName, null);
    }

    /**
     * 获取文件上传的根路径
     *
     * @param type
     * @return
     */
    public String getPath(String type) {
        StringBuffer sb = new StringBuffer();
        sb.append("/upload/");
        sb.append(FileType.getMessageByCode(type));
        sb.append("/");
        sb.append(DateUtils.formatMonth());
        sb.append("/");
        sb.append(DateUtils.formatDay());
        return sb.toString();
    }


    @Override
    public boolean deleteFile(String id) {
        String[] ids = id.split(",");
        if (null != ids) {
            for (String tempId : ids) {
                SysFile sysFile = baseMapper.selectById(Integer.parseInt(tempId));
                Integer flag = baseMapper.deleteById(sysFile.getId());
                if (null != sysFile && StringUtils.isNotBlank(sysFile.getUrl()) && flag > 0) {
                    FileUtil.deleteFile(sysFile.getUrl());
                }
            }
        }
        return true;
    }

    @Override
    public void deleteFileByAttachmentCode(String code) {
        List<SysFile> sysFiles = this.list(new QueryWrapper<SysFile>().lambda().eq(SysFile::getAttachmentCode, code));
        for (SysFile sysFile : sysFiles) {
            deleteFile(sysFile.getId().toString());
        }
    }

    @Override
    public boolean clearFile(String startTime, String endTime) {
        LambdaQueryWrapper<SysFile> queryWrapper = new QueryWrapper<SysFile>().lambda();
        queryWrapper.between(SysFile::getOperateTime, startTime, endTime);
        List<SysFile> listSysFile = baseMapper.selectList(queryWrapper);
        for (SysFile sysFile : listSysFile) {
            deleteFile(sysFile.getId().toString());
        }
        return true;
    }

    @Override
    public void clearFile() {
//        List<SysFileDto> sysFiles = baseMapper.selectNoUseAttachmentFile();
//        for (SysFileDto sysFile : sysFiles) {
//            if (null != sysFile && StringUtils.isNotBlank(sysFile.getUrl())) {
//                deleteFile(sysFile.getId().toString());
//            }
//        }
    }

}
