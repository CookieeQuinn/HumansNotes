
# 目录
### 第1章 人情笔记APP开发	
1.1 软件需求及功能分析	
1.2 Activity设计	
1.3 关键实现技术	
1.4 测试及使用说明	
1.5 开发过程说明	
1.6 有待完善的地方	
### 第2章 Android安全调查	32


# 第1章人情笔记APP开发
1.1软件需求及功能分析
 软件需求：
用户需要有一款能够记录日常生活中人情往来的收礼、随礼信息的软件。
功能分析：
1.软件启动界面：实现启动时先显示软件启动界面，然后才进入应用主界面。
2.软件的三大板块：
1)软件需要主界面、收礼记录界面、随礼记录界面三个界面。
2)通过类似底部导航栏的模块实现三个界面的切换。
3.主界面需求：
1)显示日历：用户需要在软件主界面上方看到当前月份的日历信息。
2)显示记录：用户需要在软件主界面下方看到当前选中日期所有记录。
3)添加记录：用户需要在软件主界面实现直接添加一条随礼或者收礼信息，并且需要令默认记账日期为当前用户选中的日期。
4.随礼记录界面需求：
1)初始记录；用户没有添加任何随礼记录时，显示一条默认随礼记录。
2)显示记录：用户需要在随礼记录界面看到所有随礼信息，包括名字、金额、日期。
3)添加记录：用户需要增加或者追加一条新的随礼信息。
4)修改记录：用户需要对任意一个随礼信息进行修改。
5)删除记录：用户需要删除一条原有的随礼信息。
5. 收礼记录界面需求：
1)初始记录；用户没有添加任何收礼记录时，显示一条默认收礼记录。
2)显示记录：用户需要在收礼记录界面看到所有收礼信息，包括名字、金额、日期、缘由。
3)添加记录：用户需要增加或者追加一条新的收礼信息。
4)修改记录：用户需要对任意一个收礼信息进行修改。
5)删除记录：用户需要删除一条原有的收礼信息。

1.2Activity设计
应用中使用到的activity只有三种，分别是：MainActivity、StartActivity、UpdateActivity。下面对这三种activity的设计进行详细介绍。
1.MainActivity
(1)这是应用的主activity，用户就是在这个MainActivity中使用软件的。
(2)这个activity实现了软件的三个界面：随礼界面、主界面、收礼界面。
(3)随礼界面、主界面、收礼界面如下显示。

2.StartActivity
(1)这个activity实现的是启动界面的显示。
(2)用户点开应用软件的时候，默认开启的是这个StartActivity。
(3)StartActivity将会短暂显示一小段时间，然后自动跳转到MainActivity。
(4)StartActivity实现的应用启动界面如下图。

3.UpdateActivity
(1)该activity是用于实现增添、修改记录功能的。
(2)该activity是通过mainactivity或者fragment调用的，UpdateActivity不会主动启动。
(3)随礼界面、收礼界面各有各自的UpdateActivity。
(4)随礼的UpdateActivity如下图所示，有名字、金额、日期。

(5)收礼的UpdateActivity如下图所示，有名字、金额、日期和一个缘由。


1.3关键实现技术
下面，关键技术按照各界面各功能分类描述。

1.软件启动界面
启动页需要满足以下条件：①背景全屏，四边不能留有背景空隙②隐藏顶部导航栏③自动跳转到主界面。这一需求使用两个activity实现，一个是MainActivity，一个是启动界面StartActivity，主要是令启动界面StartActivity为默认打开界面，然后设置该界面3s后自动跳转到应用主界面MainActivity。以下为实现该需求的关键技术：
①在AndroidManifest文件中设置StartActivity为启动页。
②Handler实现启动页StartActivity自动跳转到应用主界面MainActivity。
③实现启动页StartActivity全屏显示。
④在启动界面时隐藏导航栏，需要注意要在渲染布局之前
  

2.软件的三大界面：
关键技术是TabLayOut+ViewPager+ FragmentPagerAdapter +Fragment。
①布局文件中使用TabLayout和ViewPager，要实现TabLayout在界面的底部，关键需要在TabLayout的布局中添加下列属性：
android:layout_height="0dp"
android:layout_weight="1"
②新建adapter继承FragmentPagerAdapter，用于将若干个Fragment和Tab的标题装到ViewPager内，使得当滑动Tab时，ViewPager能获取Tab相应的Fragment。这个Adapter重要的要实现的函数是构造函数，重载getPageTitle【Tab的标题】，getItem【获得Tab对应的Fragment】，getCount【Tab数目】
③新建三个Fragment对象，分别用来实现主界面、收礼记录界面、随礼记录界面三个界面。
④通过以下代码实现默认显示主界面，主界面Fragment的索引为1。
 tabLayout.getTabAt(1).select();
⑤创建Adapter对象，并初始化数据包括Fragment及String标题数组，然后借助Adapter实现ViewPager和TabLayout相关联。


3.主界面需求：
关键技术是CalendarView+FloatingActionButton+Intent。
①通过为安卓自带CalendarView设置setOnDataChangeListener() 来为其添加监听事件，用于获取当前选中日期（日期存储在当前fragment的一个私有成员变量中）
②利用FloatingActionButton+Intent+onActivityResult实现添加信息的功能。用fab菜单实现两个fab按钮，这两个按钮分别负责添加随礼、收礼记录的功能，用户按需选择任意一个按钮。
③按钮的监听函数需要实现两个activity的数据传递：
（1）A创建Intent，设置数据，调用startActivityForResult启动B
（2）B通过Intent接收数据，B修改数据
（3）B在退出时，创建Intent，设置修改后的数据，调用setResult把数据传回。
（4）A在onActivityResult中接收B传回的数据，更新界面

4.随礼/收礼记录界面需求：
①ListView+ArrayAdapter实现列表。
这个比较简单，主要技术在于ArrayAdapter的getview的重载，需要实现处理数据——处理子项目布局——生成适配器——安装适配器
②onCreateContextMenu+onContextItemSelected为列表项实现菜单及响应。
onCreateContextMenu,在创建菜单时调用，可以获得创建菜单时的视图以及所点击的项目等参数，可为不同视图创建不同菜单；onContextItemSelected，在菜单项被点击时被调用，根据菜单ID可以判断点击了哪个菜单项，从而给予不同的响应。最关键在于在Activity的onCreate函数中调用registerForContextMenu为视图注册上下文菜单。
③Intent+onActivityResult实现两个activity数据传递。
（1）A创建Intent，设置数据，调用startActivityForResult启动B
（2）B通过Intent接收数据，B修改数据
（3）B在退出时，创建Intent，设置修改后的数据，调用setResult把数据传回。
（4）A在onActivityResult中接收B传回的数据，更新界面

④Serializable+Android APP的数据区文件实现数据的分离及数据的持久化。
这一步主要在于把要存储的对象序列化，然后用输入输出流写到本地的一个txt文件中，每次打开软件都从这个文件读取数据，这样就可以实现数据的持久化。要注意，每次用户修改数据后应该选取合适的地方保存（上传）数据。

⑤lazyload+onResume实现fragment懒加载。
关于fragment预加载在此不做赘述，以下是实现懒加载的关键：
 1.改fragmentadapter的构造函数为新的
public FragmentPagerAdapter(@NonNull FragmentManager fm,
                            @Behavior int behavior) {
    mFragmentManager = fm;
    mBehavior = behavior;
}
2.在fragmentadapter实例化时第二个参数传入
BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
3.前两步实现的是，预加载的时候，被预加载的fragment只执行它自己的oncreateview，而不执行onresume函数，只有点开这个fragment的时候，才会执行它的onresume
4.将点击该fragment后想加载的数据和view的逻辑（代码）放到Fragment的onResume()方法中即可

5.UpdateActivity数据更新
①Calendar+DatePickerDialog+setOnClickListener实现选择日期选择器。
1.在onCreate方法中借助Calendar获取当时的年，月，日
2.通过按钮触发日期选择器
3.实现选择器的监听，并将获取到的结果设置到TextView上

②spinner+ArrayAdapter实现下来表单选择收礼缘由。
1.定义下拉列表的列表项内容 ArrayList<String>。
ReasonDataBank reasonDataBank = new ReasonDataBank(this);
reasonDataBank.Load();
ArrayList<String> list = reasonDataBank.getReasonList();
2.为下拉列表 Spinner 定义一个适配器 ArrayAdapter<String> ，并与列表项内容相关联。
ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

3.使用 ArrayAdapter.setDropDownViewResource() 设置 Spinner 下拉列表在打开时的下拉菜单样式。
adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

4.使用 Spinner. setAdapter() 将适配器数据与 Spinner 关联起来。
spinnertext.setAdapter(adapter);

5.为 Spinner 添加事件监听器，进行事件处理。
spinnertext.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /* 将 spinnertext 显示^*/
        parent.setVisibility(View.VISIBLE);
        reason_position = position;
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        parent.setVisibility(View.VISIBLE);
    }
});
1.4测试及使用说明
一、使用说明
打开软件，显示启动页三秒自动跳转到主界面。
主界面有一个日历和一个悬浮按钮。左边为随礼记录界面，右边为收礼记录界面。
在收礼/随礼记录界面可以直接长按第一条默认信息，会弹出一个菜单。
根据需要选择菜单项：
选增加菜单项，填信息后确认，会在界面选择的信息上方增加一条新记录。
选追加菜单项，填信息后确认，会在界面选择的信息下方增加一条新记录。
选修改菜单项，填信息后确认，会在界面更新选择的信息。
选删除菜单项，确认弹出框，会在界面删除选择的信息。
回到主界面，任选一个日期，点击悬浮按钮展开按钮菜单，选择“随礼”或者“收礼”按钮，可以实现添加一条新记录的功能。

二、测试
运行程序，显示启动页；启动页后，自动跳转到主界面

点击浏览收礼、随礼界面

 回到主界面，点击右下角按钮，选择收礼，输入信息，选着日期，选择缘由，确定

查看收礼信息，长按刚刚新增的记录，选择修改

修改信息，点击确定

长按刚刚修改的记录，选择追加，输入信息，点击确定

长按刚刚追加的记录，选择删除

1.5开发过程说明
软件开发环境：
build.gradle（project）
// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath "com.android.tools.build:gradle:4.1.1"
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
allprojects {
    repositories {
        google()
        jcenter()
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}

Build.gradle（app）
plugins {
    id 'com.android.application'
}
android {
    compileSdkVersion 30
    buildToolsVersion "30.0.2"

    defaultConfig {
        applicationId "com.jnu.lxq"
        minSdkVersion 19
        targetSdkVersion 30
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

开发过程
一、启动页开发过程
1.配置启动界面的布局文件
新建一个layout文件，命名为activity_splash.xml，设置该文件的背景图片。
android:background="@drawable/ic_app_start"
注意，背景图片要先存放到相应目录中。

最初我设置启动页面的背景图片是直接使用了imageview控件，但是一直无法去除两边的白色空隙，最终网上查询方法参考了：
https://blog.csdn.net/weixin_34292402/article/details/85788424
尝试了一下直接使用android:background发现可以实现想要的效果。
2.用启动界面的activity渲染该布局文件
新建一个activity命名为StartActivity，将该activity作为启动界面的窗口。
令StartActivity类继承父类AppCompatActivity
重写StartActivity类的onCreate方法
最后用StartActivity渲染启动界面对应的布局文件activity_splash，代码如下
setContentView(R.layout.activity_splash);
以上步骤如下图所示

3.在AndroidManifest文件中设置StartActivity为启动页
上面只是将数据与布局关联起来，现在我们要让APP启动时默认显示我们想要的启动页。在AndroidManifest文件中，将默认启动页设置为我们的StartActivity即可：
具体操作是把activity的android:name属性值修改为”.StartActivity”。如下图

4.查看一下效果如下图，已经实现默认启动页的显示及全屏显示背景图片。目前还需要改进的地方是把那个导航栏去除


5.去除导航栏可以用下面的方法
参考：https://blog.csdn.net/weixin_34292402/article/details/85788424

代码如下：
supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
需要注意的是，这段代码要写在渲染布局之前，如下图

后来发现下列这一段注释掉也没事

6.现在再看看效果，如下图，已经可以显示启动页了
即实现了全屏，也实现了导航栏隐藏。

7.接下来需要实现的是，显示了启动页之后，跳转到应用主界面
实现activity跳转的关键代码是
new Handler().postDelayed(new Runnable() {
    public void run() {
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
        StartActivity.this.finish();   //关闭StartActivity，将其回收，否则按返回键会返回此界面
    }
}, SPLASH_DISPLAY_LENGHT);
如下图


8.运行程序，成功实现界面跳转。

二、软件的三大界面
关键技术是TabLayOut+ViewPager+ FragmentPagerAdapter +Fragment。
①布局文件中使用TabLayout和ViewPager，要实现TabLayout在界面的底部，关键需要在TabLayout的布局中添加下列属性：

②新建adapter继承FragmentPagerAdapter，用于将若干个Fragment和Tab的标题装到ViewPager内，使得——当滑动Tab时，ViewPager能获取Tab相应的Fragment，。这个Adapter重要的要实现的函数是构造函数，重载getPageTitle【Tab的标题】，getItem【获得Tab对应的Fragment】，getCount【Tab数目】

③新建三个Fragment对象，分别用来实现主界面、收礼记录界面、随礼记录界面三个界面。

④通过以下代码实现默认显示主界面，主界面Fragment的索引为1。
 tabLayout.getTabAt(1).select();
⑤创建Adapter对象，并初始化数据包括Fragment及String标题数组，然后借助Adapter实现ViewPager和TabLayout相关联。关键代码如下：

三、主界面

主界面利用了CalendarView+FloatingActionButton+Intent。
一、配置主界面的日历
1.通过网络查询到，CalendarView是安卓自带的一个日历控件，我们可以在主活动中 通过设置setOnDataChangeListener() 来为其添加监听事件
参考：https://www.jb51.net/article/158010.htm
2.利用安卓自带的日历控件，先配置好布局文件，如下图所示


3.在主activity中添加日历的事件监听函数
	前面我们已经在布局文件中写好日历控件的基本信息了，现在就要在渲染这个布局文件的activity中，添加这个控件的监听函数。
	CalendarView 会在选择日期时触发 CalendarView.OnDateChangeListener 事件。
	首先 为这个控件初始化一个对象，代码如下
CalendarView calendarView;
	然后把这个对象与这个控件绑定，代码如下
calendarView = (CalendarView) findViewById(R.id.calenderView);
接下来为这个控件添加一个事件监听函数，用于获取当前选中日期（日期存储在当前fragment的一个私有成员变量中）代码截图如下


二、利用FloatingActionButton+Intent+onActivityResult实现添加信息的功能。
用fab菜单实现两个fab按钮，这两个按钮分别负责添加随礼、收礼记录的功能，用户按需选择任意一个按钮。布局如下图

关键技术主要是按钮的监听函数需要实现两个activity的数据传递：
（1）A创建Intent，设置数据，调用startActivityForResult启动B
（2）B通过Intent接收数据，B修改数据
（3）B在退出时，创建Intent，设置修改后的数据，调用setResult把数据传回。
（4）A在onActivityResult中接收B传回的数据，更新界面

按钮的效果如下



四、随礼/收礼记录界面需求：
①ListView+ArrayAdapter实现列表。
这个比较简单，主要技术在于ArrayAdapter的getview的重载，需要实现处理数据——处理子项目布局——生成适配器——安装适配器
一、处理数据：
首先从需求出发，因为我希望在屏幕上显示人名、金额、日期，所以声明pay类表示一个信息：

然后在fragment中初始化

到此为止，我们的数据处理就结束了。
二、布局处理：
因为ListView列表中有很多子项目，因此若我们要显示数据，就要把数据绑定到子项目对应的控件上去。这时我们需要有一个描述该控件的布局的布局文件，子项目显示人名、金额、日期

然后根据实验要求，完善布局文件代码。过程中我发现标签都是单标签，属性是写在尖括号内的。并且熟悉了id属性的书写格式。其次对宽高属性也有了一点理解。其中文本标签的text属性我没有写，因为这个text是我们后续会在控制层用数据填写的。 
 到这里，我们的布局文件就完成了。
三、生成适配器：
适配器的本质就是生成控件并且把数据绑定到控件上去。
因为这里我们的数据源比较特殊，它是我们自定义的 类，所以我们要根据这个 类写一个“专属适配器”。 如下图这行代码，是我用来声明并初始化上述我预想中的“专属适配器” 的 ，我们需要重写BookAdapter类的getview方法，这是最核心的。 getview的本质就是生成控件，并把数据绑定到控件上。这里我们需要：1.先获取数据源2.然后生成控件3.最后把数据绑定到控件上去。这三步的代码如下图所示

完成了适配器的创建以后，我必须要安装上这个适配器才能使其发挥作用。
四、安装适配器
安装适配器的意思就是说，把适配器设置到ListView控件上，使的这个ListView能在适配器的作用下，显示子项目。安装适配器的代码如下。

②onCreateContextMenu+onContextItemSelected为列表项实现菜单及响应。
onCreateContextMenu,在创建菜单时调用，可以获得创建菜单时的视图以及所点击的项目等参数，可为不同视图创建不同菜单；onContextItemSelected，在菜单项被点击时被调用，根据菜单ID可以判断点击了哪个菜单项，从而给予不同的响应。最关键在于在Activity的onCreate函数中调用registerForContextMenu为视图注册上下文菜单。
（一）第一步：重写activity的onCreateContextMenu——这个用于长按的时候创建菜单。
①直接 generate 重写onCreateContextMenu，即可得到一个代码模板
②然后为这个菜单添加一些菜单项，如下图。其中itemId代表每一个菜单项，title是该菜单项显示出来的字符。
 onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)：在创建菜单时调用，可以获得创建菜单时的视图以及所点击的项目等参数，可为不同视图创建不同菜单。
（二）第二步：registerForContextMenu(listviewCat)——为对应的Listview控件注册上下文菜单。
这一步是为listview控件添加上下文菜单menu的关键步骤。下图红框的代码就是为我们的Listview注册刚才写好的上下文菜单menu。如果没有这一句代码，长按这个Listview是不会弹出菜单的。
另外，这一步属于对控件的操作，因此放在控件初始化方法initView里。如下图代码，为listviewCat注册了一个菜单响应的宿主（宿主是activity窗口）

（三）第三步：重写 onContextItemSelected菜单点击事件响应函数——当我们选中菜单某一项时，会执行该菜单项所对应的操作。这一步里，我们就要为每一个菜单项设置他们各自相应的操作，这个操作也可以理解为“响应”。具体步骤如下：
①直接 generate，重写onContextItemSelected，即可得到一个代码模板
②这个菜单中有很多菜单项，我们是根据itemId来区分每一个菜单项的
③可以使用switch-case结构来实现选中不同菜单项时执行不同的操作。
④如下图，该函数就是菜单响应函数，其中他只有唯一一个参数，就是item，item指的就是菜单中被选中的菜单项，这个对象有一个getItemId方法，用于获取菜单项的itemId，由此可以定位到相应的操作分支中，以便执行：
 
③Intent+onActivityResult实现两个activity数据传递。
（1）A创建Intent，设置数据，调用startActivityForResult启动B
（2）B通过Intent接收数据，B修改数据
（3）B在退出时，创建Intent，设置修改后的数据，调用setResult把数据传回。
（4）A在onActivityResult中接收B传回的数据，更新界面
④Serializable+Android APP的数据区文件实现数据的分离及数据的持久化。
这一步主要在于把要存储的对象序列化，然后用输入输出流写到本地的一个txt文件中，每次打开软件都从这个文件读取数据，这样就可以实现数据的持久化。要注意，每次用户修改数据后应该选取合适的地方保存（上传）数据。
因为下图红框代码，我的程序无法正确load数据，要注意！注释掉就可以了。

⑤lazyload+onResume实现fragment懒加载。
关于fragment预加载在此不做赘述，以下是实现懒加载的关键：
 1.改fragmentadapter的构造函数为新的
public FragmentPagerAdapter(@NonNull FragmentManager fm,
                            @Behavior int behavior) {
    mFragmentManager = fm;
    mBehavior = behavior;
}
2.在fragmentadapter实例化时第二个参数传入
BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
3.前两步实现的是，预加载的时候，被预加载的fragment只执行它自己的oncreateview，而不执行onresume函数，只有点开这个fragment的时候，才会执行它的onresume
4.将点击该fragment后想加载的数据和view的逻辑（代码）放到Fragment的onResume()方法中即可

四、UpdateActivity数据更新
①Calendar+DatePickerDialog+setOnClickListener实现选择日期选择器。
在onCreate方法中借助Calendar获取当时的年，月，日，如下图

通过按钮触发日期选择器

实现选择器的监听，并将获取到的结果设置到TextView上

下图是日期选择器效果图

②spinner+ArrayAdapter实现下来表单选择收礼缘由。
1.定义下拉列表的列表项内容 ArrayList<String>。
ReasonDataBank reasonDataBank = new ReasonDataBank(this);
reasonDataBank.Load();
ArrayList<String> list = reasonDataBank.getReasonList();
2.为下拉列表 Spinner 定义一个适配器 ArrayAdapter<String> ，并与列表项内容相关联。
ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
3.使用 ArrayAdapter.setDropDownViewResource() 设置 Spinner 下拉列表在打开时的下拉菜单样式。
adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
4.使用 Spinner. setAdapter() 将适配器数据与 Spinner 关联起来。
spinnertext.setAdapter(adapter);
5.为 Spinner 添加事件监听器，进行事件处理。
spinnertext.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /* 将 spinnertext 显示^*/
        parent.setVisibility(View.VISIBLE);
        reason_position = position;
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        parent.setVisibility(View.VISIBLE);
    }
});

1.6有待完善的地方
目前实现的功能虽然简单、数量少，但是都能正确使用，能满足用户基本需求。没有实现的功能日后将逐步完善，比如收礼记录按照时间线显示，随礼记录按缘由分类显示，主界面显示当前日期的记录。日历太简约，没有实现农历、节气、节日等。界面的浮动按钮fab不能自动收回按钮菜单，必须手动收回。
第2章Android安全调查
 	本学期我认为与安全相关的是app在手机中文件的存储。在学习数据持久化的时候涉及到几种方式：参考：https://www.cnblogs.com/hanyonglu/archive/2012/03/01/2374894.html
1 使用SharedPreferences存储数据
2 文件存储数据
3 SQLite数据库存储数据
4 使用ContentProvider存储数据
5 网络存储数据
如若要保证app数据安全，首先文件访问权限要把好关，私密数据应该使用别的app无法访问的方式存储，非必要情况尽量减少数据公开。
以下是几种方式的说明
1、使用SharedPreferences存储数据。
①适用范围：保存少量的数据，且这些数据的格式非常简单：字符串型、基本类型的值。比如应用程序的各种配置信息（如是否打开音效、是否使用震动效果、小游戏的玩家积分等），解锁口 令密码等。
②优点：SharedPreferences对象与SQLite数据库相比，免去了创建数据库，创建表，写SQL语句等诸多操作，相对而言更加方便，简洁。
③缺点：只能存储boolean，int，float，long和String五种简单的数据类型，比如其无法进行条件查询等。所以不论SharedPreferences的数据存储操作是如何简单，它也只能是存储方式的一种补充，而无法完全替代如SQLite数据库这样的其他数据存储方式。且这个方式下存储的数据只能在同一个包内使用，不能在不同的包之间使用，其实也就是说只能在创建它的应用中使用，其他应用无法使用。
2、文件存储数据
①优点：Android中最基本的一种数据存储方式。不对存储内容做任何的格式化处理，所有数据都是原封不动地保存到文件中。因此，这种方式比较适合存储一些文本数据或二进制数据。如果你想使用文件存储的方式来保存比较复杂的文本数据，则需要自己定义一套格式规范，这样方便之后将数据从文件中重新解析出来。不需要权限，APP卸载时自动删除，可以设置仅本APP才能读写，也可以共享给其他APP读写
②缺点：手机内存有限，特别大量的数据还是放到SD卡或者网络数据库之类；不能用普通的Java方式打开。文件存储的方式并不适用于保存一些较为复杂的文本数据。如果使用内存存储，当文件被保存在内部存储中时，默认情况下文件是应用程序私有的，其他应用不能访问。当用户卸载应用程序时这些文件也跟着被删除。通过该方法打开外存储的根目录，你应该在以下目录下写入你的应用数据，这样当卸载应用程序时该目录及其所有内容也将被删除。
3、SQLite数据库存储数据
①优点： 轻量级，独立性，隔离性，安全性
②缺点： SQLite在并发的读写方面性能不是很好，数据库有时候可能会被某个读写操作独占，可能会导致其他的读写操作被阻塞或者出错。不支持SQL92标准，有时候语法不严格也可以通过，会养成不好习惯，导致不会维护。
4、使用ContentProvider存储数据
①优点：解决了应用程序之间共享数据的问题。Content Provider提供了一种多应用间数据共享的方式，一个Content Provider类实现了一组标准的方法接口，从而能够让其他的应用保存或读取此Content Provider的各种数据类型。也就是说，一个程序可以通过实现一个Content Provider的抽象接口将自己的数据暴露出去。外界根本看不到，也不用看到这个应用暴露的数据在应用当中是如何存储的，或者是用数据库存储还是用文件存储，还是通过网上获得，这些一切都不重要，重要的是外界可以通过这一套标准及统一的接口和程序里的数据打交道，可以读取程序的数据，也可以删除程序的数据。
②缺点： 中间也会涉及一些权限的问题。
5、网络存储数据
①优点： 几乎可以不用担心内存问题，本地处理麻烦的可以交由服务器处理
②缺点： 完全依赖于网络 ，网络延迟和资费成本都会上升

