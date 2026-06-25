DROP TABLE IF EXISTS `slides`;
CREATE TABLE `slides` (
  `slides_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '轮播图ID：',
  `title` varchar(64) DEFAULT NULL COMMENT '标题：',
  `content` varchar(255) DEFAULT NULL COMMENT '内容：',
  `url` varchar(255) DEFAULT NULL COMMENT '链接：',
  `img` varchar(255) DEFAULT NULL COMMENT '轮播图：',
  `hits` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '点击量：',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间：',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间：',
  PRIMARY KEY (`slides_id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='轮播图：';
DROP TABLE IF EXISTS `auth`;
CREATE TABLE `auth` (
  `auth_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '授权ID：',
  `user_group` varchar(64) DEFAULT NULL COMMENT '用户组：',
  `mod_name` varchar(64) DEFAULT NULL COMMENT '模块名：',
  `table_name` varchar(64) DEFAULT NULL COMMENT '表名：',
  `page_title` varchar(255) DEFAULT NULL COMMENT '页面标题：',
  `path` varchar(255) DEFAULT NULL COMMENT '路由路径：',
  `position` varchar(32) DEFAULT NULL COMMENT '位置：',
  `mode` varchar(32) NOT NULL DEFAULT '_blank' COMMENT '跳转方式：',
  `add` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可增加：',
  `del` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可删除：',
  `set` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可修改：',
  `get` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否可查看：',
  `field_add` varchar(500) DEFAULT NULL COMMENT '添加字段：',
  `field_set` varchar(500) DEFAULT NULL COMMENT '修改字段：',
  `field_get` varchar(500) DEFAULT NULL COMMENT '查询字段：',
  `table_nav_name` varchar(500) DEFAULT NULL COMMENT '跨表导航名称：',
  `table_nav` varchar(500) DEFAULT NULL COMMENT '跨表导航：',
  `option` text COMMENT '配置：',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间：',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间：',
  PRIMARY KEY (`auth_id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='定制授权';
DROP TABLE IF EXISTS `upload`;
CREATE TABLE `upload` (
  `upload_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '上传ID',
  `name` varchar(64) DEFAULT NULL COMMENT '文件名',
  `path` varchar(255) DEFAULT NULL COMMENT '访问路径',
  `file` varchar(255) DEFAULT NULL COMMENT '文件路径',
  `display` varchar(255) DEFAULT NULL COMMENT '显示顺序',
  `father_id` int(11) DEFAULT '0' COMMENT '父级ID',
  `dir` varchar(255) DEFAULT NULL COMMENT '文件夹',
  `type` varchar(32) DEFAULT NULL COMMENT '文件类型',
  PRIMARY KEY (`upload_id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
insert into `upload` values ('1','movie.mp4','/upload/movie.mp4','',null,'0',null,'video');
DROP TABLE IF EXISTS `forum_type`;
CREATE TABLE `forum_type` (
  `type_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '分类ID：[0,10000]',
  `name` varchar(16) NOT NULL DEFAULT '' COMMENT '分类名称：[2,16]',
  `description` varchar(255) DEFAULT NULL COMMENT '描述：[0,255]描述该分类的作用',
  `url` varchar(255) DEFAULT NULL COMMENT '外链地址：[0,255]如果该分类是跳转到其他网站的情况下，就在该URL上设置',
  `father_id` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '上级分类ID：[0,32767]',
  `icon` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '分类图标：',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间：',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间：',
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='论坛频道：用于汇总浏览论坛，在不同频道下展示不同论坛。';
insert into `forum_type` values ('1','分类一','分类一','/article/list?type_id=1','0',null,'2022-12-01 22:56:32.0','2022-12-31 15:17:02.0');
insert into `forum_type` values ('2','分类二','分类二','/article/list?type_id=2','0',null,'2022-12-01 22:56:32.0','2022-12-31 15:17:08.0');
insert into `forum_type` values ('3','分类三','分类三','/article/list?type_id=3','0',null,'2022-12-01 22:56:32.0','2022-12-31 15:17:19.0');
DROP TABLE IF EXISTS `exam_question`;
CREATE TABLE `exam_question` (
  `exam_question_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '类型',
  `title` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '题目',
  `question_item` varchar(500) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '选项',
  `answer` varchar(500) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '参考答案',
  `score` double(8,2) DEFAULT NULL COMMENT '总分',
  `question_order` int(11) DEFAULT NULL COMMENT '排序',
  `exam_id` mediumint(8) DEFAULT NULL COMMENT '所属试卷',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间：',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间：',
  PRIMARY KEY (`exam_question_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;
insert into `exam_question` values ('1','单选题','启动Word后，空白文档的文档名为（　　）。','A、untitled
B、文档1.DOC
C、新文档.DOC
D、我的文档.DOC','C、新文档.DOC','10.0','1','1','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('2','单选题','Word软件处理的主要对象是（　　）。','A、表格
B、文档
C、图片
D、数据','B、文档','10.0','2','1','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('3','单选题','当前活动窗口是文档dl.doc的窗口，单击该窗口的“最小化”按钮（　　）。','A、不显示dl.doc文档内容，但dl.doc文档并未关闭
B、dl.doc文档未关闭，且继续显示其内容
C、该窗口和dl.doc文档都被关闭
D、关闭了dl.doc文档但该窗口并未关闭','A、不显示dl.doc文档内容，但dl.doc文档并未关闭','10.0','3','1','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('4','多选题','文件或文件夹的属性一般有（　　）。','A、只读
B、隐藏
C、存档
D、系统','A、只读|B、隐藏','10.0','4','1','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('5','多选题','电子计算机根据外观可以分为（　　）。','A、巨型机
B、便携机
C、微型机
D、游戏机','A、巨型机|B、便携机|C、微型机','10.0','5','1','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('6','判断题','Word和Windows都是系统软件。','A、正确
B、错误','B、错误','5.0','6','1','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('7','判断题','ROM的是只读存储器。','A、正确
B、错误','A、正确','5.0','7','1','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('8','判断题','在存储器容量的表示中,1MB的含义是1024K个汉字。','A、正确
B、错误','B、错误','5.0','8','1','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('9','填空题','在Word文档中，每个段落都有自己的段落标记，段落标记的位置在______。','','段落的结尾处','10.0','9','1','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('10','填空题','能显示页眉和页脚的方式是______。','','页面视图','10.0','10','1','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('11','主观题','请简述一下计算机的工作原理？','','计算机最主要的工作原理是存储程序与程序控制','15.0','11','1','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('18','单选题','下列诗歌属于乐府旧题的有( )','A、《短歌行》
B、《饮酒》
C、《蛇》','A、《短歌行》','5.0','1','2','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('19','单选题','提倡“文章合为时而著，歌诗合为事而作”的唐代诗人是( )','A、韩愈
B、柳宗元
C、白居易','C、白居易','5.0','2','2','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('20','单选题','“甘其食，美其服，乐其俗”中的“甘”“美”“乐”属( )','A、使动词
B、意动词
C、名词','B、意动词','5.0','3','2','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('21','单选题','“功施到今”中“施”读作( )','A、yi
B、si
C、shi','A、yi','5.0','4','2','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('22','单选题','“回眸一笑百媚生，六宫粉黛无颜色”的作者是(  )','A、陆游
B、唐琬
C、白居易','C、白居易','5.0','5','2','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('23','多选题','《采薇》一诗的艺术特点是( )','A、反复咏叹
B、抒情和写景融为一体
C、起兴手法','A、反复咏叹|B、抒情和写景融为一体|C、起兴手法','10.0','6','2','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('24','多选题','“回眸一笑百媚生，六宫粉黛无颜色”运用的修辞手法有( )','A、夸张
B、对比
C、借代','A、夸张|B、对比|C、借代','10.0','7','2','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('25','多选题','不是《西厢记.长亭送别》一折戏的主角是( )','A、崔莺莺
B、张珙
C、红娘','C、红娘','10.0','8','2','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('26','填空题','《铸剑》属于____。','','小说','5.0','9','2','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('27','填空题','“对酒当歌，人生几何?”的作者是____。','','曹操','5.0','10','2','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('28','填空题','《诗经》中的“风”是____。','','国风','5.0','11','2','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('29','填空题','《采薇》是选自《诗经》的____。','','《小雅》','5.0','12','2','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('30','判断题','王维的《送梓州李使君》是一首赠别诗','A、正确
B、错误','A、正确','5.0','13','2','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('31','判断题','《乡愁》属于余秋雨的作品','A、正确
B、错误','B、错误','5.0','14','2','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('32','判断题','《雅舍》属于梁实秋的作品','A、正确
B、错误','A、正确','5.0','15','2','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('33','主观题','默写《短歌行》','','对酒当歌，人生几何！ 譬如朝露，去日苦多。 慨当以慷，忧思难忘。 何以解忧？唯有杜康。 青青子衿，悠悠我心。 但为君故，沉吟至今。 呦呦鹿鸣，食野之苹。 我有嘉宾，鼓瑟吹笙。 明明如月，何时可掇？ 忧从中来，不可断绝。 越陌度阡，枉用相存。 契阔谈讌，心念旧恩。 月明星稀，乌鹊南飞。 绕树三匝，何枝可依？ 山不厌高，海不厌深。 周公吐哺，天下归心。','15.0','16','2','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('34','主观题','分析王维的《送梓州李使君》是在什么时代背景下抒写的？体现了当时的社会环境是如何的？','','这是一首送别之作。具体创作时间不详，李使君当是初往梓州（治今四川三台）赴任，王维写此诗相赠。','15.0','17','2','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('35','单选题','以下选项中，不能作为合法常量的是______。','A、1.234e04
B、1.234e0.4
C、1.234e+4
D、1.234e0','B、1.234e0.4','5.0','1','5','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('36','单选题','以下符合C语言语法的实型常量是______。','A、1.2E0.5
B、3.14159E
C、.5E-3
D、E15','C、.5E-3','5.0','2','5','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('37','单选题','设int a=12，则执行完语句a+=a-=a*a后，a的值是______。','A、552
B、264
C、144
D、-264','D、-264','5.0','3','5','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('38','单选题','若要求在if后一对圆括号中表示a不等于0的关系,则能正确表示这一关系的表达式为______','A、a<>0
B、!a
C、a=0
D、a','D、a','5.0','4','5','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('39','单选题','已知有double型变量x=2.5,y=4.7,整型变量a=7,  则表达式 x+a%3*(int)(x+y)%2/4 的值是_______','A、2.4
B、2.5
C、2.75
D、0','B、2.5','5.0','5','5','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('40','判断题','一个C语言的执行是从本程序的第一个函数开始,到本程序的最后一个函数结束','A、正确
B、错误','B、错误','5.0','6','5','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('41','判断题','为了避免嵌套的if-else语句的二义性，C语言规定else总是与在其之前未配对的if组成配对关系.','A、正确
B、错误','B、错误','5.0','7','5','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('42','判断题','设x 、y 、z 、t均为int型变量,则执行以下语句后,t的值为1.  x=y=z=1;  t=++x || ++y && ++z;','A、正确
B、错误','A、正确','5.0','8','5','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('43','判断题','下面程序段的输出结果是输出错误信息.  x=3;  do { y=x--;  if (!y) {printf("*");continue;}  printf("#");  } while(x=2);','A、正确
B、错误','B、错误','5.0','9','5','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('44','判断题','下面程序段的运行结果是123.  int n=0;  while (n++<=2)  printf("%d",n);','A、正确
B、错误','A、正确','5.0','10','5','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('45','主观题','下面程序的运行结果是_______.  #include<stdio.h>  void main( )  { int a,b;  a=-1;  b=0;  do {  ++a;  ++a;  b+=a;  } while(a<9);  printf("%d
",b);  }','','25','8.0','11','5','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('46','主观题','以下函数的功能是：求x的y次方，请填空._______  #include<stdio.h>  void main()  { int i,x,y;  double z;  scanf("%d %d",&x,&y);  for(i=1,z=x;i<y;i++)  z=z*______ ;  printf("x^y=%e
",z);  }','','x','8.0','12','5','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('47','主观题','下面程序的输出结果是_____.  #include<stdio.h>  void main( )  { int i;  for(i=1;i<6;i++)  { if (i%2!=0) {printf("#");continue;}  printf("*");  }  printf("
");  }','','#*#*#','8.0','13','5','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('48','主观题','有如下程序  #include<stdio.h>  void main( )  { int n=9;  while(n>6) {n--;printf("%d",n);}  }  该程序段的输出结果是_____.','','876','8.0','14','5','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('49','主观题','阅读以下程序，程序运行后的输出结果是____.  #include<stdio.h>  void main( )  { int x;  for(x=5;x>0;x--)  if (x--<5) printf("%d,",x);  else printf("%d,",x++); }','','4,3,1','8.0','15','5','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam_question` values ('50','主观题','#include <stdio.h>  void main()  { int a[4][4]={{1,3,5,},{2,4,6},{3,5,7}};  printf("%d%d%d%d
",a[0][3],a[1][2],a[2][1],a[3][0]);  }，程序的输出结果是？','','0650','10.0','16','5','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `notice_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '公告id：',
  `title` varchar(125) NOT NULL DEFAULT '' COMMENT '标题：',
  `content` longtext COMMENT '正文：',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间：',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间：',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='公告：';
insert into `notice` values ('1','网站公告','<p>公告，是指政府、团体对重大事件当众正式公布或者公开宣告，宣布。国务院2012年4月16日发布、2012年7月1日起施行的《党政机关公文处理工作条例》，对公告的使用表述为：“适用于向国内外宣布重要事项或者法定事项”。其中包含两方面的内容：一是向国内外宣布重要事项，公布依据政策、法令采取的重大行动等；二是向国内外宣布法定事项，公布依据法律规定告知国内外的有关重要规定和重大行动等。</p>','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `notice` values ('2','关于我们','<p>       一个网站要取得成功，要有先进的理念、先进的思想，更为重要的是抢占先机，及时行动。网络世界可谓一日千里、 日新月异，一个网站只有把握先机，抓住机遇，才</p><p>可能有更多的机会获得成功，可能处于网络行业发展的致高点，可能创建出成功的网站，才能能获得成功。要知道一种网站新模式在网络上只有保持几天的优势,因为人们很容易“COPY" 和模仿，因此，唯有不</p><p>断创新，不失时机地推出新的服务、新的模式、新的思想，网站才可能长久立于不败之地。</p>','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `notice` values ('3','联系方式','<h3>网站内容及品牌合作</h3><p>Email：xxxx@qq.com</p><h3>商务合作</h3><p>电话：010-xxxxxxx</p><p>Email：xxxx@qq.com</p><h3><br></h3><h3><br></h3><p><br></p>','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `notice` values ('4','网站介绍','<p>此处可上传文字、图片、视频、超链接、表格等内容区</p>','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group` (
  `group_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户组ID：[0,8388607]',
  `display` smallint(4) unsigned NOT NULL DEFAULT '100' COMMENT '显示顺序：[0,1000]',
  `name` varchar(16) NOT NULL DEFAULT '' COMMENT '名称：[0,16]',
  `description` varchar(255) DEFAULT NULL COMMENT '描述：[0,255]描述该用户组的特点或权限范围',
  `source_table` varchar(255) DEFAULT NULL COMMENT '来源表：',
  `source_field` varchar(255) DEFAULT NULL COMMENT '来源字段：',
  `source_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '来源ID：',
  `register` smallint(1) unsigned DEFAULT '0' COMMENT '注册位置:',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间：',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间：',
  PRIMARY KEY (`group_id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户组：用于用户前端身份和鉴权';
DROP TABLE IF EXISTS `praise`;
CREATE TABLE `praise` (
  `praise_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '点赞ID：',
  `user_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '点赞人：',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间：',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间：',
  `source_table` varchar(255) DEFAULT NULL COMMENT '来源表：',
  `source_field` varchar(255) DEFAULT NULL COMMENT '来源字段：',
  `source_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '来源ID：',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '点赞状态:1为点赞，0已取消',
  PRIMARY KEY (`praise_id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='点赞：';
DROP TABLE IF EXISTS `access_token`;
CREATE TABLE `access_token` (
  `token_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '临时访问牌ID',
  `token` varchar(64) DEFAULT NULL COMMENT '临时访问牌',
  `info` text,
  `maxage` int(2) NOT NULL DEFAULT '2' COMMENT '最大寿命：默认2小时',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间：',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间：',
  `user_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '用户编号:',
  PRIMARY KEY (`token_id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='临时访问牌';
insert into `access_token` values ('57','5accf85cb6a7f06f0aa2968deadaec1b',null,'2','2022-12-12 18:32:09.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('58','46ff1d4d07714f046ba07b34bffe0af9',null,'2','2022-12-12 18:32:31.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('59','ed9d6cba9826fda1beafcd9326be7a86',null,'2','2022-12-12 18:32:36.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('60','c99763c1833ea0785d9e2b81da3fd28f',null,'2','2022-12-12 18:36:46.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('61','33fbfaccd6d1cb9143e4129bd919d4b0',null,'2','2022-12-12 18:38:05.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('62','493e13da5f293ba67a56a0fe3e1fa6cf',null,'2','2022-12-12 18:44:44.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('63','c4b48e9e2160db09c703041a8fee0a1f',null,'2','2022-12-12 18:55:04.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('64','d13cdaefd3823c360c959a02a262f71d',null,'2','2022-12-12 19:22:32.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('65','6c6ff426fd77ea5a2025ce5ed2e42c8a',null,'2','2022-12-12 19:28:29.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('66','80930065a61ffcdd5cbb75f60932973c',null,'2','2022-12-12 19:41:41.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('67','94114763cf2e3b020495d8a27096d4ef',null,'2','2022-12-12 19:43:13.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('68','761052c551c97c9317bc3aa475c85b84',null,'2','2022-12-12 19:43:26.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('69','7c44ef14131a0ba7c16aa16cef104065',null,'2','2022-12-12 20:24:46.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('70','96380f3d9542c80d04bdade1cf7635a5',null,'2','2022-12-12 21:08:06.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('71','bfdc7acfcbf5763fda81945b60961222',null,'2','2022-12-12 21:08:34.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('72','170a598e51ae8ae2badde20a42fe171d',null,'2','2022-12-12 21:25:42.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('73','c82c357488c75926a92d8a9608d4b367',null,'2','2022-12-12 21:28:52.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('74','4d35290c023f407a820f37dbbb1ceb09',null,'2','2022-12-12 21:44:12.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('75','8d19162776682b695c0f62f3c7a92fec',null,'2','2022-12-12 21:44:38.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('76','a7ea2cdc9a2be179e19200e593ad5a69',null,'2','2022-12-12 21:47:50.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('77','c79a554f9832adc01f19682c5d576bc4',null,'2','2022-12-12 21:49:06.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('78','1c7d95001fa09951a679841c8100ad1f',null,'2','2022-12-12 21:51:22.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('79','776da1bcdd01ddb3cbf0a37fa13fc5b0',null,'2','2022-12-12 21:52:07.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('80','d336e88e57c329d0166931292c1fac41',null,'2','2022-12-12 21:53:34.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('81','37a40f526a6c82fc6110b512802d35bf',null,'2','2022-12-12 22:03:13.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('82','691ad331771f4109206d58aeee572371',null,'2','2022-12-12 22:06:22.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('83','9942e458886219960d3344b4a6a6fbec',null,'2','2022-12-12 23:09:17.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('84','e9939a8b7ccf9f548f0bbb5664981f96',null,'2','2022-12-12 23:11:18.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('85','f5b27912060d1909bef61fab9d96faae',null,'2','2022-12-12 23:20:40.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('86','7c5888682f1d449eb1b62f0054a79fbf',null,'2','2022-12-12 23:28:01.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('87','00dfdc6ac21c4a9da80fd71c990764d1',null,'2','2022-12-13 12:54:06.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('88','3cce592bc72840ab932ce96d85a194da',null,'2','2022-12-13 12:56:39.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('89','43fdaa989a644ad683ef4b4d488e8629',null,'2','2022-12-13 13:31:25.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('90','d6a3cecadacff0dbd6b43b25372cc2a2',null,'2','2022-12-13 20:29:58.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('91','5570bc5b07b3589f4ef8553bd46eb0d1',null,'2','2022-12-13 20:30:36.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('92','5570bc5b07b3589f4ef8553bd46eb0d1',null,'2','2022-12-13 20:30:36.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('93','26c553bd2ee2ab6605d18dfd310d85f9',null,'2','2022-12-13 20:30:54.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('94','3fd52f81236ed2c37ff91a6696d4e47a',null,'2','2022-12-13 20:32:53.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('95','893332e9ee67d60d8312b3700c58a359',null,'2','2022-12-13 20:57:29.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('96','b7844068ade535b2e517df4a40948703',null,'2','2022-12-13 21:01:03.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('97','179b37a5e1893c3af6b946bd5a1c8625',null,'2','2022-12-13 21:01:37.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('98','3a47b8a040a83ebbc9194cb255dc668c',null,'2','2022-12-16 14:30:56.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('99','afa60196afb77dcc2b520ed13a817560',null,'2','2022-12-16 14:31:03.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('100','7fc6d9b324f8c0a3a1784d04ef132692',null,'2','2022-12-16 17:54:10.0','2022-12-20 11:45:30.0','1');
insert into `access_token` values ('101','84e31b291f2bde6b7ceb27af5fe8eee3',null,'2','2022-12-30 15:22:34.0','2022-12-31 11:46:03.0','1');
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam` (
  `exam_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '考试id',
  `name` varchar(32) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '考试名称：[2,32]',
  `duration` int(11) DEFAULT NULL COMMENT '答题时长',
  `score` double(8,2) DEFAULT NULL COMMENT '总分',
  `status` varchar(10) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '状态：启用、禁用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间：',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间：',
  PRIMARY KEY (`exam_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC COMMENT='考试';
insert into `exam` values ('1','计算机基础考试','60','100.0','启用','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam` values ('2','大学语文','90','120.0','启用','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
insert into `exam` values ('3','C语言考试','70','100.0','禁用','2022-12-29 20:44:27.0','2022-12-29 20:44:27.0');
DROP TABLE IF EXISTS `forum`;
CREATE TABLE `forum` (
  `forum_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '论坛id',
  `display` smallint(5) unsigned NOT NULL DEFAULT '100' COMMENT '排序',
  `user_id` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '用户ID',
  `nickname` varchar(16) DEFAULT '' COMMENT '昵称：[0,16]',
  `praise_len` int(10) DEFAULT '0' COMMENT '点赞数',
  `hits` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '访问数',
  `title` varchar(125) NOT NULL DEFAULT '' COMMENT '标题',
  `keywords` varchar(125) DEFAULT NULL COMMENT '关键词',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `url` varchar(255) DEFAULT NULL COMMENT '来源地址',
  `tag` varchar(255) DEFAULT NULL COMMENT '标签',
  `img` text COMMENT '封面图',
  `content` longtext COMMENT '正文',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间：',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间：',
  `avatar` varchar(255) DEFAULT NULL COMMENT '发帖人头像：',
  `type` varchar(64) CHARACTER SET utf8mb4 NOT NULL DEFAULT '0' COMMENT '论坛分类：[0,1000]用来搜索指定类型的论坛帖',
  PRIMARY KEY (`forum_id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='论坛：';
insert into `forum` values ('1','100','1','小明','1','150','测试标题','关键字1','描述','#','标签','/upload/forum_1.jpg','<h1>fafgwagbagbwgwag</h1>','2022-12-17 20:45:39.0','2022-12-04 16:15:53.0','http://localhost:5000/upload/jingdian (11)_15.jpg','分类二');
insert into `forum` values ('2','100','2','小明','0','30','测试标题2','关键字2','dec','#','标签','/upload/forum_2.jpg','<p>测试文章内容2</p>','2022-12-17 20:45:39.0','2022-12-04 16:17:33.0','','分类一');
insert into `forum` values ('3','100','2','小红','0','42','测试标题3','关键字3','dec2','#','标签','/upload/forum_3.jpg','<p>测试文章内容3</p>','2022-12-17 20:45:39.0','2022-12-04 16:17:36.0','','分类二');
insert into `forum` values ('4','100','2','小红','0','22','测试标题4','关键字4','dec3','#','标签','/upload/forum_4.jpg','<p>测试文章内容4</p>','2022-12-17 20:45:39.0','2022-12-04 16:17:38.0','','分类三');
DROP TABLE IF EXISTS `hits`;
CREATE TABLE `hits` (
  `hits_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '点赞ID：',
  `user_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '点赞人：',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间：',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间：',
  `source_table` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '来源表：',
  `source_field` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '来源字段：',
  `source_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '来源ID：',
  PRIMARY KEY (`hits_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;
DROP TABLE IF EXISTS `user_answer`;
CREATE TABLE `user_answer` (
  `user_answer_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` mediumint(8) NOT NULL COMMENT '用户ID：[0,8388607]用户获取其他与用户相关的数据',
  `exam_id` mediumint(8) NOT NULL DEFAULT '0' COMMENT '考试id',
  `score` double(8,2) DEFAULT '0.00' COMMENT '分数',
  `answers` text CHARACTER SET utf8mb4 COMMENT '答案',
  `score_detail` text CHARACTER SET utf8mb4 COMMENT '评分详情',
  `objective_score` double(8,2) DEFAULT '0.00' COMMENT '客观题得分',
  `subjective_score` double(8,2) DEFAULT '0.00' COMMENT '主观题得分',
  `score_state` tinyint(2) DEFAULT '0' COMMENT '评分状态',
  `nickname` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '提交人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间：',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间：',
  PRIMARY KEY (`user_answer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `comment_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '评论ID：',
  `user_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '评论人ID：',
  `reply_to_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '回复评论ID：空为0',
  `content` longtext COMMENT '内容：',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称：',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像地址：[0,255]',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间：',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间：',
  `source_table` varchar(255) DEFAULT NULL COMMENT '来源表：',
  `source_field` varchar(255) DEFAULT NULL COMMENT '来源字段：',
  `source_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '来源ID：',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='评论：';
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `collect_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '收藏ID：',
  `user_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '收藏人ID：',
  `source_table` varchar(255) DEFAULT NULL COMMENT '来源表：',
  `source_field` varchar(255) DEFAULT NULL COMMENT '来源字段：',
  `source_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '来源ID：',
  `title` varchar(255) DEFAULT NULL COMMENT '标题：',
  `img` varchar(255) DEFAULT NULL COMMENT '封面：',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间：',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间：',
  PRIMARY KEY (`collect_id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='收藏：';
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID：[0,8388607]用户获取其他与用户相关的数据',
  `state` smallint(1) unsigned NOT NULL DEFAULT '1' COMMENT '账户状态：[0,10](1可用|2异常|3已冻结|4已注销)',
  `user_group` varchar(32) DEFAULT NULL COMMENT '所在用户组：[0,32767]决定用户身份和权限',
  `login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次登录时间：',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号码：[0,11]用户的手机号码，用于找回密码时或登录时',
  `phone_state` smallint(1) unsigned NOT NULL DEFAULT '0' COMMENT '手机认证：[0,1](0未认证|1审核中|2已认证)',
  `username` varchar(16) NOT NULL DEFAULT '' COMMENT '用户名：[0,16]用户登录时所用的账户名称',
  `nickname` varchar(16) DEFAULT '' COMMENT '昵称：[0,16]',
  `password` varchar(64) NOT NULL DEFAULT '' COMMENT '密码：[0,32]用户登录所需的密码，由6-16位数字或英文组成',
  `email` varchar(64) DEFAULT '' COMMENT '邮箱：[0,64]用户的邮箱，用于找回密码时或登录时',
  `email_state` smallint(1) unsigned NOT NULL DEFAULT '0' COMMENT '邮箱认证：[0,1](0未认证|1审核中|2已认证)',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像地址：[0,255]',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间：',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户账户：用于保存用户登录信息';
insert into `user` values ('1','1','管理员','2022-12-25 18:21:49.0',null,'0','admin','admin','bfd59291e825b5f2bbf1eb76569f8fe7','','0','/api/upload/admin_avatar.jpg','2022-12-01 17:35:13.0');
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user`(system_user_id int(11) NOT NULL AUTO_INCREMENT COMMENT '系统用户ID',
`user_name` varchar(64) comment '用户姓名',
`gender` varchar(64) comment '性别',
`examine_state` varchar(16) DEFAULT '已通过' NOT NULL comment '审核状态',
`recommend` int(11) DEFAULT '0' NOT NULL comment '智能推荐',
`user_id` int(11) DEFAULT '0' NOT NULL comment '用户ID',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (system_user_id))ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '系统用户';

DROP TABLE IF EXISTS `postgraduate_examination_materials`;
CREATE TABLE `postgraduate_examination_materials`(postgraduate_examination_materials_id int(11) NOT NULL AUTO_INCREMENT COMMENT '考研资料ID',
`data_name` varchar(64) comment '资料名称',
`cover` varchar(255) comment '封面',
`data_type` varchar(64) comment '资料类型',
`knowledge_points` varchar(64) comment '知识点',
`information_documents` varchar(255) comment '资料文件',
`hits` int(11) DEFAULT 0 NOT NULL comment '点击数',
`praise_len` int(11) DEFAULT 0 NOT NULL comment '点赞数',
`recommend` int(11) DEFAULT '0' NOT NULL comment '智能推荐',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (postgraduate_examination_materials_id))ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '考研资料';
insert into `postgraduate_examination_materials` values (1,'资料名称1','/api/upload/1605383529531703297.jpg','资料类型1','知识点1','',486,444,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `postgraduate_examination_materials` values (2,'资料名称2','/api/upload/1605383857165565952.jpg','资料类型2','知识点2','',472,25,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `postgraduate_examination_materials` values (3,'资料名称3','/api/upload/1605383431808614401.jpg','资料类型3','知识点3','',783,128,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `postgraduate_examination_materials` values (4,'资料名称4','/api/upload/1605383598666416128.jpg','资料类型4','知识点4','',291,707,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `postgraduate_examination_materials` values (5,'资料名称5','/api/upload/1605383480412209152.jpg','资料类型5','知识点5','',783,927,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `postgraduate_examination_materials` values (6,'资料名称6','/api/upload/1605383752110833664.jpg','资料类型6','知识点6','',768,851,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `postgraduate_examination_materials` values (7,'资料名称7','/api/upload/1605383363164635137.jpg','资料类型7','知识点7','',648,365,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `postgraduate_examination_materials` values (8,'资料名称8','/api/upload/1605383309079085056.jpg','资料类型8','知识点8','',659,875,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');

DROP TABLE IF EXISTS `data_sharing`;
CREATE TABLE `data_sharing`(data_sharing_id int(11) NOT NULL AUTO_INCREMENT COMMENT '资料分享ID',
`data_name` varchar(64) comment '资料名称',
`cover` varchar(255) comment '封面',
`data_type` varchar(64) comment '资料类型',
`knowledge_points` varchar(64) comment '知识点',
`information_documents` varchar(255) comment '资料文件',
`shared_objects` int(11) DEFAULT 0 comment '分享对象',
`recommend` int(11) DEFAULT '0' NOT NULL comment '智能推荐',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (data_sharing_id))ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '资料分享';
insert into `data_sharing` values (1,'资料名称1','/api/upload/1605383857165565952.jpg','资料类型1','知识点1','',0,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `data_sharing` values (2,'资料名称2','/api/upload/1605383480412209152.jpg','资料类型2','知识点2','',0,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `data_sharing` values (3,'资料名称3','/api/upload/1605383529531703297.jpg','资料类型3','知识点3','',0,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `data_sharing` values (4,'资料名称4','/api/upload/1605383363164635137.jpg','资料类型4','知识点4','',0,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `data_sharing` values (5,'资料名称5','/api/upload/1605383752110833664.jpg','资料类型5','知识点5','',0,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `data_sharing` values (6,'资料名称6','/api/upload/1605383598666416128.jpg','资料类型6','知识点6','',0,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `data_sharing` values (7,'资料名称7','/api/upload/1605383431808614401.jpg','资料类型7','知识点7','',0,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `data_sharing` values (8,'资料名称8','/api/upload/1605383309079085056.jpg','资料类型8','知识点8','',0,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');

DROP TABLE IF EXISTS `data_type`;
CREATE TABLE `data_type`(data_type_id int(11) NOT NULL AUTO_INCREMENT COMMENT '资料类型ID',
`data_type` varchar(64) comment '资料类型',
`recommend` int(11) DEFAULT '0' NOT NULL comment '智能推荐',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (data_type_id))ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '资料类型';
insert into `data_type` values (1,'资料类型1',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `data_type` values (2,'资料类型2',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `data_type` values (3,'资料类型3',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `data_type` values (4,'资料类型4',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `data_type` values (5,'资料类型5',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `data_type` values (6,'资料类型6',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `data_type` values (7,'资料类型7',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `data_type` values (8,'资料类型8',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');

DROP TABLE IF EXISTS `colleges_and_universities`;
CREATE TABLE `colleges_and_universities`(colleges_and_universities_id int(11) NOT NULL AUTO_INCREMENT COMMENT '报考院校ID',
`name_of_institution` varchar(64) comment '院校名称',
`cover` varchar(255) comment '封面',
`college_major` text comment '院校专业',
`score_over_the_years` text comment '历年分数线',
`details_of_institutions` longtext comment '院校详情',
`hits` int(11) DEFAULT 0 NOT NULL comment '点击数',
`praise_len` int(11) DEFAULT 0 NOT NULL comment '点赞数',
`recommend` int(11) DEFAULT '0' NOT NULL comment '智能推荐',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (colleges_and_universities_id))ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '报考院校';
insert into `colleges_and_universities` values (1,'院校名称1','/api/upload/1588455618736291841.jpg','此处可上传文字、图片、视频、超链接、表格等内容区1','此处可上传文字、图片、视频、超链接、表格等内容区1','此处可上传文字、图片、视频、超链接、表格等内容区1',455,342,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `colleges_and_universities` values (2,'院校名称2','/api/upload/1601165903049785345.jpg','此处可上传文字、图片、视频、超链接、表格等内容区2','此处可上传文字、图片、视频、超链接、表格等内容区2','此处可上传文字、图片、视频、超链接、表格等内容区2',655,625,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `colleges_and_universities` values (3,'院校名称3','/api/upload/1601165803086938113.jpg','此处可上传文字、图片、视频、超链接、表格等内容区3','此处可上传文字、图片、视频、超链接、表格等内容区3','此处可上传文字、图片、视频、超链接、表格等内容区3',154,455,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `colleges_and_universities` values (4,'院校名称4','/api/upload/1588455688319795201.jpg','此处可上传文字、图片、视频、超链接、表格等内容区4','此处可上传文字、图片、视频、超链接、表格等内容区4','此处可上传文字、图片、视频、超链接、表格等内容区4',182,227,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `colleges_and_universities` values (5,'院校名称5','/api/upload/1588455840975683584.jpg','此处可上传文字、图片、视频、超链接、表格等内容区5','此处可上传文字、图片、视频、超链接、表格等内容区5','此处可上传文字、图片、视频、超链接、表格等内容区5',838,815,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `colleges_and_universities` values (6,'院校名称6','/api/upload/1588455795303907328.jpg','此处可上传文字、图片、视频、超链接、表格等内容区6','此处可上传文字、图片、视频、超链接、表格等内容区6','此处可上传文字、图片、视频、超链接、表格等内容区6',493,180,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `colleges_and_universities` values (7,'院校名称7','/api/upload/1588455544144789504.jpg','此处可上传文字、图片、视频、超链接、表格等内容区7','此处可上传文字、图片、视频、超链接、表格等内容区7','此处可上传文字、图片、视频、超链接、表格等内容区7',428,561,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `colleges_and_universities` values (8,'院校名称8','/api/upload/1588455486275977217.jpg','此处可上传文字、图片、视频、超链接、表格等内容区8','此处可上传文字、图片、视频、超链接、表格等内容区8','此处可上传文字、图片、视频、超链接、表格等内容区8',32,59,0,'2023-01-27 09:42:55','2023-01-27 09:42:55');

DROP TABLE IF EXISTS `online_questions`;
CREATE TABLE `online_questions`(online_questions_id int(11) NOT NULL AUTO_INCREMENT COMMENT '在线提问ID',
`question_no` varchar(64) comment '问题编号',
`ask_the_user` int(11) DEFAULT 0 comment '提问用户',
`user_name` varchar(64) comment '用户姓名',
`problem_description` text comment '问题描述',
`problem_attachment` varchar(255) comment '问题附件',
`examine_state` varchar(16) DEFAULT '未审核' NOT NULL comment '审核状态',
`examine_reply` varchar(16) DEFAULT '' comment '审核回复',
`recommend` int(11) DEFAULT '0' NOT NULL comment '智能推荐',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (online_questions_id))ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '在线提问';
insert into `online_questions` values (1,'问题编号1',0,'用户姓名1','此处可上传文字、图片、视频、超链接、表格等内容区1','','未审核','',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `online_questions` values (2,'问题编号2',0,'用户姓名2','此处可上传文字、图片、视频、超链接、表格等内容区2','','未审核','',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `online_questions` values (3,'问题编号3',0,'用户姓名3','此处可上传文字、图片、视频、超链接、表格等内容区3','','未审核','',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `online_questions` values (4,'问题编号4',0,'用户姓名4','此处可上传文字、图片、视频、超链接、表格等内容区4','','未审核','',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `online_questions` values (5,'问题编号5',0,'用户姓名5','此处可上传文字、图片、视频、超链接、表格等内容区5','','未审核','',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `online_questions` values (6,'问题编号6',0,'用户姓名6','此处可上传文字、图片、视频、超链接、表格等内容区6','','未审核','',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `online_questions` values (7,'问题编号7',0,'用户姓名7','此处可上传文字、图片、视频、超链接、表格等内容区7','','未审核','',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `online_questions` values (8,'问题编号8',0,'用户姓名8','此处可上传文字、图片、视频、超链接、表格等内容区8','','未审核','',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');

DROP TABLE IF EXISTS `online_qa`;
CREATE TABLE `online_qa`(online_qa_id int(11) NOT NULL AUTO_INCREMENT COMMENT '在线答疑ID',
`question_no` varchar(64) NOT NULL UNIQUE comment '问题编号',
`ask_the_user` int(11) DEFAULT 0 comment '提问用户',
`user_name` varchar(64) comment '用户姓名',
`problem_description` text comment '问题描述',
`problem_attachment` varchar(255) comment '问题附件',
`qa_description` longtext comment '答疑描述',
`recommend` int(11) DEFAULT '0' NOT NULL comment '智能推荐',
`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (online_qa_id))ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '在线答疑';
insert into `online_qa` values (1,'问题编号1',0,'用户姓名1','此处可上传文字、图片、视频、超链接、表格等内容区1','','此处可上传文字、图片、视频、超链接、表格等内容区1',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `online_qa` values (2,'问题编号2',0,'用户姓名2','此处可上传文字、图片、视频、超链接、表格等内容区2','','此处可上传文字、图片、视频、超链接、表格等内容区2',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `online_qa` values (3,'问题编号3',0,'用户姓名3','此处可上传文字、图片、视频、超链接、表格等内容区3','','此处可上传文字、图片、视频、超链接、表格等内容区3',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `online_qa` values (4,'问题编号4',0,'用户姓名4','此处可上传文字、图片、视频、超链接、表格等内容区4','','此处可上传文字、图片、视频、超链接、表格等内容区4',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `online_qa` values (5,'问题编号5',0,'用户姓名5','此处可上传文字、图片、视频、超链接、表格等内容区5','','此处可上传文字、图片、视频、超链接、表格等内容区5',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `online_qa` values (6,'问题编号6',0,'用户姓名6','此处可上传文字、图片、视频、超链接、表格等内容区6','','此处可上传文字、图片、视频、超链接、表格等内容区6',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `online_qa` values (7,'问题编号7',0,'用户姓名7','此处可上传文字、图片、视频、超链接、表格等内容区7','','此处可上传文字、图片、视频、超链接、表格等内容区7',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');
insert into `online_qa` values (8,'问题编号8',0,'用户姓名8','此处可上传文字、图片、视频、超链接、表格等内容区8','','此处可上传文字、图片、视频、超链接、表格等内容区8',0,'2023-01-27 09:42:55','2023-01-27 09:42:55');

insert into `auth` values ('1','管理员','系统用户','system_user','系统用户','/system_user/table','','_blank','1','1','1','1','user_name,gender','user_name,gender','user_name,gender',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('2','管理员','系统用户','system_user','系统用户详情','/system_user/view','','_blank','1','1','1','1','user_name,gender','user_name,gender','user_name,gender',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('3','管理员','考研资料','postgraduate_examination_materials','考研资料','/postgraduate_examination_materials/table','','_blank','1','1','1','1','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents',null,'0','{"can_show_comment":true}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('4','管理员','考研资料','postgraduate_examination_materials','考研资料详情','/postgraduate_examination_materials/view','','_blank','1','1','1','1','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('5','管理员','考研资料','postgraduate_examination_materials','考研资料','/postgraduate_examination_materials/list','top','_blank','1','1','1','1','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('6','管理员','考研资料','postgraduate_examination_materials','考研资料详情','/postgraduate_examination_materials/details','','_blank','1','1','1','1','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents',null,'0','{"can_comment":true,"can_show_comment":true}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('7','管理员','资料分享','data_sharing','资料分享','/data_sharing/table','','_blank','1','1','1','1','data_name,cover,data_type,knowledge_points,information_documents,shared_objects','data_name,cover,data_type,knowledge_points,information_documents,shared_objects','data_name,cover,data_type,knowledge_points,information_documents,shared_objects',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('8','管理员','资料分享','data_sharing','资料分享详情','/data_sharing/view','','_blank','1','1','1','1','data_name,cover,data_type,knowledge_points,information_documents,shared_objects','data_name,cover,data_type,knowledge_points,information_documents,shared_objects','data_name,cover,data_type,knowledge_points,information_documents,shared_objects',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('9','管理员','资料分享','data_sharing','资料分享','/data_sharing/edit','','_blank','1','1','1','1','data_name,cover,data_type,knowledge_points,information_documents,shared_objects','data_name,cover,data_type,knowledge_points,information_documents,shared_objects','data_name,cover,data_type,knowledge_points,information_documents,shared_objects',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('10','管理员','资料类型','data_type','资料类型','/data_type/table','','_blank','1','1','1','1','data_type','data_type','data_type',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('11','管理员','资料类型','data_type','资料类型详情','/data_type/view','','_blank','1','1','1','1','data_type','data_type','data_type',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('12','管理员','报考院校','colleges_and_universities','报考院校','/colleges_and_universities/table','','_blank','1','1','1','1','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions',null,'0','{"can_show_comment":true}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('13','管理员','报考院校','colleges_and_universities','报考院校详情','/colleges_and_universities/view','','_blank','1','1','1','1','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('14','管理员','报考院校','colleges_and_universities','报考院校','/colleges_and_universities/list','top','_blank','1','1','1','1','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('15','管理员','报考院校','colleges_and_universities','报考院校详情','/colleges_and_universities/details','','_blank','1','1','1','1','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions',null,'0','{"can_comment":true,"can_show_comment":true}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('16','管理员','在线提问','online_questions','在线提问','/online_questions/table','','_blank','1','1','1','1','question_no,ask_the_user,user_name,problem_description,problem_attachment','question_no,ask_the_user,user_name,problem_description,problem_attachment','question_no,ask_the_user,user_name,problem_description,problem_attachment',null,'0','{"examine":true}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('17','管理员','在线提问','online_questions','在线提问详情','/online_questions/view','','_blank','1','1','1','1','question_no,ask_the_user,user_name,problem_description,problem_attachment','question_no,ask_the_user,user_name,problem_description,problem_attachment','question_no,ask_the_user,user_name,problem_description,problem_attachment',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('18','管理员','在线提问','online_questions','在线提问','/online_questions/edit','top','_blank','1','1','1','1','question_no,ask_the_user,user_name,problem_description,problem_attachment','question_no,ask_the_user,user_name,problem_description,problem_attachment','question_no,ask_the_user,user_name,problem_description,problem_attachment',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('19','管理员','在线答疑','online_qa','在线答疑','/online_qa/table','','_blank','1','1','1','1','question_no,ask_the_user,user_name,problem_description,problem_attachment,qa_description','question_no,ask_the_user,user_name,problem_description,problem_attachment,qa_description','question_no,ask_the_user,user_name,problem_description,problem_attachment,qa_description',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('20','管理员','在线答疑','online_qa','在线答疑详情','/online_qa/view','','_blank','1','1','1','1','question_no,ask_the_user,user_name,problem_description,problem_attachment,qa_description','question_no,ask_the_user,user_name,problem_description,problem_attachment,qa_description','question_no,ask_the_user,user_name,problem_description,problem_attachment,qa_description',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('21','管理员','我的收藏','collect','我的收藏','/collect/list','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('22','管理员','评论','comment','评论列表','/comment/table','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('23','管理员','评论','comment','评论详情','/comment/view','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('24','管理员','评论','comment','我的评论','/comment/list','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('25','管理员','评论','comment','评论详情','/comment/details','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('26','管理员','论坛','forum','交流中心','/forum/table','','_blank','1','1','1','1','','','',null,'0','{"print":true,"export_db":true,"import_db":true}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('27','管理员','论坛','forum','交流详情','/forum/view','','_blank','1','1','1','1','','','',null,'0','{"print":true}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('28','管理员','论坛','forum','交流中心','/forum/list','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('29','管理员','论坛','forum','交流详情','/forum/details','','_blank','1','1','1','1','','','',null,'0','{"can_comment":true,"can_show_comment":true}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('30','管理员','论坛分类','forum_type','交流分类列表','/forum_type/table','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('31','管理员','论坛分类','forum_type','交流分类详情','/forum_type/view','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('32','管理员','考试','exam','考试列表','/exam/table','','_blank','1','1','1','1','','','',null,'0','{"export_db":true,"answers":true,"give_score":true,"setting":true}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('33','管理员','考试','exam','考试详情','/exam/view','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('34','管理员','考试','exam','前台列表','/exam/list','','_blank','1','1','1','1','','','',null,'0','{"answers":true}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('35','管理员','考试','exam','前台详情','/exam/details','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('36','管理员','公告','notice','公告列表','/notice/table','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('37','管理员','公告','notice','公告详情','/notice/view','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('38','管理员','公告','notice','公告信息','/notice/list','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('39','管理员','公告','notice','公告详情','/notice/details','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('40','游客','系统用户','system_user','系统用户','/system_user/table','','_blank','1','0','0','0','user_name,gender','user_name,gender','user_name,gender',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('41','游客','系统用户','system_user','系统用户详情','/system_user/view','','_blank','1','0','0','0','user_name,gender','user_name,gender','user_name,gender',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('42','游客','考研资料','postgraduate_examination_materials','考研资料','/postgraduate_examination_materials/table','','_blank','0','0','0','0','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents',null,'0','{"can_show_comment":false}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('43','游客','考研资料','postgraduate_examination_materials','考研资料详情','/postgraduate_examination_materials/view','','_blank','0','0','0','0','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('44','游客','考研资料','postgraduate_examination_materials','考研资料','/postgraduate_examination_materials/list','top','_blank','0','0','0','1','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('45','游客','考研资料','postgraduate_examination_materials','考研资料详情','/postgraduate_examination_materials/details','','_blank','0','0','0','1','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents',null,'0','{"can_comment":false,"can_show_comment":true}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('46','游客','资料分享','data_sharing','资料分享','/data_sharing/table','','_blank','0','0','0','0','data_name,cover,data_type,knowledge_points,information_documents,shared_objects','data_name,cover,data_type,knowledge_points,information_documents,shared_objects','data_name,cover,data_type,knowledge_points,information_documents,shared_objects',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('47','游客','资料分享','data_sharing','资料分享详情','/data_sharing/view','','_blank','0','0','0','0','data_name,cover,data_type,knowledge_points,information_documents,shared_objects','data_name,cover,data_type,knowledge_points,information_documents,shared_objects','data_name,cover,data_type,knowledge_points,information_documents,shared_objects',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('48','游客','资料分享','data_sharing','资料分享','/data_sharing/edit','','_blank','0','0','0','0','data_name,cover,data_type,knowledge_points,information_documents,shared_objects','data_name,cover,data_type,knowledge_points,information_documents,shared_objects','data_name,cover,data_type,knowledge_points,information_documents,shared_objects',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('49','游客','资料类型','data_type','资料类型','/data_type/table','','_blank','0','0','0','0','data_type','data_type','data_type',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('50','游客','资料类型','data_type','资料类型详情','/data_type/view','','_blank','0','0','0','0','data_type','data_type','data_type',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('51','游客','报考院校','colleges_and_universities','报考院校','/colleges_and_universities/table','','_blank','0','0','0','0','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions',null,'0','{"can_show_comment":false}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('52','游客','报考院校','colleges_and_universities','报考院校详情','/colleges_and_universities/view','','_blank','0','0','0','0','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('53','游客','报考院校','colleges_and_universities','报考院校','/colleges_and_universities/list','top','_blank','0','0','0','1','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('54','游客','报考院校','colleges_and_universities','报考院校详情','/colleges_and_universities/details','','_blank','0','0','0','1','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions',null,'0','{"can_comment":false,"can_show_comment":true}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('55','游客','在线提问','online_questions','在线提问','/online_questions/table','','_blank','0','0','0','0','question_no,ask_the_user,user_name,problem_description,problem_attachment','question_no,ask_the_user,user_name,problem_description,problem_attachment','question_no,ask_the_user,user_name,problem_description,problem_attachment',null,'0','{"examine":false}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('56','游客','在线提问','online_questions','在线提问详情','/online_questions/view','','_blank','0','0','0','0','question_no,ask_the_user,user_name,problem_description,problem_attachment','question_no,ask_the_user,user_name,problem_description,problem_attachment','question_no,ask_the_user,user_name,problem_description,problem_attachment',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('57','游客','在线提问','online_questions','在线提问','/online_questions/edit','top','_blank','0','0','0','1','question_no,ask_the_user,user_name,problem_description,problem_attachment','question_no,ask_the_user,user_name,problem_description,problem_attachment','question_no,ask_the_user,user_name,problem_description,problem_attachment',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('58','游客','在线答疑','online_qa','在线答疑','/online_qa/table','','_blank','0','0','0','0','question_no,ask_the_user,user_name,problem_description,problem_attachment,qa_description','question_no,ask_the_user,user_name,problem_description,problem_attachment,qa_description','question_no,ask_the_user,user_name,problem_description,problem_attachment,qa_description',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('59','游客','在线答疑','online_qa','在线答疑详情','/online_qa/view','','_blank','0','0','0','0','question_no,ask_the_user,user_name,problem_description,problem_attachment,qa_description','question_no,ask_the_user,user_name,problem_description,problem_attachment,qa_description','question_no,ask_the_user,user_name,problem_description,problem_attachment,qa_description',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('60','游客','我的收藏','collect','我的收藏','/collect/list','','_blank','0','0','0','0','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('61','游客','评论','comment','评论列表','/comment/table','','_blank','0','0','0','0','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('62','游客','评论','comment','评论详情','/comment/view','','_blank','0','0','0','0','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('63','游客','评论','comment','我的评论','/comment/list','','_blank','0','0','0','1','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('64','游客','评论','comment','评论详情','/comment/details','','_blank','0','0','0','1','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('65','游客','论坛','forum','交流中心','/forum/table','','_blank','0','0','0','0','','','',null,'0','{"print":false,"export_db":false,"import_db":false}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('66','游客','论坛','forum','交流详情','/forum/view','','_blank','0','0','0','0','','','',null,'0','{"print":false}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('67','游客','论坛','forum','交流中心','/forum/list','','_blank','0','0','0','1','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('68','游客','论坛','forum','交流详情','/forum/details','','_blank','0','0','0','1','','','',null,'0','{"can_comment":false,"can_show_comment":true}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('69','游客','论坛分类','forum_type','论坛分类列表','/forum_type/table','','_blank','0','0','0','0','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('70','游客','论坛分类','forum_type','论坛分类详情','/forum_type/view','','_blank','0','0','0','0','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('71','游客','考试','exam','考试列表','/exam/table','','_blank','0','0','0','0','','','',null,'0','{"export_db":false,"answers":false,"give_score":false,"setting":false}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('72','游客','考试','exam','考试详情','/exam/view','','_blank','0','0','0','0','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('73','游客','考试','exam','前台列表','/exam/list','','_blank','0','0','0','0','','','',null,'0','{"answers":false}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('74','游客','考试','exam','前台详情','/exam/details','','_blank','0','0','0','0','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('75','游客','公告','notice','公告列表','/notice/table','','_blank','0','0','0','0','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('76','游客','公告','notice','公告详情','/notice/view','','_blank','0','0','0','0','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('77','游客','公告','notice','公告信息','/notice/list','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('78','游客','公告','notice','公告详情','/notice/details','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('79','系统用户','系统用户','system_user','系统用户','/system_user/table','','_blank','0','0','0','0','user_name,gender','user_name,gender','user_name,gender',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('80','系统用户','系统用户','system_user','系统用户详情','/system_user/view','','_blank','0','0','0','0','user_name,gender','user_name,gender','user_name,gender',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('81','系统用户','考研资料','postgraduate_examination_materials','考研资料','/postgraduate_examination_materials/table','','_blank','0','0','0','0','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents',null,'0','{"can_show_comment":false}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('82','系统用户','考研资料','postgraduate_examination_materials','考研资料详情','/postgraduate_examination_materials/view','','_blank','0','0','0','0','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('83','系统用户','考研资料','postgraduate_examination_materials','考研资料','/postgraduate_examination_materials/list','top','_blank','1','1','1','1','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('84','系统用户','考研资料','postgraduate_examination_materials','考研资料详情','/postgraduate_examination_materials/details','','_blank','1','1','1','1','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents','data_name,cover,data_type,knowledge_points,information_documents',null,'0','{"can_comment":true,"can_show_comment":true}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('85','系统用户','资料分享','data_sharing','资料分享','/data_sharing/table','','_blank','0','1','0','1','data_name,cover,data_type,knowledge_points,information_documents,shared_objects','data_name,cover,data_type,knowledge_points,information_documents,shared_objects','data_name,cover,data_type,knowledge_points,information_documents,shared_objects',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('86','系统用户','资料分享','data_sharing','资料分享详情','/data_sharing/view','','_blank','0','1','0','1','data_name,cover,data_type,knowledge_points,information_documents,shared_objects','data_name,cover,data_type,knowledge_points,information_documents,shared_objects','data_name,cover,data_type,knowledge_points,information_documents,shared_objects',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('87','系统用户','资料分享','data_sharing','资料分享','/data_sharing/edit','','_blank','1','1','1','1','data_name,cover,data_type,knowledge_points,information_documents,shared_objects','data_name,cover,data_type,knowledge_points,information_documents,shared_objects','data_name,cover,data_type,knowledge_points,information_documents,shared_objects',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('88','系统用户','资料类型','data_type','资料类型','/data_type/table','','_blank','0','0','0','0','data_type','data_type','data_type',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('89','系统用户','资料类型','data_type','资料类型详情','/data_type/view','','_blank','0','0','0','0','data_type','data_type','data_type',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('90','系统用户','报考院校','colleges_and_universities','报考院校','/colleges_and_universities/table','','_blank','0','0','0','0','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions',null,'0','{"can_show_comment":false}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('91','系统用户','报考院校','colleges_and_universities','报考院校详情','/colleges_and_universities/view','','_blank','0','0','0','0','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('92','系统用户','报考院校','colleges_and_universities','报考院校','/colleges_and_universities/list','top','_blank','1','1','1','1','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('93','系统用户','报考院校','colleges_and_universities','报考院校详情','/colleges_and_universities/details','','_blank','1','1','1','1','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions','name_of_institution,cover,college_major,score_over_the_years,details_of_institutions',null,'0','{"can_comment":true,"can_show_comment":true}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('94','系统用户','在线提问','online_questions','在线提问','/online_questions/table','','_blank','0','1','0','1','question_no,ask_the_user,user_name,problem_description,problem_attachment','question_no,ask_the_user,user_name,problem_description,problem_attachment','question_no,ask_the_user,user_name,problem_description,problem_attachment',null,'0','{"examine":false}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('95','系统用户','在线提问','online_questions','在线提问详情','/online_questions/view','','_blank','0','1','0','1','question_no,ask_the_user,user_name,problem_description,problem_attachment','question_no,ask_the_user,user_name,problem_description,problem_attachment','question_no,ask_the_user,user_name,problem_description,problem_attachment',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('96','系统用户','在线提问','online_questions','在线提问','/online_questions/edit','top','_blank','1','1','1','1','question_no,ask_the_user,user_name,problem_description,problem_attachment','question_no,ask_the_user,user_name,problem_description,problem_attachment','question_no,ask_the_user,user_name,problem_description,problem_attachment',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('97','系统用户','在线答疑','online_qa','在线答疑','/online_qa/table','','_blank','0','1','0','1','question_no,ask_the_user,user_name,problem_description,problem_attachment,qa_description','question_no,ask_the_user,user_name,problem_description,problem_attachment,qa_description','question_no,ask_the_user,user_name,problem_description,problem_attachment,qa_description',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('98','系统用户','在线答疑','online_qa','在线答疑详情','/online_qa/view','','_blank','0','1','0','1','question_no,ask_the_user,user_name,problem_description,problem_attachment,qa_description','question_no,ask_the_user,user_name,problem_description,problem_attachment,qa_description','question_no,ask_the_user,user_name,problem_description,problem_attachment,qa_description',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('99','系统用户','我的收藏','collect','我的收藏','/collect/list','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('100','系统用户','评论','comment','评论列表','/comment/table','','_blank','0','0','0','0','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('101','系统用户','评论','comment','评论详情','/comment/view','','_blank','0','0','0','0','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('102','系统用户','评论','comment','我的评论','/comment/list','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `auth` values ('103','系统用户','评论','comment','评论详情','/comment/details','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:56.0','2023-01-27 09:42:56.0');
insert into `auth` values ('104','系统用户','论坛','forum','交流中心','/forum/table','','_blank','1','1','1','1','','','',null,'0','{"print":true,"export_db":true,"import_db":true}','2023-01-27 09:42:56.0','2023-01-27 09:42:56.0');
insert into `auth` values ('105','系统用户','论坛','forum','交流详情','/forum/view','','_blank','1','1','1','1','','','',null,'0','{"print":true}','2023-01-27 09:42:56.0','2023-01-27 09:42:56.0');
insert into `auth` values ('106','系统用户','论坛','forum','交流中心','/forum/list','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:56.0','2023-01-27 09:42:56.0');
insert into `auth` values ('107','系统用户','论坛','forum','交流详情','/forum/details','','_blank','1','1','1','1','','','',null,'0','{"can_comment":true,"can_show_comment":true}','2023-01-27 09:42:56.0','2023-01-27 09:42:56.0');
insert into `auth` values ('108','系统用户','论坛分类','forum_type','交流分类列表','/forum_type/table','','_blank','0','0','0','0','','','',null,'0','{}','2023-01-27 09:42:56.0','2023-01-27 09:42:56.0');
insert into `auth` values ('109','系统用户','论坛分类','forum_type','交流分类详情','/forum_type/view','','_blank','0','0','0','0','','','',null,'0','{}','2023-01-27 09:42:56.0','2023-01-27 09:42:56.0');
insert into `auth` values ('110','系统用户','考试','exam','考试列表','/exam/table','','_blank','0','0','0','0','','','',null,'0','{"export_db":false,"answers":false,"give_score":false,"setting":false}','2023-01-27 09:42:56.0','2023-01-27 09:42:56.0');
insert into `auth` values ('111','系统用户','考试','exam','考试详情','/exam/view','','_blank','0','0','0','0','','','',null,'0','{}','2023-01-27 09:42:56.0','2023-01-27 09:42:56.0');
insert into `auth` values ('112','系统用户','考试','exam','前台列表','/exam/list','','_blank','1','1','1','1','','','',null,'0','{"answers":true}','2023-01-27 09:42:56.0','2023-01-27 09:42:56.0');
insert into `auth` values ('113','系统用户','考试','exam','前台详情','/exam/details','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:56.0','2023-01-27 09:42:56.0');
insert into `auth` values ('114','系统用户','公告','notice','公告列表','/notice/table','','_blank','0','0','0','0','','','',null,'0','{}','2023-01-27 09:42:56.0','2023-01-27 09:42:56.0');
insert into `auth` values ('115','系统用户','公告','notice','公告详情','/notice/view','','_blank','0','0','0','0','','','',null,'0','{}','2023-01-27 09:42:56.0','2023-01-27 09:42:56.0');
insert into `auth` values ('116','系统用户','公告','notice','公告信息','/notice/list','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:56.0','2023-01-27 09:42:56.0');
insert into `auth` values ('117','系统用户','公告','notice','公告详情','/notice/details','','_blank','1','1','1','1','','','',null,'0','{}','2023-01-27 09:42:56.0','2023-01-27 09:42:56.0');
insert into `user_group` values ('1','100','管理员',null,'','','0','0','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `user_group` values ('2','100','游客',null,'','','0','0','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `user_group` values ('3','100','系统用户',null,'system_user','system_user_id','0','3','2023-01-27 09:42:55.0','2023-01-27 09:42:55.0');
insert into `slides` values ('1','轮播图1','内容1','/article/details?article=1','/api/upload/1565526092209455105.jpg','559','2023-01-27 09:42:56.0','2023-01-27 09:42:56.0');
insert into `slides` values ('2','轮播图2','内容2','/article/details?article=2','/api/upload/1575411380251525121.jpg','55','2023-01-27 09:42:56.0','2023-01-27 09:42:56.0');
insert into `slides` values ('3','轮播图3','内容3','/article/details?article=3','/api/upload/1565524124921495553.jpg','311','2023-01-27 09:42:56.0','2023-01-27 09:42:56.0');

UPDATE forum 
SET img = CONCAT('/api',img);
