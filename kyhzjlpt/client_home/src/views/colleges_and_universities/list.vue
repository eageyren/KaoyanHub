<template>
	<div class="diy_list page_colleges_and_universities" id="colleges_and_universities_list">
		<div class="warp">
			<div class="container diy_list_container">
				<div class="diy_list_title">
					<div class="col">
						<span class="title">报考院校列表</span>
					</div>
				</div>
				<div class="row diy_list_search">
					<div class="col">
						<!-- 搜索栏 -->
						<div class="view">
							<span class="diy_list_search_title">关键字搜索：</span>
								<b-form-input size="sm" class="mr-sm-2" placeholder="院校名称搜索" v-model="query['name_of_institution']" />
										<b-form-input size="sm" class="mr-sm-2" placeholder="院校专业搜索" v-model="query['college_major']" />
										<b-button size="sm" @click="search()" >
								<b-icon icon="search"/>
							</b-button>
						</div>
						<!-- /搜索栏 -->
					</div>
				</div>
				<div class="diy_list_select_box">
					<span class="diy_list_select_title">下拉搜索：</span>
						<div class="diy_list_dropdown_box">
						<div class="col">
							<!-- 筛选 -->
							<div class="view">
																		</div>
							<!-- /筛选 -->
						</div>
					</div>
					<div class="diy_list_sort_box">
						<div class="col">
							<!-- 排序 -->
							<div class="view">
								<b-dropdown text="排序" variant="outline-dark" left>
										<b-dropdown-item v-for="(o, i) in list_sort" :key="i" @click="set_sort(o)" >
												{{ o.name }}
										</b-dropdown-item>
								</b-dropdown>
							</div>
							<!--/排序 -->
						</div>
					</div>
				</div>

				<div class="row diy_list_box">
					<div class="col">
						<!-- 列表 -->
						<list_colleges_and_universities :list="list" />
						<!-- /列表 -->
					</div>
				</div>
				<div class="row diy_list_page_box">
					<div class="col overflow-auto flex_cc">
						<!-- 分页器 -->
<!--						<diy_pager v-model="query['page']" :size="query['size']" :count="count" v-on:toPage="toPage" v-on:toSize="toSize" ></diy_pager>-->
            <b-pagination
                v-model="query.page"
                :total-rows="count"
                :per-page="query.size"
                @change="goToPage"
            />
						<!-- /分页器 -->
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import list_colleges_and_universities from "@/components/diy/list_colleges_and_universities.vue";
	import diy_pager from "@/components/diy/diy_pager";
	import mixin from "@/mixins/page.js";

	export default {
		mixins: [mixin],
		components: {
			diy_pager,
			list_colleges_and_universities
		},
		data() {
			return {
				url_get_list: "~/api/colleges_and_universities/get_list?like=0",

				// 查询条件
				query: {
					keyword: "",
					page: 1,
					size: 10,
								"name_of_institution": "", // 院校名称
												"college_major": "", // 院校专业
									},

				// 排序内容
				list_sort: [{
						name: "创建时间从高到低",
						value: "create_time desc",
					},
					{
						name: "创建时间从低到高",
						value: "create_time asc",
					},
					{
						name: "更新时间从高到低",
						value: "update_time desc",
					},
					{
						name: "更新时间从低到高",
						value: "update_time asc",
					},
						{
						name: "院校名称正序",
						value: "name_of_institution asc",
					},
					{
						name: "院校名称倒序",
						value: "name_of_institution desc",
					},
								{
						name: "院校专业正序",
						value: "college_major asc",
					},
					{
						name: "院校专业倒序",
						value: "college_major desc",
					},
							],

										
			}
		},
		methods: {
      get_list_before(param) {
      },
			/**
			 * 筛选选择
			 */
			filter_set(o,key) {
			    if (o == "全部") {
			        this.query[key] = "";
			    } else {
			        this.query[key] = o;
			    }
			    this.search();
			},

			/**
			 * 排序
			 */
			set_sort(o) {
			    this.query.orderby = o.value;
			    this.search();
			},


					
			/**
			 * 筛选
			 */
																		/**
			 * 重置
			 */
			reset() {
							this.query.name_of_institution = ""
											this.query.college_major = ""
									this.search();
			},

			// 返回条数
			toSize(i){
				this.query.size = i;
				this.first();
			},

			// 返回页数
			toPage(i){
				this.query.page = i;
				this.first();
			},

      goToPage(v){
        this.query.page = v;
        this.goToNew(v)
      },

		},
		computed: {
		},
		created() {
												}
	}
</script>

<style>
</style>
