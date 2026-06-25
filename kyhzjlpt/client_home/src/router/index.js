import Vue from 'vue';
import VueRouter from 'vue-router';
import index from '../views/index.vue'
import login from '../views/account/login.vue';

Vue.use(VueRouter)

const routes = [
	// 主页ss
	{
		path: '/',
		name: 'index',
		component: index
	},
	// 登录
	{
		path: '/account/login',
		name: 'login',
		component: login
	},
	// 忘记密码
	{
		path: '/account/forgot',
		name: 'forgot',
		component: () => import('../views/account/forgot.vue')
	},
	// 注册账号
	{
		path: '/account/register',
		name: 'register',
		component: () => import('../views/account/register.vue')
	},
	// 媒体图片
	{
		path: '/media/image',
		name: 'media_image',
		component: () => import('../views/media/image.vue')
	},
	// 音乐
	{
		path: '/media/music',
		name: 'media_music',
		component: () => import('../views/media/music.vue')
	},
	// 媒体视频
	{
		path: '/media/video',
		name: 'media_video',
		component: () => import('../views/media/video.vue')
	},
	// 浏览网站
	// 收藏路由
	{
		path: '/user/collect',
		name: 'collect_list',
		component: () => import('../views/user/collect.vue')
	},

	// 论坛路由
	{
		path: '/forum/list',
		name: 'forum_list',
		component: () => import('../views/forum/list.vue')
	},

	{
		path: '/forum/details',
		name: 'forum_details',
		component: () => import('../views/forum/details.vue')
	},

	{
		path: '/forum/view',
		name: 'forum_view',
		component: () => import('../views/forum/view.vue')
	},

	// 考试路由
	{
		path: '/exam/list',
		name: 'exam_list',
		component: () => import('../views/exam/list.vue')
	},

	{
		path: '/exam/details',
		name: 'exam_details',
		component: () => import('../views/exam/details.vue')
	},




	// 公告路由
	{
		path: '/notice/list',
		name: 'notice_list',
		component: () => import('../views/notice/list.vue')
	},
	{
		path: '/notice/details',
		name: 'notice_details',
		component: () => import('../views/notice/details.vue')
	},

	
	
		
		// 考研资料列表路由
	{
		path: '/postgraduate_examination_materials/list',
		name: '/postgraduate_examination_materials_list',
		component: () => import('../views/postgraduate_examination_materials/list.vue')
	},
	
		// 考研资料详情路由
	{
		path: '/postgraduate_examination_materials/details',
		name: '/postgraduate_examination_materials_details',
		component: () => import('../views/postgraduate_examination_materials/details.vue')
	},
			// 资料分享添加路由
	{
		path: '/data_sharing/edit',
		name: '/data_sharing_edit',
		component: () => import('../views/data_sharing/edit.vue')
	},
	
	
		
	
		
		// 报考院校列表路由
	{
		path: '/colleges_and_universities/list',
		name: '/colleges_and_universities_list',
		component: () => import('../views/colleges_and_universities/list.vue')
	},
	
		// 报考院校详情路由
	{
		path: '/colleges_and_universities/details',
		name: '/colleges_and_universities_details',
		component: () => import('../views/colleges_and_universities/details.vue')
	},
			// 在线提问添加路由
	{
		path: '/online_questions/edit',
		name: '/online_questions_edit',
		component: () => import('../views/online_questions/edit.vue')
	},
	
	
		
	
	
	// 用户路由
	{
		path: '/user/index',
		name: 'user_index',
		component: () => import('../views/user/index.vue')
	},
	// 基本信息
	{
		path: '/user/info',
		name: 'user_info',
		component: () => import('../views/user/info.vue')
	},
	// 找回密码
	{
		path: '/user/password',
		name: 'user_password',
		component: () => import('../views/user/password.vue')
	},

	// 搜索
	{
		path: '/search',
		name: 'search',
		component: () => import('../views/search/index.vue')
	},
	// 局部搜索
	{
		path: '/search/details',
		name: 'search_details',
		component: () => import('../views/search/details.vue')
	}
]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})

router.afterEach((to, from, next) => {
	let title = "考研互助交流平台-home";
	document.title = title;
	document.logo = "考研互助交流平台"
})

export default router
