<template>
	<el-main class="bg edit_wrap">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">

							<el-col v-if="user_group === '管理员' || $check_field('get','name_of_institution') || $check_field('add','name_of_institution') || $check_field('set','name_of_institution')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="院校名称" prop="name_of_institution">
												<el-input id="name_of_institution" v-model="form['name_of_institution']" placeholder="请输入院校名称"
							  v-if="user_group === '管理员' || (form['colleges_and_universities_id'] && $check_field('set','name_of_institution')) || (!form['colleges_and_universities_id'] && $check_field('add','name_of_institution'))" :disabled="disabledObj['name_of_institution_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','name_of_institution')">{{form['name_of_institution']}}</div>
											</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','cover') || $check_field('add','cover') || $check_field('set','cover')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="封面" prop="cover">
								<el-upload :disabled="disabledObj['cover_isDisabled']" id="cover" class="avatar-uploader" drag
						accept="image/gif, image/jpeg, image/png, image/jpg" action="" :http-request="upload_cover"
						:show-file-list="false" v-if="user_group === '管理员' || (form['colleges_and_universities_id'] && $check_field('set','cover')) || (!form['colleges_and_universities_id'] && $check_field('add','cover'))">
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
								<el-col v-if="user_group === '管理员' || $check_field('get','college_major') || $check_field('add','college_major') || $check_field('set','college_major')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="院校专业" prop="college_major">
								<el-input type="textarea" id="college_major" v-model="form['college_major']" placeholder="请输入院校专业"
						v-if="user_group === '管理员' || (form['colleges_and_universities_id'] && $check_field('set','college_major')) || (!form['colleges_and_universities_id'] && $check_field('add','college_major'))" :disabled="disabledObj['college_major_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','college_major')">{{form['college_major']}}</div>
							</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','score_over_the_years') || $check_field('add','score_over_the_years') || $check_field('set','score_over_the_years')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="历年分数线" prop="score_over_the_years">
								<el-input type="textarea" id="score_over_the_years" v-model="form['score_over_the_years']" placeholder="请输入历年分数线"
						v-if="user_group === '管理员' || (form['colleges_and_universities_id'] && $check_field('set','score_over_the_years')) || (!form['colleges_and_universities_id'] && $check_field('add','score_over_the_years'))" :disabled="disabledObj['score_over_the_years_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','score_over_the_years')">{{form['score_over_the_years']}}</div>
							</el-form-item>
			</el-col>
								<el-col v-if="user_group === '管理员' || $check_field('get','details_of_institutions') || $check_field('add','details_of_institutions') || $check_field('set','details_of_institutions')" :xs="24" :sm="24" :lg="24" class="el_form_editor_warp">
				<el-form-item label="院校详情" prop="details_of_institutions">
					<quill-editor v-model.number="form['details_of_institutions']"
						v-if="user_group === '管理员' || (form['colleges_and_universities_id'] && $check_field('set','details_of_institutions')) || (!form['colleges_and_universities_id'] && $check_field('add','details_of_institutions')) ">
					</quill-editor>
					<div v-else-if="$check_field('get','details_of_institutions')" v-html="form['details_of_institutions']"></div>
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
				field: "colleges_and_universities_id",
				url_add: "~/api/colleges_and_universities/add?",
				url_set: "~/api/colleges_and_universities/set?",
				url_get_obj: "~/api/colleges_and_universities/get_obj?",
				url_upload: "~/api/colleges_and_universities/upload?",

				query: {
					"colleges_and_universities_id": 0,
				},

				form: {
								"name_of_institution":  '', // 院校名称
										"cover":  '', // 封面
										"college_major":  '', // 院校专业
										"score_over_the_years":  '', // 历年分数线
										"details_of_institutions":  '', // 院校详情
											"colleges_and_universities_id": 0, // ID
					
				},
				disabledObj:{
								"name_of_institution_isDisabled": false,
										"cover_isDisabled": false,
										"college_major_isDisabled": false,
										"score_over_the_years_isDisabled": false,
										"details_of_institutions_isDisabled": false,
										},

	
		
		
		
		
	
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
					bl = this.$check_action('/colleges_and_universities/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/colleges_and_universities/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/colleges_and_universities/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/colleges_and_universities/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/colleges_and_universities/view','get');
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
