
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.jena.sparql.algebra.Op;
import sparql2flink.mapper.CreateFlinkProgram;
import sparql2flink.mapper.LoadQueryFile;
import sparql2flink.mapper.LogicalQueryPlan2FlinkProgram;
import sparql2flink.mapper.Query2LogicalQueryPlan;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class SPARQL2Flink {

	public static void main(String[] args) throws Exception {
	    Path path=null;

        if (args != null && args.length == 1) {
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.get(conf);
            Path path = new Path(args[0]);
        } else {
            System.out.println("\nYou should to specify path query file argument.\nFor example: path_query_file/query_file.rq\n"+
                    "\nExecuting sample with default SPARQL query saved in << examples >> directory");
            path = Paths.get("./examples/query.rq");
        }

        LLoadQueryFile queryFile = new LoadQueryFile(fs.open(path));
        String queryString = queryFile.loadSQFile();

        Query2LogicalQueryPlan query2LQP = new Query2LogicalQueryPlan(queryString);
        Op logicalQueryPlan = query2LQP.translationSQ2LQP();

        LogicalQueryPlan2FlinkProgram lQP2FlinkProgram = new LogicalQueryPlan2FlinkProgram(logicalQueryPlan, path);
        String flinkProgram = lQP2FlinkProgram.logicalQueryPlan2FlinkProgram();

        CreateFlinkProgram javaFlinkProgram = new CreateFlinkProgram(flinkProgram, path);
        javaFlinkProgram.createFlinkProgram();

	}
}
