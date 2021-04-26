package in.amruthashala.momapp.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConversions {

    public String convertDate(String pattern){
        String dateInString =new SimpleDateFormat(pattern).format(new Date());
        return dateInString;
    }
}
