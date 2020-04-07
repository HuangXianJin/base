package com.huangxj.base.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @ClassName Log
 * @Description 日志管理
 * @Author: huangxj
 * @Create: 2018/11/30
 **/
@TableName("sys_log")
public class Log {
   private Integer id;

   private String url;

   private String httpMethod;

   private String ip;

   private String classMethod;

   private String module;

   private String operation;

   private String operator;

   private String operateTime;

   private String args;

   private String response;

   private Integer createId;

   private Date createTime;

   private Integer modifyId;

   private Date modifyTime;

   public Integer getId() {
       return id;
   }

   public void setId(Integer id) {
       this.id = id;
   }

   public String getUrl() {
       return url;
   }

   public void setUrl(String url) {
       this.url = url == null ? null : url.trim();
   }

   public String getHttpMethod() {
       return httpMethod;
   }

   public void setHttpMethod(String httpMethod) {
       this.httpMethod = httpMethod == null ? null : httpMethod.trim();
   }

   public String getIp() {
       return ip;
   }

   public void setIp(String ip) {
       this.ip = ip == null ? null : ip.trim();
   }

   public String getClassMethod() {
       return classMethod;
   }

   public void setClassMethod(String classMethod) {
       this.classMethod = classMethod == null ? null : classMethod.trim();
   }

   public String getOperation() {
       return operation;
   }

   public void setOperation(String operation) {
       this.operation = operation == null ? null : operation.trim();
   }

   public String getOperator() {
       return operator;
   }

   public void setOperator(String operator) {
       this.operator = operator == null ? null : operator.trim();
   }

   public String getOperateTime() {
       return operateTime;
   }

   public void setOperateTime(String operateTime) {
       this.operateTime = operateTime == null ? null : operateTime.trim();
   }

   public String getArgs() {
       return args;
   }

   public void setArgs(String args) {
       this.args = args == null ? null : args.trim();
   }

   public String getResponse() {
       return response;
   }

   public void setResponse(String response) {
       this.response = response == null ? null : response.trim();
   }

   public Integer getCreateId() {
       return createId;
   }

   public void setCreateId(Integer createId) {
       this.createId = createId;
   }

   public Date getCreateTime() {
       return createTime;
   }

   public void setCreateTime(Date createTime) {
       this.createTime = createTime;
   }

   public Integer getModifyId() {
       return modifyId;
   }

   public void setModifyId(Integer modifyId) {
       this.modifyId = modifyId;
   }

   public Date getModifyTime() {
       return modifyTime;
   }

   public void setModifyTime(Date modifyTime) {
       this.modifyTime = modifyTime;
   }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "\n Log{" +
                " \n url:'" + url + '\'' +
                ",\n httpMethod:'" + httpMethod + '\'' +
                ",\n ip:'" + ip + '\'' +
                ",\n classMethod:'" + classMethod + '\'' +
                ",\n module:'" + module + '\'' +
                ",\n operation:'" + operation + '\'' +
                ",\n operator:'" + operator + '\'' +
                ",\n operateTime:'" + operateTime + '\'' +
                ",\n args:'" + args + '\'' +
                ",\n response:'" + response + '\'' +
                '}';
    }
}