package services;

import com.google.inject.Inject;
import play.i18n.Lang;
import play.i18n.MessagesApi;

public class LanguageService {

    private String languageString = "de";
    private final MessagesApi messagesApi;

    @Inject
    public LanguageService(MessagesApi messagesApi) {
        this.messagesApi = messagesApi;
    }

    public void setLang(String languageString) {
        this.languageString = languageString;
    }

    public Lang getLang() {
        return Lang.forCode(languageString);
    }

    public String get(String key) {
        return messagesApi.get(getLang(), key);
    }
}
