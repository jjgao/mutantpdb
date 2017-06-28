package mmtf.workshop.mutantpdb;

import mmtf.workshop.mutantpdb.mmtf.workshop.mutantpdb.io.DataProvider;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

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

        Dataset<Row> uniprotToPdbMapping = provider.getUniprotToPdbMapping();
        
        mutations.show();
        uniprotToPdbMapping.show();

    }
}
