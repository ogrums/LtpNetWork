# LtpNetWork

Retrofit and OkHttp client for the robot cloud. The existing Chinese README title is `RHJNetWork`. This file replaces that description. Business code should call `NetDataRepository`, not Retrofit directly.

Return values are `Resource` so a caller can observe them later with LiveData:

- `Success` — body unwrapped
- `Loading`
- `DataError`

Callers are expected to subclass `NewApi`, `RemoteData`, `NetDataRepository`, and `NetDataRepositorySource` when they add a route. Interceptors are the place for signing.

## How to depend on it

```groovy
include ':LtpNetWork'
project(':LtpNetWork').projectDir = new File('LtpNetWork/app')
implementation project(path: ':LtpNetWork')
```

The Gradle module is `:app`, but `app/build.gradle` applies `com.android.library`.

## Hosts

`ServiceGenerator.getBaseUrl()` reads `persist.sys.region.language`.

| Property | Host constant | Comment in source |
|---|---|---|
| empty or `zh` | `RELEASE_HOST_TEST` (`https://your-server.com`) | production |
| anything else | `GLOBAL_IotUrl` (`https://your-global-server.com`) | overseas |

Both hosts are placeholders. Do not make `ServiceGenerator` a singleton: the comment says the URL would not change when the language changes.

## Routes that are real product calls

| Method | Path | Meaning |
|---|---|---|
| GET | `/robot_api/v1/bind/getIotTriplet` | Long-connection device triplet |
| GET | `/robot_api/v1/bind/getSnByMac` | Serial number and hardcode |
| POST | `/robot_api/v1/device/upgrade/status` | OTA download progress |
| GET | `/robot_api/v1/ota/getLatestPackage` | Latest OTA package |
| POST | `/robot_api/v1/device/addRecord` | Upload a record (default kind is audio) |
| GET | `/robot_api/v1/cloudFile/getToken` | Qiniu upload token |
| GET | `/robot_api/v1/cloudFile/getSessionToken` | AWS S3 session token |

`index/addorder`, `addons/shop/checkout/submit`, `index/hello`, and `index/getorder` are leftover test routes.

`LTPSignInterceptor` sorts query keys in a `TreeMap` (ascending), appends a timestamp, and MD5-signs the string. `YHeaderInterceptor` (file `LTPHeaderInterceptor.kt`) adds MAC and SN headers.

`UploadService` asks for a token, uploads through Qiniu or S3, then binds the account.

## Comment glossary

| Where | Chinese | English |
|---|---|---|
| `NewApi` | 获取长连三元组 / 获取SN 和 hardcode | Get the long-connection triplet / get SN and hardcode |
| `NewApi` | 更新下载进度 / 获取OTA版本信息 | Update download progress / get OTA package info |
| `Constants` | 正式 / 海外 | Production / overseas |
| `ServiceGenerator` | 不能用单利，不然切换语言的时候，URL不会变 | Do not use a singleton, or the URL will not change when the language switches |
| `LTPSignInterceptor` | TreeMap里面的数据会按照key值自动升序排列 | `TreeMap` sorts entries by key |
| `UploadService` | 默认是录音 / 上传成功 | Default record type is audio / upload succeeded |

## Build

compileSdk 32, minSdk 26. Retrofit 2.9.0, Moshi 1.14.0, OkHttp, Qiniu Android SDK, AWS Android S3.
