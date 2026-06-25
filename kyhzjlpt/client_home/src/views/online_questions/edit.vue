<template>
	<div class="diy_edit page_online_questions" id="online_questions_edit">
		<div class='warp'>
			<div class='container'>
				<div class='row diy_edit_content_box'>
						<div v-if="$check_field('set','question_no') || $check_field('add','question_no') || $check_field('get','question_no')" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>
								问题编号:
							</span>
						</div>
								<!-- 文本 -->
									<div class="diy_field diy_text">
							<input type="text" id="form_question_no" v-model="form['question_no']" placeholder="请输入问题编号" v-if="(form['question_no'] && $check_field('set','question_no')) || (!form['question_no'] && $check_field('add','question_no'))" :disabled="true"/>
							<span v-else-if="$check_field('get','question_no')">{{ form['${obj.name}'] }}</span>
						</div>
										</div>
							<div v-if="$check_field('set','ask_the_user') || $check_field('add','ask_the_user') || $check_field('get','ask_the_user')" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>
								提问用户:
							</span>
						</div>
						<div class="diy_field diy_down">
							<select id="form_ask_the_user" :disabled="disabledObj['ask_the_user_isDisabled']" v-model="form['ask_the_user']" v-if="(form['ask_the_user'] && $check_field('set','ask_the_user')) || (!form['ask_the_user'] && $check_field('add','ask_the_user'))" >
								<option v-for="o in list_user_ask_the_user" :value="o['user_id']">
									{{o['nickname'] + '-' + o['username']}}
								</option>
							</select>
							<span v-else-if="$check_field('get','ask_the_user')">{{ form['ask_the_user'] }}</span>
						</div>
					</div>
							<div v-if="$check_field('set','user_name') || $check_field('add','user_name') || $check_field('get','user_name')" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>
								用户姓名:
							</span>
						</div>
								<!-- 文本 -->
									<div class="diy_field diy_text">
							<input type="text" id="form_user_name" v-model="form['user_name']" placeholder="请输入用户姓名" v-if="(form['user_name'] && $check_field('set','user_name')) || (!form['user_name'] && $check_field('add','user_name'))"  :disabled="disabledObj['user_name_isDisabled']"/>
							<span v-else-if="$check_field('get','user_name')">{{ form['${obj.name}'] }}</span>
						</div>
										</div>
							<div v-if="$check_field('set','problem_description') || $check_field('add','problem_description') || $check_field('get','problem_description')" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>
								问题描述:
							</span>
						</div>
								<!-- 多文本 -->
						<div class="diy_field diy_desc">
							<textarea id="form_problem_description" v-model="form['problem_description']" v-if="(form['problem_description'] && $check_field('set','problem_description')) || (!form['problem_description'] && $check_field('add','problem_description'))" :disabled="disabledObj['problem_description_isDisabled']" />
							<span v-else-if="$check_field('get','problem_description')">{{ form['${obj.name}'] }}</span>
						</div>
							</div>
							<div v-if="$check_field('set','problem_attachment') || $check_field('add','problem_attachment') || $check_field('get','problem_attachment')" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>
								问题附件:
							</span>
						</div>
								<!-- 文件 -->
						<input type="file" style="display: none;" id="form_file_problem_attachment" title="problem_attachment" @change="change_file($event.target.files,'problem_attachment')"/>
						<!-- 修改权限 -->
						<div class="diy_field diy_img" v-if="form['problem_attachment'] && $check_field('set','problem_attachment')">
							<label for="form_file_problem_attachment">
								<!--<span>{{form['problem_attachment']}} </span>-->
								<a :href="$fullUrl(form['problem_attachment'])" target="_blank" style="color: rgb(64, 158, 255);">点击下载</a>
							</label>
						</div>
						<!-- 添加权限 -->
						<div class="diy_field diy_img" v-else-if="!form['problem_attachment'] && $check_field('add','problem_attachment')">
							<label for="form_file_problem_attachment">
								<div class="btn_add_img">
									<span>+</span>
								</div>
							</label>
						</div>
						<!-- 查询权限 -->
						<div class="diy_field diy_img" v-else-if="$check_field('get','problem_attachment')">
							<span>{{form['problem_attachment']}} </span>
						</div>
							</div>
	


					<div v-if="user_group === '管理员' || $check_examine()" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>
								审核状态:
							</span>
						</div>
						<div class="diy_field diy_select" v-if="$check_action('/online_questions/edit','examine')">
							<!--<span> {{ form['examine_state'] }} </span>-->
							<select v-model="form['examine_state']">
								<option value="未审核">
									未审核
								</option>
								<option value="已通过">
									已通过
								</option>
								<option value="未通过">
									未通过
								</option>
							</select>
						</div>
						<div class="diy_field diy_text" v-else>
							<span>
								{{ form['examine_state'] }}
							</span>
						</div>
					</div>
					<div v-if="user_group === '管理员' || $check_examine()" class="form-item col-12 col-md-6">
						<div class="diy_title">
							<span>
								审核回复:
							</span>
						</div>
						<div class="diy_field diy_desc" v-if="$check_action('/online_questions/edit','examine')">
							<textarea v-model="form['examine_reply']"></textarea>
						</div>
						<div class="diy_field diy_text" v-else>
							<span>
								{{ form['examine_reply'] }}
							</span>
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
				url_get_obj: "~/api/online_questions/get_obj?",
				url_add: "~/api/online_questions/add?",
				url_set: "~/api/online_questions/set?",

				// 登录权限
				oauth: {
					"signIn": true,
					"user_group": []
				},

				// 查询条件
				query: {
						"question_no": "",
							"ask_the_user": 0,
							"user_name": "",
							"problem_description": "",
							"problem_attachment": "",
						"online_questions_id": 0,
				},

				obj: {
						"question_no": this.$get_stamp(), // 问题编号
							"ask_the_user": 0, // 提问用户
							"user_name":  '', // 用户姓名
							"problem_description":  '', // 问题描述
							"problem_attachment":  '', // 问题附件
						"examine_state": "未审核",
					"examine_reply": "",
					"online_questions_id": 0,
				},

				// 表单字段
				form: {
						"question_no": this.$get_stamp(), // 问题编号
							"ask_the_user": 0, // 提问用户
							"user_name":  '', // 用户姓名
							"problem_description":  '', // 问题描述
							"problem_attachment":  '', // 问题附件
						"examine_state": "未审核",
					"examine_reply": "",
					"online_questions_id": 0,
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
							
				// ID字段
				field: "online_questions_id",

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
					async get_user_session_ask_the_user(){
				var _this = this;
				var json = await this.$get("~/api/user_group/get_obj?name=系统用户");
				if(json.result && json.result.obj){
					var source_table = json.result.obj.source_table;
					var user_id = _this.$store.state.user.user_id;
					if (user_id){
						var url = "~/api/"+source_table+"/get_obj?"
						this.$get(url, {"user_id":_this.$store.state.user.user_id}, function(res) {
							if (res.result && res.result.obj) {
								var arr = []
								for (let key in res.result.obj) {
									arr.push(key)
								}
								var arrForm = []
								for (let key in _this.form) {
									arrForm.push(key)
								}
								_this.form["ask_the_user"] = user_id
								_this.disabledObj['ask_the_user' + '_isDisabled'] = true
								for (var i=0;i<arr.length;i++){
                  if (arr[i]!=='examine_state' && arr[i]!=='examine_reply') {
                    for (var j = 0; j < arrForm.length; j++) {
                      if (arr[i] === arrForm[j]) {
                        if (arr[i] !== "ask_the_user") {
                          _this.form[arrForm[j]] = res.result.obj[arr[i]]
                          _this.disabledObj[arrForm[j] + '_isDisabled'] = true
                          break;
                        }
                      }
                    }
                  }
								}
							}
						});
					}
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
				this.$post("~/api/online_questions/upload?", form, (res) => {
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
								this.get_user_session_ask_the_user();
					this.get_list_user_ask_the_user();
												},
	}
</script>

<style>




</style>
