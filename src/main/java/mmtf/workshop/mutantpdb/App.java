package mmtf.workshop.mutantpdb;

import mmtf.workshop.mutantpdb.mappers.AddrResidueToKey;
import mmtf.workshop.mutantpdb.utils.SaprkUtils;
import edu.sdsc.mmtf.spark.mappers.StructureToPolymerChains;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import edu.sdsc.mmtf.spark.io.MmtfReader;
import org.rcsb.mmtf.api.StructureDataInterface;

import java.util.Arrays;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        String path = System.getProperty("MMTF_REDUCED");
        if (path == null) {
            System.err.println("Environment variable for Hadoop sequence file has not been set");
            System.exit(-1);
        }

       //SparkConf conf = new SparkConf().setMaster("local[*]").setAppName(App.class.getSimpleName());
       //JavaSparkContext sc = new JavaSparkContext(conf);

        SaprkUtils.stopSparkSession();

        JavaSparkContext sc = SaprkUtils.getSparkContext();

        chains = MmtfReader
                .downloadMmtfFiles(Arrays.asList("4hhb"), sc)
                .flatMapToPair(new StructureToPolymerChains())
                .flatMapToPair(new AddrResidueToKey());
                //.mapToPair(new FilterResidue());
//        chains.foreach(data -> {
//            System.out.println("model="+data._1() + " label=" + data._2());
//        });

    sc.close();
    }
}
