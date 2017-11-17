package neo4j.services;

import com.typesafe.config.Config;
import org.apache.commons.io.FileUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Singleton
public class DataService {

    public enum Resource {
        USER_IMAGE,
        POST_IMAGE,
        EVENT_IMAGE,
        COMMENT_IMAGE
    }

    private Config config;

    private class ConfigHolder {
        String domain, homeDirectory, user, group;
        int permission;
        ResourceConfig resourceConfig;
    }

    private class ResourceConfig {
        String parentDirectory, subDirectory;
    }

    @Inject
    private DataService(Config config) {
        this.config = config;
    }

    private ConfigHolder getConfig(Resource resource) {

        String rootConfigPath = "serverFiles";

        ConfigHolder configHolder = new ConfigHolder();
        configHolder.domain = config.getString(rootConfigPath + ".domain");
        configHolder.homeDirectory = config.getString(rootConfigPath + ".homeDirectory");
        configHolder.user = config.getString(rootConfigPath + ".user");
        configHolder.group = config.getString(rootConfigPath + ".group");
        configHolder.permission = config.getInt(rootConfigPath + ".permission");

        ResourceConfig resourceConfig = new ResourceConfig();
        switch (resource) {
            case POST_IMAGE:
                resourceConfig.parentDirectory = config.getString(rootConfigPath + ".resources.post.directory");
                resourceConfig.subDirectory = config.getString(rootConfigPath + ".resources.post.image.directory");
                break;
            case EVENT_IMAGE:
                resourceConfig.parentDirectory = config.getString(rootConfigPath + ".resources.event.directory");
                resourceConfig.subDirectory = config.getString(rootConfigPath + ".resources.event.image.directory");
                break;
            case USER_IMAGE:
                resourceConfig.parentDirectory = config.getString(rootConfigPath + ".resources.user.directory");
                resourceConfig.subDirectory = config.getString(rootConfigPath + ".resources.user.image.directory");
                break;
            case COMMENT_IMAGE:
                resourceConfig.parentDirectory = config.getString(rootConfigPath + ".resources.comment.directory");
                resourceConfig.subDirectory = config.getString(rootConfigPath + ".resources.comment.image.directory");
                break;
        }
        configHolder.resourceConfig = resourceConfig;
        return configHolder;
    }

    public String move(File file, String extension, Resource resource) throws IOException {

        ConfigHolder configHolder = getConfig(resource);


        File destination = new File(configHolder.homeDirectory
                + "/"
                + configHolder.resourceConfig.parentDirectory
                + "/"
                + configHolder.resourceConfig.subDirectory, UUID.randomUUID() + "." + extension);

        FileUtils.moveFile(file, destination);

        Runtime.getRuntime().exec("chown -R " + configHolder.user + ":" + configHolder.group + " " + configHolder.homeDirectory + "/" + configHolder.resourceConfig.parentDirectory);
        //Runtime.getRuntime().exec("chown "+webUser+":"+webGroup+" "+ destination.getAbsolutePath());
        Runtime.getRuntime().exec("chmod " + configHolder.permission + " " + destination.getAbsolutePath());

        return configHolder.domain + "/" + configHolder.resourceConfig.parentDirectory + "/" + configHolder.resourceConfig.subDirectory + "/" + destination.getName();
    }

    public void deleteWithURL(String url, Resource resource) throws IOException {

        if (url.trim().isEmpty()) return;

        ConfigHolder configHolder = getConfig(resource);
        String leftUrl = configHolder.domain + "/" + configHolder.resourceConfig.parentDirectory + "/" + configHolder.resourceConfig.subDirectory + "/";
        String fileName = url.substring(leftUrl.length(), url.length());

        File file = new File(configHolder.homeDirectory
                + "/"
                + configHolder.resourceConfig.parentDirectory
                + "/"
                + configHolder.resourceConfig.subDirectory, fileName);

        if (file.exists()) {
            FileUtils.forceDelete(file);
        } else {
            System.out.println("File from " + url + ": " + file.getAbsolutePath() + " doesn't exist!");
        }
    }


}
