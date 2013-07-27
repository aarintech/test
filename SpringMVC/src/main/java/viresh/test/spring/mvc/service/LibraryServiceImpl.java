package viresh.test.spring.mvc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SG0217160
 * Date: 7/26/13
 * Time: 3:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class LibraryServiceImpl implements LibraryService {
    public List<Date> getHolidays() {


        List<Date> holidays = new ArrayList<Date>();
        holidays.add(new GregorianCalendar(2007, 11, 25).getTime());
        holidays.add(new GregorianCalendar(2008, 0, 1).getTime());
        return holidays;
    }

}
