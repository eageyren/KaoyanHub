<template>
	<div class="diy_edit page_data_sharing" id="data_sharing_edit">
		<div class='warp'>
			<div class='container'>
				<div class='row diy_edit_content_box'>
						<div v-if="$check_field('set','data_name') || $check_field('add','data_name') || $check_field('get','data_name')" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>
								资料名称:
							</span>
						</div>
								<!-- 文本 -->
									<div class="diy_field diy_text">
							<input type="text" id="form_data_name" v-model="form['data_name']" placeholder="请输入资料名称" v-if="(form['data_name'] && $check_field('set','data_name')) || (!form['data_name'] && $check_field('add','data_name'))"  :disabled="disabledObj['data_name_isDisabled']"/>
							<span v-else-if="$check_field('get','data_name')">{{ form['${obj.name}'] }}</span>
						</div>
										</div>
							<div v-if="$check_field('set','cover') || $check_field('add','cover') || $check_field('get','cover')" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>
								封面:
							</span>
						</div>
								<!-- 图片 -->
						<input type="file" :disabled="disabledObj['cover_isDisabled']" style="display: none;" id="form_img_cover" title="cover" @change="change_file($event.target.files,'cover')"/>
						<!-- 修改权限 -->
						<div class="diy_field diy_img" v-if="form['cover'] && $check_field('set','cover')">
							<label for="form_img_cover">
								<img :src="$fullUrl(form['cover'])" />
							</label>
						</div>
						<!-- 添加权限 -->
						<div class="diy_field diy_img" v-else-if="!form['cover'] && $check_field('add','cover')">
							<label for="form_img_cover">
								<div class="btn_add_img">
									<span>+</span>
								</div>
							</label>
						</div>
						<!-- 查询权限 -->
						<div class="diy_field diy_img" v-else-if="$check_field('get','cover')">
							<img :src="$fullUrl(form['cover'])" />
						</div>
							</div>
							<div v-if="$check_field('set','data_type') || $check_field('add','data_type') || $check_field('get','data_type')" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>
								资料类型:
							</span>
						</div>
								<!-- 文本 -->
									<div class="diy_field diy_text">
							<input type="text" id="form_data_type" v-model="form['data_type']" placeholder="请输入资料类型" v-if="(form['data_type'] && $check_field('set','data_type')) || (!form['data_type'] && $check_field('add','data_type'))"  :disabled="disabledObj['data_type_isDisabled']"/>
							<span v-else-if="$check_field('get','data_type')">{{ form['${obj.name}'] }}</span>
						</div>
										</div>
							<div v-if="$check_field('set','knowledge_points') || $check_field('add','knowledge_points') || $check_field('get','knowledge_points')" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>
								知识点:
							</span>
						</div>
								<!-- 文本 -->
									<div class="diy_field diy_text">
							<input type="text" id="form_knowledge_points" v-model="form['knowledge_points']" placeholder="请输入知识点" v-if="(form['knowledge_points'] && $check_field('set','knowledge_points')) || (!form['knowledge_points'] && $check_field('add','knowledge_points'))"  :disabled="disabledObj['knowledge_points_isDisabled']"/>
							<span v-else-if="$check_field('get','knowledge_points')">{{ form['${obj.name}'] }}</span>
						</div>
										</div>
							<div v-if="$check_field('set','information_documents') || $check_field('add','information_documents') || $check_field('get','information_documents')" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>
								资料文件:
							</span>
						</div>
								<!-- 文件 -->
						<input type="file" style="display: none;" id="form_file_information_documents" title="information_documents" @change="change_file($event.target.files,'information_documents')"/>
						<!-- 修改权限 -->
						<div class="diy_field diy_img" v-if="form['information_documents'] && $check_field('set','information_documents')">
							<label for="form_file_information_documents">
								<!--<span>{{form['information_documents']}} </span>-->
								<a :href="$fullUrl(form['information_documents'])" target="_blank" style="color: rgb(64, 158, 255);">点击下载</a>
							</label>
						</div>
						<!-- 添加权限 -->
						<div class="diy_field diy_img" v-else-if="!form['information_documents'] && $check_field('add','information_documents')">
							<label for="form_file_information_documents">
								<div class="btn_add_img">
									<span>+</span>
								</div>
							</label>
						</div>
						<!-- 查询权限 -->
						<div class="diy_field diy_img" v-else-if="$check_field('get','information_documents')">
							<span>{{form['information_documents']}} </span>
						</div>
							</div>
							<div v-if="$check_field('set','shared_objects') || $check_field('add','shared_objects') || $check_field('get','shared_objects')" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>
								分享对象:
							</span>
						</div>
						<div class="diy_field diy_down">
							<select id="form_shared_objects" :disabled="disabledObj['shared_objects_isDisabled']" v-model="form['shared_objects']" v-if="(form['shared_objects'] && $check_field('set','shared_objects')) || (!form['shared_objects'] && $check_field('add','shared_objects'))" >
								<option v-for="o in list_user_shared_objects" :value="o['user_id']">
									{{o['nickname'] + '-' + o['username']}}
								</option>
							</select>
							<span v-else-if="$check_field('get','shared_objects')">{{ form['shared_objects'] }}</span>
						</div>
					</div>
	




				</div>
				<div class="diy_edit_submit_box row">
					<div class="col-12">
						<div class="btn_box">
							<button class="btn_submit" @click="submit()">提交</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import mixin from "@/mixins/page.js";
	export default {
		mixins: [mixin],
		components: {},
		data() {
			return {
				url_get_obj: "~/api/data_sharing/get_obj?",
				url_add: "~/api/data_sharing/add?",
				url_set: "~/api/data_sharing/set?",

				// 登录权限
				oauth: {
					"signIn": true,
					"user_group": []
				},

				// 查询条件
				query: {
						"data_name": "",
							"cover": "",
							"data_type": "",
							"knowledge_points": "",
							"information_documents": "",
							"shared_objects": 0,
						"data_sharing_id": 0,
				},

				obj: {
						"data_name":  '', // 资料名称
							"cover":  '', // 封面
							"data_type":  '', // 资料类型
							"knowledge_points":  '', // 知识点
							"information_documents":  '', // 资料文件
							"shared_objects": 0, // 分享对象
						"data_sharing_id": 0,
				},

				// 表单字段
				form: {
						"data_name":  '', // 资料名称
							"cover":  '', // 封面
							"data_type":  '', // 资料类型
							"knowledge_points":  '', // 知识点
							"information_documents":  '', // 资料文件
							"shared_objects": 0, // 分享对象
						"data_sharing_id": 0,
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
	
				// ID字段
				field: "data_sharing_id",

			}
		},
		methods: {
			
				
				
				
				
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
		
	
			/**
			 * 修改文件
			 * @param { Object } files 上传文件对象
			 * @param { String } str 表单的属性名
			 */
			change_file(files, str) {
				var form = new FormData();
				form.append("file", files[0]);
				this.$post("~/api/data_sharing/upload?", form, (res) => {
					if (res.result) {
						this.form[str] = res.result.url;
					} else if (res.error) {
						this.$toast(res.error.message);
					}
				});
			},

			/**
			 * 获取对象后获取缓存表单
			 * @param {Object} json
			 * @param {Object} func
			 */
			get_obj_before(param){
				var form = $.db.get("form");
				// if (form) {
        //   delete(form.examine_state)
        //   delete(form.examine_reply)
        //   this.obj = $.push(this.obj ,form);
				// 	this.form = $.push(this.form ,form);
				// }
				// var arr = []
				// for (let key in form) {
				// 	arr.push(key)
				// }
				// for (var i=0;i<arr.length;i++){
				// 	this.disabledObj[arr[i] + '_isDisabled'] = true
				// }
        if (form) {
          var arr = []
          for (let key in form) {
            arr.push(key)
          }
          var arrForm = []
          for (let key in this.form) {
            arrForm.push(key)
          }
          for (var i=0;i<arr.length;i++){
            if (arr[i]!=='examine_state' && arr[i]!=='examine_reply') {
              for (var j = 0; j < arrForm.length; j++) {
                if (arrForm[j] === arr[i]) {
                  this.form[arrForm[j]] = form[arr[i]]
                  this.obj[arrForm[j]] = form[arr[i]]
                  this.disabledObj[arrForm[j] + '_isDisabled'] = true
                  break;
                }
              }
            }
          }
        }
												
        $.db.del("form");
				return param;
			},

			/**
			 * 获取对象后获取缓存表单
			 * @param {Object} json
			 * @param {Object} func
			 */
			get_obj_after(json ,func){
				// var form = $.db.get("form");
				// var obj = Object.assign({} ,form ,this.obj);
				// if (obj) {
        //   delete(obj.examine_state)
        //   delete(obj.examine_reply)
				// 	this.obj = $.push(this.obj ,obj);
				// }
				// if (form) {
        //   delete(form.examine_state)
        //   delete(form.examine_reply)
				// 	this.form = $.push(this.form ,form);
				// }
				if(func){
					func(json);
				}
			},


		},
		created() {
																					this.get_list_user_shared_objects();
			},
	}
</script>

<style>




</style>
