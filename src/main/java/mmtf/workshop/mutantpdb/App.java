package mmtf.workshop.mutantpdb;

import edu.sdsc.mmtf.spark.io.MmtfReader;
import edu.sdsc.mmtf.spark.mappers.StructureToPolymerChains;
import mmtf.workshop.mutantpdb.utils.SaprkUtils;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.rcsb.mmtf.api.StructureDataInterface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        String path = System.getProperty("MMTF_REDUCED");
        if (path == null) {
            System.err.println("Environment variable for Hadoop sequence file has not been set");
            System.exit(-1);
        }

        //SparkConf conf = new SparkConf().setMaster("local[*]").setAppName(App.class.getSimpleName());
        //JavaSparkContext sc = new JavaSparkContext(conf);

        SparkSession ss = SaprkUtils.getSparkSession();

        Dataset<Row> mutations = ss.read().parquet("/home/strokach/working/mmtf-workshop/mutantpdb/target/classes/mutations");
        mutations.show();

        // Create a set of pdb ids (e.g. '1PES')
        List<String> pdb_ids = mutations.select("pdbId").distinct().map(new MapFunction<Row, String>() {
            public String call(Row row) {
                return row.getString(0);
            }
        }, Encoders.STRING()).collectAsList();
        System.out.println(pdb_ids);

        // Create a set of pdb chain ids (e.g. '1PES.C')
        HashSet<String> pdb_chains_ids = new HashSet<String>(mutations.select("pdbId", "chainId").distinct().map(new MapFunction<Row, String>() {
            public String call(Row row) {
                return row.getString(0) + "." + row.getString(1);
            }
        }, Encoders.STRING()).collectAsList());
        System.out.println(pdb_chains_ids);

        // Create a set of pdb chain ids (e.g. '1PES.C')
        HashSet<String> pdb_chains_residue_ids = new HashSet<String>(mutations.select("pdbId", "chainId", "Position").distinct().map(new MapFunction<Row, String>() {
            public String call(Row row) {
                return row.getString(0) + "." + row.getString(1) + "." + row.getString(2);
            }
        }, Encoders.STRING()).collectAsList());
        System.out.println(pdb_chains_residue_ids);

        SaprkUtils.stopSparkSession();
//        ss.close();

        JavaSparkContext sc = SaprkUtils.getSparkContext();

        JavaPairRDD<String, StructureDataInterface> chains = MmtfReader
                .readSequenceFile(path, pdb_ids, sc)
                .flatMapToPair(new StructureToPolymerChains())
                .filter(t -> pdb_chains_ids.contains(t._1));

        System.out.println(chains.count());

//        chains.foreach(data -> {
//            System.out.println("model="+data._1() + " label=" + data._2());
//        });

        sc.close();

    }
}
