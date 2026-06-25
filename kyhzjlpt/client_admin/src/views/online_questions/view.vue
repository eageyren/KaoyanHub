<template>
	<el-main class="bg edit_wrap">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">

							<el-col v-if="user_group === '管理员' || $check_field('get','question_no') || $check_field('add','question_no') || $check_field('set','question_no')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="问题编号" prop="question_no">
												<el-input id="question_no" v-model="form['question_no']" placeholder="请输入问题编号"
							  v-if="user_group === '管理员' || (form['online_questions_id'] && $check_field('set','question_no')) || (!form['online_questions_id'] && $check_field('add','question_no'))" :disabled="true"></el-input>
					<div v-else-if="$check_field('get','question_no')">{{form['question_no']}}</div>
											</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','ask_the_user') || $check_field('add','ask_the_user') || $check_field('set','ask_the_user')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="提问用户" prop="ask_the_user">
																		<div v-if="user_group !== '管理员'">
							{{ get_user_session_ask_the_user(form['ask_the_user']) }}
							<!--<el-input id="business_name" v-model="form['ask_the_user']" placeholder="请输入提问用户"-->
							<!--v-if="user_group === '管理员' || (form['online_questions_id'] && $check_field('set','ask_the_user')) || (!form['online_questions_id'] && $check_field('add','ask_the_user'))" :disabled="disabledObj['ask_the_user_isDisabled']"></el-input>-->
							<!--<div v-else-if="$check_field('get','ask_the_user')">{{form['ask_the_user']}}</div>-->
							<el-select v-if="user_group === '管理员' || (form['online_questions_id'] && $check_field('set','ask_the_user')) || (!form['online_questions_id'] && $check_field('add','ask_the_user'))" id="ask_the_user" v-model="form['ask_the_user']" :disabled="disabledObj['ask_the_user_isDisabled']">
								<el-option v-for="o in list_user_ask_the_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
										   :value="o['user_id']">
								</el-option>
							</el-select>
							<el-select v-else-if="$check_field('get','ask_the_user')" id="ask_the_user" v-model="form['ask_the_user']" :disabled="true">
								<el-option v-for="o in list_user_ask_the_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
										   :value="o['user_id']">
								</el-option>
							</el-select>
						</div>
						<el-select v-else id="ask_the_user" v-model="form['ask_the_user']" :disabled="disabledObj['ask_the_user_isDisabled']">
							<el-option v-for="o in list_user_ask_the_user" :key="o['username']" :label="o['nickname'] + '-' + o['username']"
									   :value="o['user_id']">
							</el-option>
						</el-select>
																</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','user_name') || $check_field('add','user_name') || $check_field('set','user_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="用户姓名" prop="user_name">
												<el-input id="user_name" v-model="form['user_name']" placeholder="请输入用户姓名"
							  v-if="user_group === '管理员' || (form['online_questions_id'] && $check_field('set','user_name')) || (!form['online_questions_id'] && $check_field('add','user_name'))" :disabled="disabledObj['user_name_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','user_name')">{{form['user_name']}}</div>
											</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','problem_description') || $check_field('add','problem_description') || $check_field('set','problem_description')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="问题描述" prop="problem_description">
								<el-input type="textarea" id="problem_description" v-model="form['problem_description']" placeholder="请输入问题描述"
						v-if="user_group === '管理员' || (form['online_questions_id'] && $check_field('set','problem_description')) || (!form['online_questions_id'] && $check_field('add','problem_description'))" :disabled="disabledObj['problem_description_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','problem_description')">{{form['problem_description']}}</div>
							</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','problem_attachment') || $check_field('add','problem_attachment') || $check_field('set','problem_attachment')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="问题附件" prop="problem_attachment">
												<div v-if="disabledObj['problem_attachment_isDisabled']">
						<div v-if="$check_field('get','problem_attachment')">
							<el-button type="primary" @click="$download($fullUrl(form['problem_attachment']),form['problem_attachment'])">下载<i
									class="el-icon-download el-icon--right"></i></el-button>
						</div>
					</div>
					<div v-else>
						<el-upload v-if="user_group === '管理员' || (form['online_questions_id'] && $check_field('set','problem_attachment')) || (!form['online_questions_id'] && $check_field('add','problem_attachment'))" class="upload-demo" drag
								   action="" style="max-width: 300px;width: 100%;" :http-request="upload_problem_attachment" :limit="1" accept="">
							<i class="el-icon-upload"></i>
							<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
						</el-upload>
						<div v-else-if="$check_field('get','problem_attachment')">
							<el-button type="primary" @click="$download($fullUrl(form['problem_attachment']),form['problem_attachment'])">下载<i
									class="el-icon-download el-icon--right"></i></el-button>
						</div>
					</div>
											</el-form-item>
			</el-col>
							<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="审核状态" prop="examine_state">
					<el-select id="examine_state" v-model="form['examine_state']"
						v-if="user_group === '管理员' || (form['examine_state'] && $check_examine()) || (!form['examine_state'] && $check_examine())">
						<el-option key="未审核" label="未审核" value="未审核"></el-option>
						<el-option key="已通过" label="已通过" value="已通过"></el-option>
						<el-option key="未通过" label="未通过" value="未通过"></el-option>
					</el-select>
					<div v-else>{{form["examine_state"]}}</div>
				</el-form-item>
			</el-col>
					<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="审核回复" prop="examine_reply">
					<el-input id="examine_reply" v-model="form['examine_reply']" placeholder="请输入审核回复"
						v-if="user_group === '管理员' || (form['examine_reply'] && $check_examine()) || (!form['examine_reply'] && $check_examine())"></el-input>
					<div v-else>{{form["examine_reply"]}}</div>
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
				field: "online_questions_id",
				url_add: "~/api/online_questions/add?",
				url_set: "~/api/online_questions/set?",
				url_get_obj: "~/api/online_questions/get_obj?",
				url_upload: "~/api/online_questions/upload?",

				query: {
					"online_questions_id": 0,
				},

				form: {
								"question_no": this.$get_stamp(), // 问题编号
										"ask_the_user": 0, // 提问用户
										"user_name":  '', // 用户姓名
										"problem_description":  '', // 问题描述
										"problem_attachment":  '', // 问题附件
									"examine_state": "未审核",
							"examine_reply": "",
							"online_questions_id": 0, // ID
					
				},
				disabledObj:{
								"question_no_isDisabled": false,
										"ask_the_user_isDisabled": false,
										"user_name_isDisabled": false,
										"problem_description_isDisabled": false,
										"problem_attachment_isDisabled": false,
										},

	
		
					// 用户列表
				list_user_ask_the_user: [],
						// 用户组
				group_user_ask_the_user: "",
				
		
		
	
			}
		},
		methods: {


	
	
			
	
				/**
			 * 获取系统用户用户列表
			 */
			async get_list_user_ask_the_user() {
                // if(this.user_group !== "管理员" && this.form["ask_the_user"] === 0) {
                //     this.form["ask_the_user"] = this.user.user_id;
                // }
                var json = await this.$get("~/api/user/get_list?user_group=系统用户");
                if(json.result && json.result.list){
                    this.list_user_ask_the_user = json.result.list;
                }
                else if(json.error){
                    console.error(json.error);
                }
			},
					/**
			 * 获取系统用户用户组
			 */
			async get_group_user_ask_the_user() {
							this.form["ask_the_user"] = this.user.user_id;
							var json = await this.$get("~/api/user_group/get_obj?name=系统用户");
				if(json.result && json.result.obj){
					this.group_user_ask_the_user = json.result.obj;
				}
				else if(json.error){
					console.error(json.error);
				}
			},
			get_user_session_ask_the_user(id){
				var _this = this;
				var user_id = {"user_id":id}
				var url = "~/api/"+_this.group_user_ask_the_user.source_table+"/get_obj?"
				this.$get(url, user_id, function(res) {
					if (res.result && res.result.obj) {
						var arr = []
						for (let key in res.result.obj) {
							arr.push(key)
						}
						var arrForm = []
									for (let key in _this.form) {
							arrForm.push(key)
						}
												_this.form["ask_the_user"] = id
									_this.disabledObj['ask_the_user' + '_isDisabled'] = true
						for (var i=0;i<arr.length;i++){
						  if (arr[i]!=='examine_state' && arr[i]!=='examine_reply') {
							for (var j = 0; j < arrForm.length; j++) {
							  if (arr[i] === arrForm[j]) {
								if (arr[i] !== "ask_the_user") {
			                      _this.form[arrForm[j]] = res.result.obj[arr[i]]
			                      _this.disabledObj[arrForm[j] + '_isDisabled'] = true
								  break;
								} else {
								  _this.disabledObj[arrForm[j] + '_isDisabled'] = true
								}
							  }
							}
						  }
						}
					}
				});
			},
					get_user_ask_the_user(id){
				var obj = this.list_user_ask_the_user.getObj({"user_id":id});
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
			 * 上传问题附件
			 * @param {Object} param 文件参数
			 */
			upload_problem_attachment(param){
						this.uploadFile(param.file, "problem_attachment");
					},
	
	
		
			/**
			 * 获取对象之前
			 * @param {Object} param
			 */
			get_obj_before(param) {
				var form = "";
													
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
					bl = this.$check_action('/online_questions/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/online_questions/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/online_questions/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/online_questions/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/online_questions/view','get');
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
							this.get_list_user_ask_the_user();
					this.get_group_user_ask_the_user();
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
