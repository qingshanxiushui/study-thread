https://www.kdocs.cn/l/ctdw9s9tf6tc?from=docs 

## one java线程
CreateThread 创建线程方法一：直接使用Thread  
CreateThreadTwo 创建线程方法二：使用 Runnable 配合 Thread  
CreateThreadThree 创建线程方法三：FutureTask 配合 Thread
StartAndRun start与run方法-运行线程
SleepAndYield sleep与yield方法阻塞
JoinIntroduce  join方法等待
InterruptIntroduce interrupt方法打断
InterruptTwoPhaseTermination  interrupt方法打断-两阶段终止模式
InterruptPark interrupt方法打断
DaemonThread daemon方法-守护进程
CriticalPath 习题—统筹方法

## two 共享模型之管程
####  共享带来的问题
ShareProblem
####  Synchronized解决方案
SynchronizedProcess
SynchronizedProcessTwo 面向对象的改进
SynchronizedCaseOne 线程八锁情况1：12或21
SynchronizedCaseTwo 线程八锁情况2：1s后12，或 2 1s后 1
SynchronizedCaseThree 线程八锁情况3: 3 1s 12 或 23 1s 1 或 32 1s 1
SynchronizedCaseFour 线程八锁情况4: 2 1s 后 1
SynchronizedCaseFive 线程八锁情况5：2 1s 后 1 锁的不是一个对象
SynchronizedCaseSix 线程八锁情况6：1s后12， 或 2 1s后 1 锁的同一个对象 类对象
SynchronizedCaseSeven 线程八锁情况7：2 1s 后 1 线程1锁的类对象 线程2锁的是n2对象 不是一个对象
SynchronizedCaseEight 线程八锁情况8：1s 后12， 或 2 1s后 1 用的同一个Number.class对象 互斥
####  变量线程安全
VariableThreadUnsafe
VariableThreadsafe
VariableThreadSafeTwo
####  常见线程安全类
ThreadSafeClass
####  练习
ExerciseSell 买票
ExerciseTransfer 转账
#### monitor
MonitorUndoBiasLock  撤销偏向锁,其他线程使用对象
MonitorUndoBiasLockTwo 撤销偏向锁,调用wait/notify
MonitorBatchBiasLock 批量重偏向
#### Wait与Notify方法
WaitNotifyOne
#### Wait与Notify正确姿势
WaitNotifyTwo
GuardedInstance  同步模式之保护性暂停
GuardedInstanceMulti 多任务版
AsynchronousInstance 异步模式之生产者/消费者
#### Park与Unpark方法
ParkInstance
#### 多把锁
MultiLock
#### 活跃性
DeadLock 死锁
PhilosophersDinner 哲学家问题
LiveLock 活锁
#### ReentrantLock
ReentrantLockOne 可重入
ReentrantLockTwo 可打断
ReentrantLockThree 锁超时
PhilosophersDinnerTwo 解决哲学家问题
ReentrantLockFour 条件变量
SequentialControlOne 同步模式之顺序控制:固定运行顺序,wait notify 版
SequentialControlTwo 同步模式之顺序控制:固定运行顺序,Park Unpark 版
SequentialControlThree  同步模式之顺序控制:交替输出,wait notify 版
SequentialControlFour 同步模式之顺序控制:交替输出,Lock 条件变量版
SequentialControlFive 同步模式之顺序控制:交替输出,Park Unpark 版

## Three 共享模型之内存
TwoPhaseTermination  两阶段终止模式
TwoPhaseTerminationTwo  两阶段终止模式
BalkInstanceOne 同步模式之Balking
BalkInstanceTwo 同步模式之Balking
Singleton 线程安全的单例
SingletonTwo 线程安全的单例改进double-checked locking

## Four 共享模型之无锁
AccountTestOne 不安全
AccountTestTwo 加锁解决
AccountTestThree 无锁解决
AccountTestFour compareAndSet改为原子整数
AtomicInstance 原子整数实例
AtomicReferenceInstance  原子引用
ABAProblem 原子引用ABA问题
ABAProblemTwo 原子引用ABA问题AtomicStampedReference
ABAProblemThree 原子引用ABA问题AtomicMarkableReference
AtomicArrayInstance 原子数组
AtomicFieldInstance 字段更新器
AtomicAdderInstance 原子累加器
casLockInstance cas锁
UnsafeInstance Unsafe
UnsafeInstanceTwo Unsafe实现线程安全原子整数

## Five 共享模型之不可变
DateFormatProblem  日期转换的问题
DateFormatProblemSolveOne  日期转换的问题-同步锁解决
DateFormatProblemSolveTwo  日期转换的问题-不可变
ImmutableFlyPool 不可变设计-亨元模式连接池

## Six 共享模型之工具
#### 线程池
CustomThreadPool 自定义线程池
ThreadPoolInstance 线程池newFixedThreadPool
ThreadPoolInstanceTwo 线程池newCachedThreadPool
ThreadPoolInstanceThree 线程池newSingleThreadExecutor
ThreadPoolInstanceFour 线程池提交任务
ThreadPoolInstanceFive 线程池关闭
AsynchronousThread  异步模式之工作线程
TaskSchedulingThread  任务调度线程池
TaskExceptionHandling 正确处理执行任务异常
ScheduledTasksApplication 应用之定时任务
ForkJoinInstance 实例Fork/Join
#### JUC
JUCCustomLock 自定义锁(不可重入锁)
ReadWriteLockInstance 读写锁ReentrantReadWriteLock
StampedLockInstance StampedLock实例
SemaphoreInstance Semaphore实例：信号量，用来限制能同时访问共享资源的线程上限。
SemaphorePool Semaphore实例改进连接池
CountdownLatchInstance CountdownLatch实例: 用来进行线程同步协作，等待所有线程完成倒计时后才恢复运行。await() 用来等待计数归零，countDown() 用来让计数减一。
CountdownLatchInstanceTwo  CountdownLatch实例线程池
CountdownLatchInstanceThree CountdownLatch应用多线程准备完毕-模拟王者加载过程
CyclicBarrierInstance CyclicBarrier实例：循环栅栏，用来进行线程协作，等待线程满足某个计数。
CopyOnWriteArrayListInstance CopyOnWriteArrayList实例


