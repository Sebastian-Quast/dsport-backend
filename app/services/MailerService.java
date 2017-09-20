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

    /**
     * Sends an Email to the "to" Email. Configurations are taken from the application.conf.
     * Example:
     *
     * play.mailer {
     *      host = "www.example.de" // (mandatory)
     *      port = 25 // (defaults to 25)
     *      ssl = yes // (defaults to no)
     *      tls = yes // (defaults to no)
     *      tlsRequired = no // (defaults to no)
     *      user = null // (optional)
     *      password = null // (optional)
     *      debug = no // (defaults to no, to take effect you also need to set the log level to "DEBUG" for the application logger)
     *      timeout = null // (defaults to 60s in milliseconds)
     *      connectiontimeout = null // (defaults to 60s in milliseconds)
     *      mock = no // (defaults to no, will only log all the email properties instead of sending an email)
     *
     *      content {
     *          from="noreply@example.de"
     *          copyright="www.example.de"
     *          background="https://example.de/path/to/example.jpg"
     *          logo="https://example.de/path/to/example.svg"
     *      }
     * }
     *
     * @param to    Email of the recipient
     * @param title Title of the Email
     * @param subject   Subject of the Email
     * @param text  Text to Send
     */
    public void send(String to, String title, String subject, String text){
        Email email = new Email()
                .setSubject(subject)
                .setFrom(config.getString(CFG_FROM))
                .addTo(to)
                .setBodyHtml(getHTMLBody(title, subject, text));
        mailerClient.send(email);
    }

    /**
     * Build HTML Body. Combine Background, Header, Content and Footer to one HTML file. Inserts it to a Body and a HTML-Tag.
     * @param title Title of the Email
     * @param subject   Subject of the Email
     * @param text  Text of the Email
     * @return  Ready to send HTML-Email Body with Background-Image, Header, Footer and Content.
     */
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

    /**
     * Builds a completely styled HTML-Content with Title, Subject and Text.
     * @param title Title of the Email
     * @param subject   Subject of the Email
     * @param text  Text to send
     * @return  Completely styled HTML-Content with Title, Subject and Text
     */
    private String getHTMLContent(String title, String subject, String text){
        return "<div style=\"background-color: rgba(255,255,255,0.7); width: 60%; margin-left:50%; transform: translate(-50%, 0); padding: 16px; border-radius: 4px\">" +
                "            <h2>" + title + "</h2>" +
                "            <strong>" + subject + "</strong>" +
                "            <p>" + text + "</p>" +
                "        </div>";
    }

    /**
     * Builds a completely styled Header with Logo. URL to the Logo is taken from application.conf
     * @return Completely styled HTML-Header
     */
    private String getHTMLHeader(){
        return "<img src=\"" + config.getString(CFG_LOGO) + "\" style=\"max-height: 200px; margin-left:50%; transform: translate(-50%, 0)\">";
    }

    /**
     * Builds a completely styled Footer with copyright. Copyright Owner is taken from application.conf
     * @return Completely styled HTML-Footer
     */
    private String getHTMLFooter(){
        return "<div style=\"width: 60%; margin-left:50%; transform: translate(-50%, 0); color: white; margin-top: 32px; text-align: center\">" +
                "            &copy; 2015-" + LocalDate.now().getYear() + config.getString(CFG_COPYRIGHT) +
                "        </div>";
    }

    /**
     * Build a absolute Background for the Email. Background Image-URL is taken from application.conf
     * @return Completely styled HTML-Background
     */
    private String getHTMLBackground(){
        return "<div style=\"position: absolute; background-image: url('" + config.getString(CFG_BACKGROUND) + "'); width: 100%; height: 100%; background-size: cover; z-index: -1\"></div>";
    }

}
