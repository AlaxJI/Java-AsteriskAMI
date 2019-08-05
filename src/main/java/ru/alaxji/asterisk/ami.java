package ru.alaxji.asterisk;

import com.mkyong.hashing.PasswordMD5;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.alaxji.asterisk.AMI.AMI;
import ru.alaxji.asterisk.AMI.AMIData;
import ru.alaxji.asterisk.AMI.Actions.AuthTypes;
import ru.alaxji.asterisk.AMI.Actions.Challenge;
import ru.alaxji.asterisk.AMI.Actions.Login;
import ru.alaxji.asterisk.AMI.Interfaces.AMIActionListener;
import ru.alaxji.asterisk.AMI.Response;
import ru.alaxji.asterisk.AMI.event.ActionEvent;
import ru.alaxji.asterisk.AMI.event.Event;

public class ami {

    static boolean isChallengeResponse = false;

    public static void main(String[] args) throws Exception {
        String user = "wss";
        String secret = "SfBYWacNCngA5lpD";
        AMI ami = new AMI("10.1.1.15", 5038);
        //AMI ami = new AMI("10.1.1.15", 5038, "user", "secret");
        AMIActionListener amiListener;
        AMIActionListener amiActionListener = new AMIActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //CompletableFuture.runAsync(() -> {
                System.out.println("ACTION LISTENER");
                System.out.println((String) e.getArg());
                //});
            }
        };

        amiListener = new AMIActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //CompletableFuture.runAsync(() -> {
                if (e.getEventId() == Event.CONNECT) {
                    System.out.println("CONNECT");
                    System.out.println((String) e.getArg());
                }
                else if (e.getEventId() == Event.DISCONNECT) {
                    System.out.println("DISCONNECT");
                }
                /*
                    else if (e.getEventId() == Event.ACTION) {
                        System.out.println("ACTION");
                        System.out.println((String) e.getArg());
                    }
                 */
                else if (e.getEventId() == Event.EVENT) {
                    System.out.println("EVENT");
                    System.out.println((AMIData) e.getArg());
                }
                else if (e.getEventId() == Event.RESPONSE) {
                    System.out.println("RESPONSE");
                    System.out.println((AMIData) e.getArg());
                    AMIData amiData = (AMIData) e.getArg();
                    if (!isChallengeResponse && amiData.get("Response").equals("Success")) {
                        isChallengeResponse = true;
                        String challenge = amiData.get("Challenge");
                        //ami.setChallenge(challenge);
                        //ami.Login();
                    }
                }
                else if (e.getEventId() == Event.ERROR) {
                    System.out.println("ERROR");
                    System.out.println(e.getArg());
                }
                //});
            }
        };

        //ami.addActionListener(Event.ACTION, amiActionListener);
        ami.addActionListener(amiListener, true);
        ami.connectAndRunReader();

        Response response = new Challenge(ami, AuthTypes.MD5).execute();
        System.out.println(response.getAMIData());
        if (response.getCode() == 0) {
            AMIData challengeAMIData = response.getAMIData();
            if (challengeAMIData.get("Response").equals("Success")){
                String challenge = challengeAMIData.get("Challenge");
                response = new Login(ami,  user,  secret, AuthTypes.MD5,  challenge).execute();
                System.out.println(response.getAMIData());
            }
            //if (challengeAMIData.)
        }
        else {
        }
        //ami.addActionListener(Event.ACTION,amiActionListener2);
        //ami.addActionListener(amiActionListener3);
        //ami.addActionListener(amiActionListener1_1,true);
        //ami.addActionListener(amiActionListener2_1,true);
        //ami.addActionListener(amiActionListener3_1,true);
        //ami.connect();
        //ami.Challenge();
        // while (isChallengeResponse) //System.out.println( ch.actionName );
        //System.out.println("wait for Action:"+(1 << 3));
        //{
        {
           // Thread.sleep(10000);
        }
        //}
        ami.close();

    }
}
