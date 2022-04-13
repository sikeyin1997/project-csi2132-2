import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class test {

    public static void main(String[] args) throws ParseException {

        String sDate1="18/07/1997";

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date1=formatter.parse(sDate1);
        System.out.println(sDate1+"\t"+date1);
    }
}
