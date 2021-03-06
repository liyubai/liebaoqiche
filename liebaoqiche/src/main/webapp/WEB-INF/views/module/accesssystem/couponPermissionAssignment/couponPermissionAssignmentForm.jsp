
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE>
<html>
<head>
	<title>SysUser</title>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
</head>
<body>
<%@ include file="/WEB-INF/views/include/loading.jsp"%>
<div style="height: 500px;margin:10px 20px;box-sizing:border-box;">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north', collapsible:false" title="当前操作系统信息"
		     style="height: 80px; line-height: 40px; padding: 0 10px;">
			系统名称：<span id="systemName"></span>
		</div>
		<div data-options="region: 'west', collapsible:false" title="未选卡券"
		     style="width: 350px;">
			<table id="Grid_UnchoosedPayChannels" class="easyui-datagrid"
			       ctrlSelect="true" striped="true" rownumbers="true"
			       pagination="false" fitColumns="true" loadMsg="正在加载数据，请稍等..."
			       data-options="fit:true, checkOnSelect:true, selectOnCheck:true, border: false">
				<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'couponName',width:120">卡券名称</th>
					<th data-options="field:'id',hidden:true,width:150">卡券代码</th>
				</tr>
				</thead>
			</table>
		</div>
		<div class="btnLink" data-options="region:'center', border: false">
			<a id="btnRight_SysSelectChannel" href="javascript:void(0)"
			   class="easyui-linkbutton button-default"><i
					class="fa fa-angle-right fa-lg"></i></a> <a
				id="btnDoubleRight_SysUserSelectRole" href="javascript:void(0)"
				class="easyui-linkbutton button-default"><i
				class="fa fa-angle-double-right fa-lg"></i></a> <a
				id="btnDoubleLeft_SysUserSelectRole" href="javascript:void(0)"
				class="easyui-linkbutton button-default"><i
				class="fa fa-angle-double-left fa-lg"></i></a> <a
				id="btnLeft_SysUserSelectRole" href="javascript:void(0)"
				class="easyui-linkbutton button-default"><i
				class="fa fa-angle-left fa-lg"></i></a>
		</div>
		<div data-options="region: 'east', collapsible:false" title="已有卡券"
		     style="width: 350px">
			<table id="Grid_choosedPayChannels" class="easyui-datagrid"
			       ctrlSelect="true" striped="true" rownumbers="true"
			       pagination="false" fitColumns="true" loadMsg="正在加载数据，请稍等..."
			       data-options="fit:true, checkOnSelect:true, selectOnCheck:true, border: false">
				<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'couponName',width:120">卡券名称</th>
					<th data-options="field:'id',hidden:true,width:150">卡券代码</th>
				</tr>
				</thead>
			</table>
		</div>

		<div data-options="region:'south',border:false"
		     style="text-align: right; padding: 5px;">
			<a id="btnSave_SysUserAccredit"
			   class="easyui-linkbutton button-success"><i
					class="fa fa-wrench fa-lg"></i>&nbsp;&nbsp;授权</a>
			<a id="btnCancel" href="javascript:void(0)"
			   class="easyui-linkbutton button-warning" style="width: 80px;"><i
					class="fa fa-remove fa-lg"></i>&nbsp;&nbsp;取消</a>
		</div>

	</div></div>
<!-- </div> -->

<!-- js脚本，必须写在body中，tab的url加载模式只会异步加载body中的内容到tab中 -->
<script type="text/javascript">
	require(['jquery','common'], function($){
		var systemSign = location.search.replace("?id=","");
		$(function(){
			// 加载角色授权信息
			$.ajax({
				url: '${ctx}/accesssystem/accessSystem/sysCoupon',
				data: {
					systemSign: systemSign
				},
				type: 'POST',
				dataType: 'JSON',
				success: function (json) {

					// 设置当前显示的角色名称
					$("#systemName").html(json.accessSystemName);
					$("#Grid_UnchoosedPayChannels").datagrid();
					$("#Grid_choosedPayChannels").datagrid();
					// 显示未选中的角色
					if(json && json.sysUnChooseCouponList){
						$("#Grid_UnchoosedPayChannels").datagrid('loadData', json.sysUnChooseCouponList);
					}

					// 显示已经选中的角色
					if(json && json.sysChooseCouponList){
						$("#Grid_choosedPayChannels").datagrid('loadData', json.sysChooseCouponList);
					}

				}
			});
		});

		//授权按钮保存
		$('#btnSave_SysUserAccredit').click(function () {
			$.messager.confirm("授权确认","确认要给该系统进行授权？",function(r){
				if(r){
					var allChoosedChannels = $("#Grid_choosedPayChannels").datagrid('getData');
					var allChannels = [];
					for(var i=0;i<allChoosedChannels.rows.length;i++){
						allChannels.push(allChoosedChannels.rows[i]);
					}
					var allChannelId = "";
					for(var i = 0; i < allChannels.length; i++){
						allChannelId += allChannels[i].id;
						if(i != allChannels.length - 1){
							allChannelId += ",";
						}
					}

					$.ajax({
						url: '${ctx}/accesssystem/accessSystem/accreditCoupon',
						data: {
							systemSign : systemSign,
							allCouponsId : allChannelId
						},
						type: 'post',
						dataType: 'json',
						success: function (json) {
							if (json && json.rtnCode == '00000000') {
								$.messager.alert('提示', "授权成功", "success",function(){
									hideDialog();
								});
							} else {
								$.messager.alert('提示', json.rtnMsg, "error");
							}
						}
					});
				}
			});
		});

		//点击插入选择的N个角色
		$("#btnRight_SysSelectChannel").click(function(){

			var selectedChannels = $("#Grid_UnchoosedPayChannels").datagrid('getSelections');
			var allSelectedChannels = [];
			//插入行
			for(var i=0;i<selectedChannels.length;i++){
				allSelectedChannels.push(selectedChannels[i]);
				$("#Grid_choosedPayChannels").datagrid('insertRow', {
					row:{
						couponName : selectedChannels[i].couponName ,
						code : selectedChannels[i].code,
						id : selectedChannels[i].id
					}
				});

			}
			//删除行
			for(var j =0;j<allSelectedChannels.length;j++){
				var index = $('#Grid_UnchoosedPayChannels').datagrid('getRowIndex',allSelectedChannels[j]);
				$('#Grid_UnchoosedPayChannels').datagrid('deleteRow',index);
			}
		});

		//点击插入选择所有角色
		$("#btnDoubleRight_SysUserSelectRole").click(function(){

			var allUnchoosedChannels = $("#Grid_UnchoosedPayChannels").datagrid('getData');
			var allChannels = [];
			//插入所有行
			for(var i=0;i<allUnchoosedChannels.rows.length;i++){
				allChannels.push(allUnchoosedChannels.rows[i]);
				$("#Grid_choosedPayChannels").datagrid('insertRow', {
					row:{
						couponName : allUnchoosedChannels.rows[i].couponName ,
						code : allUnchoosedChannels.rows[i].code,
						id : allUnchoosedChannels.rows[i].id
					}
				});
			}
			//删除所有行
			for(var j =0;j<allChannels.length;j++){
				var index = $('#Grid_UnchoosedPayChannels').datagrid('getRowIndex',allChannels[j]);
				$('#Grid_UnchoosedPayChannels').datagrid('deleteRow',index);
			}

		});

		//点击移除选择的N行
		$("#btnLeft_SysUserSelectRole").click(function(){

			var selectedChannels = $("#Grid_choosedPayChannels").datagrid('getSelections');
			var allSelectedChannels = [];
			//插入行
			for(var i=0;i<selectedChannels.length;i++){
				allSelectedChannels.push(selectedChannels[i]);
				$("#Grid_UnchoosedPayChannels").datagrid('insertRow', {
					row:{
						couponName : selectedChannels[i].couponName ,
						code : selectedChannels[i].code,
						id : selectedChannels[i].id
					}
				});

			}
			//删除行
			for(var j =0;j<allSelectedChannels.length;j++){
				var index = $('#Grid_choosedPayChannels').datagrid('getRowIndex',allSelectedChannels[j]);
				$('#Grid_choosedPayChannels').datagrid('deleteRow',index);
			}
		});

		//点击移除选择的所有角色
		$("#btnDoubleLeft_SysUserSelectRole").click(function(){

			var allChoosedChannels = $("#Grid_choosedPayChannels").datagrid('getData');
			var allChannels = [];
			//插入所有行
			for(var i=0;i<allChoosedChannels.rows.length;i++){
				allChannels.push(allChoosedChannels.rows[i]);
				$("#Grid_UnchoosedPayChannels").datagrid('insertRow', {
					row:{
						couponName : allChoosedChannels.rows[i].couponName ,
						code : allChoosedChannels.rows[i].code,
						id : allChoosedChannels.rows[i].id
					}
				});
			}
			//删除所有行
			for(var j =0;j<allChannels.length;j++){
				var index = $('#Grid_choosedPayChannels').datagrid('getRowIndex',allChannels[j]);
				$('#Grid_choosedPayChannels').datagrid('deleteRow',index);
			}

		});

		// 给取消按钮添加点击事件
		$("#btnCancel").on('click', function(){
			hideDialog();
		});
	});

	// 验证是否已经选择过某一资源
	function checkResourcesChoosed(resourcesId){
		var choosed = $("#Grid_choosedPayChannels").datagrid("getData");
		//alert(choosed.rows.length);
		var isExist = false;
		for(var j = 0; j < choosed.rows.length; j++){
			//alert(choosed.rows[j].id);
			if(resourcesId == choosed.rows[j].id){
				isExist = true;
				break;
			}
		}
		if(isExist){
			return true;
		}
		return false;
	}
	// 获取指定资源在已选择资源中的下标
	function getChoosedIndex(resourcesId){
		var choosed = $("#Grid_choosedPayChannels").datagrid("getData");
		for(var j = 0; j < choosed.rows.length; j++){
			if(resourcesId == choosed.rows[j].id){
				index = j;
				break;
			}
		}
		return index;
	}
	//为选择资源的下标
	function getUnchoosedIndex(resourcesId){
		var index = -1;
		var unchoosed = $("#Grid_UnchoosedPayChannels").datagrid("getData");
		for(var j = 0; j < unchoosed.rows.length; j++){
			if(resourcesId == unchoosed.rows[j].id){
				index = j;
				break;
			}
		}
		return index;
	}
</script>
</body>
</html>