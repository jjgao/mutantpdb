package mmtf.workshop.mutantpdb.io;

import mmtf.workshop.mutantpdb.utils.SaprkUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

/**
 * Created by Yana Valasatava on 6/28/17.
 */
public class DataProvider {

    public String getOncoKBFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResource("oncokb_variants_missense.txt").getFile();
    }

    public String getMutationsFileLocation() {
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResource("mutations").getPath();
    }

    public Dataset<Row> getOncoKBMutations() {

        Dataset<Row>  df = SaprkUtils.getSparkSession().read()
                .format("com.databricks.spark.csv")
                .option("delimiter", "\t")
                .option("header", "true")
                .load(getOncoKBFile())
                .select("Gene", "Uniprot", "Ref", "Position", "Varaint");
        return df;
    }

    public Dataset<Row> getHumanGenomeMapping() {
        Dataset<Row>  df = SaprkUtils.getSparkSession().read()
                .parquet(DataLocationProvider.getHumanGenomeMappingLocation());
        return df;
    }

    public Dataset<Row> getUniprotToPdbMapping() {
        Dataset<Row>  df = SaprkUtils.getSparkSession().read()
                .parquet(DataLocationProvider.getUniprotToPdbMappingLocation());
        return df;
    }

    public Dataset<Row> getMutationsToStructures() {

        DataProvider provider = new DataProvider();
        Dataset<Row>  df = SaprkUtils.getSparkSession().read()
                .parquet(provider.getMutationsFileLocation());
        return df;
    }

    public static void main(String[] args) {

        DataProvider provider = new DataProvider();
        System.out.println(provider.getMutationsFileLocation());

    }
}
