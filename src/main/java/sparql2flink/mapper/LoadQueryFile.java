package sparql2flink.mapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
public class LoadQueryFile {

    private String queryFile;

    public class LoadQueryFile {
        private final FileSystem fs;
        private final Path path;

        public LoadQueryFile(FileSystem fs, Path path) {
            this.fs = fs;
            this.path = path;
        }

        public String loadSQFile() throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fs.open(path)));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        }
    }