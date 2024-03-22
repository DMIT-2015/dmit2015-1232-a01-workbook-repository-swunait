package dmit2015.batch;

import jakarta.batch.api.AbstractBatchlet;
import jakarta.batch.api.BatchProperty;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Objects;

@Named
@Dependent
public class ExecuteSingleLineSQLStatementBatchlet extends AbstractBatchlet {

    @PersistenceContext(unitName = "mssql-dwpubsales-jpa-pu")
    private EntityManager _entityManager;

    @Inject
    @BatchProperty(name = "sql_script_file")
    private String sqlScriptFile;
    @Transactional
    @Override
    public String process() throws Exception {
        // For reading external files outside of the project use the code below:
//        try (BufferedReader reader = new BufferedReader(new FileReader(Paths.get(inputFile).toFile())))	{
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getResourceAsStream(sqlScriptFile)))))	{
            String line;
            while ((line = reader.readLine()) != null) {
                _entityManager.createNativeQuery(line).executeUpdate();
            }
        }

        return "COMPLETED";     // The job has successfully completed
    }
}
