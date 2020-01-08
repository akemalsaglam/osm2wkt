import util.CoordinateTrasformator;
import util.FileReader;
import util.Landmark;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.util.stream.Collectors.toMap;

public class Application {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {


        /*List<Landmark> landmarkList=FileReader.getLandmarksFromFile();
        HashMap<Long, Landmark> landmarkHashMap=(HashMap<Long, Landmark>)landmarkList.stream().collect(toMap(Landmark::getId,landmark -> landmark));
        CoordinateTrasformator.transformCoordinates(landmarkHashMap);
        FileReader.writeWkt("Taxi_81044.wkt",landmarkHashMap);*/




        /*List<Landmark> landmarkList=FileReader.readOsm("");
        FileReader.writeWkt("Taxi_01.wkt",landmarkList);*/


        //FileReader.findFilesInBounds();

        FileReader.readWkt();

    }
}
