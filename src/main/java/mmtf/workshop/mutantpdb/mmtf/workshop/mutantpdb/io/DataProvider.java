package mmtf.workshop.mutantpdb.mmtf.workshop.mutantpdb.io;

import mmtf.workshop.mutantpdb.mmtf.workshop.mutantpdb.utils.SaprkUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

/**
 * Created by Yana Valasatava on 6/28/17.
 */
public class DataProvider {

    private String getOncoKBFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResource("oncokb_variants_missense.txt").getFile();
    }

    public Dataset<Row> getOncoKBMutations() {

        Dataset<Row>  df = SaprkUtils.getSparkSession().read()
                .format("com.databricks.spark.csv")
                .option("delimiter", "\t")
                .option("header", "true")
                .load(getOncoKBFile());
        return df;
    }

}