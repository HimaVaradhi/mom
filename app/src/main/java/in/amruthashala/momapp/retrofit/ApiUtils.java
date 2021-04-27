package in.amruthashala.momapp.retrofit;


import android.content.Context;

/**
 * Created by varadhi on 10/3/18.
 */

public class ApiUtils {
    Context mContext;
    String apiTag;
    private ApiUtils(Context context, String ApiTag) {
        this.mContext = context;
        this.apiTag = ApiTag;
    }

    public static final String BASE_URL = "https://varadhi-mom.herokuapp.com/";
    public static APIService getAPIService(Context context) {
        return ApiClient.getClient(BASE_URL,context).create(APIService.class);
    }
}
