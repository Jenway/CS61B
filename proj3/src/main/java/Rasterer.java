import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {


    private static final double ROOT_LRLON = MapServer.ROOT_LRLON,
            ROOT_ULLON = MapServer.ROOT_ULLON,
            ROOT_LRLAT = MapServer.ROOT_LRLAT,
            ROOT_ULLAT = MapServer.ROOT_ULLAT;
    public static final int TILE_SIZE = MapServer.TILE_SIZE;
    private static final double[] DEPTH_LON_DPP = IntStream.range(0, 8)
            .mapToDouble(i -> ((ROOT_LRLON - ROOT_ULLON) / TILE_SIZE) / Math.pow(2, i))
            .toArray();

//    private static final double[] depthLonDPP = new double[8];
//    static {
//        depthLonDPP[0] = (ROOT_LRLON - ROOT_ULLON) / MapServer.TILE_SIZE;
//        for (int i = 1; i < 8; i++) {
//            depthLonDPP[i] = depthLonDPP[i - 1] / 2;
//        }
//    }
    public Rasterer() {

    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        double upperLeftLongitude = params.get("ullon");
        double lowerRightLongitude = params.get("lrlon");
        double upperLeftLatitude = params.get("ullat");
        double lowerRightLatitude = params.get("lrlat");
        if (upperLeftLongitude > ROOT_LRLON || lowerRightLongitude < ROOT_ULLON
                || upperLeftLatitude < ROOT_LRLAT || lowerRightLatitude > ROOT_ULLAT
                || upperLeftLongitude > lowerRightLongitude
                || upperLeftLatitude < lowerRightLatitude) {
            Map<String, Object> results = new HashMap<>();
            results.put("query_success", false);
            return results;
        }
        double requestedLonDPP = (lowerRightLongitude - upperLeftLongitude) / params.get("w");
        int depth = 0;
        while (requestedLonDPP < DEPTH_LON_DPP[depth]) {
            depth++;
            if (depth == DEPTH_LON_DPP.length - 1) {
                break;
            }
        }


        int xStart = 0, xEnd = 0, yStart = 0, yEnd = 0;
        double maxLevel = Math.pow(2, depth);
        double lonPerTile = (ROOT_LRLON - ROOT_ULLON) / maxLevel;
        double latPerTile = (ROOT_LRLAT - ROOT_ULLAT) / maxLevel;

        for (double x = ROOT_ULLON; x <= ROOT_LRLON; x += lonPerTile) {
            if (x <= upperLeftLongitude) {
                xStart++;
            }
            if (x < lowerRightLongitude) {
                xEnd++;
            }
        }

        for (double y = ROOT_ULLAT; y >= ROOT_LRLAT; y += latPerTile) {
            if (y >= upperLeftLatitude) {
                yStart++;
            }
            if (y > lowerRightLatitude) {
                yEnd++;
            }
        }

        if (xStart != 0) {
            xStart--;
        }
        if (yStart != 0) {
            yStart--;
        }
        if (xEnd != 0) {
            xEnd--;
        }
        if (yEnd != 0) {
            yEnd--;
        }


        String[][] renderGrid = new String[yEnd - yStart + 1][xEnd - xStart + 1];
        for (int i = 0; i < renderGrid.length; i++) {
            for (int j = 0; j < renderGrid[0].length; j++) {
                renderGrid[i][j] = "d" + depth + "_x" + (j + xStart) + "_y" + (i + yStart) + ".png";
            }
        }

        Map<String, Object> results = new HashMap<>();

        results.put("render_grid", renderGrid);
        results.put("raster_ul_lon", ROOT_ULLON + xStart * lonPerTile);
        results.put("raster_ul_lat", ROOT_ULLAT + yStart * latPerTile);
        results.put("raster_lr_lon", ROOT_ULLON + (xEnd + 1) * lonPerTile);
        results.put("raster_lr_lat", ROOT_ULLAT + (yEnd + 1) * latPerTile);
        results.put("depth", depth);
        results.put("query_success", true);

        return results;
    }

}
