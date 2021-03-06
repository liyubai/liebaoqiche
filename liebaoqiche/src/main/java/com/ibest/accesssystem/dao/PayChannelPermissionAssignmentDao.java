package com.ibest.accesssystem.dao;

import java.util.List;

import com.ibest.pay.entity.PayChannel;
import com.ibest.pay.entity.PayType;

import org.apache.ibatis.annotations.Param;

import com.ibest.framework.common.persistence.MyBatisDao;
import com.ibest.accesssystem.entity.PayChannelPermissionAssignment;

import com.ibest.accesssystem.dto.input.PayChannelPermissionAssignmentInputDTO;

@MyBatisDao
public interface PayChannelPermissionAssignmentDao {

	/**
	 * 新增
	 * @param entity
	 * @return
	 */
	public int insert(PayChannelPermissionAssignment payChannelPermissionAssignment);
	
	/**
	 * 根据主键ID删除
	 * @param id
	 * @return
	 */
	public int deleteById(String id);

	/**
	 * 根据系统标识删除
	 * @param systemSign
	 * @return
	 */
	public int deleteBySystemSign(String systemSign);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public int deleteByIds(@Param("ids") List<String> ids); 
	
	/**
	 * 修改
	 * @param entity
	 * @return
	 */
	public int update(PayChannelPermissionAssignment payChannelPermissionAssignment);
	
	/**
	 * 根据Id获取唯一记录
	 * @param id
	 * @return
	 */
	public PayChannelPermissionAssignment findById(String id);
	
	/**
	 * 根据指定对象查询唯一结果
	 * @param entity
	 * @return
	 */
	public PayChannelPermissionAssignment findOneByObject(PayChannelPermissionAssignmentInputDTO payChannelPermissionAssignmentInputDto);
	
	/**
	 * 根据对象查询符合条件结果列表
	 * @param entity
	 * @return
	 */
	public List<PayChannelPermissionAssignment> findByObject(PayChannelPermissionAssignmentInputDTO payChannelPermissionAssignmentInputDto);

	/**
	 * 根据系統标识查询已经选择支付渠道
	 * @param inputDTO
	 * @returngetChoosedRole
	 */
	public List<PayType> getChoosedPayChannel(PayChannelPermissionAssignmentInputDTO inputDTO);

	/**
	 * 根据系統标识查询未选择的支付渠道
	 * @param inputDTO
	 * @return
	 */
	public List<PayChannel> getUnChoosedPayChannel(PayChannelPermissionAssignmentInputDTO inputDTO);


	/**
	 * 根据对象查询符合条件记录总条数
	 * @param entity
	 * @return
	 */
	public long countByObject(PayChannelPermissionAssignmentInputDTO payChannelPermissionAssignmentInputDto);
	
	public List<PayChannelPermissionAssignment> findByAll();
	
	public int updateIsOpen(PayChannelPermissionAssignment payChannelPermissionAssignment);
}
