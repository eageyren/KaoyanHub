<template>
  <div class="page_search">
	<div class="warp">
	  <div class="container">
		<div class="row">
		  <div class="col-12">
			<div class="card_result_search">
			  <div class="title">搜索结果</div>


				<!-- 论坛搜索结果 -->
			  <list_result_search
				:list="result_forum"
				title="交流中心"
				source_table="forum"
			  ></list_result_search>

						  <list_result_search
				v-if="$check_action('/system_user/list', 'get')"
				:list="result_system_user_user_name"
				title="系统用户用户姓名"
				source_table="system_user"
			  ></list_result_search>
								  <list_result_search
				v-if="$check_action('/system_user/list', 'get')"
				:list="result_system_user_gender"
				title="系统用户性别"
				source_table="system_user"
			  ></list_result_search>
									  <list_result_search
				v-if="$check_action('/postgraduate_examination_materials/list', 'get')"
				:list="result_postgraduate_examination_materials_data_name"
				title="考研资料资料名称"
				source_table="postgraduate_examination_materials"
			  ></list_result_search>
											  <list_result_search
				v-if="$check_action('/postgraduate_examination_materials/list', 'get')"
				:list="result_postgraduate_examination_materials_data_type"
				title="考研资料资料类型"
				source_table="postgraduate_examination_materials"
			  ></list_result_search>
								  <list_result_search
				v-if="$check_action('/postgraduate_examination_materials/list', 'get')"
				:list="result_postgraduate_examination_materials_knowledge_points"
				title="考研资料知识点"
				source_table="postgraduate_examination_materials"
			  ></list_result_search>
												  <list_result_search
				v-if="$check_action('/data_sharing/list', 'get')"
				:list="result_data_sharing_data_name"
				title="资料分享资料名称"
				source_table="data_sharing"
			  ></list_result_search>
																								  <list_result_search
				v-if="$check_action('/data_type/list', 'get')"
				:list="result_data_type_data_type"
				title="资料类型资料类型"
				source_table="data_type"
			  ></list_result_search>
									  <list_result_search
				v-if="$check_action('/colleges_and_universities/list', 'get')"
				:list="result_colleges_and_universities_name_of_institution"
				title="报考院校院校名称"
				source_table="colleges_and_universities"
			  ></list_result_search>
											  <list_result_search
				v-if="$check_action('/colleges_and_universities/list', 'get')"
				:list="result_colleges_and_universities_college_major"
				title="报考院校院校专业"
				source_table="colleges_and_universities"
			  ></list_result_search>
																					  <list_result_search
				v-if="$check_action('/online_questions/list', 'get')"
				:list="result_online_questions_user_name"
				title="在线提问用户姓名"
				source_table="online_questions"
			  ></list_result_search>
															  <list_result_search
				v-if="$check_action('/online_qa/list', 'get')"
				:list="result_online_qa_question_no"
				title="在线答疑问题编号"
				source_table="online_qa"
			  ></list_result_search>
											  <list_result_search
				v-if="$check_action('/online_qa/list', 'get')"
				:list="result_online_qa_user_name"
				title="在线答疑用户姓名"
				source_table="online_qa"
			  ></list_result_search>
															</div>
		  </div>
		</div>
	  </div>
	</div>
  </div>
</template>

<script>
import mixin from "../../mixins/page.js";
import list_result_search from "../../components/diy/list_result_search.vue";

export default {
  mixins: [mixin],
  data() {
	return {
	  "query": {
		word: "",
	  },
	  "result_forum": [],
						"result_system_user_user_name":[],
								"result_system_user_gender":[],
									"result_postgraduate_examination_materials_data_name":[],
											"result_postgraduate_examination_materials_data_type":[],
								"result_postgraduate_examination_materials_knowledge_points":[],
												"result_data_sharing_data_name":[],
																								"result_data_type_data_type":[],
									"result_colleges_and_universities_name_of_institution":[],
											"result_colleges_and_universities_college_major":[],
																					"result_online_questions_user_name":[],
															"result_online_qa_question_no":[],
											"result_online_qa_user_name":[],
													};
  },
  methods: {
	/**
	 * 获取交流中心
	 */
	get_forum() {
	  this.$get("~/api/forum/get_list?like=0", { page: 1, size: 10, title: this.query.word }, (json) => {
		if (json.result) {
		  this.result_forum = json.result.list;
		}
	  });
	},

				/**
	 * 获取user_name
	 */
	get_system_user_user_name(){
		this.$get("~/api/system_user/get_list?like=0", { page: 1, size: 10, "user_name": this.query.word }, (json) => {
		  if (json.result) {
			var result_system_user_user_name = json.result.list;
			result_system_user_user_name.map(o => o.title = o['user_name'])
	  			this.result_system_user_user_name = result_system_user_user_name
		 	}
		});
	},
						/**
	 * 获取gender
	 */
	get_system_user_gender(){
		this.$get("~/api/system_user/get_list?like=0", { page: 1, size: 10, "gender": this.query.word }, (json) => {
		  if (json.result) {
			var result_system_user_gender = json.result.list;
			result_system_user_gender.map(o => o.title = o['gender'])
	  			this.result_system_user_gender = result_system_user_gender
		 	}
		});
	},
							/**
	 * 获取data_name
	 */
	get_postgraduate_examination_materials_data_name(){
		this.$get("~/api/postgraduate_examination_materials/get_list?like=0", { page: 1, size: 10, "data_name": this.query.word }, (json) => {
		  if (json.result) {
			var result_postgraduate_examination_materials_data_name = json.result.list;
			result_postgraduate_examination_materials_data_name.map(o => o.title = o['data_name'])
	  			this.result_postgraduate_examination_materials_data_name = result_postgraduate_examination_materials_data_name
		 	}
		});
	},
									/**
	 * 获取data_type
	 */
	get_postgraduate_examination_materials_data_type(){
		this.$get("~/api/postgraduate_examination_materials/get_list?like=0", { page: 1, size: 10, "data_type": this.query.word }, (json) => {
		  if (json.result) {
			var result_postgraduate_examination_materials_data_type = json.result.list;
			result_postgraduate_examination_materials_data_type.map(o => o.title = o['data_type'])
	  			this.result_postgraduate_examination_materials_data_type = result_postgraduate_examination_materials_data_type
		 	}
		});
	},
						/**
	 * 获取knowledge_points
	 */
	get_postgraduate_examination_materials_knowledge_points(){
		this.$get("~/api/postgraduate_examination_materials/get_list?like=0", { page: 1, size: 10, "knowledge_points": this.query.word }, (json) => {
		  if (json.result) {
			var result_postgraduate_examination_materials_knowledge_points = json.result.list;
			result_postgraduate_examination_materials_knowledge_points.map(o => o.title = o['knowledge_points'])
	  			this.result_postgraduate_examination_materials_knowledge_points = result_postgraduate_examination_materials_knowledge_points
		 	}
		});
	},
										/**
	 * 获取data_name
	 */
	get_data_sharing_data_name(){
		this.$get("~/api/data_sharing/get_list?like=0", { page: 1, size: 10, "data_name": this.query.word }, (json) => {
		  if (json.result) {
			var result_data_sharing_data_name = json.result.list;
			result_data_sharing_data_name.map(o => o.title = o['data_name'])
	  			this.result_data_sharing_data_name = result_data_sharing_data_name
		 	}
		});
	},
																						/**
	 * 获取data_type
	 */
	get_data_type_data_type(){
		this.$get("~/api/data_type/get_list?like=0", { page: 1, size: 10, "data_type": this.query.word }, (json) => {
		  if (json.result) {
			var result_data_type_data_type = json.result.list;
			result_data_type_data_type.map(o => o.title = o['data_type'])
	  			this.result_data_type_data_type = result_data_type_data_type
		 	}
		});
	},
							/**
	 * 获取name_of_institution
	 */
	get_colleges_and_universities_name_of_institution(){
		this.$get("~/api/colleges_and_universities/get_list?like=0", { page: 1, size: 10, "name_of_institution": this.query.word }, (json) => {
		  if (json.result) {
			var result_colleges_and_universities_name_of_institution = json.result.list;
			result_colleges_and_universities_name_of_institution.map(o => o.title = o['name_of_institution'])
	  			this.result_colleges_and_universities_name_of_institution = result_colleges_and_universities_name_of_institution
		 	}
		});
	},
									/**
	 * 获取college_major
	 */
	get_colleges_and_universities_college_major(){
		this.$get("~/api/colleges_and_universities/get_list?like=0", { page: 1, size: 10, "college_major": this.query.word }, (json) => {
		  if (json.result) {
			var result_colleges_and_universities_college_major = json.result.list;
			result_colleges_and_universities_college_major.map(o => o.title = o['college_major'])
	  			this.result_colleges_and_universities_college_major = result_colleges_and_universities_college_major
		 	}
		});
	},
																			/**
	 * 获取user_name
	 */
	get_online_questions_user_name(){
		this.$get("~/api/online_questions/get_list?like=0", { page: 1, size: 10, "user_name": this.query.word }, (json) => {
		  if (json.result) {
			var result_online_questions_user_name = json.result.list;
			result_online_questions_user_name.map(o => o.title = o['user_name'])
	  			this.result_online_questions_user_name = result_online_questions_user_name
		 	}
		});
	},
													/**
	 * 获取question_no
	 */
	get_online_qa_question_no(){
		this.$get("~/api/online_qa/get_list?like=0", { page: 1, size: 10, "question_no": this.query.word }, (json) => {
		  if (json.result) {
			var result_online_qa_question_no = json.result.list;
			result_online_qa_question_no.map(o => o.title = o['question_no'])
	  			this.result_online_qa_question_no = result_online_qa_question_no
		 	}
		});
	},
									/**
	 * 获取user_name
	 */
	get_online_qa_user_name(){
		this.$get("~/api/online_qa/get_list?like=0", { page: 1, size: 10, "user_name": this.query.word }, (json) => {
		  if (json.result) {
			var result_online_qa_user_name = json.result.list;
			result_online_qa_user_name.map(o => o.title = o['user_name'])
	  			this.result_online_qa_user_name = result_online_qa_user_name
		 	}
		});
	},
												
  },
  components: { list_result_search },
	created(){
    this.query.word = this.$route.query.word || "";
  },
  mounted() {
	this.get_forum();
					this.get_system_user_user_name();
							this.get_system_user_gender();
								this.get_postgraduate_examination_materials_data_name();
										this.get_postgraduate_examination_materials_data_type();
							this.get_postgraduate_examination_materials_knowledge_points();
											this.get_data_sharing_data_name();
																							this.get_data_type_data_type();
								this.get_colleges_and_universities_name_of_institution();
										this.get_colleges_and_universities_college_major();
																				this.get_online_questions_user_name();
														this.get_online_qa_question_no();
										this.get_online_qa_user_name();
												  },
  watch: {
	$route() {
	  $.push(this.query, this.$route.query);
	  this.get_forum();
				  this.get_system_user_user_name();
						  this.get_system_user_gender();
							  this.get_postgraduate_examination_materials_data_name();
									  this.get_postgraduate_examination_materials_data_type();
						  this.get_postgraduate_examination_materials_knowledge_points();
										  this.get_data_sharing_data_name();
																						  this.get_data_type_data_type();
							  this.get_colleges_and_universities_name_of_institution();
									  this.get_colleges_and_universities_college_major();
																			  this.get_online_questions_user_name();
													  this.get_online_qa_question_no();
									  this.get_online_qa_user_name();
													},
  },
};
</script>

<style scoped>
.card_search {
  text-align: center;
}
.card_result_search>.title {
  text-align: center;
  padding: 10px 0;
}
</style>
