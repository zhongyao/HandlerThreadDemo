### Android HandlerThread的运用和理解 + IntentService的运用和理解。

#### 一、HandlerThread原理及运用

##### 1、HandlerThread出现的背景：
我们知道Thread线程是一次性消费品，当Thread线程执行完一个耗时的任务之后，线程就会被自动销毁了。如果此时我又有一
个耗时任务需要执行，我们不得不重新创建线程去执行该耗时任务。然而，这样就存在一个性能问题：多次创建和销毁线程是很耗
系统资源的。为了解这种问题，我们可以自己构建一个循环线程Looper Thread，当有耗时任务投放到该循环线程中时，线程执行耗
时任务，执行完之后循环线程处于等待状态，直到下一个新的耗时任务被投放进来。这样一来就避免了多次创建Thread线程导致的性能问题了。

##### 2、HandlerThread的主要作用:
每隔几秒钟更新数据或图片等

##### 3、HandlerThread的原理：
继承了Thread，实际上是一个使用Handler,Looper的线程。
1）继承了Thread，在run方法中通过Looper.prepare()来创建消息队列，通过Looper.Loop()来循环处理消息。
2）使用时创建HandlerThread，创建Handler并与HandlerThread的Looper绑定，Handler以消息的方式通知HandlerThread
来执行一个具体的任务。

##### 4、HandlerThread的使用步骤：
1）创建HandlerThread实例对象。
2）启动HandlerThread线程。
3）构建循环消息处理机制：即将HandlerThread创建的Looper作为参数创建Handler对象，这样就完成了Handler对象与HandlerThread的Looper对象的绑定
（这里的Handler对象可以看做是绑定在HandlerThread中，所有handleMessage方法里面的操作是在子线程中运行的）,重写
handleMessage方法处理耗时操作。





#### 二、IntentService的原理及运用

##### 1、IntentService的原理：
IntentService是继承Service并处理异步请求的一个类，在IntentService内有一个工作线程来处理耗时任务，
启动IntentService的方式跟启动传统Service的方式一样，同时当任务执行完毕之后，IntentService会自动停止。
而不需要我们手动去控制。另外，可以启动IntentService多次，而每一个耗时操作多会以工作队列的方式在IntentService的onHandleIntent回调方法中执行，
并且每次只会执行一个工作线程，根据先后顺序，执行完第一个任务在执行第二个，以此类推。

##### 2、使用IntentService的优点如下：

1）省去了在Service中手动开启线程的麻烦。

2）当操作完成后，我们不需要手动停止Service。
