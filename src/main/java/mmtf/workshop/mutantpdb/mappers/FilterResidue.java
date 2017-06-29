package mmtf.workshop.mutantpdb.mappers;

import org.apache.spark.api.java.function.PairFunction;
import org.biojava.nbio.structure.*;
import org.rcsb.mmtf.api.StructureDataInterface;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Yana Valasatava on 6/28/17.
 */
public class FilterResidue implements PairFunction<Tuple2<String, Structure>, String, String> {
    @Override
    public Tuple2<String, String> call(Tuple2<String, Structure> t) throws Exception {

        String pdbId = t._1.split(Pattern.quote("."))[0];
        String chainId = t._1.split(Pattern.quote("."))[1];
        int resNum = Integer.valueOf(t._1.split(Pattern.quote("."))[2]);

        try {
            for ( Chain chain : t._2.getChains()) {
                if ( !chain.getName().equals(chainId))
                    continue;
                List<Group> groups = chain.getAtomGroups();
                for (Group g : groups) {
                    if ( resNum == g.getResidueNumber().getSeqNum() ) {
                        Character aa = StructureTools.get1LetterCodeAmino(g.getPDBName());
                        return new Tuple2<String, String>(t._1+"."+aa.toString(), aa.toString());
                    }
                }
            }
        } catch (Exception e) {
            return new Tuple2<String, String>(t._1, null);
        }
        return new Tuple2<String, String>(t._1, null);
    }
}
