# retrofit2.0-rxjava2.0-
支持加载loading ProgressDialog，支持线程调度，支持字符串直接解析，支持调试多环境切换，支持全局添加token

```
@Override
public Observable<List<ActivityInfo>> getActivityInfos(String communittId, Map<String, Object> params) {
        return ServiceFactory.createOauthService(ActivityService.class)
                .getActivityInfos(communittId, params)
                .debounce(1, TimeUnit.SECONDS)
                .compose(new ErrorStandardCheckerTransformer<DataActivities>())
                .compose(SchedulersCompat.<DataActivities>applyIoSchedulers())
                .map(new Func1<DataActivities, List<ActivityInfo>>() {
                    @Override
                    public List<ActivityInfo> call(DataActivities dataActivities) {
                        return dataActivities.getActivities();
                    }
                });
    }
    
```

```
model.getActivityInfos(communityId, params).subscribe(new ErrorHandleSubscriber<List<ActivityInfo>>(context) {
            @Override
            public void onStart() {
                super.onStart();
                if (forupdate) {
                    view.showRefreshing(recyclerView, true);
            @Override
            public void onCompleted() {
                //如果数据为空，则展示空数据提示
                
            }

            @Override
            public void onNext(List<ActivityInfo> activityInfos) {
                if (forupdate) {//刷新数据
                }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            
            }
        });
```

外部对同一返回json格式数据的封装

```
/**
 * Created by Eric.w on 2016/12/21.
 */

public class XiaoyanResponse<T> {
    /**
     * 请求成功码
     */
    public static final int CODE_SUCCESS = 0;

    int code;
    String message;
    /**
     * code != 0;error:date = null;
     */
    T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
```

