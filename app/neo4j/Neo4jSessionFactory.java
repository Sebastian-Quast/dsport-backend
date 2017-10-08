package neo4j;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import com.typesafe.config.Config;

import javax.inject.Inject;
import java.util.List;


public class Neo4jSessionFactory {

    private Config config;

    @Inject
    private Neo4jSessionFactory(Config config) {
        this.config = config;
    }


    //TODO Sometimes cant load entity after some operations
    public Session getNeo4jSession() {
        String uri = config.getString("ogm.db.uri");
        String username = config.getString("ogm.db.username");
        String password = config.getString("ogm.db.password");
        List<String> modelList = config.getStringList("ogm.db.models");

        String[] models = modelList.toArray(new String[modelList.size()]);

        Configuration configuration = new Configuration.Builder()
                .uri(uri)
                .credentials(username, password)
                .build();
        return new SessionFactory(configuration, models).openSession();
    }
}
