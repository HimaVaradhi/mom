package in.amruthashala.momapp.retrofit;



/**
 * Created by varadhi on 10/3/18.
 */

public class ApiUtils {

    private ApiUtils() {}

    //public static final String BASE_URL = "https://rocky-refuge-28492.herokuapp.com/";
   // public static final String BASE_URL = "https://varadhi-product.herokuapp.com/";
    public static final String BASE_URL = "https://varadhi-mom.herokuapp.com/";
   // public static final String BASE_URL = "http://15.207.113.202:8000/amrutha/";

    public static APIService getAPIService() {

        return ApiClient.getClient(BASE_URL).create(APIService.class);
    }

}
