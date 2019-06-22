### Android HandlerThread的运用和理解 + IntentService的运用和理解。

#### 一、HandlerThread原理及运用
##### 1、HandlerThread的主要作用:
每隔几秒钟更新数据或图片等

##### 2、HandlerThread的原理：
继承了Thread，实际上是一个使用Handler,Looper的线程。
1）继承了Thread，在run方法中通过Looper.prepare()来创建消息队列，通过Looper.Loop()来循环处理消息。
2）使用时创建HandlerThread，创建Handler并与HandlerThread的Looper绑定，Handler以消息的方式通知HandlerThread
来执行一个具体的任务。

##### 3、HandlerThread的使用步骤：
1）创建HandlerThread线程并启动。
2）将HandlerThread创建的Looper作为参数创建Handler对象，这样就完成了Handler对象与HandlerThread的Looper对象的绑定
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
