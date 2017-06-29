package mmtf.workshop.mutantpdb;

import mmtf.workshop.mutantpdb.io.DataProvider;
import mmtf.workshop.mutantpdb.utils.SaprkUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.not;

/**
 * Created by Yana Valasatava on 6/28/17.
 */
public class App2 {
    public static void main( String[] args )
    {
        Dataset<Row> data = SaprkUtils.getSparkSession().read()
                .format("com.databricks.spark.csv")
                .option("delimiter", ",")
                .option("header", "false")
                .load("/Users/yana/git/mutantpdb/src/main/resources/pdb_residues");
        //data.show();

        Dataset<Row> mutations = DataProvider.getMutationsToStructures();
        //mutations.show();

        Dataset<Row> result = data.join(mutations, data.col("_c0").equalTo(mutations.col("pdbId"))
                .and(data.col("_c1").equalTo(mutations.col("chainId")))
                .and(data.col("_c2").equalTo(mutations.col("pdbAtomPos"))))
                .drop(data.col("_c0")).drop(data.col("_c1")).drop(data.col("_c2"))
                .withColumnRenamed("_c3", "pdbRef");

        result
                .filter(not(col("pdbRef").equalTo(col("Ref"))))
                .show(100);
    }
}
