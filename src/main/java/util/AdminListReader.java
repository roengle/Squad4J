package util;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import java.util.*;
import java.util.regex.*;

public class AdminListReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminListReader.class);

    public static final String groupRegex = "Group=(.*):([a-z]+)(,\\s*[a-z]+)*";
    public static final String adminRegex = "Admin=(\\d{17}):(\\s*\\w+)(?:.*)";

    public static final Pattern groupPattern = Pattern.compile(groupRegex);
    public static final Pattern adminPattern = Pattern.compile(adminRegex);

    private AdminListReader(){
        throw new IllegalStateException("Utility classes cannot be instantiated.");
    }

    public static List<String> getAdminIdsFromRemote(String url) throws IOException {
        //TODO: Test implementation
        URL u = new URL(url);
        try (InputStream in = u.openStream()) {
            String content = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            return parseContent(content);
        }
    }

    public static List<String> getAdminIdsFromLocal(String filePath) throws IOException {
        //TODO: Test implementation
        File file = new File(filePath);
        if(!file.exists())
            throw new FileNotFoundException("File does not exist.");

        InputStream is = new FileInputStream(file);
        String content = IOUtils.toString(is, StandardCharsets.UTF_8);

        return parseContent(content);
    }

    private static List<String> parseContent(String content){
        List<String> ids = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(content, "\n");
        while(st.hasMoreElements()){
            String token = st.nextToken();
            if(token == null)
                continue;
            if(token.matches(groupRegex)){
                Matcher matcher = groupPattern.matcher(token);
                matcher.find();
                String groupName = matcher.group(1);
                List<String> permissions = new ArrayList<>();
                permissions.add(matcher.group(2));
                if(matcher.group(3) != null){
                    permissions.addAll(
                            Arrays.asList(matcher.group(3).split(","))
                    );
                }
                //TODO: Improve implementation by including permissions with admin id
            }
            else if(token.matches(adminRegex)){
                Matcher matcher = adminPattern.matcher(token);
                matcher.find();
                String id = matcher.group(1);
                String group = matcher.group(2);
                ids.add(id);
            }
        }

        LOGGER.debug("Returning {} admins", ids.size());
        return ids;
    }
}
