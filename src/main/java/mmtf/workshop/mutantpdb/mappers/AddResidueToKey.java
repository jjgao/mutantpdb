package mmtf.workshop.mutantpdb.mappers;

import mmtf.workshop.mutantpdb.utils.SaprkUtils;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.rcsb.mmtf.api.StructureDataInterface;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.apache.spark.sql.functions.col;

/**
 * Created by strokach on 28/06/17.
 */
public class AddResidueToKey implements PairFlatMapFunction<
        Tuple2<String, StructureDataInterface>, String, StructureDataInterface> {

    Broadcast<List<Row>> mutations;

    public AddResidueToKey(Broadcast<List<Row>> mutations) {
        this.mutations = mutations;
    }

    public Iterator<Tuple2<String, StructureDataInterface>>
    call(Tuple2<String, StructureDataInterface> t) throws Exception {

        String pdbId = t._1.split(Pattern.quote("."))[0];
        String chainId = t._1.split(Pattern.quote("."))[1];

        List<Tuple2<String, StructureDataInterface>> result = new ArrayList<Tuple2<String, StructureDataInterface>>();

        List<Row> df = mutations.getValue();
        List<Row> positions = df.stream().filter(m -> (m.getString(0).equals(pdbId) && m.getString(1).equals(chainId)))
                .distinct()
                .collect(Collectors.toList());

        for (Row row : positions) {
            result.add(new Tuple2<String, StructureDataInterface>(t._1+"."+row.getInt(2), t._2));
        }
        return result.iterator();
    }
}
