package Grant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;


public class Database<User extends ObjEncodable>
{
    private static final MessageDigest globalHasher;

    static {
        try {
            globalHasher = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private final HashMap<UserLoginInfo,User> loadedUsers;
    private final Path databaseFolder;

    public Database(String databaseFolder) {
        this.loadedUsers = new HashMap<>();
        this.databaseFolder = Paths.get(databaseFolder);
    }

    public User login(String username, String password)
    {
        return loadedUsers.get(new UserLoginInfo(username,password));
    }

    public void addUser(User object, String username, String password)
    {
        loadedUsers.put(new UserLoginInfo(username,password),object);
    }

    public void saveUsers() throws FileNotFoundException
    {
        for(Entry<UserLoginInfo,User> entry : loadedUsers.entrySet())
        {
            saveEntry(entry);
        }
    }
    public void saveUser(User object) throws FileNotFoundException
    {

        for(Entry<UserLoginInfo,User> entry : loadedUsers.entrySet())
        {
            if(entry.getValue().equals(object))
            {
                saveEntry(entry);
                return;
            }

        }
    }

    public String getUsername(User object)
    {

        for(Entry<UserLoginInfo,User> entry : loadedUsers.entrySet())
        {
            if(entry.getValue().equals(object))
            {
                return entry.getKey().getUsername();
            }
        }
        return null;
    }
    public <M extends ObjDecoder<User>> User loadUser(String fileName, M userDecoder) throws FileNotFoundException
    {
        File filePath = databaseFolder.resolve(fileName).toFile();
        User user;
        UserLoginInfo login;
        try(Scanner scan = new Scanner(fileName))
        {
            String[] userdata = scan.nextLine().split("_NEXT_");
            login = (new UserLoginInfo.UserLoginInfoDecoder()).deserialize(userdata[0]);
            user = userDecoder.deserialize(userdata[1]);
        }
        loadedUsers.put(login,user);
        return user;
    }
    public <M extends ObjDecoder<User>> void loadAllUsers(M userDecoder) throws FileNotFoundException
    {
        File[] filesAvail = databaseFolder.toFile().listFiles();
        if(filesAvail==null)
            throw(new RuntimeException("Files list is null"));
        for(File f : filesAvail)
        {
            if(f.toString().endsWith(".rec"))
            {
                loadUser(f.toString(),userDecoder);
            }
        }
    }
    private void saveEntry(Entry<UserLoginInfo,User> entry) throws FileNotFoundException
    {
        File filePath = databaseFolder.resolve(entry.getKey()+".rec").toFile();
        try(PrintWriter userWriter = new PrintWriter(filePath))
        {
            userWriter.println(String.format("%s_NEXT_%s",entry.getKey().serialize(),entry.getValue().serialize()));
        }
    }

    private static class UserLoginInfo implements ObjEncodable
    {
        private final String username;
        private final byte[] passHash;

        public UserLoginInfo(String username, String password)
        {
            this.username = username;
            this.passHash = globalHasher.digest(password.getBytes());
        }

        public boolean equals(UserLoginInfo o)
        {
            return o.username.equals(username) && Arrays.equals(passHash,o.passHash);
        }

        public String getUsername()
        {
            return username;
        }

        public String getPassHash()
        {
            return new String(passHash, StandardCharsets.UTF_8);
        }


        public int hashCode()
        {
            return username.hashCode() + Arrays.hashCode(passHash);
        }

        public String serialize()
        {
            return String.format("username:%s,passHash:%s",getUsername(),getPassHash());
        }


        public static class UserLoginInfoDecoder implements ObjDecoder<UserLoginInfo>
        {
            public UserLoginInfo deserialize(String data)
            {
                String username=null;
                String passHash=null;

                for(String inst : data.split(","))
                {
                    String[] split = inst.split(":");
                    if(split[0].equals("username"))
                    {
                        username = split[1];
                    }
                    else if(split[0].equals("passHash"))
                    {
                        passHash = split[1];
                    }
                }
                if(username==null||passHash==null)
                {
                    throw(new IllegalArgumentException("Data does not contain all parameters"));
                }
                return new UserLoginInfo(username,passHash);
            }
        }
    }
}
