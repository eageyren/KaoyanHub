<template>
	<el-main class="bg edit_wrap">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">

							<el-col v-if="user_group === '管理员' || $check_field('get','data_name') || $check_field('add','data_name') || $check_field('set','data_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="资料名称" prop="data_name">
												<el-input id="data_name" v-model="form['data_name']" placeholder="请输入资料名称"
							  v-if="user_group === '管理员' || (form['data_sharing_id'] && $check_field('set','data_name')) || (!form['data_sharing_id'] && $check_field('add','data_name'))" :disabled="disabledObj['data_name_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','data_name')">{{form['data_name']}}</div>
											</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','cover') || $check_field('add','cover') || $check_field('set','cover')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="封面" prop="cover">
								<el-upload :disabled="disabledObj['cover_isDisabled']" id="cover" class="avatar-uploader" drag
						accept="image/gif, image/jpeg, image/png, image/jpg" action="" :http-request="upload_cover"
						:show-file-list="false" v-if="user_group === '管理员' || (form['data_sharing_id'] && $check_field('set','cover')) || (!form['data_sharing_id'] && $check_field('add','cover'))">
						<img v-if="form['cover']" :src="$fullUrl(form['cover'])" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
					<el-image v-else-if="$check_field('get','cover')" style="width: 100px; height: 100px"
						:src="$fullUrl(form['cover'])" :preview-src-list="[$fullUrl(form['cover'])]">
						<div slot="error" class="image-slot">
							<img src="../../../public/img/error.png" style="width: 90px; height: 90px" />
						</div>
					</el-image>
							</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','data_type') || $check_field('add','data_type') || $check_field('set','data_type')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="资料类型" prop="data_type">
												<el-input id="data_type" v-model="form['data_type']" placeholder="请输入资料类型"
							  v-if="user_group === '管理员' || (form['data_sharing_id'] && $check_field('set','data_type')) || (!form['data_sharing_id'] && $check_field('add','data_type'))" :disabled="disabledObj['data_type_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','data_type')">{{form['data_type']}}</div>
											</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','knowledge_points') || $check_field('add','knowledge_points') || $check_field('set','knowledge_points')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="知识点" prop="knowledge_points">
												<el-input id="knowledge_points" v-model="form['knowledge_points']" placeholder="请输入知识点"
							  v-if="user_group === '管理员' || (form['data_sharing_id'] && $check_field('set','knowledge_points')) || (!form['data_sharing_id'] && $check_field('add','knowledge_points'))" :disabled="disabledObj['knowledge_points_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','knowledge_points')">{{form['knowledge_points']}}</div>
											</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','information_documents') || $check_field('add','information_documents') || $check_field('set','information_documents')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="资料文件" prop="information_documents">
												<div v-if="disabledObj['information_documents_isDisabled']">
						<div v-if="$check_field('get','information_documents')">
							<el-button type="primary" @click="$download($fullUrl(form['information_documents']),form['information_documents'])">下载<i
									class="el-icon-download el-icon--right"></i></el-button>
						</div>
					</div>
					<div v-else>
						<el-upload v-if="user_group === '管理员' || (form['data_sharing_id'] && $check_field('set','information_documents')) || (!form['data_sharing_id'] && $check_field('add','information_documents'))" class="upload-demo" drag
								   action="" style="max-width: 300px;width: 100%;" :http-request="upload_information_documents" :limit="1" accept="">
							<i class="el-icon-upload"></i>
							<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
						</el-upload>
						<div v-else-if="$check_field('get','information_documents')">
							<el-button type="primary" @click="$download($fullUrl(form['information_documents']),form['information_documents'])">下载<i
									class="el-icon-download el-icon--right"></i></el-button>
						</div>
					</div>
											</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','shared_objects') || $check_field('add','shared_objects') || $check_field('set','shared_objects')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="分享对象" prop="shared_objects">
													<el-select v-if="user_group === '管理员' || (form['data_sharing_id'] && $check_field('set','shared_objects')) || (!form['data_sharing_id'] && $check_field('add','shared_objects'))" id="shared_objects" v-model="form['shared_objects']" :disabled="disabledObj['shared_objects_isDisabled']">
							<el-option v-for="o in list_user_shared_objects" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
						<el-select v-else-if="$check_field('get','shared_objects')" id="shared_objects" v-model="form['shared_objects']" :disabled="true">
							<el-option v-for="o in list_user_shared_objects" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
											</el-form-item>
			</el-col>
					
	
	
	
	
	
			<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
				<el-form-item>
					<el-button type="primary" @click="submit()">提交</el-button>
					<el-button @click="cancel()">取消</el-button>
				</el-form-item>
			</el-col>

		</el-form>
	</el-main>
</template>

<script>
	import mixin from "@/mixins/page.js";

	export default {
		mixins: [mixin],
		data() {
			return {
				field: "data_sharing_id",
				url_add: "~/api/data_sharing/add?",
				url_set: "~/api/data_sharing/set?",
				url_get_obj: "~/api/data_sharing/get_obj?",
				url_upload: "~/api/data_sharing/upload?",

				query: {
					"data_sharing_id": 0,
				},

				form: {
								"data_name":  '', // 资料名称
										"cover":  '', // 封面
										"data_type":  '', // 资料类型
										"knowledge_points":  '', // 知识点
										"information_documents":  '', // 资料文件
										"shared_objects": 0, // 分享对象
											"data_sharing_id": 0, // ID
					
				},
				disabledObj:{
								"data_name_isDisabled": false,
										"cover_isDisabled": false,
										"data_type_isDisabled": false,
										"knowledge_points_isDisabled": false,
										"information_documents_isDisabled": false,
										"shared_objects_isDisabled": false,
										},

	
		
		
		
		
		
					// 用户列表
				list_user_shared_objects: [],
			
			}
		},
		methods: {


	
	
						/**
			 * 上传封面
			 * @param {Object} param 图片参数
			 */
			upload_cover(param){
						this.uploadFile(param.file, "cover");
					},
	
	
			
	
			
	
						/**
			 * 上传资料文件
			 * @param {Object} param 文件参数
			 */
			upload_information_documents(param){
						this.uploadFile(param.file, "information_documents");
					},
	
	
			
	
				/**
			 * 获取系统用户用户列表
			 */
			async get_list_user_shared_objects() {
                // if(this.user_group !== "管理员" && this.form["shared_objects"] === 0) {
                //     this.form["shared_objects"] = this.user.user_id;
                // }
                var json = await this.$get("~/api/user/get_list?user_group=系统用户");
                if(json.result && json.result.list){
                    this.list_user_shared_objects = json.result.list;
                }
                else if(json.error){
                    console.error(json.error);
                }
			},
					get_user_shared_objects(id){
				var obj = this.list_user_shared_objects.getObj({"user_id":id});
				var ret = "";
				if(obj){
					if(obj.nickname){
						ret = obj.nickname;}
					else{
						ret = obj.username;
					}
				}
				return ret;
			},
		
			/**
			 * 获取对象之前
			 * @param {Object} param
			 */
			get_obj_before(param) {
				var form = "";
								// 获取缓存数据附加
				form = $.db.get("form");
							$.push(this.form ,form);
														
				if(this.form && form){
					Object.keys(this.form).forEach(key => {
						Object.keys(form).forEach(dbKey => {
							// if(dbKey === "charging_standard"){
							// 	this.form['charging_rules'] = form[dbKey];
							// 	this.disabledObj['charging_rules_isDisabled'] = true;
							// };
							if(key === dbKey){
								this.disabledObj[key+'_isDisabled'] = true;
							}
						})
					})
				}
																$.db.del("form");
				return param;
			},

			/**
			 * 获取对象之后
			 * @param {Object} json
			 * @param {Object} func
			 */
			get_obj_after(json, func){


																		

			},


			is_view(){
				var bl = this.user_group == "管理员";

				if(!bl){
					bl = this.$check_action('/data_sharing/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/data_sharing/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/data_sharing/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/data_sharing/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/data_sharing/view','get');
					console.log(bl ? "你有视图查询权限视作有查询权限" : "你没有视图查询权限");
				}

				console.log(bl ? "具有当前页面的查看权，请注意这不代表你有字段的查看权" : "无权查看当前页，请注意即便有字段查询权限没有页面查询权限也不行");

				return bl;
			},
			/**
			 * 上传文件
			 * @param {Object} param
			 */
			uploadimg(param) {
				this.uploadFile(param.file, "avatar");
			},


		},
		created() {
															this.get_list_user_shared_objects();
					},
	}
</script>

<style>
	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		line-height: 178px;
		text-align: center;
	}

	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	}




</style>
