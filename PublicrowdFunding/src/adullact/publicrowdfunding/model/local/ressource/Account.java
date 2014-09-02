package adullact.publicrowdfunding.model.local.ressource;

import android.content.Context;
import android.content.SharedPreferences;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import adullact.publicrowdfunding.PublicrowdFundingApplication;
import adullact.publicrowdfunding.model.exception.NoAccountExistsInLocal;
import adullact.publicrowdfunding.model.local.cache.Cache;
import adullact.publicrowdfunding.model.local.callback.WhatToDo;
import adullact.publicrowdfunding.model.server.entities.RowAffected;
import adullact.publicrowdfunding.model.server.entities.ServerAccount;
import adullact.publicrowdfunding.model.server.entities.Service;
import adullact.publicrowdfunding.model.server.entities.SimpleServerResponse;
import rx.Observable;

/**
 * Created by Ferrand on 16/07/2014.
 */
public class Account extends Resource<Account, ServerAccount, ServerAccount> {
    /* ---- Singleton ---- */
    private static Account m_own = null;
    private void initialize() throws NoAccountExistsInLocal {
        SharedPreferences sharedPreferences = PublicrowdFundingApplication.sharedPreferences();//m_context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if(!sharedPreferences.contains(KEY_USERNAME) || !sharedPreferences.contains(KEY_PASSWORD) || !sharedPreferences.contains(KEY_PSEUDO) || !sharedPreferences.contains(KEY_ADMIN)) {
                m_own = null;
                throw new NoAccountExistsInLocal();
        }

        m_username = sharedPreferences.getString(KEY_USERNAME, "");
        m_password = decrypt("mystery", sharedPreferences.getString(KEY_PASSWORD, ""));
        m_administrator = sharedPreferences.getBoolean(KEY_ADMIN, false);
        m_user = new User().getCache(sharedPreferences.getString(KEY_PSEUDO, ""));
    }

    public static Account getOwn() throws NoAccountExistsInLocal {
        if(m_own == null) {
            try {
                m_own = new Account(PublicrowdFundingApplication.context());
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
    private Account(Context context) {
        this.m_context = context;
    }
    /* ------------------- */

    /* --- Static const to store --- */
    private static final String KEY_USERNAME = "name";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ADMIN = "password";
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
        res.administrator = m_administrator;
        res.pseudo = m_user.getResourceId();
        return res;
    }

    @Override
    public Account makeCopyFromServer(ServerAccount serverAccount) {
        Account res = new Account();
        res.m_username = serverAccount.username;
        res.m_administrator = serverAccount.administrator;
        res.m_anonymous = false;
        res.m_context = PublicrowdFundingApplication.context();
        res.m_user = new User().getCache(serverAccount.pseudo);

        return res;
    }

    @Override
    public Account syncFromServer(ServerAccount serverAccount) {
        this.m_username = serverAccount.username;
        this.m_administrator = serverAccount.administrator;
        this.m_anonymous = false;
        this.m_context = PublicrowdFundingApplication.context();
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
    private Context m_context;
   /* ------------------ */

    /* --- References --- */
    private Cache<User> m_user;
    /* ------------------ */

    public Account() {
        this.m_username = null;
        this.m_password = null;
        this.m_administrator = false;
        this.m_anonymous = true;
        this.m_context = null;
    }

    public Account(String username, String password, String pseudo) {
        this.m_username = username;
        this.m_password = password;
        this.m_user = new User().getCache(pseudo);
        this.m_administrator = false;
        this.m_anonymous = false;
        this.m_context = null;
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
        m_own = null;

        SharedPreferences.Editor editor = PublicrowdFundingApplication.sharedPreferences().edit();

        editor.remove(KEY_USERNAME);
        editor.remove(KEY_PASSWORD);
        editor.remove(KEY_PSEUDO);
        editor.remove(KEY_ADMIN);
        editor.apply();
    }
    
    

    

    private void save() {
        SharedPreferences.Editor editor = PublicrowdFundingApplication.sharedPreferences().edit();

        editor.putString(KEY_USERNAME, m_username);
        editor.putString(KEY_PASSWORD, encrypt("mystery", m_password));
        editor.putString(KEY_PSEUDO, m_user.getResourceId());
        editor.putBoolean(KEY_ADMIN, m_administrator);

        editor.apply();
    }


    /* --------- Cryptography part ------------ */
    private final static String HEX = "0123456789ABCDEF";
    private static String encrypt(String seed, String password)  {
        byte[] result;
        try {
            byte[] rawKey = getRawKey(seed.getBytes());
            result = encrypt(rawKey, password.getBytes());
        }
        catch(Exception exception) {
            return password;
        }
        return toHex(result);
    }

    private static String decrypt(String seed, String encrypted)  {
        byte[] result;
        try {
            byte[] rawKey = getRawKey(seed.getBytes());
            byte[] enc = toByte(encrypted);
            result = decrypt(rawKey, enc);
        }
        catch(Exception exception) {
            return encrypted;
        }
        return new String(result);
    }

    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(seed);
        keyGenerator.init(128, sr); // 192 and 256 bits may not be available
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] raw = secretKey.getEncoded();
        return raw;
    }


    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    private static byte[] toByte(String hexString) {
        int len = hexString.length()/2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
        return result;
    }

    private static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2*buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
    }
    /* ---------------------------------------- */
}
