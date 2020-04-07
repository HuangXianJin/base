package com.huangxj.base.system.enums;

 /**
  * @Description: 数据状态
  * @Author: huangxj
  * @Create: 2018/11/22
  * @Version: 1.0
  **/
public enum DataStatus {

	 /**
	  * 数据状态
	  */
	 INVALID(0,"无效"),
	 VALID(1,"有效");

	 private final int code;
	 private final String message;

	 DataStatus(final int code, final String message){
		 this.code=code;
		 this.message=message;
	 }

	 public String getMessage() {
		 return message;
	 }

	 public int getCode() {
		 return code;
	 }

}
