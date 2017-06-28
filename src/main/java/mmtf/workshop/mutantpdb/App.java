package mmtf.workshop.mutantpdb;

import mmtf.workshop.mutantpdb.mmtf.workshop.mutantpdb.io.DataProvider;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import edu.sdsc.mmtf.spark.io.MmtfReader;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        DataProvider provider = new DataProvider();
        Dataset<Row> mutations = provider.getOncoKBMutations();

        mutations.show();

    }
}
