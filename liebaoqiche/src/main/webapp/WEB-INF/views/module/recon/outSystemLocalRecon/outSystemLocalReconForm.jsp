<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE>
<html>
<head>
	<title>OutSystemLocalRecon</title>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/loading.jsp"%>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center'">
	    	<form id="DataForm" method="post">
	    		<table style="width: 100%;">
	    			<tbody>
	    				<tr>
	    					<td style="width: 50%; padding: 5px; text-align: center;">
	    						<input class="easyui-textbox" id="id" name="id" value="${outSystemLocalRecon.id}" labelWidth="100" label="主键" required="true" data-options="prompt:'主键'" style="width: 90%;">
	    					</td>
	    					<td style="width: 50%; padding: 5px; text-align: center;">
	    						<input class="easyui-textbox" id="orderId" name="orderId" value="${outSystemLocalRecon.orderId}" labelWidth="100" label="订单号"  data-options="prompt:'订单号'" style="width: 90%;">
	    					</td>
	    				</tr>
	    				<tr>
	    					<td style="width: 50%; padding: 5px; text-align: center;">
	    						<input class="easyui-textbox" id="transactionId" name="transactionId" value="${outSystemLocalRecon.transactionId}" labelWidth="100" label="支付系统流水号"  data-options="prompt:'支付系统流水号'" style="width: 90%;">
	    					</td>
	    					<td style="width: 50%; padding: 5px; text-align: center;">
	    						<input class="easyui-textbox" id="renconType" name="renconType" value="${outSystemLocalRecon.renconType}" labelWidth="100" label="对账类型;00微信;01支付宝;02银联"  data-options="prompt:'对账类型;00微信;01支付宝;02银联'" style="width: 90%;">
	    					</td>
	    				</tr>
	    				<tr>
	    					<td style="width: 50%; padding: 5px; text-align: center;">
	    						<input class="easyui-textbox" id="renconTime" name="renconTime" value="${outSystemLocalRecon.renconTime}" labelWidth="100" label="对账日期:20180424"  data-options="prompt:'对账日期:20180424'" style="width: 90%;">
	    					</td>
	    					<td style="width: 50%; padding: 5px; text-align: center;">
	    						<input class="easyui-textbox" id="billType" name="billType" value="${outSystemLocalRecon.billType}" labelWidth="100" label="账单类型;00支付成功;01退款"  data-options="prompt:'账单类型;00支付成功;01退款'" style="width: 90%;">
	    					</td>
	    				</tr>
	    				<tr>
	    					<td style="width: 50%; padding: 5px; text-align: center;">
	    						<input class="easyui-textbox" id="tradeAmount" name="tradeAmount" value="${outSystemLocalRecon.tradeAmount}" labelWidth="100" label="交易金额"  data-options="prompt:'交易金额'" style="width: 90%;">
	    					</td>
	    					<td style="width: 50%; padding: 5px; text-align: center;">
	    						<input class="easyui-textbox" id="tradeTime" name="tradeTime" value="${outSystemLocalRecon.tradeTime}" labelWidth="100" label="交易时间"  data-options="prompt:'交易时间'" style="width: 90%;">
	    					</td>
	    				</tr>
	    				<tr>
	    					<td style="width: 50%; padding: 5px; text-align: center;">
	    						<input class="easyui-textbox" id="tradeType" name="tradeType" value="${outSystemLocalRecon.tradeType}" labelWidth="100" label="支付类型"  data-options="prompt:'支付类型'" style="width: 90%;">
	    					</td>
	    					<td style="width: 50%; padding: 5px; text-align: center;">
	    						<input class="easyui-textbox" id="tradeState" name="tradeState" value="${outSystemLocalRecon.tradeState}" labelWidth="100" label="支付状态"  data-options="prompt:'支付状态'" style="width: 90%;">
	    					</td>
	    				</tr>
	    				<tr>
	    					<td style="width: 50%; padding: 5px; text-align: center;">
	    						<input class="easyui-textbox" id="reconState" name="reconState" value="${outSystemLocalRecon.reconState}" labelWidth="100" label="对账状态;00成功；01失败"  data-options="prompt:'对账状态;00成功；01失败'" style="width: 90%;">
	    					</td>
	    					<td style="width: 50%; padding: 5px; text-align: center;">
	    						<input class="easyui-textbox" id="failMessage" name="failMessage" value="${outSystemLocalRecon.failMessage}" labelWidth="100" label="对账失败原因"  data-options="prompt:'对账失败原因'" style="width: 90%;">
	    					</td>
	    				</tr>
	    				<tr>
	    					<td style="width: 50%; padding: 5px; text-align: center;">
	    						<input class="easyui-textbox" id="creater" name="creater" value="${outSystemLocalRecon.creater}" labelWidth="100" label="创建人"  data-options="prompt:'创建人'" style="width: 90%;">
	    					</td>
	    					<td style="width: 50%; padding: 5px; text-align: center;">
	    						<input class="easyui-datebox" id="createTime" name="createTime" value="${outSystemLocalRecon.createTime}" labelWidth="100" label="创建时间"  data-options="prompt:'创建时间'" style="width: 90%;">
	    					</td>
	    				</tr>
	    				<tr>
	    					<td style="width: 50%; padding: 5px; text-align: center;">
	    						<input class="easyui-textbox" id="modifier" name="modifier" value="${outSystemLocalRecon.modifier}" labelWidth="100" label="修改人"  data-options="prompt:'修改人'" style="width: 90%;">
	    					</td>
	    					<td style="width: 50%; padding: 5px; text-align: center;">
	    						<input class="easyui-datebox" id="modifyTime" name="modifyTime" value="${outSystemLocalRecon.modifyTime}" labelWidth="100" label="修改时间"  data-options="prompt:'修改时间'" style="width: 90%;">
	    					</td>
	    				</tr>
	    				<tr>
	    					<td style="width: 50%; padding: 5px; text-align: center;">
	    						<input class="easyui-textbox" id="remarks" name="remarks" value="${outSystemLocalRecon.remarks}" labelWidth="100" label="备注"  data-options="prompt:'备注'" style="width: 90%;">
	    					</td>
	    				</tr>
	    				<tr>
	    					<td style="width: 50%; padding: 5px;" colspan="2">
	    						<label class="label-top">备注:</label>
	    						<input class="easyui-textbox" name="remarks" style="width:99.5%; height:80px;" data-options="multiline:true">
	    					</td>
	    				</tr>
	    			</tbody>
	    		</table>
			</form>
		</div>
		<div data-options="region:'south',border:false" style="text-align:right; padding:10px;">
            <a id="btnSave" href="javascript:void(0)" class="easyui-linkbutton button-success" style="width: 80px;"><i class="fa fa-check fa-lg"></i>&nbsp;&nbsp;保存</a>
            <a id="btnCancel" href="javascript:void(0)" class="easyui-linkbutton button-warning" style="width: 80px;"><i class="fa fa-remove fa-lg"></i>&nbsp;&nbsp;取消</a>
        </div>
   	</div>
    
    <!-- js脚本，必须写在body中，tab的url加载模式只会异步加载body中的内容到tab中 -->
	<script type="text/javascript">
		require(['jquery','common'], function($){
			// 给保存按钮添加点击事件
			$("#btnSave").on('click', function(){
				
				$("#btnSave").linkbutton('disable');
			
				$("#DataForm").form('submit',{
					url:$("#id").val() == ''?'${ctx}/recon/outSystemLocalRecon/create':'${ctx}/recon/outSystemLocalRecon/update',
					onSubmit:function(){
						return $(this).form('enableValidation').form('validate');
					},
					success: function(data){
						var json = $.parseJSON(data);
						if(json && json.rtnCode == '00000000'){
							$.messager.alert('提示', "保存成功", "success");
							$("#btnSave").linkbutton('enable');
						}else{
							$.messager.alert('提示', json.rtnMsg, "error");
							$("#btnSave").linkbutton('enable');
						}
					},
					error: function(){
						$("#btnSave").linkbutton('enable');
					}
				});
			});
			
			// 给保存按钮添加点击事件
			$("#btnCancel").on('click', function(){
				history.go(-1);
			});
		});
	</script>
</body>
</html>