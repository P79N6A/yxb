/**  
* 
*/ 
package com.yxbkj.yxb.common.utils;

/**
 * @ClassName: FTPStatus
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lingy
 * @date 2018年4月8日 下午1:54:51
 * @version V1.0
 */
public enum FTPStatus {  
	
	File_Exits(0), Create_Directory_Success(1), Create_Directory_Fail(2), Upload_From_Break_Success(3), Upload_From_Break_Faild(4), Download_From_Break_Success(5), Download_From_Break_Faild(6), Upload_New_File_Success(7), Upload_New_File_Failed(8), Delete_Remote_Success(9), Delete_Remote_Faild(10),Remote_Bigger_Local(11),Remote_smaller_local(12),Not_Exist_File(13),Remote_Rename_Success(14),Remote_Rename_Faild(15),File_Not_Unique(16); 
	private int status; 
	  
	  public int getStatus() 
	  { 
	    return status; 
	  } 
	  
	  public void setStatus(int status) 
	  { 
	    this.status = status; 
	  } 
	  
	  FTPStatus(int status) 
	  { 
	    this.status = status; 
	  } 
}
