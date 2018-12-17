package toan.zpx;

import org.redisson.Redisson;
import org.redisson.api.RMapCache;
import org.redisson.api.RSet;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class App 
{
    static RedissonClient redisson;
    static RTopic<String> topic;
    static RSet<String> users;
    static ChatForm chatForm;

    public static void main( String[] args )
    {
        redisson = Redisson.create();

        users = redisson.getSet("users");

        topic = redisson.getTopic("chatApp");
        topic.addListener(new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence charSequence, String s) {
                getMsg(s);
            }
        });

        chatForm = new ChatForm();

        getAllMsg();


    }

    private static void getAllMsg() {
        Set<String> usersList = users.readAll();
        for (String user : usersList ) {
            RMapCache<String, String> userMsg = redisson.getMapCache(user);
            Collection<String> allUserMsg = userMsg.readAllValues();
            for (String str : allUserMsg) {
                chatForm.appendMessage("<" + user + "> " + str);
            }
        }
    }

    public static boolean addUser(String username) {
        return users.add(username);
    }
    public static void getMsg(String str) {
        chatForm.appendMessage(str);
    }

    public static void addhMsg(String username, String msg) {
        topic.publish("<" + username + "> " + msg);

        RMapCache<String, String> userMsg = redisson.getMapCache(username);

        userMsg.put(String.valueOf((new Date().getTime())), msg, 1, TimeUnit.DAYS);
    }

    public static void shutdow() {
        redisson.shutdown();
    }



}
