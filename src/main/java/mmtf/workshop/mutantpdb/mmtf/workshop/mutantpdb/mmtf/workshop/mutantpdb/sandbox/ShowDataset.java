package mmtf.workshop.mutantpdb.mmtf.workshop.mutantpdb.mmtf.workshop.mutantpdb.sandbox;

import mmtf.workshop.mutantpdb.mmtf.workshop.mutantpdb.io.DataLocationProvider;
import mmtf.workshop.mutantpdb.mmtf.workshop.mutantpdb.io.DataProvider;
import mmtf.workshop.mutantpdb.mmtf.workshop.mutantpdb.utils.SaprkUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import static org.apache.spark.sql.functions.col;

/**
 * Created by Yana Valasatava on 6/28/17.
 */
public class ShowDataset {

    public static void main(String[] args) {

        DataProvider provider = new DataProvider();

        Dataset<Row> df = provider.getMutationsToStructures();
        df.show();

        System.out.println(df.select(col("Uniprot")).distinct().count());
    }
}
