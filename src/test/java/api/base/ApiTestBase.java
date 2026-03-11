package api.base;

import api.methods.ApiObject;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT)
public class ApiTestBase {
    protected ApiObject api;

    @BeforeEach
    public void setUp() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String apiKey = dotenv.get("REQRES_API_KEY");
        if (apiKey == null) {
            throw new RuntimeException("API key not set in .env");
        }
        api = new ApiObject(apiKey);
    }
}