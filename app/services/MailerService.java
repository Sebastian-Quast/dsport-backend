package services;

import com.typesafe.config.Config;
import play.libs.mailer.Email;
import play.libs.mailer.MailerClient;

import javax.inject.Inject;
import java.time.LocalDate;

/**
 * Created by Florian on 20.09.2017.
 */
public class MailerService {

    private final MailerClient mailerClient;
    private final Config config;

    private static final String CFG_FROM="play.mailer.content.from";
    private static final String CFG_LOGO="play.mailer.content.logo";
    private static final String CFG_COPYRIGHT="play.mailer.content.copyright";
    private static final String CFG_BACKGROUND="play.mailer.content.background";

    @Inject
    public MailerService(Config config, MailerClient mailerClient){
        this.config = config;
        this.mailerClient = mailerClient;
    }

    public void send(String to, String title, String subject, String text){
        Email email = new Email()
                .setSubject(subject)
                .setFrom(config.getString(CFG_FROM))
                .addTo(to)
                .setBodyHtml(getHTMLBody(title, subject, text));
        mailerClient.send(email);
    }

    private String getHTMLBody(String title, String subject, String text){
        return "<html>" +
                "    <body style=\"margin: 0\">" +
                                getHTMLBackground() +
                                getHTMLHeader() +
                                getHTMLContent(title, subject, text) +
                                getHTMLFooter() +
                "    </body>" +
                "</html>";
    }

    private String getHTMLContent(String title, String subject, String text){
        return "<div style=\"background-color: rgba(255,255,255,0.7); width: 60%; margin-left:50%; transform: translate(-50%, 0); padding: 16px; border-radius: 4px\">" +
                "            <h2>" + title + "</h2>" +
                "            <strong>" + subject + "</strong>" +
                "            <p>" + text + "</p>" +
                "        </div>";
    }

    private String getHTMLHeader(){
        return "<img src=\"" + config.getString(CFG_LOGO) + "\" style=\"max-height: 200px; margin-left:50%; transform: translate(-50%, 0)\">";
    }

    private String getHTMLFooter(){
        return "<div style=\"width: 60%; margin-left:50%; transform: translate(-50%, 0); color: white; margin-top: 32px; text-align: center\">" +
                "            &copy; 2015-" + LocalDate.now().getYear() + config.getString(CFG_COPYRIGHT) +
                "        </div>";
    }

    private String getHTMLBackground(){
        return "<div style=\"position: absolute; background-image: url('" + config.getString(CFG_BACKGROUND) + "'); width: 100%; height: 100%; background-size: cover; z-index: -1\"></div>";
    }

}
