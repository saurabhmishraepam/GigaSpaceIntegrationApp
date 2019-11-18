package aa.bean.app;

import aa.bean.PNRSegment;
import aa.bean.SegLegMap;
import com.epam.gigaspaceintegration.bean.Person;
import com.epam.gigaspaceintegration.constant.GSGridMode;
import com.epam.gigaspaceintegration.constant.XAPSpaceInstance;
import com.epam.gigaspaceintegration.service.CacheQueryService;
import com.epam.gigaspaceintegration.service.GSCacheQueryServiceImpl;
import com.j_spaces.core.client.SQLQuery;

import java.util.ArrayList;
import java.util.List;

public class CustomeQueryApp {
    //modify these properties to change the space and host configuration
    private static final String HOST_NAME ="saurabh-home";
    private static final String SPACE_NAME ="demo";
    private static XAPSpaceInstance xapSpacedetailes;
    private static GSCacheQueryServiceImpl<PNRSegment> pnrSegCacheService ;
    public static GSCacheQueryServiceImpl<SegLegMap> segLegCacheService ;

    private static GSGridMode mode;



    public  void init() {
        prepareTestEnvironment();
        prepareTestData();
    }

    private  void prepareTestEnvironment(){
        xapSpacedetailes= XAPSpaceInstance.CUSTOME_SPACE;
        xapSpacedetailes.setSpaceName(SPACE_NAME);
        xapSpacedetailes.setHostName(HOST_NAME);
        mode= GSGridMode.REMOTE;
        pnrSegCacheService=   new GSCacheQueryServiceImpl<PNRSegment>(mode, xapSpacedetailes);
        segLegCacheService =  new GSCacheQueryServiceImpl<SegLegMap>(mode, xapSpacedetailes);
    }

    public void prepareTestData(){

        PNRSegment pnrSegment1=new PNRSegment("ABCD",101, "12/1/2019  8:30:00 AM", "DFW", "ORD","");
        PNRSegment pnrSegment2=new PNRSegment("XYZEF",425, "12/1/2019  7:00:00 AM", "LAX", "NYC","");
        List<PNRSegment> pnrSegmentList=new ArrayList<>();
        pnrSegmentList.add(pnrSegment1);
        pnrSegmentList.add(pnrSegment2);
        pnrSegCacheService.write(pnrSegment1);
        pnrSegCacheService.write(pnrSegment2);

        SegLegMap segLegMap1= new SegLegMap(101, "12/1/2019  8:30:00 AM", "DFW", "ORD", "L1", "12/1/2019  8:30:00 AM", "101", "DFW", "STL");
        SegLegMap segLegMap2= new SegLegMap(101, "12/1/2019  12:00:00 AM", "DFW", "ORD", "L2", "12/1/2019  6:30:00 PM", "101", "STL", "ORD");

        SegLegMap segLegMap3= new SegLegMap(425, "12/1/2019  7:00:00 AM", "LAX", "NYC", "L1", "12/1/2019  7:00:00 AM", "425", "LAX", "DEN");
        SegLegMap segLegMap4= new SegLegMap(425, "12/1/2019  7:00:00 AM", "LAX", "NYC", "L2", "12/1/2019  2:00:00 PM", "425", "DEN", "NYC");
        List<SegLegMap> SegLegMapLegMaps=new ArrayList<>();
        segLegCacheService.write(segLegMap1);
        segLegCacheService.write(segLegMap2);
        segLegCacheService.write(segLegMap3);
        segLegCacheService.write(segLegMap4);

    }

    public static void main(String [] args){


        CustomeQueryApp customeQueryApp=new CustomeQueryApp();

        customeQueryApp.init();


        PNRSegment result1 = segLegCacheService.gigaSpace.read(new SQLQuery<PNRSegment>(PNRSegment.class, "flightId = 425"));
        System.out.println(result1);






    }



}
