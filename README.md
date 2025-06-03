# RHJNetWork

### 一，使用说明

返回类型定义为 Resource 方便与后期给livedata监听；

例如：

```kotlin
private val test2LiveDataPrivate = MutableLiveData<Resource<ChallengeEntity>>()
val test2LiveData: LiveData<Resource<ChallengeEntity>> get() = test2LiveDataPrivate
```

```kotlin
fun test2() {
    viewModelScope.launch {
        test2LiveDataPrivate.value = Resource.Loading()
        netDataRepo.getTest2Request().collect {
            viewModelScope.launch(Dispatchers.Main) {
                test2LiveDataPrivate.value = it
            }
        }
    }
}
```



### 二，使用举例

在NetDataRepositorySource 接口中定义具体接口方法，然后在NetDataRepository中实现此接口。

```kotlin
fun getTestRequest(): Flow<Resource<String>>
```

```kotlin
private suspend fun test(){
    val repo: NetDataRepositorySource = NetDataRepository(this)
    repo.getTestRequest().collect {
        Log.d("<<<", "it=${it.data}")
    }
}
```

​	

面向业务的只有NetDataRepository类，需要请求什么接口都从此类出发。

请求回来的是自定义的Resource类型，其中有三种状态，Success、Loading、DataError。

可以单独处理Resource类型中的数据，也可以将Resource和livedata结合，实现单向数据流绑定的方式。



---

#### # 具体步骤：

在使用此库的时候，有 4 个类需要使用者继承覆写，分别是 NewApi，RemoteData, NetDataRepository,  NetDataRepositorySource。



1，在NewApi中，声明接口方法：

```kotlin
@GET("index/getorder")
suspend fun getOrderModelReq(): Response<BaseResultBean<OrderModel>>
```



2，写一个类继承自RemoteDataSource，实现NewApi中的方法，这里注意，返回值类型不同了：

```kotlin
override suspend fun getOrderModelReq(): Resource<OrderModel> {
    val response = processCall(newApiService::getOrderModelReq)
    return if (response is OrderModel) {
        Resource.Success(data = response)
    } else {
        responseError(response)
    }
}
```



3，在NetDataRepositorySource 中声明，定义flow格式：

```kotlin
fun getOrderModelReq(): Flow<Resource<OrderModel>>
```

4，在 NetDataRepository 中具体实现：

```kotlin
override fun getOrderModelReq(): Flow<Resource<OrderModel>> {
    return flow {
        emit(remoteData.getOrderModelReq())
    }.flowOn(ioDispatcher)
}
```

5，使用，需要放在协程中：

```kotlin
private suspend fun test3(){
    val repo: NetDataRepositorySource = NetDataRepository(this)
    repo.getOrderModelReq().collect {
        Log.d("<<<", "it=${it.data?.addressModel?.cityName}")
    }
}
```

---

Tips: 此库中定义了一些拦截器，后期根据需求可用来签名、加密等。

---
集成的时候，需要在根目录的setting.gradle中加入如下路径：
```groovy
include ':LtpNetWork'
project(':LtpNetWork').projectDir = new File('LtpNetWork/app')
```
在 app目录下面的build.gradle中引入即可。
```groovy
implementation project(path: ':LtpNetWork')
```

