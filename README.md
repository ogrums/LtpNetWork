# LtpNetWork

Android Retrofit client for the Letianpai cloud. This is not a server and it cannot stand in for one. It only calls an existing host.

The production paths declared here are:

| Method | Path | Purpose |
|---|---|---|
| GET | `/robot_api/v1/bind/getIotTriplet` | IoT long-connection credentials |
| GET | `/robot_api/v1/bind/getSnByMac` | Serial number and hardcode from the MAC |
| POST | `/robot_api/v1/device/upgrade/status` | OTA progress |
| GET | `/robot_api/v1/ota/getLatestPackage` | Latest OTA package |
| POST | `/robot_api/v1/device/addRecord` | Upload a photo or voice record |
| GET | `/robot_api/v1/cloudFile/getToken` | Qiniu upload token |
| GET | `/robot_api/v1/cloudFile/getSessionToken` | S3 session token |

The launcher also needs calendar, weather, clock, countdown, general config, and channel-logo endpoints. Those paths are not in this library. `Constants.kt` points both regions at the local mock, `http://10.0.2.2:8080/` (the emulator's name for the host). Use `http://127.0.0.1:8080/` when the app is not in an emulator.

### Mock

A runnable server that returns these bodies is `mock/main.go` in [third_party_demo](https://github.com/ogrums/third_party_demo) (`go run ./mock`, port 8080).

For a unit test, use [FakeNetDataRepository](app/src/test/java/com/letianpai/network/template/repository/FakeNetDataRepository.kt). It implements `NetDataRepositorySource` and emits `Resource.Success` without the network:

```kotlin
val repo: NetDataRepositorySource = FakeNetDataRepository()
repo.getSnHardcode(hashMapOf()).collect { result ->
    Log.d("mock", result.data?.sn ?: "missing")
}
```

`getSnByMac` JSON, which matches `SnHardcode`:

```json
{"code":0,"msg":"success","data":{"client_id":"mock-client","hard_code":"mock-hardcode","sn":"EMULATOR00000000"}}
```


### 1. Usage

The return type is Resource so it can be observed with LiveData later.

Example:

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



### 2. Example

Declare the method on NetDataRepositorySource, then implement it in NetDataRepository.

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

Call sites only use NetDataRepository. Start every request from that class.

The result is a Resource with three states: Success, Loading, and DataError.

Handle the Resource payload directly, or combine it with LiveData for a one-way data flow.



---

#### Steps

To use this library, extend or override four types: NewApi, RemoteData, NetDataRepository, and NetDataRepositorySource.



1. Declare the Retrofit method on NewApi:

```kotlin
@GET("index/getorder")
suspend fun getOrderModelReq(): Response<BaseResultBean<OrderModel>>
```



2. Subclass RemoteDataSource and implement the NewApi method. The return type changes:

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



3. Declare the Flow on NetDataRepositorySource:

```kotlin
fun getOrderModelReq(): Flow<Resource<OrderModel>>
```

4. Implement it in NetDataRepository:

```kotlin
override fun getOrderModelReq(): Flow<Resource<OrderModel>> {
    return flow {
        emit(remoteData.getOrderModelReq())
    }.flowOn(ioDispatcher)
}
```

5. Call it from a coroutine:

```kotlin
private suspend fun test3(){
    val repo: NetDataRepositorySource = NetDataRepository(this)
    repo.getOrderModelReq().collect {
        Log.d("<<<", "it=${it.data?.addressModel?.cityName}")
    }
}
```

---

Tips: the library ships interceptors that can later sign or encrypt requests.

---
To integrate it, add this to the root settings.gradle:
```groovy
include ':LtpNetWork'
project(':LtpNetWork').projectDir = new File('LtpNetWork/app')
```
Then depend on it from the app build.gradle:
```groovy
implementation project(path: ':LtpNetWork')
```

