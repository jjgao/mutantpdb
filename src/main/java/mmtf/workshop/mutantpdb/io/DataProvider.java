package mmtf.workshop.mutantpdb.io;

import mmtf.workshop.mutantpdb.utils.SaprkUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

/**
 * Created by Yana Valasatava on 6/28/17.
 */
public class DataProvider {

    public Dataset<Row> getOncoKBMutations() {

        Dataset<Row>  df = SaprkUtils.getSparkSession().read()
                .format("com.databricks.spark.csv")
                .option("delimiter", "\t")
                .option("header", "true")
                .load(DataLocationProvider.getOncoKBFileLocation())
                .select("Gene", "Uniprot", "Ref", "Position", "Varaint");
        return df;
    }

    public Dataset<Row> getUniprotToPdbMapping() {
        Dataset<Row>  df = SaprkUtils.getSparkSession().read()
                .parquet(DataLocationProvider.getUniprotToPdbMappingLocation());
        return df;
    }

    public Dataset<Row> getMutationsToStructures() {

        Dataset<Row>  df = SaprkUtils.getSparkSession().read()
                .parquet(DataLocationProvider.getMutationsFileLocation());
        return df;
    }

    public static void main(String[] args) {
        
        System.out.println();

    }
}
