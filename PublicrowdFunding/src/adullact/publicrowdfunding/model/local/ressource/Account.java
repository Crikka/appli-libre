package adullact.publicrowdfunding.model.local.ressource;

import java.util.ArrayList;
import java.util.Map;

import rx.Observable;
import adullact.publicrowdfunding.PublicrowdFundingApplication;
import adullact.publicrowdfunding.model.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.cache.Local;
import adullact.publicrowdfunding.model.local.callback.HoldToDo;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.server.entities.RowAffected;
import adullact.publicrowdfunding.model.server.entities.ServerAccount;
import adullact.publicrowdfunding.model.server.entities.Service;
import adullact.publicrowdfunding.model.server.entities.SimpleServerResponse;
import android.content.SharedPreferences;

/**
 * @author Ferrand and Nelaupe
 */
public class Account extends Resource<Account, ServerAccount, ServerAccount> {
    /* ---- Singleton ---- */
    private static Account m_own = null;
    private void initialize() throws NoAccountExistsInLocal {
        SharedPreferences sharedPreferences = PublicrowdFundingApplication.sharedPreferences();
        if(!sharedPreferences.contains(KEY_USERNAME) || !sharedPreferences.contains(KEY_PASSWORD) || !sharedPreferences.contains(KEY_PSEUDO) || !sharedPreferences.contains(KEY_ADMIN)) {
                m_own = null;
                throw new NoAccountExistsInLocal();
        }

        m_username = sharedPreferences.getString(KEY_USERNAME, "");
        m_password = sharedPreferences.getString(KEY_PASSWORD, "");
        m_administrator = sharedPreferences.getBoolean(KEY_ADMIN, false);

        User user = new User();
        user.setResourceId(sharedPreferences.getString(KEY_PSEUDO, ""));
        m_user = new Local<User>(user).useIt();

        new Local<Account>(this).useIt();
    }

    public static Account getOwn() throws NoAccountExistsInLocal {
        if(m_own == null) {
            try {
                m_own = new Account();
                m_own.initialize();
            }
            catch(NoAccountExistsInLocal exception) {
                m_own = null; // Set to null to keep own account null
                throw exception;
            }
        }

        return m_own;
    }

    public static Account getOwnOrAnonymous() {
        if(m_own == null) {
            return (new Account());
        }

        return m_own;
    }
    /* ------------------- */

    /* --- Static const to store --- */
    private static final String KEY_USERNAME = "name";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ADMIN = "admin";
    private static final String KEY_PSEUDO = "pseudo";
    /* ----------------------------- */

    /* ----- Resource ----- */
    @Override
    public String getResourceId() {
        return m_username;
    }

    @Override
    public void setResourceId(String id) {
        this.m_username = id;
    }

    @Override
    public ServerAccount toServerResource() {
        ServerAccount res = new ServerAccount();
        res.username = m_username;
        res.password = m_password;
        res.administrator = m_administrator ? 1 : 0;
        res.pseudo = m_user.getResourceId();
        return res;
    }

    @Override
    public Account makeCopyFromServer(ServerAccount serverAccount) {
        Account res = new Account();
        res.m_username = serverAccount.username;
        res.m_administrator = (serverAccount.administrator == 1);
        res.m_anonymous = false;
        res.m_user = new User().getCache(serverAccount.pseudo);

        return res;
    }

    @Override
    public Account syncFromServer(ServerAccount serverAccount) {
        this.m_username = serverAccount.username;
        this.m_administrator = (serverAccount.administrator == 1);
        this.m_anonymous = false;
        this.m_user = new User().getCache(serverAccount.pseudo);

        return this;
    }

    @Override
    public Observable<ServerAccount> methodGET(Service service) {
        return service.detailAccount(getResourceId());
    }

    @Override
    public Observable<SimpleServerResponse> methodPUT(Service service) {
        return service.modifyAccount(toServerResource(), getResourceId());
    }

    @Override
    public Observable<RowAffected> methodPOST(Service service) {
        return service.createAccount(toServerResource());
    }

    @Override
    public Observable<SimpleServerResponse> methodDELETE(Service service) {
        return service.deleteAccount(getResourceId());
    }

    @Override
    public Observable<ArrayList<ServerAccount>> methodGETAll(Service service, Map<String,String> filter) {
        return service.listAccount(filter);
    }
    /* -------------------- */

    /* ---- Own data ---- */
    private String m_username;
    private String m_password;
    private boolean m_administrator;
    private boolean m_anonymous;
   /* ------------------ */

    /* --- References --- */
    private Cache<User> m_user;
    /* ------------------ */

    public Account() {
        this.m_username = null;
        this.m_password = null;
        this.m_administrator = false;
        this.m_anonymous = true;
    }

    public Account(String username, String password, String pseudo) {
        this.m_username = username;
        this.m_password = password;
        this.m_user = new User().getCache(pseudo);
        this.m_administrator = false;
        this.m_anonymous = false;
    }

    public void setOwn() {
        m_own = this;
        save();
    }

    public boolean isAdmin() {
        return m_administrator;
    }

    public String getUsername() {
        return m_username;
    }

    public String getPassword() {
        return m_password;
    }

    public String getPseudo() {
        return m_user.getResourceId();
    }

    public void getUser(WhatToDo<User> userWhatToDo) {
        m_user.toResource(userWhatToDo);
    }

    public Cache<User> getUser() {
        return m_user;
    }

    public void setAdmin(){
        m_administrator = true;
    }

    public static boolean isConnect(){
        return (m_own != null);
    }

    public static boolean autoConnect() {
        try {
            Account.getOwn();
            return true;
        } catch (NoAccountExistsInLocal noAccountExistsInLocal) {
            return false;
        }
    }

    public static void disconnect(){
        Account own = m_own;
        m_own = null;

        SharedPreferences.Editor editor = PublicrowdFundingApplication.sharedPreferences().edit();

        editor.remove(KEY_USERNAME);
        editor.remove(KEY_PASSWORD);
        editor.remove(KEY_PSEUDO);
        editor.remove(KEY_ADMIN);
        editor.apply();

        own.getUser(new HoldToDo<User>() {
            @Override
            public void hold(User user) {
                new Cache<User>(user).declareUpToDate().useIt();
            }
        });
        new Cache<Account>(own).declareUpToDate().useIt();
    }

    private void save() {
        SharedPreferences.Editor editor = PublicrowdFundingApplication.sharedPreferences().edit();

        editor.putString(KEY_USERNAME, m_username);
        editor.putString(KEY_PASSWORD,  m_password);
        editor.putString(KEY_PSEUDO, m_user.getResourceId());
        editor.putBoolean(KEY_ADMIN, m_administrator);

        editor.apply();
    }
}
