package com.ibest.integral.entity;

import java.util.Date;

import com.ibest.framework.common.enums.EnumsAppType;
import com.ibest.framework.common.persistence.BaseEntity;

public class UserIntegral extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//活动名称
	private String activityName;
	//积分类别名称
	private String categoryName;	
	//积分数
	private Integer consumeNum;
	
	//jsp页面上显示字段
	private Integer number;
	
	// user_id
	private String userId;
	private String userName;
	private String integral;
	private String systemName;

	// activity_system_id
	private String activitySystemId;

	// activity_id
	private String activityId;
				
	// vin_no
	private String vinNo;
				
	// integral_id
	private String integralId;
				
	// access_system_id
	private String accessSystemId;
				
	// app_id
	private String appId;
	private String appDesc;

	// 订单号
	private String orderId;
				
	// 流水号
	private String serialId;
				
	// 类型；0获取，1使用
	private String type;
				
	// 创建人
	private String creater;
				
	// 创建时间
	private Date createTime;
				
	// 修改人
	private String modifier;
				
	// 修改时间
	private Date modifyTime;
				
	// 版本
	private String version;
								

	public Integer getConsumeNum() {
		return consumeNum;
	}

	public void setConsumeNum(Integer consumeNum) {
		this.consumeNum = consumeNum;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getUserId(){
		return userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getActivitySystemId() {
		return activitySystemId;
	}

	public void setActivitySystemId(String activitySystemId) {
		this.activitySystemId = activitySystemId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getVinNo(){
		return vinNo;
	}
	
	public void setVinNo(String vinNo){
		this.vinNo = vinNo;
	}
				
	public String getIntegralId(){
		return integralId;
	}
	
	public void setIntegralId(String integralId){
		this.integralId = integralId;
	}
				
	public String getAccessSystemId(){
		return accessSystemId;
	}
	
	public void setAccessSystemId(String accessSystemId){
		this.accessSystemId = accessSystemId;
	}
				
	public String getAppId(){
		return appId;
	}
	
	public void setAppId(String appId){
		this.appId = appId;
	}
				
	public String getOrderId(){
		return orderId;
	}
	
	public void setOrderId(String orderId){
		this.orderId = orderId;
	}
				
	public String getSerialId(){
		return serialId;
	}
	
	public void setSerialId(String serialId){
		this.serialId = serialId;
	}
				
	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}
				
	public String getCreater(){
		return creater;
	}
	
	public void setCreater(String creater){
		this.creater = creater;
	}
				
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
				
	public String getModifier(){
		return modifier;
	}
	
	public void setModifier(String modifier){
		this.modifier = modifier;
	}
				
	public Date getModifyTime(){
		return modifyTime;
	}
	
	public void setModifyTime(Date modifyTime){
		this.modifyTime = modifyTime;
	}
				
	public String getVersion(){
		return version;
	}
	
	public void setVersion(String version){
		this.version = version;
	}

	public String getAppDesc() {
		return appDesc = EnumsAppType.getLable(this.appId);
	}
}