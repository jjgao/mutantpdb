package mmtf.workshop.mutantpdb.io;

/**
 * Created by Yana Valasatava on 6/28/17.
 */
public class DataLocationProvider {

    private final static String userHome = System.getProperty("user.home");

    private static String uniprotToPdbMappingLocation = getUserHome() + "/spark/parquet/uniprot-pdb/20161104/";
    private static String mutationsMappingLocation = getUserHome() + "/spark/parquet/mutations";

    public static String getUserHome() {
        return userHome;
    }

    public static String getUniprotToPdbMappingLocation() {
        return uniprotToPdbMappingLocation;
    }

    public static String getMutationsFileLocation() {
        return DataLocationProvider.class.getResource("mutations").getPath();
    }

    public static String getOncoKBFileLocation() {
        return DataLocationProvider.class.getResource("oncokb_variants_missense.txt").getFile();
    }
}
