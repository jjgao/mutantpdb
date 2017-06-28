package mmtf.workshop.mutantpdb;

import mmtf.workshop.mutantpdb.io.DataLocationProvider;
import mmtf.workshop.mutantpdb.io.DataProvider;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;

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
        // Uniprot to PDB mapping
        Dataset<Row> map = provider.getUniprotToPdbMapping();

        Dataset<Row> mutationsOnStructures = mutations.join(map, mutations.col("Uniprot")
                .equalTo(map.col("uniProtId"))
                .and(mutations.col("Position").equalTo(map.col("uniProtPos"))))
                .drop(map.col("uniProtId")).drop(map.col("uniProtPos"))
                .orderBy(mutations.col("Uniprot"));

        mutationsOnStructures.write()
                .mode(SaveMode.Overwrite)
                .parquet(DataLocationProvider.getMutationsFileLocation());

    }
}
